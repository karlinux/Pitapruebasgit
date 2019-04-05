package com.g214.pita;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Multipuntopita extends AppCompatActivity {

    private final Handler_sqlite inserta = new Handler_sqlite(this);
    private final Handler_sqliteU insertaU = new Handler_sqliteU(this);
    private final Links l = new Links();
    String fecha = l.getFecha();
    String li = l.getLink();
    String VERSION = l.getVersion();
    String APK = l.getApk();

    EditText etOtro, etDos, etTres, etCuatro, etCuantos;
    TextView tvVersion, tvPregunta, tvEmpresa;
    Button btnGuardar, btnRegresar;
    Spinner spPreguntas, spPreguntas2;
    String n, pregunta, pregunta1, chk1, chk2, chk3, chk4, chk5, chk6, chk7, chk8, chk9,
            chk10, pregunta2, usuario, id, empresa, imei, FOLIOENCUESTA, dm, otro, FOLIORESPUESTA, error;
    //Intents
    String Foto, Regresar, Pregunta;
    int numpregunta, checkboxs;
    String [] IDPREGUNTAS, PREGUNTAS;
    ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> adapter2;

    int num, numCheck;

    List<Item> items;
    ListView listView;
    ItemsListAdapter myItemsListAdapter;
    TypedArray arrayText;
    LinearLayout.LayoutParams lp;


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

        inserta.abrir();
        usuario = inserta.usuario();
        numpregunta = Integer.parseInt(inserta.NUMPREGUNTA());
        id = inserta.iden();
        empresa = inserta.empresa();
        fecha = inserta.fecha("encuesta", "FECHAENTREVISTA");
        inserta.cerrar();
        //Toast.makeText(getApplicationContext(), Integer.toString(numpregunta), Toast.LENGTH_SHORT).show();
        numpregunta ++;

        listView = (ListView)findViewById(R.id.listview);

        initItems(numpregunta);
        myItemsListAdapter = new ItemsListAdapter(this, items);
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

        StringBuilder stringFecha = new StringBuilder();

        stringFecha.append(imei);
        stringFecha.append(fecha.substring(0,4));
        stringFecha.append(fecha.substring(5,7));
        stringFecha.append(fecha.substring(8,10));
        stringFecha.append(fecha.substring(11,13));
        stringFecha.append(fecha.substring(14,16));
        stringFecha.append(fecha.substring(17,19));
        stringFecha.append(id);

        StringBuilder stringFolioRespuesta = new StringBuilder();

        FOLIOENCUESTA = stringFecha.toString();

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

        spPreguntas = (Spinner) findViewById(R.id.spPreguntas);
        spPreguntas2 = (Spinner) findViewById(R.id.spPreguntas2);

        tvVersion = (TextView) findViewById(R.id.tvVersion);
        tvPregunta = (TextView) findViewById(R.id.tvPregunta);
        tvEmpresa = (TextView) findViewById(R.id.tvEmpresa);

        btnGuardar = (Button) findViewById(R.id.btnIniciar);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);

        etCuantos = (EditText) findViewById(R.id.etCuantos);
        etOtro = (EditText) findViewById(R.id.etOtro);
        etDos = (EditText) findViewById(R.id.etDos);
        etTres = (EditText) findViewById(R.id.etTres);
        etCuatro = (EditText) findViewById(R.id.etCuatro);
        //etOtro.setVisibility(View.GONE); #65646A
        etDos.setVisibility(View.GONE);
        etTres.setVisibility(View.GONE);
        etCuatro.setVisibility(View.GONE);


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

        switch (numpregunta){

            case 2: // 2.1 ¿Cuenta con Certificación OEA?
                lp = (LinearLayout.LayoutParams) tvEmpresa.getLayoutParams();
                lp.setMargins(10,0,0,20);
                tvEmpresa.setVisibility(View.VISIBLE);
                tvEmpresa.setText("2.1. Video vigilancia");

                lp = (LinearLayout.LayoutParams) tvPregunta.getLayoutParams();
                lp.setMargins(10,0,0,20);

                numCheck=0;
                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);
                etOtro.setHint("Otro");
                etOtro.setVisibility(View.GONE);
                tvVersion.setVisibility(View.GONE);
                spPreguntas.setVisibility(View.GONE);


            break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
            case 12:
            case 13:
            case 14:
            case 16:
            case 17:
            case 19:
            case 20:
            case 21:
            case 22:
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
            case 10:
            case 15:
            case 18:
            case 25:
                lp = (LinearLayout.LayoutParams) tvEmpresa.getLayoutParams();
                lp.setMargins(10,0,0,20);
                tvEmpresa.setVisibility(View.VISIBLE);
                if(numpregunta>=15) {
                    tvEmpresa.setText("2.3. Posición de monitoreo");
                }else {
                    tvEmpresa.setText("2.2. Cámaras Térmicas");
                }
                lp = (LinearLayout.LayoutParams) tvPregunta.getLayoutParams();
                lp.setMargins(10,0,0,20);

                numCheck=0;
                tvPregunta.setText(IDPREGUNTAS[numpregunta] + PREGUNTAS[numpregunta]);



                lp = (LinearLayout.LayoutParams) etOtro.getLayoutParams();
                lp.setMargins(100,0,100,60);
                etOtro.setLayoutParams(lp);
                etOtro.setGravity(Gravity.CENTER_HORIZONTAL);
                etOtro.setVisibility(View.VISIBLE);
                etOtro.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                etOtro.setHint("Cuantas ya están operando");

                lp = (LinearLayout.LayoutParams) etDos.getLayoutParams();
                lp.setMargins(100,0,100,60);
                etDos.setLayoutParams(lp);
                etDos.setGravity(Gravity.CENTER_HORIZONTAL);
                etDos.setVisibility(View.VISIBLE);
                etDos.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                etDos.setHint("Cuantos están en pruebas \no listas para pruebas");

                lp = (LinearLayout.LayoutParams) etTres.getLayoutParams();
                lp.setMargins(100,0,100,60);
                etTres.setLayoutParams(lp);
                etTres.setGravity(Gravity.CENTER_HORIZONTAL);
                etTres.setVisibility(View.VISIBLE);
                etTres.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                etTres.setHint("Cuantas no están operando, \nni en pruebas o listos para pruebas");

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

                otro = etOtro.getText().toString();

                switch (numpregunta){
                    case 2:
                        pregunta1 = chk1+"|"+chk2+"|"+chk3;
                        num = (chk1.equals("0") && chk2.equals("0")  && chk3.equals("0") ? 0 : 1);
                        error = "AL MENOS UNA CASILLA DEBE DE ESTAR SELECCIONADA";
                        break;
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 16:
                    case 17:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                        pregunta1 = etOtro.getText().toString();
                        pregunta2 = etDos.getText().toString();
                        otro = "";
                        num = (pregunta1.equals("") || pregunta2.equals("")  ? 0 : 1);
                        error = "INGRESE DATOS EN AMBAS CASILLAS ";
                        break;
                    case 10:
                    case 15:
                    case 18:

                        pregunta1 = etOtro.getText().toString();
                        pregunta2 = etDos.getText().toString();
                        otro = etTres.getText().toString();

                        num = (pregunta1.equals("") || pregunta2.equals("") || otro.equals("")  ? 0 : 1);
                        error = "INGRESE DATOS EN AMBAS CASILLAS ";
                        break;
                }

                if(num!=0) {
                inserta.abrir();
                inserta.insertarPreg(FOLIOENCUESTA, IDPREGUNTAS[numpregunta], pregunta1, pregunta2, otro, Integer.toString(numpregunta), FOLIORESPUESTA);
                inserta.actualizaPregunta(id, Integer.toString(numpregunta));
                inserta.cerrar();

                if( numpregunta == 41 ){
                    Intent intent = new Intent(Foto);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(Pregunta);
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
                        case 2:
                            if (position == 0)
                                chk1 = newState && textArray[0].equals("1") ? "1" : "0";
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
            case 2:
                arrayText = getResources().obtainTypedArray(R.array.punto2_1_2);
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

