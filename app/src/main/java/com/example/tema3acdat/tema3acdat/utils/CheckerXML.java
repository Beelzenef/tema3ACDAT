package com.example.tema3acdat.tema3acdat.utils;

/**
 * Checker XML
 */

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Xml;

import com.example.tema3acdat.tema3acdat.R;
import com.example.tema3acdat.tema3acdat.pojo.Estacion;
import com.example.tema3acdat.tema3acdat.pojo.Post;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Analizador de XML
 */

public class CheckerXML {

    public static String analizar(String texto) throws XmlPullParserException, IOException {
        StringBuilder cadena = new StringBuilder();
        XmlPullParser xpp = Xml.newPullParser();
        xpp.setInput(new StringReader(texto));
        int eventType = xpp.getEventType();
        cadena.append("Leyendo XML:\n ");
        while (eventType != XmlPullParser.END_DOCUMENT) {

            if (eventType == XmlPullParser.START_DOCUMENT)
                cadena.append("START DOCUMENT \n");
            if (eventType == XmlPullParser.START_TAG)
                cadena.append("START TAG: " + xpp.getName() + "\n");
            if (eventType == XmlPullParser.TEXT)
                cadena.append("CONTENT: " + xpp.getText() + "\n");
            if (eventType == XmlPullParser.END_TAG)
                cadena.append("END TAG: " + xpp.getName() + "\n");
            eventType = xpp.next();
        }

        cadena.append("END DOCUMENT");
        return cadena.toString();
    }

    public static String analizarXmlNextText(Context c) throws XmlPullParserException, IOException {

        StringBuilder stringResultante = new StringBuilder();

        double suma = 0.0;
        int contador = 0;

        int mayorSueldo = 0;
        int menorSueldo = 0;

        XmlResourceParser xrp = c.getResources().getXml(R.xml.empleados);
        int eventType = xrp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:

                    if (xrp.getName().equals("nombre")) {
                        stringResultante.append("Nombre: " + xrp.nextText() + "\n");
                    }

                    if (xrp.getName().equals("cargo")) {
                        stringResultante.append("Cargo: " + xrp.nextText() + "\n");
                    }
                    if (xrp.getName().equals("edad")) {
                        suma += Double.parseDouble(xrp.getText());
                        stringResultante.append("Edad: " + xrp.nextText() + "\n");
                        contador++;
                    }
                    if (xrp.getName().equals("sueldo")) {
                        if (Integer.parseInt(xrp.getText()) > mayorSueldo)
                            mayorSueldo = Integer.parseInt(xrp.getText());
                        if (Integer.parseInt(xrp.getText()) < menorSueldo)
                            menorSueldo = Integer.parseInt(xrp.getText());

                        stringResultante.append("Sueldo: " + xrp.nextText() + "\n");
                    }
                    break;
            }
            eventType = xrp.next();
        }

        stringResultante.append("Media de todas las edades : " + String.format("%.2f", suma / contador));
        stringResultante.append("Menor sueldo : " + Integer.toString(menorSueldo));
        stringResultante.append("Mayor sueldo : " + Integer.toString(mayorSueldo));
        xrp.close();
        return stringResultante.toString();
    }



    public static String analizarRSS(File file) throws NullPointerException, XmlPullParserException, IOException {

        boolean dentroItem = false;

        StringBuilder builder = new StringBuilder();
        XmlPullParser xpp = Xml.newPullParser();
        xpp.setInput(new FileReader(file));

        int eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (xpp.getName().equals("item"))
                        dentroItem = true;

                    if (dentroItem && xpp.getName().equals("title")) {
                        builder.append("Post: " + xpp.nextText() + "\n");
                        dentroItem = false;
                    }
                    break;
            }
            eventType = xpp.next();
        }
        return builder.toString();
    }

    public static ArrayList<Post> leerPosts(File file) throws XmlPullParserException, IOException {
        int eventType;

        ArrayList<Post> posts = null;
        Post actual = null;
        boolean dentroItem = false;

        XmlPullParser xpp = Xml.newPullParser();
        xpp.setInput(new FileReader(file));
        eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    posts = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    if (xpp.getName().equals("item")) {
                        dentroItem = true;
                        actual = new Post();
                    }

                    if (dentroItem && xpp.getName().equals("title")) {
                        actual.setTitle(xpp.nextText());
                    }
                    if (dentroItem && xpp.getName().equals("link")) {
                        actual.setLink(xpp.nextText());
                    }
                    if (dentroItem && xpp.getName().equals("description")) {
                        actual.setDescription(xpp.nextText());
                    }
                    if (dentroItem && xpp.getName().equals("pubDate")) {
                        actual.setPubDate(xpp.nextText());
                    }

                    break;
                case XmlPullParser.END_TAG:
                    if (xpp.getName().equals("item")) {
                        dentroItem = false;
                        posts.add(actual);
                    }
                    break;
            }
            eventType = xpp.next();
        }
        //devolver el array de posts
        return posts;
    }

    public static ArrayList<Estacion> leerEstaciones(File file) throws XmlPullParserException, IOException {
        int eventType;

        ArrayList<Estacion> estaciones = null;
        Estacion actual = null;
        boolean dentroItem = false;

        XmlPullParser xpp = Xml.newPullParser();
        xpp.setInput(new FileReader(file));
        eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    estaciones = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    if (xpp.getName().equals("estacion-bicicleta")) {
                        dentroItem = true;
                        actual = new Estacion();
                    }

                    if (dentroItem && xpp.getName().equals("title")) {
                        actual.setDireccion(xpp.nextText());
                    }
                    if (dentroItem && xpp.getName().equals("estado")) {
                        actual.setEstado(xpp.nextText());
                    }
                    if (dentroItem && xpp.getName().equals("bicisDisponibles")) {
                        actual.setBicisDisponibles(Integer.parseInt(xpp.nextText()));
                    }
                    if (dentroItem && xpp.getName().equals("anclajesDisponibles")) {
                        actual.setAnclajes(Integer.parseInt(xpp.nextText()));
                    }

                    break;
                case XmlPullParser.END_TAG:
                    if (xpp.getName().equals("estacion-bicicleta")) {
                        dentroItem = false;
                        estaciones.add(actual);
                    }
                    break;
            }
            eventType = xpp.next();
        }
        //Devolver el array de estaciones
        return estaciones;
    }
}