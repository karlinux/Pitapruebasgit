package com.g214.pita;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by carlos on 08/09/17.
 */

public class Sincronizapita extends AppCompatActivity {

    private final Handler_sqlite inserta = new Handler_sqlite(this);

    private final Links l = new Links();
    String li = l.getLinkSinc();
    String carpeta = l.getCarpeta();
    String APK = l.getApk();
    String PATH = l.getPath();
    String textin, imeistring, imagen, imagenes, foto1, Inicio,  nombreFoto, modificar, Sincroniza;
    String enviar = "0";
    int sinc, numdatos ;
    Button btnSalir;
    String fecha;

    private Adaptador adaptador;
    ListView list;
    private int nameFoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sincronizar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        modificar = enviar.equals("1")?"0":"1";

        Inicio = ".Inicio" + APK;
        Sincroniza = ".Sincroniza"+ APK;

        btnSalir = (Button) findViewById(R.id.btnSalir);

        list = (ListView) findViewById(R.id.lvLista);
        adaptador = new Adaptador(this, GetArrayItems());

        list.setAdapter(adaptador);


        inserta.abrir();

        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        imeistring = telephonyManager.getDeviceId();

        imagen = Environment.getExternalStorageDirectory() + "/"+ carpeta +"/";

        imagenes = "/data/data/" + PATH + "/imagenes.jpg";

