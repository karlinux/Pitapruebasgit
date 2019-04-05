package com.g214.pita;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Preguntapuntopita extends AppCompatActivity {

    private final Handler_sqlite inserta = new Handler_sqlite(this);
    private final Handler_sqliteU insertaU = new Handler_sqliteU(this);
    private final Links l = new Links();
    String fecha = l.getFecha();
    String li = l.getLink();
    String VERSION = l.getVersion();
    String APK = l.getApk();

    EditText etOtro, etMeses, etDias;
    TextView tvVersion, tvPregunta, tvEmpresa, tvNo;
    Button btnIniciar, btnRegresar;
    Spinner spPregunta, spPregunta2;
    Bundle bolsa;
    String n, pregunta, selecPregunta, selecPregunta2, pregunta2, usuario, id, empresa, imei, FOLIOENCUESTA, otro, FOLIORESPUESTA;
    int numpregunta;
    String [] IDPREGUNTAS, PREGUNTAS;
    //Intents
    String Foto, Pregunta, Multi, Inicio;
    Intent intent;
    int num;
    InputFilter[] FilterArray;
    LinearLayout.LayoutParams lp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        PREGUNTAS = getResources().getStringArray(R.array.preguntaspita);
        IDPREGUNTAS = getResources().getStringArray(R.array.numpregunta);

        Foto = ".Foto" + APK;
        Inicio = ".Inicio"+ APK;
        Pregunta = ".Preguntapunto" + APK;
        Multi = ".Multipunto" + APK;
        num =0;
        inserta.abrir();
        usuario = inserta.usuario();
        numpregunta = Integer.parseInt(inserta.NUMPREGUNTA());
        id = inserta.iden();
        FOLIOENCUESTA = inserta.FOLIOENCUESTA();
        empresa = inserta.empresa();
        fecha = inserta.fecha("encuesta", "FECHAENTREVISTA");
        //inserta.actualizaPregunta(id, "8");
        inserta.cerrar();
        //Toast.makeText(getApplicationContext(), Integer.toString(numpregunta), Toast.LENGTH_SHORT).show();
        numpregunta ++;

        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        imei = telephonyManager.getDeviceId();


        StringBuilder stringFolioRespuesta = new StringBuilder();

        stringFolioRespuesta.append(imei);
        stringFolioRespuesta.append(fecha.substring(0,4));
        stringFolioRespuesta.append(fecha.substring(5,7));
        stringFolioRespuesta.append(fecha.substring(8,10));
        stringFolioRespuesta.append(fecha.substring(11,13));
        stringFolioRespuesta.append(fecha.substring(14,16));
        stringFolioRespuesta.append(fecha.substring(17,19));
        stringFolioRespuesta.append(String.format("%02d",numpregunta));
        stringFolioRespuesta.append(id);

        FOLIORESPUESTA = stringFolioRespuesta.toString();

        //Toast.makeText(this, FOLIOENCUESTA, Toast.LENGTH_SHORT).show();

        tvVersion = (TextView) findViewById(R.id.tvVersion);
        tvPregunta = (TextView) findViewById(R.id.tvPregunta);
        tvEmpresa = (TextView) findViewById(R.id.tvEmpresa);
        tvNo = (TextView) findViewById(R.id.tvNo);

        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);

        spPregunta = (Spinner) findViewById(R.id.spPregunta);
        spPregunta2 = (Spinner) findViewById(R.id.spPregunta2);

        etOtro = (EditText) findViewById(R.id.etOtro);
        etMeses = (EditText) findViewById(R.id.etMeses);
        etDias = (EditText) findViewById(R.id.etDias);
        //etOtro.setVisibility(View.GONE); #65646A


        tvVersion.setText("I. IDENTIFICACIÓN DE LA EMPRESA Y \n   DATOS DE LA ENTREVISTA");
        //tvDialogo.setText(String.format(getString(R.string.dialogo), usuario));


        Typeface ligt =Typeface.createFromAsset(getAssets(), "fonts/montserratLight.otf");
        Typeface regular=Typeface.createFromAsset(getAssets(), "fonts/montserratRegular.otf");
        Typeface medio=Typeface.createFromAsset(getAssets(), "fonts/montserratMedium.otf");

        tvVersion.setTypeface(medio);
        tvPregunta.setTypeface(medio);
        tvEmpresa.setTypeface(medio);
        btnRegresar.setTypeface(medio);
        btnIniciar.setTypeface(medio);
        tvVersion.setTextColor(Color.parseColor("#A5783C"));
        tvVersion.setText("");
        tvNo.setTextColor(Color.parseColor("#65646A"));
        tvEmpresa.setText(empresa);
        spPregunta2.setVisibility(View.GONE);
        etOtro.setVisibility(View.GONE);
        etMeses.setVisibility(View.GONE);
        etDias.setVisibility(View.GONE);
        tvNo.setVisibility(View.GONE);

        switch (numpregunta){

            case 1:// 2.1.1
                tvVersion.setText(" II. Video vigilancia (sólo si tiene contrato PITA)");
                tvVersion.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                tvVersion.setTextColor(Color.parseColor("#79032e"));
                tvEmpresa.setVisibility(View.VISIBLE);
                tvEmpresa.setText("2.1. Video vigilancia");
                tvVersion.setVisibility(View.VISIBLE);

                lp = (LinearLayout.LayoutParams) tvPregunta.getLayoutParams();
                lp.setMargins(40,20,20,0);

            tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);
            etOtro.setHint("Otro");
            spPregunta.setAdapter(ArrayAdapter.createFromResource(this, R.array.p1_1, R.layout.spinner_item));
            spPregunta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int pos, long id) {
                    selecPregunta = String.valueOf(spPregunta.getSelectedItem());
                    String [] arrayPregunta = selecPregunta.split("-");

                    pregunta = arrayPregunta[0];

                    if(pregunta.equals("2")){

                        numpregunta = 22;

                    }else{

                        inserta.abrir();
                        numpregunta = Integer.parseInt(inserta.NUMPREGUNTA());
                        numpregunta++;
                        inserta.cerrar();

                    }

                    Toast.makeText(Preguntapuntopita.this, Integer.toString(numpregunta), Toast.LENGTH_SHORT).show();

                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            break;
            case 23:// 2.1.1
                tvVersion.setText(" III. Vehículos ligeros");
                tvVersion.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                tvVersion.setTextColor(Color.parseColor("#79032e"));
                tvEmpresa.setVisibility(View.VISIBLE);
                tvEmpresa.setText("3.1. Carriles de vehículos ligeros");
                tvVersion.setVisibility(View.VISIBLE);

                lp = (LinearLayout.LayoutParams) tvPregunta.getLayoutParams();
                lp.setMargins(40,20,20,0);

            tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);
            etOtro.setHint("Otro");
            spPregunta.setAdapter(ArrayAdapter.createFromResource(this, R.array.p1_1, R.layout.spinner_item));
            spPregunta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int pos, long id) {
                    selecPregunta = String.valueOf(spPregunta.getSelectedItem());
                    String [] arrayPregunta = selecPregunta.split("-");

                    pregunta = arrayPregunta[0];

                    if(pregunta.equals("2")){

                        numpregunta = 22;

                    }else{

                        inserta.abrir();
                        numpregunta = Integer.parseInt(inserta.NUMPREGUNTA());
                        numpregunta++;
                        inserta.cerrar();

                    }

                    //Toast.makeText(Preguntapuntopita.this, Integer.toString(numpregunta), Toast.LENGTH_SHORT).show();

                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            break;
            case 24:// 2.1.1
                tvEmpresa.setVisibility(View.VISIBLE);
                tvEmpresa.setText("3.1. Carriles de vehículos ligeros");
                tvVersion.setVisibility(View.VISIBLE);
                spPregunta.setVisibility(View.GONE);

                lp = (LinearLayout.LayoutParams) tvPregunta.getLayoutParams();
                lp.setMargins(40,20,20,0);

                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);

                lp = (LinearLayout.LayoutParams) etOtro.getLayoutParams();
                lp.setMargins(100,0,100,40);
                etOtro.setLayoutParams(lp);
                etOtro.setGravity(Gravity.CENTER_HORIZONTAL);
                etOtro.setVisibility(View.VISIBLE);
                etOtro.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                etOtro.setHint("Carriles");
            break;

                // DESFAULT MANDA A LA ACTIVITY DE FOTO
            default:
                intent = new Intent(Multi);
                startActivity(intent);
                finish();
            break;
        }

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Inicio);
                startActivity(intent);
                finish();
            }
        });

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                otro = etOtro.getText().toString();

                switch (numpregunta){
                    case 1:
                    case 23:
                        num = (pregunta.equals("0")? 0 : 1);
                    break;
                    case 24:
                        pregunta = otro;
                        otro = "";
                        num = (otro.equals("0")? 0 : 1);
                        break;
                }



                if(num!=0) {
                    inserta.abrir();
                    inserta.insertarPreg(FOLIOENCUESTA, IDPREGUNTAS[numpregunta], pregunta, pregunta2, otro, Integer.toString(numpregunta), FOLIORESPUESTA);
                    inserta.actualizaPregunta(id, String.valueOf(numpregunta));
                    inserta.cerrar();

                    if (numpregunta == 41) {
                        Intent intent = new Intent(Foto);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(Pregunta);
                        startActivity(intent);
                        finish();
                    }

                    Toast.makeText(getApplicationContext(), "DATOS GUARDADOS", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "INGRESE LOS DATOS", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

