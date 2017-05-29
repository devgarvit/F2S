package com.whistle.blooddonor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.whistle.blooddonor.R;

/**
 * Created by garvit on 19/5/17.
 */
public class SpinnerAdapter extends ArrayAdapter<String> {

    private String[] mListBloodGroups;
    private Context context;
    private int resourceId;

    public SpinnerAdapter(Context context, int resourceId,
                          String[] listBloodGroups) {
        super(context, resourceId, listBloodGroups);
        this.mListBloodGroups = listBloodGroups;
        this.context = context;
        this.resourceId = resourceId;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(resourceId, parent, false);
        TextView textView = (TextView) row.findViewById(R.id.txt_blood_group);
        textView.setText(mListBloodGroups[position]);

        return row;
    }
}