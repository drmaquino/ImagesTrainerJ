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
import android.widget.Toast;

public class EncodingActivity extends Activity
{
	protected String carpeta;
	private TextView tvCurrentPair;
	protected TextView tvCurrentResueltas;
	protected TextView tvCurrentPendientes;

	private List<ImageButton> imageButtons;
	private ImageButton btn1;
	private ImageButton btn2;
	private ImageButton btn3;
	private ImageButton btn4;

	protected List<Imagen> imagenesPendientes;
	protected List<Imagen> imagenesResueltas;
	protected List<Imagen> imagenesTotal;
	protected List<Imagen> imagenesParaMostrar;
	protected Imagen imagenCorrecta;
	protected IOHelper ioh;
	protected DBHelper dbh;

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

		carpeta = (String) this.getIntent().getCharSequenceExtra("carpeta");

		cargarImagenes();
		prepararImagenesParaMostrar();
		mostrarImagenesPorPantalla();
		mostrarElParQueDeseoEvaluar();
		actualizarContadores();
	}

	protected void cargarImagenes()
	{
		imagenesPendientes = dbh.findImagenesByCarpetaEstado(carpeta, "pendiente");
		imagenesResueltas = dbh.findImagenesByCarpetaEstado(carpeta, "resuelta");

		imagenesTotal.addAll(imagenesPendientes);
		imagenesTotal.addAll(imagenesResueltas);

		if (imagenesTotal.size() < 4)
		{
			crearDialogoNoHayImagenes().show();
		}
	}

	protected void prepararImagenesParaMostrar()
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

			Bitmap imgBitmap = ioh.getBitmapFromFolder(carpeta, imagen.get_nombre());

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

	protected void actualizarContadores()
	{
		tvCurrentResueltas.setText(String.format("Resueltas: %s", imagenesResueltas.size()));
		tvCurrentPendientes.setText(String.format("Pendientes: %s", imagenesPendientes.size()));
	}

	public void buttonClick(View v)
	{
		ImageButton button = (ImageButton) v;
		String userSelectedPair = (String) button.getContentDescription();
		chequearRespuesta(userSelectedPair);
	}

	protected void chequearRespuesta(String userSelectedPair)
	{
		if (imagenCorrecta.get_nombre().equals(userSelectedPair))
		{
			imagenCorrecta.set_estado("resuelta");
			dbh.updateImagen(imagenCorrecta);
			imagenesResueltas.add(imagenCorrecta);
		}
		else
		{
			Toast.makeText(this, "Incorrecto...!", Toast.LENGTH_SHORT).show();
			imagenesPendientes.add(imagenCorrecta);
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
			dbh.reiniciarImagenesByCarpeta(carpeta);
		}
	}

	protected AlertDialog crearDialogofinDelJuego()
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

	protected AlertDialog crearDialogoNoHayImagenes()
	{
		Builder dbNoImages = new AlertDialog.Builder(this);
		dbNoImages.setTitle("Carpeta vacía");
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
