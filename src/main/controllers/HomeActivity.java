package main.controllers;

import java.util.List;

import main.helper.IOHelper;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class HomeActivity extends Activity
{
	private IOHelper ioh;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		ImageButton ib = (ImageButton) findViewById(R.id.my_image_button);
		ioh = new IOHelper(getBaseContext());
		ioh.createGameFolder();
	}

	public void launchEncodingActivity(View view)
	{
		// Intent intent = new Intent(this, EncodingActivity.class);
		// startActivity(intent);
		Toast.makeText(this, "proximamente", Toast.LENGTH_SHORT).show();
	}

	public void launchDecodingActivity(View view)
	{
		// Intent intent = new Intent(this, DecodingActivity.class);
		// startActivity(intent);
		Toast.makeText(this, "proximamente", Toast.LENGTH_SHORT).show();
	}

	public void launchTimeAttackActivity(View view)
	{
		Intent intent = new Intent(this, TimeAttackActivity.class);
		startActivity(intent);
	}

}
