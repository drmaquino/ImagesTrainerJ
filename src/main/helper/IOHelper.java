package main.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class IOHelper
{
	private static final String IMAGES_FOLDER = "com.imagestrainer.imagenes";
	private Context _context;
	private String _externalStoragePath;

	public IOHelper(Context context)
	{
		this._context = context;
		this._externalStoragePath = Environment.getExternalStorageDirectory().toString();
	}

	public void createGameFolder()
	{
		File file = null;
		try
		{
			File mFolder = new File(_externalStoragePath + "/" + IMAGES_FOLDER);
			if (!mFolder.exists())
			{
				mFolder.mkdir();
			}

			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			if (!prefs.getBoolean("firstTime", false))
			{
				file = new File(mFolder.getAbsolutePath(), "readme.txt");
				String text = "";
				text += "Aca van las carpetas con los archivos de imagen deseados.\n";
				text += "Los formatos aceptados son: jpg, jpeg, y png.\n";
				text += "Los archivos pueden tener una descripcion adicional.\n";
				text += "La descripcion debe ir en el nombre del archivo, entre parentesis, separada por un espacio.";
				text += "Ejemplo:";
				text += "nombre (descripcion).jpeg";
				saveToFile(file, text, false);

				createExampleFolder();

				SharedPreferences.Editor editor = prefs.edit();
				editor.putBoolean("firstTime", true);
				editor.commit();
			}
		}
		catch (Exception e)
		{
			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	private void createExampleFolder()
	{
		try
		{
			String carpeta = "arquitectura del mundo";
			File mFolder = new File(_externalStoragePath + "/" + IMAGES_FOLDER + "/" + carpeta);
			if (!mFolder.exists())
			{
				mFolder.mkdir();
			}
			
			List<String> assets = listarAssetsEnCarpeta(carpeta); 
			
			for (String asset : assets)
			{
				File f = new File(mFolder.getAbsolutePath(), asset);
				Bitmap bm = getBitmapFromAsset(carpeta + "/" + asset);
				writeBitmapToFile(bm, f);
			}
		}
		catch (Exception e)
		{
			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	private void writeBitmapToFile(Bitmap bmp, File filename)
	{
		FileOutputStream out = null;
		try
		{
			out = new FileOutputStream(filename);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (out != null)
				{
					out.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void saveToFile(File file, CharSequence texto, boolean append)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(file, append);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fos);
			myOutWriter.append(texto);
			myOutWriter.append("\n");
			myOutWriter.close();
			fos.close();
		}
		catch (Exception e)
		{
			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	public Bitmap getBitmapFromAsset(String strName)
	{
		AssetManager assetManager = getBaseContext().getAssets();
		InputStream istr = null;
		try
		{
			istr = assetManager.open(strName);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		Bitmap bitmap = BitmapFactory.decodeStream(istr);
		return bitmap;
	}

	public Bitmap getBitmapFromImagesFolder(String fileName)
	{
		Bitmap myBitmap = null;
		File imgFile = new File(String.format("%s/%s/%s", _externalStoragePath, IMAGES_FOLDER, fileName));
		if (imgFile.exists())
		{
			myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		}
		return myBitmap;
	}

	public Bitmap getBitmapFromFolder(String folder, String fileName)
	{
		Bitmap myBitmap = null;
		File imgFile = new File(String.format("%s/%s/%s/%s", _externalStoragePath, IMAGES_FOLDER, folder, fileName));
		if (imgFile.exists())
		{
			myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		}
		return myBitmap;
	}

	public List<String> getListImagesInGameFolder()
	{
		List<String> files = new ArrayList<String>();
		File fileList = new File(String.format("%s/%s/", _externalStoragePath, IMAGES_FOLDER));
		if (fileList != null)
		{
			File[] filenames = fileList.listFiles();
			for (File tmpf : filenames)
			{
				if (tmpf.getName().endsWith(".jpg") || tmpf.getName().endsWith(".jpeg") || tmpf.getName().endsWith(".png"))
				{
					files.add(tmpf.getName());
				}
			}
		}
		return files;
	}

	public List<String> getListImagesInFolder(String folder)
	{
		List<String> files = new ArrayList<String>();
		File fileList = new File(String.format("%s/%s/%s", _externalStoragePath, IMAGES_FOLDER, folder));
		if (fileList != null)
		{
			File[] filenames = fileList.listFiles();
			if (filenames != null)
			{
				for (File tmpf : filenames)
				{
					if (tmpf.getName().endsWith(".jpg") || tmpf.getName().endsWith(".jpeg") || tmpf.getName().endsWith(".png"))
					{
						files.add(tmpf.getName());
					}
				}
			}
		}
		return files;
	}

	public List<String> getListFoldersInGameFolder()
	{
		List<String> files = new ArrayList<String>();
		File fileList = new File(String.format("%s/%s/", _externalStoragePath, IMAGES_FOLDER));
		if (fileList != null)
		{
			File[] filenames = fileList.listFiles();
			for (File tmpf : filenames)
			{
				if (!tmpf.getName().contains("."))
				{
					files.add(tmpf.getName());
				}
			}
		}
		return files;
	}

	private List<String> listarAssetsEnCarpeta(String carpeta)
	{
		List<String> assets = new ArrayList<String>();
		Resources res = getBaseContext().getResources(); // if you are in an activity
		AssetManager am = res.getAssets();
		String fileList[];
		try
		{
			fileList = am.list(carpeta);
			if (fileList != null)
			{
				for (int i = 0; i < fileList.length; i++)
				{
					assets.add(fileList[i]);
				}
			}
		}
		catch (IOException e)
		{
		}
		return assets;
	}

	private Context getBaseContext()
	{
		return this._context;
	}
}
