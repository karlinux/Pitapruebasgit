package com.g214.pita;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Imagenespita extends AppCompatActivity {

    private final Handler_sqlite inserta = new Handler_sqlite(this);
    private final Handler_sqlite_puntos insertaU = new Handler_sqlite_puntos(this);
    private final Links l = new Links();
    String fecha = l.getFecha();
    String li = l.getLink();
    String VERSION = l.getVersion();
    String APK = l.getApk();
    String CAMPO = l.getCampo();
    Bundle bolsa;
    TextView tvFecha, tvVersion, tvUsuario;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12;
    String Siguiente, FOLIOENCUESTA, punto, puntoTactico;
    String [] tipoFoto;
    Cursor curPunto, curPuntoTactico;
    Typeface ligt, regular, medio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        tipoFoto = new String[]{"", "Video vigilancia", "Vehículos ligeros", "Vehículos de carga", "Infraestructura auxiliar",
                "Binomio canino", "Carril administrado", "Zona de revisión ", "Control de carga peatonal", "Zona de amarillos de carga",
                "Zona de revisión de carga", "Centro de monitoreo", ""};

        tvFecha = (TextView) findViewById(R.id.tvFecha);
        tvVersion = (TextView) findViewById(R.id.tvVersion);
        tvUsuario = (TextView) findViewById(R.id.tvUsuario);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btn10 = (Button) findViewById(R.id.btn10);
        btn11 = (Button) findViewById(R.id.btn11);
        btn12 = (Button) findViewById(R.id.btn12);

        inserta.abrir();
        curPunto = inserta.punto();
        curPunto.moveToLast();
        punto = curPunto.getString(0);
        inserta.cerrar();

        insertaU.abrir();
        curPuntoTactico = insertaU.punto(punto);
        curPuntoTactico.moveToLast();
        puntoTactico = curPuntoTactico.getString(0);
        curPunto = inserta.punto();
        curPunto.moveToLast();
        FOLIOENCUESTA = curPunto.getString(0);
        insertaU.cerrar();

        tvFecha.setText(fecha);
        tvVersion.setText("Versión " + VERSION + ".0");

        ligt =Typeface.createFromAsset(getAssets(), "fonts/montserratLight.otf");
        regular=Typeface.createFromAsset(getAssets(), "fonts/montserratRegular.otf");
        medio=Typeface.createFromAsset(getAssets(), "fonts/montserratMedium.otf");
        tvFecha.setTypeface(regular);
        tvVersion.setTypeface(ligt);
        tvUsuario.setTypeface(medio);
        //btnIniciar.setTypeface(medio);
        btn1.setTypeface(medio);
        btn2.setTypeface(medio);
        btn3.setTypeface(medio);
        btn4.setTypeface(medio);
        btn5.setTypeface(medio);
        btn6.setTypeface(medio);
        btn7.setTypeface(medio);
        btn8.setTypeface(medio);
        btn9.setTypeface(medio);
        btn10.setTypeface(medio);
        btn11.setTypeface(medio);
        btn12.setTypeface(medio);

        btn1.setText(tipoFoto[1]);
        btn2.setText(tipoFoto[2]);
        btn3.setText(tipoFoto[3]);
        btn4.setText(tipoFoto[4]);
        btn5.setText(tipoFoto[5]);
        btn6.setText(tipoFoto[6]);
        btn7.setText(tipoFoto[7]);
        btn8.setText(tipoFoto[8]);
        btn9.setText(tipoFoto[9]);
        btn10.setText(tipoFoto[10]);
        btn12.setText(tipoFoto[11]);
        btn11.setText("REGRESAR");

        tvUsuario.setText(puntoTactico);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inserta.abrir();
                inserta.actualizaFoto(FOLIOENCUESTA, "1");
                inserta.cerrar();

                Intent intent = new Intent(".Fotopita");
                startActivity(intent);
                finish();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inserta.abrir();
                inserta.actualizaFoto(FOLIOENCUESTA, "2");
                inserta.cerrar();
                Intent intent = new Intent(".Fotopita");
                startActivity(intent);
                finish();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inserta.abrir();
                inserta.actualizaFoto(FOLIOENCUESTA, "3");
                inserta.cerrar();
                Intent intent = new Intent(".Fotopita");
                startActivity(intent);
                finish();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inserta.abrir();
                inserta.actualizaFoto(FOLIOENCUESTA, "4");
                inserta.cerrar();
                Intent intent = new Intent(".Fotopita");
                startActivity(intent);
                finish();
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inserta.abrir();
                inserta.actualizaFoto(FOLIOENCUESTA, "5");
                inserta.cerrar();
                Intent intent = new Intent(".Fotopita");
                startActivity(intent);
                finish();
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inserta.abrir();
                inserta.actualizaFoto(FOLIOENCUESTA, "6");
                inserta.cerrar();
                Intent intent = new Intent(".Fotopita");
                startActivity(intent);
                finish();
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inserta.abrir();
                inserta.actualizaFoto(FOLIOENCUESTA, "7");
                inserta.cerrar();
                Intent intent = new Intent(".Fotopita");
                startActivity(intent);
                finish();
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inserta.abrir();
                inserta.actualizaFoto(FOLIOENCUESTA, "8");
                inserta.cerrar();
                Intent intent = new Intent(".Fotopita");
                startActivity(intent);
                finish();
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inserta.abrir();
                inserta.actualizaFoto(FOLIOENCUESTA, "9");
                inserta.cerrar();
                Intent intent = new Intent(".Fotopita");
                startActivity(intent);
                finish();
            }
        });

        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inserta.abrir();
                inserta.actualizaFoto(FOLIOENCUESTA, "10");
                inserta.cerrar();
                Intent intent = new Intent(".Fotopita");
                startActivity(intent);
                finish();
            }
        });

        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(".Menupita");
                startActivity(intent);
                finish();
            }
        });

        btn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inserta.abrir();
                inserta.actualizaFoto(FOLIOENCUESTA, "11");
                inserta.cerrar();
                Intent intent = new Intent(".Fotopita");
                startActivity(intent);
                finish();
            }
        });


    }
}

