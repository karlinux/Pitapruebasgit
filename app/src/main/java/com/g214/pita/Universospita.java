package com.g214.pita;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Universospita extends AppCompatActivity {
    private final Handler_sqliteU insertaU = new Handler_sqliteU(this);

    private final Links l = new Links();
    String li = l.getLink();
    String carpeta = l.getCarpeta();
    String PATH = l.getPath();
    String fecha = l.getFecha();
    String VERSION = l.getVersion();
    String APK = l.getApk();

    Button btnRegresar;
    TextView tvFecha, tvVersion;
    String Inicio;

    Typeface ligt, regular, medio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        Inicio = ".Inicio" + APK;
        tvFecha = (TextView) findViewById(R.id.tvFecha);
        tvVersion = (TextView) findViewById(R.id.tvVersion);
        tvFecha.setText(fecha);
        tvVersion.setText("Versión " + VERSION + ".0");

        /*ligt = Typeface.createFromAsset(getAssets(), "fonts/montserratLight.otf");
        regular=Typeface.createFromAsset(getAssets(), "fonts/montserratRegular.otf");
        medio=Typeface.createFromAsset(getAssets(), "fonts/montserratMedium.otf");
        tvFecha.setTypeface(regular);
        tvVersion.setTypeface(ligt);
        */

        btnRegresar = (Button)findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Inicio);
                startActivity(intent);
                finish();
            }
        });
        UploaderSu nueva = new UploaderSu();
        nueva.execute();
    }

    class UploaderSu extends AsyncTask<String, Void, Void> {
        ProgressDialog pDialog;

        @Override
        protected Void doInBackground(String... params) {

            String foto = "/data/data/"+ PATH +"/databases/";

            File dir =new File(foto);

            if(!dir.exists()){
                dir.mkdirs();
            }

            try {

                //indico URL al archivo
                String DownloadUrl= li + "universo.php";

                String fileName="universo";

                URL url = new URL(DownloadUrl);
                File file= new File(dir,"universo");

                long startTime = System.currentTimeMillis();


                /* abro una conexion a URL. */
                URLConnection ucon = url.openConnection();

                /*
                 * Defino InputStreams para leer desde la URLConnection.
                 */
                InputStream is = ucon.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);

                /*
                 * leo bytes para el Buffer hasta que no queden mas para leer(-1).
                 */
                ByteArrayBuffer baf = new ByteArrayBuffer(5000);
                int current = 0;
                while ((current = bis.read()) != -1) {
                    baf.append((byte) current);
                }

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(baf.toByteArray());
                fos.flush();
                fos.close();

                Log
                        .d("Descarga", "descarga lista en"  + ((System.currentTimeMillis() - startTime) / 1000) + " segundos");

            } catch (IOException e) {
                Log.d("Descarga", "Error: " + e);
            }


            return null;
        }

        protected Boolean conectadoWifi() {
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

        protected Boolean conectadoRedMovil() {
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
            pDialog = new ProgressDialog(Universospita.this);
            if (conectadoRedMovil() || conectadoWifi()) {
                pDialog.setMessage("DESCARGANDO ESPERE");
            } else {

                Toast.makeText(getApplicationContext(), "NO ESTA CONECTADO A INTERNET", Toast.LENGTH_LONG).show();
            }
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //Toast.makeText(MainActivity.this, imeistring, Toast.LENGTH_SHORT).show();

            pDialog.dismiss();
        }
    }
}
