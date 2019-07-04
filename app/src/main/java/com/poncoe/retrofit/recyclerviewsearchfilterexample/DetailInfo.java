package com.poncoe.retrofit.recyclerviewsearchfilterexample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetailInfo extends AppCompatActivity {
    TextView nameTxt, isiTxt;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameTxt = findViewById(R.id.nameDetailTxt);
        isiTxt = findViewById(R.id.usernameDetailTxt);
        img = findViewById(R.id.images);

        //GET INTENT
        Intent i = this.getIntent();

        //RECEIVE DATA
        String images = i.getExtras().getString("images");
        String name = i.getExtras().getString("title");
        String isi = i.getExtras().getString("isi");

        //BIND DATA
        img.setImageURI(Uri.parse(images));
        nameTxt.setText(name);
        isiTxt.setText(isi);

        Picasso.get()
                .load(images)
                //.resize(1200, 800)                     // optional
                .into(img);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
