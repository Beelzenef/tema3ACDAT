package com.example.tema3acdat.tema3acdat.leyendorss;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tema3acdat.tema3acdat.R;
import com.example.tema3acdat.tema3acdat.pojo.Post;
import com.example.tema3acdat.tema3acdat.utils.CheckerXML;
import com.example.tema3acdat.tema3acdat.utils.RestClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import android.support.design.widget.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class VerRSS extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static String CANAL = "canal";
    private static String TEMPORAL = "tmp.xml";
    ListView lista;
    ArrayList<Post> listaPosts;
    ArrayAdapter<Post> adapter;
    FloatingActionButton fab_updatePosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_rss);

        CANAL = getIntent().getExtras().getBundle("Bundle").getString("RSS");

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
                Toast.makeText(VerRSS.this, "Algo ha salido mal... :(",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                try {
                    progreso.dismiss();
                    listaPosts = CheckerXML.leerPosts(file);
                    mostrar();
                } catch (Exception e) {
                    Toast.makeText(VerRSS.this, "¡Error! :(",
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
        if (listaPosts != null)
            if (adapter == null) {
                adapter = new ArrayAdapter<Post>(this, android.R.layout.simple_list_item_1, listaPosts);
                lista.setAdapter(adapter);
            }
            else {
                adapter.clear();
                adapter.addAll(listaPosts);
            }
        else
            Toast.makeText(getApplicationContext(), "Error al crear la lista", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Uri uri = Uri.parse((String) listaPosts.get(position).getLink());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
        else
            Toast.makeText(getApplicationContext(), "No hay un navegador", Toast.LENGTH_SHORT).show();
    }

}
