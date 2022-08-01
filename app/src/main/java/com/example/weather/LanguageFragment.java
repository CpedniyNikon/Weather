package com.example.weather;


import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class LanguageFragment extends Fragment {
    private TextView enTextView = null;
    private TextView ruTextView = null;
    private TextView uaTextView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.language_theme, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        enTextView = (TextView) getActivity().findViewById(R.id.english_language);
        ruTextView = (TextView) getActivity().findViewById(R.id.russian_language);
        uaTextView = (TextView) getActivity().findViewById(R.id.ukrainian_language);
        if (Locale.getDefault().getLanguage().equals("en")) {
            enTextView.setTextColor(Color.parseColor("#017DF1"));
        }
        if (Locale.getDefault().getLanguage().equals("ru")) {
            ruTextView.setTextColor(Color.parseColor("#017DF1"));
        }
        if (Locale.getDefault().getLanguage().equals("ua")) {
            uaTextView.setTextColor(Color.parseColor("#017DF1"));
        }
        enTextView.setOnClickListener(v -> {
            setLocal(getActivity(), "en");
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.english_language);
            enTextView.setTextColor(Color.parseColor("#017DF1"));
            ruTextView.setTextColor(Color.parseColor("#FFFFFF"));
            uaTextView.setTextColor(Color.parseColor("#FFFFFF"));
        });
        ruTextView.setOnClickListener(v -> {
            setLocal(getActivity(), "ru");
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.russian_language);
            enTextView.setTextColor(Color.parseColor("#FFFFFF"));
            ruTextView.setTextColor(Color.parseColor("#017DF1"));
            uaTextView.setTextColor(Color.parseColor("#FFFFFF"));
        });
        uaTextView.setOnClickListener(v -> {
            setLocal(getActivity(), "ua");
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.ukrainian_language);
            enTextView.setTextColor(Color.parseColor("#FFFFFF"));
            ruTextView.setTextColor(Color.parseColor("#FFFFFF"));
            uaTextView.setTextColor(Color.parseColor("#017DF1"));
        });
        super.onViewCreated(view, savedInstanceState);
    }

    public void setLocal(Activity activity, String langCode) {
        Locale locale = new Locale(langCode);
        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
