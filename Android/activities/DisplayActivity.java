package com.example.home;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class DisplayActivity extends AppCompatActivity {

    static final int DISPLAY_ON = 1;
    static final int DISPLAY_OFF = 0;

    static final int TEMPERATURE_INSIDE_DISPLAY = 0;
    static final int TEMPERATURE_OUTSIDE_DISPLAY = 1;
    static final int HUMIDITY_INSIDE_DISPLAY = 2;
    static final int HUMIDITY_OUTSIDE_DISPLAY = 3;
    static final int TEMPERATURE_HUMIDITY_INSIDE_DISPLAY = 4;
    static final int TEMPERATURE_HUMIDITY_OUTSIDE_DISPLAY = 5;
    static final int AIR_QUALITY_DISPLAY = 6;
    static final int GAS_DISPLAY = 7;
    static final int RAINFALL_DISPLAY = 8;
    static final int CUSTOM_DISPLAY = 9;

    TextView DisplayOnButton;
    TextView DisplayOffButton;

    Switch TemperatureInsideSwitch;
    Switch TemperatureIOutsideSwitch;
    Switch HumidityInsideSwitch;
    Switch HumidityOutsideSwitch;
    Switch TemperatureAndHumidityInsideSwitch;
    Switch TemperatureAndHumidityOutsideSwitch;
    Switch AirQualitySwitch;
    Switch GasSwitch;
    Switch RainfallSwitch;
    Switch CustomSwitch;

    int display_state = DISPLAY_OFF;
    int display_mode = TEMPERATURE_INSIDE_DISPLAY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Window window = DisplayActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(DisplayActivity.this, R.color.green_background));
        window.setNavigationBarColor(ContextCompat.getColor(DisplayActivity.this, R.color.green_navigation_background));

        DisplayOnButton = (TextView) findViewById(R.id.display_on_textview);
        DisplayOffButton = (TextView) findViewById(R.id.display_off_textview);

        TemperatureInsideSwitch = (Switch) findViewById(R.id.temp_inside_switch);
        TemperatureIOutsideSwitch = (Switch) findViewById(R.id.temp_outside_switch);
        HumidityInsideSwitch = (Switch) findViewById(R.id.hum_inside_switch);
        HumidityOutsideSwitch = (Switch) findViewById(R.id.hum_outside_switch);
        TemperatureAndHumidityInsideSwitch = (Switch) findViewById(R.id.temp_hum_inside_switch);
        TemperatureAndHumidityOutsideSwitch = (Switch) findViewById(R.id.temp_hum_outside_switch);
        AirQualitySwitch = (Switch) findViewById(R.id.air_quality_switch);
        GasSwitch = (Switch) findViewById(R.id.gas_switch);
        RainfallSwitch = (Switch) findViewById(R.id.rainfall_switch);
        CustomSwitch = (Switch) findViewById(R.id.custom_switch);

        getActiveDisplayMode();

        DisplayOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayOnButton.setBackgroundColor(ContextCompat.getColor(DisplayActivity.this, R.color.green_navigation_background));
                DisplayOffButton.setBackgroundColor(ContextCompat.getColor(DisplayActivity.this, R.color.green_fade_background));

                DisplayOnButton.setTextColor(ContextCompat.getColor(DisplayActivity.this, R.color.white));
                DisplayOffButton.setTextColor(ContextCompat.getColor(DisplayActivity.this, R.color.black));

                display_state = DISPLAY_ON;

            }
        });

        DisplayOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayOnButton.setBackgroundColor(ContextCompat.getColor(DisplayActivity.this, R.color.green_fade_background));
                DisplayOffButton.setBackgroundColor(ContextCompat.getColor(DisplayActivity.this, R.color.green_navigation_background));

                DisplayOnButton.setTextColor(ContextCompat.getColor(DisplayActivity.this, R.color.black));
                DisplayOffButton.setTextColor(ContextCompat.getColor(DisplayActivity.this, R.color.white));

                display_state = DISPLAY_OFF;

            }
        });

        TemperatureInsideSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TEMPERATURE_INSIDE_DISPLAY != display_mode) {
                    TemperatureInsideSwitch.setChecked(true);
                    TemperatureInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.green_navigation_background)));
                    TemperatureIOutsideSwitch.setChecked(false);
                    TemperatureIOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    HumidityInsideSwitch.setChecked(false);
                    HumidityInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    HumidityOutsideSwitch.setChecked(false);
                    HumidityOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureAndHumidityInsideSwitch.setChecked(false);
                    TemperatureAndHumidityInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureAndHumidityOutsideSwitch.setChecked(false);
                    TemperatureAndHumidityOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    AirQualitySwitch.setChecked(false);
                    AirQualitySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    GasSwitch.setChecked(false);
                    GasSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    RainfallSwitch.setChecked(false);
                    RainfallSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    CustomSwitch.setChecked(false);
                    CustomSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));

                    display_mode = TEMPERATURE_INSIDE_DISPLAY;
                }
                else {
                    TemperatureInsideSwitch.setChecked(true);
                    display_mode = TEMPERATURE_INSIDE_DISPLAY;
                }
            }
        });

        TemperatureIOutsideSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TEMPERATURE_OUTSIDE_DISPLAY != display_mode) {
                    TemperatureInsideSwitch.setChecked(false);
                    TemperatureInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureIOutsideSwitch.setChecked(true);
                    TemperatureIOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.green_navigation_background)));
                    HumidityInsideSwitch.setChecked(false);
                    HumidityInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    HumidityOutsideSwitch.setChecked(false);
                    HumidityOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureAndHumidityInsideSwitch.setChecked(false);
                    TemperatureAndHumidityInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureAndHumidityOutsideSwitch.setChecked(false);
                    TemperatureAndHumidityOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    AirQualitySwitch.setChecked(false);
                    AirQualitySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    GasSwitch.setChecked(false);
                    GasSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    RainfallSwitch.setChecked(false);
                    RainfallSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    CustomSwitch.setChecked(false);
                    CustomSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));

                    display_mode = TEMPERATURE_OUTSIDE_DISPLAY;
                }
                else {
                    TemperatureIOutsideSwitch.setChecked(true);
                    display_mode = TEMPERATURE_OUTSIDE_DISPLAY;
                }
            }
        });

        HumidityInsideSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(HUMIDITY_INSIDE_DISPLAY != display_mode) {
                    TemperatureInsideSwitch.setChecked(false);
                    TemperatureInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureIOutsideSwitch.setChecked(false);
                    TemperatureIOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    HumidityInsideSwitch.setChecked(true);
                    HumidityInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.green_navigation_background)));
                    HumidityOutsideSwitch.setChecked(false);
                    HumidityOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureAndHumidityInsideSwitch.setChecked(false);
                    TemperatureAndHumidityInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureAndHumidityOutsideSwitch.setChecked(false);
                    TemperatureAndHumidityOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    AirQualitySwitch.setChecked(false);
                    AirQualitySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    GasSwitch.setChecked(false);
                    GasSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    RainfallSwitch.setChecked(false);
                    RainfallSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    CustomSwitch.setChecked(false);
                    CustomSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));

                    display_mode = HUMIDITY_INSIDE_DISPLAY;
                }
                else {
                    HumidityInsideSwitch.setChecked(true);
                    display_mode = HUMIDITY_INSIDE_DISPLAY;
                }
            }
        });

        HumidityOutsideSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(HUMIDITY_OUTSIDE_DISPLAY != display_mode) {
                    TemperatureInsideSwitch.setChecked(false);
                    TemperatureInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureIOutsideSwitch.setChecked(false);
                    TemperatureIOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    HumidityInsideSwitch.setChecked(false);
                    HumidityInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    HumidityOutsideSwitch.setChecked(true);
                    HumidityOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.green_navigation_background)));
                    TemperatureAndHumidityInsideSwitch.setChecked(false);
                    TemperatureAndHumidityInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureAndHumidityOutsideSwitch.setChecked(false);
                    TemperatureAndHumidityOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    AirQualitySwitch.setChecked(false);
                    AirQualitySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    GasSwitch.setChecked(false);
                    GasSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    RainfallSwitch.setChecked(false);
                    RainfallSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    CustomSwitch.setChecked(false);
                    CustomSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));

                    display_mode = HUMIDITY_OUTSIDE_DISPLAY;
                }
                else {
                    HumidityOutsideSwitch.setChecked(true);
                    display_mode = HUMIDITY_OUTSIDE_DISPLAY;
                }
            }
        });

        TemperatureAndHumidityInsideSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TEMPERATURE_HUMIDITY_INSIDE_DISPLAY != display_mode) {
                    TemperatureInsideSwitch.setChecked(false);
                    TemperatureInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureIOutsideSwitch.setChecked(false);
                    TemperatureIOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    HumidityInsideSwitch.setChecked(false);
                    HumidityInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    HumidityOutsideSwitch.setChecked(false);
                    HumidityOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureAndHumidityInsideSwitch.setChecked(true);
                    TemperatureAndHumidityInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.green_navigation_background)));
                    TemperatureAndHumidityOutsideSwitch.setChecked(false);
                    TemperatureAndHumidityOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    AirQualitySwitch.setChecked(false);
                    AirQualitySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    GasSwitch.setChecked(false);
                    GasSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    RainfallSwitch.setChecked(false);
                    RainfallSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    CustomSwitch.setChecked(false);
                    CustomSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));

                    display_mode = TEMPERATURE_HUMIDITY_INSIDE_DISPLAY;
                }
                else {
                    TemperatureAndHumidityInsideSwitch.setChecked(true);
                    display_mode = TEMPERATURE_HUMIDITY_INSIDE_DISPLAY;
                }
            }
        });

        TemperatureAndHumidityOutsideSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TEMPERATURE_HUMIDITY_OUTSIDE_DISPLAY != display_mode) {
                    TemperatureInsideSwitch.setChecked(false);
                    TemperatureInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureIOutsideSwitch.setChecked(false);
                    TemperatureIOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    HumidityInsideSwitch.setChecked(false);
                    HumidityInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    HumidityOutsideSwitch.setChecked(false);
                    HumidityOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureAndHumidityInsideSwitch.setChecked(false);
                    TemperatureAndHumidityInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureAndHumidityOutsideSwitch.setChecked(true);
                    TemperatureAndHumidityOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.green_navigation_background)));
                    AirQualitySwitch.setChecked(false);
                    AirQualitySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    GasSwitch.setChecked(false);
                    GasSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    RainfallSwitch.setChecked(false);
                    RainfallSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    CustomSwitch.setChecked(false);
                    CustomSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));

                    display_mode = TEMPERATURE_HUMIDITY_OUTSIDE_DISPLAY;
                }
                else {
                    TemperatureAndHumidityOutsideSwitch.setChecked(true);
                    display_mode = TEMPERATURE_HUMIDITY_OUTSIDE_DISPLAY;
                }
            }
        });

        AirQualitySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AIR_QUALITY_DISPLAY != display_mode) {
                    TemperatureInsideSwitch.setChecked(false);
                    TemperatureInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureIOutsideSwitch.setChecked(false);
                    TemperatureIOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    HumidityInsideSwitch.setChecked(false);
                    HumidityInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    HumidityOutsideSwitch.setChecked(false);
                    HumidityOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureAndHumidityInsideSwitch.setChecked(false);
                    TemperatureAndHumidityInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureAndHumidityOutsideSwitch.setChecked(false);
                    TemperatureAndHumidityOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    AirQualitySwitch.setChecked(true);
                    AirQualitySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.green_navigation_background)));
                    GasSwitch.setChecked(false);
                    GasSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    RainfallSwitch.setChecked(false);
                    RainfallSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    CustomSwitch.setChecked(false);
                    CustomSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));

                    display_mode = AIR_QUALITY_DISPLAY;
                }
                else {
                    AirQualitySwitch.setChecked(true);
                    display_mode = AIR_QUALITY_DISPLAY;
                }
            }
        });

        GasSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GAS_DISPLAY != display_mode) {
                    TemperatureInsideSwitch.setChecked(false);
                    TemperatureInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureIOutsideSwitch.setChecked(false);
                    TemperatureIOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    HumidityInsideSwitch.setChecked(false);
                    HumidityInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    HumidityOutsideSwitch.setChecked(false);
                    HumidityOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureAndHumidityInsideSwitch.setChecked(false);
                    TemperatureAndHumidityInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureAndHumidityOutsideSwitch.setChecked(false);
                    TemperatureAndHumidityOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    AirQualitySwitch.setChecked(false);
                    AirQualitySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    GasSwitch.setChecked(true);
                    GasSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.green_navigation_background)));
                    RainfallSwitch.setChecked(false);
                    RainfallSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    CustomSwitch.setChecked(false);
                    CustomSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));

                    display_mode = GAS_DISPLAY;
                }
                else {
                    GasSwitch.setChecked(true);
                    display_mode = GAS_DISPLAY;
                }
            }
        });

        RainfallSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RAINFALL_DISPLAY != display_mode) {
                    TemperatureInsideSwitch.setChecked(false);
                    TemperatureInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureIOutsideSwitch.setChecked(false);
                    TemperatureIOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    HumidityInsideSwitch.setChecked(false);
                    HumidityInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    HumidityOutsideSwitch.setChecked(false);
                    HumidityOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureAndHumidityInsideSwitch.setChecked(false);
                    TemperatureAndHumidityInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureAndHumidityOutsideSwitch.setChecked(false);
                    TemperatureAndHumidityOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    AirQualitySwitch.setChecked(false);
                    AirQualitySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    GasSwitch.setChecked(false);
                    GasSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    RainfallSwitch.setChecked(true);
                    RainfallSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.green_navigation_background)));
                    CustomSwitch.setChecked(false);
                    CustomSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));

                    display_mode = RAINFALL_DISPLAY;
                }
                else {
                    RainfallSwitch.setChecked(true);
                    display_mode = RAINFALL_DISPLAY;
                }
            }
        });

        CustomSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CUSTOM_DISPLAY != display_mode) {
                    TemperatureInsideSwitch.setChecked(false);
                    TemperatureInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureIOutsideSwitch.setChecked(false);
                    TemperatureIOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    HumidityInsideSwitch.setChecked(false);
                    HumidityInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    HumidityOutsideSwitch.setChecked(false);
                    HumidityOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureAndHumidityInsideSwitch.setChecked(false);
                    TemperatureAndHumidityInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    TemperatureAndHumidityOutsideSwitch.setChecked(false);
                    TemperatureAndHumidityOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    AirQualitySwitch.setChecked(false);
                    AirQualitySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    GasSwitch.setChecked(false);
                    GasSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    RainfallSwitch.setChecked(false);
                    RainfallSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.gray_navigation_background)));
                    CustomSwitch.setChecked(true);
                    CustomSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.green_navigation_background)));

                    display_mode = CUSTOM_DISPLAY;
                }
                else {
                    CustomSwitch.setChecked(true);
                    display_mode = CUSTOM_DISPLAY;
                }
            }
        });
    }

    void getActiveDisplayMode() {
        if(TEMPERATURE_INSIDE_DISPLAY == display_mode) {
            TemperatureInsideSwitch.setChecked(true);
            TemperatureInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.green_navigation_background)));
        }
        else if(TEMPERATURE_OUTSIDE_DISPLAY == display_mode) {
            TemperatureIOutsideSwitch.setChecked(true);
            TemperatureIOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.green_navigation_background)));
        }
        else if(HUMIDITY_INSIDE_DISPLAY == display_mode) {
            HumidityInsideSwitch.setChecked(true);
            HumidityInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.green_navigation_background)));
        }
        else if(HUMIDITY_OUTSIDE_DISPLAY == display_mode) {
            HumidityOutsideSwitch.setChecked(true);
            HumidityOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.green_navigation_background)));
        }
        else if(TEMPERATURE_HUMIDITY_INSIDE_DISPLAY == display_mode) {
            TemperatureAndHumidityInsideSwitch.setChecked(true);
            TemperatureAndHumidityInsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.green_navigation_background)));
        }
        else if(TEMPERATURE_HUMIDITY_OUTSIDE_DISPLAY == display_mode) {
            TemperatureAndHumidityOutsideSwitch.setChecked(true);
            TemperatureAndHumidityOutsideSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.green_navigation_background)));
        }
        else if(AIR_QUALITY_DISPLAY == display_mode) {
            AirQualitySwitch.setChecked(true);
            AirQualitySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.green_navigation_background)));
        }
        else if(GAS_DISPLAY == display_mode) {
            GasSwitch.setChecked(true);
            GasSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.green_navigation_background)));
        }
        else if(RAINFALL_DISPLAY == display_mode) {
            RainfallSwitch.setChecked(true);
            RainfallSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.green_navigation_background)));
        }
        else if(CUSTOM_DISPLAY == display_mode) {
            CustomSwitch.setChecked(true);
            CustomSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DisplayActivity.this, R.color.green_navigation_background)));
        }
    }
}
