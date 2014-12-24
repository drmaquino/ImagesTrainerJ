package main.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

public class ModoDePracticaActivity extends Activity
{
    private String carpeta;
    private Spinner desde;
    private Spinner hacia;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_de_practica);

        desde = (Spinner) findViewById(R.id.spinner_desde);
        hacia = (Spinner) findViewById(R.id.spinner_hacia);

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

    public void comenzarPractica(View view)
    {
        Intent intent = new Intent(this, DecodingActivity.class);
        
        String texto_desde = desde.getSelectedItem().toString();
        String texto_hacia = hacia.getSelectedItem().toString();

        intent.putExtra("carpeta", carpeta);
        intent.putExtra("desde", texto_desde);
        intent.putExtra("hasta", texto_hacia);
        
        startActivity(intent);
    }
}
