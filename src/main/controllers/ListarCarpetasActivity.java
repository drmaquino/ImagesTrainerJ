package main.controllers;

import java.util.List;

import main.helper.DBHelper;
import main.helper.IOHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, carpetas);
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
}