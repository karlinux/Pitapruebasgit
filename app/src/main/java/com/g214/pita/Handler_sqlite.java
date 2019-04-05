package com.g214.pita;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;

public class Handler_sqlite extends SQLiteOpenHelper {



	private String encuesta = "encuesta";
	private String respuestas = "respuestas";
	private String bitacora = "BITACORA";
	private String login = "login";
	private String imagenes = "imagenes";

	private String SQLUpdateV2 = "ALTER TABLE encuesta ADD COLUMN TIPO text DEFAULT '0'";
	private String SQLUpdateV3 = "ALTER TABLE encuesta ADD COLUMN RESPONSABLEPITA text DEFAULT '0'";
	private String SQLUpdateV4 = "ALTER TABLE encuesta ADD COLUMN CORREO text DEFAULT '@'";


	String query5 = "CREATE TABLE "+bitacora+"("+_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
			" fecha TIMESTAMP default (datetime('now', 'localtime')), fechabitacora DATE, CAUSA TEXT, FOLIO TEXT , IDBITACORA," +
			" estado INTEGER DEFAULT 0);";

	public Handler_sqlite(Context ctx) {

		super(ctx, "mibase", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
			// FOLIOENCUESTA = _ID imei fecha de la tabla encuesta
		String query = "CREATE TABLE "+ encuesta +"("+_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, FOLIOENCUESTA text, IDCUESTIONARIO text, " +
				"CODIGORESULTADO text DEFAULT '0', CVEENT text, CVEMUN text, CVELOC text, COLONIA text, RECINTOADUANAL text, EMPRESA text, " +
				"CALLE text, NUMEROINT text, NUMEROEXT text, " +
				"ENTRECALLE text, YCALLE text, CP text, NOMBREENTREVISTADOR text, CLAVEENTREVISTADOR text, HORAINICIO text, HORATERMINO text, " +
				"OTRO_CODIGORESULTADO text DEFAULT '0', LATITUD text, LONGITUD text, TIPO text, RESPONSABLEPITA text DEFAULT '0', CORREO text DEFAULT '0'," +
				"CREATE_BY text, FECHAENTREVISTA TIMESTAMP default (datetime('now', 'localtime')), guardado INTEGER DEFAULT 1, " +
				"estado INTEGER DEFAULT 0, NUMPREGUNTA TEXT DEFAULT '0')";
		db.execSQL(query);

		// IDCUESTIONARIO = _ID imei fecha de la tabla encuesta
		String query4 = "CREATE TABLE "+ respuestas +"("+_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, IDCUESTIONARIO text,  " +
				"SECCION text, PREGUNTA text, RESPUESTA text, RESPUESTA_ADICIONAL text, OTRO text, CREATE_BY text, " +
				"guardado INTEGER DEFAULT 1, estado INTEGER DEFAULT 0, NUMPREGUNTA TEXT DEFAULT '0', FOLIORESPUESTA TEXT)";
		db.execSQL(query4);

		String query2 = "CREATE TABLE "+ login +"("+_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"usuario text, idUsuario text, pass text, nombre text, paterno, materno text, fecha TIMESTAMP default (datetime('now', 'localtime')), " +
				" estado INTEGER DEFAULT 0);";
		db.execSQL(query2);

		String query3 = "CREATE TABLE imagenes("+_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, imagen text DEFAULT '0', " +
				"carril text DEFAULT '0', tipo text DEFAULT '0', IDENCUESTA text DEFAULT '0'," +
				"fecha TIMESTAMP default (datetime('now', 'localtime')), " +
				"estado INTEGER DEFAULT 0);";
		db.execSQL(query3);

		db.execSQL(query5);

	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int version_ant, int version_nue)
	{
		//db.execSQL(query5);
		//db.execSQL("DROP TABLE IF EXISTS encuesta");
		//onCreate(db);
		//db.execSQL("DROP TABLE IF EXISTS encuesta3");
		//db.execSQL(SQLUpdateV2);
		//onCreate(db);

		//if(version_ant == 1 && version_nue == 2){
		//db.execSQL(SQLUpdateV2);
		//db.execSQL(SQLUpdateV3);
		//db.execSQL(SQLUpdateV4);
		//}
	}

	public void insertarMenu()
	{
		ContentValues valores = new ContentValues();
		valores.put("estado", "1");

		this.getWritableDatabase().insert("menu", null, valores);
	}

	public void insertarReg(String RAZONSOCIAL, String CREATE_BY, String TIPO)
	{
		ContentValues valores = new ContentValues();
		valores.put("RECINTOADUANAL", RAZONSOCIAL);
		valores.put("CREATE_BY", CREATE_BY);
		valores.put("TIPO", TIPO);


		if ( (RAZONSOCIAL != null) && (!RAZONSOCIAL.equals("")) ) {
			this.getWritableDatabase().insert(encuesta, null, valores);
		}
	}

	public void insertaFoto(String IDENCUESTA, String IMAGEN, String TIPO, String CARRIL)
	{
		ContentValues valores = new ContentValues();
		valores.put("IDENCUESTA", IDENCUESTA);
		valores.put("IMAGEN", IMAGEN);
		valores.put("carril", CARRIL);
		valores.put("tipo", TIPO);


		if ( (IDENCUESTA != null) && (!IDENCUESTA.equals("")) ) {
			this.getWritableDatabase().insert("imagenes", null, valores);
		}
	}


	// EMPRESA
	public void actualzaRespuesta(String id, String EMPRESA, String CAMPO, String guardado)
	{
		ContentValues valores = new ContentValues();
		valores.put(CAMPO, EMPRESA);
		valores.put("guardado", guardado);

		this.getWritableDatabase().update(encuesta, valores, "_ID=?", new String[] {id});
	}

	public void insertarPreg(String IDCUESTIONARIO, String IDENCUESTA, String RESPUESTA, String RESPUESTA_ADICIONAL, String OTRO, String NUMPREGUNTA, String FOLIORESPUESTA)
	{
		ContentValues valores = new ContentValues();
		valores.put("IDCUESTIONARIO", IDCUESTIONARIO);
		valores.put("PREGUNTA", IDENCUESTA);
		valores.put("RESPUESTA", RESPUESTA);
		valores.put("RESPUESTA_ADICIONAL", RESPUESTA_ADICIONAL);
		valores.put("OTRO", OTRO);
		valores.put("NUMPREGUNTA", NUMPREGUNTA);
		valores.put("FOLIORESPUESTA", FOLIORESPUESTA);


		if ( (IDCUESTIONARIO != null) && (!IDCUESTIONARIO.equals("")) ) {
			this.getWritableDatabase().insert(respuestas, null, valores);
		}
	}

	public void actualizaCor(String id, String lat, String lon, String HORATERMINO) {
		// TODO Auto-generated method stub
		ContentValues valores = new ContentValues();
		valores.put("guardado", "0");
		valores.put("latitud", lat);
		valores.put("longitud", lon);
		valores.put("NUMPREGUNTA", "0");
		valores.put("HORATERMINO", HORATERMINO);
		this.getWritableDatabase().update(encuesta, valores, "_ID=?", new String[] {id});
	}

	public String NUMPREGUNTA(){
		String result="0";
		String columnas[] = {"NUMPREGUNTA"};
		try{
			Cursor c = this.getReadableDatabase().query(encuesta, columnas,  null, null, null, null, null);
			c.moveToLast();
			if ( c.getCount() > 0) {
				int iu;
				iu = c.getColumnIndex("NUMPREGUNTA");
				result = c.getString(iu);
			}
		}catch(SQLiteException e){
			System.err.println("Exception @ rawQuery: " + e.getMessage());
			{
				result="0";
			}

		}
		return result;
	}

	public Cursor editarPregutas(String id){
		String columnas[] = {_ID, "NUMPREGUNTA", "RESPUESTA", "RESPUESTA_ADICIONAL", "OTRO"};
		String ide[] = {id};
		Cursor c = this.getReadableDatabase().query(respuestas, columnas, _ID+"=?", ide, null, null, null, null);

		return c;
	}

	public Cursor nueve(String id){
		String columnas[] = {_ID, "NUMPREGUNTA", "RESPUESTA", "FOLIORESPUESTA"};
		String ide[] = {id, "9"};
		Cursor c = this.getReadableDatabase().query(respuestas, columnas, "IDCUESTIONARIO=? and NUMPREGUNTA=?", ide, null, null, null, null);

		return c;
	}


	public Cursor bases(){
		String columnas [] ={_ID, "IDCUESTIONARIO", "NUMPREGUNTA"};
		Cursor c = this.getReadableDatabase().query(respuestas, columnas, null, null, null, null, null);
		return c;
	}

	public void updateBase(String id, String FOLIORESPUESTA){
		ContentValues valores = new ContentValues();
		valores.put("IDCUESTIONARIO", FOLIORESPUESTA);
		this.getWritableDatabase().update(respuestas, valores, _ID+"=?", new String[] {id});

	}
    public String NUMPREGUNTA(String id){
        String result="0";
        String columnas[] = {"NUMPREGUNTA"};
        String ids [] ={id};
        try{
            Cursor c = this.getReadableDatabase().query(respuestas, columnas,  _ID+"=?", ids, null, null, null);
            c.moveToLast();
            if ( c.getCount() > 0) {
                int iu;
                iu = c.getColumnIndex("NUMPREGUNTA");
                result = c.getString(iu);
            }
        }catch(SQLiteException e){
            System.err.println("Exception @ rawQuery: " + e.getMessage());
            {
                result="0";
            }

        }
        return result;
    }

	public String empresa(){
		String result="0";
		String columnas[] = {"EMPRESA"};
		try{
			Cursor c = this.getReadableDatabase().query(encuesta, columnas,  null, null, null, null, null);
			c.moveToLast();
			if ( c.getCount() > 0) {
				int iu;
				iu = c.getColumnIndex("EMPRESA");
				result = c.getString(iu);
			}
		}catch(SQLiteException e){
			System.err.println("Exception @ rawQuery: " + e.getMessage());
			{
				result="0";
			}

		}
		return result;
	}

	public void insertarReg2(String usuario, String nombre, String paterno, String materno, String idUsuario)
	{
		ContentValues valores = new ContentValues();
		valores.put("usuario", usuario);
		valores.put("nombre", nombre);
		valores.put("paterno", paterno);
		valores.put("materno", materno);
        valores.put("idUsuario", idUsuario);
		valores.put("pass", "1");

		if ( (usuario != null) && (!usuario.equals("")) ) {
			this.getWritableDatabase().insert(login, null, valores);
		}
	}

	public void insertarBitacora(String fechabitacora, String CAUSA, String FOLIO, String IDBITACORA)
	{
		ContentValues valores = new ContentValues();
		valores.put("fechabitacora", fechabitacora);
		valores.put("CAUSA", CAUSA);
		valores.put("FOLIO", FOLIO);
		valores.put("IDBITACORA", IDBITACORA);

		if ( (CAUSA != null) && (!CAUSA.equals("")) ) {
			this.getWritableDatabase().insert(bitacora, null, valores);
		}
	}

	public Cursor borrar(){
		String columnas[] = {_ID, "pass"};
		Cursor c = this.getReadableDatabase().query(login, columnas, null, null, null, null, null, "0,3");
		return c;
	}

	public String usuario(){
		String result="";
		String columnas[] = {"nombre","paterno", "materno"};
		try{
			Cursor c = this.getReadableDatabase().query(login, columnas,  null, null, null, null, null);
			c.moveToLast();
			if ( c.getCount() > 0) {

				result = c.getString(0)+ " " + c.getString(1) + " " + c.getString(2);
			}
		}catch(SQLiteException e){
			System.err.println("Exception @ rawQuery: " + e.getMessage());
			{
				result="";
			}

		}
		return result;
	}

	public String idusuario(){
		String result="";
		String columnas[] = {"usuario"};
		try{
			Cursor c = this.getReadableDatabase().query(login, columnas,  null, null, null, null, null);
			c.moveToLast();
			if ( c.getCount() > 0) {

				result = c.getString(0);
			}
		}catch(SQLiteException e){
			System.err.println("Exception @ rawQuery: " + e.getMessage());
			{
				result="";
			}

		}
		return result;
	}


	public void actualizaDomicilio(String id, String cp, String calle, String numext, String numint, String cveedo,
								   String cvemun, String FOLIOENCUESTA, String imei, String colonia, String HORAINICIO,
								   String NOMBREENTREVISTADOR, String CLAVEENTREVISTADOR) {
		// TODO Auto-generated method stub
		ContentValues valores = new ContentValues();
		valores.put("guardado", "2");
		valores.put("CP", cp);
		valores.put("CALLE", calle);
		valores.put("NUMEROEXT", numext);
		valores.put("NUMEROINT", numint);
		valores.put("CVEENT", cveedo);
		valores.put("CVEMUN", cvemun);
		valores.put("FOLIOENCUESTA", FOLIOENCUESTA);
		valores.put("CREATE_BY", imei);
		valores.put("COLONIA", colonia);
		valores.put("HORAINICIO", HORAINICIO);
		valores.put("NOMBREENTREVISTADOR", NOMBREENTREVISTADOR);
		valores.put("CLAVEENTREVISTADOR", CLAVEENTREVISTADOR);

		this.getWritableDatabase().update(encuesta, valores, "_ID=?", new String[] {id});
	}

	public void actualizaPregunta(String id, String NUMPREGUNTA ) {
		// TODO Auto-generated method stub
		ContentValues valores = new ContentValues();
		valores.put("NUMPREGUNTA", NUMPREGUNTA);
		this.getWritableDatabase().update(encuesta, valores, "_ID=?", new String[] {id});
	}

	public void actualizaEntidad(String id, String cveedo, String cvemun,  String cveloc, String colonia) {
		// TODO Auto-generated method stub
		ContentValues valores = new ContentValues();
		valores.put("guardado", "3");
		valores.put("CVEENT", cveedo);
		valores.put("CVEMUN", cvemun);
		valores.put("CVELOC", cveloc);
		valores.put("COLONIA", colonia);

		this.getWritableDatabase().update(encuesta, valores, "_ID=?", new String[] {id});
	}

	public void actualizaEntidad(String id, String cveloc) {
		// TODO Auto-generated method stub
		ContentValues valores = new ContentValues();
		valores.put("guardado", "3");
		valores.put("CVELOC", cveloc);

		this.getWritableDatabase().update(encuesta, valores, "_ID=?", new String[] {id});
	}

	public void actualizaCalles(String id, String calle1, String calle2) {
		// TODO Auto-generated method stub
		ContentValues valores = new ContentValues();
		valores.put("guardado", "4");
		valores.put("ENTRECALLE", calle1);
		valores.put("YCALLE", calle2);

		this.getWritableDatabase().update(encuesta, valores, "_ID=?", new String[] {id});
	}


	public Cursor revisar(){
		String columnas[] = {_ID, "FOLIOENCUESTA", "RECINTOADUANAL"};
		String[] ident= {"0"};
		Cursor c = this.getReadableDatabase().query(encuesta, columnas, null, null, null, null, null);
		return c;
	}

	public Cursor bitacora(String FOLIORESPUESTA){
		String columnas[] = {_ID, "fechabitacora", "CAUSA"};
		String[] ident= {FOLIORESPUESTA};
		Cursor c = this.getReadableDatabase().query(bitacora, columnas, "FOLIO=?", ident, null, null, null);
		return c;
	}

	public Cursor respuestas(String FOLIOENCUESTA){
		String columnas[] = {_ID, "PREGUNTA", "IDCUESTIONARIO", "NUMPREGUNTA"};
		String[] ident= {FOLIOENCUESTA};
		Cursor c = this.getReadableDatabase().query(respuestas, columnas, "IDCUESTIONARIO=?", ident, null, null, null);
		return c;
	}

	public Cursor entidad(String id){
		String columnas[] = {_ID, "CVEENT", "CVEMUN"};
		String[] ident= {id};
		Cursor c = this.getReadableDatabase().query(encuesta, columnas, _ID+"=?", ident, null, null, null);
		return c;
	}

	public void actualizaBase(String id, String guardado, String estado) {
		// TODO Auto-generated method stub
		ContentValues valores = new ContentValues();
		valores.put("guardado", guardado);
		valores.put("estado", estado);

		this.getWritableDatabase().update(encuesta, valores, "_ID=?", new String[] {id});
	}

	public void actualizaGuardado(String cuis, String guardado) {
		// TODO Auto-generated method stub
		ContentValues valores = new ContentValues();
		valores.put("guardado", guardado);

		this.getWritableDatabase().update(encuesta, valores, "_ID=?", new String[] {cuis});
	}

	public void actualizaMenu(String menu) {
		// TODO Auto-generated method stub
		ContentValues valores = new ContentValues();
		valores.put("estado", menu);
		this.getWritableDatabase().update("menu", valores, null, null);
	}

	public Cursor enviarEspuestas(String ide){
		String columnas[] = {_ID,  "IDCUESTIONARIO", "PREGUNTA", "RESPUESTA", "RESPUESTA_ADICIONAL", "OTRO", "FOLIORESPUESTA"};

		String[] ident= {ide};															// group by Having order by, limit
		Cursor c = this.getReadableDatabase().query(respuestas, columnas, "estado=?", ident, null, null, null, "0,30");
		return c;
	}

	public Cursor enviarImagenes(String ide){
		String columnas[] = {_ID,  "imagen", "carril", "tipo", "IDENCUESTA", "fecha"};

		String[] ident= {ide};															// group by Having order by, limit
		Cursor c = this.getReadableDatabase().query(imagenes, columnas, "estado=?", ident, null, null, null, "0,10");
		return c;
	}

	public Cursor enviar(String ide){
		String columnas[] = {_ID, "FOLIOENCUESTA", "CODIGORESULTADO", "CVEENT", "CVEMUN", "CVELOC", "COLONIA", "RECINTOADUANAL",
				"CALLE", "NUMEROINT", "NUMEROEXT", "ENTRECALLE", "YCALLE", "CP", "NOMBREENTREVISTADOR", "CLAVEENTREVISTADOR", "HORAINICIO",
				"HORATERMINO", "OTRO_CODIGORESULTADO", "LATITUD", "LONGITUD", "CREATE_BY", "FECHAENTREVISTA", "CORREO", "RESPONSABLEPITA"};
		String[] ident= {ide};															// group by Having order by, limit
		Cursor c = this.getReadableDatabase().query(encuesta, columnas, "estado=?", ident, null, null, null, "0,30");
		return c;
	}

	public Cursor correo(String ide){
		String columnas[] = {_ID, "FOLIOENCUESTA", "CORREO", "RESPONSABLEPITA"};
		String[] ident= {ide};															// group by Having order by, limit
		Cursor c = this.getReadableDatabase().query(encuesta, columnas, "estado=?", ident, null, null, null, "0,30");
		return c;
	}

	public Cursor enviar(){
		String columnas[] = {_ID, "FOLIOENCUESTA", "FECHAENTREVISTA", "estado"};
		//String[] ident= {ide, "1"};
		//group by Having order by, limit
		Cursor c = this.getReadableDatabase().query("encuesta", columnas, null, null, null, null, null, null);
		return c;
	}

	public String[] envio(String ide){
		String columnas[] = {_ID, "nombre", "paterno", "materno", "fecha", "fechanac", "imei", "latitud", "longitud",
				"entidadnac", "curp", "rfc", "sexo", "calle", "numext", "numint", "edocivil",
				"colonia", "municipio", "ciudad", "entidad", "cp", "pais"};

		String[] ident= {ide};
		Cursor c = this.getReadableDatabase().query(encuesta, columnas, "estado=?", ident, null, null, null, "0,30");
		int num = c.getCount();
		String result[]= new String[num];


		int contador = 0;
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext() ){

			result[contador] =
					c.getString(0)+ "---" +c.getString(1)+ "---" +c.getString(2)+ "---" +c.getString(3) + "---" +
					c.getString(4)+ "---" + c.getString(5)+ "---" + c.getString(6)+ "---" + c.getString(7) + "---" +
					c.getString(8)+ "---" + c.getString(9)+ "---" + c.getString(10)+ "---" + c.getString(11) + "---" +
					c.getString(12)+ "---" + c.getString(13)+ "---" + c.getString(14)+ "---" + c.getString(15) + "---" +
					c.getString(16)+ "---" + c.getString(17)+ "---" + c.getString(18)+ "---" + c.getString(19) + "---" +
					c.getString(20)+ "---" + c.getString(21)+ "---" + c.getString(22);
			contador++;
		}
		return result;
	}

