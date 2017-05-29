package com.whistle.blooddonor.Retrofit;

import android.content.Context;

import com.whistle.blooddonor.common.CommonKeyUtility;

import retrofit2.Response;


/**
 * Created by garvit on 20/5/17.
 */


public interface RetrofitTaskListener<M> {
    public void onRetrofitTaskComplete(Response<M> response, Context context, CommonKeyUtility.CallerFunction callerFunction);

    public void onRetrofitTaskFailure(Throwable t, CommonKeyUtility.CallerFunction callerFunction);
}