package com.sike.courses.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.birbit.android.jobqueue.JobManager;
import com.sike.courses.CoursesApp;
import com.sike.courses.R;
import com.sike.courses.api.response.RequestResult;
import com.sike.courses.event.httpEvent.BaseHttpEvent;
import com.sike.courses.job.http.GetDataJob;
import com.sike.courses.mvp.model.Course;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by agritsenko on 17.07.2017.
 */

public class NotificationService extends Service{


    NotificationManager nm;
    NotificationCompat.Builder mBuilder;


    @Override
    public void onCreate() {
        super.onCreate();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder =
                new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_drawer)
                .setContentTitle("Курсы")
                .setContentText("Установлены новые курсы ЦБ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendNotif();
        return super.onStartCommand(intent, flags, startId);
    }


    void sendNotif() {
        nm.notify(1, mBuilder.build());
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
