package com.eldar.fit.seminarski.helper;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MyUrlConnection {

    public enum HttpMethod {
        GET, POST, HEAD, OPTIONS, TRACE
    }

    // ... maybe create an api endpoint that returns a <-
    // list of all the success status codes that it returns?
    public static List<Integer> OkStatuses = Arrays.asList(200, 201, 202, 204);
    public static String charset = "UTF-8";

    public static MyApiResult request(String urlString, HttpMethod httpMethod, String postData, String contentType) {

        HttpURLConnection urlConnection = null;
        String result = null;

        try {
            Log.i("Test", "requestTry");
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestProperty("Content-type", contentType);
            urlConnection.setRequestProperty("Accept", contentType);
            urlConnection.setRequestProperty("Accept-Charset", charset);
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            urlConnection.setRequestMethod(httpMethod.toString());


            if (postData != null) {
                Log.i("Test", "postData" + postData);
                urlConnection.setDoOutput(true);
                byte[] outputBytes = postData.getBytes(charset);
                OutputStream ostream = urlConnection.getOutputStream();
                ostream.write(outputBytes);
                ostream.flush();
                ostream.close();
            }

            int statusCode = urlConnection.getResponseCode();
            Log.i("Test", "TRY URL: STATUS CODE " + statusCode);
            if (OkStatuses.contains(statusCode)) {
                Log.i("Test", "status Ok: " + statusCode);
                InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                String response = convertToString(is);

                return MyApiResult.OK(response);
            } else {
                Log.i("Test", "status 0");
                InputStream is = new BufferedInputStream(urlConnection.getErrorStream());
                String response = convertToString(is);

                return MyApiResult.Error(statusCode, response);
            }


        } catch(Exception e) {
            e.printStackTrace();
            return MyApiResult.Error(0, e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private static String convertToString(InputStream in) {
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

}
