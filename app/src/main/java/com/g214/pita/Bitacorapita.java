package com.g214.pita;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by carlos on 30/06/17.
 */

public class Bitacorapita extends Activity {
    private final Handler_sqlite inserta = new Handler_sqlite(this);
    private final Links l = new Links();
    String APK = l.getApk();
    String fechaini = l.getFecha();
    String carpeta = l.getCarpeta();

    private static final int CAMARA_PERMISSION = 11;
    Button btnGuardar;
    ImageButton btnFoto;
    int nameFoto;
    String imeistring, foto, imagen, fecha, cuis, dm, Gps, Foto, Inicio;
    TextView tvNumero, tvFecha, tvPosicion;
    Typeface ligt, regular, medio;
    //IntentS
    String FOTO;
    Boolean bool;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foto);

        tvFecha = (TextView) findViewById(R.id.tvFecha);

        Gps = ".Bitacoratexto" + APK;
        Foto = ".Bitacora" + APK;
        Inicio = ".Inicio" + APK;

        tvFecha.setText(fechaini);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMARA_PERMISSION);

                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMARA_PERMISSION);

                }

            } else {

            }
        }


        File directory = new File(Environment.getExternalStorageDirectory()+ "/" + carpeta);
        boolean boole = directory.exists();
        if(boole==false){
            directory.mkdirs();
        }

      tvNumero = (TextView) findViewById(R.id.tvNumero);
        tvPosicion = (TextView) findViewById(R.id.tvPosicion);
        tvNumero.setText("FOTO BITACORA");
        tvPosicion.setText("");
/*
        ligt = Typeface.createFromAsset(getAssets(), "fonts/montserratLight.otf");
        regular=Typeface.createFromAsset(getAssets(), "fonts/montserratRegular.otf");
        medio=Typeface.createFromAsset(getAssets(), "fonts/montserratMedium.otf");
        tvNumero.setTypeface(medio);
        tvFecha.setTypeface(regular);
        tvPosicion.setTypeface(medio);*/



        inserta.abrir();
        cuis = inserta.cuis();
        nameFoto = Integer.parseInt(inserta.iden());
        inserta.cerrar();

        System.gc();

        btnFoto = (ImageButton) findViewById(R.id.btnFoto);

        inserta.abrir();
        nameFoto = Integer.parseInt(inserta.iden());
        fecha = inserta.fecha("encuesta", "FECHAENTREVISTA");
        inserta.cerrar();

        imagen = Environment.getExternalStorageDirectory() + "/"+ carpeta +"/";
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
        StringBuilder stringFecha = new StringBuilder();
        stringFecha.append(imagen);
        stringFecha.append(imeistring);
        stringFecha.append(fecha.substring(0,4));
        stringFecha.append(fecha.substring(5,7));
        stringFecha.append(fecha.substring(8,10));
        stringFecha.append(fecha.substring(11,13));
        stringFecha.append(fecha.substring(14,16));
        stringFecha.append(fecha.substring(17,19));
        stringFecha.append(Integer.toString(nameFoto));
        stringFecha.append("_BITACORA.jpg");

        foto = stringFecha.toString();

        File file = new File(foto);

        bool = false;
        bool = file.exists();

        if(bool==true){
            Bitmap bmSource = BitmapFactory.decodeFile(foto);
            int width3 = bmSource.getWidth();
            int height3 = bmSource.getHeight();
            if(height3<width3){
                bmSource = Bitmap.createScaledBitmap(bmSource,
                        300, 226, false);
            }else{
                bmSource = Bitmap.createScaledBitmap(bmSource,
                        226, 300, false);
            }

            btnFoto.setImageBitmap(bmSource);
        }else{

            btnFoto.setImageResource(R.drawable.bitacora);
        }

        btnFoto = (ImageButton) findViewById(R.id.btnFoto);
        btnFoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Uri output = Uri.fromFile(new File(foto));

                dm =  Build.PRODUCT;

                if(dm.equals("C4000")){
                    Uri output = Uri.fromFile(new File(foto));
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
                    startActivityForResult(intent, 1);
                }else {
                    Uri output = FileProvider.getUriForFile(getApplicationContext(),
                            "com.g214."+APK+".provider", new File(foto));
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
                    startActivityForResult(intent, 1);
                }
            }
        });

        btnGuardar = (Button) findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File file = new File(foto);
                bool = file.exists();

                String error="";
                if(bool==false){
                    error="CAPTURE LA FOTO";
                }

                if(error.equals("")){
                    Intent ins= new Intent(Gps);
                    startActivity(ins);
                    finish();

                }else{
                    Toast.makeText(getApplicationContext(), error,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        File file = new File(foto);

        boolean bools = false;
        bools = file.exists();

        if(bools){
            Bitmap bmSource = BitmapFactory.decodeFile(foto);
            int width3 = bmSource.getWidth();
            int height3 = bmSource.getHeight();
            if(height3<width3){

                bmSource = Bitmap.createScaledBitmap(bmSource,600, 450, false);
                Matrix matrix = new Matrix();
                float degrees = -90;
                matrix.postRotate(degrees);
                bmSource = Bitmap.createBitmap(bmSource, 0, 0, bmSource.getWidth(), bmSource.getHeight(), matrix, true);

            }else{
                bmSource = Bitmap.createScaledBitmap(bmSource,450, 600, false);
            }
            FileOutputStream fos1 = null;
            try
            {
                fos1 = new FileOutputStream(foto);
                bmSource.compress(Bitmap.CompressFormat.JPEG, 100, fos1);
                btnFoto.setImageBitmap(bmSource);
                fos1.flush();
                fos1.close();
                fos1 = null;

            }
            catch (IOException e)
            {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Error " + e.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
            Intent inteF = new Intent(Foto);
            startActivity(inteF);
            finish();
            foto="";
        }else{
            btnFoto.setImageResource(R.drawable.bitacora);
        }
    }

    public boolean onCreateOptionsMenu(Menu menus) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menusa, menus);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.principal:

                inserta.abrir();
                inserta.actualizaGuardado(Integer.toString(nameFoto), "0");
                inserta.cerrar();
                Intent inten = new Intent(Inicio);
                startActivity(inten);
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent int1 = new Intent(Foto);
            startActivity(int1);
            return true;
        }else if (keyCode == 139){

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            dm =  Build.PRODUCT;

            if(dm.equals("C4000")){
                Uri output = Uri.fromFile(new File(foto));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
                startActivityForResult(intent, 1);
            }else {
                Uri output = FileProvider.getUriForFile(getApplicationContext(),
                        "com.g214."+APK+".provider", new File(foto));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
                startActivityForResult(intent, 1);
            }
        }
        return false;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == CAMARA_PERMISSION){

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                Intent inteF = new Intent(Foto);
                startActivity(inteF);
                finish();

            }else{
                finish();
            }

        }
    }
}
