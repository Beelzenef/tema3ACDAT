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
import java.util.Date;

/**
 * Analizador de XML
 */

public class CheckerXML {

    public static String analizarXmlNextText(Context c) throws XmlPullParserException, IOException {

        StringBuilder stringResultante = new StringBuilder();

        XmlResourceParser xrp = c.getResources().getXml(R.xml.empleados);
        int eventType = xrp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:

                    if (xrp.getName().equals("nombre")) {
                        stringResultante.append("Nombre: " + xrp.nextText() + "\n");
                    }

                    if (xrp.getName().equals("sueldo")) {

                        stringResultante.append("Sueldo: " + xrp.nextText() + "\n");
                    }

                    if (xrp.getName().equals("edad")) {
                        //suma += Double.parseDouble(xrp.getText());
                        stringResultante.append("Edad: " + xrp.nextText() + "\n");
                    }

                    if (xrp.getName().equals("cargo")) {
                        stringResultante.append("Cargo: " + xrp.nextText() + "\n");
                    }


                    break;
            }
            eventType = xrp.next();
        }


        xrp.close();
        return stringResultante.toString();
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

    public static String getPrevision(String fecha, File file) throws XmlPullParserException, IOException {

        StringBuilder stringResultante = new StringBuilder();

        boolean dentroItem = false;
        boolean dentroTemperatura = false;

        XmlPullParser xpp = Xml.newPullParser();
        xpp.setInput(new FileReader(file));
        int eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:

                    if (xpp.getName().equals("dia")) {
                        if (xpp.getAttributeValue(0).equals(fecha))
                            dentroItem = true;
                        if (dentroItem)
                        {
                            stringResultante.append("Previsión para el día: " + fecha + "\n");
                        }
                    }

                    if (xpp.getName().equals("temperatura") && dentroItem) {
                        dentroTemperatura = true;
                    }

                    if (xpp.getName().equals("maxima") && dentroTemperatura) {
                        //suma += Double.parseDouble(xrp.getText());
                        stringResultante.append("Temperatura máxima: " + xpp.nextText() + "\n");
                    }

                    if (xpp.getName().equals("minima") && dentroTemperatura) {
                        //suma += Double.parseDouble(xrp.getText());
                        stringResultante.append("Temperatura mínima: " + xpp.nextText() + "\n");
                        dentroItem = false;
                    }

                    break;
                case XmlPullParser.END_TAG:
                    if (xpp.getName().equals("dia")) {
                        dentroItem = false;
                    }

                    if (xpp.getName().equals("temperatura")) {
                        dentroTemperatura = false;
                    }
                    break;
            }
            eventType = xpp.next();
        }
        return stringResultante.toString();
    }
}