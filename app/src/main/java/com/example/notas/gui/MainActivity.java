package com.example.notas.gui;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.notas.R;
import com.example.notas.model.RecordatorioModel;
import com.example.notas.persistencia.RecordatorioRepository;

import java.time.LocalDate;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static int persistencia=0;

    TextInputEditText descripcion;
    TextInputEditText fecha;
    TextInputLayout fechalayout;
    TextInputLayout horalayout;
    TextInputEditText hora;
    AppCompatButton guardar;
    RecordatorioRepository repositorio;



    public static String CHANNEL_ID= "XD";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();
        descripcion = findViewById(R.id.descripcion);
        fecha = findViewById(R.id.fecha);
        hora = findViewById(R.id.hora);
        fechalayout = findViewById(R.id.fechalayout);
        horalayout = findViewById(R.id.horalayout);
        guardar = findViewById(R.id.guardar);
        Calendar calendario = Calendar.getInstance();
        calendario.setTimeInMillis(System.currentTimeMillis());


        repositorio = new RecordatorioRepository(getBaseContext());





        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                StringBuilder strb = new StringBuilder();
                if(i2<10) strb.append('0');
                strb.append(i2);
                strb.append('/');
                if(i1<9) strb.append('0');
                strb.append(i1+1);
                strb.append('/');
                strb.append(i);
                fecha.setText(strb.toString());
                calendario.set(i,i1,i2,0,0,0);

            }
        },  LocalDate.now().getYear(), LocalDate.now().getMonthValue()-1, LocalDate.now().getDayOfMonth());

        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        timePicker.setHour(i);
                        timePicker.setMinute(i1);
                        hora.setTag(timePicker);
                        StringBuilder strb = new StringBuilder();
                        if(i<10) strb.append('0');
                        strb.append(i);
                        strb.append(':');
                        if(i1<10) strb.append('0');
                        strb.append(i1);
                        hora.setText(strb.toString());
                    }
                },
                0,0,true
                );


        hora.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    hora.clearFocus();
                    timePickerDialog.show();
                } else {
                    timePickerDialog.hide();

                }
            }
        });

        fecha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    fecha.clearFocus();
                    datePickerDialog.show();
                } else {
                    datePickerDialog.hide();

                }
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent destino = new Intent(MainActivity.this, RecordatorioReceiver.class);
                destino.setAction("com.example.tp3.NOTA");
                destino.putExtra("Texto", descripcion.getText().toString());
                PendingIntent pendingIntent =
                        PendingIntent.getBroadcast(MainActivity.this,0,destino,0);
                if(TextUtils.isEmpty(fecha.getText())){
                    Toast.makeText(MainActivity.this, "Seleccione una fecha", Toast.LENGTH_LONG).show();
                }
                else {
                    if(TextUtils.isEmpty(hora.getText())){
                        Toast.makeText(MainActivity.this, "Seleccione una hora" , Toast.LENGTH_LONG).show();
                    }
                    else{

                       // Toast.makeText(MainActivity.this, "Recordatorio creado con exito" , Toast.LENGTH_LONG).show();

                        long fechamillis = calendario.getTimeInMillis() + ((new Long(((TimePicker)hora.getTag()).getHour()) )*3600000 ) + ((new Long(((TimePicker)hora.getTag()).getMinute()))* 60000);
                        alarm.set(AlarmManager.RTC_WAKEUP, fechamillis , pendingIntent);

                        //alarm.set(AlarmManager.RTC_WAKEUP, (calendario.getTimeInMillis() + ((new Long(((TimePicker)hora.getTag()).getHour()) )*3600000 ) + ((new Long(((TimePicker)hora.getTag()).getMinute()))* 60000) ) , pendingIntent);



                        RecordatorioModel recordatorio = new RecordatorioModel(descripcion.getText().toString(),new Date(fechamillis));

                        Intent result = new Intent();
                        if(repositorio.guardarRecordatorio(recordatorio)){
                            setResult(Activity.RESULT_OK,result);
                            finish();
                        }
                        else {
                            setResult(Activity.RESULT_CANCELED,result);
                            finish();
                        }

                    }
                }


            }
        });
        
    }




    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notificaciones_notas";
            String description = "";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    };
}