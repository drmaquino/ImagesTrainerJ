package main.controllers;

import java.util.Collections;

import main.model.Imagen;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class DesdeDescripcionHaciaImagenActivity extends MultipleChoiceActivity
{
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
            ImageButton imageButton = (ImageButton) respuestas.get(i);
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
}
