package main.controllers;

import main.helper.DBHelper;
import main.helper.IOHelper;
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
}
