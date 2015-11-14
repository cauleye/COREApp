package edu.lawrence.elijahcauley.coreapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SplashScreenActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_splash_screen);
        //this code sets a timer on the code in run
        //the 0 represents a variable initial delay, before run starts
        //the 5 represents a variable delay between each iteration of run
        //and then we specify the unit we are using
        final ScheduledExecutorService logInScreen = Executors.newSingleThreadScheduledExecutor();
        logInScreen.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                goToLogin();
            }
        }, 5, 0, TimeUnit.SECONDS);
    }

    public void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
