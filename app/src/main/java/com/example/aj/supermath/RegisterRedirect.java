package com.example.aj.supermath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RegisterRedirect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_redirect);
    }

    public void goBack (View view) {
        Intent back = new Intent (this, MainActivity.class);
        startActivity(back);
    }
}
