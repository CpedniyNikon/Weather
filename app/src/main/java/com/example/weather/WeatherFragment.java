package com.example.weather;


import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class WeatherFragment extends Fragment {
    private Button button = null;
    private TextView tempTextView = null;
    private TextView speedTextView = null;
    private TextView pressureTextView = null;
    private TextView humidityTextView = null;
    private TextView descriptionTextView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        button = (Button) getActivity().findViewById(R.id.updateWeatherButton);
        tempTextView = (TextView) getActivity().findViewById(R.id.temperature);
        speedTextView = (TextView) getActivity().findViewById(R.id.wind_speed_value);
        pressureTextView = (TextView) getActivity().findViewById(R.id.pressure_value);
        humidityTextView = (TextView) getActivity().findViewById(R.id.humidity_value);
        descriptionTextView = (TextView) getActivity().findViewById(R.id.weather);
        button.setOnClickListener(v -> {
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
                Log.d("WTF", description);
                GlobalConstants.description = description;
                descriptionTextView.setText(description);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

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

        super.onViewCreated(view, savedInstanceState);
    }
}