        inserta.abrir();
        Cursor datos = inserta.enviar(enviar);
        numdatos = datos.getCount();
        sinc = numdatos;
        //Toast.makeText(this, Integer.toString(numdatos), Toast.LENGTH_SHORT).show();
        if(numdatos!=0){
            while(datos.moveToNext()){

                Uploader nuevaTarea = new Uploader();

                String correo ="";
                String responsable ="";

                correo = datos.getString(24)==null? "-":datos.getString(24);

                if(datos.getString(23)== null){
                    responsable = "-";
                }else{
                    responsable = datos.getString(23);
                }

                String[] unarreglo = {
                        datos.getString(0)==null? "-":datos.getString(0),
                        datos.getString(1)==null? "-":datos.getString(1),
                        datos.getString(2)==null? "-":datos.getString(2),
                        datos.getString(3)==null? "-":datos.getString(3),
                        datos.getString(4)==null? "-":datos.getString(4),
                        datos.getString(5)==null? "-":datos.getString(5),
                        datos.getString(6)==null? "-":datos.getString(6),
                        datos.getString(7)==null? "-":datos.getString(7),
                        datos.getString(8)==null? "-":datos.getString(8),
                        datos.getString(9)==null? "-":datos.getString(9),
                        datos.getString(10)==null? "-":datos.getString(10),
                        datos.getString(11)==null? "-":datos.getString(11),
                        datos.getString(12)==null? "-":datos.getString(12),
                        datos.getString(13)==null? "-":datos.getString(13),
                        datos.getString(14)==null? "-":datos.getString(14),
                        datos.getString(15)==null? "-":datos.getString(15),
                        datos.getString(16)==null? "-":datos.getString(16),
                        datos.getString(17)==null? "-":datos.getString(17),
                        datos.getString(18)==null? "-":datos.getString(18),
                        datos.getString(19)==null? "-":datos.getString(19),
                        datos.getString(20)==null? "-":datos.getString(20),
                        datos.getString(21)==null? "-":datos.getString(21),
                        datos.getString(22)==null? "-":datos.getString(22),
                        datos.getString(23)==null? "-":datos.getString(23),
                        datos.getString(24)==null? "-":datos.getString(24)
                };
/*
                String[] unarreglo = {
                        datos.getString(0),
                        datos.getString(1),
                        datos.getString(2),
                        datos.getString(3),
                        datos.getString(4),
                        datos.getString(5),
                        datos.getString(6),
                        datos.getString(7),
                        datos.getString(8),
                        datos.getString(9),
                        datos.getString(10),
                        datos.getString(11),
                        datos.getString(12),
                        datos.getString(13),
                        datos.getString(14),
                        datos.getString(15),
                        datos.getString(16),
                        datos.getString(17),
                        datos.getString(18),
                        datos.getString(19),
                        datos.getString(20),
                        datos.getString(21),
                        datos.getString(22),
                        correo,
                        responsable
                };
*/
                nuevaTarea.execute(unarreglo);
            }

        }else{

            Cursor respuestas = inserta.enviarImagenes(enviar);
            numdatos = respuestas.getCount();
            sinc = numdatos;

            if(numdatos!=0) {
                while (respuestas.moveToNext()) {
                    Uprespuestas tareaRespuestas = new Uprespuestas();

                    foto1  = imagen + respuestas.getString(1);

                    File file = new File(foto1);
                    Log.d("IMAGENSINCRO", foto1);
                    if(file.exists()) {
                        //foto1 = imagenes;

                        String carril = "";
                        if (respuestas.getString(2) == null) {
                            carril = "0";
                        }else{
                            carril = respuestas.getString(2);
                        }
                        String[] arregloRespuestas = {
                                respuestas.getString(0)==null? "-":respuestas.getString(0),
                                respuestas.getString(1)==null? "-":respuestas.getString(1),
                                respuestas.getString(2)==null? "-":respuestas.getString(2),
                                respuestas.getString(3)==null? "-":respuestas.getString(3),
                                respuestas.getString(4)==null? "-":respuestas.getString(4),
                                respuestas.getString(5)==null? "-":respuestas.getString(5),
                                foto1,
                                "IMAGENES"
                        };
                        tareaRespuestas.execute(arregloRespuestas);
                    }else{
                        Toast.makeText(this, "No existe imagen", Toast.LENGTH_SHORT).show();
                    }
                }
            }else{
                Toast.makeText(getApplicationContext(),
                        "NO HAY DATOS PARA SINCRONIZAR",
                        Toast.LENGTH_SHORT).show();
            }
            respuestas.close();
        }
        datos.close();
        inserta.cerrar();


        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Inicio);
                startActivity(in);
                finish();
            }
        });


    }



    class Uprespuestas extends AsyncTask<String, Void, Void> {
        ProgressDialog pDialog;
        String _ID = "";
        String imagen = "";
        String carril= "";
        String tipo= "";
        String IDENCUESTA= "";
        String fecha= "";
        String miFoto1 = "";

        @Override
        protected Void doInBackground(String... params) {
            _ID = params[0];
            imagen = params[1];
            carril= params[2];
            tipo= params[3];
            IDENCUESTA= params[4];
            fecha = params[5];
            miFoto1 = params[6];

            try {

                HttpClient httpclient = new DefaultHttpClient();
                httpclient.getParams().setParameter(
                        CoreProtocolPNames.PROTOCOL_VERSION,
                        HttpVersion.HTTP_1_1);

                HttpPost httppost = new HttpPost(li + "sincronizar.php");
                //httppost.setHeader("Accept-Encoding", "UTF-8");

                File file1 = new File(miFoto1);
//entity.addPart("video_title", new StringBody(edtvideo_title.getText().toString(),Charset.forName(HTTP.UTF_8)));
                MultipartEntity mpEntity = new MultipartEntity();

                ContentBody foto1 = new FileBody(file1, "image/jpeg");
                mpEntity.addPart("fotos1", foto1);

                mpEntity.addPart("foto", new StringBody("1"));
                mpEntity.addPart("tabla", new StringBody("IMAGENES"));
                mpEntity.addPart("id", new StringBody(_ID));

                mpEntity.addPart("IMAGEN", new StringBody(imagen));
                mpEntity.addPart("CARRIL", new StringBody(carril));
                mpEntity.addPart("TIPO", new StringBody(tipo));
                mpEntity.addPart("FOLIOENCUESTA", new StringBody(IDENCUESTA));
                mpEntity.addPart("FECHA", new StringBody(fecha));


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
            pDialog = new ProgressDialog(Sincronizapita.this);
            if (conectadoRedMovil() || conectadoWifi()) {
                pDialog.setMessage("SUBIENDO DATOS ESPERE");
            } else {

                Toast.makeText(getApplicationContext(), "NO ESTA CONECTADO A INTERNET", Toast.LENGTH_LONG).show();
            }
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            inserta.abrir();
            //String t[] = inserta.nosincro2();
            inserta.cerrar();
            // int cuantos = x.length;

            //Toast.makeText(getApplicationContext(), textin, Toast.LENGTH_SHORT).show();
            if(textin!=null){
                if (textin.equals("0")) {
                    Toast.makeText(getApplicationContext(), textin, Toast.LENGTH_LONG).show();
                } else {

                    inserta.abrir();
                    inserta.modifica(textin, modificar, "imagenes");
                    //Toast.makeText(getApplicationContext(), textin, Toast.LENGTH_SHORT).show();
                    //String x[] = inserta.envio("1");
                    inserta.cerrar();
                    sinc--;
                    //int cuantos = x.length;
                    if(sinc == 0){

                        Toast.makeText(getApplicationContext(), textin, Toast.LENGTH_SHORT).show();

                        Intent intPrin3 = new Intent(getApplicationContext(), Sincronizapita.class);
                        startActivity(intPrin3);
                        finish();
                    }
                }
            }else{
                //Toast.makeText(getApplicationContext(), li + "sincroaduanas.php", Toast.LENGTH_SHORT).show();
            }

            pDialog.dismiss();
        }
    }

    class Uploader extends AsyncTask<String, Void, Void> {

        ProgressDialog pDialog;
        String _ID = "";
        String FOLIOENCUESTA= "";
        String CODIGORESULTADO= "";
        String CVEENT= "";
        String CVEMUN= "";
        String CVELOC= "";
        String COLONIA= "";
        String RECINTOADUANAL = "";
        String CALLE= "";
        String NUMEROINT= "";
        String NUMEROEXT= "";
        String ENTRECALLE= "";
        String YCALLE= "";
        String CP= "";
        String NOMBREENTREVISTADOR= "";
        String CLAVEENTREVISTADOR= "";
        String HORAINICIO = "";
        String HORATERMINO= "";
        String OTRO_CODIGORESULTADO= "";
        String LATITUD= "";
        String LONGITUD= "";
        String CREATE_BY= "";
        String FECHAENTREVISTA="";
        String RESPONSABLEPITA="";
        String CORREO="";


        @Override
        protected Void doInBackground(String... params) {
            _ID = params[0];
            FOLIOENCUESTA= params[1];
            CODIGORESULTADO= params[2];
            CVEENT= params[3];
            CVEMUN= params[4];
            CVELOC= params[5];
            COLONIA= params[6];
            RECINTOADUANAL = params[7];
            CALLE= params[8];
            NUMEROINT= params[9];
            NUMEROEXT= params[10];
            ENTRECALLE= params[11];
            YCALLE= params[12];
            CP= params[13];
            NOMBREENTREVISTADOR= params[14];
            CLAVEENTREVISTADOR= params[15];
            HORAINICIO = params[16];
            HORATERMINO= params[17];
            OTRO_CODIGORESULTADO= params[18];
            LATITUD= params[19];
            LONGITUD= params[20];
            CREATE_BY= params[21];
            FECHAENTREVISTA= params[22];
            RESPONSABLEPITA= params[23];
            CORREO= params[24];


            try {

                HttpClient httpclient = new DefaultHttpClient();
                httpclient.getParams().setParameter(
                        CoreProtocolPNames.PROTOCOL_VERSION,
                        HttpVersion.HTTP_1_1);

                HttpPost httppost = new HttpPost(li + "sincronizar.php");
                //httppost.setHeader("Accept-Encoding", "UTF-8");


//entity.addPart("video_title", new StringBody(edtvideo_title.getText().toString(),Charset.forName(HTTP.UTF_8)));
                MultipartEntity mpEntity = new MultipartEntity();

                mpEntity.addPart("eliminar", new StringBody("0"));
                mpEntity.addPart("foto", new StringBody("0"));
                mpEntity.addPart("tabla", new StringBody("ENCUESTA"));
                mpEntity.addPart("id", new StringBody(_ID));

                mpEntity.addPart("FOLIOENCUESTA", new StringBody(FOLIOENCUESTA));
                mpEntity.addPart("IDCUESTIONARIO", new StringBody("2"));
                mpEntity.addPart("CODIGORESULTADO", new StringBody(CODIGORESULTADO));
                mpEntity.addPart("CVEENT", new StringBody(CVEENT));
                mpEntity.addPart("CVEMUN", new StringBody(CVEMUN));
                mpEntity.addPart("CVELOC", new StringBody(CVELOC));
                mpEntity.addPart("COLONIA", new StringBody(COLONIA,Charset.forName(HTTP.UTF_8)));
                mpEntity.addPart("RAZONSOCIAL", new StringBody(RECINTOADUANAL,Charset.forName(HTTP.UTF_8)));
                mpEntity.addPart("CALLE", new StringBody(CALLE,Charset.forName(HTTP.UTF_8)));
                mpEntity.addPart("NUMEROINT", new StringBody(NUMEROINT));
                mpEntity.addPart("NUMEROEXT", new StringBody(NUMEROEXT));
                //mpEntity.addPart("ENTRECALLE", new StringBody(ENTRECALLE,Charset.forName(HTTP.UTF_8)));
                //mpEntity.addPart("YCALLE", new StringBody(YCALLE,Charset.forName(HTTP.UTF_8)));
                mpEntity.addPart("CP", new StringBody(CP));
                mpEntity.addPart("NOMBREENTREVISTADOR", new StringBody(NOMBREENTREVISTADOR,Charset.forName(HTTP.UTF_8)));
                mpEntity.addPart("CLAVEENTREVISTADOR", new StringBody(CLAVEENTREVISTADOR));
                mpEntity.addPart("HORAINICIO", new StringBody(HORAINICIO));
                mpEntity.addPart("HORATERMINO", new StringBody(HORATERMINO));
                mpEntity.addPart("OTRO_CODIGORESULTADO", new StringBody(OTRO_CODIGORESULTADO));
                mpEntity.addPart("LATITUD", new StringBody(LATITUD));
                mpEntity.addPart("LONGITUD", new StringBody(LONGITUD));
                mpEntity.addPart("CREATE_BY", new StringBody(CREATE_BY));
                mpEntity.addPart("FECHAENTREVISTA", new StringBody(FECHAENTREVISTA));
                mpEntity.addPart("CORREOENTREVISTADO", new StringBody(CORREO));
                mpEntity.addPart("RESPONSABLE", new StringBody(RESPONSABLEPITA,Charset.forName(HTTP.UTF_8)));



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
            pDialog = new ProgressDialog(Sincronizapita.this);
            if (conectadoRedMovil() || conectadoWifi()) {
                pDialog.setMessage("SUBIENDO DATOS ESPERE");
            } else {

                Toast.makeText(getApplicationContext(), "NO ESTA CONECTADO A INTERNET", Toast.LENGTH_LONG).show();
            }
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            inserta.abrir();
            //String t[] = inserta.nosincro2();
            inserta.cerrar();
            // int cuantos = x.length;

            //Toast.makeText(getApplicationContext(), textin, Toast.LENGTH_SHORT).show();
                if(textin!=null){
                    if (textin.equals("0")) {
                        Toast.makeText(getApplicationContext(), textin, Toast.LENGTH_LONG).show();
                    } else {

                        inserta.abrir();
                        inserta.modifica(textin, modificar, "encuesta");
                        //Toast.makeText(getApplicationContext(), textin, Toast.LENGTH_SHORT).show();
                        //String x[] = inserta.envio("1");
                        inserta.cerrar();
                        sinc--;
                        //int cuantos = x.length;
                        if(sinc == 0){

                            Toast.makeText(getApplicationContext(), textin, Toast.LENGTH_SHORT).show();

                            Intent intPrin3 = new Intent(Sincroniza);
                            startActivity(intPrin3);
                            finish();
                        }
                    }
                }else{
                    //Toast.makeText(getApplicationContext(), li + "sincroaduanas.php", Toast.LENGTH_SHORT).show();
                }

            pDialog.dismiss();
        }
    }
    private ArrayList<Entidad> GetArrayItems(){
        ArrayList<Entidad> listItems = new ArrayList<>();
        Cursor cur = inserta.enviar();
        while (cur.moveToNext()) {

            String sinc = (Integer.parseInt(cur.getString(3))==0)? "NO SINCRONIZADO":"SINCRONIZADO";

            listItems.add(new Entidad(cur.getString(0), cur.getString(3), "FOLIO\n"+ cur.getString(1)+ "\n" + sinc));//id
        }
        cur.close();
        inserta.close();
        return listItems;
    }
}

