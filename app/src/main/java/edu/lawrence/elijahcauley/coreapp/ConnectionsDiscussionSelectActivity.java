package edu.lawrence.elijahcauley.coreapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class ConnectionsDiscussionSelectActivity extends AppCompatActivity {
    public static String categoryIdForDiscussion = "";
    private JSONArray handles = null;
    private String[] handleStrs;
    private int selected_handle = -1;
    private HashMap<String, Integer> discussionId;
    public static String discussionIdString;
    public static Dialog dialogToDelete;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections_discussion_select);
        Intent intent = getIntent();
        if (intent.getStringExtra(ConnectionsHomeActivity.categoryIdString) != null) {
            categoryIdForDiscussion = intent.getStringExtra(ConnectionsHomeActivity.categoryIdString);
        }
        Button deleteCategory = (Button) findViewById(R.id.delete_discussion);
        deleteCategory.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  dialogToDelete = new Dialog(ConnectionsDiscussionSelectActivity.this);
                                                  dialogToDelete.setTitle("Delete a Discussion");
                                                  dialogToDelete.setContentView(R.layout.dialog_delete_discussion);
                                                  dialogToDelete.show();
                                                  //CHANGE THE BELOW INFO
                                                  Button deleteInputCatergory = (Button) dialogToDelete.findViewById(R.id.delete_discussion_selected);
                                                  deleteInputCatergory.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          if (selected_handle != -1) {
                                                              String deleteCategoryName = handleStrs[selected_handle];
                                                              Integer CategoryId = discussionId.get(deleteCategoryName);
                                                              new DeleteTask(String.valueOf(CategoryId)).execute();
                                                              dialogToDelete.hide();
                                                          }
                                                          else {
                                                              userMessage("Please select a category to delete");
                                                              dialogToDelete.hide();
                                                          }
                                                      }
                                                  });
                                                  Button cancel = (Button) dialogToDelete.findViewById(R.id.cancel);
                                                  cancel.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          dialogToDelete.hide();
                                                      }
                                                  });
                                              }
                                          }
        );
        new ListViewTask().execute();
    }

    private void userMessage(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

    private class ListViewTask extends AsyncTask<String, Void, String> {
        private String uri;
        private JSONObject toGet;

        ListViewTask() {
            Log.d("COREApp", "building the ListViewTask");
            uri = "http://" + URIHandler.hostName + "/CORE/api/discussion?category=" + Integer.valueOf(categoryIdForDiscussion);
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
        discussionId = new HashMap<String, Integer>();

        ListView handlesList = (ListView) findViewById(R.id.discussion_list);
        try {
            handles = new JSONArray(json);
            handleStrs = new String[handles.length()];
            for(int n = 0;n < handleStrs.length;n++) {
                JSONObject handle = handles.getJSONObject(n);
                handleStrs[n] = handle.getString("title") + " - " + handle.getString("author");
                discussionId.put(handle.getString("title") + " - " + handle.getString("author"), handle.getInt("iddiscussion"));
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

    public void goToDiscussion(View view) {
        if (selected_handle != -1) {
            String selected = handleStrs[selected_handle];
            Integer id = discussionId.get(selected);
            String id_string = String.valueOf(id);
            Log.d("COREApp", id_string);
            Intent intent = new Intent(this, ConnectionsDiscussionViewActivity.class);
            intent.putExtra(discussionIdString, id_string);
            startActivity(intent);
        }
        else {
            userMessage("Please select a discussion.");
        }
    }

    /*public void goToDiscussionView(View view) {
        Intent intent = new Intent(this, ConnectionsDiscussionViewActivity.class);
        startActivity(intent);
    }*/

    public void createNewDiscussion(View view) {
        Intent intent = new Intent(this, CreateNewDiscussionActivity.class);
        intent.putExtra(ConnectionsDiscussionSelectActivity.categoryIdForDiscussion, categoryIdForDiscussion);
        intent.putExtra(LoginActivity.usernameSystem, LoginActivity.usernameSystem);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ConnectionsHomeActivity.class);
        startActivity(intent);
        finish();
    }

    private class DeleteTask extends AsyncTask<String, Void, Void> {
        private String uri;

        DeleteTask(String id) {
            uri = "http://" + URIHandler.hostName + "/CORE/api/discussion/" + id;
        }

        @Override
        protected Void doInBackground(String... urls) {
            try {
                URIHandler.doDelete(uri);
            } catch (IOException e) {
            }
            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Void result) {
            selected_handle = -1;
            new ListViewTask().execute();
        }
    }

}
