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

	private List<ImageButton> imageButtons;
	private ImageButton btn1;
	private ImageButton btn2;
	private ImageButton btn3;
	private ImageButton btn4;

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
	protected void localizarRespuestasEnLayout()
	{
		imageButtons = new ArrayList<ImageButton>();

		btn1 = (ImageButton) findViewById(R.id.btn1);
		btn2 = (ImageButton) findViewById(R.id.btn2);
		btn3 = (ImageButton) findViewById(R.id.btn3);
		btn4 = (ImageButton) findViewById(R.id.btn4);

		imageButtons.add(btn1);
		imageButtons.add(btn2);
		imageButtons.add(btn3);
		imageButtons.add(btn4);
	}

	@Override
	protected void mostrarRespuestasPosibles()
	{
		Collections.shuffle(imagenesParaMostrar);

		for (int i = 0; i < imagenesParaMostrar.size(); i++)
		{
			ImageButton imageButton = imageButtons.get(i);
			Imagen imagen = imagenesParaMostrar.get(i);
			
			String nombre_real;
            if (!imagenCorrecta.get_descripcion().equals(""))
            {
                nombre_real = String.format("%s (%s)", imagen.get_nombre(), imagen.get_descripcion());
            }
            else
            {
                nombre_real = imagen.get_nombre();
            }

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
			String nombre = imagenCorrecta.get_nombre().split("\\.")[0];
			if (nombre.length() > 2)
			{
				tvCurrentPair.setText(nombre.substring(0, 1).toUpperCase() + nombre.substring(1, nombre.length()).toLowerCase());
			}
			else
			{
				tvCurrentPair.setText(nombre.toUpperCase());
			}
		}
	}

	public void buttonClick(View v)
	{
		ImageButton button = (ImageButton) v;
		String userSelectedPair = (String) button.getContentDescription();
		chequearRespuesta(userSelectedPair);
	}
}
