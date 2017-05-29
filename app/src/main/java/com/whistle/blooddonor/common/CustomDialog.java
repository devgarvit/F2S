package com.whistle.blooddonor.common;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.whistle.blooddonor.R;

/**
 * Created by garvit on 22/5/17.
 */
public class CustomDialog extends Dialog implements View.OnClickListener {
    Activity mActivity;
    String mMessage;

    public CustomDialog(Activity activity, String message) {
        super(activity);

        this.mActivity = activity;
        this.mMessage = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_dialog);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView txtMessage = (TextView) findViewById(R.id.txt_message);
        txtMessage.setText(mMessage);
        Button btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
        }

    }
}
