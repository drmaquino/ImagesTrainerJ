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

    // I/O Helper
    private IOHelper ioh;

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
    private static final String KEY_CARPETA = "carpeta";
    private static final String KEY_ESTADO = "estado";
    private static final String KEY_DESCRIPCION = "descripcion";

    private static String CREATE_TABLE_IMAGENES = "CREATE TABLE " + TABLE_IMAGENES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NOMBRE + " TEXT," + KEY_CARPETA + " TEXT," + KEY_ESTADO + " TEXT," + KEY_DESCRIPCION + " TEXT" + ")";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ioh = new IOHelper(context);
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
        values.put(KEY_CARPETA, imagen.get_carpeta());
        values.put(KEY_ESTADO, imagen.get_estado());
        values.put(KEY_DESCRIPCION, imagen.get_descripcion());
        db.insert(TABLE_IMAGENES, null, values);
        db.close();

    }

    public Imagen getImagenById(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Imagen imagen = new Imagen();

        Cursor cursor = db.query(TABLE_IMAGENES, new String[] { KEY_ID, KEY_NOMBRE, KEY_CARPETA, KEY_ESTADO, KEY_DESCRIPCION }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor.moveToFirst())
        {
            imagen.set_id(Integer.parseInt(cursor.getString(0)));
            imagen.set_nombre(cursor.getString(1));
            imagen.set_carpeta(cursor.getString(2));
            imagen.set_estado(cursor.getString(3));
            imagen.set_descripcion(cursor.getString(4));
            db.close();
        }
        else
            imagen = null;

        return imagen;

    }

    public List<Imagen> findAllImagenes()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Imagen> imagenes = new ArrayList<Imagen>();

        Cursor cursor = db.query(TABLE_IMAGENES, new String[] { KEY_ID, KEY_NOMBRE, KEY_CARPETA, KEY_ESTADO, KEY_DESCRIPCION }, null, null, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do
            {
                Imagen imagen = new Imagen();
                imagen.set_id(Integer.parseInt(cursor.getString(0)));
                imagen.set_nombre(cursor.getString(1));
                imagen.set_carpeta(cursor.getString(2));
                imagen.set_estado(cursor.getString(3));
                imagen.set_descripcion(cursor.getString(4));
                imagenes.add(imagen);
            }
            while (cursor.moveToNext());
        }
        db.close();

        return imagenes;
    }

    public List<Imagen> findImagenesByEstado(String estado)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Imagen> imagenes = new ArrayList<Imagen>();

        Cursor cursor = db.query(TABLE_IMAGENES, new String[] { KEY_ID, KEY_NOMBRE, KEY_CARPETA, KEY_ESTADO, KEY_DESCRIPCION }, KEY_ESTADO + "=?", new String[] { String.valueOf(estado) }, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do
            {
                Imagen imagen = new Imagen();
                imagen.set_id(Integer.parseInt(cursor.getString(0)));
                imagen.set_nombre(cursor.getString(1));
                imagen.set_carpeta(cursor.getString(2));
                imagen.set_estado(cursor.getString(3));
                imagen.set_descripcion(cursor.getString(4));
                imagenes.add(imagen);
            }
            while (cursor.moveToNext());
        }
        db.close();

        return imagenes;
    }

    public List<Imagen> findImagenesByCarpetaEstado(String carpeta, String estado)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Imagen> imagenes = new ArrayList<Imagen>();

        Cursor cursor = db.query(TABLE_IMAGENES, new String[] { KEY_ID, KEY_NOMBRE, KEY_CARPETA, KEY_ESTADO, KEY_DESCRIPCION }, KEY_CARPETA + "=?" + " AND " + KEY_ESTADO + " =?", new String[] { carpeta, estado }, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do
            {
                Imagen imagen = new Imagen();
                imagen.set_id(Integer.parseInt(cursor.getString(0)));
                imagen.set_nombre(cursor.getString(1));
                imagen.set_carpeta(cursor.getString(2));
                imagen.set_estado(cursor.getString(3));
                imagen.set_descripcion(cursor.getString(4));
                imagenes.add(imagen);
            }
            while (cursor.moveToNext());
        }
        db.close();

        return imagenes;
    }

    public List<Imagen> findImagenesByFolder(String carpeta)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Imagen> imagenes = new ArrayList<Imagen>();

        Cursor cursor = db.query(TABLE_IMAGENES, new String[] { KEY_ID, KEY_NOMBRE, KEY_CARPETA, KEY_ESTADO, KEY_DESCRIPCION }, KEY_CARPETA + "=?", new String[] { String.valueOf(carpeta) }, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do
            {
                Imagen imagen = new Imagen();
                imagen.set_id(Integer.parseInt(cursor.getString(0)));
                imagen.set_nombre(cursor.getString(1));
                imagen.set_carpeta(cursor.getString(2));
                imagen.set_estado(cursor.getString(3));
                imagen.set_descripcion(cursor.getString(4));
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

    public void deleteImagen(Imagen imagen)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_IMAGENES, KEY_ID + " = ?", new String[] { String.valueOf(imagen.get_id()) });
        db.close();
    }

    public void deleteImagenesByFolder(String folder)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_IMAGENES, KEY_CARPETA + " = ?", new String[] { folder });
        db.close();
    }

    public int countImagenes()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_IMAGENES, new String[] { KEY_ID, KEY_NOMBRE, KEY_CARPETA, KEY_ESTADO, KEY_DESCRIPCION }, null, null, null, null, null, null);
        cursor.moveToFirst();
        cursor.close();
        return cursor.getCount();
    }

    public int countImagenesByEstado(String estado)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_IMAGENES, new String[] { KEY_ID, KEY_NOMBRE, KEY_CARPETA, KEY_ESTADO, KEY_DESCRIPCION }, KEY_ESTADO + "=?", new String[] { String.valueOf(estado) }, null, null, null, null);
        cursor.moveToFirst();
        cursor.close();
        return cursor.getCount();
    }

    public List<String> getListFoldersInDB()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> folders = new ArrayList<String>();

        Cursor cursor = db.query(true, TABLE_IMAGENES, new String[] { KEY_ESTADO }, null, null, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do
            {
                folders.add(cursor.getString(0));
            }
            while (cursor.moveToNext());
        }
        db.close();

        return folders;
    }

    public void regenerateDB()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGENES);
        onCreate(db);
        db.close();
    }

    public void sincronizarDB()
    {
        List<String> foldersInGameFolder = ioh.getListFoldersInGameFolder();
        List<String> foldersInDB = getListFoldersInDB();

        for (String carpeta : foldersInGameFolder)
        {
            sincronizarCarpeta(carpeta);
        }

        for (String dbFolder : foldersInDB)
        {
            if (!foldersInGameFolder.contains(dbFolder))
            {
                deleteImagenesByFolder(dbFolder);
            }
        }
    }

    public void sincronizarCarpeta(String carpeta)
    {
        List<String> imagesInFolder = ioh.getListImagesInFolder(carpeta);
        List<Imagen> imagesInDB = this.findImagenesByFolder(carpeta);

        for (Imagen imagen : imagesInDB)
        {
            int existe = imagesInFolder.indexOf(imagen.get_nombre());
            if (existe == -1)
            {
                this.deleteImagen(imagen);
            }
            imagesInFolder.remove(imagen.get_nombre());
        }

        for (String imagen : imagesInFolder)
        {
            Imagen i = new Imagen();

            String nombre;
            String descripcion;
            
            if (imagen.contains(" (") && imagen.contains(")"))
            {
                nombre = imagen.split(" \\(")[0];
                descripcion = imagen.split(" \\(")[1].split("\\)")[0];
                
                i.set_nombre(nombre);
                i.set_descripcion(descripcion);
            }
            else
            {
                i.set_nombre(imagen);
                i.set_descripcion("");
            }

            i.set_carpeta(carpeta);
            i.set_estado("pendiente");
            this.addImagen(i);
        }
    }

    public void reiniciarImagenesByCarpeta(String carpeta)
    {
        List<Imagen> imagesInDB = this.findImagenesByFolder(carpeta);

        for (Imagen imagen : imagesInDB)
        {
            imagen.set_estado("pendiente");
            updateImagen(imagen);
        }
    }
}
