package main.helper;

import java.util.ArrayList;
import java.util.List;

import main.model.Imagen;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "mydb";

	// Imagenes table name
	private static final String TABLE_IMAGENES = "imagenes";

	// Common Table Columns names
	private static final String KEY_ID = "id";

	// Imagenes Table Columns names
	private static final String KEY_NOMBRE = "nombre";
	private static final String KEY_ESTADO = "estado";

	private static String CREATE_TABLE_IMAGENES = "CREATE TABLE " + TABLE_IMAGENES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NOMBRE + " TEXT," + KEY_ESTADO + " TEXT" + ")";

	public DBHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREATE_TABLE_IMAGENES);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGENES);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations for TRABAJOS
	 */

	public void addImagen(Imagen imagen)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NOMBRE, imagen.get_nombre());
		values.put(KEY_ESTADO, imagen.get_estado());
		db.insert(TABLE_IMAGENES, null, values);
		db.close();

	}

	public Imagen getImagenById(String id)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Imagen imagen = new Imagen();

		Cursor cursor = db.query(TABLE_IMAGENES, new String[] { KEY_ID, KEY_NOMBRE, KEY_ESTADO }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor.moveToFirst())
		{
			imagen.set_id(Integer.parseInt(cursor.getString(0)));
			imagen.set_nombre(cursor.getString(1));
			imagen.set_estado(cursor.getString(2));
			db.close();
		}
		else
			imagen = null;

		return imagen;

	}

	public List<Imagen> findImagenesByEstado(String estado)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		List<Imagen> imagenes = new ArrayList<Imagen>();

		Cursor cursor = db.query(TABLE_IMAGENES, new String[] { KEY_ID, KEY_NOMBRE, KEY_ESTADO }, KEY_ESTADO + "=?", new String[] { String.valueOf(estado) }, null, null, null, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst())
		{
			do
			{
				Imagen imagen = new Imagen();
				imagen.set_id(Integer.parseInt(cursor.getString(0)));
				imagen.set_nombre(cursor.getString(1));
				imagen.set_estado(cursor.getString(2));
				imagenes.add(imagen);
			}
			while (cursor.moveToNext());
		}
		db.close();

		return imagenes;
	}

	public int updateImagen(Imagen imagen)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ESTADO, imagen.get_estado());

		return db.update(TABLE_IMAGENES, values, KEY_ID + " = ?", new String[] { String.valueOf(imagen.get_id()) });

	}

	public void deleteTabajo(Imagen imagen)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_IMAGENES, KEY_ID + " = ?", new String[] { String.valueOf(imagen.get_id()) });
		db.close();
	}

	public int getImagenesCount()
	{
		String countQuery = "SELECT * FROM " + TABLE_IMAGENES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.moveToFirst();
		cursor.close();
		return cursor.getCount();
	}
	
	public int countImagenesByEstado(String estado)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_IMAGENES, new String[] { KEY_ID, KEY_NOMBRE, KEY_ESTADO }, KEY_ESTADO + "=?", new String[] { String.valueOf(estado) }, null, null, null, null);
		cursor.moveToFirst();
		cursor.close();
		return cursor.getCount();
	}

	public void regenerateDB()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGENES);
		onCreate(db);
		db.close();
	}
}
