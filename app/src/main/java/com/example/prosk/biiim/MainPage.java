package com.example.prosk.biiim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainPage extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);

    }


    public void openAcitivty_caisse(View view)
    {
        Intent intent = new Intent(MainPage.this, activity_caisse.class);
        startActivity(intent);
    }

    public void openAcitivty_bl(View view)
    {
        Intent intent = new Intent(MainPage.this, activity_bl.class);
        startActivity(intent);
    }

    public void openAcitivty_commandes(View view)
    {
        Intent intent = new Intent(MainPage.this, activity_commandes.class);
        startActivity(intent);
    }
}
