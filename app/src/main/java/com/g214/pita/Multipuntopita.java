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
    Button btnGuardar, btnRegresar, btnFinalizar;
    Spinner spPreguntas, spPreguntas2;
    String n, pregunta, pregunta1, chk1, chk2, chk3, chk4, chk5, chk6, chk7, chk8, chk9, NUM_CARRIL_LIGERO,
            chk10, pregunta2, usuario, id, empresa, imei, FOLIOENCUESTA, dm, otro, FOLIORESPUESTA, error, carril, rbOpcion, maximo, TIPOCARRIL;
    //Intents
    String Foto, Regresar, Pregunta, seccion;
    int numpregunta, checkboxs;
    String [] IDPREGUNTAS, PREGUNTAS;
    ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> adapter2;
    RadioGroup rbOp;
    RadioButton rbOp1, rbOp2, rbOp3;
    int num, numCheck, conteo, cuestionario;
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
        FOLIOENCUESTA = inserta.FOLIOENCUESTA();
        inserta.cerrar();
        fecha = inserta.fecha("encuesta", "FECHAENTREVISTA", FOLIOENCUESTA);
        bolsa = getIntent().getExtras();
        cuestionario = Integer.parseInt(bolsa.getString("numpregunta"));

        inserta.abrir();

        switch (cuestionario){ //// Primer switch Seccion de Preguntas

            case 0:
                seccion = "UNO";
                numpregunta = Integer.parseInt(inserta.NUMPREGUNTA(FOLIOENCUESTA, seccion));
                if(numpregunta==4){
                    Intent intent = new Intent(".Menupita");
                    startActivity(intent);
                    finish();
                    Toast.makeText(this, "YA FUE CAPTURADA LA PRIMERA VISTA", Toast.LENGTH_SHORT).show();
                }
                break;

            case 4:
                seccion = "DOS";
                numpregunta = Integer.parseInt(inserta.NUMPREGUNTA(FOLIOENCUESTA, seccion));
                if(numpregunta==27){
                    Intent intent = new Intent(".Menupita");
                    startActivity(intent);
                    finish();
                    Toast.makeText(this, "YA FUE CAPTURADA LA PRIMERA VISTA", Toast.LENGTH_SHORT).show();
                }
                break;
            case 27:
                seccion = "TRES";
                numpregunta = Integer.parseInt(inserta.NUMPREGUNTA(FOLIOENCUESTA, seccion));
                if(numpregunta==47){
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

        //////////////////////////   Segundo switch tipo de pregunta
        switch (numpregunta){

            case 1:// 2.1.1
            case 3:// 2.1.1
            case 5:// 2.1.1
            case 17:
            case 22:
            case 28:

                tvVersion.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                tvVersion.setTextColor(Color.parseColor("#79032e"));

                if(numpregunta>27){
                    tvVersion.setText("IV. Vehículos de carga");
                    tvEmpresa.setText("4.1.  Carriles de vehículos de carga");
                }else if(numpregunta>21){
                    tvVersion.setText(" II. Vehículos ligeros");
                    tvEmpresa.setText("3.5. Equipos móviles en zona de inspección de carriles ligeros");
                }else if(numpregunta>16){
                    tvVersion.setText(" II. Vehículos ligeros");
                    tvEmpresa.setText("3.3. Zona de revisión de ligeros");
                }else if(numpregunta>4){
                    tvVersion.setText(" II. Vehículos ligeros");
                    tvEmpresa.setText("3.1. Carriles de vehículos ligeros");
                }else{
                    tvVersion.setText(" II. Video vigilancia");
                    tvEmpresa.setText("2.1. Video vigilancia");
                }


                tvEmpresa.setVisibility(View.VISIBLE);

                tvVersion.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);

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

                        if(pregunta.equals("2") && numpregunta==1){

                            numpregunta = 2;

                        }else if(pregunta.equals("2") && numpregunta==3){

                            numpregunta = 4;

                        }else if(pregunta.equals("2") && numpregunta==17){

                            numpregunta = 21;

                        }else if(pregunta.equals("2") && numpregunta==22){

                            numpregunta = 24;

                        }else{

                            inserta.abrir();
                            numpregunta = Integer.parseInt(inserta.NUMPREGUNTA(FOLIOENCUESTA, seccion));
                            numpregunta++;
                            inserta.cerrar();

                        }

                        //Toast.makeText(Preguntapuntopita.this, Integer.toString(numpregunta), Toast.LENGTH_SHORT).show();

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                break;
            case 2: // 2.1.2
            case 4:
            case 6:
            case 18:
            case 23:
            case 29:
                lp = (LinearLayout.LayoutParams) tvEmpresa.getLayoutParams();
                lp.setMargins(10,0,0,20);
                tvEmpresa.setVisibility(View.VISIBLE);
                tvEmpresa.setVisibility(View.VISIBLE);

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

                if(numpregunta==23){
                    etOtro.setHint("¿Cuantas En uso?");
                    etDos.setHint("¿Cuantas Almacenado?");
                }

                if(numpregunta==29){
                    etOtro.setHint("¿Cuantos Instalados?");
                    etDos.setHint("¿Cuantos Sin instalar?");
                }

                tvVersion.setVisibility(View.GONE);
                spPreguntas.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);

                break;

            case 7:
            case 30:
                tvEmpresa.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                btnFinalizar.setVisibility(View.VISIBLE);
                inserta.abrir();
                maximo= inserta.maxnumero(FOLIOENCUESTA);
                inserta.cerrar();

                if(numpregunta>35) {
                    tvEmpresa.setText("3.3. Zona de revisión");
                }else {
                    tvEmpresa.setText("3.1. Carriles de vehículos ligeros");
                }
                tvVersion.setVisibility(View.VISIBLE);

                if(numpregunta>29){
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

                if(numpregunta==7){
                    etOtro.setHint("Carril No.");
                }else if(numpregunta==30){
                    etOtro.setHint("Carril No.");
                }else if(numpregunta==36){
                    etOtro.setText("1");
                    etOtro.setHint("Zonas de revisioin.");
                }

                btnFinalizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        inserta.abrir();
                        inserta.actualizaPregunta(FOLIOENCUESTA, maximo, seccion);
                        inserta.cerrar();

                        Intent intent = new Intent(".Multipuntopita");
                        intent.putExtras(bolsa);
                        startActivity(intent);
                        finish();
                    }
                });
                break;
            case 19:
            case 24:
                tvEmpresa.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                spPreguntas.setVisibility(View.GONE);
                btnFinalizar.setVisibility(View.VISIBLE);

                inserta.abrir();
                maximo= inserta.maxnumero(FOLIOENCUESTA);
                inserta.cerrar();

                if(numpregunta>23) {
                    tvEmpresa.setText("3.5. Equipos móviles Zona de inspección");
                    tvEmpresa.setVisibility(View.GONE);
                }else {
                    tvEmpresa.setText("3.1. Carriles de vehículos ligeros");
                }
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

                if(numpregunta==19){
                    etOtro.setHint("Zona revisión No.");
                }else if(numpregunta == 24){
                    etOtro.setHint("Equipo móvil No.");
                }

                btnFinalizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        inserta.abrir();
                        inserta.actualizaPregunta(FOLIOENCUESTA, maximo, seccion);
                        inserta.cerrar();

                        Intent intent = new Intent(".Multipuntopita");
                        intent.putExtras(bolsa);
                        startActivity(intent);
                        finish();
                    }
                });
                break;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:

                listView.setVisibility(View.GONE);
                spPreguntas.setVisibility(View.GONE);

                rbOp.setVisibility(View.VISIBLE);
                rbOp1.setText("a) Instalado");
                rbOp2.setText("b) Sin instalar");
                rbOp3.setText("c) No tiene");

                lp = (LinearLayout.LayoutParams) tvEmpresa.getLayoutParams();
                lp.setMargins(10,0,0,20);
                tvEmpresa.setVisibility(View.VISIBLE);

                if(numpregunta>=8) {
                    tvEmpresa.setText("3.1. Carriles de vehículos ligeros");
                }else{
                    tvEmpresa.setText("2.1. Video vigilancia");
                }

                inserta.abrir();
                carril = inserta.numeroPregunta("NUMPREGUNTA", "7");
                inserta.cerrar();
                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);
                break;
            case 20:
            case 21:
            case 25:
            case 26:
            case 27:

                listView.setVisibility(View.GONE);
                spPreguntas.setVisibility(View.GONE);

                rbOp.setVisibility(View.VISIBLE);
                rbOp1.setText("a) Instalado");
                rbOp2.setText("b) Sin instalar");
                rbOp3.setVisibility(View.GONE);

                if(numpregunta>=25){
                rbOp1.setText("a) Funciona");
                rbOp2.setText("b) No Funciona");
                }

                lp = (LinearLayout.LayoutParams) tvEmpresa.getLayoutParams();
                lp.setMargins(10,0,0,20);
                tvEmpresa.setVisibility(View.VISIBLE);

                if(numpregunta>=25) {
                    tvEmpresa.setText("3.6. Equipos moviles");
                }else if(numpregunta>=8) {
                    tvEmpresa.setText("3.1. Carriles de vehículos ligeros");
                }else{
                    tvEmpresa.setText("2.1. Video vigilancia");
                }

                inserta.abrir();
                carril = inserta.numeroPregunta("NUMPREGUNTA", "19");
                inserta.cerrar();
                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);

                break;
            case 46:
                lp = (LinearLayout.LayoutParams) tvEmpresa.getLayoutParams();
                lp.setMargins(10,0,0,20);
                tvEmpresa.setVisibility(View.VISIBLE);

                if(numpregunta>=26) {
                    tvEmpresa.setText("3.1. Carriles de vehículos ligeros");
                }else{
                    tvEmpresa.setText("2.1. Video vigilancia");
                }

                inserta.abrir();
                carril = inserta.numeroPregunta("NUMPREGUNTA", "26");
                inserta.cerrar();

                lp = (LinearLayout.LayoutParams) tvPregunta.getLayoutParams();
                lp.setMargins(10,0,0,20);

                numCheck=0;
                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);
                etOtro.setHint("Otro");
                etOtro.setVisibility(View.GONE);
                tvVersion.setVisibility(View.GONE);
                spPreguntas.setVisibility(View.GONE);

            if(numpregunta == 35){
                inserta.abrir();
                NUM_CARRIL_LIGERO = inserta.campo("NUM_CARRIL_LIGERO", "encuesta");
                Cursor contar = inserta.conteo(FOLIOENCUESTA, "27");
                conteo = contar.getCount();
                inserta.cerrar();
            }

            break;

            case 42:
                lp = (LinearLayout.LayoutParams) tvEmpresa.getLayoutParams();
                lp.setMargins(10,0,0,20);
                tvEmpresa.setVisibility(View.VISIBLE);
                if(numpregunta>=9) {
                    tvEmpresa.setText("2.2. Cámaras Térmicas");
                }else if(numpregunta>=16) {
                    tvEmpresa.setText("2.3. Posición de monitoreo");
                }else{
                    tvEmpresa.setText("2.1. Video vigilancia");
                }
                lp = (LinearLayout.LayoutParams) tvPregunta.getLayoutParams();
                lp.setMargins(10,0,0,20);

                numCheck=0;
                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);
                etOtro.requestFocus();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                etOtro.setHint("Cuantos ya están instalados");

                if(numpregunta==19){
                    etDos.setHint("Cuantos no están operando, ni en \npruebas o listos para pruebas");
                }else {
                    etDos.setHint("Cuantos no están instalados");
                }
                lp = (LinearLayout.LayoutParams) etOtro.getLayoutParams();
                lp.setMargins(100,0,100,40);
                etOtro.setLayoutParams(lp);
                etOtro.setGravity(Gravity.CENTER_HORIZONTAL);
                etOtro.setVisibility(View.VISIBLE);
                etOtro.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                lp = (LinearLayout.LayoutParams) etDos.getLayoutParams();
                lp.setMargins(100,0,100,180);
                etDos.setLayoutParams(lp);
                etDos.setGravity(Gravity.CENTER_HORIZONTAL);
                etDos.setVisibility(View.VISIBLE);
                etDos.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                tvVersion.setVisibility(View.GONE);
                spPreguntas.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);

                break;

            case 50: // 2.4 ¿Cuenta con Certificación OEA?
                numCheck=0;
                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);
                tvEmpresa.setVisibility(View.GONE);
                tvVersion.setVisibility(View.GONE);
                etOtro.setHint("Otro");
                etOtro.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
                adapter = ArrayAdapter.createFromResource(
                        this, R.array.p1_1, R.layout.spinner_item);
                // Specify the layout to use when the list of choices appears

                adapter.setDropDownViewResource(R.layout.spinner_item);

                spPreguntas.setAdapter(adapter);

                spPreguntas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int pos, long id) {
                        String [] arrayPreguntas = String.valueOf(spPreguntas.getSelectedItem()).split("-");

                        pregunta = arrayPreguntas[0];

                        if(pregunta.equals("2")){
                            spPreguntas2.setVisibility(View.VISIBLE);

                        }else{
                            spPreguntas2.setVisibility(View.GONE);
                            etCuantos.setVisibility(View.GONE);
                            etOtro.setVisibility(View.GONE);
                            listView.setVisibility(View.GONE);
                            etOtro.setText("");
                        }

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                adapter2 = ArrayAdapter.createFromResource(
                        this, R.array.p2_4_2, R.layout.spinner_item);
                // Specify the layout to use when the list of choices appears

                adapter2.setDropDownViewResource(R.layout.spinner_item);

                spPreguntas2.setAdapter(adapter2);

                spPreguntas2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int pos, long id) {
                        String [] arrayPreguntas = String.valueOf(spPreguntas2.getSelectedItem()).split("-");

                        pregunta2 = arrayPreguntas[0];

                        if(pregunta2.equals("1")){
                            listView.setVisibility(View.VISIBLE);
                            etCuantos.setVisibility(View.VISIBLE);
                            etCuantos.setHint("¿Cuantos?");

                        }else if(pregunta2.equals("3")){
                            etOtro.setVisibility(View.VISIBLE);
                            etCuantos.setHint("¿Especifique?");
                            listView.setVisibility(View.GONE);
                            etCuantos.setVisibility(View.GONE);
                        }else{
                            listView.setVisibility(View.GONE);
                            etCuantos.setVisibility(View.GONE);
                            etOtro.setVisibility(View.GONE);
                            etOtro.setText("");
                        }

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                break;
        }
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(Regresar);
                startActivity(inte);
                finish();
            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

    ////////////////////////////////////////////////  Tercer switch  tipo de resultado////////////////////////////////////////////
                switch (numpregunta){
                    case 1:
                    case 3:
                    case 5:
                    case 17:
                    case 22:
                    case 28:
                        pregunta1 = pregunta;
                        pregunta2 = "";
                        otro = "";
                        num = (pregunta1.equals("0") ? 0 : 1);
                        error = "SELECCIONE UNA OPCIÓN";
                        break;
                    case 7:
                        if(otro.equals("CARRILES")) {
                            pregunta1 = etOtro.getText().toString();

                        }else{
                            pregunta1 = otro;
                            Toast.makeText(Multipuntopita.this, otro, Toast.LENGTH_SHORT).show();
                        }

                        pregunta2 = "";
                        otro = "";
                        num = (pregunta1.equals("") ? 0 : 1);
                        error = "INGRESE LOS DATOS";
                        break;
                    case 19:
                    case 24:
                        pregunta1 = etOtro.getText().toString();
                        pregunta2 = "";
                        otro = "";
                        num = (pregunta1.equals("") ? 0 : 1);
                        error = "INGRESE LOS DATOS";
                        break;
                    case 2:
                    case 4:
                    case 6:
                    case 18:
                    case 23:
                    case 29:
                        pregunta1 = etOtro.getText().toString();
                        pregunta2 = etDos.getText().toString();
                        otro = "";
                        num = (pregunta1.equals("") || pregunta2.equals("")  ? 0 : 1);
                        error = "INGRESE DATOS EN AMBAS CASILLAS ";
                        break;
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
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
                        break;
                    case 20:
                    case 21:
                    case 25:
                    case 26:
                    case 27:
                        if(rbOp1.isChecked()){
                            pregunta1 = "a";
                        }else if(rbOp2.isChecked()){
                            pregunta1 = "b";
                        }else {
                            pregunta1 = "";
                        }
                        pregunta2 = carril;
                        num = (pregunta1.equals("")? 0 : 1);
                        error = "SELECCIONE UNA OPCIÓN";
                        break;
                    case 56:
                        pregunta2 = carril;
                        pregunta1 = chk1+"|"+chk2+"|"+chk3;
                        num = (chk1.equals("0") && chk2.equals("0")  && chk3.equals("0") ? 0 : 1);
                        error = "AL MENOS UNA CASILLA DEBE DE ESTAR SELECCIONADA";
                        break;

                    case 65:

                        pregunta1 = etOtro.getText().toString();
                        pregunta2 = etDos.getText().toString();


                            num = (pregunta1.equals("") || pregunta2.equals("") ? 0 : 1);
                            error = "INGRESE DATOS EN TODAS LAS CASILLAS ";

                            if(num==1){
                                int num1= Integer.parseInt(pregunta1) + Integer.parseInt(pregunta2) +Integer.parseInt(otro);
                                int num2= Integer.parseInt(NUM_CARRIL_LIGERO);
                                if(numpregunta==25 && num1>num2) {
                                    error = "LA SUMA DE CARRILES ES MAYOR AL NUMERO INGRESADO";
                                    num=0;
                                }
                            }
                        break;
                }

                if(num!=0) {

                inserta.abrir();
                inserta.insertarPreg(FOLIOENCUESTA, IDPREGUNTAS[numpregunta], pregunta1, pregunta2, otro, Integer.toString(numpregunta), FOLIORESPUESTA);
                inserta.cerrar();

                    if(numpregunta == 16){
                        numpregunta = 6;
                    }

                    if(numpregunta == 21){
                        numpregunta = 18;
                    }
                    if(numpregunta == 27){
                        numpregunta = 23;
                    }

                inserta.abrir();
                inserta.actualizaPregunta(FOLIOENCUESTA, Integer.toString(numpregunta), seccion);
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

