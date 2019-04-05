package com.g214.pita;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private final Handler_sqlite inserta = new Handler_sqlite(this);
    private final Links l = new Links();
    String carpeta = l.getCarpeta();

    private static final int WRITE_PERMISSION = 11;
    private static final int READ_PHONE_STATE = 10;
    String path = l.getPath();
    String VERSION = l.getVersion();
    String APK = l.getApk();
    String li = l.getLink();

    String inicio = ".Inicio"+APK;
    EditText etNombre, etPassword;
    TextView tvNumero;
    Button btnBoton;
    String imeistring, n, foto, textin, completo, usuario, tipo, dm;
    TextView tvImei;
    Typeface light, regular, medio;
    int id;
    AlertDialog alert = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tvNumero = (TextView) findViewById(R.id.tvNumero);
        tvNumero.setText("Versión " + VERSION + ".0");
        etNombre = (EditText) findViewById(R.id.etNombre);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnBoton = (Button) findViewById(R.id.btnBoton);
        tvImei = (TextView) findViewById(R.id.tvImei);

        light=Typeface.createFromAsset(getAssets(), "fonts/montserratLight.otf");
        regular=Typeface.createFromAsset(getAssets(), "fonts/montserratRegular.otf");
        medio=Typeface.createFromAsset(getAssets(), "fonts/montserratMedium.otf");
        etNombre.setTypeface(regular);
        etPassword.setTypeface(regular);
        tvNumero.setTypeface(light);
        tvImei.setTypeface(light);
        btnBoton.setTypeface(medio);

        inserta.abrir();
        n = inserta.guardado();
        Cursor curBorrar = inserta.borrar();
        id = curBorrar.getCount();
        inserta.cerrar();

        //Toast.makeText(this, Integer.toString(id), Toast.LENGTH_SHORT).show();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION);

                } else {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION);

                }

            } else {

                if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                    if (shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                        requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE);

                    } else {
                        requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE);

                    }

                } else {

                    File directory = new File(Environment.getExternalStorageDirectory() + "/" + carpeta);
                    boolean bool = directory.exists();
                    if (bool == false) {
                        directory.mkdirs();
                    }


                    /////////////////////////////  Elimina contenido de carpeta si se instalo la aplicación

                    if (id == 0) {
                        //Toast.makeText(this, Integer.toString(id), Toast.LENGTH_SHORT).show();
                       // eliminarPorExtension(Environment.getExternalStorageDirectory() + "/" + carpeta, "jpg");
                    }

                    /////////////////////////////  Copia imagen

                    foto = "/data/data/" + carpeta + "/imagenes.jpg";
                    File file = new File(foto);
                    if ( id == 0) {
//                        copyDataBase();
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
                    imeistring = telephonyManager.getDeviceId();

                }

            }
        } else {

            File directory = new File(Environment.getExternalStorageDirectory() + "/" + carpeta);
            boolean bool = directory.exists();
            if (bool == false) {
                directory.mkdirs();
            }


            /////////////////////////////  Elimina contenido de carpeta si se instalo la aplicación

            if ( id == 0) {
                //Toast.makeText(this, Integer.toString(id), Toast.LENGTH_SHORT).show();
               // eliminarPorExtension(Environment.getExternalStorageDirectory() + "/" + carpeta, "jpg");
            }

            /////////////////////////////  Copia imagen

            foto = "/data/data/" + carpeta + "/files/imagenes.jpg";

            File file = new File(foto);

            if ( id == 0) {
                //copyDataBase();
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

        }

        // Ejecuta alert de fecha y si ya se logueo manda a la siguiente activity

        if (n.equals("1")) {
            Bundle bolsa = new Bundle();
            bolsa.putString("cuis", "");

            Intent intPrin = new Intent(inicio);
            intPrin.putExtras(bolsa);
            startActivity(intPrin);
            finish();
        } else {
            AlertFecha();
        }

        ///////////////////////////// Crea carpeta de imagenes

        tvImei.setText(imeistring);

        btnBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pswd;

                usuario = etNombre.getText().toString();
                pswd = etPassword.getText().toString();

                String[] unarreglo = {usuario, pswd, imeistring};

                Session nuevaTarea = new Session();
                nuevaTarea.execute(unarreglo);
            }
        });

    }

    private void copyDataBase() {
        // TODO Auto-generated method stub

        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        for(String filename : files) {
            //Toast.makeText(this, filename, Toast.LENGTH_SHORT).show();
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(filename);
                File outFile = new File("/data/data/"+path+"/"+ filename);
                //Toast.makeText(getApplicationContext(), filename, Toast.LENGTH_SHORT).show();

                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
            }
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    public static void eliminarPorExtension(String path, final String extension){

        File[] archivos = new File(path).listFiles(new FileFilter() {

            public boolean accept(File archivo) {

                if (archivo.isFile())

                    return archivo.getName().endsWith('.' + extension);

                return false;

            }

        });

        for (File archivo : archivos)

            archivo.delete();

    }
    public void crearAccesoDirecto() {
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
        shortcut.putExtra("duplicado", Boolean.FALSE);
        String appClass = this.getPackageName() + "." + this.getLocalClassName();
        ComponentName comp = new ComponentName(this.getPackageName(), appClass);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));

        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(this, R.drawable.icon);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);

        sendBroadcast(shortcut);
    }

    class Session extends AsyncTask<String, Void, Void> {


        ProgressDialog pDialog;
        String usuarios = "";
        String passs = "";
        String imei = "";

        @Override
        protected Void doInBackground(String... params) {
            usuarios = params[0];
            passs = params[1];
            imei = params[2];

            try {

                HttpClient httpclient = new DefaultHttpClient();
                httpclient.getParams().setParameter(
                        CoreProtocolPNames.PROTOCOL_VERSION,
                        HttpVersion.HTTP_1_1);

                HttpPost httppost = new HttpPost(li + "login.php");

                MultipartEntity mpEntity = new MultipartEntity();

                mpEntity.addPart("usuario", new StringBody(usuarios));
                mpEntity.addPart("pass", new StringBody(passs));
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
            pDialog = new ProgressDialog(MainActivity.this);
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

            if(textin != null) {

                String[] esplitTextin = textin.split("---");

                if(esplitTextin.length>1) {

                    if (esplitTextin[2].equals("")) {
                        completo = "";
                    } else {
                        completo = esplitTextin[2];
                    }


                    if (!esplitTextin[1].equals(VERSION) || esplitTextin[2].equals("0") || esplitTextin[3].equals("0")) {

                        //Toast.makeText(getApplicationContext(), esplitEstatus[2], Toast.LENGTH_SHORT).show();
                        Dialogo(esplitTextin[1], esplitTextin[2], esplitTextin[3]);

                    } else {
                        if (esplitTextin[0].equals("0")) {
                            Toast.makeText(getApplicationContext(), "USUARIO O CONTRASEÑA NO VALIDO ", Toast.LENGTH_LONG).show();

                        } else {

                            if (esplitTextin[0].equals("1")) {
                                //Layout activity de acceso

                                if (n.equals("")) {
                                    crearAccesoDirecto();
                                }

                                inserta.abrir();
                                inserta.insertarReg2(usuario, esplitTextin[4], esplitTextin[5], esplitTextin[6], esplitTextin[7]);
                                inserta.cerrar();
                                Intent intPrin = new Intent(inicio);
                                startActivity(intPrin);
                                finish();

                            } else {
                                Toast.makeText(getApplicationContext(), "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                            }

                            //Toast.makeText(MainActivity.this, textin, Toast.LENGTH_SHORT).show();

                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "NO HUBO RESPUESTA DEL SERVIDOR", Toast.LENGTH_SHORT).show();
                }
            }

            pDialog.dismiss();
        }

    }

    private void AlertFecha() {
        long ahora = System.currentTimeMillis();
        Date fecha = new Date(ahora);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat hr = new SimpleDateFormat("HH:mm:ss");
        final String salida = df.format(fecha);
        String hora = hr.format(fecha);

        View view = this.getLayoutInflater().inflate(R.layout.dialogo,null);
        LinearLayout mLinearLayout1 = (LinearLayout) view.findViewById(R.id.layDialogo);

        dm =  Build.PRODUCT;

        //dm = "C4000";
        if(dm.equals("C4000")) {
            mLinearLayout1.setBackgroundColor(Color.WHITE);
        }

        TextView tvText =  (TextView) view.findViewById(R.id.tvTexto);
        Button btnSi = (Button) view.findViewById(R.id.btnSi);
        Button btnNo = (Button) view.findViewById(R.id.btnNo);

        Button btnDescargar = (Button) view.findViewById(R.id.btnDescargar);
        //btnDescargar.setVisibility(Button.INVISIBLE);
        btnDescargar.setVisibility(View.GONE);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setView(view);
        builder.setTitle("");
        tvText.setTypeface(light);
        tvText.setText(
                "\n\nFecha: " + salida +
                        "\nHora: " + hora +
                        "\n¿La fecha y hora \nson correctas?"
        );
        builder.setCancelable(false);
        btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_DATE_SETTINGS));
            }
        });

        alert = builder.create();
        alert.show();
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
                    finish();
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
                    finish();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == WRITE_PERMISSION || requestCode == READ_PHONE_STATE){

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                Intent inteF = new Intent(this, MainActivity.class);
                startActivity(inteF);
                finish();

            }else{
                finish();
            }

        }
    }
}