package com.mycompany.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Usuario on 29/05/2015.
 */
public class AlarmBrod extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, context.getResources().getString(R.string.inicio_alarma), Toast.LENGTH_LONG).show();

        Intent act = new Intent(context,AlarmReceiver.class);
        act.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(act);

    }
}
