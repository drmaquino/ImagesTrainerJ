package main.controllers;

import java.util.List;

import main.helper.DBHelper;
import main.helper.IOHelper;
import main.model.Imagen;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends Activity
{
	private IOHelper ioh;
	private DBHelper dbh;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		ioh = new IOHelper(this);
		dbh = new DBHelper(this);
		ioh.createGameFolder();
		synchronizeDB();
	}

	public void launchEncodingActivity(View view)
	{
		Intent intent = new Intent(this, EncodingActivity.class);
		startActivity(intent);
	}

	public void launchDecodingActivity(View view)
	{
		 Intent intent = new Intent(this, DecodingActivity.class);
		 startActivity(intent);
	}

	public void synchronizeDB()
	{
		List<String> foldersInGameFolder = ioh.getListFoldersInGameFolder();
		
		for (String carpeta : foldersInGameFolder)
		{
			List<String> imagesInFolder = ioh.getListImagesInFolder(carpeta);
			List<Imagen> imagesInDB = dbh.findImagenesByFolder(carpeta);
			
			for (Imagen imagen : imagesInDB)
			{
				int existe = imagesInFolder.indexOf(imagen.get_nombre());
				if (existe == -1)
				{
					dbh.deleteImagen(imagen);
				}
				imagesInFolder.remove(imagen.get_nombre());
			}

			for (String imagen : imagesInFolder)
			{
				Imagen i = new Imagen();
				i.set_nombre(imagen);
				i.set_carpeta(carpeta);
				i.set_estado("pendiente");
				dbh.addImagen(i);
			}			
		}
	}
}
