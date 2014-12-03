package main.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
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
			file = new File(mFolder.getAbsolutePath(), "readme.txt");
			saveToFile(file, "aca sueltos van los archivos de imagen.", false);
		}
		catch (Exception e)
		{
			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
				if (tmpf.getName().endsWith(".jpg"))
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
			for (File tmpf : filenames)
			{
				if (tmpf.getName().endsWith(".jpg"))
				{
					files.add(tmpf.getName());
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

	private Context getBaseContext()
	{
		return this._context;
	}
}
