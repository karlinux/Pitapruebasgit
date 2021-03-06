package com.g214.pita;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by carlos on 30/06/17.
 */

public class Fotopita extends Activity {
    private final Handler_sqlite inserta = new Handler_sqlite(this);
    private final Links l = new Links();
    String APK = l.getApk();
    String fechaini = l.getFecha();
    String carpeta = l.getCarpeta();
    String CAMPO = l.getCampo();

    private static final int CAMARA_PERMISSION = 11;
    Button btnGuardar, btnFinalizar, btnRegresar;
    ImageButton btnFoto;
    EditText etCarril;
    String imeistring, nombrefoto, foto, imagen, fecha, id, seccion, fotoimagen, cuis, dm, Gps, Foto, Inicio, FOLIOENCUESTA, nameFoto, carril, Siguiente;
    TextView tvNumero, tvFecha, tvPosicion;
    Spinner spDocumento;
    Bundle bolsa;
    Typeface ligt, regular, medio;
    ListView lista;
    Boolean bool;
    Cursor curPunto;
    int numpregunta, cuestionario, numpregunta2;
    String [] tipoFoto;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foto);

        if(CAMPO.equals("1")){
            Siguiente=".Empresa" + APK;
        }else{
            Siguiente=".Gps" + APK;
        }

        tipoFoto = new String[]{"",
                "Video vigilancia",             //1
                "Vehículos ligeros",            //2
                "Vehículos de carga",           //3
                "Infraestructura auxiliar",     //4
                "Binomio canino",               //5
                "Carril administrado",          //6
                "Zona de revisión ",            //7
                "Control de carga peatonal",    //8
                "Zona de amarillos de carga",   //9
                "Zona de revisión de carga",    //10
                "Centro de monitoreo",          //11
                "Equipos moviles",              //12
                "Zona de rojos de carga",       //13
                "Sensores en espacios de carga",       //14
                "Zona de rayos x"};              //15

        tvFecha = (TextView) findViewById(R.id.tvFecha);
        etCarril = (EditText) findViewById(R.id.etCarril);
        tvPosicion = (TextView) findViewById(R.id.tvPosicion);
        tvNumero = (TextView) findViewById(R.id.tvNumero);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnFinalizar = (Button) findViewById(R.id.btnFinalizar);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        spDocumento = (Spinner) findViewById(R.id.spDocumento);
        lista = (ListView) findViewById(R.id.lvLista);
        spDocumento.setVisibility(View.GONE);
        lista.setVisibility(View.GONE);
        //tvPosicion.setVisibility(View.GONE);
        tvFecha.setVisibility(View.GONE);
        tvNumero.setVisibility(View.GONE);
        btnFinalizar.setVisibility(View.GONE);

        bolsa = getIntent().getExtras();
        inserta.abrir();
        cuestionario = Integer.parseInt(bolsa.getString("numpregunta"));
        inserta.cerrar();

        RadioGroup contenedor = (RadioGroup) findViewById(R.id.rgDoc);
        contenedor.setVisibility(View.GONE);

        carril = "";

        Gps = ".Gps" + APK;
        Foto = ".Foto" + APK;
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
        //tvNumero.setText("FOTO IDENTIFICACION");


        ligt = Typeface.createFromAsset(getAssets(), "fonts/montserratLight.otf");
        regular=Typeface.createFromAsset(getAssets(), "fonts/montserratRegular.otf");
        medio=Typeface.createFromAsset(getAssets(), "fonts/montserratMedium.otf");
        tvNumero.setTypeface(medio);
        tvFecha.setTypeface(regular);
        tvPosicion.setTypeface(medio);

        inserta.abrir();
        cuis = inserta.cuis();
        curPunto = inserta.punto();
        curPunto.moveToLast();
        FOLIOENCUESTA = curPunto.getString(0);
        inserta.cerrar();

        inserta.abrir();
        numpregunta = Integer.parseInt(inserta.NUMPREGUNTAFOTO(FOLIOENCUESTA));
        fotoimagen = inserta.imagen(FOLIOENCUESTA);
        inserta.cerrar();
        tvPosicion.setText(tipoFoto[numpregunta]);
        //Toast.makeText(this, FOLIOENCUESTA, Toast.LENGTH_SHORT).show();
        btnFoto = (ImageButton) findViewById(R.id.btnFoto);

        inserta.abrir();
        nameFoto = inserta.idenFoto(FOLIOENCUESTA);
        id = inserta.iden(FOLIOENCUESTA);
        fecha = inserta.fecha("encuesta", "FECHAENTREVISTA", FOLIOENCUESTA);
        id = inserta.idenRespuestas(FOLIOENCUESTA);
        inserta.cerrar();

        switch (cuestionario){ //// Primer switch Seccion de Preguntas

            case 0:
                seccion = "UNO";
                numpregunta2 = Integer.parseInt(inserta.NUMPREGUNTA(FOLIOENCUESTA, seccion));
                break;

            case 4:
                seccion = "DOS";
                numpregunta2 = Integer.parseInt(inserta.NUMPREGUNTA(FOLIOENCUESTA, seccion));
                break;
            case 27:
                seccion = "TRES";
                numpregunta2 = Integer.parseInt(inserta.NUMPREGUNTA(FOLIOENCUESTA, seccion));
                break;
            case 102:
                seccion = "CUATRO";
                numpregunta2 = Integer.parseInt(inserta.NUMPREGUNTA(FOLIOENCUESTA, seccion));
                break;

        }

        nameFoto = Integer.toString(Integer.parseInt(nameFoto) + 1);

                etCarril.setVisibility(View.GONE);

        imagen = Environment.getExternalStorageDirectory() + "/"+ carpeta +"/";
        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        imeistring = telephonyManager.getDeviceId();

        StringBuilder stringFecha2 = new StringBuilder();

        stringFecha2.append(imeistring);
        stringFecha2.append(fecha.substring(0,4));
        stringFecha2.append(fecha.substring(5,7));
        stringFecha2.append(fecha.substring(8,10));
        stringFecha2.append(fecha.substring(11,13));
        stringFecha2.append(fecha.substring(14,16));
        stringFecha2.append(fecha.substring(17,19));
        stringFecha2.append(nameFoto);
        stringFecha2.append(".jpg");

        nombrefoto = stringFecha2.toString();

        StringBuilder stringFecha = new StringBuilder();
        stringFecha.append(imagen);
        stringFecha.append(imeistring);
        stringFecha.append(fecha.substring(0,4));
        stringFecha.append(fecha.substring(5,7));
        stringFecha.append(fecha.substring(8,10));
        stringFecha.append(fecha.substring(11,13));
        stringFecha.append(fecha.substring(14,16));
        stringFecha.append(fecha.substring(17,19));
        stringFecha.append(nameFoto);
        stringFecha.append(".jpg");

        foto = stringFecha.toString();

        File file = new File(foto);

        bool = false;
        bool = file.exists();
        Log.d("IMAGENSINCRO", foto);
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
            btnRegresar.setVisibility(View.GONE);
            btnGuardar.setVisibility(View.VISIBLE);
        }else{
            btnRegresar.setVisibility(View.VISIBLE);
            btnGuardar.setVisibility(View.GONE);
            btnFoto.setImageResource(R.drawable.zc4000);
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
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File file = new File(foto);
                bool = file.exists();
                carril = etCarril.getText().toString();
                String error="";
                if(bool==false){
                    error="CAPTURE LA FOTO";
                }else{
                    error="";
                }

                if(error.equals("")){

                    inserta.abrir();
                    inserta.insertaFoto(FOLIOENCUESTA, nombrefoto, Integer.toString(numpregunta), id);
                    inserta.cerrar();

                    Intent ins= new Intent(Foto);
                    ins.putExtras(bolsa);
                    startActivity(ins);
                    finish();

                }else{
                    Toast.makeText(getApplicationContext(), error,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fotoimagen.equals("1")){
                    numpregunta2++;

                    switch (numpregunta){


                    }
                    // En esta fase se regresa al principio del ciclo

                    switch (numpregunta2){               //
                        case 15:                        //
                            numpregunta2 = 5;            //
                            break;
                        case 20:
                            numpregunta2 = 17;
                            break;
                        case 26:
                            numpregunta2 = 22;
                            break;                      //
                        case 46:
                            numpregunta2 = 28;
                            break;
                        case 57:
                            numpregunta2 = 48;
                            break;
                        case 69:                        //
                            numpregunta2 = 65;            //
                            break;                      //
                        case 74:                        //
                            numpregunta2 = 71;           //
                            break;
                        case 78:
                            numpregunta2 = 76;
                            break;
                        case 89:                        //
                            numpregunta2 = 80;           //
                            break;
                    }                                   //
                    //////////////////////////////////////
                    inserta.abrir();
                    inserta.actualizaFoto(FOLIOENCUESTA, "0", "0", seccion, Integer.toString(numpregunta2));
                    inserta.cerrar();
                    Intent ins = new Intent(".Multipuntopita");
                    ins.putExtras(bolsa);
                    startActivity(ins);
                    finish();
                }else{
                    inserta.abrir();
                    inserta.actualizaFoto(FOLIOENCUESTA, "0");
                    inserta.cerrar();
                    Intent ins = new Intent(".Imagenespita");
                    startActivity(ins);
                    finish();
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
            inteF.putExtras(bolsa);
            startActivity(inteF);
            finish();
            foto="";
        }else{
            btnFoto.setImageResource(R.drawable.zc4000);
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
                inserta.actualizaGuardado(nameFoto, "0");
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
            int1.putExtras(bolsa);
            startActivity(int1);
            finish();
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
                inteF.putExtras(bolsa);
                startActivity(inteF);
                finish();

            }else{
                finish();
            }

        }
    }
}