	public void salir(String id) {
		// TODO Auto-generated method stub
		ContentValues valores = new ContentValues();
		valores.put("pass", "0");
		this.getWritableDatabase().update(login, valores, "pass=?", new String[] {id});
	}

	public void modifica(String id, String estatus, String tabla) {
		// TODO Auto-generated method stub
		ContentValues valores = new ContentValues();
		valores.put("estado", estatus);
		this.getWritableDatabase().update(tabla, valores, "_ID=?", new String[] {id});
	}

	public String guardado(){
		String result="";
		String columnas[] = {"pass"};
		try{
			Cursor c = this.getReadableDatabase().query(login, columnas,  null, null, null, null, null);
			c.moveToLast();
			if ( c.getCount() > 0) {
				int iu;
				iu = c.getColumnIndex("pass");
				result = c.getString(iu);
			}
		}catch(SQLiteException e){
			System.err.println("Exception @ rawQuery: " + e.getMessage());
			{
				result="";
			}

		}
		return result;
	}

	public String guardado2(){
		String result="0";
		String columnas[] = {"guardado"};
		try{
			Cursor c = this.getReadableDatabase().query(encuesta, columnas,  null, null, null, null, null);
			c.moveToLast();
			if ( c.getCount() > 0) {
				int iu;
				iu = c.getColumnIndex("guardado");
				result = c.getString(iu);
			}
		}catch(SQLiteException e){
			System.err.println("Exception @ rawQuery: " + e.getMessage());
			{
				result="0";
			}

		}
		return result;
	}

