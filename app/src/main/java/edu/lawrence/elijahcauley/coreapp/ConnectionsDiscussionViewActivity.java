package edu.lawrence.elijahcauley.coreapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class ConnectionsDiscussionViewActivity extends AppCompatActivity {
    //public static String categoryIdForDiscussion = "";
    private JSONArray handles = null;
    private String[] handleStrs;
    private int selected_handle = -1;
    private HashMap<String, Integer> commentId;
    private HashMap<Integer, String> userInfo;
    //private ArrayList<String> userNames;
    //private ArrayList<String> comments;
    private static String discussionIdString;
   //private HashMap<Integer, String> userInfoForDiscussion;
    private Dialog dialogToDelete;
    private Dialog dialogToAdd;
    private LinearLayout commentList;
    public int username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections_discussion_view);
        Intent intent = getIntent();
        discussionIdString = intent.getStringExtra(ConnectionsDiscussionSelectActivity.discussionIdString);
       // userInfoForDiscussion = LoginActivity.userInfo;
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



    private void getUserName(String result) {
        try {
            //JSONArray nameList = new JSONArray(result);
            //for (int x = 0; x < nameList.length(); ++x) {
                JSONObject name = new JSONObject(result);
                userInfo.put(name.getInt("iduser"), name.getString("username"));
                //replaceNumbersWithName();
                //userNames.add(name.getString("username"));
                Log.d("COREAPP", userInfo.toString());

            //}
        }
        catch (JSONException ex) {
            Log.d("COREAPP", "Exception in " + ex.getMessage());
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
            author.setText("Created by: " + object.getString("author") + " at " + object.getString("date"), TextView.BufferType.EDITABLE);
            body.setText(object.getString("body"), TextView.BufferType.EDITABLE);
        }
        catch (JSONException ex) {
            Log.d("COREREST", "Exception in loadHandles: " + ex.getMessage());
        }
        new CommentViewTask().execute();

    }

    private class GetUserNameTask extends AsyncTask<String, Void, String> {
        private String uri;
        private JSONObject toGet;

        GetUserNameTask(int id) {
            Log.d("COREApp", "Getting the Username");
            uri = "http://" + URIHandler.hostName + "/CORE/api/user/" + id;
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
            Log.d("COREApp", "calling getUserName()");
            getUserName(result);
        }
    }

    private void loadComments(String json) {
        handles = null;
        handleStrs = null;
        commentId = new HashMap<String, Integer>();
        userInfo = new HashMap<Integer, String>();
        //comments = new ArrayList<String>();
        //userNames = new ArrayList<String>();

        //Log.d("COREAPPPPPP", json);


        commentList = (LinearLayout) findViewById(R.id.comments);
        commentList.removeAllViews();
        try {
            handles = new JSONArray(json);
            handleStrs = new String[handles.length()];
            for (int n = 0; n < handleStrs.length; n++) {
                String comment = "";
                JSONObject handle = handles.getJSONObject(n);
                TextView view = new TextView(this);
                     //new GetUserNameTask(handle.getInt("user")).execute();
                     //Log.d("COREAPP", "GOING TO PUT IT INTO THE COMMENT");
                     //comment += handle.getInt("user");
                     comment += handle.getString("comment") + "\n";
                     view.setText(comment);
                     view.setId(handle.getInt("idcomment"));
                     view.setLayoutParams(new LinearLayout.LayoutParams(
                             LinearLayout.LayoutParams.FILL_PARENT,
                             LinearLayout.LayoutParams.MATCH_PARENT));
                     //handleStrs[n] = handle.getString("comment");
                     view.setTextSize(20);
                     view.isClickable();
                     view.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             deleteComment(v.getId(), v);
                         }
                     });
                /*Button deleteButton = new Button(this);
                deleteButton.setText("Delete this comment");
                deleteButton.isClickable();
                deleteButton.setBackgroundColor(0xFFaaaaFF);*/
                     //view.setTextColor();
                     commentList.addView(view);
                     //commentList.addView(deleteButton);
                     commentId.put(handle.getString("comment"), handle.getInt("idcomment"));
            }

        } catch (JSONException ex) {
            Log.d("COREREST", "Exception in loadHandles: " + ex.getMessage());
            handleStrs = new String[0];

        }
    }


      /*public void replaceNumbersWithName() {
          for (int x = 0; x < commentList.getChildCount(); ++x) {
              View view = commentList.getChildAt(x);
              commentList.removeView(view);
              TextView view_1 = (TextView) view;
              CharSequence text = view_1.getText();
              String textString = text.toString();
              Log.d("IN REPLACE", textString);
              String[] splittedString = textString.split("");
              String username_1 = "";
              try {
                  username_1 = userInfo.get(Integer.valueOf(splittedString[0]));
              }
              catch (Exception ex) {
                  continue;
              }
              splittedString[0] = username_1;
              String newText = "";
              for (String n : splittedString) {
                  newText += n;
              }
              Log.d("AFTER MATH", newText);
              view_1.setText(newText);
              commentList.addView(view_1);
          }

      }*/

        public void deleteComment(int id, View view) {
            final View viewFinal = view;
            final int idFinal = id;
            dialogToDelete = new Dialog(ConnectionsDiscussionViewActivity.this);
            dialogToDelete.setTitle("Delete a Comment");
            dialogToDelete.setContentView(R.layout.dialog_delete_comment);
            dialogToDelete.show();
            //CHANGE THE BELOW INFO
            Button deleteInputCatergory = (Button) dialogToDelete.findViewById(R.id.delete_comment_selected);
            deleteInputCatergory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //String deleteCategoryName = handleStrs[selected_handle];
                    //Integer CategoryId = discussionId.get(deleteCategoryName);
                    new DeleteTask(String.valueOf(idFinal)).execute();
                    commentList.removeView(viewFinal);
                    dialogToDelete.hide();
                    userMessage("This comment has been deleted.");
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

    private class DeleteTask extends AsyncTask<String, Void, Void> {
        private String uri;

        DeleteTask(String id) {
            uri = "http://" + URIHandler.hostName + "/CORE/api/comment/" + id;
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
            //selected_handle = -1;
            new ListViewTask().execute();
        }
    }

        public void addComment(View view) {
            dialogToAdd = new Dialog(ConnectionsDiscussionViewActivity.this);
            dialogToAdd.setTitle("Add a New Comment");
            dialogToAdd.setContentView(R.layout.dialog_new_comment);
            TextView usernameToShow = (TextView) dialogToAdd.findViewById(R.id.username_to_show);
            usernameToShow.setText("A New Comment by " + LoginActivity.usernameSystem + ":");
            dialogToAdd.show();
            Button addComment = (Button) dialogToAdd.findViewById(R.id.submit_comment);



            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            java.util.Date date = new java.util.Date();

            final String formatedDate = dateFormat.format(date);

            addComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText newComment = (EditText) dialogToAdd.findViewById(R.id.new_comment);
                    String commentString = LoginActivity.usernameSystem + ": " + newComment.getText().toString();
                    String submission = "{\"user\":" + LoginActivity.usernameIdSystem.toUpperCase() + ",\"discussion\":" + discussionIdString + ",\"comment\":" + "\"" + commentString + "\"" + ",\"date\":" + "\"" + formatedDate + "\""  + "}";
                    new PostNewComment(submission).execute();
                    dialogToAdd.hide();
                }
            });
        }

    private class PostNewComment extends AsyncTask<String, Void, String> {
        private String uri;
        private String json;

        PostNewComment(String json) {
            uri = "http://" + URIHandler.hostName + "/CORE/api/comment";
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
            new CommentViewTask().execute();
        }
    }
    }



