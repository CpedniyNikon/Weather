package com.example.weather;


import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LocationFragment extends Fragment {
    private Button button = null;
    private EditText editText = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.location_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        button = (Button) getActivity().findViewById(R.id.updateCityButton);
        editText = (EditText) getActivity().findViewById(R.id.locationEditText);
        button.setOnClickListener(v -> {
            GlobalConstants.city = editText.getText().toString();
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(GlobalConstants.city);
            Log.d("WTF", GlobalConstants.city);

        });
        super.onViewCreated(view, savedInstanceState);
    }
}
