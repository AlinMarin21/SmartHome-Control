package com.example.home;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class AirConditionerActivity extends AppCompat {

    static final int AC_OFF = 0;
    static final int AC_ON = 1;

    static final int AUTO_AC_ON = 1;
    static final int AUTO_AC_OFF = 0;

    ImageView FanIcon;
    Switch AutoACSwitch;
    SeekBar ACSpeedBar;

    int ac_state = AC_OFF;
    int auto_ac_state = AUTO_AC_OFF;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airconditioner);

        Window window = AirConditionerActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(AirConditionerActivity.this, R.color.red_background));
        window.setNavigationBarColor(ContextCompat.getColor(AirConditionerActivity.this, R.color.red_navigation_background));

        FanIcon = (ImageView) findViewById(R.id.fan_icon);
        AutoACSwitch = (Switch) findViewById(R.id.auto_ac_switch);
        ACSpeedBar = (SeekBar) findViewById(R.id.ac_speed_bar);

        if(BufferManager.currentLanguage.equals("fr")) {
            AutoACSwitch.setTextSize(16);
        }
        if(BufferManager.currentLanguage.equals("de")) {
            AutoACSwitch.setTextSize(14);
        }
        if(BufferManager.currentLanguage.equals("ru")) {
            AutoACSwitch.setTextSize(11);
        }

        ACSpeedBar.setVisibility(View.INVISIBLE);

        ACSpeedBar.setMax(255);

        FanIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AUTO_AC_ON != auto_ac_state) {
                    if (AC_OFF == ac_state) {
                        FanIcon.setImageResource(R.drawable.baseline_wind_power_200_open);
                        ac_state = AC_ON;
                        ACSpeedBar.setVisibility(View.VISIBLE);
                    } else if (AC_ON == ac_state) {
                        FanIcon.setImageResource(R.drawable.baseline_wind_power_200);
                        ac_state = AC_OFF;
                        ACSpeedBar.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        AutoACSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AUTO_AC_OFF == auto_ac_state) {
                    auto_ac_state = AUTO_AC_ON;
                    AutoACSwitch.setChecked(true);
                    AutoACSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(AirConditionerActivity.this, R.color.red_navigation_background)));

                    FanIcon.setImageResource(R.drawable.baseline_wind_power_200);
                    ac_state = AC_OFF;
                    ACSpeedBar.setVisibility(View.INVISIBLE);
                }
                else {
                    auto_ac_state = AUTO_AC_OFF;
                    AutoACSwitch.setChecked(false);
                    AutoACSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(AirConditionerActivity.this, R.color.gray_navigation_background)));
                }
            }
        });
    }
}
