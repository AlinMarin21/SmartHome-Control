package com.example.home;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class TerraceActivity extends AppCompatActivity {

    static final int LIGHT_ON = 1;
    static final int LIGHT_OFF = 0;

    static final int MANUAL_CONTROL = 0;
    static final int MOTION_CONTROL = 1;
    static final int DARKNESS_CONTROL = 2;

    Switch motionSwitch = null;
    Switch brightnessSwitch = null;
    Switch manualSwitch = null;

    int led_state = LIGHT_OFF;
    int control_mode = MANUAL_CONTROL;
    ImageView terraceBulb = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terrace);

        Window window = TerraceActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(TerraceActivity.this, R.color.beige_background));
        window.setNavigationBarColor(ContextCompat.getColor(TerraceActivity.this, R.color.beige_navigation_background));

        motionSwitch = (Switch) findViewById(R.id.motion_switch);
        brightnessSwitch = (Switch) findViewById(R.id.brightness_switch);
        manualSwitch = (Switch) findViewById(R.id.manual_switch);

        terraceBulb = (ImageView) findViewById(R.id.bulb_icon_terrace);

        if(MANUAL_CONTROL == control_mode) {
            manualSwitch.setChecked(true);
            manualSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(TerraceActivity.this, R.color.beige_navigation_background)));

            if(LIGHT_OFF == led_state) {
                terraceBulb.setImageResource(R.drawable.baseline_lightbulb_200_black);
            }
            else if(LIGHT_ON == led_state) {
                terraceBulb.setImageResource(R.drawable.baseline_lightbulb_200_open);
            }
        }
        else if(MOTION_CONTROL == control_mode) {
            motionSwitch.setChecked(true);
            motionSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(TerraceActivity.this, R.color.beige_navigation_background)));

//            if(MOTION_DETECTED == GlobalBuffer.RxBuffer[1]) {
//                terraceBulb.setImageResource(R.drawable.bulb_on2);
//            }
//            else {
//                terraceBulb.setImageResource(R.drawable.bulb_off2);
//            }
        }
        else if(DARKNESS_CONTROL == control_mode) {
            brightnessSwitch.setChecked(true);
            brightnessSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(TerraceActivity.this, R.color.beige_navigation_background)));

//            if(DARKNESS_THRESHOLD > GlobalBuffer.RxBuffer[2]) {
//                terraceBulb.setImageResource(R.drawable.bulb_on2);
//            }
//            else {
//                terraceBulb.setImageResource(R.drawable.bulb_off2);
//            }
        }

        manualSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MANUAL_CONTROL != control_mode) {
                    manualSwitch.setChecked(true);
                    manualSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(TerraceActivity.this, R.color.beige_navigation_background)));
                    motionSwitch.setChecked(false);
                    motionSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(TerraceActivity.this, R.color.gray_navigation_background)));
                    brightnessSwitch.setChecked(false);
                    brightnessSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(TerraceActivity.this, R.color.gray_navigation_background)));

                    control_mode = MANUAL_CONTROL;

                    led_state = LIGHT_OFF;
                    terraceBulb.setImageResource(R.drawable.baseline_lightbulb_200_black);
                }
                else {
                    manualSwitch.setChecked(true);
                    control_mode = MANUAL_CONTROL;
                }
            }
        });

        motionSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MOTION_CONTROL != control_mode) {
                    manualSwitch.setChecked(false);
                    manualSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(TerraceActivity.this, R.color.gray_navigation_background)));
                    motionSwitch.setChecked(true);
                    motionSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(TerraceActivity.this, R.color.beige_navigation_background)));
                    brightnessSwitch.setChecked(false);
                    brightnessSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(TerraceActivity.this, R.color.gray_navigation_background)));

                    control_mode = MOTION_CONTROL;

                    led_state = LIGHT_OFF;
                }
                else {
                    motionSwitch.setChecked(true);
                    control_mode = MOTION_CONTROL;
                }

//                if(MOTION_DETECTED == GlobalBuffer.RxBuffer[1]) {
//                    terraceBulb.setImageResource(R.drawable.bulb_on2);
//                }
//                else {
//                    terraceBulb.setImageResource(R.drawable.bulb_off2);
//                }
            }
        });

        brightnessSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DARKNESS_CONTROL != control_mode) {
                    manualSwitch.setChecked(false);
                    manualSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(TerraceActivity.this, R.color.gray_navigation_background)));
                    motionSwitch.setChecked(false);
                    motionSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(TerraceActivity.this, R.color.gray_navigation_background)));
                    brightnessSwitch.setChecked(true);
                    brightnessSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(TerraceActivity.this, R.color.beige_navigation_background)));

                    control_mode = DARKNESS_CONTROL;

                    led_state = LIGHT_OFF;
                }
                else {
                    brightnessSwitch.setChecked(true);
                    control_mode = DARKNESS_CONTROL;
                }

//                if(DARKNESS_THRESHOLD > GlobalBuffer.RxBuffer[2]) {
//                    terraceBulb.setImageResource(R.drawable.bulb_on2);
//                }
//                else {
//                    terraceBulb.setImageResource(R.drawable.bulb_off2);
//                }
            }
        });

        terraceBulb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MANUAL_CONTROL == control_mode) {
                    if (LIGHT_OFF ==led_state) {
                        terraceBulb.setImageResource(R.drawable.baseline_lightbulb_200_open);
                        led_state = LIGHT_ON;
                    } else if (LIGHT_ON == led_state) {
                        terraceBulb.setImageResource(R.drawable.baseline_lightbulb_200_black);
                        led_state = LIGHT_OFF;
                    }
                }
                else {
                    //do nothing
                }
            }
        });

    }
}
