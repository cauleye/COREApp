package edu.lawrence.elijahcauley.coreapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ResourcesViewPDFActivity extends AppCompatActivity {
    private String url = "http://143.44.69.94/CORE/";
    public static String urlWeekNumber = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources_view_pdf);

        Intent intent = getIntent();
        urlWeekNumber = intent.getStringExtra(ResourcesHomeScreenActivity.weekNumber);
        int week = Integer.parseInt(urlWeekNumber);
        setUpURL(week);

        WebView view = (WebView) this.findViewById(R.id.webView);
        view.getSettings().setJavaScriptEnabled(true);
        view.setWebViewClient(new WebViewClient());
        view.loadUrl(url);

    }



    private void setUpURL(int week){
        if (week == 1){
            url += "Week_One_Syllabus_Fall_Term.pdf";
        } else if (week == 2){
            url += "Week_Two_Syllabus_Fall_Term.pdf";
        } else if (week == 3){
            url += "Week_Three_Syllabus_Fall_Term.pdf";
        } else if (week == 4){
            url += "Week_Four_Syllabus_Fall_Term.pdf";
        } else if (week == 5){
            url += "Week_Five_Syllabus_Fall_Term.pdf";
        } else if (week == 6){
            url += "Week_Six_Syllabus_Fall_Term.pdf";
        } else if (week == 7){
            url += "Week_Seven_Syllabus_Fall_Term.pdf";
        } else if (week == 8){
            url += "Week_Eight_Syllabus_Fall_Term.pdf";
        } else if (week == 9){
            url += "Week_Nine_Syllabus_Fall_Term.pdf";
        } else if (week == 10){
            url += "Week_Ten_Syllabus_Fall_Term.pdf";
        } else {
            System.out.print("Week number: " + week + ", is invalid.");
        }

    }
    //This was an old solution to showing the pdfs. We're no longer using it

    //There's an issue where the api level has to be 21, ours is currently 14, if we want to have the PDF reader work
    /*
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void render(){
        try{
            imageView = (ImageView) findViewById(R.id.image);
            int REQ_WIDTH = imageView.getWidth();
            int REQ_HEIGHT = imageView.getHeight();

            Bitmap bitmap = Bitmap.createBitmap(REQ_WIDTH, REQ_HEIGHT, Bitmap.Config.ARGB_4444);
            //File file = new File("COREApp/app/src/main/assets/Week_One_Syllabus_Fall_Term.pdf"); // put in pdf src
            PdfRenderer renderer = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                renderer = new PdfRenderer(ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY));
            }

            if (currentPage < 0) {
                currentPage = 0;
            } else if (currentPage > renderer.getPageCount()){
                currentPage = renderer.getPageCount() - 1;
            }

            Matrix m = imageView.getImageMatrix();
            Rect rect = new Rect(0,0, REQ_WIDTH, REQ_HEIGHT);
            renderer.openPage(currentPage).render(bitmap, rect, m, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            imageView.setImageMatrix(m);
            imageView.setImageBitmap(bitmap);
            imageView.invalidate();

        } catch (Exception e){
            e.printStackTrace();
        }

    } */

}
