package com.example.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class SettingsActivity extends AppCompatActivity {

    LinearLayout ThresholdsActivityButton;
    LinearLayout UnitsActivityButton;
    LinearLayout AlarmActivityButton;
    LinearLayout CustomDisplayActivityButton;
    LinearLayout LanguageActivityButton;

    Intent ThresholdActivityIntent;
    Intent UnitsActivityIntent;
    Intent AlarmActivityIntent;
    Intent CustomDisplayActivityIntent;
    Intent LanguageActivityIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Window window = SettingsActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(SettingsActivity.this, R.color.purple_background));
        window.setNavigationBarColor(ContextCompat.getColor(SettingsActivity.this, R.color.purple_navigation_background));

        ThresholdsActivityButton = (LinearLayout) findViewById(R.id.Limits_button);
        UnitsActivityButton = (LinearLayout) findViewById(R.id.measurement_units_button);
        AlarmActivityButton = (LinearLayout) findViewById(R.id.alarm_button);
        CustomDisplayActivityButton = (LinearLayout) findViewById(R.id.custom_display_button);
        LanguageActivityButton = (LinearLayout) findViewById(R.id.language_button);

        ThresholdsActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThresholdActivityIntent = new Intent(SettingsActivity.this, ThresholdsActivity.class);
                startActivity(ThresholdActivityIntent);
            }
        });

        UnitsActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UnitsActivityIntent = new Intent(SettingsActivity.this, MeasurementUnitsActivity.class);
                startActivity(UnitsActivityIntent);
            }
        });

        AlarmActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmActivityIntent = new Intent(SettingsActivity.this, AlarmActivity.class);
                startActivity(AlarmActivityIntent);
            }
        });

        CustomDisplayActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDisplayActivityIntent = new Intent(SettingsActivity.this, CustomDisplayActivity.class);
                startActivity(CustomDisplayActivityIntent);
            }
        });

        LanguageActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LanguageActivityIntent = new Intent(SettingsActivity.this, LanguageActivity.class);
                startActivity(LanguageActivityIntent);
            }
        });
    }
}
