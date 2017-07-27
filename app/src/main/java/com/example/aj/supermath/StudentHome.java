package com.example.aj.supermath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class StudentHome extends AppCompatActivity {

    public static final String USERID = "userId";
    public static final String USERNAME = "username";
    String username;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        userId = (Integer) getIntent().getExtras().get(USERID);
        username = getIntent().getExtras().get(USERNAME).toString();
        TextView welcomeText = (TextView) findViewById(R.id.WelcomeText);
        welcomeText.setText("Logged in as " + username);
    }


    @Override
    public void onBackPressed () {
    }

    public void viewLessons (View view) {
        BackgroundTask backgroundTask = new BackgroundTask(StudentHome.this);
        backgroundTask.execute("getTopics");
    }

    public void viewScores (View view) {
        BackgroundTask backgroundTask = new BackgroundTask(StudentHome.this);
        backgroundTask.execute("getScores");
    }

    public void logout(View view) {
        this.finish();
    }
}
