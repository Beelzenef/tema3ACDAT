package com.example.tema3acdat.tema3acdat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LectorRSS extends AppCompatActivity {

    private String enlaceRSS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lector_rss);

        enlaceRSS = "Sin enlace seleccionado";
    }

    public void onClick_lanzarRSS(View v) {

        Bundle b = new Bundle();
        Intent intent = new Intent(LectorRSS.this, VerRSS.class);

        switch (v.getId()) {
            case R.id.btn_LeerGeekstorming:
                b.putString("RSS", "https://geekstorming.wordpress.com/feed/");
                intent.putExtra("Canal", b);
                break;
            case R.id.btn_LeerNextStop:
                b.putString("RSS", "https://putifruta.wordpress.com/feed/");
                intent.putExtra("Canal", b);
                break;
            case R.id.btn_LeerLinuxMagazine:
                b.putString("RSS", "http://www.linux-magazine.com/rss/feed/lmi_news");
                intent.putExtra("Canal", b);
                break;
        }

        startActivity(intent);

    }
}
