package com.example.aj.supermath;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class UnitTestActivity extends AppCompatActivity {

    public static final String TOPIC_NUMBER = "topic_Number";
    public static final String LIST = "list";
    ArrayList<TestItem> list;
    TextView[] questions = new TextView[10];
    RadioGroup[] choices = new RadioGroup[10];
    RadioButton[] choiceItems = new RadioButton[40];
    int topic_Number;
    int currentScore;
    Random rng = new Random();
    ArrayList<Integer> randomize = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit);

        // Get extras from Intent
        topic_Number = (Integer) getIntent().getExtras().get(TOPIC_NUMBER);
        list = (ArrayList<TestItem>) getIntent().getExtras().get(LIST);

        questions[0] = (TextView) findViewById(R.id.Q1);
        choices[0] = (RadioGroup) findViewById(R.id.choices1);
        choiceItems[0] = (RadioButton) findViewById(R.id.choiceA1);
        choiceItems[1] = (RadioButton) findViewById(R.id.choiceB1);
        choiceItems[2] = (RadioButton) findViewById(R.id.choiceC1);
        choiceItems[3] = (RadioButton) findViewById(R.id.choiceD1);

        questions[1] = (TextView) findViewById(R.id.Q2);
        choices[1] = (RadioGroup) findViewById(R.id.choices2);
        choiceItems[4] = (RadioButton) findViewById(R.id.choiceA2);
        choiceItems[5] = (RadioButton) findViewById(R.id.choiceB2);
        choiceItems[6] = (RadioButton) findViewById(R.id.choiceC2);
        choiceItems[7] = (RadioButton) findViewById(R.id.choiceD2);

        questions[2] = (TextView) findViewById(R.id.Q3);
        choices[2] = (RadioGroup) findViewById(R.id.choices3);
        choiceItems[8] = (RadioButton) findViewById(R.id.choiceA3);
        choiceItems[9] = (RadioButton) findViewById(R.id.choiceB3);
        choiceItems[10] = (RadioButton) findViewById(R.id.choiceC3);
        choiceItems[11] = (RadioButton) findViewById(R.id.choiceD3);

        questions[3] = (TextView) findViewById(R.id.Q4);
        choices[3] = (RadioGroup) findViewById(R.id.choices4);
        choiceItems[12] = (RadioButton) findViewById(R.id.choiceA4);
        choiceItems[13] = (RadioButton) findViewById(R.id.choiceB4);
        choiceItems[14] = (RadioButton) findViewById(R.id.choiceC4);
        choiceItems[15] = (RadioButton) findViewById(R.id.choiceD4);

        questions[4] = (TextView) findViewById(R.id.Q5);
        choices[4] = (RadioGroup) findViewById(R.id.choices5);
        choiceItems[16] = (RadioButton) findViewById(R.id.choiceA5);
        choiceItems[17] = (RadioButton) findViewById(R.id.choiceB5);
        choiceItems[18] = (RadioButton) findViewById(R.id.choiceC5);
        choiceItems[19] = (RadioButton) findViewById(R.id.choiceD5);

        questions[5] = (TextView) findViewById(R.id.Q6);
        choices[5] = (RadioGroup) findViewById(R.id.choices6);
        choiceItems[20] = (RadioButton) findViewById(R.id.choiceA6);
        choiceItems[21] = (RadioButton) findViewById(R.id.choiceB6);
        choiceItems[22] = (RadioButton) findViewById(R.id.choiceC6);
        choiceItems[23] = (RadioButton) findViewById(R.id.choiceD6);

        questions[6] = (TextView) findViewById(R.id.Q7);
        choices[6] = (RadioGroup) findViewById(R.id.choices7);
        choiceItems[24] = (RadioButton) findViewById(R.id.choiceA7);
        choiceItems[25] = (RadioButton) findViewById(R.id.choiceB7);
        choiceItems[26] = (RadioButton) findViewById(R.id.choiceC7);
        choiceItems[27] = (RadioButton) findViewById(R.id.choiceD7);

        questions[7] = (TextView) findViewById(R.id.Q8);
        choices[7] = (RadioGroup) findViewById(R.id.choices8);
        choiceItems[28] = (RadioButton) findViewById(R.id.choiceA8);
        choiceItems[29] = (RadioButton) findViewById(R.id.choiceB8);
        choiceItems[30] = (RadioButton) findViewById(R.id.choiceC8);
        choiceItems[31] = (RadioButton) findViewById(R.id.choiceD8);

        questions[8] = (TextView) findViewById(R.id.Q9);
        choices[8] = (RadioGroup) findViewById(R.id.choices9);
        choiceItems[32] = (RadioButton) findViewById(R.id.choiceA9);
        choiceItems[33] = (RadioButton) findViewById(R.id.choiceB9);
        choiceItems[34] = (RadioButton) findViewById(R.id.choiceC9);
        choiceItems[35] = (RadioButton) findViewById(R.id.choiceD9);

        questions[9] = (TextView) findViewById(R.id.Q10);
        choices[9] = (RadioGroup) findViewById(R.id.choices10);
        choiceItems[36] = (RadioButton) findViewById(R.id.choiceA10);
        choiceItems[37] = (RadioButton) findViewById(R.id.choiceB10);
        choiceItems[38] = (RadioButton) findViewById(R.id.choiceC10);
        choiceItems[39] = (RadioButton) findViewById(R.id.choiceD10);

        while (randomize.size() < 10) {
            int x = rng.nextInt(10);
            if (!(randomize.contains(x))) randomize.add(x);
        }

        for (int i = 0; i < 10; i++) {
            questions[i].setText(list.get(randomize.get(i)).question);
            ArrayList<Integer> generated = new ArrayList<Integer>();
            while (generated.size() < 4) {
                int x = rng.nextInt(4);
                if (!(generated.contains(x))) generated.add(x);
            }

            choiceItems[(i*4)+generated.get(0)].setText(list.get(randomize.get(i)).choice1);
            choiceItems[(i*4)+generated.get(1)].setText(list.get(randomize.get(i)).choice2);
            choiceItems[(i*4)+generated.get(2)].setText(list.get(randomize.get(i)).choice3);
            choiceItems[(i*4)+generated.get(3)].setText(list.get(randomize.get(i)).choice4);
        }
    }

    public void evaluate (View view) {
        currentScore = 0;
        int chosen;
        RadioButton clickedButton;

        for (int i = 0; i < 10; i++) {
            chosen = choices[i].getCheckedRadioButtonId();
            if (chosen > 0) {
                clickedButton = (RadioButton) findViewById(chosen);
                if (clickedButton.getText().toString().equals(list.get(randomize.get(i)).answer)) currentScore++;
            }
        }

        BackgroundTask backgroundTask = new BackgroundTask(UnitTestActivity.this);
        backgroundTask.execute("post_score", "score" + topic_Number + "_post", String.valueOf(currentScore),
                String.valueOf(topic_Number), "Unit Test");
    }

    @Override
    public void onBackPressed() {

    }
}