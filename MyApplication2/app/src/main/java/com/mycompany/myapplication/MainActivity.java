package com.mycompany.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        //noinspection SimplifiableIfStatement
        switch(item.getItemId()) {
            case R.id.twitter:
                Log.i("", "Twitter item clicked");
                return true;

            case R.id.facebook:
                Log.i("", "Facebook item clicked");
                return true;

            case R.id.action_settings:
                Log.i("", "Settings item clicked");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startActivity(View view) {
        Intent newActivity = new Intent(this,MainActivity2.class);
        String message;
        EditText text =(EditText) findViewById(R.id.editText);
        message = text.getText().toString();
        if (message.trim().isEmpty()){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Error");
            dialog.setMessage("Por favor ingresa algun caracter en el Edit Text");
            dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }else {
            newActivity.putExtra("Edit Text", message);
            startActivity(newActivity);
        }
    }
}
