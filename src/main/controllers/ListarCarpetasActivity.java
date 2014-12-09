package main.controllers;

import java.util.List;

import main.helper.DBHelper;
import main.helper.IOHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_about:
                crearDialogoAbout().show();
                return true;
            case R.id.action_help:
            	crearDialogoHelp().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private AlertDialog crearDialogoAbout()
    {
        Builder dbAbout = new AlertDialog.Builder(this);
        dbAbout.setTitle("Images Trainer");
        String msg = "";
        msg += "\nCreado por:";
        msg += "\nMariano Aquino";
        msg += "\nVersión 1.0";
        msg += "\n06/12/2014";
        msg += "\n";
        dbAbout.setMessage(msg);
        dbAbout.setNeutralButton("Cerrar", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                dialog.dismiss();
            }
        });
        return dbAbout.create();
    }
    
    private AlertDialog crearDialogoHelp()
    {
        Builder dbAbout = new AlertDialog.Builder(this);
        dbAbout.setTitle("Help");
        String msg = "";
        msg += "\nAcá va la ayuda!";
        msg += "\n";
        dbAbout.setMessage(msg);
        dbAbout.setNeutralButton("Cerrar", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                dialog.dismiss();
            }
        });
        return dbAbout.create();
    }
}