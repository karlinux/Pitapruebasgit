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

public class Preguntapita extends AppCompatActivity {

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
        PREGUNTAS = getResources().getStringArray(R.array.preguntas);
        Foto = ".Foto" + APK;
        Inicio = ".Inicio"+ APK;
        Pregunta = ".Pregunta" + APK;
        Multi = ".Multi" + APK;
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

        IDPREGUNTAS = new String[]{"","1.1", "1.2", "1.3", "1.4", "1.5", "1.6", "1.7", "1.8", "1.9","1.10",
                "1.11", "1.12", "1.13", "2.1", "2.2", "2.3", "2.4", "2.5", "2.6", "2.7", "3.1", "3.2", "3.3",
                "3.4", "3.5", "3.6", "1", "2", "3", "4"};

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

            case 1:// 1.1
            case 2:// 1.2
            tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);
            etOtro.setHint("Otro");
            spPregunta.setAdapter(ArrayAdapter.createFromResource(this, R.array.p1_1, R.layout.spinner_item));
            spPregunta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int pos, long id) {
                    selecPregunta = String.valueOf(spPregunta.getSelectedItem());
                    String [] arrayPregunta = selecPregunta.split("-");

                    pregunta = arrayPregunta[0];

                    //Toast.makeText(Preguntapita.this, pregunta, Toast.LENGTH_SHORT).show();
                    if(pregunta.equals("2")){
                        if(numpregunta==2){
                            etOtro.setVisibility(View.VISIBLE);
                            etOtro.setText("Nombre");
                        }
                        numpregunta = 13;

                    }else{
                        inserta.abrir();
                        numpregunta = Integer.parseInt(inserta.NUMPREGUNTA());
                        numpregunta++;
                        inserta.cerrar();
                        etOtro.setVisibility(View.GONE);
                        etOtro.setText("");
                    }
                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            break;

            case 9:
                inserta.abrir();
                Cursor curNueve = inserta.nueve(FOLIOENCUESTA);
                curNueve.moveToLast();
                int numero = curNueve.getCount();
                if(numero>0) {
                    if (curNueve.getString(2).equals("1") && curNueve.getString(1).equals("9")) {
                        Intent intent = new Intent(".Bitacorapita");
                        startActivity(intent);
                        finish();
                    }
                }
                inserta.cerrar();
            case 3:
            case 4:
            case 6:
            case 10:
            case 11:
            case 14:

                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);
                etOtro.setHint("Otro");
                spPregunta2.setVisibility(View.GONE);

                spPregunta.setAdapter(ArrayAdapter.createFromResource(this, R.array.p1_1, R.layout.spinner_item));
                spPregunta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int pos, long id) {
                        selecPregunta = String.valueOf(spPregunta.getSelectedItem());
                        String [] arrayPregunta = selecPregunta.split("-");

                        pregunta = arrayPregunta[0];
                        if(pregunta.equals("2")) {
                            etOtro.setVisibility(View.VISIBLE);
                            etOtro.setHint("¿Por qué?");

                        }else {
                            etOtro.setVisibility(View.GONE);
                            etOtro.setText("");
                        }

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                break;
            case 12:
                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);
                etOtro.setHint("Otro");
                spPregunta2.setVisibility(View.GONE);

                spPregunta.setAdapter(ArrayAdapter.createFromResource(this, R.array.p1_1, R.layout.spinner_item));
                spPregunta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int pos, long id) {
                        selecPregunta = String.valueOf(spPregunta.getSelectedItem());
                        String [] arrayPregunta = selecPregunta.split("-");

                        pregunta = arrayPregunta[0];
                        if(pregunta.equals("2")) {
                            etOtro.setVisibility(View.VISIBLE);
                            etOtro.setHint("¿Por qué?");

                        }else if(pregunta.equals("1")){
                            etOtro.setVisibility(View.VISIBLE);
                            etOtro.setHint("¿Cual es?");
                        }else{
                            etOtro.setVisibility(View.GONE);
                        }

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;
            case 7:// 1.7
                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);

                spPregunta.setAdapter(ArrayAdapter.createFromResource(this, R.array.p1_1, R.layout.spinner_item));
                spPregunta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int pos, long id) {
                        selecPregunta = String.valueOf(spPregunta.getSelectedItem());
                        String [] arrayPregunta = selecPregunta.split("-");

                        pregunta = arrayPregunta[0];
                        if(pregunta.equals("1")) {
                            etOtro.setVisibility(View.VISIBLE);
                            etOtro.setHint("¿Por qué?");

                        }else {
                            etOtro.setVisibility(View.GONE);
                            etOtro.setText("");

                        }

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                break;
            case 18:// 2.18
            case 25: // 2.25
            case 30:// 2.

                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);

                spPregunta.setAdapter(ArrayAdapter.createFromResource(this, R.array.p2_7, R.layout.spinner_item));
                spPregunta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int pos, long id) {
                        selecPregunta = String.valueOf(spPregunta.getSelectedItem());
                        String [] arrayPregunta = selecPregunta.split("-");

                        pregunta = arrayPregunta[0];
                        if(pregunta.equals("1")) {
                            etOtro.setVisibility(View.VISIBLE);
                            etOtro.setHint("¿Por qué?");
                            etOtro.setText("");

                        }else if(pregunta.equals("2")){
                            etOtro.setVisibility(View.VISIBLE);
                            etOtro.setHint("¿Por qué?");
                            etOtro.setText("");

                        }else{
                            etOtro.setVisibility(View.GONE);
                            etOtro.setText("");
                        }

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                break;
            case 38://3.06
                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);
                spPregunta.setAdapter(ArrayAdapter.createFromResource(this, R.array.si_no, R.layout.spinner_item));
                spPregunta.setVisibility(View.GONE);
                etOtro.setVisibility(View.VISIBLE);
                etOtro.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                if(numpregunta == 38 ){
                    etOtro.setHint("TOTAL DE TRABAJADORES");
                }else {
                    etOtro.setHint("REGISTRE CANTIDADES CERRADAS A PESOS");
                    tvNo.setVisibility(View.VISIBLE);
                    tvNo.setText("888888. No sabe");
                }


                break;
            case 31:
            case 32:
            case 37://4.1
            case 39://4.3
            case 40://4.4
            case 41://4.5
                String contenido = "";
                String texto = "";
                if( numpregunta == 37 ){
                    contenido="Sector";
                }else if( numpregunta == 41 ){

                   etOtro.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                }
                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);
                spPregunta.setVisibility(View.GONE);
                etOtro.setVisibility(View.VISIBLE);
                etOtro.setHint("REGISTRE TEXTUAL");

                etOtro.setText(texto);
                etOtro.setHint(contenido);


                break;
            case 19:// 2.19
            case 20:// 2.20
                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);
                spPregunta.setAdapter(ArrayAdapter.createFromResource(this, R.array.p2_7, R.layout.spinner_item));
                spPregunta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int pos, long id) {

                        selecPregunta = String.valueOf(spPregunta.getSelectedItem());
                        String [] arrayPregunta = selecPregunta.split("-");

                        pregunta = arrayPregunta[0];
                        if(pregunta.equals("1")) {
                            etOtro.setVisibility(View.VISIBLE);
                            etOtro.setHint("¿Qué impacto ha tenido?");

                        }else {
                            etOtro.setVisibility(View.GONE);
                            etOtro.setText("");

                        }

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;
            case 21:// 2.21
                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);
                spPregunta.setAdapter(ArrayAdapter.createFromResource(this, R.array.p2_7, R.layout.spinner_item));
                spPregunta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int pos, long id) {

                        selecPregunta = String.valueOf(spPregunta.getSelectedItem());
                        String [] arrayPregunta = selecPregunta.split("-");

                        pregunta = arrayPregunta[0];
                        if(pregunta.equals("1")) {
                            etOtro.setVisibility(View.VISIBLE);
                            etOtro.setHint("¿Qué beneficio ha tenido?");

                        }else {
                            etOtro.setVisibility(View.GONE);
                            etOtro.setText("");

                        }

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;
            case 22:// 2.22
            case 29:// 2.29

                FilterArray = new InputFilter[1];
                FilterArray[0] = new InputFilter.LengthFilter(3);
                etOtro.setFilters(FilterArray);

                lp = (LinearLayout.LayoutParams) etOtro.getLayoutParams();
                lp.setMargins(100,10,100,0);
                etOtro.setLayoutParams(lp);

                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);
                spPregunta.setVisibility(View.GONE);
                etOtro.setVisibility(View.VISIBLE);
                etOtro.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                etOtro.setGravity(Gravity.CENTER_HORIZONTAL);
                //etOtro.setPadding(50,30,50,20);

                etOtro.setHint("Porcentaje %");
                tvNo.setVisibility(View.VISIBLE);
                tvNo.setText("888. No sabe");

                break;
            case 23:// 2.23
            case 24:// 2.24

                FilterArray = new InputFilter[1];
                FilterArray[0] = new InputFilter.LengthFilter(2);
                etOtro.setFilters(FilterArray);

                lp = (LinearLayout.LayoutParams) etOtro.getLayoutParams();
                lp.setMargins(130,0,130,0);
                etOtro.setLayoutParams(lp);

                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);
                spPregunta.setAdapter(ArrayAdapter.createFromResource(this, R.array.p2_7, R.layout.spinner_item));
                spPregunta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int pos, long id) {

                        selecPregunta = String.valueOf(spPregunta.getSelectedItem());
                        String [] arrayPregunta = selecPregunta.split("-");

                        pregunta = arrayPregunta[0];
                        if(pregunta.equals("1")) {
                            etOtro.setVisibility(View.VISIBLE);
                            etOtro.setHint("00");
                            etOtro.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                            etOtro.setGravity(Gravity.CENTER_HORIZONTAL);
                            tvNo.setVisibility(View.VISIBLE);
                            tvNo.setText("En una escala del 0 al 10 donde 0 es muy malo y 10 muy bueno cómo calificaría el seguimiento?");

                        }else {
                            etOtro.setVisibility(View.GONE);
                            tvNo.setVisibility(View.GONE);
                            etOtro.setText("");

                        }

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;

            case 26:// 2.26
                FilterArray = new InputFilter[1];
                FilterArray[0] = new InputFilter.LengthFilter(2);
                etOtro.setFilters(FilterArray);

                FilterArray = new InputFilter[1];
                FilterArray[0] = new InputFilter.LengthFilter(249);
                etMeses.setFilters(FilterArray);

                lp = (LinearLayout.LayoutParams) etOtro.getLayoutParams();
                lp.setMargins(130,0,130,0);
                etOtro.setLayoutParams(lp);

                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);
                spPregunta.setVisibility(View.GONE);
                etOtro.setVisibility(View.VISIBLE);
                etMeses.setVisibility(View.VISIBLE);
                tvNo.setVisibility(View.VISIBLE);
                tvNo.setText("¿Por qué da esta calificación a la satisfacción con los beneficios que ha obtenido?");
                etOtro.setHint("00");
                etOtro.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                etOtro.setGravity(Gravity.CENTER_HORIZONTAL);
                etMeses.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                etMeses.setHint("¿Por qué?");

                break;
            case 28:// 2.28
                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);
                spPregunta.setAdapter(ArrayAdapter.createFromResource(this, R.array.si_no, R.layout.spinner_item));
                spPregunta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int pos, long id) {
                        selecPregunta = String.valueOf(spPregunta.getSelectedItem());
                        String [] arrayPregunta = selecPregunta.split("-");

                        pregunta = arrayPregunta[0];
                        if(pregunta.equals("5")) {
                            etOtro.setVisibility(View.VISIBLE);
                            etOtro.setHint("¿En qué porcentajes?");

                        }else {
                            etOtro.setVisibility(View.GONE);
                            etOtro.setText("");

                        }

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;

            // MULTIPREGUNTAS
            case 5:// 2.5
            case 8: // 2.8
            case 13:// 2.13
            case 15://3.01
            case 16:
            case 17:
            case 34://3.02
            case 35://3.03
            case 36://3.04
                intent = new Intent(Multi);
                startActivity(intent);
                finish();

                break;


                // DESFAULT MANDA A LA ACTIVITY DE FOTO
            default:
                //Toast.makeText(this, Integer.toString(numpregunta), Toast.LENGTH_SHORT).show();
                intent = new Intent(Foto);
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
                    case 5:
                    case 14:
                        if(!pregunta.equals("0")) {
                            num = 1;
                        }
                    break;
                    case 2:
                    case 3:
                    case 4:
                    case 6:
                    case 7:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 15:
                    case 23:
                    case 24:
                    case 25:
                    case 27:
                    case 28:
                    case 30:
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                        pregunta2 = etOtro.getText().toString();
                        num = (pregunta.equals("")? 0 : 1);
                        otro = "";
                        break;
                    case 22:
                    case 29:
                    case 31:
                    case 32:
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                        pregunta = etOtro.getText().toString();
                        num = (pregunta.equals("")? 0 : 1);
                        otro="";
                        break;
                    case 17:
                        pregunta = etMeses.getText().toString();
                        num = (pregunta.equals("")? 0 : 1);
                        otro = "";
                        break;
                    case 26:
                        pregunta = etOtro.getText().toString();
                        pregunta2 = etMeses.getText().toString();
                        otro = "";
                        break;

                }

                num = (pregunta.equals("") || pregunta.equals("0")? 0 : 1);

                if(num!=0) {
                    inserta.abrir();
                    inserta.insertarPreg(FOLIOENCUESTA, IDPREGUNTAS[numpregunta], pregunta, pregunta2, otro, Integer.toString(numpregunta), FOLIORESPUESTA,"");
                    if(numpregunta==9 && pregunta.equals("1")) {
                        //Toast.makeText(Preguntapita.this, "ENTRA", Toast.LENGTH_SHORT).show();
                    }else{
                        inserta.actualizaPregunta(id, Integer.toString(numpregunta), "UNO", "0");

                    }
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

