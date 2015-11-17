package edu.lawrence.elijahcauley.coreapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ConnectionsDiscussionViewActivity extends AppCompatActivity {
    //public static String categoryIdForDiscussion = "";
    private JSONArray handles = null;
    private String[] handleStrs;
    private int selected_handle = -1;
    //private HashMap<String, Integer> discussionId;
    private static String discussionIdString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections_discussion_view);
        Intent intent = getIntent();
        discussionIdString = intent.getStringExtra(ConnectionsDiscussionSelectActivity.discussionIdString);
        new ListViewTask().execute();
    }

    private class ListViewTask extends AsyncTask<String, Void, String> {
        private String uri;
        private JSONObject toGet;

        ListViewTask() {
            Log.d("COREApp", "building the ListViewTask");
            uri = "http://" + URIHandler.hostName + "/CORE/api/discussion/" + Integer.valueOf(discussionIdString);
            //this.toGet = toGet;
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




    private void loadHandles(String json) {
        Log.d("COREREST","Got JSON: "+json);
        handles = null;
        handleStrs = null;

        //ListView handlesList = (ListView) findViewById(R.id.discussion_list); //WE DO NOT HAVE A VIEW FOR THIS YET
        EditText title = (EditText) findViewById(R.id.discussion_title);

        try {
            handles = new JSONArray(json);
            handleStrs = new String[handles.length()];
            for(int n = 0;n < handleStrs.length;n++) {
                JSONObject handle = handles.getJSONObject(n);
                //handleStrs[n] = handle.getString("title") + " - " + handle.getString("author");
                title.setText(handle.getString("title"), TextView.BufferType.EDITABLE);
                Log.d("Happening", "happen");
            }
        } catch (JSONException ex) {
            Log.d("COREREST", "Exception in loadHandles: " + ex.getMessage());
            handleStrs = new String[0];
        }

        Log.d("COREREST","Made "+handleStrs.length + " strings." );

        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, handleStrs);
        handlesList.setAdapter(adapter);
        Log.d("COREREST", "Set adapter");

        handlesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int i, long l) {
                // remember the selection
                selected_handle = i;

            }
        });*/
    }


}
