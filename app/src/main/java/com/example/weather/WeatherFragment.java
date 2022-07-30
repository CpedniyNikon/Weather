package com.example.weather;


import android.os.Bundle;
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
    private TextView textView = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        button = (Button) getActivity().findViewById(R.id.updateWeatherButton);
        textView = (TextView) getActivity().findViewById(R.id.temperature);
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
                JSONObject jsonObject = object.getJSONObject("main");
                int temp = jsonObject.getInt("temp");
                GlobalConstants.temperature = temp;
                Toast.makeText(getActivity(), "Температура: " + String.valueOf(temp - 273 + 1) + " по Цельсию", Toast.LENGTH_SHORT).show();
                textView.setText(String.valueOf(temp-273+1));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        if(GlobalConstants.temperature != 0) {
            textView.setText(String.valueOf(GlobalConstants.temperature-273+1));
        }

    }
}
