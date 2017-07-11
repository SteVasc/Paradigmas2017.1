package com.example.stefany.paradigmas20171.view_control.results;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.model.infrastructure.Session;
import com.example.stefany.paradigmas20171.view_control.access.RegisterActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class WaitingForResultsActivity extends AppCompatActivity {
    private String urlAddress;
    private String serverResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_for_results);
        urlAddress = Session.getServerAddress();
        communicate();
        Toast.makeText(WaitingForResultsActivity.this, serverResponse, Toast.LENGTH_LONG).show();
    }

    public void communicate(){
        final WaitingForResultsActivity.ResultsController controller = new WaitingForResultsActivity.ResultsController();
        controller.execute();
        try {
            controller.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        this.serverResponse = controller.result;
    }

    private class ResultsController extends AsyncTask<String, Void, String> {

        public String result;

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder result = new StringBuilder();
            HttpURLConnection connection = null;

            try {
                URL url = new URL(urlAddress + "optimize_plz/");
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("POST");
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                String email = "eu@example.com";
                String password = "12345";
                String jsonString = "{\"email\" : \"" + email + "\", \"password\" : \"" + password + "\", \"subjects\" : " +
                        "[{\"1403\" : \"PASSED\"}, {\"2231\" : \"FAILED\"}, {\"3678\" : \"ONGOING\"}]}";
                writer.write(jsonString);
                writer.close();
                connection.connect();
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));;
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            this.result = result.toString();
            return result.toString();
        }
    }
}
