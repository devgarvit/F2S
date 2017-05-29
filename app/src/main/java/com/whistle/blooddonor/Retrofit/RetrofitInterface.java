package com.whistle.blooddonor.Retrofit;

import com.whistle.blooddonor.Entities.CodeVerifyEntity;
import com.whistle.blooddonor.Entities.UserInfoEntity;
import com.whistle.blooddonor.Entities.WhistleUnAuthEntity;
import com.whistle.blooddonor.Events.EventUserInfo;
import com.whistle.blooddonor.Events.EventWhistleList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;


/**
 * Created by garvit on 20/5/17.
 */
public interface RetrofitInterface<M> {

    @POST
    Call<Void> postUserInfo(@Body UserInfoEntity userInfoEntity, @Url String url);

    @POST
    Call<EventUserInfo> codeVerify(@Body CodeVerifyEntity codeVerifyEntity, @Url String url);

    @GET
    Call<EventWhistleList> getWhistleAuth( @Url String url);

    @POST
    Call<EventWhistleList> postWhistleUnAuth(@Body WhistleUnAuthEntity whistleUnAuthEntity, @Url String url);
}
