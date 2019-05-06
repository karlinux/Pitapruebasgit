package com.g214.pita;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Bitacoratextopita extends AppCompatActivity {

    private final Handler_sqlite inserta = new Handler_sqlite(this);
    private final Handler_sqliteU insertaU = new Handler_sqliteU(this);
    private final Links l = new Links();
    String fecha = l.getFecha();
    String li = l.getLink();
    String VERSION = l.getVersion();
    String APK = l.getApk();
    Calendar calendario = Calendar.getInstance();

    String  id, jefe, imei, FOLIORESPUESTA, FOLIOENCUESTA, n, r, IDBITACORA;
    int sinc, seg;
    Button btnSalir;
    Bundle cuices;
    String[] arrayFecha;
    Bundle bolsa;
    EditText etCaso, etFecha;
    Button btnRegresar, btnIniciar;
    private Adaptador adaptador;
    private ListView list;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitacora);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        inserta.abrir();
        FOLIOENCUESTA = inserta.FOLIOENCUESTA();
        id = inserta.iden();
        Cursor curNueve = inserta.nueve(FOLIOENCUESTA);
        curNueve.moveToLast();
        FOLIORESPUESTA = curNueve.getString(3);

        inserta.cerrar();

        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        imei = telephonyManager.getDeviceId();

        Date fechaActual = new Date();
        SimpleDateFormat a = new SimpleDateFormat("yyyy");
        SimpleDateFormat m = new SimpleDateFormat("MM");
        SimpleDateFormat d = new SimpleDateFormat("d");
        SimpleDateFormat h = new SimpleDateFormat("H");
        SimpleDateFormat mi = new SimpleDateFormat("m");
        SimpleDateFormat s = new SimpleDateFormat("s");

        String mes = m.format(fechaActual);
        String ano = a.format(fechaActual);
        String dia = d.format(fechaActual);
        String hora = h.format(fechaActual);
        String min = mi.format(fechaActual);
        String seg = s.format(fechaActual);


        StringBuilder stringFolioRespuesta = new StringBuilder();

        stringFolioRespuesta.append(imei);
        stringFolioRespuesta.append(ano);
        stringFolioRespuesta.append(mes);
        stringFolioRespuesta.append(dia);
        stringFolioRespuesta.append(hora);
        stringFolioRespuesta.append(min);
        stringFolioRespuesta.append(seg);

        IDBITACORA = stringFolioRespuesta.toString();

        //Toast.makeText(this, FOLIORESPUESTA, Toast.LENGTH_SHORT).show();
        Typeface ligt =Typeface.createFromAsset(getAssets(), "fonts/montserratLight.otf");
        Typeface regular=Typeface.createFromAsset(getAssets(), "fonts/montserratRegular.otf");
        Typeface medio=Typeface.createFromAsset(getAssets(), "fonts/montserratMedium.otf");

        btnIniciar = (Button)findViewById(R.id.btnIniciar);
        btnRegresar = (Button)findViewById(R.id.btnRegresar);
        etCaso = (EditText)findViewById(R.id.etCaso);
        etFecha = (EditText)findViewById(R.id.etFecha);

        list = (ListView) findViewById(R.id.lvLista);
        adaptador = new Adaptador(this, GetArrayItems());
        btnIniciar.setText("AGREGAR");
        btnRegresar.setText("FINALIZAR");
        list.setAdapter(adaptador);


        list.setClickable(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //Intent intent = new Intent(getApplicationContext(), Detallepita.class);
                //startActivity(intent);
                //finish();

                //Log.d("Posicion", String.valueOf(GetArrayItems().get(position).getTitulo()));


            }
        });
        etFecha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    new DatePickerDialog(Bitacoratextopita.this, date, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH),
                            calendario.get(Calendar.DAY_OF_MONTH)).show();
                    etCaso.requestFocus();
                }
            }
        });

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String caso = etCaso.getText().toString();
                String fech = etFecha.getText().toString();

                if(!caso.equals("") && !fech.equals("") ) {
                    inserta.abrir();
                    inserta.insertarBitacora(fech, caso, FOLIORESPUESTA, IDBITACORA);
                    inserta.cerrar();
                    //Toast.makeText(Bitacoratextopita.this, FOLIORESPUESTA + "|" + IDBITACORA, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(".Bitacoratextopita");
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(Bitacoratextopita.this, "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserta.abrir();
                inserta.actualizaPregunta(id, "9", "uno", "0");
                inserta.cerrar();
                Intent intent = new Intent(".Preguntapita");
                startActivity(intent);
                finish();
            }
        });


    }

    private ArrayList<Entidad> GetArrayItems(){
        ArrayList<Entidad> listItems = new ArrayList<>();
        inserta.abrir();
        Cursor cur= inserta.bitacora(FOLIORESPUESTA);
        while (cur.moveToNext()) {
            listItems.add(new Entidad(cur.getString(0),cur.getString(0), "FECHA: "+cur.getString(1) + "\n" +cur.getString(2)));//id
        }
        cur.close();
        inserta.close();
        return listItems;
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            calendario.set(Calendar.YEAR, year);
            calendario.set(Calendar.MONTH, monthOfYear);
            calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            actualizarInput();
        }

    };
    private void actualizarInput() {
        String formatoDeFecha = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(formatoDeFecha, Locale.US);

        etFecha.setText(sdf.format(calendario.getTime()));
    }
}

