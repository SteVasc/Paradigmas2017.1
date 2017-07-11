package com.example.stefany.paradigmas20171.view_control.server_control;

import android.os.AsyncTask;
import android.util.Log;

import com.example.stefany.paradigmas20171.model.infrastructure.Session;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ServerAccessController extends AsyncTask {

    private String result;

    @Override
    protected String doInBackground(Object[] objects) {
        String urlString = Session.getServerAddress();

        try {
            URL url = new URL(urlString + "/login/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null){
                buffer.append(line);
            }
            this.result = buffer.toString();

            /*if (method.equals("PUT")){
                register(url);
            } else if (method.equals("POST")){
                login(url);
            }*/


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getResult() {
        return result;
    }

    private void register(URL url){
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("PUT");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            String email = Session.getEmail();
            String password = Session.getPassword();
            String jsonString = "{'email' : '" + email + "', 'password' : '" + password + "'}";
            writer.write(jsonString);
            writer.close();
            connection.connect();
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null){
                buffer.append(line);
            }
            this.result = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login(URL url){
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String email = Session.getEmail();
            String password = Session.getPassword();
            String jsonString = "{'email' : '" + email + "', 'password' : '" + password + "'}";
            writer.write(jsonString);
            writer.flush();
            writer.close();
            connection.connect();
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null){
                buffer.append(line);
            }
            this.result = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
