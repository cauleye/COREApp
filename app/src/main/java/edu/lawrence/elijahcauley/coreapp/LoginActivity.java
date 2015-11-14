package edu.lawrence.elijahcauley.coreapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    //This code is directly from Gregg's RESTMail android app. I'm working on editing it for us
    public void logIn(View view) {
        EditText userText = (EditText) findViewById(R.id.user_name);
        String userName = userText.getText().toString();
        EditText passwordText = (EditText) findViewById(R.id.password);
        String password = passwordText.getText().toString();
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

        LogInTask(String userName,String password) {
            uri="http://"+URIHandler.hostName+"/CORE/api/user?user="+userName+"&password="+password;
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
        startActivity(intent);
        finish();
    }
}




