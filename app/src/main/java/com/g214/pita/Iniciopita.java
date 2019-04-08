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

public class Iniciopita extends AppCompatActivity {

    private final Handler_sqlite inserta = new Handler_sqlite(this);
    private final Handler_sqliteU insertaU = new Handler_sqliteU(this);
    private final Links l = new Links();
    String fecha = l.getFecha();
    String li = l.getLink();
    String VERSION = l.getVersion();
    String APK = l.getApk();
    String CAMPO = l.getCampo();

    TextView tvFecha, tvVersion, tvUsuario;
    Button btnIniciar, btnRevisar, btnSincronizar, btnGeneral;
    Bundle bolsa;
    String n, inclu, Universos, textin, imeistring, usuario, dm, Sincroniza, Siguiente, Siguiente1, Siguiente2;
    int num, inicio;
    Typeface ligt, regular, medio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        if(CAMPO.equals("1")){
            Siguiente1=".Foto" + APK;
            Siguiente2=".Gps" + APK;
        }else{
            Siguiente1=".Empresa" + APK;
            Siguiente2=".Preguntapunto" + APK;
        }

        Universos = ".Universos"+ APK;
        Sincroniza = ".Sincroniza"+ APK;
        Siguiente = ".Dialogo"+ APK;

        inicio = 0;

        if(inicio == 1) {
            Intent intente = new Intent(getApplicationContext(), Preguntapita.class);
            startActivity(intente);
            finish();
        }

        inserta.abrir();
        usuario = inserta.usuario();
        inserta.cerrar();

        tvFecha = (TextView) findViewById(R.id.tvFecha);
        tvVersion = (TextView) findViewById(R.id.tvVersion);
        tvUsuario = (TextView) findViewById(R.id.tvUsuario);
        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        btnRevisar = (Button) findViewById(R.id.btnRevisar);
        btnGeneral = (Button) findViewById(R.id.btnGeneral);
        btnSincronizar = (Button) findViewById(R.id.btnSincronizar);

        tvFecha.setText(fecha);
        tvVersion.setText("Versión " + VERSION + ".0");
        tvUsuario.setText(usuario);

        ligt =Typeface.createFromAsset(getAssets(), "fonts/montserratLight.otf");
        regular=Typeface.createFromAsset(getAssets(), "fonts/montserratRegular.otf");
        medio=Typeface.createFromAsset(getAssets(), "fonts/montserratMedium.otf");
        tvFecha.setTypeface(regular);
        tvVersion.setTypeface(ligt);
        tvUsuario.setTypeface(medio);
        //btnIniciar.setTypeface(medio);
        btnRevisar.setTypeface(medio);
        btnSincronizar.setTypeface(medio);

        insertaU.abrir();
        Cursor cur = insertaU.cursor();
        num = cur.getCount();
        insertaU.cerrar();

        if(num<1){
            Intent intent = new Intent(Universos);
            startActivity(intent);
            finish();
        }

        inserta.abrir();
        n = inserta.guardado2();
        inserta.cerrar();

        //todo Toast.makeText(this, n, Toast.LENGTH_SHORT).show();

        if (n.equals("1")) {

            Intent intPrin = new Intent(getApplicationContext(), Domiciliopita.class);
            startActivity(intPrin);
            finish();

        } else if (n.equals("2")) {

            Intent intent = new Intent(getApplicationContext(), Entidadpita.class);
            startActivity(intent);
            finish();
        }
        else if (n.equals("3")) {

            Intent intent = new Intent(Siguiente1);
            //Intent intent = new Intent(getApplicationContext(), Empresapita.class);
            startActivity(intent);
            finish();
        }
        else if (n.equals("4")) {

            //Intent intent = new Intent(Siguiente1);
            Intent intent = new Intent(getApplicationContext(), Empresapita.class);
            startActivity(intent);
            finish();
        }

        else if (n.equals("5") && !CAMPO.equals("1")) {

            Intent intent = new Intent(Siguiente2);
            //Intent intent = new Intent(getApplicationContext(), Preguntapuntopita.class);
            startActivity(intent);
            finish();
        }

