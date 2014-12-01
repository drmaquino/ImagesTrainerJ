package main.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import main.helper.DBHelper;
import main.helper.IOHelper;
import main.model.Imagen;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class EncodingActivity extends Activity
{
	private TextView tvCurrentPair;
	private TextView tvCurrentResueltas;
	private TextView tvCurrentPendientes;
	private TextView tvCurrentIncorrectas;

	private List<ImageButton> imageButtons;
	private ImageButton btn1;
	private ImageButton btn2;
	private ImageButton btn3;
	private ImageButton btn4;

	private List<Imagen> imagenesPendientes;
	private List<Imagen> imagenesResueltas;
	private List<Imagen> imagenesTotal;
	private List<Imagen> imagenesParaMostrar;
	private Imagen imagenCorrecta;
	private IOHelper ioh;
	private DBHelper dbh;
	private int imagenesIncorrectas;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_encoding);

		ioh = new IOHelper(this);
		dbh = new DBHelper(this);

		tvCurrentPair = (TextView) findViewById(R.id.current_pair);
		tvCurrentResueltas = (TextView) findViewById(R.id.current_resueltas);
		tvCurrentPendientes = (TextView) findViewById(R.id.current_pendientes);
		tvCurrentIncorrectas = (TextView) findViewById(R.id.current_incorrectas);

		imagenesPendientes = new ArrayList<Imagen>();
		imagenesResueltas = new ArrayList<Imagen>();
		imagenesTotal = new ArrayList<Imagen>();
		imagenesParaMostrar = new ArrayList<Imagen>();
		imageButtons = new ArrayList<ImageButton>();

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
		inicializarContadorIncorrectas();
		actualizarContadores();
	}

	private void cargarImagenes()
	{
		imagenesPendientes = dbh.findImagenesByEstado("pendiente");
		imagenesResueltas = dbh.findImagenesByEstado("resuelta");

		imagenesTotal.addAll(imagenesPendientes);
		imagenesTotal.addAll(imagenesResueltas);

		if (imagenesTotal.size() < 4)
		{
			crearDialogoNoHayImagenes().show();
		}
	}

	private void prepararImagenesParaMostrar()
	{
		int index;
		Random random;
		List<Imagen> tresImagenes;

		if (!imagenesPendientes.isEmpty())
		{
			random = new Random();
			index = random.nextInt(imagenesPendientes.size());
			imagenCorrecta = imagenesPendientes.remove(index);
			imagenesParaMostrar.add(imagenCorrecta);

			tresImagenes = new ArrayList<Imagen>();

			while (tresImagenes.size() < 3)
			{
				index = random.nextInt(imagenesTotal.size());
				Imagen imagen = imagenesTotal.get(index);
				if (imagen.get_nombre() != imagenCorrecta.get_nombre())
				{
					imagen = imagenesTotal.remove(index);
					tresImagenes.add(imagen);
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
			Imagen imagen = imagenesParaMostrar.get(i);

			Bitmap imgBitmap = ioh.getBitmapFromImagesFolder(imagen.get_nombre());

			if (imgBitmap != null)
			{
				imageButton.setImageBitmap(imgBitmap);
				imageButton.setContentDescription(imagen.get_nombre());
			}
		}
	}

	private void mostrarElParQueDeseoEvaluar()
	{
		if (imagenCorrecta != null)
		{
			tvCurrentPair.setText(imagenCorrecta.get_nombre().split("\\.")[0].toUpperCase());
		}
	}

	private void inicializarContadorIncorrectas()
	{
		imagenesIncorrectas = 0;
	}

	private void actualizarContadores()
	{
		tvCurrentResueltas.setText(String.format("Resueltas: %s", imagenesResueltas.size()));
		tvCurrentPendientes.setText(String.format("Pendientes: %s", imagenesPendientes.size()));
		tvCurrentIncorrectas.setText(String.format("Incorrectas: %s", imagenesIncorrectas));
	}

	public void buttonClick(View v)
	{
		ImageButton button = (ImageButton) v;
		String userSelectedPair = (String) button.getContentDescription();
		chequearRespuesta(userSelectedPair);
	}

	private void chequearRespuesta(String userSelectedPair)
	{
		if (imagenCorrecta.get_nombre().equals(userSelectedPair))
		{
			imagenCorrecta.set_estado("resuelta");
			dbh.updateImagen(imagenCorrecta);
			imagenesResueltas.add(imagenCorrecta);
		}
		else
		{
			imagenesPendientes.add(imagenCorrecta);
			imagenesIncorrectas++;
		}
		actualizarContadores();

		imagenesParaMostrar.remove(imagenCorrecta);
		imagenesTotal.addAll(imagenesParaMostrar);
		imagenesParaMostrar = new ArrayList<Imagen>();

		if (!imagenesPendientes.isEmpty())
		{
			prepararImagenesParaMostrar();
			mostrarImagenesPorPantalla();
			mostrarElParQueDeseoEvaluar();
		}
		else
		{
			crearDialogofinDelJuego().show();
			dbh.regenerateDB();
		}
	}

	private AlertDialog crearDialogofinDelJuego()
	{
		Builder dbConfirmacionReinicio = new AlertDialog.Builder(this);
		dbConfirmacionReinicio.setTitle("Fin del juego");
		dbConfirmacionReinicio.setMessage("Has llegado al final de la lista!");
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
		Builder dbNoImages = new AlertDialog.Builder(this);
		dbNoImages.setTitle("Carpeta vac�a");
		String msg = "";
		msg += "Lo siento, debe colocar al menos";
		msg += "\n4 imagenes en la carpeta:";
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
