package com.example.aj.supermath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView username;
    TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void logIn(View view) {
        username = (TextView) findViewById(R.id.usernameLoginField);
        String usernameInput = username.getText().toString();

        password = (TextView) findViewById(R.id.passwordLoginField);
        String passwordInput = password.getText().toString();

        if (validateInput(usernameInput, passwordInput)) {
            password.setText("");
            BackgroundTask backgroundTask = new BackgroundTask(MainActivity.this);
            backgroundTask.execute("login", usernameInput, passwordInput);
        }
    }

    public boolean validateInput (String username, String password) {
        if (username.length() < 4 || username.length() > 32) {
            Toast.makeText(MainActivity.this, "Invalid username. Try again.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 8 || password.length() > 32) {
            Toast.makeText(MainActivity.this, "Invalid password. Try again.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void register (View view) {
        Intent i = new Intent(this, Register.class);
        startActivity(i);
    }
}