	public String cuis(){
		String result="0";
		String columnas[] = {"cuis"};
		try{
			Cursor c = this.getReadableDatabase().query(encuesta, columnas,  null, null, null, null, null);
			//c.moveToFirst();
			c.moveToLast();
			if ( c.getCount() > 0) {
				int iu;
				iu = c.getColumnIndex("cuis");
				result = c.getString(iu);
			}
		}catch(SQLiteException e){
			System.err.println("Exception @ rawQuery: " + e.getMessage());
			{
				result="0";
			}

		}
		return result;
	}

	public String fecha(String tabla, String fecha){
		String result="00-00-0000 00:00:00";
		String columnas[] = {fecha};
		try{
			Cursor c = this.getReadableDatabase().query(tabla, columnas,  null, null, null, null, null);
			//c.moveToFirst();
			c.moveToLast();
			if ( c.getCount() > 0) {
				int iu;
				iu = c.getColumnIndex(fecha);
				result = c.getString(iu);
			}
		}catch(SQLiteException e){
            System.err.println("Exception @ rawQuery: " + e.getMessage());
			{
				result="00-00-0000 00:00:00";
			}

		}
		return result;
	}

	public String fecha(String tabla, String fecha, String FOLIOENCUESTA){
		String result="00-00-0000 00:00:00";
		String columnas[] = {fecha};
		String[] ident= {FOLIOENCUESTA};
		try{
			Cursor c = this.getReadableDatabase().query(tabla, columnas,  "FOLIOENCUESTA=?", ident, null, null, null);
			//c.moveToFirst();
			c.moveToLast();
			if ( c.getCount() > 0) {
				int iu;
				iu = c.getColumnIndex(fecha);
				result = c.getString(iu);
			}
		}catch(SQLiteException e){
			System.err.println("Exception @ rawQuery: " + e.getMessage());
			{
				result="00-00-0000 00:00:00";
			}

		}
		return result;
	}

