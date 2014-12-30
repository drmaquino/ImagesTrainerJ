package main.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.model.Imagen;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class DesdeNombreHaciaImagenActivity extends MultipleChoiceActivity
{
    private TextView tvCurrentPair;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void localizarLayout()
    {
        setContentView(R.layout.activity_desde_nombre_hacia_imagen);
    }

    @Override
    protected void localizarPreguntaEnLayout()
    {
        tvCurrentPair = (TextView) findViewById(R.id.current_pair);
    }

    @Override
    protected void mostrarRespuestasPosibles()
    {
        Collections.shuffle(imagenesParaMostrar);

        for (int i = 0; i < imagenesParaMostrar.size(); i++)
        {
//            ImageButton imageButton = imageButtons.get(i);
            ImageButton imageButton = (ImageButton) respuestasButtons.get(i);
            Imagen imagen = imagenesParaMostrar.get(i);

            String nombre_real = imagen.get_filename();

            Bitmap imgBitmap = ioh.getBitmapFromFolder(carpeta, nombre_real);

            if (imgBitmap != null)
            {
                imageButton.setImageBitmap(imgBitmap);
                imageButton.setContentDescription(imagen.get_nombre());
            }
        }
    }

    @Override
    protected void mostrarPregunta()
    {
        if (imagenCorrecta != null)
        {
            tvCurrentPair.setText(capitalize(imagenCorrecta.get_nombre()));
        }
    }
}
