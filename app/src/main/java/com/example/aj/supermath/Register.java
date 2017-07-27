package com.example.aj.supermath;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void submit (View view) {
        TextView name = (TextView) findViewById(R.id.nameRegisterField);
        String nameInput = name.getText().toString();

        TextView username = (TextView) findViewById(R.id.usernameRegisterField);
        String usernameInput = username.getText().toString();

        TextView password = (TextView) findViewById(R.id.passwordRegisterField);
        String passwordInput = password.getText().toString();

        TextView password2 = (TextView) findViewById(R.id.password2RegisterField);
        String password2Input = password2.getText().toString();

        if (validateInput(nameInput, usernameInput, passwordInput, password2Input)) {
            BackgroundTask backgroundTask = new BackgroundTask(Register.this);
            backgroundTask.execute("register", nameInput, usernameInput, passwordInput);
        }
    }

    public boolean validateInput (String name, String username, String password, String password2) {
        if (name.length() < 4) {
            Toast.makeText(Register.this, "Invalid name. Try again.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (username.length() < 4 || username.length() > 32) {
            Toast.makeText(Register.this, "Invalid username. Try again.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 8 || password.length() > 32) {
            Toast.makeText(Register.this, "Invalid password. Try again.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password2.length() < 8 || password2.length() > 32) {
            Toast.makeText(Register.this, "Invalid password. Try again.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equals(password2)) {
            Toast.makeText(Register.this, "Passwords do not match. Try again.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
