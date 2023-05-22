package com.example.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class HomeMenuActivity extends AppCompatActivity {

    private long pressedTime;

    Button LightsActivityButton = null;
    Button DoorsActivityButton = null;
    Button AirConditionerActivityButton = null;
    Button HomeAmbientActivityButton = null;
    Button VoiceControlActivityButton = null;
    Button SettingsActivityButton = null;
    Button DisplayActivityButton = null;

    Intent lights_activity_intent = null;
    Intent doors_activity_intent = null;
    Intent air_conditioner_activity_intent = null;
    Intent home_ambient_activity_intent = null;
    Intent voice_control_activity_intent = null;
    Intent settings_activity_intent = null;
    Intent display_activity_intent = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homemenu);

        Window window = HomeMenuActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(HomeMenuActivity.this, R.color.beige_background));
        window.setNavigationBarColor(ContextCompat.getColor(HomeMenuActivity.this, R.color.beige_navigation_background));

        LightsActivityButton = (Button) findViewById(R.id.light_button);
        DoorsActivityButton = (Button) findViewById(R.id.doors_button);
        AirConditionerActivityButton = (Button) findViewById(R.id.ac_button);
        HomeAmbientActivityButton = (Button) findViewById(R.id.ambient_button);
        VoiceControlActivityButton = (Button) findViewById(R.id.voice_button);
        SettingsActivityButton = (Button) findViewById(R.id.settings_button);
        DisplayActivityButton = (Button) findViewById(R.id.display_button);

        LightsActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lights_activity_intent = new Intent(HomeMenuActivity.this, LightsActivity.class);
                startActivity(lights_activity_intent);
            }
        });

        DoorsActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doors_activity_intent = new Intent(HomeMenuActivity.this, DoorsActivity.class);
                startActivity(doors_activity_intent);
            }
        });

        AirConditionerActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                air_conditioner_activity_intent = new Intent(HomeMenuActivity.this, AirConditionerActivity.class);
                startActivity(air_conditioner_activity_intent);
            }
        });

        HomeAmbientActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home_ambient_activity_intent = new Intent(HomeMenuActivity.this, HomeAmbientActivity.class);
                startActivity(home_ambient_activity_intent);
            }
        });

        VoiceControlActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voice_control_activity_intent = new Intent(HomeMenuActivity.this, VoiceControlActivity.class);
                startActivity(voice_control_activity_intent);
            }
        });

        SettingsActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settings_activity_intent = new Intent(HomeMenuActivity.this, SettingsActivity.class);
                startActivity(settings_activity_intent);
            }
        });

        DisplayActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display_activity_intent = new Intent(HomeMenuActivity.this, DisplayActivity.class);
                startActivity(display_activity_intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}
