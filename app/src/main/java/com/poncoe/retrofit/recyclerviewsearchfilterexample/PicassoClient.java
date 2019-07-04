package com.poncoe.retrofit.recyclerviewsearchfilterexample;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicassoClient {

    public static void downloadImage(Context c, String url, ImageView img) {
        if (url != null && url.length() > 0) {
            Picasso.get().load(url).placeholder(R.mipmap.ic_launcher).into(img);

        } else {
            Picasso.get().load(R.mipmap.ic_launcher).into(img);
        }
    }
}