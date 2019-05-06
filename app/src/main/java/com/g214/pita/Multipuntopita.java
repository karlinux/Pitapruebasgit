package com.g214.pita;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Multipuntopita extends AppCompatActivity {

    private final Handler_sqlite inserta = new Handler_sqlite(this);
    private final Handler_sqlite_puntos insertaU = new Handler_sqlite_puntos(this);
    private final Links l = new Links();
    String fecha = l.getFecha();
    String li = l.getLink();
    String VERSION = l.getVersion();
    String APK = l.getApk();

    EditText etOtro, etDos, etTres, etCuatro, etCuantos;
    TextView tvVersion, tvPregunta, tvEmpresa;
    Button btnGuardar, btnRegresar, btnFinalizar, btnTerminar, btnRevisar;
    Spinner spPreguntas, spPreguntas2;
    String n, pregunta, pregunta1, chk1, chk2, chk3, chk4, chk5, chk6, chk7, chk8, chk9, NUM_CARRIL_LIGERO, limite, fotoimagen, tipo, nombrecarril, complemento,
            chk10, pregunta2, usuario, id, empresa, imei, FOLIOENCUESTA, dm, otro, FOLIORESPUESTA, error, carril, imagen, maximo, TIPOCARRIL, preguntabase;
    //Intents
    String Foto, Regresar, Pregunta, seccion;
    int numpregunta, checkboxs;
    String [] IDPREGUNTAS, PREGUNTAS;
    ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> adapter2;
    RadioGroup rbOp;
    RadioButton rbOp1, rbOp2, rbOp3;
    int num, numCheck, conteo, cuestionario, conteocarriles;
    Bundle bolsa;
    List<Item> items;
    ListView listView;
    ItemsListAdapter myItemsListAdapter;
    TypedArray arrayText;
    LinearLayout.LayoutParams lp;
    ArrayList<String> nombresCarriles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        PREGUNTAS = getResources().getStringArray(R.array.preguntaspita);
        IDPREGUNTAS = getResources().getStringArray(R.array.numpregunta);
        otro = "";
        error = "";
        complemento = "";
        fotoimagen = "0";
        imagen = "0";
        tipo = "0";
        id = "";
        pregunta1 = "";
        pregunta2 = "";
        Regresar = ".Inicio" + APK;
        Foto = ".Foto" + APK;
        Pregunta = ".Preguntapunto" + APK;
        num = 0;
        chk1 = "0";
        chk2 = "0";
        chk3 = "0";
        chk4 = "0";
        chk5 = "0";
        chk6 = "0";
        chk7 = "0";
        chk8 = "0";
        chk9 = "0";
        chk10 = "0";

        rbOp = (RadioGroup) findViewById(R.id.rgOp);
        rbOp1 = (RadioButton) rbOp.getChildAt(0);
        rbOp2 = (RadioButton) rbOp.getChildAt(1);
        rbOp3 = (RadioButton) rbOp.getChildAt(2);

        rbOp.setVisibility(View.GONE);
        listView = (ListView)findViewById(R.id.listview);

        inserta.abrir();
        Cursor curPunto = inserta.punto();
        curPunto.moveToLast();
        FOLIOENCUESTA = curPunto.getString(0);
        inserta.cerrar();

        fecha = inserta.fecha("encuesta", "FECHAENTREVISTA", FOLIOENCUESTA);
        bolsa = getIntent().getExtras();
        cuestionario = Integer.parseInt(bolsa.getString("numpregunta"));
        fotoimagen = inserta.imagen(FOLIOENCUESTA);
        inserta.abrir();

        if(fotoimagen.equals("1")){

            Intent intent = new Intent(getApplicationContext(), Fotopita.class);
            intent.putExtras(bolsa);
            startActivity(intent);
            finish();

        }
        switch (cuestionario){ //// Primer switch Seccion de Preguntas

            case 0:
                seccion = "UNO";
                numpregunta = Integer.parseInt(inserta.NUMPREGUNTA(FOLIOENCUESTA, seccion));
                if(numpregunta>=4){
                    Intent intent = new Intent(".Menupita");
                    startActivity(intent);
                    finish();
                    Toast.makeText(this, "YA FUE CAPTURADA LA PRIMERA VISTA", Toast.LENGTH_SHORT).show();
                }
                break;

            case 4:
                seccion = "DOS";
                numpregunta = Integer.parseInt(inserta.NUMPREGUNTA(FOLIOENCUESTA, seccion));
                limite ="27";
                if(numpregunta>=27){
                    Intent intent = new Intent(".Menupita");
                    startActivity(intent);
                    finish();
                    Toast.makeText(this, "YA FUE CAPTURADA LA PRIMERA VISTA", Toast.LENGTH_SHORT).show();
                }
                break;
            case 27:
                seccion = "TRES";
                numpregunta = Integer.parseInt(inserta.NUMPREGUNTA(FOLIOENCUESTA, seccion));
                limite ="95";
                if(numpregunta==102){
                    Intent intent = new Intent(".Menupita");
                    startActivity(intent);
                    finish();
                    Toast.makeText(this, "YA FUE CAPTURADA LA PRIMERA VISTA", Toast.LENGTH_SHORT).show();
                }
                break;
            case 102:
                seccion = "CUATRO";
                numpregunta = Integer.parseInt(inserta.NUMPREGUNTA(FOLIOENCUESTA, seccion));
                if(numpregunta==110){
                    Intent intent = new Intent(".Menupita");
                    startActivity(intent);
                    finish();
                    Toast.makeText(this, "YA FUE CAPTURADA LA PRIMERA VISTA", Toast.LENGTH_SHORT).show();
                }
                break;

        }
        inserta.cerrar();
//////////////////////////////////////////////////////////////////////////////////////////////////////
        numpregunta++;

        inserta.abrir();
        id = inserta.idenRespuestas(FOLIOENCUESTA);
        inserta.cerrar();

        int suma = 0;
        suma = Integer.parseInt(id)+1;
        id = String.valueOf(suma);


        initItems(numpregunta);
        myItemsListAdapter = new ItemsListAdapter(this, items);

        Toast.makeText(this, Integer.toString(numpregunta), Toast.LENGTH_SHORT).show();
        listView.setAdapter(myItemsListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }});
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
        stringFolioRespuesta.append(String.format("%03d",numpregunta));

        FOLIORESPUESTA = stringFolioRespuesta.toString();

        spPreguntas = (Spinner) findViewById(R.id.spPreguntas);
        spPreguntas2 = (Spinner) findViewById(R.id.spPreguntas2);

        tvVersion = (TextView) findViewById(R.id.tvVersion);
        tvPregunta = (TextView) findViewById(R.id.tvPregunta);
        tvEmpresa = (TextView) findViewById(R.id.tvEmpresa);

        btnGuardar = (Button) findViewById(R.id.btnIniciar);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        btnFinalizar = (Button) findViewById(R.id.btnFinalizar);
        btnTerminar = (Button) findViewById(R.id.btnTerminar);
        btnRevisar = (Button) findViewById(R.id.btnRevisar);
        btnRevisar.setVisibility(View.GONE);
        etCuantos = (EditText) findViewById(R.id.etCuantos);
        etOtro = (EditText) findViewById(R.id.etOtro);
        etDos = (EditText) findViewById(R.id.etDos);
        etTres = (EditText) findViewById(R.id.etTres);
        etCuatro = (EditText) findViewById(R.id.etCuatro);
        //etOtro.setVisibility(View.GONE); #65646A
        etDos.setVisibility(View.GONE);
        etTres.setVisibility(View.GONE);
        etCuatro.setVisibility(View.GONE);
        btnFinalizar.setVisibility(View.GONE);
        btnTerminar.setVisibility(View.GONE);


        tvVersion.setText("I. IDENTIFICACIÓN DE LA EMPRESA Y \n   DATOS DE LA ENTREVISTA");
        //tvDialogo.setText(String.format(getString(R.string.dialogo), usuario));
        etCuantos.setVisibility(View.GONE);
        spPreguntas2.setVisibility(View.GONE);

        Typeface ligt =Typeface.createFromAsset(getAssets(), "fonts/montserratLight.otf");
        Typeface regular=Typeface.createFromAsset(getAssets(), "fonts/montserratRegular.otf");
        Typeface medio=Typeface.createFromAsset(getAssets(), "fonts/montserratMedium.otf");


        tvVersion.setTextColor(Color.parseColor("#A5783C"));
        tvVersion.setText("II. CONOCIMIENTO Y EXPERIENCIA CON OEA");
        tvEmpresa.setText(empresa);
        etOtro.setVisibility(View.GONE);

        if(numpregunta>109){
            tvVersion.setText("VI. Otros");
            tvEmpresa.setText("6.3. Binomios");
        }else if(numpregunta>105){
            tvVersion.setText("VI. Otros");
            tvEmpresa.setText("6.2. Rayos gamma");
        }else if(numpregunta>103){
            tvVersion.setText("VI. Otros");
            tvEmpresa.setText("6.1. Rayos X");
        }else  if(numpregunta>102){
            tvVersion.setText("V. Infraestructura auxiliar");
            tvEmpresa.setText("5.1. Planta de emergencia");
        }else if(numpregunta>90){
            tvVersion.setText("IV. Vehículos de carga");
            tvEmpresa.setText("4.14. Centro de monitoreo");
        }else if(numpregunta>79){
            tvVersion.setText("IV. Vehículos de carga");
            tvEmpresa.setText("4.13. Carriles Administrados");
        }else if(numpregunta>79){
            tvVersion.setText("IV. Vehículos de carga");
            tvEmpresa.setText("4.12. Carriles Administrados");
        }else if(numpregunta>75){
            tvVersion.setText("IV. Vehículos de carga");
            tvEmpresa.setText("4.11. Espacios de estacionamiento para revisión en zona de carga");
        }else if(numpregunta>71){
            tvVersion.setText("IV. Vehículos de carga");
            tvEmpresa.setText("4.10. Zona de rojos de carga"+complemento);
        }else if(numpregunta>70){
            tvVersion.setText("IV. Vehículos de carga");
            if(numpregunta==71){
                complemento="\nEs la zona qué considera \"X\" número de espacios físicos para los tractocamiones de carga que pasan a revisión física de la mercancia.";
            }
            tvEmpresa.setText("4.9. Zona de rojos de carga"+complemento);
        }else if(numpregunta>65){
            tvVersion.setText("IV. Vehículos de carga");
            tvEmpresa.setText("4.8. Zona de amarillos de carga");
        }else if(numpregunta>64){
            tvVersion.setText("IV. Vehículos de carga");
            tvEmpresa.setText("4.7. Zona de amarillos de carga");
        }else if(numpregunta>61){
            tvVersion.setText("IV. Vehículos de carga");
            tvEmpresa.setText("4.6. Equipos móviles de carga");
        }else if(numpregunta>59){
            tvVersion.setText("IV. Vehículos de carga");
            tvEmpresa.setText("4.6. Equipos móviles en zona de inspección de Carga"+complemento);
        }else if(numpregunta>58){
            tvVersion.setText("IV. Vehículos de carga");
                if(numpregunta==59){
                    complemento ="\nEn zona o zonas de carga es el total de los qué hay en el punto táctico";
                }
            tvEmpresa.setText("4.5. Equipos móviles en zona de inspección de Carga"+complemento);
        }else if(numpregunta>48){
            tvVersion.setText("IV. Vehículos de carga");
            tvEmpresa.setText("4.4. Posiciones de control de carga peatonal");
        }else if(numpregunta>47){
            tvVersion.setText("IV. Vehículos de carga");
            tvEmpresa.setText("4.3. Posiciones de control de carga peatonal");
        }else if(numpregunta>28){
            tvVersion.setText("IV. Vehículos de carga");
            tvEmpresa.setText("4.2. Carriles de vehículos de carga");
        }else if(numpregunta>27){
            tvVersion.setText("IV. Vehículos de carga");
            tvEmpresa.setText("4.1. Carriles de vehículos de carga");
        }else if(numpregunta>22){
            tvVersion.setText(" II. Vehículos ligeros");
            tvEmpresa.setText("3.6. Equipos móviles");
        }else if(numpregunta>21){
            tvVersion.setText(" II. Vehículos ligeros");
            tvEmpresa.setText("3.5. Equipos móviles en zona de inspección de carriles ligeros");
        }else if(numpregunta>17){
            tvVersion.setText(" II. Vehículos ligeros");
            tvEmpresa.setText("3.4. Zona de revisión de ligeros");
        }else if(numpregunta>16){
            tvVersion.setText(" II. Vehículos ligeros");
            tvEmpresa.setText("3.3. Zona de revisión de ligeros");
        }else if(numpregunta>5){
            tvVersion.setText(" II. Vehículos ligeros");
            tvEmpresa.setText("3.2. Carriles de vehículos ligeros");
        }else if(numpregunta>4){
            tvVersion.setText(" II. Vehículos ligeros");
            tvEmpresa.setText("3.1. Carriles de vehículos ligeros");
        }else if(numpregunta>2){
            tvVersion.setText(" II. Video vigilancia");
            tvEmpresa.setText("2.2. Cámaras Térmicas");
        }else{
            tvVersion.setText(" II. Video vigilancia");
            tvEmpresa.setText("2.1. Video vigilancia");
        }
        tvVersion.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        tvVersion.setTextColor(Color.parseColor("#79032e"));
        //////////////////////////   Segundo switch tipo de pregunta
        switch (numpregunta){

            case 1:     //2.1.1
            case 3:     //2.2.1
            case 5:     //3.1.1.
            case 17:    //3.3.1.
            case 22:    //3.5.1.
            case 28:    //4.1.1.
            case 48:    //4.3.1.
            case 59:    //4.5.1.
            case 65:    //4.6.1.
            case 71:    //4.8.1.
            case 76:    //4.9.3.
            case 80:    //4.10.2.
            case 91:    //4.12.2.
            case 92:    //4.12.2.
            case 94:    //4.12.4.   FOTO
            case 96:    //4.12.6.   FOTO
            case 104:    //6.1.1.
            case 107:   //6.2.1.

                tvVersion.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                tvVersion.setTextColor(Color.parseColor("#79032e"));

                tvEmpresa.setVisibility(View.VISIBLE);
                tvVersion.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);

                if(numpregunta == 91 || numpregunta == 94 || numpregunta == 96){
                    btnFinalizar.setVisibility(View.VISIBLE);
                    btnFinalizar.setText("IMAGEN");
                    tipo = "11";
                }

                lp = (LinearLayout.LayoutParams) tvPregunta.getLayoutParams();
                lp.setMargins(40,20,20,0);

                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);
                etOtro.setHint("Otro");
                spPreguntas.setAdapter(ArrayAdapter.createFromResource(this, R.array.p1_1, R.layout.spinner_item));
                spPreguntas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int pos, long id) {

                        String [] arrayPreguntas = String.valueOf(spPreguntas.getSelectedItem()).split("-");

                        pregunta = arrayPreguntas[0];

                        if(numpregunta == 91 && pregunta.equals("2")){
                            btnFinalizar.setVisibility(View.GONE);
                            btnFinalizar.setText("IMAGEN");
                        }else if(numpregunta == 91 && pregunta.equals("1")){
                            btnFinalizar.setVisibility(View.VISIBLE);
                            btnFinalizar.setText("IMAGEN");
                        }

                        if(numpregunta == 94 && pregunta.equals("2")){
                            btnFinalizar.setVisibility(View.GONE);
                            btnFinalizar.setText("IMAGEN");
                        }else if(numpregunta == 94 && pregunta.equals("1")){
                            btnFinalizar.setVisibility(View.VISIBLE);
                            btnFinalizar.setText("IMAGEN");
                        }
                        if(numpregunta == 96 && pregunta.equals("2")){
                            btnFinalizar.setVisibility(View.GONE);
                            btnFinalizar.setText("IMAGEN");
                        }else if(numpregunta == 96 && pregunta.equals("1")){
                            btnFinalizar.setVisibility(View.VISIBLE);
                            btnFinalizar.setText("IMAGEN");
                        }

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                btnGuardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pregunta1 = pregunta;
                        pregunta2 = "";
                        otro = "";
                        num = (pregunta1.equals("0") ? 0 : 1);
                        error = "SELECCIONE UNA OPCIÓN";

                        if(num!=0) {

                            inserta.abrir();
                            inserta.insertarPreg(FOLIOENCUESTA, IDPREGUNTAS[numpregunta], pregunta1, pregunta2, otro, Integer.toString(numpregunta), FOLIORESPUESTA, id);
                            inserta.cerrar();

                            switch (numpregunta){
                                case 1:
                                    if(pregunta.equals("2")){
                                        numpregunta = 2;
                                    }
                                    break;
                                case 3:
                                    if(pregunta.equals("2")){
                                        numpregunta = 4;
                                    }
                                    break;
                                case 5:
                                    if(pregunta.equals("2")){
                                        numpregunta = 27;
                                    }
                                    break;
                                case 17:
                                    if(pregunta.equals("2")){
                                        numpregunta = 21;
                                    }
                                    break;
                                case 22:
                                    if(pregunta.equals("2")){
                                        numpregunta = 27;
                                    }
                                    break;
                                case 28:
                                    if(pregunta.equals("2")){
                                        numpregunta = 47;
                                    }
                                    break;
                                case 48:
                                    if(pregunta.equals("2")){
                                        numpregunta = 58;
                                    }
                                    break;
                                case 59:
                                    if(pregunta.equals("2")){
                                        numpregunta = 64;
                                    }
                                    break;
                                case 65:
                                    if(pregunta.equals("2")){
                                        numpregunta = 70;
                                    }
                                    break;
                                case 71:
                                    if(pregunta.equals("2")){
                                        numpregunta = 75;
                                    }
                                    break;
                                case 76:
                                    if(pregunta.equals("2")){
                                        numpregunta = 79;
                                    }
                                    break;
                                case 80:
                                    if(pregunta.equals("2")){
                                        numpregunta = 90;
                                    }
                                    break;
                                case 91:
                                    if(pregunta.equals("2")){
                                        numpregunta = 102;
                                    }
                                    break;
                                case 92:
                                    if(pregunta.equals("2")){
                                        numpregunta = 93;
                                    }
                                    break;
                                case 94:
                                    if(pregunta.equals("2")){
                                        numpregunta = 95;
                                    }
                                    break;
                                case 96:
                                    if(pregunta.equals("2")){
                                        numpregunta = 98;
                                    }
                                    break;
                                case 104:
                                    if(pregunta.equals("2")){
                                        numpregunta = 106;
                                    }
                                    break;
                                case 107:
                                    if(pregunta.equals("2")){
                                        numpregunta = 109;
                                    }
                                    break;
                            }
                            inserta.abrir();
                            inserta.actualizaPregunta(FOLIOENCUESTA, Integer.toString(numpregunta), seccion, imagen);
                            inserta.cerrar();
                            Intent intent = new Intent(".Multipuntopita");
                            intent.putExtras(bolsa);
                            startActivity(intent);
                            finish();

                            Toast.makeText(getApplicationContext(), "DATOS GUARDADOS", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                btnFinalizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        pregunta1 = pregunta;
                        pregunta2 = "";
                        otro = "";
                        num = (pregunta1.equals("0") ? 0 : 1);
                        error = "SELECCIONE UNA OPCIÓN";

                        if(num!=0) {

                            inserta.abrir();
                            inserta.insertarPreg(FOLIOENCUESTA, IDPREGUNTAS[numpregunta], pregunta1, pregunta2, otro, Integer.toString(numpregunta), FOLIORESPUESTA, id);
                            inserta.cerrar();

                            numpregunta--;
                            inserta.abrir();
                            inserta.actualizaPregunta(FOLIOENCUESTA, Integer.toString(numpregunta), seccion, "1", tipo);
                            inserta.cerrar();

                            Intent intent = new Intent(".Fotopita");
                            intent.putExtras(bolsa);
                            startActivity(intent);
                            finish();

                            Toast.makeText(getApplicationContext(), "DATOS GUARDADOS", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            case 2:     //2.1.2
            case 4:     //2.2.2.
            case 16:    //3.1.2.
            case 21:    //3.3.2.
            case 27:    //3.5.2.
            case 47:    //4.1.2.
            case 58:    //4.3.2.
            case 64:    //4.5.2.
            case 70:    //4.6.2.
            case 75:    //4.8.2.
            case 79:    //4.8.2.
            case 90:    //4.12.3.
            case 93:    //4.12.3.
            case 95:    //4.12.5.
            case 97:    //4.12.7.
            case 100:    //4.12.9.   FOTO
            case 103:    //5.1.1.   FOTO

                tvEmpresa.setVisibility(View.VISIBLE);

                tvVersion.setVisibility(View.VISIBLE);

                spPreguntas.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
                if(numpregunta == 100 ){
                    btnFinalizar.setVisibility(View.VISIBLE);
                    btnFinalizar.setText("IMAGEN");
                    tipo="11";

                }

                if(numpregunta == 103 ){
                    btnFinalizar.setVisibility(View.VISIBLE);
                    btnFinalizar.setText("IMAGEN");
                    tipo="4";

                }
                lp = (LinearLayout.LayoutParams) tvPregunta.getLayoutParams();
                lp.setMargins(10,0,0,20);

                numCheck=0;
                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);
                inserta.abrir();
                NUM_CARRIL_LIGERO = inserta.campo("NUM_CARRIL_LIGERO", "encuesta");
                inserta.cerrar();

                etOtro.requestFocus();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                lp = (LinearLayout.LayoutParams) etOtro.getLayoutParams();
                lp.setMargins(100,0,100,50);
                etOtro.setLayoutParams(lp);
                etOtro.setGravity(Gravity.CENTER_HORIZONTAL);
                etOtro.setVisibility(View.VISIBLE);
                etOtro.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);


                lp = (LinearLayout.LayoutParams) etDos.getLayoutParams();
                lp.setMargins(100,0,100,50);
                etDos.setLayoutParams(lp);
                etDos.setGravity(Gravity.CENTER_HORIZONTAL);
                etDos.setVisibility(View.VISIBLE);
                etDos.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                etOtro.setHint("¿Cuantas instaladas?");
                etDos.setHint("¿Cuantas sin instalar?");

                if(numpregunta==27){
                    etOtro.setHint("¿Cuantas en uso?");
                    etDos.setHint("¿Cuantas almacenado?");
                }

                if(numpregunta==29){
                    etOtro.setHint("¿Cuantos instalados?");
                    etDos.setHint("¿Cuantos sin instalar?");
                }
                if(numpregunta==60 || numpregunta==64){
                    etOtro.setHint("¿Cuantos en uso?");
                    etDos.setHint("¿Cuantos almacenado?");
                }
                if(numpregunta==74){
                    etOtro.setHint("¿Cuantos instalados?");
                    etDos.setHint("¿Cuantos sin instalar?");
                }
                if(numpregunta==86){
                    etOtro.setHint("¿Cuantos instalados?");
                    etDos.setHint("¿Cuantos sin instalar?");
                }

                if(numpregunta==93){
                    etOtro.setHint("¿Cuantos instalados PITA?");
                    etDos.setHint("¿Cuantos instalados no PITA?");
                }

                if(numpregunta==103) {
                    etOtro.setHint("¿Cuántas son de PITA?");
                    etDos.setHint("¿Cuantos no son de PITA?");
                }

                btnGuardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        pregunta1 = etOtro.getText().toString();
                        pregunta2 = etDos.getText().toString();
                        otro = "";
                        num = (pregunta1.equals("") || pregunta2.equals("")  ? 0 : 1);
                        error = "INGRESE DATOS EN AMBAS CASILLAS ";

                        if(num!=0) {

                            inserta.abrir();
                            inserta.insertarPreg(FOLIOENCUESTA, IDPREGUNTAS[numpregunta], pregunta1, pregunta2, otro, Integer.toString(numpregunta), FOLIORESPUESTA, id);
                            inserta.actualizaPregunta(FOLIOENCUESTA, Integer.toString(numpregunta), seccion, imagen);
                            inserta.cerrar();
                            Intent intent = new Intent(".Multipuntopita");
                            intent.putExtras(bolsa);
                            startActivity(intent);
                            finish();


                            Toast.makeText(getApplicationContext(), "DATOS GUARDADOS", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                btnFinalizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        pregunta1 = etOtro.getText().toString();
                        pregunta2 = etDos.getText().toString();
                        otro = "";
                        num = (pregunta1.equals("") || pregunta2.equals("")  ? 0 : 1);
                        error = "INGRESE DATOS EN AMBAS CASILLAS ";

                        if(num!=0) {

                            inserta.abrir();
                            inserta.insertarPreg(FOLIOENCUESTA, IDPREGUNTAS[numpregunta], pregunta1, pregunta2, otro, Integer.toString(numpregunta), FOLIORESPUESTA, id);
                            inserta.cerrar();

                            numpregunta--;
                            inserta.abrir();
                            inserta.actualizaPregunta(FOLIOENCUESTA, Integer.toString(numpregunta), seccion, "1", tipo);
                            inserta.cerrar();

                            Intent intent = new Intent(".Fotopita");
                            intent.putExtras(bolsa);
                            startActivity(intent);
                            finish();

                            Toast.makeText(getApplicationContext(), "DATOS GUARDADOS", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;

            case 105:    //6.1.2.    FOTO
            case 106:    //6.1.3.    FOTO
            case 108:   //6.2.2.    FOTO
            case 109:   //6.2.3.    FOTO
                lp = (LinearLayout.LayoutParams) tvEmpresa.getLayoutParams();
                lp.setMargins(10,0,0,20);
                tvEmpresa.setVisibility(View.VISIBLE);
                tvVersion.setVisibility(View.VISIBLE);
                btnFinalizar.setVisibility(View.VISIBLE);
                btnFinalizar.setText("IMAGEN");
                tipo = "15";

                lp = (LinearLayout.LayoutParams) tvPregunta.getLayoutParams();
                lp.setMargins(10,0,0,20);

                numCheck=0;
                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);

                etOtro.requestFocus();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                lp = (LinearLayout.LayoutParams) etOtro.getLayoutParams();
                lp.setMargins(100,0,100,50);
                etOtro.setLayoutParams(lp);
                etOtro.setGravity(Gravity.CENTER_HORIZONTAL);
                etOtro.setVisibility(View.VISIBLE);
                etOtro.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                etOtro.setHint("¿Cuántas hay?");

                spPreguntas.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);

                btnGuardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        pregunta1 = etOtro.getText().toString();
                        pregunta2 = "";
                        otro = "";
                        num = (pregunta1.equals("") ? 0 : 1);
                        error = "INGRESE DATOS EN AMBAS CASILLAS ";

                        if(num!=0) {

                            inserta.abrir();
                            inserta.insertarPreg(FOLIOENCUESTA, IDPREGUNTAS[numpregunta], pregunta1, pregunta2, otro, Integer.toString(numpregunta), FOLIORESPUESTA, id);
                            inserta.actualizaPregunta(FOLIOENCUESTA, Integer.toString(numpregunta), seccion, imagen);
                            inserta.cerrar();
                            Intent intent = new Intent(".Multipuntopita");
                            intent.putExtras(bolsa);
                            startActivity(intent);
                            finish();

                            Toast.makeText(getApplicationContext(), "DATOS GUARDADOS", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                btnFinalizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        pregunta1 = etOtro.getText().toString();
                        pregunta2 = "";
                        otro = "";
                        num = (pregunta1.equals("") ? 0 : 1);
                        error = "INGRESE DATOS EN AMBAS CASILLAS ";

                        if(num!=0) {

                            inserta.abrir();
                            inserta.insertarPreg(FOLIOENCUESTA, IDPREGUNTAS[numpregunta], pregunta1, pregunta2, otro, Integer.toString(numpregunta), FOLIORESPUESTA, id);
                            inserta.cerrar();

                            numpregunta--;
                            inserta.abrir();
                            inserta.actualizaPregunta(FOLIOENCUESTA, Integer.toString(numpregunta), seccion, "1", tipo);
                            inserta.cerrar();

                            Intent intent = new Intent(".Fotopita");
                            intent.putExtras(bolsa);
                            startActivity(intent);
                            finish();

                            Toast.makeText(getApplicationContext(), "DATOS GUARDADOS", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            case 6:     //3.2.
            case 29:    //4.2.
                tvEmpresa.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                btnTerminar.setVisibility(View.VISIBLE);
                inserta.abrir();
                maximo= inserta.maxnumero(FOLIOENCUESTA, limite);
                inserta.cerrar();


                tvVersion.setVisibility(View.VISIBLE);

                if(numpregunta>28){
                    TIPOCARRIL = "CARRILES DE CARGA";
                }else{
                    TIPOCARRIL = "CARRILES LIGEROS";
                }
                nombresCarriles = new ArrayList<String>();
                insertaU.abrir();
                //Toast.makeText(this, FOLIOENCUESTA, Toast.LENGTH_SHORT).show();
                Cursor cur = insertaU.carriles(FOLIOENCUESTA, TIPOCARRIL);
                nombresCarriles.add("CARRILES");

                while (cur.moveToNext()) {
                    nombresCarriles.add(cur.getString(0));//id
                }
                cur.close();
                insertaU.cerrar();

                ArrayAdapter<String> arrayAdapter =
                        new ArrayAdapter<String>(this, R.layout.spinner_item, nombresCarriles);
                arrayAdapter.setDropDownViewResource(R.layout.spinner_item);

                this.spPreguntas.setAdapter(arrayAdapter);

                spPreguntas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int pos, long id) {
                        otro = String.valueOf(spPreguntas.getSelectedItem());

                        if(otro.equals("CARRILES")) {
                            etOtro.setVisibility(View.VISIBLE);
                        }else{
                            etOtro.setVisibility(View.GONE);
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                lp = (LinearLayout.LayoutParams) tvPregunta.getLayoutParams();
                lp.setMargins(40,20,20,0);

                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);

                lp = (LinearLayout.LayoutParams) etOtro.getLayoutParams();
                lp.setMargins(100,0,100,40);
                etOtro.setLayoutParams(lp);
                etOtro.setGravity(Gravity.CENTER_HORIZONTAL);
                etOtro.setVisibility(View.VISIBLE);

                if(numpregunta==6){
                    etOtro.setHint("Carril No.");
                }else if(numpregunta==29){
                    etOtro.setHint("Carril No.");
                }else if(numpregunta==35){
                    etOtro.setText("1");
                    etOtro.setHint("Zonas de revisión.");
                }

                btnTerminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        inserta.abrir();
                        inserta.actualizaPregunta(FOLIOENCUESTA, maximo, seccion, imagen);
                        inserta.cerrar();

                        Intent intent = new Intent(".Multipuntopita");
                        intent.putExtras(bolsa);
                        startActivity(intent);
                        finish();
                    }
                });

                btnGuardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(otro.equals("CARRILES")) {
                            pregunta1 = etOtro.getText().toString();

                        }else{
                            pregunta1 = otro;
                            Toast.makeText(Multipuntopita.this, otro, Toast.LENGTH_SHORT).show();
                        }

                        pregunta2 = id;
                        otro = "";
                        num = (pregunta1.equals("") ? 0 : 1);
                        error = "INGRESE LOS DATOS";

                        if(num!=0) {

                            inserta.abrir();
                            inserta.insertarPreg(FOLIOENCUESTA, IDPREGUNTAS[numpregunta], pregunta1, pregunta2, otro, Integer.toString(numpregunta), FOLIORESPUESTA, id);
                            inserta.actualizaPregunta(FOLIOENCUESTA, Integer.toString(numpregunta), seccion, imagen);
                            inserta.cerrar();
                            Intent intent = new Intent(".Multipuntopita");
                            intent.putExtras(bolsa);
                            startActivity(intent);
                            finish();


                            Toast.makeText(getApplicationContext(), "DATOS GUARDADOS", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            case 18:    //3.4.
            case 23:    //3.6.
            case 49:    //4.4.
            case 60:    //4.4.
            case 66:    //4.7.
            case 72:    //4.9.
            case 77:    //4.9.
            case 81:    //4.11.
                tvEmpresa.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                spPreguntas.setVisibility(View.GONE);
                btnTerminar.setVisibility(View.VISIBLE);

                inserta.abrir();
                maximo= inserta.maxnumero(FOLIOENCUESTA, limite);
                inserta.cerrar();

                tvVersion.setVisibility(View.VISIBLE);

                lp = (LinearLayout.LayoutParams) tvPregunta.getLayoutParams();
                lp.setMargins(40,20,20,0);

                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);

                lp = (LinearLayout.LayoutParams) etOtro.getLayoutParams();
                lp.setMargins(100,0,100,40);
                etOtro.setLayoutParams(lp);
                etOtro.setGravity(Gravity.CENTER_HORIZONTAL);
                etOtro.setVisibility(View.VISIBLE);
                etOtro.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

                if(numpregunta==18){
                    etOtro.setHint("Zona revisión No.");
                }else if(numpregunta==49){
                    etOtro.setHint("Posición carga peatonal No.");
                }else if(numpregunta == 23){
                    etOtro.setHint("Equipo móvil No.");
                }else if(numpregunta == 60){
                    etOtro.setHint("Equipo móvil No.");
                }else if(numpregunta == 66){
                    etOtro.setHint("Zona No.");
                }else if(numpregunta == 77){
                    etOtro.setHint("Espacio.");
                }else if(numpregunta == 72){
                    etOtro.setHint("Zona No.");
                }else if(numpregunta == 78){
                    etOtro.setHint("Carril No.");
                }if(numpregunta == 81){
                etOtro.setHint("Carril No.");
            }

                btnTerminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        inserta.abrir();
                        inserta.actualizaPregunta(FOLIOENCUESTA, maximo, seccion, imagen);
                        inserta.cerrar();

                        Intent intent = new Intent(".Multipuntopita");
                        intent.putExtras(bolsa);
                        startActivity(intent);
                        finish();
                    }
                });

                btnGuardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        pregunta1 = etOtro.getText().toString();
                        pregunta2 = id;
                        otro = "";
                        num = (pregunta1.equals("") ? 0 : 1);
                        error = "INGRESE LOS DATOS";

                        if(num!=0) {

                            inserta.abrir();
                            inserta.insertarPreg(FOLIOENCUESTA, IDPREGUNTAS[numpregunta], pregunta1, pregunta2, otro, Integer.toString(numpregunta), FOLIORESPUESTA, id);
                            inserta.actualizaPregunta(FOLIOENCUESTA, Integer.toString(numpregunta), seccion, imagen);
                            inserta.cerrar();

                            if(numpregunta == 4 && cuestionario==0){

                                Intent intent = new Intent(".Menupita");
                                intent.putExtras(bolsa);
                                startActivity(intent);
                                finish();

                            }else if(numpregunta == 37 && cuestionario==4){

                                Intent intent = new Intent(".Menupita");
                                intent.putExtras(bolsa);
                                startActivity(intent);
                                finish();

                            }else if(numpregunta == 95 && cuestionario==27){

                                Intent intent = new Intent(".Menupita");
                                intent.putExtras(bolsa);
                                startActivity(intent);
                                finish();

                            }else if(numpregunta == 103 && cuestionario==95){

                                Intent intent = new Intent(".Menupita");
                                intent.putExtras(bolsa);
                                startActivity(intent);
                                finish();

                            }else{
                                Intent intent = new Intent(".Multipuntopita");
                                intent.putExtras(bolsa);
                                startActivity(intent);
                                finish();
                            }

                            Toast.makeText(getApplicationContext(), "DATOS GUARDADOS", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            case 7:     //3.2.1.
            case 8:     //3.2.2.
            case 9:    //3.2.3.
            case 10:    //3.2.4.
            case 11:    //3.2.5.
            case 12:    //3.2.6.
            case 13:    //3.2.7.
            case 14:    //3.2.8.
            case 15:    //3.2.9.

            case 30:    //4.2.1.
            case 31:    //4.2.2.
            case 32:    //4.2.3.
            case 33:    //4.2.4.
            case 34:    //4.2.5.
            case 35:    //4.2.6.
            case 36:    //4.2.7.
            case 37:    //4.2.8.
            case 38:    //4.2.9.
            case 39:    //4.2.10.
            case 40:    //4.2.11.
            case 42:    //4.2.13.
            case 43:    //4.2.14.
            case 44:    //4.2.15.
            case 45:    //4.2.16.
            case 50:    //4.4.1.
            case 51:    //4.4.2.
            case 52:    //4.4.3.
            case 53:    //4.4.4.
            case 54:    //4.4.5.
            case 55:    //4.4.6.
            case 56:    //4.4.7.
            case 67:    //4.7.1.
            case 68:    //4.7.2.
            case 69:    //4.7.3.
            case 73:    //4.9.1.
            case 74:    //4.9.2.
            case 82:    //4.11.1.
            case 83:    //4.11.2.
            case 84:    //4.11.3.
            case 85:    //4.11.4.
            case 86:    //4.11.5.
            case 87:    //4.11.6.
            case 88:    //4.11.7.
            case 89:    //4.11.8.
            case 98:    //4.12.7.1

                listView.setVisibility(View.GONE);
                spPreguntas.setVisibility(View.GONE);


                btnFinalizar.setVisibility(View.VISIBLE);
                btnFinalizar.setText("IMAGEN");
                if(numpregunta == 98){
                btnFinalizar.setVisibility(View.GONE);
                btnFinalizar.setText("IMAGEN");
                }

                rbOp.setVisibility(View.VISIBLE);
                rbOp1.setText("a) Instalado");
                rbOp2.setText("b) Sin instalar");
                rbOp3.setText("c) No aplica");

                if(numpregunta==98){
                    rbOp1.setText("a) Instalado");
                    rbOp2.setText("b) Sin instalar");
                    rbOp3.setText("b) No hay acceso");
                }


                lp = (LinearLayout.LayoutParams) tvEmpresa.getLayoutParams();
                lp.setMargins(10,0,0,20);
                tvEmpresa.setVisibility(View.VISIBLE);

                inserta.abrir();
                if(numpregunta<17) {
                    preguntabase = "6";
                    tipo = "2";
                }else if(numpregunta<49) {
                    preguntabase = "29";
                    tipo = "3";
                }else if(numpregunta<63) {
                    preguntabase = "49";
                    tipo = "8";
                }else if(numpregunta<71) {
                    preguntabase = "66";
                    tipo = "9";
                }else if(numpregunta<77) {
                    preguntabase = "72";
                    tipo = "13";
                }else if(numpregunta<81) {
                    preguntabase = "78";
                    tipo = "13";
                }else{
                    preguntabase ="81";
                    tipo = "6";
                }
                carril = inserta.numeroPregunta("NUMPREGUNTA", preguntabase);
                nombrecarril = inserta.nombreCarril("NUMPREGUNTA", preguntabase);
                Cursor c = inserta.contarCarril("NUMPREGUNTA", preguntabase);
                conteocarriles = c.getCount();
                inserta.cerrar();
                if(numpregunta<91) {
                    tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta] + "\n NOMBRE " + nombrecarril + " CAPTURADOS " + String.valueOf(conteocarriles));
                }else{
                    tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);
                }
                btnGuardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(rbOp1.isChecked()){
                            pregunta1 = "a";
                        }else if(rbOp2.isChecked()){
                            pregunta1 = "b";
                        }else if(rbOp3.isChecked()){
                            pregunta1 = "c";
                        }else{
                            pregunta1 = "";
                        }
                        pregunta2 = carril;

                        num = (pregunta1.equals("")? 0 : 1);
                        error = "SELECCIONE UNA OPCIÓN";

                        if(num!=0) {

                            inserta.abrir();
                            inserta.insertarPreg(FOLIOENCUESTA, IDPREGUNTAS[numpregunta], pregunta1, pregunta2, otro, Integer.toString(numpregunta), FOLIORESPUESTA, id);
                            inserta.cerrar();

                            // En esta fase se regresa al principio del ciclo

                            switch (numpregunta){               //
                                case 15:                        //
                                    numpregunta = 5;            //
                                    break;
                                case 69:                        //
                                    numpregunta = 65;           //
                                    break;                      //
                                case 74:                        //
                                    numpregunta = 71;           //
                                    break;
                                case 89:                        //
                                    numpregunta = 80;           //
                                    break;                      //
                            }                                   //
                            //////////////////////////////////////

                            inserta.abrir();
                            inserta.actualizaPregunta(FOLIOENCUESTA, Integer.toString(numpregunta), seccion, imagen);
                            inserta.cerrar();

                              Intent intent = new Intent(".Multipuntopita");
                                intent.putExtras(bolsa);
                                startActivity(intent);
                                finish();

                            Toast.makeText(getApplicationContext(), "DATOS GUARDADOS", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                btnFinalizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(rbOp1.isChecked()){
                            pregunta1 = "a";
                        }else if(rbOp2.isChecked()){
                            pregunta1 = "b";
                        }else if(rbOp3.isChecked()){
                            pregunta1 = "c";
                        }else{
                            pregunta1 = "";
                        }
                        pregunta2 = carril;
                        if(numpregunta==91){
                            pregunta2="";
                        }
                        num = (pregunta1.equals("")? 0 : 1);
                        error = "SELECCIONE UNA OPCIÓN";

                        if(num!=0) {

                            inserta.abrir();
                            inserta.insertarPreg(FOLIOENCUESTA, IDPREGUNTAS[numpregunta], pregunta1, pregunta2, otro, Integer.toString(numpregunta), FOLIORESPUESTA, id);
                            inserta.cerrar();

                            numpregunta--;
                            inserta.abrir();
                            inserta.actualizaPregunta(FOLIOENCUESTA, Integer.toString(numpregunta), seccion, "1", tipo);
                            inserta.cerrar();

                            Intent intent = new Intent(".Fotopita");
                            intent.putExtras(bolsa);
                            startActivity(intent);
                            finish();

                            Toast.makeText(getApplicationContext(), "DATOS GUARDADOS", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            case 19:    //3.4.1.
            case 20:    //3.4.2.
            case 24:    //3.6.1.
            case 25:    //3.6.2.
            case 26:    //3.6.3.
            case 41:    //4.2.12.
            case 46:    //4.2.17.
            case 57:    //4.4.8.
            case 61:    //4.4.8.
            case 62:    //4.4.8.
            case 63:    //4.4.8.
            case 78:    //4.9.2.
            case 99:    //4.12.8.
            case 101:    //4.12.10.
            case 102:    //4.12.11.
            case 110:   //6.3.1.

                listView.setVisibility(View.GONE);
                spPreguntas.setVisibility(View.GONE);

                if(numpregunta == 26 || numpregunta == 63){

                }else {
                    btnFinalizar.setVisibility(View.VISIBLE);
                    btnFinalizar.setText("IMAGEN");
                }

                rbOp.setVisibility(View.VISIBLE);
                rbOp1.setText("a) Instalado");
                rbOp2.setText("b) Sin instalar");
                rbOp3.setVisibility(View.GONE);

                if(numpregunta>=25){
                    rbOp1.setText("a) Funciona");
                    rbOp2.setText("b) No Funciona");
                }

                if(numpregunta==42){
                    rbOp1.setText("a) Existe");
                    rbOp2.setText("b) No existe");
                }
                if(numpregunta==46 || numpregunta==58 || numpregunta==72){
                    rbOp1.setText("a) Ya están colocadas");
                    rbOp2.setText("b) No están colocadas");
                }
                if(numpregunta==26){
                    rbOp1.setText("a) Tiene");
                    rbOp2.setText("b) No tiene");
                }

                if(numpregunta==78){
                    rbOp1.setText("a) Instalado");
                    rbOp2.setText("b) Sin instalar");
                }

                if(numpregunta==99){
                    rbOp1.setText("a) Instalado");
                    rbOp2.setText("b) No instalado");
                }
                if(numpregunta==101){
                    rbOp1.setText("a) Existe");
                    rbOp2.setText("b) No existe");
                }
                if(numpregunta==102){
                    rbOp1.setText("a) Ya están colocadas");
                    rbOp2.setText("b) No están colocadas");
                }
                if(numpregunta==110){
                    rbOp1.setText("a) Hubo acceso");
                    rbOp2.setText("b) No hubo acceso");
                }

                lp = (LinearLayout.LayoutParams) tvEmpresa.getLayoutParams();
                lp.setMargins(10,0,0,20);
                tvEmpresa.setVisibility(View.VISIBLE);

                inserta.abrir();
                if(numpregunta<21) {
                    preguntabase = "18";
                    tipo = "7";
                }else if(numpregunta<28) {
                    preguntabase = "23";
                    tipo = "12";
                }else if(numpregunta<49) {
                    preguntabase = "29";
                    tipo = "3";
                }else if(numpregunta<59) {
                    preguntabase = "49";
                    tipo = "8";
                }else if(numpregunta<63) {
                    preguntabase = "49";
                    tipo = "12";
                }else if(numpregunta<63) {
                    preguntabase = "60";
                    tipo = "8";
                }else if(numpregunta<67) {
                    preguntabase = "62";
                    tipo = "9";
                }else if(numpregunta<74) {
                    preguntabase = "68";
                    tipo = "9";
                }else{
                    preguntabase = "77";
                    tipo = "14";
                }
                if(numpregunta==99 || numpregunta == 101 || numpregunta == 102){
                    tipo = "11";
                }

                if(numpregunta==110){
                    tipo = "5";
                }
                inserta.abrir();
                carril = inserta.numeroPregunta("NUMPREGUNTA", preguntabase);
                nombrecarril = inserta.nombreCarril("NUMPREGUNTA", preguntabase);
                 c = inserta.contarCarril("NUMPREGUNTA", preguntabase);
                conteocarriles = c.getCount();
                inserta.cerrar();
                if(numpregunta<91) {
                    tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta] +
                            "\n NOMBRE " + nombrecarril + " CAPTURADOS " + String.valueOf(conteocarriles));
                }else{
                    tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);
                }
                Toast.makeText(this, carril, Toast.LENGTH_SHORT).show();

                btnGuardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(rbOp1.isChecked()){
                            pregunta1 = "a";
                        }else if(rbOp2.isChecked()){
                            pregunta1 = "b";
                        }else {
                            pregunta1 = "";
                        }
                        pregunta2 = carril;

                        if(numpregunta==92){
                            pregunta2="";
                        }
                        num = (pregunta1.equals("")? 0 : 1);
                        error = "SELECCIONE UNA OPCIÓN";

                        if(num!=0) {

                            inserta.abrir();
                            inserta.insertarPreg(FOLIOENCUESTA, IDPREGUNTAS[numpregunta], pregunta1, pregunta2, otro, Integer.toString(numpregunta), FOLIORESPUESTA, id);
                            inserta.cerrar();

                            switch (numpregunta){
                                case 20:
                                    numpregunta = 17;
                                    break;
                                case 26:
                                    numpregunta = 22;
                                    break;
                                case 46:
                                    numpregunta = 28;
                                    break;
                                case 57:
                                    numpregunta = 48;
                                    break;
                                case 78:
                                    numpregunta = 76;
                                    break;
                            }

                            Intent intent = new Intent(".Multipuntopita");
                            intent.putExtras(bolsa);
                            startActivity(intent);
                            finish();

                            inserta.abrir();
                            inserta.actualizaPregunta(FOLIOENCUESTA, Integer.toString(numpregunta), seccion, imagen);
                            inserta.cerrar();

                            Toast.makeText(getApplicationContext(), "DATOS GUARDADOS", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                btnFinalizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(rbOp1.isChecked()){
                            pregunta1 = "a";
                        }else if(rbOp2.isChecked()){
                            pregunta1 = "b";
                        }else {
                            pregunta1 = "";
                        }
                        pregunta2 = carril;

                        if(numpregunta==92){
                            pregunta2="";
                        }
                        num = (pregunta1.equals("")? 0 : 1);
                        error = "SELECCIONE UNA OPCIÓN";

                        if(num!=0) {

                            inserta.abrir();
                            inserta.insertarPreg(FOLIOENCUESTA, IDPREGUNTAS[numpregunta], pregunta1, pregunta2, otro, Integer.toString(numpregunta), FOLIORESPUESTA, id);
                            inserta.cerrar();

                            numpregunta--;
                            inserta.abrir();
                            inserta.actualizaPregunta(FOLIOENCUESTA, Integer.toString(numpregunta), seccion, "1", tipo);
                            inserta.cerrar();

                            Intent intent = new Intent(".Fotopita");
                            intent.putExtras(bolsa);
                            startActivity(intent);
                            finish();

                            Toast.makeText(getApplicationContext(), "DATOS GUARDADOS", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
        }
        btnRevisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserta.actualizaPregunta(FOLIOENCUESTA, Integer.toString(numpregunta), seccion, imagen);
                Intent inte = new Intent(getApplicationContext(), Multipuntopita.class);
                inte.putExtras(bolsa);
                startActivity(inte);
                finish();
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(Regresar);
                startActivity(inte);
                finish();
            }
        });

    }

    public class Item {
        boolean checked;
        Drawable ItemDrawable;
        String ItemString;
        Item(String t, boolean b){
            ItemString = t;
            checked = b;
        }

        public boolean isChecked(){
            return checked;
        }
    }

    static class ViewHolder {
        CheckBox checkBox;
        TextView text;
    }

    public class ItemsListAdapter extends BaseAdapter {

        private Context context;
        private List<Item> list;

        ItemsListAdapter(Context c, List<Item> l) {
            context = c;
            list = l;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public boolean isChecked(int position) {
            return list.get(position).checked;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            // reuse views
            ViewHolder viewHolder = new ViewHolder();
            if (rowView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                rowView = inflater.inflate(R.layout.row, null);

                viewHolder.checkBox = (CheckBox) rowView.findViewById(R.id.rowCheckBox);
                viewHolder.text = (TextView) rowView.findViewById(R.id.rowTextView);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) rowView.getTag();
            }

            viewHolder.checkBox.setChecked(list.get(position).checked);

            final String itemStr = list.get(position).ItemString;
            viewHolder.text.setText(itemStr);

            viewHolder.checkBox.setTag(position);

            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean newState = !list.get(position).isChecked();
                    list.get(position).checked = newState;

                    String []textArray = itemStr.split(".-");

                    switch (numpregunta) {
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                            if (position == 0)
                                chk1 = newState && textArray[0].equals("1") ? "1" : "0";
                            //checkBox1.setChecked(false);
                            //list.get(position).setChecked(false);
                            if (position == 1)
                                chk2 = newState && textArray[0].equals("2") ? "2" : "0";
                            if (position == 2)
                                chk3 = newState && textArray[0].equals("3") ? "3" : "0";
                            break;
                    }

                    //Toast.makeText(context, chk1 + chk2 + chk3, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(), textArray [0] + "|"+  position, Toast.LENGTH_LONG).show();
                   /* Toast.makeText(getApplicationContext(),
                            textArray [0]+ " setOnClickListener\nchecked: " + newState,
                            Toast.LENGTH_LONG).show(); */
                }
            });

            viewHolder.checkBox.setChecked(isChecked(position));

            return rowView;
        }
    }

    private void initItems(int npregunta){
        items = new ArrayList<>();

        Log.d("numero error", String.valueOf(npregunta));
        switch (npregunta){
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                arrayText = getResources().obtainTypedArray(R.array.punto3_2_1);
                break;
            default:
                arrayText = getResources().obtainTypedArray(R.array.p1_8);
                break;
        }

        for(int i=0; i<arrayText.length(); i++){
            String s = arrayText.getString(i);
            boolean b = false;
            Item item = new Item(s, b);
            items.add(item);
        }

        arrayText.recycle();

    }

}

