package com.example.tema3acdat.tema3acdat;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tema3acdat.tema3acdat.estacionbicis.BiziZaragoza;
import com.example.tema3acdat.tema3acdat.utils.CheckerXML;
import com.example.tema3acdat.tema3acdat.utils.RestClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.w3c.dom.Text;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class MalagaMeteo extends AppCompatActivity {

    public static final String CANAL = "http://www.aemet.es/xml/municipios/localidad_29067.xml";

    private TextView txtV_prevHoy;
    private TextView txtV_prevManana;

    private String hoy;
    private String manana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_malaga_meteo);

        txtV_prevHoy = (TextView) findViewById(R.id.txtV_prevHoy);
        txtV_prevManana = (TextView) findViewById(R.id.txtV_prevManana);

       setFechas();
    }

    public void onClick_getPrevisiones (View v)
    {
        switch (v.getId()) {
            case R.id.btn_UpdatePrevs:
                descarga(CANAL);
                break;
        }
    }

    private void descarga(String canal) {
        final ProgressDialog progreso = new ProgressDialog(this);
        RestClient.get(canal, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                progreso.dismiss();
                Toast.makeText(MalagaMeteo.this, "Algo ha salido mal... :(",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                try {
                    progreso.dismiss();
                    txtV_prevHoy.setText(CheckerXML.getPrevision(hoy, file));
                    txtV_prevManana.setText(CheckerXML.getPrevision(manana, file));
                } catch (Exception e) {
                    Toast.makeText(MalagaMeteo.this, "¡Error! :(",
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

    private void setFechas() {
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);

        hoy = fechaACadena(cal.getTime());

        cal.add(Calendar.DAY_OF_YEAR, 1);
        manana = fechaACadena(cal.getTime());

    }

    private String fechaACadena(Date d) {
        //pasar una fecha a un string
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(d);
    }
}
