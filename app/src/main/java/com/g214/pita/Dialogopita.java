package com.g214.pita;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Dialogopita extends AppCompatActivity {

    private final Handler_sqlite inserta = new Handler_sqlite(this);
    private final Handler_sqliteU insertaU = new Handler_sqliteU(this);
    private final Links l = new Links();
    String fecha = l.getFecha();
    String li = l.getLink();
    String VERSION = l.getVersion();
    String APK = l.getApk();

    EditText etCuis;
    TextView tvVersion;
    Button btnIniciar, btnRegresar;
    Bundle bolsa;
    String n, Inicio, Siguiente, textin, imeistring, usuario;
    int num;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        Inicio = ".Inicio" + APK;
        Siguiente = ".Buscar" + APK;


        inserta.abrir();
        usuario = inserta.usuario();
        inserta.cerrar();


        tvVersion = (TextView) findViewById(R.id.tvVersion);
        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);


        tvVersion.setText("I. IDENTIFICACIÓN DE LA EMPRESA Y \n   DATOS DE LA ENTREVISTA");
        //tvDialogo.setText(String.format(getString(R.string.dialogo), usuario));

        String htmlText = " %s ";
        String myData = "";
        myData ="" +
                "<string name=\"hello\">" +
                "<html>" +
                " <head>" +
                "<style type=\"text/css\">" +
                "@font-face {" +
                "    font-family: MyFont;" +
                "    src: url(\"fonts/montserratAlternatesSemiBold.otf\")" +
                "}" +
                "body {" +
                "    font-family: MyFont;" +
                "}" +
                "</style>" +
                "</head>" +
                " <body>" +
                "<p style=\"text-align:center;font-size:16px;color:#9D2449\">PRESENTACIÓN</p>"+
                "<p style=\"text-align:justify;font-size:12px;\">Buen día, mi nombre es %s y " +
                "trabajo para G214, una empresa contratada por la Administración General de Aduanas (AGA) " +
                "para supervisar los avances en las obras y acciones contratadas a través del Proyecto de " +
                "Integración Tecnológica Aduanera (PITA), esta supervisión permitirá tomar decisiones para " +
                "que, en el menor tiempo posible, se concluya con la modernización de las aduanas. Los datos " +
                "que nos proporcione sólo se usarán para fines estadísticos y con estricta confidencialidad en " +
                "apego a lo que señala la Ley Federal de Protección de Datos Personales en Posesión de los " +
                "Particulares y no implica ninguna responsabilidad para los usted ni las personas involucradas en PITA.</p>"+
                " </body>" +
                "</html>" +
                "</string>" +
                "";
        WebView webView = (WebView) findViewById(R.id.webView1);
        webView.loadDataWithBaseURL("file:///android_asset/",String.format(myData, usuario),"text/html","utf-8",null);
        //webView.loadData(String.format(myData, usuario), "text/html; charset=utf-8", "utf-8");
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);

/*
        Typeface ligt =Typeface.createFromAsset(getAssets(), "fonts/montserratLight.otf");
        Typeface regular=Typeface.createFromAsset(getAssets(), "fonts/montserratRegular.otf");
        Typeface medio=Typeface.createFromAsset(getAssets(), "fonts/montserratMedium.otf");
        tvVersion.setTypeface(medio);
        btnIniciar.setTypeface(medio);
        btnRegresar.setTypeface(medio);
*/
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(Inicio);
                startActivity(inte);
                finish();
            }
        });
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Siguiente);
                startActivity(intent);
                finish();

            }
        });
    }
}

