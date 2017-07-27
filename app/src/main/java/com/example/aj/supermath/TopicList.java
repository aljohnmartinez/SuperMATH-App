package com.example.aj.supermath;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class TopicList extends AppCompatActivity {

    public static final String USERID = "userId";
    public static final String LIST = "list";
    int userId;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userId = (Integer) getIntent().getExtras().get(USERID);
        list = (ArrayList<String>) getIntent().getExtras().get(LIST);

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        ListView listLessons = (ListView) findViewById(R.id.topicListView);
        listLessons.setAdapter(listAdapter);
        listLessons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View v, int position, long id) {
                BackgroundTask backgroundTask = new BackgroundTask(TopicList.this);
                backgroundTask.execute("getLessons", String.valueOf(position + 1), list.get(position));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}