package com.eldar.fit.seminarski.helper;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.eldar.fit.seminarski.R;

import java.lang.reflect.Type;

public class MyApiRequest {

    public static String CONTENT_TYPE_JSON = "application/json";
    public static String ENDPOINT_RESTORANI = "restorani";
    public static String ENDPOINT_AUTH = "auth";

    public static <T> void request(final Activity activity, final String endpoint, final MyUrlConnection.HttpMethod httpMethod, final Object postObject, final MyAbstractRunnable<T> callback) {
        new AsyncTask<Void, Void, MyApiResult>() {

            @Override
            protected MyApiResult doInBackground(Void... voids)
            {
                try {Thread.sleep(1000);} catch (Exception e ) {}
                String jsonPostObject = postObject == null ? null : MyGson.build().toJson(postObject);
                MyApiResult result = MyUrlConnection.request(MyConfig.apiBase + "/" + endpoint, httpMethod, jsonPostObject, CONTENT_TYPE_JSON);
                return result;
            }

            @Override
            protected void onPostExecute(MyApiResult result)
            {
                View parentLayout = activity.findViewById(R.id.content);
                Snackbar snackbar = null;
                if (result.isException) {

                    if (result.resultCode == 0) {
                        Log.i("Test", "asyncApiRequest - error server communication.");
                        snackbar = Snackbar.make(parentLayout, "Greška u komunikaciji sa serverom. ", Snackbar.LENGTH_LONG);
                    } else {
                        Log.i("Test", "asyncApiRequest - error:" + result.errorMessage + " statuscode:" + result.resultCode);
                        snackbar = Snackbar.make(parentLayout, "Greška " + result.resultCode + ": " + result.errorMessage, Snackbar.LENGTH_LONG);
                    }
                }
                else {
                    Type genericType = callback.getGenericType();

                    try {
                        T mappedObj = MyGson.build().fromJson(result.value, genericType);
                        callback.run(mappedObj);
                    } catch (Exception e) {
                        Log.i("Test", "asyncApiRequest - get - Exception:" + e.getMessage());
                        snackbar = Snackbar.make(parentLayout, "Greška pri prikazivanju podataka. ", Snackbar.LENGTH_LONG);
                    }
                }
                if (snackbar != null) {
                    snackbar.setAction("Učitaj ponovo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MyApiRequest.get(activity, endpoint, callback);
                        }
                    });
                    snackbar.show();
                }
            }
        }.execute();
    }

    public static <T> void get(final Activity activity, final String endpoint, final MyAbstractRunnable<T> callback){
        request(activity, endpoint, MyUrlConnection.HttpMethod.GET, null, callback);
    }

    public static <T> void post(final Activity activity, final String endpoint, Object postObject, final MyAbstractRunnable<T> callback){
        request(activity, endpoint, MyUrlConnection.HttpMethod.POST, postObject, callback);
    }
}
