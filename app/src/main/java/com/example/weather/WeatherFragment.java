package com.example.weather;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

public class WeatherFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private TextView tempTextView = null;
    private TextView speedTextView = null;
    private TextView pressureTextView = null;
    private TextView humidityTextView = null;
    private TextView descriptionTextView = null;
    private TextView sunriseTime = null;
    private TextView sunsetTime = null;
    private SwipeRefreshLayout swipeRefreshLayout = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        tempTextView = (TextView) getActivity().findViewById(R.id.temperature);
        speedTextView = (TextView) getActivity().findViewById(R.id.wind_speed_value);
        pressureTextView = (TextView) getActivity().findViewById(R.id.pressure_value);
        humidityTextView = (TextView) getActivity().findViewById(R.id.humidity_value);
        descriptionTextView = (TextView) getActivity().findViewById(R.id.weather);
        sunriseTime = (TextView) getActivity().findViewById(R.id.sunrise_time);
        sunsetTime = (TextView) getActivity().findViewById(R.id.sunset_time);
        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        if(!GlobalConstants.city.equals("null")) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(GlobalConstants.city);
        } else ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.Weather);

        if (GlobalConstants.temperature != 0) {
            tempTextView.setText(String.valueOf(GlobalConstants.temperature - 273 + 1));
        }
        if (GlobalConstants.speed != -1) {
            speedTextView.setText(String.valueOf(GlobalConstants.speed));
        }
        if (GlobalConstants.pressure != 0) {
            pressureTextView.setText(String.valueOf(GlobalConstants.pressure));
        }
        if (GlobalConstants.humidity != -1) {
            humidityTextView.setText(String.valueOf(GlobalConstants.humidity));
        }
        if (!GlobalConstants.description.equals("null")) {
            descriptionTextView.setText(GlobalConstants.description);
        }
        if (!GlobalConstants.sunriseTime.equals("null")) {
            sunriseTime.setText(GlobalConstants.sunriseTime);
        }
        if (!GlobalConstants.sunsetTime.equals("null")) {
            sunsetTime.setText(GlobalConstants.sunsetTime);
        }

        super.onViewCreated(view, savedInstanceState);
    }

    private String getTime(int unitTime, int timezone) {
        Date sunriseDate = new java.util.Date((long) unitTime * 1000);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String sunriseTime = sdf.format(sunriseDate);

        String[] sunriseStrings = sunriseTime.split(" ");

        String[] sunriseTimeStrings = sunriseStrings[1].split(":");
        int hours = Integer.parseInt(sunriseTimeStrings[0]);
        hours += timezone;
        hours %= 24;
        sunriseTimeStrings[0] = String.valueOf(hours);
        if (sunriseTimeStrings[0].length() == 1)
            sunriseTimeStrings[0] = "0" + sunriseTimeStrings[0];
        return sunriseTimeStrings[0] + ":" + sunriseTimeStrings[1];
    }

    @Override
    public void onRefresh() {
        String response = null;
        try {
            SendRequest sendRequest = new SendRequest();
            sendRequest.execute(GlobalConstants.city);
            response = sendRequest.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            JSONObject object = new JSONObject(Objects.requireNonNull(response));

            JSONObject tempObject = object.getJSONObject("main");
            int temp = tempObject.getInt("temp");
            GlobalConstants.temperature = temp;
            tempTextView.setText(String.valueOf(temp - 273 + 1));

            JSONObject speedObject = object.getJSONObject("wind");
            int speed = speedObject.getInt("speed");
            GlobalConstants.speed = speed;
            speedTextView.setText(String.valueOf(speed));

            JSONObject pressureObject = object.getJSONObject("main");
            int pressure = pressureObject.getInt("pressure");
            GlobalConstants.pressure = pressure;
            pressureTextView.setText(String.valueOf(pressure));

            JSONObject humidityObject = object.getJSONObject("main");
            int humidity = humidityObject.getInt("humidity");
            GlobalConstants.humidity = humidity;
            humidityTextView.setText(String.valueOf(humidity));

            JSONArray weatherArray = object.getJSONArray("weather");
            JSONObject weatherObject = weatherArray.getJSONObject(0);
            String description = weatherObject.getString("main");
            GlobalConstants.description = description;
            descriptionTextView.setText(description);

            JSONObject timeObject = object.getJSONObject("sys");
            int unixSunriseTime = timeObject.getInt("sunrise");
            int unixSunsetTime = timeObject.getInt("sunset");

            int timezone = object.getInt("timezone");
            timezone /= 3600;
            sunriseTime.setText(getTime(unixSunriseTime, timezone));
            GlobalConstants.sunriseTime = getTime(unixSunriseTime, timezone);
            sunsetTime.setText(getTime(unixSunsetTime, timezone));
            GlobalConstants.sunsetTime = getTime(unixSunsetTime, timezone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        swipeRefreshLayout.setRefreshing(false);
    }
}