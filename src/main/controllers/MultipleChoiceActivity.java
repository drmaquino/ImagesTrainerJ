package main.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.helper.DBHelper;
import main.helper.IOHelper;
import main.model.Imagen;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public abstract class MultipleChoiceActivity extends Activity
{
	protected String carpeta;

	protected TextView tvCurrentResueltas;
	protected TextView tvCurrentPendientes;

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
		localizarLayout();
		localizarContadoresEnLayout();
		localizarPreguntaEnLayout();
		localizarRespuestasEnLayout();
		inicializarCarpeta();
		inicializarHelpers();
		inicializarListas();		
		cargarImagenes();
		prepararRespuestasPosibles();
		mostrarRespuestasPosibles();
		mostrarPregunta();
		actualizarContadores();
	}

	protected abstract void localizarLayout();
	
	protected void localizarContadoresEnLayout()
	{
		tvCurrentResueltas = (TextView) findViewById(R.id.current_resueltas);
		tvCurrentPendientes = (TextView) findViewById(R.id.current_pendientes);
	}
	
	protected abstract void localizarPreguntaEnLayout();
	
	protected abstract void localizarRespuestasEnLayout();

	protected void inicializarCarpeta()
	{
		carpeta = (String) this.getIntent().getCharSequenceExtra("carpeta");
	}

	protected void inicializarListas()
	{
		imagenesPendientes = new ArrayList<Imagen>();
		imagenesResueltas = new ArrayList<Imagen>();
		imagenesTotal = new ArrayList<Imagen>();
		imagenesParaMostrar = new ArrayList<Imagen>();
	}

	protected void inicializarHelpers()
	{
		ioh = new IOHelper(this);
		dbh = new DBHelper(this);
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

	protected void prepararRespuestasPosibles()
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

	protected abstract void mostrarRespuestasPosibles();

	protected abstract void mostrarPregunta();

	protected void actualizarContadores()
	{
		tvCurrentResueltas.setText(String.format("Resueltas: %s", imagenesResueltas.size()));
		tvCurrentPendientes.setText(String.format("Pendientes: %s", imagenesPendientes.size()));
	}

	public abstract void buttonClick(View v);

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
			prepararRespuestasPosibles();
			mostrarRespuestasPosibles();
			mostrarPregunta();
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
