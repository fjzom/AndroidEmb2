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
    int typerQuery;
    String[] taskArray, dateArray,timeArray,idArray;
    public CustomArrayAdapter(Context context, int textViewResourceId, String[] taskArray, String[] dateArray, String[] timeArray, String[] idArray, int typeQuery) {
        super(context, textViewResourceId);
        this.dateArray = dateArray;
        this.taskArray = taskArray;
        this.timeArray = timeArray;
        this.idArray = idArray;
        this.typerQuery= typeQuery;
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
        TextView idToShow =(TextView)v.findViewById(R.id.idItemTask);
        dateToShow.setText(dateArray[position]);
        taskToShow.setText(taskArray[position]);
        timeToShow.setText(timeArray[position]);
        idToShow.setText(idArray[position]);


        return v;
    }

    @Override
    public int getCount(){
        return dateArray.length;
    }

}