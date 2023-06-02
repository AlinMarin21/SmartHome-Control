package com.example.home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AppCompat extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageManager languageManager = new LanguageManager(this);
        BufferManager.currentLanguage = languageManager.getLanguage();
        languageManager.updateResource(languageManager.getLanguage());

    }
}
