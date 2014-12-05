package main.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import main.helper.DBHelper;
import main.helper.IOHelper;
import main.model.Imagen;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DecodingActivity extends EncodingActivity
{
//	private String carpeta;
	private ImageView tvCurrentImage;
//	private TextView tvCurrentResueltas;
//	private TextView tvCurrentPendientes;

	private List<Button> pairButtons;
	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;

//	private List<Imagen> imagenesPendientes;
//	private List<Imagen> imagenesResueltas;
//	private List<Imagen> imagenesTotal;
//	private List<Imagen> imagenesParaMostrar;
//	private Imagen imagenCorrecta;
//	private IOHelper ioh;
//	private DBHelper dbh;

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
		
		carpeta = (String) this.getIntent().getCharSequenceExtra("carpeta");

		cargarImagenes();
		prepararImagenesParaMostrar();
		mostrarParesPorPantalla();
		mostrarLaImagenQueDeseoEvaluar();
		actualizarContadores();
	}

	private void mostrarParesPorPantalla()
	{
		Collections.shuffle(imagenesParaMostrar);

		for (int i = 0; i < imagenesParaMostrar.size(); i++)
		{
			Button pairButton = pairButtons.get(i);
			Imagen imagen = imagenesParaMostrar.get(i);
			pairButton.setContentDescription(imagen.get_nombre());
			
			String nombre = imagen.get_nombre().split("\\.")[0];
			if (nombre.length() > 2)
			{
				pairButton.setText(nombre.substring(0, 1).toUpperCase() + nombre.substring(1, nombre.length()).toLowerCase());
			}
			else
			{
				pairButton.setText(nombre.toUpperCase());
			}
		}
	}

	private void mostrarLaImagenQueDeseoEvaluar()
	{
		if (imagenCorrecta != null)
		{
			Bitmap imgBitmap = ioh.getBitmapFromFolder(carpeta, imagenCorrecta.get_nombre());

			if (imgBitmap != null)
			{
				tvCurrentImage.setImageBitmap(imgBitmap);
			}
		}
	}

	public void buttonClick(View v)
	{
		Button button = (Button) v;
		String userSelectedPair = (String) button.getContentDescription();
		chequearRespuesta(userSelectedPair);
	}	
}
