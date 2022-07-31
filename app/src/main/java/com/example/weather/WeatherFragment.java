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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d("WTF","SHITASFUCK");
        button = (Button) getActivity().findViewById(R.id.updateWeatherButton);
        tempTextView = (TextView) getActivity().findViewById(R.id.temperature);
        speedTextView = (TextView) getActivity().findViewById(R.id.wind_speed_value);
        pressureTextView = (TextView) getActivity().findViewById(R.id.pressure_value);
        humidityTextView = (TextView) getActivity().findViewById(R.id.humidity_value);
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
    }
}

//{
// "coord":{
// "lon":37.6156,"lat":55.7522
// },
// "weather":
// [{"id":801,"main":"Clouds","description":"небольшая облачность","icon":"02n"}],
// "base":"stations",
// "main":
// {
// "temp":288.17,
// "feels_like":287.19,
// "temp_min":285.25,
// "temp_max":289.39,
// "pressure":1019,
// "humidity":56,
// "sea_level":1019,
// "grnd_level":1001
// },
// "visibility":10000,"wind":
// {
// "speed":0.45,
// "deg":41,
// "gust":0.46
// },
// "clouds":
// {
// "all":23
// },
// "dt":1659219418,
// "sys":
// {
// "type":2,
// "id":2018597,
// "country":"RU",
// "sunrise":1659231144,
// "sunset":1659289159
// },
// "timezone":10800,
// "id":524901,
// "name":"Москва",
// "cod":200
// }