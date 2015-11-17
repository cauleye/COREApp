package edu.lawrence.elijahcauley.coreapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class ResourcesViewPDFActivity extends AppCompatActivity {

    private ImageView imageView;
    private int currentPage = 0;
    private Button next, previous;
    private File file;
    public static String fileWeekNumber = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources_view_pdf);

        Intent intent = getIntent();
        fileWeekNumber = intent.getStringExtra(ResourcesHomeScreenActivity.weekNumber);
        int week = Integer.parseInt(fileWeekNumber);
        setUpFile(week);

        next = (Button) findViewById(R.id.next);
        previous = (Button) findViewById(R.id.previous);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage++;
                render();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage--;
                render();
            }
        });
    }

    private void setUpFile(int week){
        if (week == 1){
            file = new File("COREApp/app/src/main/assets/Week_One_Syllabus_Fall_Term.pdf");
        } else if (week == 2){
            file = new File("COREApp/app/src/main/assets/Week_Two_Syllabus_Fall_Term.pdf");
        } else if (week == 3){
            file = new File("CORE/COREApp/app/src/main/assets/Week_Three_Syllabus_Fall_Term.pdf");
        } else if (week == 4){
            file = new File("CORE/COREApp/app/src/main/assets/Week_Four_Syllabus_Fall_term.pdf");
        } else if (week == 5){
            file = new File("COREApp/app/src/main/assets/Week_Five_Syllabus_Fall_Term.pdf");
        } else if (week == 6){
            file = new File("COREApp/app/src/main/assets/Week_Six_Syllabus_Fall_Term.pdf");
        } else if (week == 7){
            file = new File("COREApp/app/src/main/assets/Week_Seven_Syllabus_Fall_Term.pdf");
        } else if (week == 8){
            file = new File("COREApp/app/src/main/assets/Week_Eight_Syllabus_Fall_Term.pdf");
        } else if (week == 9){
            file = new File("COREApp/app/src/main/assets/Week_Nine_Syllabus_Fall_Term.pdf");
        } else if (week == 10){
            file = new File("COREApp/app/src/main/assets/Week_Ten_Syllabus_Fall_Term.pdf");
        } else {
            System.out.print("Week number: " + week + ", is invalid.");
        }

    }

    //There's an issue where the api level has to be 21, ours is currently 14, if we want to have the PDF reader work

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

    }
}
