package edu.lawrence.elijahcauley.coreapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ConnectionsHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections_home);
    }

    public void goToDiscussionSelect(View view) {
        Intent intent = new Intent(this, ConnectionsDiscussionSelectActivity.class);
        startActivity(intent);
    }


}
