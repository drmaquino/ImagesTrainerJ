package main.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ModoDePracticaActivity extends Activity
{
	private String carpeta;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modo_de_practica);
		carpeta = (String) this.getIntent().getCharSequenceExtra("carpeta");
	}

	public void launchEncodingActivity(View view)
	{
		Intent intent = new Intent(this, EncodingActivity.class);
		intent.putExtra("carpeta", carpeta);
		startActivity(intent);
	}

	public void launchDecodingActivity(View view)
	{
		 Intent intent = new Intent(this, DecodingActivity.class);
		 intent.putExtra("carpeta", carpeta);
		 startActivity(intent);
	}
}
