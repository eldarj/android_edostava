package com.eldar.fit.seminarski.helper;

import android.os.AsyncTask;
import android.util.Log;
import java.lang.reflect.Type;

public class MyApiRequest {
    // Content type
    public static String CONTENT_TYPE_JSON = "application/json";

    /* ENDPOINTS */
    // Lokacije
    public static String ENDPOINT_LOCATIONS = "lokacije";

    // Restorani
    public static String ENDPOINT_RESTORANI = "restorani";
    public static String ENDPOINT_RESTORANI_LIKE = "restorani/%1$d/like";
    public static String ENDPOINT_RESTORANI_UNLIKE = "restorani/%1$d/unlike";
    public static String ENDPOINT_RESTORANI_HRANA = "restorani/%1$d/hrana";
    public static String ENDPOINT_RESTORANI_KOMENTARI = "restorani/%1$d/komentari/subscribe";
    public static String ENDPOINT_RESTORANI_KOMENTARI_NOVI = "restorani/%1$d/komentari/novi";

    // Narudzbe
    public static String ENDPOINT_NARUDZBE = "narudzbe";
    public static String ENDPOINT_NARUDZBE_CREATE = "narudzbe/create";
    public static String ENDPOINT_NARUDZBE_STATUS = "narudzbe/%1$d/status";
    public static String ENDPOINT_NARUDZBE_DELETE = "narudzbe/%1$d/delete";

    // Auth & User
    public static String ENDPOINT_USER_LOGIN_CHECK_AUTH = "auth";
    public static String ENDPOINT_USER_LOGOUT = "auth/logout";
    public static String ENDPOINT_USER_REGISTER_AUTH = "auth/register";
    public static String ENDPOINT_USER_UPDATE_AUTH = "auth/update";
    public static String ENDPOINT_USER_DELETE_AUTH = "auth/delete";
    public static String ENDPOINT_USER_UPLOAD_IMAGE = "auth/user/image";

    public static <T> AsyncTask request(final String endpoint, final MyUrlConnection.HttpMethod httpMethod, final Object postObject, final MyAbstractRunnable<T> callback) {
        try {
            AsyncTask<Void, Void, MyApiResult> task = new AsyncTask<Void, Void, MyApiResult>() {

                @Override
                protected MyApiResult doInBackground(Void... voids) {
                    //Log.i("Test", "---in doInBackground---");
                    try {Thread.sleep(1000);} catch (Exception e) {e.printStackTrace();} // zakomentariši ovo za produkciju

                    String jsonPostObject = postObject == null ? null : MyGson.build().toJson(postObject);
                    MyApiResult result = MyUrlConnection.request(MyConfig.apiBase + "/" + endpoint, httpMethod, jsonPostObject, CONTENT_TYPE_JSON);

                    return result;
                }

                @Override
                protected void onPostExecute(MyApiResult result) {
                    // Log.i("Test", "---onPostExecute---");
                    if (result.isException) {
                        if (result.resultCode == 0) {
                            //Log.i("Test", "asyncApiRequest - error server communication.");
                            callback.error(result.resultCode, "Greška u komunikaciji sa serverom.");
                        } else {
                            //Log.i("Test", "asyncApiRequest - error:" + result.errorMessage + " statuscode:" + result.resultCode);
                            callback.error(result.resultCode, "Greška na serveru, provjerite podatke.");
                        }
                    } else {
                        Type genericType = callback.getGenericType();

                        try {
                            //Log.i("Test", "asyncApiRequest JSONResult: " + result.value);
                            T mappedObj = MyGson.build().fromJson(result.value, genericType);
                            callback.run(mappedObj);
                        } catch (Exception e) {
                            Log.i("Test", "asyncApiRequest - Exception: " + e.getMessage());
                            callback.error(0, "Greška pri prikazivanju podataka.");
                        }
                    }
                }
            };

            task.execute();
            return task;
        } catch (Exception e) {
            Log.i("Test", "---asyncTask--- Catched exception...");
            //e.printStackTrace();
        }
        return null;
    }

    public static <T> AsyncTask get(final String endpoint, final MyAbstractRunnable<T> callback){
        return request(endpoint, MyUrlConnection.HttpMethod.GET, null, callback);
    }

    public static <T> AsyncTask post(final String endpoint, Object postObject, final MyAbstractRunnable<T> callback){
        return request(endpoint, MyUrlConnection.HttpMethod.POST, postObject, callback);
    }

    // put? handle with post
    // delete? handle with post
}
