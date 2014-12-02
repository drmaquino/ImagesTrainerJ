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
//		Toast.makeText(this, "proximamente", Toast.LENGTH_SHORT).show();
	}

	public void synchronizeDB()
	{
		List<String> imagesInGameFolder = ioh.getListImagesInGameFolder();
		List<Imagen> imagesInDB = dbh.findAllImagenes();

		for (Imagen imagen : imagesInDB)
		{
			int existe = imagesInGameFolder.indexOf(imagen.get_nombre());
			if (existe == -1)
			{
				dbh.deleteImagen(imagen);
			}
			imagesInGameFolder.remove(imagen.get_nombre());
		}

		for (String imagen : imagesInGameFolder)
		{
			Imagen i = new Imagen();
			i.set_nombre(imagen);
			i.set_estado("pendiente");
			dbh.addImagen(i);
		}
	}
}
