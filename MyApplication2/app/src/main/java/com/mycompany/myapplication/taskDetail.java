package com.mycompany.myapplication;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by CarlosTadeo on 25/04/2015.
 */
public class taskDetail extends Activity{
    public String nombre;
    public TextView nomTask,mat,dateTask,timeTask,descTask;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_search);

        nomTask = (TextView) findViewById(R.id.searchTarea);
        mat = (TextView) findViewById(R.id.searchMateria);
        dateTask = (TextView) findViewById(R.id.searchTime);
        timeTask = (TextView) findViewById(R.id.searchDate);
        descTask = (TextView) findViewById(R.id.searchDescripcion);


        Bundle bundle = getIntent().getExtras();
        nombre = bundle.getString("nombre");


        DbHelper dbh = new DbHelper(this);
        SQLiteDatabase db = dbh.getWritableDatabase();

        String[] campos = new String[]{"tarea_id", "nombre", "descripcion", "materia", "hora", "fecha"};
        String[] args = new String[]{nombre};

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
}
