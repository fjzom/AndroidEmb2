package com.mycompany.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class detailActivity extends Activity implements OnClickListener {


    public EditText searchTarea,searchMateria,searchDescripcion;
    public TextView searchTime,searchDate;
    private DbHelper dbHelper;
    private SQLiteDatabase database;
    private Button deleteBtn, updateBtn;
    private long _id;
    private String _idS;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        // Get the message from the intent
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);


        final DbHelper dbh = new DbHelper(this);
        final SQLiteDatabase db = dbh.getWritableDatabase();
        searchTarea = (EditText) findViewById(R.id.searchTarea);
        searchMateria = (EditText) findViewById(R.id.searchMateria);
        searchTime = (TextView) findViewById(R.id.txtTimePicker);
        searchDate = (TextView) findViewById(R.id.txtDatePicker);
        searchDescripcion = (EditText) findViewById(R.id.searchDescripcion);

        String[] campos = new String[]{"tarea_id", "nombre", "descripcion", "materia", "hora", "fecha"};
        String[] args = new String[]{message};

        Cursor c = db.query("TAREA", campos, "tarea_id=?", args, null, null, null);


        if (c.moveToFirst()) {
            String tareaIdTaskSearch = c.getString(0);
            String nombreTaskSearch = c.getString(1);
            String descripcionTaskSearch = c.getString(2);
            String materiaTaskSearch = c.getString(3);
            String horaTaskSearch = c.getString(4);
            String dateTaskSearch = c.getString(5);


            searchTarea.setText(nombreTaskSearch);
            searchMateria.setText(materiaTaskSearch);
            searchDate.setText(dateTaskSearch);
            searchTime.setText(horaTaskSearch);
            searchDescripcion.setText(descripcionTaskSearch);

            _idS = tareaIdTaskSearch;
            _id = Long.parseLong(tareaIdTaskSearch);
        } else {

            Toast.makeText(getApplicationContext(),
                    "Ha ocurrido un error :(", Toast.LENGTH_LONG).show();
            db.close();
        }
        updateBtn = (Button)findViewById(R.id.update_btn);
        deleteBtn = (Button)findViewById(R.id.delete_btn);
        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);

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

public void addRecod(View view){
    Calendar cal = Calendar.getInstance();
    String nombreTarea = searchTarea.getText().toString();
    String descTarea = searchDescripcion.getText().toString();

    Intent intent = new Intent(Intent.ACTION_EDIT);
    intent.setType("vnd.android.cursor.item/event");
    intent.putExtra("beginTime", cal.getTimeInMillis());
    intent.putExtra("allDay", true);
    intent.putExtra("rrule", "FREQ=DAILY");
    intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
    intent.putExtra("title", nombreTarea);
    intent.putExtra("description",descTarea);
    Intent inten = new Intent();
    inten.setClass(detailActivity.this,MainActivity.class);
    startActivity(inten);

    startActivity(intent);
    finish();
}

    @Override
    public void onClick(View v) {

        DbHelper admin = new DbHelper(this,"agendadb", null, 1);
        final SQLiteDatabase bd = admin.getWritableDatabase();


        switch(v.getId()){
            case R.id.delete_btn:
                AlertDialog.Builder deleteMessage = new AlertDialog.Builder(this);
                deleteMessage.setMessage(R.string.confirmDelReg);
                deleteMessage.setPositiveButton(R.string.btn_Yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            bd.delete("tarea", "tarea_id" + "=" + _id, null);
                            Toast.makeText(getApplicationContext(), "Tarea borrada",
                                    Toast.LENGTH_LONG).show();
                            bd.close();
                            dialog.dismiss();
                            returnHome();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error ",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
                deleteMessage.setNegativeButton(R.string.btn_No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                deleteMessage.show();


                break;
            case R.id.update_btn:
                AlertDialog.Builder  updateMessage = new AlertDialog.Builder(this);
                updateMessage.setMessage(R.string.confirmUpdateReg);
                updateMessage.setPositiveButton(R.string.btn_Yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            ContentValues cvUpdates = new ContentValues();
                            String upsearchTarea = searchTarea.getText().toString();
                            String upsearchMateria = searchMateria.getText().toString();
                            String upsearchTime = searchTime.getText().toString();
                            String upsearchDate = searchDate.getText().toString();
                            String upsearchDescripcion = searchDescripcion.getText().toString();

                            cvUpdates.put("nombre", upsearchTarea);
                            cvUpdates.put("materia", upsearchMateria);
                            cvUpdates.put("hora", upsearchTime);
                            cvUpdates.put("fecha", upsearchDate);
                            cvUpdates.put("descripcion", upsearchDescripcion);
                            bd.update("tarea", cvUpdates, "tarea_id" + "=" + _id, null);
                            Toast.makeText(getApplicationContext(), "Tarea actualziada",
                                    Toast.LENGTH_LONG).show();
                            bd.close();
                            dialog.dismiss();
                            returnHome();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error ",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
                updateMessage.setNegativeButton(R.string.btn_No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                updateMessage.show();



                break;

            default:
                break;
        }


    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(),
               MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }

}
