package com.csp.cases.activity.network.jobscheduler;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;

import com.csp.utils.android.log.LogCat;

/**
 * Created by chenshp on 2018/4/12.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        LogCat.e("onStartJob");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        LogCat.e("onStopJob");
        return false;
    }
}
