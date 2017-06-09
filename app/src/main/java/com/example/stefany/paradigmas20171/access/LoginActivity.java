package com.example.stefany.paradigmas20171.access;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.steps.StepFirstAccessActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText password;
    private AutoCompleteTextView email;
    private Button btnLogin;
    private Button btnRegister;
    private Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (AutoCompleteTextView) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.sign_in_button);
        btnRegister = (Button) findViewById(R.id.sign_up_button);
        btnContinue = (Button) findViewById(R.id.continue_button);

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
                finish();
                startActivity(intentGoRegister);
            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session.setLogged(false);
                Intent intentFirstAccess = new Intent(LoginActivity.this, StepFirstAccessActivity.class);
                startActivity(intentFirstAccess);
            }
        });

    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.equals("ricardo@example.com");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.equals("123456");
    }
    private void attemptLogin() {
        // Reset errors.
        this.email.setError(null);
        this.password.setError(null);

        // Store values at the time of the login attempt.
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();

        boolean cancel = false;
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
        if (TextUtils.isEmpty(email)) {
            this.email.setError(getString(R.string.error_field_required));
            focusView = this.email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            this.email.setError(getString(R.string.error_invalid_email));
            focusView = this.email;
            cancel = true;
        }

        focusView.requestFocus();

        login(cancel);
    }

    private void login(boolean cancel) {
        if (!cancel) {
            //Operation Successful
            Session.setLogged(true);
            Intent intentGoProfile = new Intent(LoginActivity.this, ProfileActivity.class);
            finish();
            startActivity(intentGoProfile);
        }
    }
}
