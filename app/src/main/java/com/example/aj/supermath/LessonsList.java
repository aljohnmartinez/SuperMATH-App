package com.example.aj.supermath;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class LessonsList extends AppCompatActivity {

    public static final String TOPIC_NUMBER = "topic_Number";
    public static final String USERID = "userId";
    public static final String TITLE = "title";
    public static final String LIST = "list";
    int topic_Number;
    int userId;
    String title;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        topic_Number = (Integer) getIntent().getExtras().get(TOPIC_NUMBER);
        userId = (Integer) getIntent().getExtras().get(USERID);
        list = (ArrayList<String>) getIntent().getExtras().get(LIST);
        title = getIntent().getExtras().get(TITLE).toString();

        TextView topicTitle = (TextView) findViewById(R.id.TopicTitle);
        topicTitle.setText(title);

        // Add pre-test
        list.add(0, "Diagnostic Test");

        // Add post-test
        list.add("Unit Test");

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        ListView listLessons = (ListView) findViewById(R.id.lessonOptions);
        listLessons.setAdapter(listAdapter);
        listLessons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View v, int position, long id) {
                BackgroundTask backgroundTask = new BackgroundTask(LessonsList.this);
                if (position == 0) {
                    backgroundTask.execute("getTestItems", String.valueOf(topic_Number), "0");
                } else if (position == list.size() - 1) {
                    backgroundTask.execute("getTestItems", String.valueOf(topic_Number), "1");
                } else {
                    backgroundTask.execute("getPage", String.valueOf(topic_Number), String.valueOf(position));
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}