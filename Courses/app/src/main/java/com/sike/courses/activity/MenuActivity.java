package com.sike.courses.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.birbit.android.jobqueue.JobManager;
import com.sike.courses.CoursesApp;
import com.sike.courses.R;
import com.sike.courses.api.response.RequestResult;
import com.sike.courses.event.httpEvent.BaseHttpEvent;
import com.sike.courses.job.http.GetDataJob;
import com.sike.courses.mvp.model.Course;
import com.sike.courses.service.MyService;
import com.sike.courses.service.NotificationService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MenuActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    protected JobManager jobManager;
    protected ArrayList<Course> tmpArrayList;
    protected ArrayList<Course> tmpCBList;
    protected ArrayList<Course> tmpArrayListCB = new ArrayList<>();
    protected MyAdapter mAdapter;
    protected Intent mIntent;
    protected SharedPreferences sp;
    String tmpStr;
    String LOG_TAG = "ServiceLogs";
    final String FILENAME = "CBdate";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        sp = PreferenceManager.getDefaultSharedPreferences(this);
        PreferenceManager.setDefaultValues(this, R.xml.pref, false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        jobManager = CoursesApp.getInstance().getJobManager();
        jobManager.addJobInBackground(new GetDataJob());


    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onDestroy() {
        sp.edit().clear().commit();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_info) {
            mIntent = new Intent("android.intent.action.INFO");
            startActivity(mIntent);
        }

        if(id == R.id.nav_settings){
            mIntent = new Intent("android.intent.action.OPTIONS_CHANGED");
            startActivity(mIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseHttpEvent event) {
        if(event.getResult().getRequestResult().equals(RequestResult.SUCCESS)
                || event.getResult().getRequestResult().equals(RequestResult.CACHE)) {

            HashMap<Object, ArrayList<Course>> list = event.getResult().getTypedAnswer();

            onResponse(list, event.type);


        }else {
            onError(event.getResult().getRequestResult());
        }
    }


    protected void onResponse(HashMap<Object, ArrayList<Course>> data, int event) {
        if(tmpArrayList == null){
            tmpCBList = data.get("currency");
            for(Course obj: tmpCBList){
                if(!(obj.isSubnameNull(obj))){
                    if(obj.getSubname().equals("ЦБ.")){
                        tmpArrayListCB.add(obj);
                    }
                }
            }
        }
        tmpArrayList = data.get("cash");
        tmpArrayList.addAll(tmpArrayList.size(), data.get("currency"));
        tmpArrayList.addAll(tmpArrayList.size(), data.get("indices"));
        mAdapter = new MyAdapter(this, tmpArrayList);
        GridView gvMain = (GridView) findViewById(R.id.gvMain);
        gvMain.setAdapter(mAdapter);
        Log.d(LOG_TAG, "TmpCBDate="+tmpArrayListCB.get(0).getDate()+" CBDate="+tmpCBList.get(3).getDate());
        if(!(tmpArrayListCB.get(0).getDate().equals(tmpCBList.get(3).getDate()))){
            startService(new Intent(this, NotificationService.class));
        }else {
            stopService(new Intent(this, NotificationService.class));
        }

    }


    protected void onError(RequestResult requestResult){
        String errorMsg;
        if(requestResult.equals(RequestResult.ERROR)){
            errorMsg = "Please check your network connection!";
        }else{
            errorMsg = "Server error!";
        }
        Toast toast = Toast.makeText(getApplicationContext(),
                errorMsg, Toast.LENGTH_SHORT);
        toast.show();
    }

}
