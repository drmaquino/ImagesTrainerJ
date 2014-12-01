package main.controllers;

import java.util.List;

import main.helper.DBHelper;
import main.helper.IOHelper;
import main.model.Imagen;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends Activity
{
	private IOHelper ioh;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		ioh = new IOHelper(getBaseContext());
		ioh.createGameFolder();
	}

	public void launchEncodingActivity(View view)
	{
		 Intent intent = new Intent(this, EncodingActivity.class);
		 startActivity(intent);
	}
	
	public void launchDecodingActivity(View view)
	{
		// Intent intent = new Intent(this, DecodingActivity.class);
		// startActivity(intent);
		Toast.makeText(this, "proximamente", Toast.LENGTH_SHORT).show();
	}
	
	public void synchronizeDB(View v)
	{
		IOHelper ioh = new IOHelper(this);
		DBHelper dbh = new DBHelper(this);
		dbh.regenerateDB();
		List<String> imagesInGameFolder = ioh.getListImagesInGameFolder();
		for (String filename : imagesInGameFolder)
		{
			Imagen imagen = new Imagen();
			imagen.set_nombre(filename);
			imagen.set_estado("pendiente");
			dbh.addImagen(imagen);
		}
		int ic = dbh.countImagenes();
		Toast.makeText(this, ic + " imagenes sincronizadas", Toast.LENGTH_SHORT).show();
	}
}
