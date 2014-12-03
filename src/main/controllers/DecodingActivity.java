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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DecodingActivity extends Activity
{
	private ImageView tvCurrentImage;
	private TextView tvCurrentResueltas;
	private TextView tvCurrentPendientes;

	private List<Button> pairButtons;
	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;

	private List<Imagen> imagenesPendientes;
	private List<Imagen> imagenesResueltas;
	private List<Imagen> imagenesTotal;
	private List<Imagen> imagenesParaMostrar;
	private Imagen imagenCorrecta;
	private IOHelper ioh;
	private DBHelper dbh;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_decoding);

		ioh = new IOHelper(this);
		dbh = new DBHelper(this);

		tvCurrentImage = (ImageView) findViewById(R.id.current_image);
		tvCurrentResueltas = (TextView) findViewById(R.id.current_resueltas);
		tvCurrentPendientes = (TextView) findViewById(R.id.current_pendientes);

		imagenesPendientes = new ArrayList<Imagen>();
		imagenesResueltas = new ArrayList<Imagen>();
		imagenesTotal = new ArrayList<Imagen>();
		imagenesParaMostrar = new ArrayList<Imagen>();
		pairButtons = new ArrayList<Button>();

		btn1 = (Button) findViewById(R.id.pair_1);
		btn2 = (Button) findViewById(R.id.pair_2);
		btn3 = (Button) findViewById(R.id.pair_3);
		btn4 = (Button) findViewById(R.id.pair_4);

		pairButtons.add(btn1);
		pairButtons.add(btn2);
		pairButtons.add(btn3);
		pairButtons.add(btn4);

		cargarImagenes();
		prepararImagenesParaMostrar();
		mostrarImagenesPorPantalla();
		mostrarElParQueDeseoEvaluar();
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
			Button pairButton = pairButtons.get(i);
			Imagen imagen = imagenesParaMostrar.get(i);
			pairButton.setContentDescription(imagen.get_nombre());
			pairButton.setText(imagen.get_nombre().split("\\.")[0].toUpperCase());
		}
	}

	private void mostrarElParQueDeseoEvaluar()
	{
		if (imagenCorrecta != null)
		{
			Bitmap imgBitmap = ioh.getBitmapFromImagesFolder(imagenCorrecta.get_nombre());

			if (imgBitmap != null)
			{
				tvCurrentImage.setImageBitmap(imgBitmap);
			}
		}
	}

	private void actualizarContadores()
	{
		tvCurrentResueltas.setText(String.format("Resueltas: %s", imagenesResueltas.size()));
		tvCurrentPendientes.setText(String.format("Pendientes: %s", imagenesPendientes.size()));
	}

	public void buttonClick(View v)
	{
		Button button = (Button) v;
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
			Toast.makeText(this, "Incorrecta...!", Toast.LENGTH_SHORT).show();
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
