package edu.lawrence.elijahcauley.coreapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class CreateNewDiscussionActivity extends AppCompatActivity {
    private String categoryId;
    private String username;
    private JSONArray handles = null;
    private String[] handleStrs;

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

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        java.util.Date date = new java.util.Date();

        String formatedDate = dateFormat.format(date);

        String submission = "{\"author\":" + "\"" + username + "\"" + ",\"title\":" + "\"" + titleString + "\"" + ",\"body\":" + "\"" + bodyString + "\"" + ",\"category\":" + Integer.valueOf(categoryId) + ",\"date\":" + "\"" + formatedDate + "\"" + "}";

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
