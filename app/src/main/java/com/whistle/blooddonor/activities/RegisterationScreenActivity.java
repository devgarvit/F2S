package com.whistle.blooddonor.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.whistle.blooddonor.Entities.Reachability;
import com.whistle.blooddonor.Entities.User;
import com.whistle.blooddonor.Entities.UserInfoEntity;
import com.whistle.blooddonor.Events.EventLocation;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Response;

/**
 * Created by garvit on 19/5/17.
 */

public class RegisterationScreenActivity extends AppCompatActivity implements View.OnClickListener, RetrofitTaskListener {
    EditText editName, editGender, editPhoneNumber, editDOB;
    TextView txtBloodGroup, txtDOB;
    Button btnNext;
    DatePickerDialog.OnDateSetListener currentDate;
    Calendar calendar;
    private int year, month, day;
    Location mLocation;
    private ProgressDialog progressDialog;
    private static final String TAG = RegisterationScreenActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration_screen);
        initUI();
    }

    private void initUI() {
        txtDOB = (TextView) findViewById(R.id.txt_dob);
        txtBloodGroup = (TextView) findViewById(R.id.txt_blood_group);
        editName = (EditText) findViewById(R.id.edit_name);
        editGender = (EditText) findViewById(R.id.edit_gender);
        editDOB = (EditText) findViewById(R.id.edit_dob);
        editPhoneNumber = (EditText) findViewById(R.id.edit_phone_number);
        btnNext = (Button) findViewById(R.id.btn_next);

        progressDialog = new ProgressDialog(this);
        btnNext.setOnClickListener(this);
        txtDOB.setOnClickListener(this);
        editDOB.setOnClickListener(this);
        if (getIntent().getExtras().containsKey("blood_group"))
            txtBloodGroup.setText(getIntent().getStringExtra("blood_group"));

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        setDate(year, month, day, editDOB, true);

        setCacheDetails(editName, editGender, editDOB, editPhoneNumber);

        currentDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setDate(year, month, dayOfMonth, editDOB, false);
            }
        };
    }

    private void setCacheDetails(EditText editName, EditText editGender, EditText editDOB, EditText editPhoneNumber) {
        if ((new TinyDB(getApplicationContext()).getString("name")) != null)
            editName.setText(new TinyDB(getApplicationContext()).getString("name"));
        if ((new TinyDB(getApplicationContext()).getString("gender")) != null)
            editGender.setText(new TinyDB(getApplicationContext()).getString("gender"));
        if ((new TinyDB(getApplicationContext()).getString("dob")) != null)
            editDOB.setText(new TinyDB(getApplicationContext()).getString("dob"));
        if ((new TinyDB(getApplicationContext()).getString("phone")) != null)
            editPhoneNumber.setText(new TinyDB(getApplicationContext()).getString("phone"));
    }

    private void setDate(int year, int monthOfYear, int dayOfMonth, EditText editDOB, boolean isHint) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String myFormat = "dd MMM yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
//        if (isHint)
//            editDOB.setHint(sdf.format(calendar.getTime()));
//        else
        editDOB.setText(sdf.format(calendar.getTime()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_dob:
            case R.id.txt_dob:
                new DatePickerDialog(RegisterationScreenActivity.this, currentDate, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.btn_next:
                if (editName.getText().length() == 0) {
                    editName.setError("Name Can't be Empty");
                    return;
                } else if (editGender.getText().length() == 0) {
                    editGender.requestFocus();
                    editGender.setError("Gender Can't be Empty");
                    return;
                } else if (editDOB.getText().length() == 0) {
                    editDOB.requestFocus();
                    editDOB.setError("DOB Can't be Empty");
                    return;
                } else if (editPhoneNumber.getText().length() < 10) {
                    editPhoneNumber.requestFocus();
                    editPhoneNumber.setError("Invalid Phone Number");
                    return;
                } else {
                    syncDetails(editName.getText().toString(), editPhoneNumber.getText().toString());
                    cacheDetails(editName.getText().toString(), editGender.getText().toString(), editDOB.getText().toString(), editPhoneNumber.getText().toString());

                }


                break;

            default:
                break;
        }
    }

    private void syncDetails(String name, String phone) {
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
        user.setName(name);
        user.setPhone("+91" + phone);
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

        retrofit(getApplicationContext(), userInfoEntity);

    }

    private void cacheDetails(String Name, String Gender, String DOB, String Phone) {
        new TinyDB(getApplicationContext()).putString("name", Name);
        new TinyDB(getApplicationContext()).putString("gender", Gender);
        new TinyDB(getApplicationContext()).putString("dob", DOB);
        new TinyDB(getApplicationContext()).putString("phone", Phone);
    }

    @Override
    public void onRetrofitTaskComplete(Response response, Context context, CommonKeyUtility.CallerFunction callerFunction) {
        dismissProgressBar();
        if (response != null) {
            if (response.code() == 200) {
                Intent intent = new Intent(RegisterationScreenActivity.this, PhoneValidationActivity.class);
                intent.putExtra("phone_number", editPhoneNumber.getText().toString());
                startActivity(intent);
            } else
                Toast.makeText(getApplicationContext(), response.code() + " Failed", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onRetrofitTaskFailure(Throwable t, CommonKeyUtility.CallerFunction callerFunction) {
        dismissProgressBar();
        CommonUtility.alertDialog("We could not reach the server.Please try again",RegisterationScreenActivity.this);
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
    }

    @Subscribe
    public void onEvent(EventLocation event) {
        mLocation = event.getLocation();
    }

    private void retrofit(Context context, UserInfoEntity userInfoEntity) {
        if (CommonUtility.isNetworkAvailable(context)) {
            showProgressBar(false);
            String url = NetworkUtils.URL_USER_INFO();
            RetrofitTask task = new RetrofitTask<>(this, CommonKeyUtility.HTTP_REQUEST_TYPE.POST, CommonKeyUtility.CallerFunction.POST_USER_INFO, url, getApplicationContext(), userInfoEntity);
            task.execute(false);
        } else {
            CommonUtility.alertDialog("No Internet Connection",RegisterationScreenActivity.this);
        }
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
