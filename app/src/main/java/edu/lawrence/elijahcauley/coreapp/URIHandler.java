package edu.lawrence.elijahcauley.coreapp;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Joe Gregg on 10/20/2015.
 */
public class URIHandler {
    public static final String hostName = "143.44.69.94"; //Server IP Address of the server hosting our REST application

    public static String doGet(String uri,String failure) throws IOException {
        InputStream is = null;

        try {
            Log.d("COREApp","GET uri: " + uri);
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            if (response != 200)
                return failure;

            is = conn.getInputStream();
            // Read the response as an array of chars
            Reader reader = null;
            reader = new InputStreamReader(is, "UTF-8");
            char[] buffer = new char[conn.getContentLength()];
            reader.read(buffer);
            String result = new String(buffer);
            Log.d("COREApp","Received: " + result);
            return result;

        } catch(Exception ex) {
            Log.d("COREApp","Exception in doGet:" + ex.toString());
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return failure;
    }

    public static String doPost(String uri, String data) throws IOException {
        InputStream is = null;

        try {
            Log.d("COREApp", "Post uri: " + uri);
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            Log.d("COREApp", "Posted: " + data);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStream os = conn.getOutputStream();
            os.write(data.getBytes());
            os.flush();

            // Starts the query
            conn.connect();

            is = conn.getInputStream();

            Reader reader = null;
            reader = new InputStreamReader(is, "UTF-8");
            char[] buffer = new char[conn.getContentLength()];
            reader.read(buffer);
            // Convert the array of chars to a String and return that
            String result = new String(buffer);
            Log.d("COREApp","Received: " + result);
            //Log.d("COREApp", conn.getErrorStream().toString());
            return result;
        } catch(Exception ex) {
            Log.d("Cafe","Exception in doPost:" + ex.toString());
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return "";
    }

    public static String doPut(String uri, String data) throws IOException {
        InputStream is = null;

        try {
            Log.d("COREApp", "Put uri: " + uri);
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            Log.d("COREApp", "Put: " + data);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStream os = conn.getOutputStream();
            os.write(data.getBytes());
            os.flush();

            // Starts the query
            conn.connect();

            is = conn.getInputStream();
            // Read the response as an array of char
            Reader reader = null;
            reader = new InputStreamReader(is, "UTF-8");
            char[] buffer = new char[conn.getContentLength()];
            reader.read(buffer);
            // Convert the array of chars to a String and return that
            String result = new String(buffer);
            Log.d("COREApp","Received: " + result);
            return result;
        } catch(Exception ex) {
            Log.d("COREApp","Exception in doPut:" + ex.toString());
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return "";
    }

    public static void doDelete(String uri) throws IOException {
        try {
            Log.d("COREApp","DELETE uri: " + uri);
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");

            // Starts the query
            conn.connect();
            int responseCode = conn.getResponseCode();
            Log.d("COREApp","DELETE response: "+responseCode);
        } catch (Exception ex) {
            Log.d("COREApp","Exception in doDelete:" + ex.toString());
        }
    }



}