	public String iden(){
		String result="0";
		String columnas[] = {_ID};
		try{
			Cursor c = this.getReadableDatabase().query(encuesta, columnas,  null, null, null, null, null);
			//c.moveToFirst();
			c.moveToLast();
			if ( c.getCount() > 0) {
				int iu;
				iu = c.getColumnIndex(_ID);
				result = c.getString(iu);
			}
		}catch(SQLiteException e){
			System.err.println("Exception @ rawQuery: " + e.getMessage());
			{
				result="0";
			}

		}
		return result;
	}

	public String documento(){
		String result="0";
		String columnas[] = {"tipo"};
		try{
			Cursor c = this.getReadableDatabase().query(imagenes, columnas,  null, null, null, null, null);
			//c.moveToFirst();
			c.moveToLast();
			if ( c.getCount() > 0) {
				int iu;
				iu = c.getColumnIndex(columnas[0]);
				result = c.getString(iu);
			}
		}catch(SQLiteException e){
			System.err.println("Exception @ rawQuery: " + e.getMessage());
			{
				result="0";
			}

		}
		return result;
	}

    public String ultima(){
        String result="0";
        String columnas[] = {"tipo"};
        try{
            Cursor c = this.getReadableDatabase().query(imagenes, columnas,  null, null, null, null, null);
            //c.moveToFirst();
            c.moveToLast();
            if ( c.getCount() > 0) {
                int iu;
                iu = c.getColumnIndex(columnas[0]);
                result = c.getString(iu);
            }
        }catch(SQLiteException e){
            System.err.println("Exception @ rawQuery: " + e.getMessage());
            {
                result="0";
            }

        }
        return result;
    }

