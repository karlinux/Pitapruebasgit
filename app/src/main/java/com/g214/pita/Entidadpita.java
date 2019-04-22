package com.g214.pita;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by carlos on 15/03/17.
 */

public class Entidadpita extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private final Handler_sqlite inserta = new Handler_sqlite(this);
    private final Handler_sqlite_puntos insertaU = new Handler_sqlite_puntos(this);
    private final Links l = new Links();
    String fecha = l.getFecha();
    String VERSION = l.getVersion();
    String CAMPO = l.getCampo();
    String APK = l.getApk();

    Button btnIniciar;
    TextView tvVersion, tvFolio, tvFecha;
    LayoutInflater inflater;
    View convertView;
    EditText etColonia;

    String municipio, selectEntidad, selectMunicipio, selectLocalidad, colonia, entidad, localidad, id, entidadBase, municipioBase, Siguiente;
    private Spinner spEntidad, spMunicipio, spLocalidad;
    String[] arrayEntidad;
    ArrayList<String> nameList, nameListloc;
    int entidadPos, munPos;
    Typeface ligt, regular, medio;
    Cursor domicilio, cur;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entidad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        arrayEntidad = getResources().getStringArray(R.array.entidad);

        if(CAMPO.equals("1")){
            Siguiente=".Foto" + APK;
        }else{
            Siguiente=".Empresa" + APK;
        }

        ligt =Typeface.createFromAsset(getAssets(), "fonts/montserratLight.otf");
        regular=Typeface.createFromAsset(getAssets(), "fonts/montserratRegular.otf");
        medio=Typeface.createFromAsset(getAssets(), "fonts/montserratMedium.otf");
        //////  Spiner  ///////
        spEntidad = (Spinner) findViewById(R.id.spEntidad);
        spMunicipio = (Spinner) findViewById(R.id.spMunicipio);
        spLocalidad = (Spinner) findViewById(R.id.spLocalidad);
        tvFecha = (TextView) findViewById(R.id.tvFecha);
        tvVersion = (TextView) findViewById(R.id.tvVersion);
        btnIniciar = (Button)findViewById(R.id.btnIniciar);
        etColonia = (EditText)findViewById(R.id.etColonia);

        entidadBase = "";
        municipioBase = "";
        tvFecha.setText(fecha);
        tvVersion.setText("Versión " + VERSION + ".0");

        tvFecha.setTypeface(regular);
        tvVersion.setTypeface(ligt);
        btnIniciar.setTypeface(medio);

        inserta.abrir();
        id = inserta.iden();
        domicilio = inserta.entidad(id);

            while (domicilio.moveToNext()) {
                if(domicilio.getString(1)==null){
                    entidadBase = "";
                }else{
                    entidadBase = domicilio.getString(1);
                }
                if(domicilio.getString(2)==null){
                    municipioBase = "";
                }else{
                    municipioBase = domicilio.getString(2);
                }
            }

        domicilio.close();
        inserta.cerrar();

        if(!entidadBase.equals("") && !municipioBase.equals("")){
            etColonia.setVisibility(View.GONE);
        }

        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!entidadBase.equals("") && !municipioBase.equals("")){
                    entidad = entidadBase;
                    municipio = municipioBase;
                }else {
                    String[] entidadArray = selectEntidad.split("-");
                    entidad = entidadArray[0];
                    String[] municipioArray = selectMunicipio.split("-");
                    municipio = municipioArray[0];
                }
                String[] localidadArray = selectLocalidad.split("-");
                localidad = localidadArray[0];


                String error="";
                colonia = etColonia.getText().toString();
                if(entidad.equals("0")){
                    error = "SELECCIONE EL ESTADO";
                }else if(municipio.equals("0")){
                    error = "SELECCIONE EL MUNICIPIO";
                }else if(localidad.equals("0")){
                    error = "SELECCIONE LA LOCALIDAD";
                }else if(entidadBase.equals("") && municipioBase.equals("") && colonia.equals("")){
                    error = "INGRESE LA COLONIA";
                }

                if (error.equals("")) {
                    inserta.abrir();
                    if(!entidadBase.equals("") && !municipioBase.equals("")){

                        inserta.actualizaEntidad(id,localidad);
                    }else{
                        inserta.actualizaEntidad(id,entidad, municipio, localidad, colonia);
                    }
                    inserta.cerrar();

                    Toast.makeText(getApplicationContext(), "DATOS GUARDADOS", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Siguiente);
                    //Intent intent = new Intent(getApplicationContext(), Empresapita.class);
                    //Intent intent = new Intent(getApplicationContext(), Fotopita.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                }
            }
        });
        loadSpinnerMunicipios();

    }
    private void loadSpinnerMunicipios() {

        // Create an ArrayAdapter using the string array and a default spinner
        // layout

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.entidad, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears

        adapter.setDropDownViewResource(R.layout.spinner_item);

        // Apply the adapter to the spinner
        this.spEntidad.setAdapter(adapter);

        if(!entidadBase.equals("") && !municipioBase.equals("")){
            this.spEntidad.setSelection(Integer.parseInt(entidadBase));
            this.spEntidad.setEnabled(false);
        }


       // View convertView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.spinner_item,null);

        // This activity implements the AdapterView.OnItemSelectedListener
        this.spEntidad.setOnItemSelectedListener(this);
        this.spMunicipio.setOnItemSelectedListener(this);
        this.spLocalidad.setOnItemSelectedListener(this);

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        switch (parent.getId()) {

            case R.id.spEntidad:
                //nameList.add("00.SELECCIONE ESTADO");

                nameList = new ArrayList<String>();

                insertaU.abrir();
                if(!entidadBase.equals("") && !municipioBase.equals("")){

                    entidadPos = Integer.parseInt(entidadBase);
                    munPos = Integer.parseInt(municipioBase);

                    Cursor cur = insertaU.municipio(Integer.toString(entidadPos), Integer.toString(munPos));
                    while (cur.moveToNext()) {

                        nameList.add(cur.getString(1) + "-" + cur.getString(2));//id
                    }
                    cur.close();

                }else {

                    entidadPos = pos;
                    cur = insertaU.municipio(Integer.toString(pos));
                    int num = cur.getCount();
                    nameList.add("0-MUNICIPIO");
                    while (cur.moveToNext()) {

                        nameList.add(cur.getString(1) + "-" + cur.getString(2));//id
                    }
                    cur.close();
                }
                insertaU.cerrar();
                ArrayAdapter<String> arrayAdapter =
                        new ArrayAdapter<String>(this, R.layout.spinner_item, nameList);
                arrayAdapter.setDropDownViewResource(R.layout.spinner_item);

                // Apply the adapter to the spinner
                this.spMunicipio.setAdapter(arrayAdapter);
                selectEntidad = String.valueOf(spEntidad.getSelectedItem());

                break;

            case R.id.spMunicipio:
                selectMunicipio = String.valueOf(spMunicipio.getSelectedItem());
                String [] municipioArray = selectMunicipio.split("-");
                munPos = Integer.parseInt(municipioArray[0]);

                if(!entidadBase.equals("") && !municipioBase.equals("")){
                    entidadPos = Integer.parseInt(entidadBase);
                    munPos = Integer.parseInt(municipioBase);
                }

                //Toast.makeText(this, municipioArray[0], Toast.LENGTH_SHORT).show();
                nameListloc = new ArrayList<String>();
                insertaU.abrir();
                cur = insertaU.localidad(Integer.toString(entidadPos), Integer.toString(munPos));
                nameListloc.add("0-LOCALIDAD");
                while (cur.moveToNext()) {
                    nameListloc.add(cur.getString(1) + "-" + cur.getString(2));//id
                }
                cur.close();
                insertaU.cerrar();


                ArrayAdapter<String> arrayAdaptermun =
                        new ArrayAdapter<String>(this, R.layout.spinner_item, nameListloc);
                arrayAdaptermun.setDropDownViewResource(R.layout.spinner_item);

                // Apply the adapter to the spinner
                this.spLocalidad.setAdapter(arrayAdaptermun);
                selectMunicipio = String.valueOf(spMunicipio.getSelectedItem());
                break;

            case R.id.spLocalidad:
                selectLocalidad = String.valueOf(spLocalidad.getSelectedItem());
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent int1 = new Intent(getApplicationContext(), Entidadpita.class);
            startActivity(int1);
            finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            finish();
            return true;
        }
        return false;
    }
}
