package com.mycompany.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 7 on 2/14/2015.
 */
public class DbHelper extends SQLiteOpenHelper {

    //private static final String  DB_NAME = "agendadb";
    //private static final int DB_SCHEME_VERSION = 1;

    //public DbHelper(Context context) {
      //  super(context, DB_NAME, null, DB_SCHEME_VERSION);
    //
    //}
    public DbHelper(Context context) {
    super(context,"agendadb",null,1);
    }
    public DbHelper(Context context, String nombre, CursorFactory factory, int version) {
        super(context, nombre, factory, version);
    }

        @Override
    public void onCreate(SQLiteDatabase db) {
       //crear base de datos
        db.execSQL("create table tarea(tarea_id integer primary key autoincrement, nombre text, descripcion text, materia text, hora text, fecha text)");
        //db.execSQL(DataBaseManager.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists tarea");
        db.execSQL("create table tarea(tarea_id integer primary key autoincrement, nombre text, descripcion text, materia text, hora text, fecha text)");
    }


    public Cursor getInfo(){
        Cursor cursor = getReadableDatabase().rawQuery("select * from tarea", null);

        return cursor;
    }


    public String getAllTareas(int id)
    {
        String name="";
        List<Tarea> tareaList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM TAREA" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Tarea objTarea = new Tarea();
                objTarea.setTarea_id(Integer.parseInt(cursor.getString(0)));

                objTarea.setNombre(cursor.getString(1));
                objTarea.setDescripcion(cursor.getString(2));
                // Adding tarea to list
                if(objTarea.getTarea_id()==id){name=objTarea.getNombre();
                continue;
                }
                tareaList.add(objTarea);
            } while (cursor.moveToNext());
        }

        return name;

    }
    public List<String> tareaList(){
        List<String> tareaLista = new ArrayList<>();
        String selectQuery = "SELECT nombre FROM TAREA";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do {
                String nombre;
                nombre = cursor.getString(0);
                tareaLista.add(nombre);
            } while (cursor.moveToNext());
        }
        return tareaLista;
    }

    public List<String> getFieldFromTable(String fieldToSelect,int typeQuery){
        List<String> fieldList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        switch(typeQuery) {
            case 0:
            String selectQuery = "SELECT " + fieldToSelect + " FROM TAREA";


            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    String field;
                    field = cursor.getString(0);
                    fieldList.add(field);
                } while (cursor.moveToNext());
            }
                break;
            case 1:

                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                String today = day+"/"+(month+1)+"/"+year;
                String[] arg = new String[]{today};
                String selectQueryAll = "SELECT " + fieldToSelect + " FROM TAREA where fecha = ?";


                Cursor cursor2 = db.rawQuery(selectQueryAll, arg);

                if (cursor2.moveToFirst()) {
                    do {
                        String field;
                        field = cursor2.getString(0);
                        fieldList.add(field);
                    } while (cursor2.moveToNext());
                }else{
                    String selectQuery3 = "SELECT " + fieldToSelect + " FROM TAREA";


                    Cursor cursor3 = db.rawQuery(selectQuery3, null);

                    if (cursor3.moveToFirst()) {
                        do {
                            String field;
                            field = cursor3.getString(0);
                            fieldList.add(field);
                        } while (cursor3.moveToNext());
                    }

                }
                break;
        }
        return fieldList;
    }
}
