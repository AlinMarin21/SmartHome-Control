package com.example.home;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class TerraceActivity extends AppCompat {

    private static final int LIGHT_ON = 1;
    private static final int LIGHT_OFF = 0;

    private static final int MANUAL_CONTROL = 0;
    private static final int MOTION_CONTROL = 1;
    private static final int DARKNESS_CONTROL = 2;

    private static final int MOTION_DETECTED = 1;
    private static final int NO_MOTION = 0;

    private Switch motionSwitch = null;
    private Switch brightnessSwitch = null;
    private Switch manualSwitch = null;

    private int led_state = LIGHT_OFF;
    private int control_mode = MANUAL_CONTROL;
    private ImageView terraceBulb = null;

    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terrace);

        Window window = TerraceActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(TerraceActivity.this, R.color.green_background));
        window.setNavigationBarColor(ContextCompat.getColor(TerraceActivity.this, R.color.green_navigation_background));

        motionSwitch = (Switch) findViewById(R.id.motion_switch);
        brightnessSwitch = (Switch) findViewById(R.id.brightness_switch);
        manualSwitch = (Switch) findViewById(R.id.manual_switch);

        terraceBulb = (ImageView) findViewById(R.id.bulb_icon_terrace);

        led_state = BufferManager.TxBuffer[13];
        control_mode = BufferManager.TxBuffer[14];

        mHandler = new Handler();
        startRepeatingTask();

        if(MANUAL_CONTROL == control_mode) {
            manualSwitch.setChecked(true);
            manualSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(TerraceActivity.this, R.color.green_navigation_background)));

            if(LIGHT_OFF == led_state) {
                terraceBulb.setImageResource(R.drawable.baseline_lightbulb_200_black);
            }
            else if(LIGHT_ON == led_state) {
                terraceBulb.setImageResource(R.drawable.baseline_lightbulb_200_open);
            }
        }
        else if(MOTION_CONTROL == control_mode) {
            motionSwitch.setChecked(true);
            motionSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(TerraceActivity.this, R.color.green_navigation_background)));

            if(MOTION_DETECTED == BufferManager.RxBuffer[1]) {
                terraceBulb.setImageResource(R.drawable.baseline_lightbulb_200_open);
            }
            else {
                terraceBulb.setImageResource(R.drawable.baseline_lightbulb_200_black);
            }
        }
        else if(DARKNESS_CONTROL == control_mode) {
            brightnessSwitch.setChecked(true);
            brightnessSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(TerraceActivity.this, R.color.green_navigation_background)));

            if(BufferManager.TxBuffer[27] > BufferManager.RxBuffer[2]) {
                terraceBulb.setImageResource(R.drawable.baseline_lightbulb_200_open);
            }
            else {
                terraceBulb.setImageResource(R.drawable.baseline_lightbulb_200_black);
            }
        }

        manualSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MANUAL_CONTROL != control_mode) {
                    manualSwitch.setChecked(true);
                    manualSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(TerraceActivity.this, R.color.green_navigation_background)));
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
                BufferManager.TxBuffer[13] = (byte) led_state;
                BufferManager.TxBuffer[14] = (byte) control_mode;
            }
        });

        motionSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MOTION_CONTROL != control_mode) {
                    manualSwitch.setChecked(false);
                    manualSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(TerraceActivity.this, R.color.gray_navigation_background)));
                    motionSwitch.setChecked(true);
                    motionSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(TerraceActivity.this, R.color.green_navigation_background)));
                    brightnessSwitch.setChecked(false);
                    brightnessSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(TerraceActivity.this, R.color.gray_navigation_background)));

                    control_mode = MOTION_CONTROL;

                    led_state = LIGHT_OFF;
                }
                else {
                    motionSwitch.setChecked(true);
                    control_mode = MOTION_CONTROL;
                }
                BufferManager.TxBuffer[13] = (byte) led_state;
                BufferManager.TxBuffer[14] = (byte) control_mode;

                if(MOTION_DETECTED == BufferManager.RxBuffer[1]) {
                    terraceBulb.setImageResource(R.drawable.baseline_lightbulb_200_open);
                }
                else {
                    terraceBulb.setImageResource(R.drawable.baseline_lightbulb_200_black);
                }
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
                    brightnessSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(TerraceActivity.this, R.color.green_navigation_background)));

                    control_mode = DARKNESS_CONTROL;

                    led_state = LIGHT_OFF;
                }
                else {
                    brightnessSwitch.setChecked(true);
                    control_mode = DARKNESS_CONTROL;
                }
                BufferManager.TxBuffer[13] = (byte) led_state;
                BufferManager.TxBuffer[14] = (byte) control_mode;

                if(BufferManager.TxBuffer[27] > BufferManager.RxBuffer[2]) {
                    terraceBulb.setImageResource(R.drawable.baseline_lightbulb_200_open);
                }
                else {
                    terraceBulb.setImageResource(R.drawable.baseline_lightbulb_200_black);
                }
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
                    BufferManager.TxBuffer[13] = (byte) led_state;
                }
                else {
                    //do nothing
                }
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                if(MOTION_CONTROL == control_mode) {
                    if(MOTION_DETECTED == BufferManager.RxBuffer[1]) {
                        terraceBulb.setImageResource(R.drawable.baseline_lightbulb_200_open);
                    }
                    else {
                        terraceBulb.setImageResource(R.drawable.baseline_lightbulb_200_black);
                    }
                }
                if(DARKNESS_CONTROL == control_mode) {
                    if(BufferManager.RxBuffer[2] < BufferManager.TxBuffer[27]) {
                        terraceBulb.setImageResource(R.drawable.baseline_lightbulb_200_open);
                    }
                    else {
                        terraceBulb.setImageResource(R.drawable.baseline_lightbulb_200_black);
                    }
                }
            } finally {
                mHandler.postDelayed(mStatusChecker, 250);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }
}
