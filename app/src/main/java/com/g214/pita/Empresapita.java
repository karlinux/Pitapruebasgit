package com.g214.pita;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Empresapita extends AppCompatActivity {

    private final Handler_sqlite inserta = new Handler_sqlite(this);
    private final Handler_sqliteU insertaU = new Handler_sqliteU(this);
    private final Links l = new Links();
    String fecha = l.getFecha();
    String li = l.getLink();
    String VERSION = l.getVersion();
    String APK = l.getApk();
    String CAMPO = l.getCampo();

    EditText etRazon;
    TextView tvVersion;
    Button btnGuardar, btnRegresar;
    Bundle bolsa;
    String n, error, identificador, textin, imeistring, usuario, id, estado, Siguiente;
    //Intents
    String Dialogo, Domicilio;
    int num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        Dialogo = ".Dialogo" + APK;
        Domicilio =".Domicilio" + APK;

        if(CAMPO.equals("1")){
            Siguiente=".Gps" + APK;

        }else{
            Siguiente=".Preguntapunto" + APK;
        }

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
        //dm = Build.MANUFACTURER;
        //Toast.makeText(this, dm, Toast.LENGTH_LONG).show();
        imeistring = telephonyManager.getDeviceId();

        inserta.abrir();
        usuario = inserta.usuario();
        id = inserta.iden();
        inserta.cerrar();

        tvVersion = (TextView) findViewById(R.id.tvVersion);
        btnGuardar = (Button) findViewById(R.id.btnIniciar);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        etRazon = (EditText) findViewById(R.id.etRazon);

        inserta.abrir();
        n = inserta.guardado2();
        inserta.cerrar();
        //Toast.makeText(this, n, Toast.LENGTH_SHORT).show();



        if(n.equals("1")){
            etRazon.setHint("Empresa responsable de Obra");
            error = "INGRESE EL NOMBRE DE LA EMPRESA";
        }else{
            error = "INGRESE EL NOMBRE RECINTO ADUANAL";
                etRazon.setHint("Recinto Aduanal");
            Domicilio = ".Empresa" + APK;
        }

        if(n.equals("3")){
                error = "INGRESE EL NOMBRE DEL RESPONSABLE";
                Domicilio = ".Empresa" + APK;

            if(CAMPO.equals("1")){
                Dialogo = ".Foto" + APK;

            }else{
                Dialogo = ".Entidad" + APK;
            }

            estado = "2";
            etRazon.setHint("Nombre del responsable");

        }else if(n.equals("4")){
            estado = "3";
                error = "INGRESE EL CORREO";
            etRazon.setHint("Correo");
            etRazon.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            Dialogo = ".Empresa" + APK;
            Domicilio = Siguiente;

        }
        tvVersion.setText("I. IDENTIFICACIÓN DE LA EMPRESA Y \n   DATOS DE LA ENTREVISTA");
        //tvDialogo.setText(String.format(getString(R.string.dialogo), usuario));


        Typeface ligt =Typeface.createFromAsset(getAssets(), "fonts/montserratLight.otf");
        Typeface regular=Typeface.createFromAsset(getAssets(), "fonts/montserratRegular.otf");
        Typeface medio=Typeface.createFromAsset(getAssets(), "fonts/montserratMedium.otf");

        tvVersion.setTypeface(medio);
        btnGuardar.setTypeface(medio);
        btnRegresar.setTypeface(medio);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!CAMPO.equals("1")){
                inserta.abrir();
                inserta.actualizaGuardado(id, estado);
                inserta.cerrar();
                }

                Intent inte = new Intent(Dialogo);
                startActivity(inte);
                finish();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!etRazon.getText().toString().equals("")) {

                    //if (!validarEmail(etRazon.getText().toString()) && n.equals("4")){
                    //    Toast.makeText(Empresapita.this, "Ingrese un correo válido", Toast.LENGTH_SHORT).show();
                    //}else {
                        inserta.abrir();
                        switch (n) {
                            case "":
                            case "0":
                                inserta.insertarReg(etRazon.getText().toString(), imeistring, "2");
                                error = "INGRESE ";
                                break;
                            case "1":
                                inserta.actualzaRespuesta(id, etRazon.getText().toString(), "EMPRESA", "1");
                                break;
                            case "3":
                                inserta.actualzaRespuesta(id, etRazon.getText().toString(), "RESPONSABLEPITA", "4");
                                break;
                            case "4":
                                inserta.actualzaRespuesta(id, etRazon.getText().toString(), "CORREO", "5");
                                break;
                        }
                        inserta.cerrar();

                        Intent intent = new Intent(Domicilio);
                        startActivity(intent);
                        finish();
                   // }
                }else{
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}

