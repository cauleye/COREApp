package edu.lawrence.elijahcauley.coreapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ResourcesViewPDFActivity extends AppCompatActivity {
    private String url = "";
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
            url = "https://drive.google.com/file/d/0BzM5VKncOGOSYTNVNlhLRUI1ODA/view?usp=sharing";
        } else if (week == 2){
            url = "https://drive.google.com/file/d/0BzM5VKncOGOSRm9PLW85dFVqaFE/view?usp=sharing";
        } else if (week == 3){
            url = "https://drive.google.com/file/d/0BzM5VKncOGOSOFNnMmc4Z05ybFE/view?usp=sharing";
        } else if (week == 4){
            url = "https://drive.google.com/file/d/0BzM5VKncOGOSZ0FEdS1fR1ZqSFk/view?usp=sharing";
        } else if (week == 5){
            url = "https://drive.google.com/file/d/0BzM5VKncOGOSaFRBZGppOS1pVzg/view?usp=sharing";
        } else if (week == 6){
            url = "https://drive.google.com/file/d/0BzM5VKncOGOScnQweGRQUU5Tc2M/view?usp=sharing";
        } else if (week == 7){
            url = "https://drive.google.com/file/d/0BzM5VKncOGOSdFJVSWNkMUVvZEE/view?usp=sharing";
        } else if (week == 8){
            url = "https://drive.google.com/file/d/0BzM5VKncOGOSVzJoVG1CQllsS2c/view?usp=sharing";
        } else if (week == 9){
            url = "https://drive.google.com/file/d/0BzM5VKncOGOSeEdjUm5qeC1wMU0/view?usp=sharing";
        } else if (week == 10){
            url = "https://drive.google.com/file/d/0BzM5VKncOGOSRF96Vnc2aDh3Q2c/view?usp=sharing";
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
