package com.example.t1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ClickOnBookDetexpand extends AppCompatActivity {

    TextView bkdesc,disbkname,disbkauthor;
    Toolbar mtoolbar;
    ImageView coverimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_on_book_detexpand);
        bkdesc=findViewById(R.id.clickbkdesp);
        mtoolbar=findViewById(R.id.clickbooktoolbar);
        coverimage=findViewById(R.id.clickbkimg);
        disbkname=findViewById(R.id.clickbkname);
        disbkauthor=findViewById(R.id.clickbkauthor);



        Intent intent=getIntent();
        String bkisbn=intent.getStringArrayExtra("ID_EXTRA")[0];
        String bkname=intent.getStringArrayExtra("ID_EXTRA")[1];
        String bkauthor=intent.getStringArrayExtra("ID_EXTRA")[2];
        String bkimgurl=intent.getStringArrayExtra("ID_EXTRA")[3];

        //set previous intent known details
        disbkname.setText(bkname);
        disbkauthor.setText("Author: "+bkauthor);


        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle(bkname);
        if(!bkimgurl.isEmpty())
        {
            Picasso.with(ClickOnBookDetexpand.this).load(bkimgurl).into(coverimage);
        }

    }



    public void clickborrow(View view){



    }

    public void clickfav(View view) {
    }
}
