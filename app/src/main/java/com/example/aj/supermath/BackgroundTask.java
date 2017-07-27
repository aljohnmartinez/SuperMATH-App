package com.example.aj.supermath;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by AJ on 5/8/2016.
 */
public class BackgroundTask extends AsyncTask<String, Void, String> {
    public static String strURL = "http://192.168.43.16:3000/";

    Context context;
    Activity activity;
    AlertDialog.Builder builder;
    ProgressDialog progressDialog;
    public static int userId = 0;

    public BackgroundTask(Context context) {
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    protected void onPreExecute() {
        builder = new AlertDialog.Builder(activity);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Connecting to server");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(strURL + params[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            if (!params[0].equals("getTopics")) {
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestProperty("connection", "close");

                // HTTP Request
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = "";
                if (params[0].equals("register")) {
                    String name = params[1];
                    String username = params[2];
                    String password = params[3];

                    data = URLEncoder.encode("name", "UTF-8")+ "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                            URLEncoder.encode("username", "UTF-8")+ "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                            URLEncoder.encode("password", "UTF-8")+ "=" + URLEncoder.encode(password, "UTF-8");
                } else if (params[0].equals("login")) {
                    String username = params[1];
                    String password = params[2];

                    data = URLEncoder.encode("username", "UTF-8")+ "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                            URLEncoder.encode("password", "UTF-8")+ "=" + URLEncoder.encode(password, "UTF-8");
                } else if (params[0].equals("getScores")) {
                    data = URLEncoder.encode("userId", "UTF-8")+ "=" + URLEncoder.encode(String.valueOf(userId), "UTF-8");
                } else if (params[0].equals("getLessons")) {
                    String unit = params[1];
                    String lessonTitle = params[2];

                    data = URLEncoder.encode("unit", "UTF-8")+ "=" + URLEncoder.encode(unit, "UTF-8") + "&" +
                            URLEncoder.encode("lessonTitle", "UTF-8")+ "=" + URLEncoder.encode(lessonTitle, "UTF-8");
                } else if (params[0].equals("getPage")) {
                    String topicId = params[1];
                    String subtopicId = params[2];

                    data = URLEncoder.encode("topicId", "UTF-8")+ "=" + URLEncoder.encode(topicId, "UTF-8") + "&" +
                            URLEncoder.encode("subtopicId", "UTF-8")+ "=" + URLEncoder.encode(subtopicId, "UTF-8");
                } else if (params[0].equals("getTestItems")) {
                    String topicId = params[1];
                    String type = params[2];

                    data = URLEncoder.encode("topicId", "UTF-8")+ "=" + URLEncoder.encode(topicId, "UTF-8") + "&" +
                            URLEncoder.encode("type", "UTF-8")+ "=" + URLEncoder.encode(type, "UTF-8");
                } else if (params[0].equals("post_score")) {
                    String colname = params[1];
                    String score = params[2];
                    String topicId = params[3];
                    String typeTitle = params[4];

                    data = URLEncoder.encode("colname", "UTF-8")+ "=" + URLEncoder.encode(colname, "UTF-8") + "&" +
                            URLEncoder.encode("score", "UTF-8")+ "=" + URLEncoder.encode(score, "UTF-8") + "&" +
                            URLEncoder.encode("id", "UTF-8")+ "=" + URLEncoder.encode(String.valueOf(userId), "UTF-8") + "&" +
                            URLEncoder.encode("topicId", "UTF-8")+ "=" + URLEncoder.encode(topicId, "UTF-8") + "&" +
                            URLEncoder.encode("typeTitle", "UTF-8")+ "=" + URLEncoder.encode(typeTitle, "UTF-8");
                }

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
            }

            // Get HTTP Response
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return stringBuilder.toString().trim();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String json) {
        try {
            progressDialog.dismiss();
            JSONObject jsonObject = new JSONObject(json);
            JSONObject JO = jsonObject.getJSONObject("server_response");
            String code = JO.getString("code");
            String message = JO.getString("message");
            if (code.equals("reg_false") || code.equals("login_false")){
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            } else if (code.equals("reg_true")) {
                Intent intent = new Intent(activity, RegisterRedirect.class);
                activity.startActivity(intent);
            } else if (code.equals("login_true")) {
                int id = JO.getInt("id");
                userId = id;
                String username = JO.getString("username");
                Intent intent = new Intent(activity, StudentHome.class);
                intent.putExtra(StudentHome.USERID, id);
                intent.putExtra(StudentHome.USERNAME, username);
                activity.startActivity(intent);
            } else if (code.equals("getScores_true")) {
                int cnt = JO.getInt("count");
                JSONArray jsonDataArray = JO.getJSONArray("data");
                ArrayList<String> score_pre = new ArrayList<String>();
                ArrayList<String> score_post = new ArrayList<String>();
                int count = 0;

                JSONObject currentObject = jsonDataArray.getJSONObject(0);
                while (count < cnt) {
                    score_pre.add(currentObject.getString("score"+(count+1)+"_pre"));
                    score_post.add(currentObject.getString("score"+(count+1)+"_post"));
                    count++;
                }

                Intent intent = new Intent(activity, ViewScores.class);
                intent.putExtra(ViewScores.SCORE_PRE, (ArrayList<String>) score_pre);
                intent.putExtra(ViewScores.SCORE_POST, (ArrayList<String>) score_post);
                activity.startActivity(intent);
            } else if (code.equals("getTopic_true")) {
                JSONArray jsonDataArray = JO.getJSONArray("data");
                ArrayList<String> list = new ArrayList<String>();
                int count = 0;

                while (count < jsonDataArray.length()) {
                    JSONObject currentObject = jsonDataArray.getJSONObject(count);
                    list.add(currentObject.getString("topicName"));
                    count++;
                }

                Intent intent = new Intent(activity, TopicList.class);
                intent.putExtra(TopicList.USERID, (int) userId);
                intent.putExtra(TopicList.LIST, list);
                activity.startActivity(intent);
            } else if (code.equals("getLesson_true")) {
                int unit = JO.getInt("unit");
                String title = JO.getString("title");
                JSONArray jsonDataArray = JO.getJSONArray("data");
                ArrayList<String> list = new ArrayList<String>();
                int count = 0;

                while (count < jsonDataArray.length()) {
                    JSONObject currentObject = jsonDataArray.getJSONObject(count);
                    list.add(currentObject.getString("subtopicName"));
                    count++;
                }

                Intent intent = new Intent(activity, LessonsList.class);
                intent.putExtra(LessonsList.USERID, (int) userId);
                intent.putExtra(LessonsList.TOPIC_NUMBER, (int) unit);
                intent.putExtra(LessonsList.TITLE, title);
                intent.putExtra(LessonsList.LIST, list);
                activity.startActivity(intent);
            } else if (code.equals("getPage_true")) {
                String title = JO.getString("title");
                String text1 = JO.getString("text1");
                String text2 = JO.getString("text2");
                String img1filename = JO.getString("img1filename");
                String img2filename = JO.getString("img2filename");
                Intent intent = new Intent(activity, LessonProper.class);
                intent.putExtra(LessonProper.LESSON_NAME, title);
                intent.putExtra(LessonProper.TEXT1, text1);
                intent.putExtra(LessonProper.TEXT2, text2);
                intent.putExtra(LessonProper.IMG1FILENAME, img1filename);
                intent.putExtra(LessonProper.IMG2FILENAME, img2filename);
                activity.startActivity(intent);
            } else if (code.equals("getTestItems_true")) {
                int topicId = JO.getInt("topicId");
                int type = JO.getInt("type");
                JSONArray jsonDataArray = JO.getJSONArray("data");
                ArrayList<TestItem> list = new ArrayList<TestItem>();
                int count = 0;

                while (count < jsonDataArray.length()) {
                    JSONObject currentObject = jsonDataArray.getJSONObject(count);
                    TestItem currentItem = new TestItem( currentObject.getString("question"), currentObject.getString("choice1"),
                            currentObject.getString("choice2"), currentObject.getString("choice3"), currentObject.getString("choice4"),
                            currentObject.getString("answer"));
                    list.add(currentItem);
                    count++;
                }

                if (type == 0) {
                    Intent intent = new Intent(activity, DiagnosticActivity.class);
                    intent.putExtra(DiagnosticActivity.TOPIC_NUMBER, (int) topicId);
                    intent.putExtra(DiagnosticActivity.LIST, (ArrayList<TestItem>) list);
                    activity.startActivity(intent);
                } else {
                    Intent intent = new Intent(activity, UnitTestActivity.class);
                    intent.putExtra(UnitTestActivity.TOPIC_NUMBER, (int) topicId);
                    intent.putExtra(UnitTestActivity.LIST, (ArrayList<TestItem>) list);
                    activity.startActivity(intent);
                }
            } else if (code.equals("post_score_true")) {
                int score = JO.getInt("score");
                String topicTitle = JO.getString("topicTitle");
                String typeTitle = JO.getString("typeTitle");
                Intent intent = new Intent(activity, ShowScore.class);
                intent.putExtra(ShowScore.CURRENT_SCORE, score);
                intent.putExtra(ShowScore.TOPIC_NAME, topicTitle);
                intent.putExtra(ShowScore.TYPE, typeTitle);
                activity.startActivity(intent);
                activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
