package main.controllers;

import java.util.Collections;

import main.model.Imagen;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class DesdeDescripcionHaciaNombreActivity extends MultipleChoiceActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void localizarLayout()
    {
        setContentView(R.layout.activity_desde_texto_hacia_texto);
    }
    
    @Override
    protected void mostrarPregunta()
    {
        if (imagenCorrecta != null)
        {
            TextView tv = (TextView) pregunta;
            String desc = imagenCorrecta.get_descripcion().equals("") ? imagenCorrecta.get_nombre() : imagenCorrecta.get_descripcion();
            tv.setText(capitalize(desc));
        }
    }

    @Override
    protected void mostrarRespuestasPosibles()
    {
        Collections.shuffle(imagenesParaMostrar);

        for (int i = 0; i < imagenesParaMostrar.size(); i++)
        {
            Button pairButton = (Button) respuestas.get(i);
            Imagen imagen = imagenesParaMostrar.get(i);
            
            pairButton.setContentDescription(imagen.get_nombre());

            String desc = imagen.get_nombre();
            pairButton.setText(capitalize(desc));
        }
    }
}
