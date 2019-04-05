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

public class Detallepita extends AppCompatActivity {

    private final Handler_sqlite inserta = new Handler_sqlite(this);
    private final Handler_sqliteU insertaU = new Handler_sqliteU(this);
    private final Links l = new Links();
    String fecha = l.getFecha();
    String li = l.getLink();
    String VERSION = l.getVersion();
    String APK = l.getApk();
    Bundle bolsa, bolsaid;


    String  folio, n, r;
    int sinc, numpregunta;
    Button btnSalir;
    Bundle cuices;
    String[] arrayFecha, PREGUNTAS, IDPREGUNTAS;
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
        getIntent().removeExtra("id");
        setSupportActionBar(toolbar);
        PREGUNTAS = getResources().getStringArray(R.array.preguntas);
        IDPREGUNTAS = new String[]{"","2.1", "2.2", "2.3", "2.4", "2.5", "2.6", "2.7", "2.8", "2.9","2.10",
                "2.11", "2.12", "2.13", "2.14", "2.15", "2.16", "2.17", "2.18", "2.19", "2.20", "2.21", "2.22", "2.23",
                "2.24", "2.25", "2.26", "2.27", "2.28", "2.29", "2.30", "2.31", "2.32", "3.1", "3.2", "3.3", "3.4", "4.1",
                "4.2", "4.3", "4.4", "4.5"};
        bolsa = getIntent().getExtras();
        folio = bolsa.getString("folio");

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
                String numero = toString().valueOf(GetArrayItems().get(position).getId());

                inserta.abrir();
                numpregunta = Integer.parseInt(inserta.NUMPREGUNTA(numero));
                inserta.cerrar();
                switch (numpregunta){

                    case 5:// 2.5
                    case 9:// 2.9
                    case 13:// 2.13
                    case 33://3.01
                    case 34://3.02
                    case 35://3.03
                    case 36://3.04

                        bolsaid = new Bundle();
                        bolsaid.putString("id", String.valueOf(GetArrayItems().get(position).getId()));

                        //Toast.makeText(Detalleduana.this, String.valueOf(GetArrayItems().get(position).getId()), Toast.LENGTH_SHORT).show();
                        //Intent inten = new Intent(getApplicationContext(), EditarMultiaduana.class);
                        //inten.putExtras(bolsaid);
                        //inten.putExtras(bolsa);
                        //startActivity(inten);
                        finish();

                        break;
                    default:


                        bolsaid = new Bundle();
                        bolsaid.putString("id", String.valueOf(GetArrayItems().get(position).getId()));

                        //Intent intent = new Intent(getApplicationContext(), Editaraduana.class);
                        //intent.putExtras(bolsaid);
                        //intent.putExtras(bolsa);
                        //startActivity(intent);
                        finish();

                        break;
                }

                //Toast.makeText(Detalleduana.this, String.valueOf(GetArrayItems().get(position).getId()), Toast.LENGTH_SHORT).show();



            }
        });
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(getApplicationContext(), Revisarpita.class);
                startActivity(inte);
                finish();
            }
        });
    }

    private ArrayList<Entidad> GetArrayItems(){
        ArrayList<Entidad> listItems = new ArrayList<>();
        Cursor cur= inserta.respuestas(folio);
        while (cur.moveToNext()) {
            listItems.add(new Entidad(cur.getString(0), cur.getString(1),
                    IDPREGUNTAS[Integer.parseInt(cur.getString(3))] + PREGUNTAS[Integer.parseInt(cur.getString(3))]));//id
        }
        cur.close();
        inserta.close();
        return listItems;
    }
}

