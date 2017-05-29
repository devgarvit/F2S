package com.whistle.blooddonor.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.whistle.blooddonor.Entities.WhistleUnAuthEntity;
import com.whistle.blooddonor.Events.EventLocation;
import com.whistle.blooddonor.Events.EventWhistleList;
import com.whistle.blooddonor.Events.WhistleList;
import com.whistle.blooddonor.Persistent.TinyDB;
import com.whistle.blooddonor.R;
import com.whistle.blooddonor.Retrofit.RetrofitTask;
import com.whistle.blooddonor.Retrofit.RetrofitTaskListener;
import com.whistle.blooddonor.Utils.NetworkUtils;
import com.whistle.blooddonor.adapters.SpinnerAdapter;
import com.whistle.blooddonor.common.CommonKeyUtility;
import com.whistle.blooddonor.common.CommonUtility;
import com.whistle.blooddonor.common.IconGenerator;
import com.whistle.blooddonor.location.LocationRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by garvit on 18/5/17.
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, RetrofitTaskListener, AdapterView.OnItemSelectedListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener, View.OnClickListener {

    GoogleMap googleMap;
    String[] bloodGroups = {"A+", "A-", "B+",
            "B-", "AB+", "AB-", "O+", "O-", "A1+", "A1-", "A2B+", "A2B-"};
    boolean isSpinnerShows;
    Location mLocation;
    TextView txtName, txtGender, txtPhone;
    private ProgressDialog progressDialog;
    LinearLayout llUserData, llLookingDonor;
    private static final String TAG = MapActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initUI();
        initMap();

        if (new TinyDB(getApplicationContext()).getBoolean("isRegistered")) {
            isSpinnerShows = false;
            getSupportActionBar().setTitle("Around You");

            String url = NetworkUtils.URL_WHISTLES_AUTHORIZED() + "?limit=10&radius=10";
            Log.d(TAG, url);
            RetrofitTask task = new RetrofitTask<>(this, CommonKeyUtility.HTTP_REQUEST_TYPE.GET, CommonKeyUtility.CallerFunction.GET_WHISTLES_AUTHORIZED, url, getApplicationContext());
            task.execute(true);
        } else {
            isSpinnerShows = true;
            getSupportActionBar().setTitle("Donors around you");

            retrofit("All", getApplicationContext());
        }

    }

    private void initUI() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eb5353")));
        progressDialog = new ProgressDialog(this);
        txtName = (TextView) findViewById(R.id.txt_name);
        txtGender = (TextView) findViewById(R.id.txt_gender);
        txtPhone = (TextView) findViewById(R.id.txt_phone_number);
        llUserData = (LinearLayout) findViewById(R.id.ll_user_data);
        llLookingDonor = (LinearLayout) findViewById(R.id.ll_looking_donor);
        llLookingDonor.setOnClickListener(this);
    }

    private void retrofit(String keyword, Context context) {
        if (CommonUtility.isNetworkAvailable(context)) {
            showProgressBar(false);
            WhistleUnAuthEntity entity = new WhistleUnAuthEntity();
            entity.setRadius(10);
            entity.setLimit(10);
            entity.setCategory("f2s");
            entity.setKeyword(keyword);
            List<Double> location = new ArrayList<>();
            if (mLocation != null) {
                location.add(mLocation.getLatitude());
                location.add(mLocation.getLongitude());
            } else if ((Double) getIntent().getExtras().get("lat") != 0.0) {
                location.add((Double) getIntent().getExtras().get("lat"));
                location.add((Double) getIntent().getExtras().get("lng"));
            } else {
                location.add(0.0);
                location.add(0.0);
            }
            entity.setLocation(location);
            animateCamera(location);

            Gson gson = new Gson();
            String str = gson.toJson(entity);
            String url = NetworkUtils.URL_WHISTLES_UNAUTHORIZED();
            Log.d(TAG, str);
            Log.d(TAG, url);
            RetrofitTask task = new RetrofitTask<>(this, CommonKeyUtility.HTTP_REQUEST_TYPE.POST, CommonKeyUtility.CallerFunction.POST_WHISTLES_UNAUTHORIZED, url, getApplicationContext(), entity);
            task.execute(false);
        } else {
            CommonUtility.alertDialog("No Internet Connection", MapActivity.this);
        }
    }

    private void animateCamera(List<Double> latlng) {
        try {
            LatLng coordinate = new LatLng(latlng.get(0), latlng.get(1)); //Store these lat lng values somewhere. These should be constant.
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                    coordinate, 15);
            googleMap.animateCamera(location);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMapClickListener(this);
    }

    @Override
    public void onRetrofitTaskComplete(Response response, Context context, CommonKeyUtility.CallerFunction callerFunction) {
        dismissProgressBar();
        if (response != null) {
            if (callerFunction == CommonKeyUtility.CallerFunction.GET_WHISTLES_AUTHORIZED) {
                if (response.code() == 200) {
                    EventWhistleList whistleList = (EventWhistleList) response.body();
                    setMarker(whistleList, true);
                } else
                    Toast.makeText(getApplicationContext(), response.code() + " Failed", Toast.LENGTH_SHORT).show();
            } else if (callerFunction == CommonKeyUtility.CallerFunction.POST_WHISTLES_UNAUTHORIZED) {
                if (response.code() == 200) {
                    EventWhistleList whistleList = (EventWhistleList) response.body();
                    setMarker(whistleList, false);
                } else
                    Toast.makeText(getApplicationContext(), response.code() + " Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setMarker(EventWhistleList whistleList, boolean isAuthorized) {
        googleMap.clear();
        LatLng startLatLng = null, endLatLng = null;
        MarkerOptions markerOptions;


        for (int i = 0; i < whistleList.getMatchingWhistles().size(); i++) {

            Double latitude = whistleList.getMatchingWhistles().get(i).getLocation().getCoordinates().get(0);
            Double longitude = whistleList.getMatchingWhistles().get(i).getLocation().getCoordinates().get(1);
            LatLng location = new LatLng(latitude, longitude);

            if (isAuthorized) {
                markerOptions = new MarkerOptions().position(location)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
                Marker marker = googleMap.addMarker(markerOptions);
                marker.setTag("");
            } else {
                IconGenerator iconFactory = new IconGenerator(this);
                markerOptions = new MarkerOptions().position(location)
                        .icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(whistleList.getMatchingWhistles().get(i).getWhistles().getSubCategory())));
                Marker marker = googleMap.addMarker(markerOptions);
                marker.setTag(whistleList.getMatchingWhistles().get(i));
            }

            if (i == 0)
                startLatLng = location;
            endLatLng = location;
        }
        if (startLatLng != null && endLatLng != null)
            setBounds(startLatLng, endLatLng);
    }

    private void setBounds(LatLng startLatLng, LatLng endLatLng) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(startLatLng).include(endLatLng);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100));
    }


    @Override
    public void onRetrofitTaskFailure(Throwable t, CommonKeyUtility.CallerFunction callerFunction) {
        dismissProgressBar();
        CommonUtility.alertDialog("We could not reach the server.Please try again", MapActivity.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.action_bar_spinner, menu);
//
//        MenuItem item = menu.findItem(R.id.spinner);
//        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
//
////        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
////                R.array.blood_groups, R.layout.spinner_text_view);
////        adapter.setDropDownViewResource(R.layout.spinner_item_select_groups);
//
//        ArrayAdapter<String> myAdapter = new SpinnerAdapter(this, R.layout.spinner_item_maps, bloodGroups);
//        spinner.setAdapter(myAdapter);
//        spinner.setOnItemSelectedListener(this);
//        return isSpinnerShows;
//    }
//
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        retrofit(bloodGroups[position], getApplicationContext());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (!marker.getTag().equals("")) {
            llUserData.setVisibility(View.VISIBLE);
            WhistleList list = (WhistleList) marker.getTag();
            txtName.setText(list.getName());
            txtGender.setText(list.getWhistles().getComment());
            txtPhone.setText(list.getPhone());
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_spinner, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.blood_groups, R.layout.spinner_text_view);
//        adapter.setDropDownViewResource(R.layout.spinner_item_select_groups);

        ArrayAdapter<String> myAdapter = new SpinnerAdapter(this, R.layout.spinner_item_maps, bloodGroups);
        spinner.setAdapter(myAdapter);
        spinner.setOnItemSelectedListener(this);
        return isSpinnerShows;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        llUserData.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_looking_donor:
                isSpinnerShows = true;
                getSupportActionBar().setTitle("Donors around you");

                retrofit("All", getApplicationContext());
                invalidateOptionsMenu();
                break;
        }
    }


}