	public String idenFoto(){
		String result="0";
		String columnas[] = {_ID};
		try{
			Cursor c = this.getReadableDatabase().query("imagenes", columnas,  null, null, null, null, null);
			//c.moveToFirst();
			c.moveToLast();
			if ( c.getCount() > 0) {
				int iu;
				iu = c.getColumnIndex(_ID);
				result = c.getString(iu);
			}
		}catch(SQLiteException e){
			System.err.println("Exception @ rawQuery: " + e.getMessage());
			{
				result="0";
			}

		}
		return result;
	}

	public String FOLIOENCUESTA(){
		String result = "0";
		String columnas[] = {"FOLIOENCUESTA"};

		try{
			Cursor c = this.getReadableDatabase().query(encuesta, columnas,  null, null, null, null, null);
			//c.moveToFirst();
			c.moveToLast();
			if ( c.getCount() > 0) {
				int iu;
				iu = c.getColumnIndex("FOLIOENCUESTA");
				result = c.getString(iu);
			}
		}catch(SQLiteException e){
			System.err.println("Exception @ rawQuery: " + e.getMessage());
			{
				result="0";
			}

		}
		return result;
	}

	public String base(){
		String result="0";
		String columnas[] = {"estado"};
		String[] ident= {"1"};
		try{

			Cursor c = this.getReadableDatabase().query(login, columnas, "estado=?", ident, null, null, null, "0,1");
			//c.moveToFirst();
			c.moveToLast();
			if ( c.getCount() > 0) {
				int iu;
				iu = c.getColumnIndex(_ID);
				result = c.getString(iu);
			}
		}catch(SQLiteException e){
			System.err.println("Exception @ rawQuery: " + e.getMessage());
			{
				result="0";
			}

		}
		return result;
	}

	public Cursor checarMenu(){
		String columnas[] = {_ID, "estado"};
		Cursor c = this.getReadableDatabase().query("menu", columnas, null, null, null, null, null);
		return c;
	}

	public void abrir() {
		// abrir base
		this.getWritableDatabase();
	}

	public void cerrar() {

		this.close();
	}
}