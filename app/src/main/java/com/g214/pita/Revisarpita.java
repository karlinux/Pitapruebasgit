package com.g214.pita;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Revisarpita extends AppCompatActivity {

    private final Handler_sqlite inserta = new Handler_sqlite(this);
    private final Handler_sqliteU insertaU = new Handler_sqliteU(this);
    private final Links l = new Links();
    String fecha = l.getFecha();
    String li = l.getLink();
    String VERSION = l.getVersion();
    String APK = l.getApk();


    String  cuis, jefe, nom, tipo, tienetel, n, r;
    int sinc, seg;
    Button btnSalir;
    Bundle cuices;
    String[] arrayFecha;
    Bundle bolsa;
    Button btnRegresar, btnIniciar;
    private Adaptador adaptador;
    private ListView list;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        Typeface ligt =Typeface.createFromAsset(getAssets(), "fonts/montserratLight.otf");
        Typeface regular=Typeface.createFromAsset(getAssets(), "fonts/montserratRegular.otf");
        Typeface medio=Typeface.createFromAsset(getAssets(), "fonts/montserratMedium.otf");

        btnIniciar = (Button)findViewById(R.id.btnIniciar);
        btnRegresar = (Button)findViewById(R.id.btnRegresar);
        btnIniciar.setVisibility(View.GONE);

        list = (ListView) findViewById(R.id.lvLista);
        adaptador = new Adaptador(this, GetArrayItems());

        list.setAdapter(adaptador);

        list.setClickable(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                bolsa = new Bundle();
                bolsa.putString("folio", String.valueOf(GetArrayItems().get(position).getTitulo()));
                Intent intent = new Intent(getApplicationContext(), FotoDocpita.class);
                intent.putExtras(bolsa);
                startActivity(intent);
                finish();

                //Log.d("Posicion", String.valueOf(GetArrayItems().get(position).getTitulo()));


            }
        });
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(getApplicationContext(), Iniciopita.class);
                startActivity(inte);
                finish();
            }
        });


    }

    private ArrayList<Entidad> GetArrayItems(){
        ArrayList<Entidad> listItems = new ArrayList<>();
        inserta.abrir();
        Cursor cur= inserta.revisar();
        while (cur.moveToNext()) {
            listItems.add(new Entidad(cur.getString(0),cur.getString(1), "Punto Táctico \n"+cur.getString(2)+"\n"+cur.getString(1)));//id
        }
        cur.close();
        inserta.close();
        return listItems;
    }
}

