package com.g214.pita;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by carlos on 10/08/17.
 */

public class Links {
    public static String link = "http://g214.com.mx/pita/";
    //public static String LINKSINC = "http://138.197.169.196:9214/aduanas/";
    public static String LINKSINC = "http://169.57.16.76:8030/Sincronizador_PMCCA/";
    public static String GPS = "0";
    public static String FECHA = "0";
    public static String JSON = "http://g214.com.mx/pita/json.php";

    //public static String VERSION = "6";
    public static String VERSION = "8";
    public static String CARPETA = "archpita";
    public static String PATH = "com.g214.pita";

    public static String APK = "pita";

    public  static String CAMPO = "1";

    public String getLink(){
       return link;
   }
    public String getLinkSinc(){
        return LINKSINC;
    }
    public String getJson(){
        return JSON;
    }
    public String getCampo(){
        return CAMPO;
    }

    public String getApk(){
        return APK;
    }
    public String getCarpeta(){
        return CARPETA;
    }
    public String getPath(){
        return PATH;
    }
    public String getVersion(){
        return VERSION;
    }

    public String getFecha() {

        Date fechaActual = new Date();
        SimpleDateFormat a = new SimpleDateFormat("yyyy");
        SimpleDateFormat m = new SimpleDateFormat("MM");
        SimpleDateFormat d = new SimpleDateFormat("d");

        String mes = m.format(fechaActual);
        String ano = a.format(fechaActual);
        String dia = d.format(fechaActual);

        String meses[] = {"", "enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};


       FECHA = dia+" de "+meses[Integer.parseInt(mes)]+" de "+ano;

        return FECHA;
    }
}
/*Modificar base de datos

* inserta.abrir();
        Cursor cur = inserta.bases();
        int num = cur.getCount();
        while(cur.moveToNext()){

            String fech = cur.getString(1).substring(16,30);
            //Toast.makeText(this, fech, Toast.LENGTH_SHORT).show();
            inserta.updateBase(cur.getString(0), fech + cur.getString(2));
        }
        inserta.cerrar();
* */