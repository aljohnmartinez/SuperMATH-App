package com.example.aj.supermath;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ShowScore extends AppCompatActivity {

    public static final String TOPIC_NAME = "topic_Name";
    public static final String CURRENT_SCORE = "currentScore";
    public static final String TYPE = "type";

    int currentScore;
    String type;
    String topic_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_score);

        currentScore = (Integer) getIntent().getExtras().get(CURRENT_SCORE);
        type = getIntent().getExtras().get(TYPE).toString();
        topic_Name = getIntent().getExtras().get(TOPIC_NAME).toString();

        TextView score = (TextView) findViewById(R.id.score);
        score.setText(String.valueOf(currentScore));
        TextView topicTitleView = (TextView) findViewById(R.id.topicTitleView);
        topicTitleView.setText(topic_Name);
        TextView typeTitleView = (TextView) findViewById(R.id.typeTitleView);
        typeTitleView.setText(type);
    }

    public void back (View view) {
        this.finish();
    }
}
