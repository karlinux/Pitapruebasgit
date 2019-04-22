package com.g214.pita;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by carlos on 08/09/17.
 */

public class Buscarpita extends AppCompatActivity {

    private final Handler_sqlite inserta = new Handler_sqlite(this);
    private final Handler_sqlite_puntos insertaC = new Handler_sqlite_puntos(this);
    private final Links l = new Links();

    String li = l.getLinkSinc();
    String fech = l.getFecha();
    TextView tvFecha, tvNombre;
    EditText etNombre;

    ListView list;
    String n, r, imeistring, puntoTactico;
    int sinc, seg;
    Button btnSalir, btnSiguiente;
    Bundle cuices, nombre;
    String[] fecha;
    ArrayList<String> nameList;
    Cursor puntoTac;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        imeistring = telephonyManager.getDeviceId();
        r="";

        seg=0;
        btnSalir = (Button) findViewById(R.id.btnSalir);
        btnSiguiente = (Button) findViewById(R.id.btnIniciar);

        btnSalir.setText("REGRESAR");

        etNombre = (EditText) findViewById(R.id.etNombre);
        //tvNombre = (TextView) findViewById(R.id.tvNombre);
        etNombre.setHint("PUNTO TÁCTICO");

        etNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Adaptador();
            }
        });


        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getApplicationContext(), Iniciopita.class);
                startActivity(in);
                finish();
            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                puntoTactico = etNombre.getText().toString().trim();
                if(!etNombre.getText().toString().equals("")) {
                    inserta.abrir();
                    inserta.insertarReg(etNombre.getText().toString(), imeistring, "1");
                    inserta.cerrar();
                    Intent in = new Intent(getApplicationContext(), Domiciliopita.class);
                    startActivity(in);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "INGRESE EL PUNTO TÁCTICO", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void Adaptador() {

        list = (ListView) findViewById(R.id.lvLista);
        nameList = new ArrayList<String>();
        String nombre = etNombre.getText().toString();


        insertaC.abrir();
        Cursor cur= insertaC.buscarnombre("%"+nombre+"%");
        while (cur.moveToNext()) {

            nameList.add(cur.getString(1)+"-\n"+cur.getString(2)+" "+cur.getString(3)+" "+cur.getString(4));
        }
        cur.close();
        insertaC.close();

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, nameList);
        //Set The Adapter
        list.setAdapter(arrayAdapter);


        list.setClickable(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String item = ((TextView)view).getText().toString();

                String[] persona = item.split("-");

                String person = persona[0];

                inserta.abrir();
                inserta.insertarPunto(person);
                puntoTac = inserta.puntoTactico(person);

                if(puntoTac.getCount()<1){
                    inserta.insertarReg(person, imeistring, "1");
                }
                inserta.cerrar();

                Intent intent = new Intent(".Menupita");
                startActivity(intent);
                finish();

                //Toast.makeText(getBaseContext(), inte, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent int1 = new Intent(this, MainActivity.class);
            startActivity(int1);
            finish();
            return true;
        }
        return false;
    }


}

