package com.example.tema3acdat.tema3acdat.leyendorss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.tema3acdat.tema3acdat.R;

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
            case R.id.btn_LeerLinuxMagazine:
                b.putString("RSS", "http://www.linux-magazine.com/rss/feed/lmi_news");
                intent.putExtra("Bundle", b);
                break;
            case R.id.btn_LeerEurogamer:
                b.putString("RSS", "http://www.eurogamer.es/?format=rss");
                intent.putExtra("Bundle", b);
                break;
        }

        startActivity(intent);

    }
}