        btnIniciar.setVisibility(View.GONE);
        //btnRevisar.setVisibility(View.GONE);


        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Dialogopita.class);
                startActivity(intent);
                finish();

            }
        });

        btnRevisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Revisarpita.class);
                startActivity(intent);
                finish();
            }
        });

        btnGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Siguiente);
                startActivity(intent);
                finish();

            }
        });

        btnSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sincroniza);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

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
        imeistring = telephonyManager.getDeviceId();

        String[] unarreglo = {imeistring};

        Version nuevaTarea = new Version();
        nuevaTarea.execute(unarreglo);
    }

    /////// MENU
    public boolean onCreateOptionsMenu(Menu menus) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menus, menus);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.salir:
                inserta.abrir();
                inserta.salir("1");

                Intent inten = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(inten);
                finish();

                inserta.cerrar();

                return true;

            case R.id.sincronizar:
                Intent intent = new Intent(Sincroniza);
                startActivity(intent);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    ////  TASK PARA CONECTARSE CON EL SERVIDOR
    class Version extends AsyncTask<String, Void, Void> {

        String imei = "";

        @Override
        protected Void doInBackground(String... params) {
            imei = params[0];

            try {

                HttpClient httpclient = new DefaultHttpClient();
                httpclient.getParams().setParameter(
                        CoreProtocolPNames.PROTOCOL_VERSION,
                        HttpVersion.HTTP_1_1);

                HttpPost httppost = new HttpPost(li + "login.php");

                MultipartEntity mpEntity = new MultipartEntity();

                mpEntity.addPart("imei", new StringBody(imei));

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
            //pDialog = new ProgressDialog(Iniciopita.this);
            if(conectadoRedMovil() || conectadoWifi()){
                //pDialog.setMessage("INICIANDO ESPERE");
            }else{

                Toast.makeText(getApplicationContext(),"NO ESTA CONECTADO A INTERNET", Toast.LENGTH_LONG).show();
            }
            //pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //pDialog.setCancelable(false);
            //pDialog.show();
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if(textin != null) {

                String[] esplitTextin = textin.split("---");

                if (esplitTextin.length > 2){

                    if (!esplitTextin[1].equals(VERSION) || esplitTextin[2].equals("0") || esplitTextin[3].equals("0")) {

                        //Toast.makeText(getApplicationContext(), esplitEstatus[2], Toast.LENGTH_SHORT).show();
                        Dialogo(esplitTextin[1], esplitTextin[2], esplitTextin[3]);

                    } else {

                    }
            }
            }

            //pDialog.dismiss();
        }
    }

    private void Dialogo(String version, String estatus, String imei) {

        View view = this.getLayoutInflater().inflate(R.layout.dialogo,null);
        TextView tvText =  (TextView) view.findViewById(R.id.tvTexto);
        Button btnDescargar = (Button) view.findViewById(R.id.btnDescargar);
        Button btnSi = (Button) view.findViewById(R.id.btnSi);
        Button btnNo = (Button) view.findViewById(R.id.btnNo);

        btnSi.setVisibility(View.GONE);
        btnNo.setVisibility(View.GONE);

        LinearLayout mLinearLayout1 = (LinearLayout) view.findViewById(R.id.layDialogo);

        dm =  Build.PRODUCT;

        //dm = "C4000";
        if(dm.equals("C4000")) {
            mLinearLayout1.setBackgroundColor(Color.WHITE);
        }

        int numer = Integer.parseInt(VERSION);
        numer = numer+1;
        AlertDialog.Builder builder = new AlertDialog.Builder(this,  R.style.AlertDialogTheme);

        if(estatus.equals("0")){
            tvText.setText(R.string.periodo);

            builder.setView(view)
                    .setCancelable(false)
            ;

            btnDescargar.setText("Cerrar");
            btnDescargar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inserta.abrir();
                    inserta.salir("1");
                    Intent inten = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(inten);
                    finish();
                    inserta.cerrar();
                }
            });
        }else if(imei.equals("0")){

            tvText.setText(R.string.inhabilitar);
            builder.setView(view)
                    .setCancelable(false)
            ;
            btnDescargar.setText("Cerrar");
            btnDescargar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inserta.abrir();
                    inserta.salir("1");
                    Intent inten = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(inten);
                    finish();
                    inserta.cerrar();
                }
            });

        }else{
            tvText.setText(getString(R.string.version)+ version + ".0");
            builder.setView(view)
                    .setCancelable(false)
            ;

            btnDescargar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("http://g214.com.mx/" + APK+".apk");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
        }
        AlertDialog dialog = builder.create();
        dialog.show();

    }

}

