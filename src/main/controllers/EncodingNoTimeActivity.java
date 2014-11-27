package main.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import main.helper.IOHelper;
import main.helper.Timer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class EncodingNoTimeActivity extends Activity
{
	private TextView tvCurrentPair;
	private TextView tvCurrentCorrect;
	private TextView tvCurrentIncorrect;

	private List<ImageButton> imageButtons;
	private ImageButton btn1;
	private ImageButton btn2;
	private ImageButton btn3;
	private ImageButton btn4;

	private List<String> imagenes;
	private List<String> imagenesExtras;
	private List<String> imagenesParaMostrar;
	private List<String> imagenesUsadas;
	private String imagenCorrecta;
	private IOHelper ioh;
	private int imagenesCorrectas;
	private int imagenesIncorrectas;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_encoding_no_time);

		ioh = new IOHelper(this);

		tvCurrentPair = (TextView) findViewById(R.id.current_pair);
		tvCurrentCorrect = (TextView) findViewById(R.id.current_correct);
		tvCurrentIncorrect = (TextView) findViewById(R.id.current_incorrect);

		imagenes = new ArrayList<String>();
		imagenesExtras = new ArrayList<String>();
		imageButtons = new ArrayList<ImageButton>();
		imagenesParaMostrar = new ArrayList<String>();
		imagenesUsadas = new ArrayList<String>();

		btn1 = (ImageButton) findViewById(R.id.btn1);
		btn2 = (ImageButton) findViewById(R.id.btn2);
		btn3 = (ImageButton) findViewById(R.id.btn3);
		btn4 = (ImageButton) findViewById(R.id.btn4);

		imageButtons.add(btn1);
		imageButtons.add(btn2);
		imageButtons.add(btn3);
		imageButtons.add(btn4);

		cargarImagenes();
		prepararImagenesParaMostrar();
		mostrarImagenesPorPantalla();
		mostrarElParQueDeseoEvaluar();
		inicializarContadores();
		actualizarContadores();

		Timer.start();
	}

	private void inicializarContadores()
	{
		imagenesCorrectas = 0;
		imagenesIncorrectas = 0;
	}

	private void actualizarContadores()
	{
		tvCurrentCorrect.setText(String.format("Correctas: %s", imagenesCorrectas));
		tvCurrentIncorrect.setText(String.format("Incorrectas: %s", imagenesIncorrectas));
	}

	private void cargarImagenes()
	{
		List<String> imagesInGameFolder = ioh.getListImagesInGameFolder();
		imagenes.addAll(imagesInGameFolder);
		imagenesExtras.addAll(imagesInGameFolder);
	}

	private void prepararImagenesParaMostrar()
	{
		int index;
		Random random;
		List<String> tresImagenes;

		if (!imagenes.isEmpty())
		{
			random = new Random();
			index = random.nextInt(imagenes.size());
			imagenCorrecta = imagenes.remove(index);
			imagenesParaMostrar.add(imagenCorrecta);

			tresImagenes = new ArrayList<String>();

			while (tresImagenes.size() < 3)
			{
				index = random.nextInt(imagenesExtras.size());
				String image = imagenesExtras.get(index);
				if (image != imagenCorrecta)
				{
					image = imagenesExtras.remove(index);
					tresImagenes.add(image);
				}
			}
			imagenesParaMostrar.addAll(tresImagenes);
		}
	}

	private void mostrarImagenesPorPantalla()
	{
		Collections.shuffle(imagenesParaMostrar);

		for (int i = 0; i < imagenesParaMostrar.size(); i++)
		{
			ImageButton imageButton = imageButtons.get(i);
			String image = imagenesParaMostrar.get(i);

			Bitmap imgBitmap = ioh.getBitmapFromImagesFolder(image);

			if (imgBitmap != null)
			{
				imageButton.setImageBitmap(imgBitmap);
				imageButton.setContentDescription(image);
			}
		}
	}

	private void mostrarElParQueDeseoEvaluar()
	{
		if (imagenCorrecta != null)
		{
			tvCurrentPair.setText(imagenCorrecta.split("\\.")[0].toUpperCase());
		}
		else
		{
			crearDialogoNoHayImagenes().show();
		}
	}

	public void buttonClick(View v)
	{
		ImageButton button = (ImageButton) v;
		String userSelectedPair = (String) button.getContentDescription();
		if (userSelectedPair.equals("error"))
		{
			return;
		}
		chequearRespuesta(userSelectedPair);
	}

	private void chequearRespuesta(String userSelectedPair)
	{
		if (imagenCorrecta.equals(userSelectedPair))
		{
			imagenesUsadas.add(imagenCorrecta);
			imagenesCorrectas++;
		}
		else
		{
			imagenes.add(imagenCorrecta);
			imagenesIncorrectas++;
		}
		actualizarContadores();
		
		imagenesParaMostrar.remove(imagenCorrecta);
		imagenesExtras.addAll(imagenesParaMostrar);
		imagenesParaMostrar = new ArrayList<String>();

		if (!imagenes.isEmpty())
		{
			prepararImagenesParaMostrar();
			mostrarImagenesPorPantalla();
			mostrarElParQueDeseoEvaluar();
		}
		else
		{
			crearDialogofinDelJuego().show();
		}
	}

	private AlertDialog crearDialogofinDelJuego()
	{
		Timer.stop();
		double elapsedTime = Timer.getTime();
		Timer.reset();

		Builder dbConfirmacionReinicio = new AlertDialog.Builder(this);
		dbConfirmacionReinicio.setTitle("Felicitaciones!");
		dbConfirmacionReinicio.setMessage(String.format("Has acertado todas las imagenes en %.2f segundos!", elapsedTime));
		dbConfirmacionReinicio.setIcon(R.drawable.ic_launcher);
		dbConfirmacionReinicio.setPositiveButton("Volver a empezar", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				dialog.dismiss();
				finish();
				startActivity(getIntent());
			}
		});
		dbConfirmacionReinicio.setNegativeButton("Salir", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				finish();
			}
		});
		return dbConfirmacionReinicio.create();
	}

	private AlertDialog crearDialogoNoHayImagenes()
	{
		Timer.stop();
		double elapsedTime = Timer.getTime();
		Timer.reset();

		Builder dbNoImages = new AlertDialog.Builder(this);
		dbNoImages.setTitle("Carpeta vacía");
		String msg = "";
		msg += "Lo siento, no se han encontrado";
		msg += "\nimagenes en la carpeta:";
		msg += "\ncom.imagestrainer.imagenes";
		dbNoImages.setMessage(msg);
		dbNoImages.setPositiveButton("Aceptar", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				dialog.dismiss();
				finish();
			}
		});
		return dbNoImages.create();
	}
}
