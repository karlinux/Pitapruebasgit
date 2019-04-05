package com.g214.pita;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class Domiciliopita extends AppCompatActivity {

    private final Handler_sqlite inserta = new Handler_sqlite(this);
    private final Handler_sqliteU insertaU = new Handler_sqliteU(this);
    private final Links l = new Links();
    String fecha = l.getFecha();
    String VERSION = l.getVersion();
    String APK = l.getApk();
    String JSON = l.getLink();

    EditText etCp, etEntidad, etMunicipio, etCalle, etNumExt, etNumInt, etColonia;
    TextView tvVersion;
    Button btnIniciar, btnRegresar;
    Spinner spColonia;
    ArrayList<String> nameList;
    Bundle bolsa;
    String id, n, cp, idusuario, inicio, nommun, textin, cvemun, usuario, nomedo, cveedo, calle, numext, numint, imei, FOLIOENCUESTA, colonia, Regresar;
    int num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domicilio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Regresar = ".Inicio"+ APK;
        inserta.abrir();
        usuario = inserta.usuario();
        idusuario = inserta.idusuario();
        id = inserta.iden();
        fecha = inserta.fecha("encuesta", "FECHAENTREVISTA");
        inserta.cerrar();

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
        imei = telephonyManager.getDeviceId();

        StringBuilder stringInicio = new StringBuilder();
        stringInicio.append(fecha.substring(11,19));

        inicio = stringInicio.toString();

        StringBuilder stringFecha = new StringBuilder();

        stringFecha.append(imei);
        stringFecha.append(fecha.substring(0,4));
        stringFecha.append(fecha.substring(5,7));
        stringFecha.append(fecha.substring(8,10));
        stringFecha.append(fecha.substring(11,13));
        stringFecha.append(fecha.substring(14,16));
        stringFecha.append(fecha.substring(17,19));
        stringFecha.append(id);


        FOLIOENCUESTA = stringFecha.toString();



        tvVersion = (TextView) findViewById(R.id.tvVersion);
        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        etCp = (EditText) findViewById(R.id.etCp);
        etCalle = (EditText) findViewById(R.id.etCalle);
        etNumExt = (EditText) findViewById(R.id.etNumExt);
        etColonia = (EditText) findViewById(R.id.etColonia);
        etNumInt = (EditText) findViewById(R.id.etNumInt);
        etEntidad = (EditText) findViewById(R.id.etEntidad);
        etMunicipio = (EditText) findViewById(R.id.etMunicipio);
        spColonia = (Spinner) findViewById(R.id.spColonia);
        etColonia.setVisibility(View.GONE);

        etEntidad.setKeyListener(null);
        etMunicipio.setKeyListener(null);
        etEntidad.setFocusable(false);
        etMunicipio.setFocusable(false);

        tvVersion.setText("I. IDENTIFICACIÓN DE LA EMPRESA Y \n   DATOS DE LA ENTREVISTA");
        //tvDialogo.setText(String.format(getString(R.string.dialogo), usuario));


        /*Typeface ligt =Typeface.createFromAsset(getAssets(), "fonts/montserratLight.otf");
        Typeface regular=Typeface.createFromAsset(getAssets(), "fonts/montserratRegular.otf");
        Typeface medio=Typeface.createFromAsset(getAssets(), "fonts/montserratMedium.otf");

        tvVersion.setTypeface(medio);
        btnIniciar.setTypeface(medio);
        btnRegresar.setTypeface(medio);

*/
        colonia = "";
        spColonia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                colonia = String.valueOf(spColonia.getSelectedItem());

                if(colonia.equals("SELECCIONE COLONIA")) {
                    etColonia.setVisibility(View.VISIBLE);
                }else{
                    etColonia.setVisibility(View.GONE);
                }
                //Toast.makeText(Domicilioaduanas.this, colonia, Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        etCp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                cp = etCp.getText().toString();
                char [] arrayrfc = cp.toCharArray();

                if(arrayrfc.length > 3 && !b){
                    Votos nuevaTarea = new Votos();
                    String[] unarreglo = {cp};
                    nuevaTarea.execute(unarreglo);
                }

            }
        });
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(Regresar);
                startActivity(inte);
                finish();
            }
        });
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calle = etCalle.getText().toString();
                numext = etNumExt.getText().toString();
                numint = etNumInt.getText().toString();

                String error = "";
                if(cp.equals("")){
                    error = "INGRESE SU CÓDIGO POSTAL ";
                }else if(calle.equals("")){
                    error = "INGRESE CALLE";
                }else if(numext.equals("")){
                    error = "INGRESE NÚMERO EXTERIOR";
                }else if(colonia.equals("SELECCIONE COLONIA") && etColonia.getText().toString().equals("")){
                    error = "AGREGUE LA COLONIA";
                }else{
                    error = "";
                }

                if(error.equals("")) {

                    if(colonia.equals("SELECCIONE COLONIA")){
                        colonia = etColonia.getText().toString();
                    }
                    inserta.abrir();
                    inserta.actualizaDomicilio(id, cp, calle, numext, numint, cveedo, cvemun, FOLIOENCUESTA, imei, colonia, inicio, usuario, idusuario);
                    inserta.cerrar();
                    Intent intent = new Intent(getApplicationContext(), Entidadpita.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    class Votos extends AsyncTask<String, Void, Void> {
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

                HttpPost httppost = new HttpPost(JSON + "json.php");
                MultipartEntity mpEntity = new MultipartEntity();
                mpEntity.addPart("cp", new StringBody(cp));
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
            pDialog = new ProgressDialog(Domiciliopita.this);
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
        nameList = new ArrayList<String>();

            nameList.add("SELECCIONE COLONIA");
        for (int i = 0; i < jsonobject.length(); i++) {
            JSONObject pru = jsonobject.getJSONObject(i);
            nameList.add( pru.getString("d_asenta")); //id
            cvemun = pru.getString("c_mnpio");
            nommun = pru.getString("D_mnpio");
            nomedo = pru.getString("d_estado");
            cveedo = pru.getString("c_estado");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, nameList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);

        // Apply the adapter to the spinner
        this.spColonia.setAdapter(arrayAdapter);
        etEntidad.setText(cveedo+"-"+nomedo);
        etMunicipio.setText(cvemun+"-"+nommun);

    }
}

