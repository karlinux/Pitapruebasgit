package com.g214.pita;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Entrecallespita extends AppCompatActivity {

    private final Handler_sqlite inserta = new Handler_sqlite(this);
    private final Handler_sqliteU insertaU = new Handler_sqliteU(this);
    private final Links l = new Links();
    //String fecha = l.getFecha();
    String VERSION = l.getVersion();
    String APK = l.getApk();
    String JSON = l.getLink();

    EditText etCalle1, etCalle2;
    TextView tvVersion, tvEntre;
    Button btnIniciar, btnRegresar;
    ArrayList<String> nameList;
    String id, n, calle1, calle2;
    int num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrecalles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        inserta.abrir();
        id = inserta.iden();
        inserta.cerrar();

        tvVersion = (TextView) findViewById(R.id.tvVersion);
        tvEntre = (TextView) findViewById(R.id.tvEntre);

        etCalle1 = (EditText)findViewById(R.id.etCalle1);
        etCalle2 = (EditText)findViewById(R.id.etCalle2);
        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        //spColonia.setVisibility(View.GONE);


        tvVersion.setText("I. IDENTIFICACIÓN DE LA EMPRESA Y \n   DATOS DE LA ENTREVISTA");
        //tvDialogo.setText(String.format(getString(R.string.dialogo), usuario));


        Typeface ligt =Typeface.createFromAsset(getAssets(), "fonts/montserratLight.otf");
        Typeface regular=Typeface.createFromAsset(getAssets(), "fonts/montserratRegular.otf");
        Typeface medio=Typeface.createFromAsset(getAssets(), "fonts/montserratMedium.otf");

        tvVersion.setTypeface(medio);
        tvEntre.setTypeface(medio);
        tvEntre.setTextColor(Color.parseColor("#65646A"));
        etCalle1.setTextColor(Color.parseColor("#65646A"));
        etCalle2.setTextColor(Color.parseColor("#65646A"));
        btnIniciar.setTypeface(medio);
        btnRegresar.setTypeface(medio);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent inte = new Intent(getApplicationContext(), Razonaduanas.class);
                //startActivity(inte);
                finish();
            }
        });
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calle1 = etCalle1.getText().toString();
                calle2 = etCalle2.getText().toString();

                String error = "";
                if(calle1.equals("")){
                    error = "INGRESE CALLE 1 ";
                }else if(calle2.equals("")){
                    error = "INGRESE CALLE 2";
                }else{
                    error = "";
                }

                if(error.equals("")) {

                    inserta.abrir();
                    inserta.actualizaCalles(id, calle1, calle2);
                    Intent intent = new Intent(getApplicationContext(), Preguntapita.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}

