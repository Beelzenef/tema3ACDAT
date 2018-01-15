package com.example.tema3acdat.tema3acdat.estacionbicis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tema3acdat.tema3acdat.R;
import com.example.tema3acdat.tema3acdat.pojo.Estacion;
import com.example.tema3acdat.tema3acdat.pojo.Post;
import com.example.tema3acdat.tema3acdat.utils.CheckerXML;
import com.example.tema3acdat.tema3acdat.utils.RestClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import java.io.File;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class BiziZaragoza extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static String CANAL = "https://www.zaragoza.es/sede/servicio/urbanismo-infraestructuras/estacion-bicicleta.xml";
    private static String TEMPORAL = "tmp.xml";
    ListView lista;
    ArrayList<Estacion> listaEstaciones;
    ArrayAdapter<Estacion> adapter;
    FloatingActionButton fab_updatePosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_rss);

        lista = (ListView) findViewById(R.id.listView);
        lista.setOnItemClickListener(this);
        fab_updatePosts = (FloatingActionButton) findViewById(R.id.fab_updateNews);
        fab_updatePosts.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == fab_updatePosts)
            descarga(CANAL, TEMPORAL);
    }

    private void descarga(String canal, String temporal) {
        final ProgressDialog progreso = new ProgressDialog(this);
        RestClient.get(canal, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                progreso.dismiss();
                Toast.makeText(BiziZaragoza.this, "Algo ha salido mal... :(",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                try {
                    progreso.dismiss();
                    listaEstaciones = CheckerXML.leerEstaciones(file);
                    mostrar();
                } catch (Exception e) {
                    Toast.makeText(BiziZaragoza.this, "¡Error! :(",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Descargando");
                progreso.setCancelable(false);
                progreso.show();
            }
        });
    }

    private void mostrar() {
        if (listaEstaciones != null)
            if (adapter == null) {
                adapter = new ArrayAdapter<Estacion>(this, android.R.layout.simple_list_item_1, listaEstaciones);
                lista.setAdapter(adapter);
            }
            else {
                adapter.clear();
                adapter.addAll(listaEstaciones);
            }
        else
            Toast.makeText(getApplicationContext(), "Error al crear la lista", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Estacion estacionElegida = listaEstaciones.get(position);

        Bundle b = new Bundle();
        b.putParcelable(Estacion.TAG, estacionElegida);
        Intent intent = new Intent(BiziZaragoza.this, EstacionActivity.class);
        intent.putExtra("Bundle", b);
        startActivity(intent);

    }
}
