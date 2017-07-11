package com.example.stefany.paradigmas20171.view_control.access;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.model.infrastructure.Session;
import com.example.stefany.paradigmas20171.view_control.server_control.ServerAccessController;
import com.example.stefany.paradigmas20171.view_control.steps.StepFirstAccessActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    private EditText password;
    private EditText email;
    private FloatingActionButton btnLogin;
    private FloatingActionButton btnRegister;
    private Button btnContinue;
    private String urlAddress;
    private String serverResponse;
    private boolean cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        btnLogin = (FloatingActionButton) findViewById(R.id.fab_sign_in);
        btnRegister = (FloatingActionButton) findViewById(R.id.fab_sign_up);
        btnContinue = (Button) findViewById(R.id.continue_button);
        urlAddress = Session.getServerAddress();
        serverResponse = "";
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGoRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intentGoRegister);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session.setLogged(false);
                Intent intentFirstAccess = new Intent(LoginActivity.this, StepFirstAccessActivity.class);
                startActivity(intentFirstAccess);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

    }

    public void communicate(){
        final LoginActivity.LoginController controller = new LoginActivity.LoginController();
        controller.execute();
        try {
            controller.get();
        } catch (InterruptedException e) {
            abortOperation();
            e.printStackTrace();
        } catch (ExecutionException e) {
            abortOperation();
            e.printStackTrace();
        }
        this.serverResponse = controller.result;
    }

    private void attemptLogin() {
        this.email.setError(null);
        this.password.setError(null);
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();

        cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(password)) {
            this.password.setError(getString(R.string.error_invalid_password));
            focusView = this.password;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            this.email.setError(getString(R.string.error_field_required));
            focusView = this.email;
            cancel = true;
        }
        try {
            Session.setEmail(email);
            Session.setPassword(password);
            communicate();
            Toast.makeText(LoginActivity.this, this.serverResponse, Toast.LENGTH_LONG).show();
            Log.d("RETURN", serverResponse);
        } catch (Exception e){
            cancel = true;
            e.printStackTrace();
        }
        if (focusView != null) {
            focusView.requestFocus();
        }
        login();
    }
    public void abortOperation(){
        cancel = true;
    }

    private void login() {
        if (!cancel) {
            //Operation Successful
            Session.setLogged(true);
            Intent intentGoProfile = new Intent(LoginActivity.this, ProfileActivity.class);
            startActivity(intentGoProfile);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
    }

    private class LoginController extends AsyncTask<String, Void, String>{

        public String result;

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder result = new StringBuilder();
            HttpURLConnection connection = null;
            try {
                URL url = new URL(urlAddress + "login/");
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("POST");
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                String email = Session.getEmail();
                String password = Session.getPassword();
                String jsonString = "{\"email\" : \"" + email + "\", \"password\" : \"" + password + "\"}";
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
                abortOperation();
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
