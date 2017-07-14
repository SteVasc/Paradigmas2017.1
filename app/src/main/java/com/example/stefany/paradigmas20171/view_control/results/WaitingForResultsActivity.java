package com.example.stefany.paradigmas20171.view_control.results;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.model.infrastructure.Session;
import com.example.stefany.paradigmas20171.model.infrastructure.Subject;
import com.example.stefany.paradigmas20171.model.infrastructure.SubjectStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class WaitingForResultsActivity extends AppCompatActivity {
    private String urlAddress;
    private String serverResponse;
    private static ArrayList<Subject> updatedSubjects;
    private ArrayList<Subject> resultSubjects;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_for_results);
        urlAddress = Session.getServerAddress();
        updatedSubjects = Session.getSubjectManager().getUpdatedSubjects();
        communicateByPass();
        handleServerResponse();
        //communicate();
    }

    public void toNext(boolean messageReceived){
        if (messageReceived) {
            OptimizeResultsActivity.setStartingSemester();
            OptimizeResultsActivity.setOriginalSubjects(resultSubjects);
            Intent intentGoToResults = new Intent(WaitingForResultsActivity.this, OptimizeResultsActivity.class);
            startActivity(intentGoToResults);
            finish();
        }
    }

    public void communicateByPass(){
        this.serverResponse = "{\"status\": \"ok\", \"course\":[" +
                "{\"semester\": \"5\", \"code\": \"6295\"}, " +
                "{\"semester\": \"5\", \"code\": \"6246\"}," +
                "{\"semester\": \"5\", \"code\": \"6296\"}," +
                "{\"semester\": \"5\", \"code\": \"6297\"}," +
                "{\"semester\": \"5\", \"code\": \"14310\"}," +
                "{\"semester\": \"4\", \"code\": \"5345\"}," +
                "{\"semester\": \"4\", \"code\": \"6243\"}," +
                "{\"semester\": \"4\", \"code\": \"6288\"}," +
                "{\"semester\": \"4\", \"code\": \"6289\"}," +
                "{\"semester\": \"4\", \"code\": \"4163\"}]}";
    }

    public String formMessage(){
        String email = Session.getEmail();
        String password = Session.getPassword();
        message = "{\"email\" : \"" + email + "\", \"password\" : \"" + password + "\", \"subjects\" : [";
        for (Subject subject : updatedSubjects){
            message += "{";
            message += "\"code\":\"" + subject.getCode() + "\", \"status\":\"" + getToServerStatus(subject);
            message += "}";
        }
        message = message + "]}";
        return message;
    }

    private String getToServerStatus(Subject subject) {
        String status;
        if (subject.getStatus() == SubjectStatus.APPROVED){
            status = "PASSED";
        } else if (subject.getStatus() == SubjectStatus.DISAPPROVED){
            status = "FAILED";
        } else if (subject.getStatus() == SubjectStatus.STUDYING){
            status = "ONGOING";
        } else {
            status = "OTHER";
        }
        return status;
    }

    public void handleServerResponse(){
        try {
            JSONObject object = new JSONObject(this.serverResponse);
            String status = object.getString("status");
            if (status.contains("ok")){
                getSubjectsResults();
                OptimizeResultsActivity.setOriginalSubjects(this.resultSubjects);
                Log.d("RES_A", "passed");
                toNext(true);
            } else {
                Log.d("RES_A", "on_else");
                toNext(false);
            }
        } catch (JSONException e) {
            Log.d("RES_A", "on_stacktrace");
            toNext(false);
            e.printStackTrace();
        }
    }

    public void getSubjectsResults(){
        try {
            ArrayList<Subject> subjectArrayList = new ArrayList<>();
            final JSONObject object = new JSONObject(this.serverResponse);
            final JSONArray array = object.getJSONArray("course");
            final int length = array.length();
            for (int i = 0; i < length; i++){
                final JSONObject jsonObject = array.getJSONObject(i);
                Subject subject = Session.getSubjectManager().getSubjectByCode(jsonObject.getString("code"));
                subjectArrayList.add(subject);
            }
            resultSubjects = subjectArrayList;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void communicate(){
        final WaitingForResultsActivity.ResultsController controller = new WaitingForResultsActivity.ResultsController();
        controller.execute();
        try {
            controller.get();
        } catch (InterruptedException | ExecutionException e) {
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
            if (isURLReachable(WaitingForResultsActivity.this)) {
                try {
                    URL url = new URL(urlAddress + "optimize_plz/");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setRequestMethod("POST");
                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                    String jsonString = formMessage();
                    writer.write(jsonString);
                    writer.close();
                    connection.connect();
                    InputStream in;
                    if (connection.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST)
                        in = new BufferedInputStream(connection.getErrorStream());
                    else
                        in = new BufferedInputStream(connection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
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
            } else {
                this.result = "SERVIDOR NÃO RESPONDENDO";
            }
            this.result = result.toString();
            return result.toString();
        }
    }
    private boolean isURLReachable(Context context) {
        boolean isResponding = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            try {
                URL url = new URL(Session.getServerAddress());   // Change to "http://google.com" for www  test.
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(5 * 1000);          // 10 s.
                urlConnection.connect();
                if (urlConnection.getResponseCode() == 200) {        // 200 = "OK" code (http connection is fine).
                    isResponding = true;
                } else {
                    isResponding = false;
                }
            } catch (MalformedURLException e1) {
                isResponding = false;
            } catch (IOException e) {
                isResponding = false;
            }
        }
        return isResponding;
    }
}
