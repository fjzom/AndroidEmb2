package com.mycompany.myapplication;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Usuario on 15/05/2015.
 */
public class CustomArrayAdapter extends ArrayAdapter<String> {

    String[] taskArray, dateArray,timeArray;
    public CustomArrayAdapter(Context context, int textViewResourceId, String[] taskArray, String[] dateArray, String[] timeArray) {
        super(context, textViewResourceId);
        this.dateArray = dateArray;
        this.taskArray = taskArray;
        this.timeArray = timeArray;
    }

    public CustomArrayAdapter(Context context, int resource, String[] taskArray, String[] items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_array_adapter, null);
        }

        //String p = getItem(position);
//
        TextView dateToShow = (TextView)v.findViewById(R.id.date);
        TextView taskToShow = (TextView)v.findViewById(R.id.task);
        TextView timeToShow = (TextView)v.findViewById(R.id.time);
        dateToShow.setText(dateArray[position]);
        taskToShow.setText(taskArray[position]);
        timeToShow.setText(timeArray[position]);
//        if (p != null) {
//            TextView tt1 = (TextView) v.findViewById(R.id.id);
//            TextView tt2 = (TextView) v.findViewById(R.id.categoryId);
//            TextView tt3 = (TextView) v.findViewById(R.id.description);
//
//            if (tt1 != null) {
//                tt1.setText(p.getId());
//            }
//
//            if (tt2 != null) {
//                tt2.setText(p.getCategory().getId());
//            }
//
//            if (tt3 != null) {
//                tt3.setText(p.getDescription());
//            }
//        }

        return v;
    }

    @Override
    public int getCount(){
        return dateArray.length;
    }

}