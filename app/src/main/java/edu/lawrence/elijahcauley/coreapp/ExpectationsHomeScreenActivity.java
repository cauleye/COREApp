package edu.lawrence.elijahcauley.coreapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ExpectationsHomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_expectations_home_screen);
        String url = "https://moodle.lawrence.edu/course/view.php?id=6541";
        WebView view = (WebView) this.findViewById(R.id.webView);
        view.getSettings().setJavaScriptEnabled(true);
        view.setWebViewClient(new WebViewClient());
        view.loadUrl(url);
    }
}
