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

import kotlin.jvm.internal.Ref;

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
            if (!Locale.getDefault().getLanguage().equals("en")) {
                setLocal(getActivity(), "en");
                ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.english_language);
                enTextView.setTextColor(Color.parseColor("#017DF1"));
                ruTextView.setTextColor(Color.parseColor("#FFFFFF"));
                uaTextView.setTextColor(Color.parseColor("#FFFFFF"));
                MainActivity.navigationView.getMenu().getItem(0).setTitle("Weather");
                MainActivity.navigationView.getMenu().getItem(1).setTitle("Location");
                MainActivity.navigationView.getMenu().getItem(2).setTitle("Theme");
                MainActivity.navigationView.getMenu().getItem(3).setTitle("Language");
            }
        });
        ruTextView.setOnClickListener(v -> {
            if (!Locale.getDefault().getLanguage().equals("ru")) {
                setLocal(getActivity(), "ru");
                ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.russian_language);
                enTextView.setTextColor(Color.parseColor("#FFFFFF"));
                ruTextView.setTextColor(Color.parseColor("#017DF1"));
                uaTextView.setTextColor(Color.parseColor("#FFFFFF"));
                MainActivity.navigationView.getMenu().getItem(0).setTitle("Погода");
                MainActivity.navigationView.getMenu().getItem(1).setTitle("Местоположение");
                MainActivity.navigationView.getMenu().getItem(2).setTitle("Тема");
                MainActivity.navigationView.getMenu().getItem(3).setTitle("Язык");
            }
        });
        uaTextView.setOnClickListener(v -> {
            if (!Locale.getDefault().getLanguage().equals("ua")) {
                setLocal(getActivity(), "ua");
                ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.ukrainian_language);
                enTextView.setTextColor(Color.parseColor("#FFFFFF"));
                ruTextView.setTextColor(Color.parseColor("#FFFFFF"));
                uaTextView.setTextColor(Color.parseColor("#017DF1"));
                MainActivity.navigationView.getMenu().getItem(0).setTitle("Погода");
                MainActivity.navigationView.getMenu().getItem(1).setTitle("Місцеположення");
                MainActivity.navigationView.getMenu().getItem(2).setTitle("Тема");
                MainActivity.navigationView.getMenu().getItem(3).setTitle("Мова");
            }
        });

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.Language);
        super.onViewCreated(view, savedInstanceState);
    }

    public void setLocal(Activity activity, String langCode) {
        Locale locale = new Locale(langCode);
        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        Locale.setDefault(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
