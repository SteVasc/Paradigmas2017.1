package com.example.stefany.paradigmas20171.view_control.access;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.model.infrastructure.Session;
import com.example.stefany.paradigmas20171.view_control.steps.StepFirstAccessActivity;

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
import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {

    private EditText password;
    private AutoCompleteTextView email;
    private Button btnRegister;
    private String urlAddress;
    private String serverResponse;
    private boolean cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        serverResponse ="";
        email = (AutoCompleteTextView) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.sign_up_button);
        urlAddress = Session.getServerAddress();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intentBackLogin = new Intent(RegisterActivity.this, LoginActivity.class);
        finish();
        startActivity(intentBackLogin);
    }

    public void communicate(){
        final RegisterActivity.RegisterController controller = new RegisterActivity.RegisterController();
        controller.execute();
        try {
            controller.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        this.serverResponse = controller.result;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
    private void attemptRegister() {
        // Reset errors.
        this.email.setError(null);
        this.password.setError(null);

        // Store values at the time of the login attempt.
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();

        cancel = false;
        View focusView = null;
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            this.password.setError(getString(R.string.error_invalid_password));
            focusView = this.password;
            cancel = true;
        } else if (!isPasswordValid(password)){
            this.password.setError(getString(R.string.error_invalid_password));
            focusView = this.password;
            cancel = true;
        }
        // Check for a valid email address.
        else if (TextUtils.isEmpty(email)) {
            this.email.setError(getString(R.string.error_field_required));
            focusView = this.email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            this.email.setError(getString(R.string.error_invalid_email));
            focusView = this.email;
            cancel = true;
        } else {
            Session.setEmail(this.email.getText().toString());
            Session.setPassword(this.password.getText().toString());
            communicate();
        }
        if (focusView != null) {
            focusView.requestFocus();
        }
        handleResponse();
    }

    public void handleResponse(){
        try {
            JSONObject object = new JSONObject(this.serverResponse);
            String status = object.getString("status");
            if (status.equals("ok")){
                register();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void abortOperation(){
        cancel = true;
    }

    private void register() {
        if (!cancel) {
            //Operation Successful
            Toast.makeText(RegisterActivity.this, this.serverResponse, Toast.LENGTH_LONG).show();
            Intent intentGoSteps = new Intent(RegisterActivity.this, StepFirstAccessActivity.class);
            finish();
            startActivity(intentGoSteps);
        }
    }

    private class RegisterController extends AsyncTask<String, Void, String> {

        public String result;

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder result = new StringBuilder();
            HttpURLConnection connection = null;
            if (isURLReachable(RegisterActivity.this)) {
                try {
                    URL url = new URL(urlAddress + "register/");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setRequestMethod("PUT");
                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                    JSONObject json = new JSONObject();
                    json.put("email", Session.getEmail());
                    json.put("password", Session.getPassword());
                    writer.write(json.toString());
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
                this.result = "SERVIDOR NÂO RESPONDENDO";
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
