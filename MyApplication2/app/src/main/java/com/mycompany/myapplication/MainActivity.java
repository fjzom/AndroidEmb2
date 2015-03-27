package com.mycompany.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity {
    public String nombreTarea, nombreMateria, txtFecha, txtHora, descripcionTarea;
    public Boolean llamarBaseDeDatos;


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
                DatePickerDialog dateDialog = new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.DAY_OF_MONTH),
                        myCalendar.get(Calendar.MONTH));
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
                nombreTarea = tarea.getText().toString();
                nombreMateria = materia.getText().toString();
                txtFecha = fecha.getText().toString();
                txtHora = hora.getText().toString();
                descripcionTarea = descripcion.getText().toString();
                llamarBaseDeDatos = true;
                Toast.makeText(getApplicationContext(), R.string.tarea_guardada,
                        Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        if (llamarBaseDeDatos)
            llamarBaseDeDatos();
    }

    private void llamarBaseDeDatos() {

    }

}
