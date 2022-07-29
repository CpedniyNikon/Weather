package com.example.weather;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SendRequest extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        String page = "https://api.openweathermap.org/data/2.5/weather?q=" + strings[0] + "&appid=d4f204ce8fd704e6a465d49949543a67&lang=ru";
        Log.d("WTF", page);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(page);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(250);
            urlConnection.setReadTimeout(250);

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append('\n');
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("WTF", stringBuilder.toString());
        return stringBuilder.toString();
    }
}
