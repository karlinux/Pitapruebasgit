package com.g214.pita;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Menupita extends AppCompatActivity {

    private final Handler_sqlite inserta = new Handler_sqlite(this);
    private final Handler_sqlite_puntos insertaU = new Handler_sqlite_puntos(this);
    private final Links l = new Links();
    String fecha = l.getFecha();
    String li = l.getLink();
    String VERSION = l.getVersion();
    String APK = l.getApk();
    String CAMPO = l.getCampo();
    String JSON = l.getLink();

    Bundle bolsa;
    TextView tvFecha, tvVersion, tvUsuario;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12;
    String textin, idbase, punto, puntoTactico;
    Cursor curPunto, curPuntoTactico;
    Typeface ligt, regular, medio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

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
        btn9.setVisibility(View.GONE);
        btn10.setVisibility(View.GONE);
        btn11.setVisibility(View.GONE);
        btn12.setVisibility(View.GONE);

        inserta.abrir();
        curPunto = inserta.punto();
        curPunto.moveToLast();
        punto = curPunto.getString(0);
        inserta.cerrar();

        insertaU.abrir();
        curPuntoTactico = insertaU.punto(punto);
        curPuntoTactico.moveToLast();
        puntoTactico = curPuntoTactico.getString(0);
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

        btn1.setText("GENERAL");
        btn2.setText("VIDEO VIGILANCIA");
        btn3.setText("VEHICULOS LIGEROS");
        btn4.setText("VEHICULOS DE CARGA");
        btn5.setText("INFRAEXTRUCTURA AUXILIAR Y OTROS");
        btn6.setText("XX");
        btn7.setText("REGRESAR");
        btn8.setText("FOTOS VIDEO VIGILANCIA");

        tvUsuario.setText(puntoTactico);
        btn6.setVisibility(View.GONE);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                inserta.abrir();
                inserta.actualizaPunto(punto, puntoTactico);
                inserta.cerrar();

                bolsa = new Bundle();
                bolsa.putString("numpregunta", "0");
                Intent intent = new Intent(".Domiciliopita");
                intent.putExtras(bolsa);
                startActivity(intent);
                finish();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bolsa = new Bundle();
                bolsa.putString("numpregunta", "0");
                Intent intent = new Intent(".Multipuntopita");
                intent.putExtras(bolsa);
                startActivity(intent);
                finish();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bolsa = new Bundle();
                bolsa.putString("numpregunta", "4");
                Intent intent = new Intent(".Multipuntopita");
                intent.putExtras(bolsa);
                startActivity(intent);
                finish();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bolsa = new Bundle();
                bolsa.putString("numpregunta", "27");
                Intent intent = new Intent(".Multipuntopita");
                intent.putExtras(bolsa);
                startActivity(intent);
                finish();
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bolsa = new Bundle();
                bolsa.putString("numpregunta", "102");
                Intent intent = new Intent(".Multipuntopita");
                intent.putExtras(bolsa);
                startActivity(intent);
                finish();
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserta.abrir();
                inserta.actualizaPita();
                inserta.cerrar();

                Intent intent = new Intent(".Iniciopita");
                startActivity(intent);
                finish();
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserta.abrir();
                inserta.actualizaFotos(punto, "1");
                inserta.cerrar();

                Intent intent = new Intent(".Fotocarrilespita");
                startActivity(intent);
                finish();

               // Intent intent = new Intent(".Imagenespita");
               // startActivity(intent);
            }
        });


    }

    class Carriles extends AsyncTask<String, Void, Void> {
        ProgressDialog pDialog;
        String id = "";

        @Override
        protected Void doInBackground(String... params) {
            id = params[0];

            try {
                HttpClient httpclient = new DefaultHttpClient();
                httpclient.getParams().setParameter(
                        CoreProtocolPNames.PROTOCOL_VERSION,
                        HttpVersion.HTTP_1_1);

                HttpPost httppost = new HttpPost(JSON + "jsonpuntos.php");
                MultipartEntity mpEntity = new MultipartEntity();
                mpEntity.addPart("cp", new StringBody(idbase));
                httppost.setEntity(mpEntity);
                HttpResponse resp = httpclient.execute(httppost);
                HttpEntity ent = resp.getEntity();/* y obtenemos una respuesta */
                textin = EntityUtils.toString(ent);
                httpclient.getConnectionManager().shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        protected Boolean conectadoWifi(){
            ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (info != null) {
                    if (info.isConnected()) {
                        return true;
                    }
                }
            }
            return false;
        }
        protected Boolean conectadoRedMovil(){
            ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (info != null) {
                    if (info.isConnected()) {
                        return true;
                    }
                }
            }
            return false;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Menupita.this);
            if(conectadoRedMovil() || conectadoWifi()){
                pDialog.setMessage("INICIANDO ESPERE");
            }else{

                Toast.makeText(getApplicationContext(),"NO ESTA CONECTADO A INTERNET", Toast.LENGTH_LONG).show();
            }
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //Toast.makeText(getApplicationContext(), textin, Toast.LENGTH_LONG).show();
            if(textin!=null){
                if (textin.equals("")) {
                    Toast.makeText(getApplicationContext(), "ERROR AL SINCRONIZAR", Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(getApplicationContext(), textin, Toast.LENGTH_LONG).show();


                    try {
                        databases(textin);

                        //Toast.makeText(getApplicationContext(), textin, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }else{
                Toast.makeText(getApplicationContext(), textin, Toast.LENGTH_LONG).show();
            }
            pDialog.dismiss();
        }
    }
    public void databases(String json) throws JSONException {
        JSONArray jsonobject = new JSONArray(json);


        for (int i = 0; i < jsonobject.length(); i++) {
            JSONObject pru = jsonobject.getJSONObject(i);

           // cvemun = pru.getString("id");
           // nommun = pru.getString("IDPUNTO");
           // nomedo = pru.getString("PUNTO");
           // cveedo = pru.getString("NOMBRE_CARRIL");
           // cveedo = pru.getString("TIPO_CARRIL");
        }
    }
}

