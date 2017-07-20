package com.sike.courses.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.TimeUnit;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.JobManager;
import com.sike.courses.CoursesApp;
import com.sike.courses.activity.MyAdapter;
import com.sike.courses.job.http.GetDataJob;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


/**
 * Created by agritsenko on 13.07.2017.
 */



public class MyService extends Service {

    protected SharedPreferences sp;


    protected JobManager jobManager;
    final String LOG_TAG = "ServiceLogs";
    int [] threads = new int[2];
    int i = 0;
    boolean alreadyStartedThread = false;
    ArrayList<MyRun> activeThreads = new ArrayList<>();

    ExecutorService es = Executors.newFixedThreadPool(2);

    public void onCreate() {
        super.onCreate();
        //Log.d(LOG_TAG, "MyService onCreate");
        Log.d(LOG_TAG, "Создался");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        int time = intent.getIntExtra("time",1);
        Log.d(LOG_TAG, "Созданный поток");
        MyRun thread = new MyRun(time, startId, true);
        threads[i]=startId;
        if(startId == 1&&!(alreadyStartedThread)){
            es.execute(thread);
            activeThreads.add(thread);
            alreadyStartedThread = true;
            i++;
        }
        if(startId >1&&alreadyStartedThread){
            thread.stop(threads[i]-1);
            activeThreads.add(thread);
            es.execute(thread);
        }
        //return super.onStartCommand(intent, flags, startId);
        return START_NOT_STICKY;
    }

    public void onDestroy() {
        //activeThreads.get(0). = false;
        activeThreads.get(i-1).stop(activeThreads.get(i-1).startId);
        activeThreads.get(i).stop(activeThreads.get(i).startId);
        es.shutdownNow();
        i=0;
        super.onDestroy();
        Log.d(LOG_TAG, "Уничтожился");
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "ОнБинд");
        return null;
    }


    class MyRun implements Runnable {

        public int time;
        public int startId;
        protected boolean update;

        public MyRun(int time, int srartId, boolean update) {
            this.time = time;
            this.startId = srartId;
            this.update = update;
            Log.d(LOG_TAG, "MyRun#" + srartId + " create");
        }

        @Override
        public void run() {
            Log.d(LOG_TAG, "MyRun#" + startId + " start, time = " + time +" thread = "+threads[i]);
            for (int i = 1; update; i++) {
                Log.d(LOG_TAG, startId+"# i= " + i);
                jobManager = CoursesApp.getInstance().getJobManager();
                jobManager.addJobInBackground(new GetDataJob());
                try {
                    java.util.concurrent.TimeUnit.SECONDS.sleep(time*60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            stopSelf();
        }

        void stop(int startId){
            Log.d(LOG_TAG, "MyRun#" + startId + " end, stopSelf(" + startId + ")");
            if(!(es.equals(activeThreads.get(startId-1)))){
                MyRun curThread = activeThreads.get(startId-1);
                curThread.update = false;
            }
        }
    }
}
