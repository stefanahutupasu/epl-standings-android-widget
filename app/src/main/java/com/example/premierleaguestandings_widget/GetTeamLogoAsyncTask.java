package com.example.premierleaguestandings_widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

 class GetTeamLogoAsyncTask extends AsyncTask<AsyncTaskParams, Void, AsyncTaskResult> {

    @Override
    protected AsyncTaskResult doInBackground(AsyncTaskParams... params) {
        String crestUrl = params[0].url;
        int imageViewId = params[0].imageViewId;
        Context context = params[0].context;

        try {
            URL url = new URL(crestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap originalBitmap = BitmapFactory.decodeStream(input);

            // Resize the bitmap
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 25, 25, true);

            AsyncTaskResult result = new AsyncTaskResult();
            result.bitmap = resizedBitmap;
            result.imageViewId = imageViewId;
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Handle error case appropriately
        }
    }

    @Override
    protected void onPostExecute(AsyncTaskResult result) {
        // This method will be called on the main thread when the task is complete
        if (result != null && result.bitmap != null) {
            // Use the Bitmap and ImageView ID

            RemoteViews views = new RemoteViews(result.context.getPackageName(), R.layout.widget_layout);
            views.setImageViewBitmap(result.imageViewId, result.bitmap);
        } else {
            // Handle error case appropriately
        }
    }
}

class AsyncTaskParams {
    String url;
    int imageViewId;
    Context context;
}

class AsyncTaskResult {
    Bitmap bitmap;
    int imageViewId;
    Context context;
}


