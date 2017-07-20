package com.sike.courses;

import android.app.Application;
import android.util.Log;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.birbit.android.jobqueue.log.CustomLogger;
import com.birbit.android.jobqueue.timer.Timer;


/**
 * Created by agritsenko on 29.05.2017.
 */

public class CoursesApp extends Application {
    private JobManager jobManager;
    private static CoursesApp app;
    private static final String TAG = "MyApplication";
    public static CoursesApp getInstance() {
        return app;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        app = this;


        configureJobManager();
    }

    private void configureJobManager() {
        Log.d(TAG, "configureJobManager START");
        Configuration configuration = new Configuration.Builder(this)
                .customLogger(new CustomLogger() {


                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        Log.d(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Log.e(TAG, String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        Log.e(TAG, String.format(text, args));
                    }

                    @Override
                    public void v(String text, Object... args) {

                    }
                }).minConsumerCount(0)// always keep at least five consumerjob
                // alive
                .maxConsumerCount(5)// up to 10 consumers at a time
                .loadFactor(3)// 3 jobs per consumer
                .consumerKeepAlive(15)// wait 15 sec
                .build();
        jobManager = new JobManager(configuration);
        Log.d(TAG, "configureJobManager END");
    }

    public JobManager getJobManager() {
        return jobManager;
    }

    public Timer customizeTimer(){
        return null;
    }

}
