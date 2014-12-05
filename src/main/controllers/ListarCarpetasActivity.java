package main.controllers;

import java.util.List;

import main.helper.DBHelper;
import main.helper.IOHelper;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListarCarpetasActivity extends Activity
{
	private ListView lvCarpetas;
	private IOHelper ioh;
	protected DBHelper dbh;
	List<String> carpetas;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listar_carpetas);

		lvCarpetas = (ListView) findViewById(R.id.listaDeCarpetas);

		ioh = new IOHelper(this);
		dbh = new DBHelper(this);

		ioh.createGameFolder();

		carpetas = ioh.getListFoldersInGameFolder();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, carpetas)
		{
			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				View view = super.getView(position, convertView, parent);

				TextView textView = (TextView) view.findViewById(android.R.id.text1);

				/* YOUR CHOICE OF COLOR */
				textView.setTextColor(Color.WHITE);

				return view;
			}
		};
		lvCarpetas.setAdapter(adapter);

		lvCarpetas.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View v, int position, long id)
			{
				String carpeta = carpetas.get(position);
				Toast.makeText(getApplicationContext(), "sincronizando \"" + carpeta + "\"...", Toast.LENGTH_SHORT).show();
				dbh.sincronizarCarpeta(carpeta);
				Intent intent = new Intent(getApplicationContext(), ListarJuegosActivity.class);
				intent.putExtra("carpeta", carpeta);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle presses on the action bar items
		switch (item.getItemId())
		{
			case R.id.action_search:
				// openSearch();
				Toast.makeText(this, "search...", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.action_compose:
				// composeMessage();
				Toast.makeText(this, "compose...", Toast.LENGTH_SHORT).show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}