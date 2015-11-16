package edu.lawrence.elijahcauley.coreapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void goToConnectionsHome(View view) {
        Intent intent = new Intent(this, ConnectionsHomeActivity.class);
        startActivity(intent);
    }

    public void goToOrientation(View view) {
        Intent intent = new Intent(this, OrientationHomeScreenActivity.class);
        startActivity(intent);

    }

    public void goToExpectations(View view) {


    }
}
