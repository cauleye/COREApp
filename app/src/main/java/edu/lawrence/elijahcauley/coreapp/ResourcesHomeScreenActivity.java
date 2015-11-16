package edu.lawrence.elijahcauley.coreapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ResourcesHomeScreenActivity extends AppCompatActivity {
    private int selected_handle = 0;
    public static String weekNumber;
    private String[] weekNames = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources_home_screen);

        fillListView();
    }

    public void goToSelectedWeek() {
        String weekSelected = weekNames[selected_handle + 1];

        Log.d("COREApp", weekSelected);
        Intent intent = new Intent(this, ResourcesViewPDFActivity.class);
        intent.putExtra(weekNumber, weekSelected);
        startActivity(intent);
    }


    private void fillListView(){
        ListView handlesList = (ListView) findViewById(R.id.weeklySyllabusListView);

        weekNames[0] = "Week 1: Campus Engagement";
        weekNames[1] = "Week 2: Identity";
        weekNames[2] = "Week 3: Relationships";
        weekNames[3] = "Week 4: Social Expectations on Residential Campus";
        weekNames[4] = "Week 5: Advising and the Liberal Arts";
        weekNames[5] = "Week 6: Leaders's Choice and Wellness";
        weekNames[6] = "Week 7: Intersectionality and Inclusion";
        weekNames[7] = "Week 8: Beyond the Bubble";
        weekNames[8] = "Week 9: Finals and Transitioning Home";
        weekNames[9] = "Week 10: Reflection";

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, weekNames);
        handlesList.setAdapter(adapter);

        handlesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int i, long l) {
                // remember the selection
                selected_handle = i;
            }
        });

    }


}
