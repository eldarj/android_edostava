package com.eldar.fit.seminarski.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class MyAsyncImageInflater extends AsyncTask<String, Void, Bitmap> {
    ImageView image;

    public MyAsyncImageInflater(ImageView image) {
        this.image = image;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            Bitmap decodedBitmap = BitmapFactory.decodeStream(in, null, options);

            bitmap = Bitmap.createScaledBitmap(decodedBitmap, 300, 300, true);
            Log.i("TEST BITMAP", "TEST BITMAP SUCCESS!");

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        Log.i("ONPOST EXECUTE BITMAP", "ONPOST BITMAP SUCCESS!");
        image.setImageBitmap(result);
    }
}
