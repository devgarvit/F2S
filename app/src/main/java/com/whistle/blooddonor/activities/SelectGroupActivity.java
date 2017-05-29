package com.whistle.blooddonor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.whistle.blooddonor.Persistent.TinyDB;
import com.whistle.blooddonor.R;
import com.whistle.blooddonor.adapters.SpinnerAdapter;

/**
 * Created by garvit on 17/5/17.
 */

public class SelectGroupActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    String[] bloodGroups = {"A+", "A-", "B+",
            "B-", "AB+", "AB-", "O+", "O-", "A1+", "A1-", "A2B+", "A2B-"};
    String selectedGroup = null;
    Button btnNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_group);

        btnNext = (Button) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);
        Spinner spinner = (Spinner) findViewById(R.id.spinner_select_group);
        ArrayAdapter<String> myAdapter = new SpinnerAdapter(this, R.layout.spinner_item_select_groups, bloodGroups);
        spinner.setAdapter(myAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if (selectedGroup != null) {
                    Intent intent = new Intent(SelectGroupActivity.this, RegisterationScreenActivity.class);
                    intent.putExtra("blood_group", selectedGroup);
                    new TinyDB(getApplicationContext()).putString("blood_group", selectedGroup);
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "Please Select Blood Group", Toast.LENGTH_LONG).show();
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedGroup = bloodGroups[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
