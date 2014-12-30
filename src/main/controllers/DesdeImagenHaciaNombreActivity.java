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

    private List<Button> pairButtons;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;

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
    protected void localizarRespuestasEnLayout()
    {
        pairButtons = new ArrayList<Button>();

        btn1 = (Button) findViewById(R.id.pair_1);
        btn2 = (Button) findViewById(R.id.pair_2);
        btn3 = (Button) findViewById(R.id.pair_3);
        btn4 = (Button) findViewById(R.id.pair_4);

        pairButtons.add(btn1);
        pairButtons.add(btn2);
        pairButtons.add(btn3);
        pairButtons.add(btn4);
    }

    @Override
    protected void mostrarRespuestasPosibles()
    {
        Collections.shuffle(imagenesParaMostrar);

        for (int i = 0; i < imagenesParaMostrar.size(); i++)
        {
            Button pairButton = pairButtons.get(i);
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
