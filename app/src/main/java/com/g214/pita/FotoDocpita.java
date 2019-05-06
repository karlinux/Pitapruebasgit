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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by carlos on 30/06/17.
 */

public class FotoDocpita extends Activity {
    private final Handler_sqlite inserta = new Handler_sqlite(this);
    private final Links l = new Links();
    String APK = l.getApk();
    String fechaini = l.getFecha();
    String carpeta = l.getCarpeta();

    private static final int CAMARA_PERMISSION = 11;
    Button btnGuardar, btnFinalizar, btnRegresar;
    ImageButton btnFoto;
    EditText etCarril;
    String imeistring, Regresar, nombrefoto, foto, folio, imagen, fecha, id, cuis, dm, Gps, Foto, Inicio, FOLIOENCUESTA,
            nameFoto, carril, selecDocumento, documento, tipoDocumento;
    TextView tvNumero, tvFecha, tvPosicion;
    Typeface ligt, regular, medio;
    Boolean bool;
    Spinner spDocumento;
    Bundle bolsa;
    int numpregunta;
    String [] tipoFoto;
    RadioGroup contenedor;
    private Adaptador adaptador;
    RadioButton opcionI1, opcionI2;
    ListView lista;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foto);

        //tipoFoto = new String[]{"", "Encuesta de percepción", "Instrumento de supervisión", ""};

        tipoFoto = new String[]{"", "Video vigilancia", "Vehículos ligeros", "Vehículos de carga", "Infraestructura auxiliar",
                "Binomio canino", "Carril administrado", "Zona de revisión ", "Control de carga peatonal", "Zona de amarillos de carga",
                "Zona de revisión de carga", "Centro de monitoreo", "Encuesta de percepción", "Instrumento de supervisión"};

        btnFoto = (ImageButton) findViewById(R.id.btnFoto);
        btnFoto.setVisibility(View.GONE);
        tvFecha = (TextView) findViewById(R.id.tvFecha);
        etCarril = (EditText) findViewById(R.id.etCarril);
        tvPosicion = (TextView) findViewById(R.id.tvPosicion);
        tvNumero = (TextView) findViewById(R.id.tvNumero);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnFinalizar = (Button) findViewById(R.id.btnFinalizar);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        spDocumento = (Spinner) findViewById(R.id.spDocumento);
        lista = (ListView) findViewById(R.id.lvLista);


        etCarril.setVisibility(View.GONE);
        tvFecha.setVisibility(View.GONE);
        tvNumero.setVisibility(View.GONE);
        btnGuardar.setVisibility(View.GONE);

        contenedor = (RadioGroup) findViewById(R.id.rgDoc);
        opcionI1 = (RadioButton) contenedor.getChildAt(0);
        opcionI2 = (RadioButton) contenedor.getChildAt(1);
        spDocumento.setVisibility(View.GONE);
        contenedor.setVisibility(View.GONE);

        carril = "";

        Gps = ".Revisar" + APK;
        Foto = ".FotoDoc" + APK;
        Inicio = ".Inicio" + APK;
        Regresar = ".Revisar" + APK;

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

        bolsa = getIntent().getExtras();
        FOLIOENCUESTA = bolsa.getString("folio");

        adaptador = new Adaptador(this, GetArrayItems(FOLIOENCUESTA));

        lista.setAdapter(adaptador);

        lista.setClickable(false);

        inserta.abrir();
        cuis = inserta.cuis();
        tipoDocumento = inserta.documento();
        //FOLIOENCUESTA = inserta.FOLIOENCUESTA();
        numpregunta = Integer.parseInt(inserta.NUMPREGUNTA());
        inserta.cerrar();

        numpregunta = numpregunta + 1;

        if(tipoDocumento.equals("12")) {
            opcionI1.setChecked(true);
        }else{
            opcionI2.setChecked(true);
        }

        tvPosicion.setText("RESPUESTAS");

        btnFoto = (ImageButton) findViewById(R.id.btnFoto);

        spDocumento.setAdapter(ArrayAdapter.createFromResource(this, R.array.documento, R.layout.spinner_item));
        spDocumento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                selecDocumento = String.valueOf(spDocumento.getSelectedItem());
                String [] arrayPregunta = selecDocumento.split("-");

                documento = arrayPregunta[0];

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        inserta.abrir();
        nameFoto = inserta.idenFoto();
        id = inserta.iden();
        fecha = inserta.fecha("encuesta", "FECHAENTREVISTA", FOLIOENCUESTA);
        inserta.cerrar();

        //Toast.makeText(this, fecha, Toast.LENGTH_SHORT).show();
        nameFoto = Integer.toString(Integer.parseInt(nameFoto) + 1);

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

        //Toast.makeText(this, nameFoto, Toast.LENGTH_SHORT).show();
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
            btnFinalizar.setVisibility(View.GONE);
            btnGuardar.setVisibility(View.VISIBLE);
        }else{
            btnFinalizar.setVisibility(View.GONE);
            //btnGuardar.setVisibility(View.GONE);
            btnFoto.setImageResource(R.drawable.documento);
        }

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

                if(opcionI1.isChecked()){
                    documento = "12";
                }else{
                    documento = "13";
                }

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
                    inserta.insertaFoto(FOLIOENCUESTA, nombrefoto, documento, "0");
                    inserta.cerrar();

                    bolsa.putString("folio", FOLIOENCUESTA);
                    Intent inteF = new Intent(Foto);
                    inteF.putExtras(bolsa);
                    startActivity(inteF);
                    finish();
                    foto="";

                }else{
                    Toast.makeText(getApplicationContext(), error,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent ins = new Intent(Regresar);
                    startActivity(ins);
                    finish();
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ins = new Intent(Regresar);
                startActivity(ins);
                finish();
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        File file = new File(foto);

        boolean bools = false;
        bools = file.exists();

        if(bools){
            Bitmap bmSource = BitmapFactory.decodeFile(foto);
            int width3 = bmSource.getWidth(); //1728 864
            int height3 = bmSource.getHeight();//3072 1536
            //Toast.makeText(getApplicationContext(), Integer.toString(height3), Toast.LENGTH_SHORT).show();
            if(height3<width3){

                bmSource = Bitmap.createScaledBitmap(bmSource,1536, 864, false);
                Matrix matrix = new Matrix();
                float degrees = -90;
                matrix.postRotate(degrees);
                bmSource = Bitmap.createBitmap(bmSource, 0, 0, bmSource.getWidth(), bmSource.getHeight(), matrix, true);

            }else{
                bmSource = Bitmap.createScaledBitmap(bmSource,864, 1536, false);
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

            bolsa.putString("folio", FOLIOENCUESTA);
            Intent inteF = new Intent(Foto);
            inteF.putExtras(bolsa);
            startActivity(inteF);
            finish();
            foto="";
        }else{
            btnFoto.setImageResource(R.drawable.documento);
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

    private ArrayList<Entidad> GetArrayItems(String folio){
        ArrayList<Entidad> listItems = new ArrayList<>();

        inserta.abrir();

        Cursor res= inserta.respuestasDetalle(folio);
        while (res.moveToNext()) {
            String sincronizado = "";
            if(res.getString(1).equals("1")){
                sincronizado = "SINCRONIZADO";
            }else{
                sincronizado = "NO SINCRONIZADO";
            }
            listItems.add(
                    new Entidad(
                            "", "", ""+
                            "Pregunta \n"+res.getString(3)+
                            "\n"+sincronizado
                    )
            );
        }
        res.close();

        listItems.add(new Entidad("", "", "IMÁGENES"));

        Cursor cur= inserta.imagenesDetalle(folio);
        while (cur.moveToNext()) {
            String sincronizado = "";
            if(cur.getString(1).equals("1")){
                sincronizado = "SINCRONIZADO";
            }else{
                sincronizado = "NO SINCRONIZADO";
            }
            listItems.add(
                    new Entidad(
                            "", "", ""+
                            "Imágen \n"+tipoFoto[Integer.parseInt(cur.getString(2))]+
                    "\n"+sincronizado
                    )
            );
        }
        cur.close();
        inserta.close();
        return listItems;
    }
}
