package com.g214.pita;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;

public class Handler_sqlite_puntos extends SQLiteOpenHelper {

	public Handler_sqlite_puntos(Context ctx) {

		super(ctx, "puntos", null, 1);

	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{


		String query = "CREATE TABLE registro(_ID INTEGER PRIMARY KEY AUTOINCREMENT, IDPUNTOTACTICO TEXT, PUNTOTACTICO TEXT, ADUANA TEXT, CVE_ENT TEXT, " +
				"ENTIDAD TEXT, CVE_MUN text, MUNICIPIO TEXT, CVE_LOC TEXT, LOCALIDAD TEXT, DIRECCION TEXT, CP TEXT);";
		db.execSQL(query);

		String query2 = "CREATE TABLE localidad(_ID INTEGER PRIMARY KEY AUTOINCREMENT, C_ENTIDAD INTEGER, C_MUNICIPIO INTEGER, " +
				"C_LOCALIDAD INTEGER, NOM_LOCALIDAD text);";
		db.execSQL(query2);

		String query3 = "CREATE TABLE municipios(_ID INTEGER PRIMARY KEY AUTOINCREMENT, C_ENTIDAD INTEGER, C_MUNICIPIO INTEGER, " +
				"NOM_MUNICIPIO text);";
		db.execSQL(query3);
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int version_ant, int version_nue)
	{

		db.execSQL("DROP TABLE IF EXISTS registro");
		onCreate(db);

	}

	public Cursor buscarnombre(String PUNTOTACTICO){
		String columnas[] = {"_ID", "IDPUNTOTACTICO", "PUNTOTACTICO", "ADUANA", "CVE_ENT", "ENTIDAD"};
		String bus = "PUNTOTACTICO like ?";
		String columnas2[] = {PUNTOTACTICO};
		Cursor c = this.getReadableDatabase().query("registro", columnas, bus, columnas2, null, null, null, "0,30");

		return c;
	}

	public Cursor cursor(){
		String columnas[] = {_ID, "IDPUNTOTACTICO", "PUNTOTACTICO"};
		Cursor c = this.getReadableDatabase().query("registro", columnas, null, null, null, null, null);
		return c;
	}

	public Cursor punto(String IDPUNTOTACTICO){
		String columnas[] = {"PUNTOTACTICO"};
		String[] ident= {IDPUNTOTACTICO};
		Cursor c = this.getReadableDatabase().query("registro", columnas,  "IDPUNTOTACTICO=?", ident, null, null, null);
		return c;
	}

	public Cursor municipio(String C_ENTIDAD){
		String columnas[] = {_ID, "C_MUNICIPIO", "NOM_MUNICIPIO"};
		String[] ident= {C_ENTIDAD};
		Cursor c = this.getReadableDatabase().query("municipios", columnas, "C_ENTIDAD=?", ident, null, null, null, null);
		return c;
	}

	public Cursor carriles(String IDPUNTO, String TIPO_CARRIL){
		String columnas[] = {"NOMBRE_CARRIL"};
		String[] ident= {IDPUNTO, TIPO_CARRIL};
		Cursor c = this.getReadableDatabase().query("PUNTOS", columnas, "IDPUNTO=? and TIPO_CARRIL=?", ident, null, null, null, "0,30");
		return c;
	}

	public Cursor municipio(String C_ENTIDAD, String C_MUNICIPIO){
		String columnas[] = {_ID, "C_MUNICIPIO", "NOM_MUNICIPIO"};
		String[] ident= {C_ENTIDAD, C_MUNICIPIO};
		Cursor c = this.getReadableDatabase().query("municipios", columnas, "C_ENTIDAD=? AND C_MUNICIPIO=?", ident, null, null, null, null);
		return c;
	}
	public Cursor buscar(String C_ENTIDAD){
		String columnas[] = {_ID, "C_MUNICIPIO", "NOM_MUNICIPIO"};
		String[] ident= {"1"};
		Cursor c = this.getReadableDatabase().query("municipios", columnas, null, null, null, null, null);
		return c;
	}

	public Cursor localidad(String C_ENTIDAD, String C_MUNICIPIO){
		String columnas[] = {_ID, "C_LOCALIDAD", "NOM_LOCALIDAD"};
		String[] ident= {C_ENTIDAD, C_MUNICIPIO};
		Cursor c = this.getReadableDatabase().query("localidad", columnas, "C_ENTIDAD=? and C_MUNICIPIO=?", ident, null, null, null, "0,30");
		return c;
	}
	public String iden(){
		String result="0";
		String columnas[] = {_ID};
		try{
			Cursor c = this.getReadableDatabase().query("registro", columnas,  null, null, null, null, null);
			//c.moveToFirst();
			c.moveToLast();
			if ( c.getCount() > 0) {
				int iu;
				//iu = c.getColumnIndex(_ID);
				result = c.getString(0);
			}
		}catch(SQLiteException e){
			System.err.println("Exception @ rawQuery: " + e.getMessage());
			{
				result="0";
			}

		}
		return result;
	}

	public void abrir() {
		// abrir base
		this.getWritableDatabase();
	}

	public void cerrar() {
		
		this.close();
	}
}
