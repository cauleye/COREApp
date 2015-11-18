package edu.lawrence.elijahcauley.coreapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrientationHomeScreenActivity extends AppCompatActivity {
    public static String url;

    //Add code here
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation_home_screen);

        final Button shareButton = (Button) findViewById(R.id.welcomeWeekButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToWelcomeWeekSchedule();
            }
        });

        final Button studentOrganizationsButton = (Button) findViewById(R.id.fallAndWinterTermButton);
        studentOrganizationsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToFallTermCORELeaderOrientation();
            }
        });

    }

    public void goToWelcomeWeekSchedule(){
        String shareURL = "https://drive.google.com/file/d/0BzM5VKncOGOSZnlBTENnbXdUZW8/view?usp=sharing";
        Intent intent = new Intent(this, OrientationWebViewActivity.class);
        intent.putExtra(url, shareURL);
        startActivity(intent);

    }

    public void goToFallTermCORELeaderOrientation(){
        String studentOrgsUrl = "https://drive.google.com/file/d/0BzM5VKncOGOSQW8zQjNERUZxQWM/view?usp=sharing";
        Intent intent = new Intent(this, OrientationWebViewActivity.class);
        intent.putExtra(url, studentOrgsUrl);
        startActivity(intent);

    }
}
