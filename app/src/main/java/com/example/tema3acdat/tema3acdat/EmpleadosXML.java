package com.example.tema3acdat.tema3acdat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tema3acdat.tema3acdat.utils.CheckerXML;

public class EmpleadosXML extends AppCompatActivity {

    TextView textoNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleados_xml);

        textoNotas = (TextView) findViewById(R.id.txtV_notasXML);

        try {
            textoNotas.setText(CheckerXML.analizarXmlNextText(this));
        } catch (Exception e)
        {
            Toast.makeText(this, "Error al leer XML", Toast.LENGTH_SHORT).show();
        }
    }
}
