package edu.lawrence.elijahcauley.coreapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    public static String usernameSystem;
    public static HashMap<Integer, String> userInfo = new HashMap<Integer, String>();
    public static Dialog dialogToAddNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button addCategory = (Button) findViewById(R.id.create_a_new_account);
        addCategory.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               dialogToAddNewUser = new Dialog(LoginActivity.this);
                                               dialogToAddNewUser.setTitle("Create a New User");
                                               dialogToAddNewUser.setContentView(R.layout.dialog_new_user);
                                               dialogToAddNewUser.show();
                                               Button addInputUser = (Button) dialogToAddNewUser.findViewById(R.id.submit_user);
                                               addInputUser.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {

                                                       //Add code that checks to see if both edit fields are full

                                                       if (checkUserInput()){

                                                           //new CheckUserName().execute();
                                                           addUser();
                                                           dialogToAddNewUser.hide();
                                                       }
                                                   }
                                               });
                                           }
                                       }
        );

    }

    //This code is directly from Gregg's RESTMail android app. I'm working on editing it for us
    public void logIn(View view) {
        EditText userText = (EditText) findViewById(R.id.user_name);
        String userName = userText.getText().toString();
        EditText passwordText = (EditText) findViewById(R.id.password);
        String password = passwordText.getText().toString();
        Log.d("COREApp", password + "  " + userName);
       ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new LogInTask(userName,password).execute();
        } else {
           userMessage("There is no connection.");
        }
    }

    private class LogInTask extends AsyncTask<String, Void, String> {
        private String uri;
        private String username;

        LogInTask(String userName,String password) {
            uri="http://"+URIHandler.hostName+"/CORE/api/user?username="+userName+"&password="+password;
            this.username = userName;
        }

        @Override
        protected String doInBackground(String... urls) {

            try {
                return URIHandler.doGet(uri,"0");
            } catch (IOException e) {
                return "0";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if("0".equals(result))
                userMessage("Log in failed");
            else
                goToMain(result);
                usernameSystem = username;
                userInfo.put(Integer.valueOf(result), usernameSystem);
        }
    }

    private void userMessage(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void goToMain(String userID) {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        //intent.putExtra(USER_ID, userID); --->>> we will have to change this
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
        intent.putExtra(LoginActivity.usernameSystem, usernameSystem);
        startActivity(intent);
        finish();
    }

    public boolean checkUserInput(){
        EditText inputTextUserName = (EditText) dialogToAddNewUser.findViewById(R.id.user_name);
        String inputTextUserNameString = inputTextUserName.getText().toString();

        EditText inputTextPassword = (EditText) dialogToAddNewUser.findViewById(R.id.password);
        String inputTextPasswordString = inputTextPassword.getText().toString();

        EditText inputTextConfirmPassword = (EditText) dialogToAddNewUser.findViewById(R.id.confirm_password);
        String inputTextConfirmPasswordString = inputTextConfirmPassword.getText().toString();

        if (inputTextConfirmPasswordString.equals(inputTextPasswordString)){
            if (inputTextUserNameString.isEmpty()){
                userMessage("Please enter a Username");
                return false;
            } else if (inputTextConfirmPasswordString.isEmpty() || inputTextPasswordString.isEmpty()){
                userMessage("Please enter and confirm a Password");
                return false;
            } else{
                return true;
            }
        } else {
            userMessage("Your Passwords do not match");
            return false;
        }

    }

    public void addUser() {
        EditText inputTextUserName = (EditText) dialogToAddNewUser.findViewById(R.id.user_name);
        String inputTextUserNameString = inputTextUserName.getText().toString();

        EditText inputTextPassword = (EditText) dialogToAddNewUser.findViewById(R.id.password);
        String inputTextPasswordString = inputTextPassword.getText().toString();


        String addUserJSON = "{\"username\":" + "\"" + inputTextUserNameString + "\"" + ", \"password\":"  + "\"" + inputTextPasswordString + "\"" + "}";
        new PostNewUser(addUserJSON).execute();
    }

    private class PostNewUser extends AsyncTask<String, Void, String> {
        private String uri;
        private String json;
        PostNewUser(String json) {
            uri = "http://" + URIHandler.hostName + "/CORE/api/user";
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

        /*
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            return "";
            //new ListViewTask().execute();
        } */
    }
    /*
    private class CheckUserName extends AsyncTask<String, Void, String> {
        EditText inputTextUserName = (EditText) dialogToAddNewUser.findViewById(R.id.user_name);
        String inputTextUserNameString = inputTextUserName.getText().toString();
        private String uri;
        private String json;
        CheckUserName(String json) {
            uri = "http://" + URIHandler.hostName + "/CORE/api/user?user=" + inputTextUserNameString;
            this.json = json;
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                return URIHandler.doGet(uri, json);
            } catch (IOException e) {
                return "";
            }
        }


        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if (Integer.parseInt(result) > 0){

            }

            //return true;
            //new ListViewTask().execute();
        }
    } */
}




