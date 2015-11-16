package edu.lawrence.elijahcauley.coreapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ConnectionsHomeActivity extends AppCompatActivity {
    private JSONArray handles = null;
    private int selected_handle = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections_home);
        ListViewTask listViewTask = new ListViewTask();
        listViewTask.execute();
    }

    public void goToDiscussionSelect(View view) {
        Intent intent = new Intent(this, ConnectionsDiscussionSelectActivity.class);
        startActivity(intent);
    }

    //TODO add code that puts a new category into the database


    //This code populates the list view with the categories from the database.
    private class ListViewTask extends AsyncTask<String, Void, String> {
        private String uri;
        private JSONObject toGet;

        ListViewTask() {
            Log.d("COREApp", "buling the ListViewTask");
            uri = "http://" + URIHandler.hostName + "/CORE/api/category";
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
        String[] handleStrs = null;

        ListView handlesList = (ListView) findViewById(R.id.discussionTopicsList);
        try {
            handles = new JSONArray(json);
            handleStrs = new String[handles.length()];
            for(int n = 0;n < handleStrs.length;n++) {
                JSONObject handle = handles.getJSONObject(n);
                handleStrs[n] = handle.getString("categoryname");
            }
        } catch (JSONException ex) {
            Log.d("COREREST", "Exception in loadHandles: " + ex.getMessage());
            handleStrs = new String[0];
        }

        Log.d("COREREST","Made "+handleStrs.length + " strings." );

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
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
        });
    }


}
