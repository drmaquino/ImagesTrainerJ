package main.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.model.Imagen;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class DesdeImagenHaciaNombreActivity extends MultipleChoiceActivity
{
    private ImageView tvCurrentImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void localizarLayout()
    {
        setContentView(R.layout.activity_desde_image_hacia_nombre);
    }

    @Override
    protected void localizarPreguntaEnLayout()
    {
        tvCurrentImage = (ImageView) findViewById(R.id.current_image);
    }

    @Override
    protected void mostrarRespuestasPosibles()
    {
        Collections.shuffle(imagenesParaMostrar);

        for (int i = 0; i < imagenesParaMostrar.size(); i++)
        {
            Button pairButton = (Button) respuestasButtons.get(i);
            Imagen imagen = imagenesParaMostrar.get(i);
            
            pairButton.setContentDescription(imagen.get_nombre());

            pairButton.setText(capitalize(imagen.get_nombre()));
        }
    }

    @Override
    protected void mostrarPregunta()
    {
        if (imagenCorrecta != null)
        {
            String nombre_real = imagenCorrecta.get_filename();
            
            Bitmap imgBitmap = ioh.getBitmapFromFolder(carpeta, nombre_real);

            if (imgBitmap != null)
            {
                tvCurrentImage.setImageBitmap(imgBitmap);
            }
        }
    }
}
