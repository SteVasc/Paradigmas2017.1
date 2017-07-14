package com.example.stefany.paradigmas20171.view_control.access;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.model.infrastructure.Session;
import com.example.stefany.paradigmas20171.model.infrastructure.Subject;
import com.example.stefany.paradigmas20171.model.infrastructure.SubjectStatus;
import com.example.stefany.paradigmas20171.view_control.steps.StepFirstAccessActivity;

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

public class LoginActivity extends AppCompatActivity {

    private EditText password;
    private EditText email;
    private FloatingActionButton btnLogin;
    private FloatingActionButton btnRegister;
    private Button btnContinue;
    private String urlAddress;
    private String serverResponse;
    private boolean loginDenied;
    private ImageButton btnServerConfig;

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
        btnServerConfig = (ImageButton) findViewById(R.id.button_server_config);
        btnServerConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
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
                Session.setEmail("eu@example.com");
                Session.setPassword("12345");
                Intent intentFirstAccess = new Intent(LoginActivity.this, StepFirstAccessActivity.class);
                startActivity(intentFirstAccess);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

    }

    public void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
        alert.setTitle("Endereço do servidor");
        alert.setMessage("Qual o endereço do servidor");
        alert.setCancelable(true);
        final EditText address = new EditText(this);
        alert.setView(address);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Session.setServerAddress(address.getText().toString());
                Toast.makeText(LoginActivity.this, Session.getServerAddress(), Toast.LENGTH_SHORT).show();
            }
        }).show();

    }

    public void communicateBypass(){
        this.serverResponse = "{\"email\":\"ayy@lma.o\",\"password\":\"dadada\"," +
                "\"semester\":\"5\"," +
                "\"subjects\":" +
                "[{\"code\":\"6438\",\"status\":\"PASSED\"}," +
                "{\"code\":\"6438\",\"status\":\"PASSED\"},{\"code\":\"4162\",\"status\":\"PASSED\"}," +
                "{\"code\":\"6439\",\"status\":\"PASSED\"},{\"code\":\"6214\",\"status\":\"ONGOING\"}," +
                "{\"code\":\"6239\",\"status\":\"PASSED\"},{\"code\":\"6283\",\"status\":\"FAILED\"}]}";
        loginDenied = false;
        try {
            ArrayList<Subject> mySubjects = getMySubjects(serverResponse);
            setCredentials(serverResponse);
            Session.getSubjectManager().setMySubjects(mySubjects);
            ProfileActivity.setSemester(getMaxSemester()-1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private int getMaxSemester() {
        int i = 0;
        for (Subject subject : Session.getSubjectManager().getMySubjects()){
            if (subject.getSemester() > i){
                i = subject.getSemester();
            }
        }
        return i;
    }

    public void setCredentials(String toJson) throws JSONException {
        final JSONObject object = new JSONObject(toJson);
        Session.setEmail(object.getString("email"));
        Session.setPassword(object.getString("password"));
    }

    public ArrayList<Subject> getMySubjects(String toJson) throws JSONException {
        ArrayList<Subject> subjectArrayList = new ArrayList<>();
        final JSONObject object = new JSONObject(toJson);
        final JSONArray array = object.getJSONArray("subjects");
        final int length = array.length();
        for (int i = 0; i < length; i++){
            final JSONObject jsonObject = array.getJSONObject(i);
            Subject subject = Session.getSubjectManager().getSubjectByCode(jsonObject.getString("code"));
            subject = adjustStatus(subject, jsonObject.getString("status"));
            subjectArrayList.add(subject);
        }
        return subjectArrayList;
    }

    public Subject adjustStatus(Subject s, String status){
        Subject subject = s;
        if (status.contains("FAILED")){
            subject.setStatus(SubjectStatus.DISAPPROVED);
        } else if (status.contains("PASSED")){
            subject.setStatus(SubjectStatus.APPROVED);
        } else if (status.contains("ONGOING")){
            subject.setStatus(SubjectStatus.STUDYING);
        }
        return subject;
    }

    public void communicate(){
        final LoginActivity.LoginController controller = new LoginActivity.LoginController();
        try {
            controller.execute();
            controller.get();
            handleAnswer();

        } catch (JSONException e) {
            abortOperation();
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        this.serverResponse = controller.result;
    }

    private void handleAnswer() throws JSONException {
        ArrayList<Subject> mySubjects = getMySubjects(serverResponse);
        setCredentials(serverResponse);
        Session.getSubjectManager().setMySubjects(mySubjects);
    }

    private void attemptLogin() {
        this.email.setError(null);
        this.password.setError(null);
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();

        loginDenied = false;
        View focusView = null;
        if (TextUtils.isEmpty(password)) {
            this.password.setError(getString(R.string.error_invalid_password));
            focusView = this.password;
            loginDenied = true;
        }
        // Check for a valid email address.
        else if (TextUtils.isEmpty(email)) {
            this.email.setError(getString(R.string.error_field_required));
            focusView = this.email;
            loginDenied = true;
        } else {
            try {
                Session.setEmail(email);
                Session.setPassword(password);
                communicate();
            } catch (Exception e) {
                loginDenied = true;
                e.printStackTrace();
            }
        }
        if (focusView != null) {
            focusView.requestFocus();
        }
        login();
    }
    public void abortOperation(){
        loginDenied = true;
    }

    private void login() {
        if (!loginDenied) {
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
            if (isURLReachable(LoginActivity.this)) {
                try {
                    URL url = new URL(urlAddress + "login/");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setRequestMethod("POST");
                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                    /*
                    Passando os atributos para um json
                    */
                    JSONObject json = new JSONObject();
                    json.put("email", Session.getEmail());
                    json.put("password", Session.getPassword());
                    writer.write(json.toString());
                    writer.close();
                    connection.connect();
                    InputStream in;
                    /*
                     Antes de abrir uma conexão com o inputStream, verificar o código de retorno da
                    requisição, caso a o código de retorno seja de erro a conexão de resposta virá
                    através de um ErrorStream, caso contrário virá através de um InputStream.
                    */
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
                    //communicateBypass();
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            } else {
                //communicateBypass();
            }
            this.result = result.toString();
            return result.toString();
        }
        //code based in a answer of StackOverflow forums
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
}
