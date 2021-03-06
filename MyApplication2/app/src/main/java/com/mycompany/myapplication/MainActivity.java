package com.mycompany.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    public String nombreTarea, nombreMateria, txtFecha, txtHora, descripcionTarea;
    public Boolean llamarBaseDeDatos;
    public int query = 0;
    public final static String EXTRA_MESSAGE = "com.mycompany.myapplication.MESSAGE";
    public String posString = "";
    private enum camposDataBase{TID ("tarea_id"),Nombre("nombre"), Descripcion("descripcion"), Materia("materia"), Fecha("fecha"), Hora("hora");
        private String nombreCampo;
        private camposDataBase(String nombreCampo) {
            this.nombreCampo = nombreCampo;
        }

        @Override
        public String toString(){
            return nombreCampo;
        } };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String today = day+"/"+month+"/"+year;
        Toast.makeText(getApplicationContext(),"Tareas para hoy: "+today,
                Toast.LENGTH_LONG).show();
        query = 1;
        populateList(query);
        ListView taskListv = (ListView) findViewById(R.id.listView1);
        // React to user clicks on item
        taskListv.setClickable(true);
        taskListv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
                                    long id) {
                String contactId = ((TextView) view.findViewById(R.id.idItemTask)).getText().toString();

               ShowTareaFromList(position,contactId);


            }
        });

    }
    public void ShowTareaFromList(int pos,String id){

        DbHelper admin = new DbHelper(this,"agendadb", null, 1);
        final SQLiteDatabase bd = admin.getWritableDatabase();

                String idTarea =id;
                Cursor c = bd.rawQuery("SELECT * FROM TAREA where tarea_id=?" ,new String [] {idTarea});
                if (c.moveToFirst()) {
                    Intent intent = new Intent(this.getBaseContext(), detailActivity.class);
                    intent.putExtra(EXTRA_MESSAGE, idTarea);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Esta vacia",
                            Toast.LENGTH_LONG).show();

                }





    }
    public void setQueryH(MenuItem item){
      query =1;
        populateList(query);
    }
    public void setQuery(MenuItem item){
     query=0;
        populateList(query);
    }
    private void populateList(int tquery) {
              int typeQuery = tquery;
        DbHelper admin = new DbHelper(this,"agendadb", null, 1);
        final SQLiteDatabase bd = admin.getWritableDatabase();
        List<String> taskList;
        List<String> taskIDList;
        List<String> dateList;
        List<String> timeList;
        List<String> assigList;
        List<String> descList;
        dateList = admin.getFieldFromTable(camposDataBase.Fecha.toString(),typeQuery);
        timeList = admin.getFieldFromTable(camposDataBase.Hora.toString(),typeQuery);
        assigList = admin.getFieldFromTable(camposDataBase.Materia.toString(),typeQuery);
        descList = admin.getFieldFromTable(camposDataBase.Descripcion.toString(),typeQuery);
        taskList = admin.getFieldFromTable(camposDataBase.Nombre.toString(),typeQuery);
        taskIDList= admin.getFieldFromTable(camposDataBase.TID.toString(),typeQuery);
       //taskList = admin.tareaList();

        String[] dateArray = new String[dateList.size()];
        dateArray = dateList.toArray(dateArray);
        String[] taskArray = new String[taskList.size()];
        taskArray = taskList.toArray(taskArray);
        String[] timeArray = new String[timeList.size()];
        timeArray = timeList.toArray(timeArray);
        String[] idArray = new String[taskIDList.size()];
        idArray = taskIDList.toArray(idArray);

        CustomArrayAdapter taskAdapter = new CustomArrayAdapter(this,R.layout.custom_array_adapter,taskArray,dateArray,timeArray,idArray, typeQuery);
        //ArrayAdapter<String> taskArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, taskArray);
        ListView taskListv = (ListView) findViewById(R.id.listView1);
//        taskListv.setAdapter(taskArrayAdapter);
        taskListv.setAdapter(taskAdapter);

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
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void AgregarTarea(MenuItem item) {

        llamarBaseDeDatos = false;
        Button btnCancelar;
        Button btnGuardar;
        final Dialog dialog = new Dialog(this);
        DbHelper admin = new DbHelper(this,"agendadb", null, 1);
        final SQLiteDatabase bd = admin.getWritableDatabase();
        //setting custom layout to dialog
        dialog.setContentView(R.layout.layout_pop_up);
        dialog.setTitle(R.string.nueva_tarea);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();

        final TextView fecha = (TextView) dialog.findViewById(R.id.txtDatePicker);
        final TextView hora = (TextView) dialog.findViewById(R.id.txtTimePicker);
        final EditText tarea = (EditText) dialog.findViewById(R.id.editTarea);
        final EditText materia = (EditText)dialog.findViewById(R.id.editMateria);
        final EditText descripcion = (EditText)dialog.findViewById(R.id.editDescripcion);


        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hora.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle(R.string.hora_de_alarma);
                mTimePicker.show();

            }
        });
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar myCalendar = Calendar.getInstance();

                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        fecha.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                        //updateLabel();
                    }

                };
                DatePickerDialog dateDialog = new DatePickerDialog(MainActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.DAY_OF_MONTH),myCalendar.get(Calendar.MONTH));
                dateDialog.setTitle(R.string.fecha_de_la_tarea);

                dateDialog.show();
            }
        });

        btnCancelar = (Button)dialog.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        btnGuardar = (Button)dialog.findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Faltan validaciones de campos vacios
                String error = "";
                //while()
                nombreTarea = tarea.getText().toString();
                nombreMateria = materia.getText().toString();
                txtFecha = fecha.getText().toString();
                txtHora = hora.getText().toString();
                descripcionTarea = descripcion.getText().toString();
                if(nombreTarea.trim().isEmpty() || nombreMateria.trim().isEmpty() || txtFecha.trim().isEmpty()
                        || txtHora.trim().isEmpty() || descripcionTarea.trim().isEmpty())
                    error = getString(R.string.error);
                if(error.isEmpty()) {
                    llamarBaseDeDatos = true;
                    ContentValues registro = new ContentValues();
                    registro.put("nombre", nombreTarea);
                    registro.put("descripcion", descripcionTarea);
                    registro.put("materia", nombreMateria);
                    registro.put("fecha", txtFecha);
                    registro.put("hora", txtHora);
                    //se crea registro capturado en BD
                    bd.insert("tarea", null, registro);
                    tarea.setText("");
                    materia.setText("");
                    fecha.setText("");
                    hora.setText("");
                    String contenido = "";
                    //se recorren todos los regitros de la tabla tarea y los guardo en contenido que es una cadena
                    Cursor fila = bd.rawQuery("select nombre, descripcion, materia, fecha, hora from tarea", null);
                    while (fila.moveToNext()) {
                        //fila.getString(0);
                        contenido = contenido + fila.getString(0) + "\n" + fila.getString(1) + "\n";
                    }
                    bd.close();

                    Toast.makeText(getApplicationContext(), R.string.tarea_guardada,
                            Toast.LENGTH_LONG).show();
                    populateList(query);
                }else {

                    AlertDialog.Builder errorMessage = new AlertDialog.Builder(dialog.getContext());
                    errorMessage.setMessage(error);
                    errorMessage.setNeutralButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dia, int which) {
                            dialog.show();
                            dia.dismiss();
                        }
                    });
                    errorMessage.show();
                }
                dialog.dismiss();

            }
        });
        if (llamarBaseDeDatos)
            llamarBaseDeDatos();

    }

    public void DeleteAllTask(MenuItem item){
        llamarBaseDeDatos = false;

        DbHelper admin = new DbHelper(this,"agendadb", null, 1);
        final SQLiteDatabase bd = admin.getWritableDatabase();

        AlertDialog.Builder deleteMessage = new AlertDialog.Builder(this);
        deleteMessage.setMessage(R.string.confirmDel);
        deleteMessage.setPositiveButton(R.string.btn_Yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    bd.delete("tarea", null, null);
                    Toast.makeText(getApplicationContext(), "La base de datos se vacio correctamente",
                            Toast.LENGTH_LONG).show();
                    bd.close();
                    dialog.dismiss();
                    query=0;
                    populateList(query);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error ",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        deleteMessage.setNegativeButton(R.string.btn_Cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        deleteMessage.show();

    }

    public void BuscarTarea(MenuItem item){
        llamarBaseDeDatos = false;
        Button btnCancelar;
        Button btnBuscar;

        final Dialog dialog = new Dialog(this);
        DbHelper admin = new DbHelper(this,"agendadb", null, 1);
        final SQLiteDatabase bd = admin.getWritableDatabase();

        //setting custom layout to dialog
        dialog.setContentView(R.layout.layout_pop_search);
        dialog.setTitle(R.string.buscar_tarea);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();

        final EditText nameTask = (EditText)dialog.findViewById(R.id.nameTarea);

        btnCancelar = (Button)dialog.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnBuscar = (Button)dialog.findViewById(R.id.btnSearch);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreTarea = nameTask.getText().toString();
                Cursor c = bd.rawQuery("SELECT materia FROM TAREA where tarea_id=?" ,new String [] {nombreTarea});
                if (c.moveToFirst()) {
                    Intent intent = new Intent(v.getContext(), detailActivity.class);
                    intent.putExtra(EXTRA_MESSAGE, nameTask.getText().toString());
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Esta vacia",
                            Toast.LENGTH_LONG).show();

                }



            }
        });


        if (llamarBaseDeDatos)
            llamarBaseDeDatos();
    }





    private void llamarBaseDeDatos() {

    }

}
