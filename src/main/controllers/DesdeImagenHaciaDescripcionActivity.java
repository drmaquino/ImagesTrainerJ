package main.controllers;

import java.util.Collections;

import main.model.Imagen;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class DesdeImagenHaciaDescripcionActivity extends MultipleChoiceActivity
{
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
    protected void mostrarPregunta()
    {
        if (imagenCorrecta != null)
        {
            String nombre_real = imagenCorrecta.get_filename();
            
            Bitmap imgBitmap = ioh.getBitmapFromFolder(carpeta, nombre_real);

            if (imgBitmap != null)
            {
                ImageView iv = (ImageView) pregunta;
                iv.setImageBitmap(imgBitmap);
            }
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

            String desc = imagen.get_descripcion().equals("") ? imagen.get_nombre() : imagen.get_descripcion();
            pairButton.setText(capitalize(desc));
        }
    }
}
