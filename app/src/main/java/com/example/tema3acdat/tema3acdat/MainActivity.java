package com.example.tema3acdat.tema3acdat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.tema3acdat.tema3acdat.leyendorss.LectorRSS;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick_lanzarExs(View v) {
        Intent unIntent = null;

        switch (v.getId()) {
            case R.id.btn_Bizi:
                unIntent = new Intent(MainActivity.this, BiziZaragoza.class);
                break;
            case R.id.btn_empleadosXML:
                unIntent = new Intent(MainActivity.this, EmpleadosXML.class);
                break;
            case R.id.btn_lectorRSS:
                unIntent = new Intent(MainActivity.this, LectorRSS.class);
                break;
            case R.id.btn_MalagaMeteo:
                unIntent = new Intent(MainActivity.this, MalagaMeteo.class);
                break;
        }

        startActivity(unIntent);
    }
}
