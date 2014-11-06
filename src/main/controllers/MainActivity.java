package main.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void launchEncodingActivity(View view)
    {
        Intent intent = new Intent(this, EncodingActivity.class);
        startActivity(intent);
    }
    
    public void launchDecodingActivity(View view)
    {
        Intent intent = new Intent(this, DecodingActivity.class);
        startActivity(intent);
    }
}
