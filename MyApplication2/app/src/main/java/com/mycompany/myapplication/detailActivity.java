package com.mycompany.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class detailActivity extends ActionBarActivity {

    public TextView nomTask,mat,dateTask,timeTask,descTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get the message from the intent
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        nomTask = (TextView) findViewById(R.id.searchTarea);
        mat = (TextView) findViewById(R.id.searchMateria);
        dateTask = (TextView) findViewById(R.id.searchTime);
        timeTask = (TextView) findViewById(R.id.searchDate);
        descTask = (TextView) findViewById(R.id.searchDescripcion);
        DbHelper dbh = new DbHelper(this);
        SQLiteDatabase db = dbh.getWritableDatabase();

        String[] campos = new String[]{"tarea_id", "nombre", "descripcion", "materia", "hora", "fecha"};
        String[] args = new String[]{message};

        Cursor c = db.query("TAREA", campos, "nombre=?", args, null, null, null);


        if (c.moveToFirst()) {
            String nombreTaskSearch = c.getString(1);
            String descripcionTaskSearch = c.getString(2);
            String materiaTaskSearch = c.getString(3);
            String horaTaskSearch = c.getString(4);
            String dateTaskSearch = c.getString(5);

            nomTask.setText(nombreTaskSearch);
            mat.setText(materiaTaskSearch);
            dateTask.setText(dateTaskSearch);
            timeTask.setText(horaTaskSearch);
            descTask.setText(descripcionTaskSearch);


        } else {

            Toast.makeText(getApplicationContext(),
                    "Ha ocurrido un error :(", Toast.LENGTH_LONG).show();
            db.close();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
