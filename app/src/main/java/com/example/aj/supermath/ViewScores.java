package com.example.aj.supermath;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewScores extends AppCompatActivity {

    public static final String SCORE_PRE = "score_pre";
    public static final String SCORE_POST = "score_post";
    ArrayList<String> score_pre;
    ArrayList<String> score_post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_scores);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        score_pre = (ArrayList<String>) getIntent().getExtras().get(SCORE_PRE);
        score_post = (ArrayList<String>) getIntent().getExtras().get(SCORE_POST);

        TableLayout table = (TableLayout)findViewById(R.id.table);
        table.setStretchAllColumns(true);
        table.bringToFront();
        for(int i = 0; i < score_pre.size(); i++){
            TableRow tr =  new TableRow(this);
            TextView c1 = new TextView(this);
            c1.setText(String.valueOf(i + 1));
            c1.setGravity(Gravity.RIGHT);
            c1.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            TextView c2 = new TextView(this);
            c2.setText(String.valueOf(score_pre.get(i)));
            c2.setGravity(Gravity.RIGHT);
            c2.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            TextView c3 = new TextView(this);
            c3.setText(String.valueOf(score_post.get(i)));
            c3.setGravity(Gravity.RIGHT);
            c3.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);

            tr.addView(c1);
            tr.addView(c2);
            tr.addView(c3);
            table.addView(tr);
        }

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
