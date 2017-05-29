package com.whistle.blooddonor.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.whistle.blooddonor.Entities.CodeVerifyEntity;
import com.whistle.blooddonor.Entities.Reachability;
import com.whistle.blooddonor.Entities.User;
import com.whistle.blooddonor.Entities.UserInfoEntity;
import com.whistle.blooddonor.Events.EventLocation;
import com.whistle.blooddonor.Events.EventUserInfo;
import com.whistle.blooddonor.Persistent.TinyDB;
import com.whistle.blooddonor.R;
import com.whistle.blooddonor.Retrofit.RetrofitTask;
import com.whistle.blooddonor.Retrofit.RetrofitTaskListener;
import com.whistle.blooddonor.Utils.NetworkUtils;
import com.whistle.blooddonor.common.CommonKeyUtility;
import com.whistle.blooddonor.common.CommonUtility;
import com.whistle.blooddonor.location.LocationRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by garvit on 19/5/17.
 */

public class PhoneValidationActivity extends AppCompatActivity implements View.OnClickListener, RetrofitTaskListener {

    TextView txtPhoneNumber;
    EditText editSmsCode;
    Button btnApprove, btnResend;
    private static final String TAG = PhoneValidationActivity.class.getSimpleName();
    private EventListener eventListen;
    IntentFilter filter = new IntentFilter();
    Location mLocation;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_validation);

        intUI();
    }

    private void intUI() {
        txtPhoneNumber = (TextView) findViewById(R.id.txt_phone_number);
        editSmsCode = (EditText) findViewById(R.id.edit_sms_code);
        btnApprove = (Button) findViewById(R.id.btn_approve);
        progressDialog = new ProgressDialog(this);
        btnResend = (Button) findViewById(R.id.btn_resend_otp);
        btnApprove.setOnClickListener(this);
        btnResend.setOnClickListener(this);

        if (getIntent().getExtras().containsKey("phone_number"))
            txtPhoneNumber.setText("+91 " + getIntent().getStringExtra("phone_number"));

        eventListen = new EventListener();
        filter.addAction("com.whistle.blooddonor");
        registerReceiver(eventListen, filter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_approve: {
                if (editSmsCode.getText().length() == 6) {
                    CodeVerifyEntity verifyEntity = new CodeVerifyEntity();
                    verifyEntity.setVerify(Integer.parseInt(editSmsCode.getText().toString()));
                    Gson gson = new Gson();
                    String str = gson.toJson(verifyEntity);
                    String url = NetworkUtils.URL_CODE_VERIFY();
                    Log.d(TAG, str);
                    Log.d(TAG, url);

                    RetrofitTask task = new RetrofitTask<>(this, CommonKeyUtility.HTTP_REQUEST_TYPE.POST, CommonKeyUtility.CallerFunction.POST_CODE_VERIFY, url, getApplicationContext(), verifyEntity);
                    task.execute(false);
                }
            }
            break;
            case R.id.btn_resend_otp:
                syncDetails();
                break;
            default:
                break;
        }

    }

    private void syncDetails() {
        List<Double> location = new ArrayList<>();

        if (mLocation != null) {
            location.add(mLocation.getLatitude());
            location.add(mLocation.getLongitude());
        } else {
            location.add(0.0);
            location.add(0.0);
        }

        List<String> whistleImages = new ArrayList<>();
        whistleImages.add("http://www.dowhistle.com/img/themes/meadow/logo-intro.png");
        whistleImages.add("http://www.dowhistle.com/img/themes/meadow/logo-intro.png");

        Reachability reachability = new Reachability();
        reachability.setCall(true);
        reachability.setSMS(true);
        reachability.setEmail(true);

        User user = new User();
        user.setName(new TinyDB(getApplicationContext()).getString("name"));
        user.setPhone("+91" + new TinyDB(getApplicationContext()).getString("phone"));
        user.setLocation(location);
        user.setReachability(reachability);
        user.setPhoto("http://www.dowhistle.com/img/themes/meadow/logo-intro.png");
        user.setCategory("f2s");
        user.setSubCategory(new TinyDB(getApplicationContext()).getString("blood_group"));
        user.setProvider(true);
        user.setComment(new TinyDB(getApplicationContext()).getString("gender"));
        user.setWhistleImages(whistleImages);

        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setUser(user);

        retrofit(getApplicationContext(),userInfoEntity);

    }

    private void retrofit(Context context, UserInfoEntity userInfoEntity) {
        if (CommonUtility.isNetworkAvailable(context)) {
            showProgressBar(false);
            String url = NetworkUtils.URL_USER_INFO();
            RetrofitTask task = new RetrofitTask<>(this, CommonKeyUtility.HTTP_REQUEST_TYPE.POST, CommonKeyUtility.CallerFunction.POST_USER_INFO, url, getApplicationContext(), userInfoEntity);
            task.execute(false);
        } else {
            CommonUtility.alertDialog("No Internet Connection",PhoneValidationActivity.this);
        }
    }

    @Override
    public void onRetrofitTaskComplete(Response response, Context context, CommonKeyUtility.CallerFunction callerFunction) {
        dismissProgressBar();
        if (response != null) {
            if (callerFunction == CommonKeyUtility.CallerFunction.POST_CODE_VERIFY) {
                if (response.code() == 200) {
                    EventUserInfo eventGeofenceResponse = (EventUserInfo) response.body();
                    new TinyDB(getApplicationContext()).putString("accessToken", eventGeofenceResponse.getNewUser().getAccessToken());
                    startActivity(new Intent(PhoneValidationActivity.this, RegisterSuccessActivity.class));
                } else
                    Toast.makeText(getApplicationContext(), response.code() + " Failed", Toast.LENGTH_SHORT).show();


            } else if (callerFunction == CommonKeyUtility.CallerFunction.POST_USER_INFO) {
                if (response.code() != 200)
                    Toast.makeText(getApplicationContext(), response.code() + " Failed", Toast.LENGTH_SHORT).show();

            }
        }

    }


    private class EventListener extends BroadcastReceiver {

        @Override
        public void onReceive(Context t, Intent arg1) {
            try {
                editSmsCode = (EditText) findViewById(R.id.edit_sms_code);
                String code = arg1.getExtras().getString("code");
                editSmsCode.setText(code);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRetrofitTaskFailure(Throwable t, CommonKeyUtility.CallerFunction callerFunction) {
        dismissProgressBar();
        CommonUtility.alertDialog("We could not reach the server.Please try again",PhoneValidationActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocationRequest.getInstance().startFusedLocation(getApplicationContext());
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocationRequest.getInstance().stopLocationRequest(getApplicationContext());
        EventBus.getDefault().unregister(this);

        try {
            if (eventListen != null)
                unregisterReceiver(eventListen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onEvent(EventLocation event) {
        mLocation = event.getLocation();
    }

    private void showProgressBar(boolean isCancellable) {
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(isCancellable);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    private void dismissProgressBar() {
        if (null != progressDialog && progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
