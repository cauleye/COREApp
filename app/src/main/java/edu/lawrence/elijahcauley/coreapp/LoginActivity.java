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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //the immersive login screen is created
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        setContentView(R.layout.activity_login);
        //this code sets a timer on the code in run
        //the 0 represents a variable initial delay, before run starts
        //the 5 represents a variable delay between each iteration of run
        //and then we specify the unit we are using
        final ScheduledExecutorService logInScreen = Executors.newSingleThreadScheduledExecutor();
        logInScreen.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {

            }
        }, 0, 5, TimeUnit.SECONDS);

        //it's not consistent when we leave the application

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
            userMessage(getResources().getString(R.string.message_no_network));
        }
    }

    private class LogInTask extends AsyncTask<String, Void, String> {
        private String uri;

        LogInTask(String userName,String password) {
            uri="http://"+URIHandler.hostName+"/RESTMail/api/user?user="+userName+"&password="+password;
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
                userMessage(getResources().getString(R.string.message_login_failed));
            else
                goToMain(result);
        }
    }

    private void userMessage(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void goToMain(String userID) {
        Intent intent = new Intent(this, MessagePickerActivity.class);
        intent.putExtra(USER_ID, userID);
        startActivity(intent);
    }
}



}
