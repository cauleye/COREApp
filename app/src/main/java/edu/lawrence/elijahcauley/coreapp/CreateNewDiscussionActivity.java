package edu.lawrence.elijahcauley.coreapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class CreateNewDiscussionActivity extends AppCompatActivity {
    private String categoryId;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_discussion);
        Intent intent = getIntent();
        categoryId = intent.getStringExtra(ConnectionsDiscussionSelectActivity.categoryIdForDiscussion);
        username = LoginActivity.usernameSystem;
    }

    private void userMessage(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void submitNewDiscussion(View view) {
        EditText title = (EditText) findViewById(R.id.discussion_title);
        String titleString = title.getText().toString();
        EditText body = (EditText) findViewById(R.id.discussion_body);
        String bodyString = body.getText().toString();

        /*Calendar date = Calendar.getInstance();
        long date_2 = date.getTimeInMillis();
        java.util.Date date_1;
        java.util.Date date_3 = new java.util.Date(date_2);
       // Timestamp time = new Timestamp(date_2);
        //String stringDate = date_2.*/

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        java.util.Date date = new java.util.Date();
        //dateFormat.format(date);
        String hello = dateFormat.format(date);
        //hello = hello.replace("/", "-");
        //String[] hello_2 = hello.split(" ");
        //String hello_3 = hello_2[0];

        //Log.d("COREAPPINCREATE--------", stringDate);

        String submission = "{\"author\":" + "\"" + username + "\"" + ",\"title\":" + "\"" + titleString + "\"" + ",\"body\":" + "\"" + bodyString + "\"" + ",\"category\":" + Integer.valueOf(categoryId) + ",\"date\":" + "\"" + hello + "\"" + "}";

        new PostNewDiscussion(submission).execute();
    }

     class PostNewDiscussion extends AsyncTask<String, Void, String> {
        private String uri;
        private String json;

        PostNewDiscussion(String json) {
            uri = "http://" + URIHandler.hostName + "/CORE/api/discussion";
            this.json = json;
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                return URIHandler.doPost(uri, json);
            } catch (IOException e) {
                return "";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            userMessage("Your discussion has been successfully added.");
            returnToDiscussionList();
        }
    }

    public void returnToDiscussionList() {
        Intent intent = new Intent(this, ConnectionsDiscussionSelectActivity.class);
        startActivity(intent);
    }




}
