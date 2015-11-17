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
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class ConnectionsHomeActivity extends AppCompatActivity {
    private JSONArray handles = null;
    private int selected_handle = -1;
    private HashMap<String, Integer> categoryId;
    private String[] handleStrs;
    public static String categoryIdString;
    public static Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections_home);
        ListViewTask listViewTask = new ListViewTask();
        listViewTask.execute();
        Button addCategory = (Button) findViewById(R.id.addTopicLabel);
        addCategory.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               dialog = new Dialog(ConnectionsHomeActivity.this);
                                               dialog.setTitle("Create a New Category");
                                               dialog.setContentView(R.layout.dialog_new_category);
                                               dialog.show();
                                               Button addInputCatergory = (Button) dialog.findViewById(R.id.submit_category);
                                               addInputCatergory.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       addCategory();
                                                   }
                                               });
                                           }
                                       }
        );
        Button deleteCategory = (Button) findViewById(R.id.delete_category);
        addCategory.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               dialog = new Dialog(ConnectionsHomeActivity.this);
                                               dialog.setTitle("Delete a Category");
                                               dialog.setContentView(R.layout.dialog_new_category);
                                               dialog.show();
                                               //CHANGE THE BELOW INFO
                                               Button addInputCatergory = (Button) dialog.findViewById(R.id.delete_category);
                                               addInputCatergory.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       //addCategory();
                                                   }
                                               });
                                           }
                                       }
        );

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
        handleStrs = null; ///-->>> making handleStrs a member variable could possibly be an issue
        categoryId = new HashMap<String, Integer>();

        ListView handlesList = (ListView) findViewById(R.id.discussionTopicsList);
        try {
            handles = new JSONArray(json);
            handleStrs = new String[handles.length()];
            for(int n = 0;n < handleStrs.length;n++) {
                JSONObject handle = handles.getJSONObject(n);
                handleStrs[n] = handle.getString("categoryname");
                categoryId.put(handle.getString("categoryname"), handle.getInt("idcategory"));
            }
        } catch (JSONException ex) {
            Log.d("COREREST", "Exception in loadHandles: " + ex.getMessage());
            handleStrs = new String[0];
        }

        Log.d("COREREST", "Made " + handleStrs.length + " strings.");

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
                //goToSelectedCategory();
            }
        });

    }

    public void goToSelectedCategory(View view) {
        String selected = handleStrs[selected_handle];

        Integer id = categoryId.get(selected);
        String id_string = String.valueOf(id);
        Log.d("COREApp", id_string);
        Intent intent = new Intent(this, ConnectionsDiscussionSelectActivity.class);
        intent.putExtra(categoryIdString, id_string);
        startActivity(intent);
    }


    public void addCategory() {
        EditText inputText = (EditText) dialog.findViewById(R.id.new_category_name);
        String inputTextString = inputText.getText().toString();
        //java.util.Date date = new java.util.Date();
        java.util.Date date = new java.util.Date();

        String addCategoryJSON = "{\"categoryname\":" + inputTextString + "}";
        new PostNewCategory(addCategoryJSON).execute();
    }

    private class PostNewCategory extends AsyncTask<String, Void, String> {
        private String uri;
        private String json;
        PostNewCategory(String json) {
            uri = "http://" + URIHandler.hostName + "/CORE/api/category";
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

            new ListViewTask().execute();
        }
    }

}
