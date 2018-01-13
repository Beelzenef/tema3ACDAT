package com.example.tema3acdat.tema3acdat.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Clase Estación de bicletas
 */

public class Estacion implements Parcelable {

    private String direccion;
    private String estado;
    private int anclajes;
    private int bicisDisponibles;

    public static final String TAG = "Estacion";

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getAnclajes() {
        return anclajes;
    }

    public void setAnclajes(int anclajes) {
        this.anclajes = anclajes;
    }

    public int getBicisDisponibles() {
        return bicisDisponibles;
    }

    public void setBicisDisponibles(int bicisDisponibles) {
        this.bicisDisponibles = bicisDisponibles;
    }

    public Estacion() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(direccion);
        parcel.writeString(estado);
        parcel.writeInt(anclajes);
        parcel.writeInt(bicisDisponibles);
    }

    private Estacion(Parcel source) {
        this.direccion = source.readString();
        this.estado = source.readString();
        this.anclajes = source.readInt();
        this.bicisDisponibles = source.readInt();
    }

    public static Creator<Estacion> CREATOR = new Creator<Estacion>() {
        @Override
        public Estacion createFromParcel(Parcel parcel) {
            return new Estacion(parcel);
        }

        @Override
        public Estacion[] newArray(int i) {
            return new Estacion[0];
        }
    };

    @Override
    public String toString() {
        return this.direccion;
    }
}
