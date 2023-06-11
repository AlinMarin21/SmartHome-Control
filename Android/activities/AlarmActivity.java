package com.example.home;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class AlarmActivity extends AppCompat {

    private static final int SWITCH_ON = 1;
    private static final int SWITCH_OFF = 0;
    private Switch AirQualityAlarmSwitch;
    private Switch GasAlarmSwitch;
    private Switch HumiditySwitch;
    private Switch MotionSwitch;

    private int air_quality_switch_state = SWITCH_OFF;
    private int gas_switch_state = SWITCH_OFF;
    private int humidity_switch_state = SWITCH_OFF;
    private int motion_switch_state = SWITCH_OFF;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Window window = AlarmActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(AlarmActivity.this, R.color.purple_background));
        window.setNavigationBarColor(ContextCompat.getColor(AlarmActivity.this, R.color.purple_navigation_background));

        AirQualityAlarmSwitch = (Switch) findViewById(R.id.aq_alarm_switch);
        GasAlarmSwitch = (Switch) findViewById(R.id.gas_alarm_switch);
        HumiditySwitch = (Switch) findViewById(R.id.humidity_alarm_switch);
        MotionSwitch = (Switch) findViewById(R.id.motion_alarm_switch);

        air_quality_switch_state = BufferManager.TxBuffer[31];
        gas_switch_state = BufferManager.TxBuffer[32];
        humidity_switch_state = BufferManager.TxBuffer[33];
        motion_switch_state = BufferManager.TxBuffer[34];

        if(SWITCH_ON == air_quality_switch_state) {
            AirQualityAlarmSwitch.setChecked(true);
            AirQualityAlarmSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(AlarmActivity.this, R.color.purple_navigation_background)));
        }
        else {
            AirQualityAlarmSwitch.setChecked(false);
            AirQualityAlarmSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(AlarmActivity.this, R.color.gray_navigation_background)));
        }

        if(SWITCH_ON == gas_switch_state) {
            GasAlarmSwitch.setChecked(true);
            GasAlarmSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(AlarmActivity.this, R.color.purple_navigation_background)));
        }
        else {
            GasAlarmSwitch.setChecked(false);
            GasAlarmSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(AlarmActivity.this, R.color.gray_navigation_background)));
        }

        if(SWITCH_ON == humidity_switch_state) {
            HumiditySwitch.setChecked(true);
            HumiditySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(AlarmActivity.this, R.color.purple_navigation_background)));
        }
        else {
            HumiditySwitch.setChecked(false);
            HumiditySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(AlarmActivity.this, R.color.gray_navigation_background)));
        }

        if(SWITCH_ON == motion_switch_state) {
            MotionSwitch.setChecked(true);
            MotionSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(AlarmActivity.this, R.color.purple_navigation_background)));
        }
        else {
            MotionSwitch.setChecked(false);
            MotionSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(AlarmActivity.this, R.color.gray_navigation_background)));
        }

        AirQualityAlarmSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SWITCH_OFF == air_quality_switch_state) {
                    AirQualityAlarmSwitch.setChecked(true);
                    AirQualityAlarmSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(AlarmActivity.this, R.color.purple_navigation_background)));
                    air_quality_switch_state = SWITCH_ON;
                }
                else {
                    AirQualityAlarmSwitch.setChecked(false);
                    AirQualityAlarmSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(AlarmActivity.this, R.color.gray_navigation_background)));
                    air_quality_switch_state = SWITCH_OFF;
                }
                BufferManager.TxBuffer[31] = (byte) air_quality_switch_state;
            }
        });

        HumiditySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SWITCH_OFF == humidity_switch_state) {
                    HumiditySwitch.setChecked(true);
                    HumiditySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(AlarmActivity.this, R.color.purple_navigation_background)));
                    humidity_switch_state = SWITCH_ON;
                }
                else {
                    HumiditySwitch.setChecked(false);
                    HumiditySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(AlarmActivity.this, R.color.gray_navigation_background)));
                    humidity_switch_state = SWITCH_OFF;
                }
                BufferManager.TxBuffer[33] = (byte) humidity_switch_state;
            }
        });

        GasAlarmSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SWITCH_OFF == gas_switch_state) {
                    GasAlarmSwitch.setChecked(true);
                    GasAlarmSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(AlarmActivity.this, R.color.purple_navigation_background)));
                    gas_switch_state = SWITCH_ON;
                }
                else {
                    GasAlarmSwitch.setChecked(false);
                    GasAlarmSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(AlarmActivity.this, R.color.gray_navigation_background)));
                    gas_switch_state = SWITCH_OFF;
                }
                BufferManager.TxBuffer[32] = (byte) gas_switch_state;
            }
        });

        MotionSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SWITCH_OFF == motion_switch_state) {
                    MotionSwitch.setChecked(true);
                    MotionSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(AlarmActivity.this, R.color.purple_navigation_background)));
                    motion_switch_state = SWITCH_ON;
                }
                else {
                    MotionSwitch.setChecked(false);
                    MotionSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(AlarmActivity.this, R.color.gray_navigation_background)));
                    motion_switch_state = SWITCH_OFF;
                }
                BufferManager.TxBuffer[34] = (byte) motion_switch_state;
            }
        });
    }
}
