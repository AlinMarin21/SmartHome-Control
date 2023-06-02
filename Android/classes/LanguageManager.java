package com.example.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageManager {

    private Context ct;
    private SharedPreferences sharedPreferences;

    public LanguageManager(Context ctx) {
        ct = ctx;
        sharedPreferences = ct.getSharedPreferences("LANG", Context.MODE_PRIVATE);
    }

    public void updateResource(String code) {
        Locale locale = new Locale(code);
        Locale.setDefault(locale);
        Resources resources = ct.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        setLanguage(code);
    }

    public String getLanguage() {

        return sharedPreferences.getString("lang", "en");
    }

    public void setLanguage(String code) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lang", code);
        editor.commit();
    }
}
