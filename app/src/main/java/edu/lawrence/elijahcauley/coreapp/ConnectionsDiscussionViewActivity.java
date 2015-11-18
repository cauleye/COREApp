package edu.lawrence.elijahcauley.coreapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class ConnectionsDiscussionViewActivity extends AppCompatActivity {
    //public static String categoryIdForDiscussion = "";
    private JSONArray handles = null;
    private String[] handleStrs;
    private int selected_handle = -1;
    private HashMap<String, Integer> commentId;
    private static String discussionIdString;
    private HashMap<Integer, String> userInfoForDiscussion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections_discussion_view);
        Intent intent = getIntent();
        discussionIdString = intent.getStringExtra(ConnectionsDiscussionSelectActivity.discussionIdString);
        userInfoForDiscussion = LoginActivity.userInfo;
        new ListViewTask().execute();
    }

    private class ListViewTask extends AsyncTask<String, Void, String> {
        private String uri;
        private JSONObject toGet;

        ListViewTask() {
            Log.d("COREApp", "building the ListViewTask");
            uri = "http://" + URIHandler.hostName + "/CORE/api/discussion/" + Integer.valueOf(discussionIdString);
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                return URIHandler.doGet(uri, "");
            } catch (IOException e) {
                return "";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.d("COREApp","calling loadHandles()");
            loadHandles(result);
        }
    }

    private class CommentViewTask extends AsyncTask<String, Void, String> {
        private String uri;
        private JSONObject toGet;

        CommentViewTask() {
            Log.d("COREApp", "building the ListViewTask");
            uri = "http://" + URIHandler.hostName + "/CORE/api/comment?discussion=" + Integer.valueOf(discussionIdString);
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                return URIHandler.doGet(uri, "");
            } catch (IOException e) {
                return "";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.d("COREApp", "calling loadComments()");
            loadComments(result);
        }
    }




    private void loadHandles(String json) {
        Log.d("COREREST","Got JSON: "+json);

        TextView title = (TextView) findViewById(R.id.discussion_title);
        TextView author = (TextView) findViewById(R.id.discussion_author);
        TextView body = (TextView) findViewById(R.id.discussion_body);


        try {
            JSONObject object = new JSONObject(json);
            title.setText(object.getString("title"), TextView.BufferType.EDITABLE);
            author.setText("Created by: " + object.getString("author"), TextView.BufferType.EDITABLE);
            body.setText(object.getString("body"), TextView.BufferType.EDITABLE);
        }
        catch (JSONException ex) {
            Log.d("COREREST", "Exception in loadHandles: " + ex.getMessage());
        }
        new CommentViewTask().execute();

    }

    private void loadComments(String json) {
        handles = null;
        handleStrs = null;
        commentId = new HashMap<String, Integer>();

        //Log.d("COREAPPPPPP", json);



        LinearLayout commentList = (LinearLayout) findViewById(R.id.comments);
        try {
            handles = new JSONArray(json);
            handleStrs = new String[handles.length()];
            for(int n = 0;n < handleStrs.length;n++) {
                String comment = "";
                JSONObject handle = handles.getJSONObject(n);
                TextView view = new TextView(this);
                Log.d("COREAPPPPPP", LoginActivity.userInfo.toString());
                comment += LoginActivity.userInfo.get(handle.getInt("user")) + "\n";
                comment += handle.getString("comment") + "\n";
                view.setText(comment);
                view.setId(handle.getInt("idcomment"));
                view.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
                //handleStrs[n] = handle.getString("comment");
                view.setTextSize(20);
                view.isClickable();
                commentList.addView(view);
                commentId.put(handle.getString("comment"), handle.getInt("idcomment"));
            }
        } catch (JSONException ex) {
            Log.d("COREREST", "Exception in loadHandles: " + ex.getMessage());
            handleStrs = new String[0];
            TextView view = new TextView(this);
            view.setTextSize(20);
            view.setText("There are no comments for this discussion");
            commentList.addView(view);
        }
    }


}
