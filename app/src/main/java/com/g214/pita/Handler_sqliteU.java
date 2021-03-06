package com.g214.pita;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;

public class Handler_sqliteU extends SQLiteOpenHelper {

	private String SQLUpdateV2 = "ALTER TABLE encuesta3 ADD COLUMN obs text";
	private String query3 = "CREATE TABLE registro(_ID INTEGER PRIMARY KEY AUTOINCREMENT, PUNTOTACTICO TEXT, ENTIDAD TEXT, MUNICIPIO, TEXT, LOCALIDAD TEXT);";
	public Handler_sqliteU(Context ctx) {

		super(ctx, "universo", null, 2);

	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		
		String query = "CREATE TABLE municipios(_ID INTEGER PRIMARY KEY AUTOINCREMENT, C_ENTIDAD INTEGER, C_MUNICIPIO INTEGER, " +
				"NOM_MUNICIPIO text);";
		db.execSQL(query);

		String query2 = "CREATE TABLE localidad(_ID INTEGER PRIMARY KEY AUTOINCREMENT, C_ENTIDAD INTEGER, C_MUNICIPIO INTEGER, " +
				"C_LOCALIDAD INTEGER, NOM_LOCALIDAD text);";
		db.execSQL(query2);
        db.execSQL(query3);

	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int version_ant, int version_nue)
	{


		db.execSQL("DROP TABLE IF EXISTS registro");
        db.execSQL(query3);
		//onCreate(db);

	}

	public Cursor buscarnombre(String PUNTOTACTICO){
		String columnas[] = {"_ID", "PUNTOTACTICO"};
		String bus = "PUNTOTACTICO like ?";
		String columnas2[] = {PUNTOTACTICO};
		Cursor c = this.getReadableDatabase().query("registro", columnas, bus, columnas2, null, null, null, "0,30");

		return c;
	}

	public Cursor cursor(){
		String columnas[] = {_ID, "C_MUNICIPIO", "NOM_MUNICIPIO"};
		Cursor c = this.getReadableDatabase().query("municipios", columnas, null, null, null, null, null);
		return c;
	}

	public Boolean cursorPita(){
		String columnas[] = {_ID, "PUNTOTACTICO"};
		boolean isExist = false;
		Cursor c = this.getReadableDatabase().query("registro", columnas, null, null, null, null, null);
		if (c != null) {
			if (c.getCount() < 1) {
				isExist = true;
			}
		}
		c.close();
		return isExist;
	}

	public int prueba(){
		String columnas[] = {_ID};

		Cursor c = this.getReadableDatabase().query("registro", columnas, null, null, null, null, null);
		if (c != null) {
			c.getCount();
		}
		c.close();
		return c.getCount();
	}
	public Cursor municipio(String C_ENTIDAD){
		String columnas[] = {_ID, "C_MUNICIPIO", "NOM_MUNICIPIO"};
		String[] ident= {C_ENTIDAD};
		Cursor c = this.getReadableDatabase().query("municipios", columnas, "C_ENTIDAD=?", ident, null, null, null, null);
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
