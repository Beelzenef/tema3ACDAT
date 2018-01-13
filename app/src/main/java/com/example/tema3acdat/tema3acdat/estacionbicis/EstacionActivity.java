package com.example.tema3acdat.tema3acdat.estacionbicis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.tema3acdat.tema3acdat.R;
import com.example.tema3acdat.tema3acdat.pojo.Estacion;

public class EstacionActivity extends AppCompatActivity {

    private EditText edT_Estado;
    private EditText edT_Direccion;
    private EditText edT_Anclajes;
    private EditText edT_BicisDisponibles;

    Estacion estacionElegida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estacion);

        findViews();

        estacionElegida = getIntent().getExtras().getBundle("Bundle").getParcelable(Estacion.TAG);

        edT_Estado.setText(estacionElegida.getEstado());
        edT_Direccion.setText(estacionElegida.getDireccion());
        edT_Anclajes.setText(Integer.toString(estacionElegida.getAnclajes()));
        edT_BicisDisponibles.setText(Integer.toString(estacionElegida.getBicisDisponibles()));
    }

    private void findViews()
    {
        edT_Anclajes = (EditText) findViewById(R.id.edT_Anclajes);
        edT_Direccion = (EditText) findViewById(R.id.edT_Direccion);
        edT_BicisDisponibles = (EditText) findViewById(R.id.edT_Bicicletas);
        edT_Estado = (EditText) findViewById(R.id.edT_Estado);
    }
}
