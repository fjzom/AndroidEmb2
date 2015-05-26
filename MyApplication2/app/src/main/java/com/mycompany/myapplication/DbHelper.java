package com.mycompany.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 7 on 2/14/2015.
 */
public class DbHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "TAREA";

    // Table columns
    public static final String _ID = "TAREA_ID";
    public static final String TODO_SUBJECT = "subject";
    public static final String TODO_DESC = "description";

    // Database Information
    static final String DB_NAME = "AGENDADB.DB";

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


    public List<Tarea> getAllTareas()
    {
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
                tareaList.add(objTarea);
            } while (cursor.moveToNext());
        }

        return tareaList;

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

    public List<String> getFieldFromTable(String fieldToSelect){
        List<String> fieldList = new ArrayList<>();
        String selectQuery = "SELECT " +fieldToSelect+" FROM TAREA";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do {
                String field;
                field = cursor.getString(0);
                fieldList.add(field);
            } while (cursor.moveToNext());
        }
        return fieldList;
    }
}
