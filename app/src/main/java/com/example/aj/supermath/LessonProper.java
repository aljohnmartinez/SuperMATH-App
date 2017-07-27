package com.example.aj.supermath;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LessonProper extends AppCompatActivity {

    public static final String LESSON_NAME = "lesson_Name";
    public static final String TEXT1 = "text1";
    public static final String TEXT2 = "text2";
    public static final String IMG1FILENAME = "img1filename";
    public static final String IMG2FILENAME = "img2filename";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_proper);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // Get extras from Intent
        String lesson_Name = getIntent().getExtras().get(LESSON_NAME).toString();
        String txt1 = getIntent().getExtras().get(TEXT1).toString();
        String txt2 = getIntent().getExtras().get(TEXT2).toString();
        String img1filename = getIntent().getExtras().get(IMG1FILENAME).toString();
        String img2filename = getIntent().getExtras().get(IMG2FILENAME).toString();

        TextView titleText = (TextView) findViewById(R.id.titleText);
        titleText.setText(lesson_Name);

        TextView text1 = (TextView) findViewById(R.id.text1);
        text1.setText(txt1);

        if (!img1filename.equals(null)) {
            ImageView image1 = (ImageView) findViewById(R.id.image1);
            Bitmap bitmap = getBitmapFromURL(BackgroundTask.strURL + "images/" + img1filename);
            image1.setImageBitmap(bitmap);
        }

        TextView text2 = (TextView) findViewById(R.id.text2);
        text2.setText(txt2);

        if (!img1filename.equals(null)) {
            ImageView image2 = (ImageView) findViewById(R.id.image2);
            Bitmap bitmap = getBitmapFromURL(BackgroundTask.strURL + "images/" + img2filename);
            image2.setImageBitmap(bitmap);
        }
    }

    public Bitmap getBitmapFromURL (String src) {
        try {
            URL url = new URL (src);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("connection", "close");

            InputStream inputStream = httpURLConnection.getInputStream();
            Bitmap image = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            httpURLConnection.disconnect();
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
