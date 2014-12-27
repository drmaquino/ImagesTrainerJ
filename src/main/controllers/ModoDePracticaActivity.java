package main.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

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

    public void launchEncodingActivity()
    {
        Intent intent = new Intent(this, DesdeNombreHaciaImagenActivity.class);
        intent.putExtra("carpeta", carpeta);
        startActivity(intent);
    }

    public void launchDecodingActivity()
    {
        Intent intent = new Intent(this, DesdeImagenHaciaNombreActivity.class);
        intent.putExtra("carpeta", carpeta);
        startActivity(intent);
    }

    public void comenzarPractica(View view)
    {
        String texto_desde = desde.getSelectedItem().toString();
        String texto_hacia = hacia.getSelectedItem().toString();
        
        if (texto_desde.equals(texto_hacia))
        {
            Toast.makeText(getBaseContext(), "No puedes elegir la misma opcion dos veces", Toast.LENGTH_SHORT).show();
        }
        else if (texto_desde.equals("Imagen") && texto_hacia.equals("Nombre"))
        {
            launchDecodingActivity();
        }
        else if (texto_desde.equals("Nombre") && texto_hacia.equals("Imagen"))
        {
            launchEncodingActivity();
        }
    }
}
