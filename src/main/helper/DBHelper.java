package main.helper;

import java.util.ArrayList;
import java.util.List;

import main.model.MemoImage;
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

	// Images table name
	private static final String TABLE_IMAGES = "images";

	// Common Table Columns names
	private static final String KEY_ID = "id";

	// Images Table Columns names
	private static final String KEY_PAR = "par";
	private static final String KEY_NAME = "name";
	private static final String KEY_SRC = "src";
	private static final String KEY_STATE = "state";

	private static String CREATE_TABLE_IMAGES = "CREATE TABLE " + TABLE_IMAGES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PAR + " TEXT," + KEY_NAME + " TEXT," + KEY_SRC + " TEXT" + KEY_STATE + " TEXT" + ")";

	public DBHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREATE_TABLE_IMAGES);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations for IMAGES
	 */

	public void addImagen(MemoImage imagen)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_PAR, imagen.get_par());
		values.put(KEY_NAME, imagen.get_name());
		values.put(KEY_SRC, imagen.get_src());
		values.put(KEY_STATE, imagen.get_state());
		db.insert(TABLE_IMAGES, null, values);
		db.close();
	}

	public MemoImage getImageById(String id)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		MemoImage imagen = null;

		Cursor cursor = db.query(TABLE_IMAGES, new String[] { KEY_ID, KEY_PAR, KEY_NAME, KEY_SRC, KEY_STATE }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor.moveToFirst())
		{
			imagen = new MemoImage();
			imagen.set_id(Integer.parseInt(cursor.getString(0)));
			imagen.set_par(cursor.getString(1));
			imagen.set_name(cursor.getString(2));
			imagen.set_src(cursor.getString(3));
			imagen.set_state(cursor.getString(4));
			db.close();
		}
		return imagen;
	}

	public MemoImage getImageByPar(String par)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		MemoImage imagen = null;

		Cursor cursor = db.query(TABLE_IMAGES, new String[] { KEY_ID, KEY_PAR, KEY_NAME, KEY_SRC, KEY_STATE }, KEY_PAR + "=?", new String[] { String.valueOf(par) }, null, null, null, null);
		if (cursor.moveToFirst())
		{
			imagen = new MemoImage();
			imagen.set_id(Integer.parseInt(cursor.getString(0)));
			imagen.set_par(cursor.getString(1));
			imagen.set_name(cursor.getString(2));
			imagen.set_src(cursor.getString(3));
			imagen.set_state(cursor.getString(4));
			db.close();
		}
		return imagen;
	}

	public int updateImagen(MemoImage imagen)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_PAR, imagen.get_par());
		values.put(KEY_NAME, imagen.get_name());
		values.put(KEY_SRC, imagen.get_src());
		values.put(KEY_STATE, imagen.get_state());

		// updating row
		return db.update(TABLE_IMAGES, values, KEY_ID + " = ?", new String[] { String.valueOf(imagen.get_id()) });
	}

	public void deleteTabajo(MemoImage imagen)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_IMAGES, KEY_ID + " = ?", new String[] { String.valueOf(imagen.get_id()) });
		db.close();
	}

	public List<MemoImage> getAllImages()
	{
		List<MemoImage> images = new ArrayList<MemoImage>();
		SQLiteDatabase db = this.getWritableDatabase();
		String selectQuery = "SELECT  * FROM " + TABLE_IMAGES;
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst())
		{
			do
			{
				MemoImage imagen = new MemoImage();
				imagen.set_id(Integer.parseInt(cursor.getString(0)));
				imagen.set_par(cursor.getString(1));
				imagen.set_name(cursor.getString(2));
				imagen.set_src(cursor.getString(3));
				imagen.set_state(cursor.getString(4));
				images.add(imagen);
			}
			while (cursor.moveToNext());
		}
		return images;
	}

	public MemoImage getImagesByState(String state)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		MemoImage imagen = null;

		Cursor cursor = db.query(TABLE_IMAGES, new String[] { KEY_ID, KEY_PAR, KEY_NAME, KEY_SRC, KEY_STATE }, KEY_STATE + "=?", new String[] { String.valueOf(state) }, null, null, null, null);
		if (cursor.moveToFirst())
		{
			imagen = new MemoImage();
			imagen.set_id(Integer.parseInt(cursor.getString(0)));
			imagen.set_par(cursor.getString(1));
			imagen.set_name(cursor.getString(2));
			imagen.set_state(cursor.getString(3));
			db.close();
		}
		return imagen;
	}

	public int getImagesCount()
	{
		String countQuery = "SELECT * FROM " + TABLE_IMAGES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.moveToFirst();
		cursor.close();
		return cursor.getCount();
	}

	public List<MemoImage> findImagesByState(String state)
	{
		List<MemoImage> images = new ArrayList<MemoImage>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_IMAGES, new String[] { KEY_ID, KEY_PAR, KEY_NAME, KEY_SRC, KEY_STATE }, KEY_STATE + " = ?", new String[] { String.valueOf(state) }, null, null, null, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst())
		{
			do
			{
				MemoImage imagen = new MemoImage();
				imagen.set_id(Integer.parseInt(cursor.getString(0)));
				imagen.set_par(cursor.getString(1));
				imagen.set_name(cursor.getString(2));
				imagen.set_src(cursor.getString(3));
				imagen.set_state(cursor.getString(4));
				images.add(imagen);
			}
			while (cursor.moveToNext());
		}
		return images;
	}

	public void regenerateDB()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
		onCreate(db);
		db.close();
	}
}
