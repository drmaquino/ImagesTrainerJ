package main.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TimeAttackActivity extends Activity
{
	private static final String letters = "bcdefghijklmnopqrstuvwx";

	private TextView tvCurrentPair;
	private List<ImageButton> imageButtons;
	private ImageButton btn1;
	private ImageButton btn2;
	private ImageButton btn3;
	private ImageButton btn4;
	
	private String currentPair;
	private String userSelectedPair;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_encoding);

		tvCurrentPair = (TextView) findViewById(R.id.current_pair);

		btn1 = (ImageButton) findViewById(R.id.btn1);
		btn2 = (ImageButton) findViewById(R.id.btn2);
		btn3 = (ImageButton) findViewById(R.id.btn3);
		btn4 = (ImageButton) findViewById(R.id.btn4);

		imageButtons = new ArrayList<ImageButton>();

		imageButtons.add(btn1);
		imageButtons.add(btn2);
		imageButtons.add(btn3);
		imageButtons.add(btn4);

		resetChallenge();
	}

	private void resetChallenge()
	{
		setRandomImages();
		currentPair = chooseRandomPairfromSelection();
		tvCurrentPair.setText(currentPair);
	}
	
	private void setRandomImages()
	{
		for (ImageButton ib : imageButtons)
		{
			String pair = getRandomPair();
			Drawable imgDrawable = getDrawableByName(pair + ".jpg");
			if (imgDrawable != null)
			{
				ib.setImageDrawable(imgDrawable);
			}
			ib.setContentDescription(pair);
		}
	}

	private String getRandomPair()
	{
		String pair = "";
		for (int i = 0; i < 2; i++)
		{
			int randomNumber = new Random().nextInt(letters.length());
			char randomLetter = letters.charAt(randomNumber);
			pair += randomLetter;
		}
		return pair;
	}
	
	private Drawable getDrawableByName(String filename)
	{
		Drawable result = null;
		try
		{
			InputStream inputStream = getAssets().open(filename);
			result = Drawable.createFromStream(inputStream, null);
		}
		catch (IOException e)
		{
		}
		return result;
	}
	
	private String chooseRandomPairfromSelection()
	{
		Random random = new Random();
		int randIndex = random.nextInt(4);
		return imageButtons.get(randIndex).getContentDescription().toString();
	}	

	public void buttonClick(View v)
	{
		ImageButton button = (ImageButton) v;
		userSelectedPair = (String) button.getContentDescription();
		checkAnswer();
	}
	
	private void checkAnswer()
	{
		if (getCurrentPair().equals(getSelectedPair()))
		{
			Toast.makeText(this, "Correcto!", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(this, "Incorrecto...", Toast.LENGTH_SHORT).show();
		}
		resetChallenge();
	}

	private String getCurrentPair()
	{
		return tvCurrentPair.getText().toString();
	}
	
	private String getSelectedPair()
	{
		return userSelectedPair;
	}
}
