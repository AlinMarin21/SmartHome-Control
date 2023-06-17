package com.example.home;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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

    private static final int AC_OFF = 0;
    private static final int AC_ON = 1;

    private static final int AUTO_AC_ON = 1;
    private static final int AUTO_AC_OFF = 0;

    private ImageView FanIcon;
    private Switch AutoACSwitch;
    private SeekBar ACSpeedBar;

    private int ac_state = AC_OFF;
    private int auto_ac_state = AUTO_AC_OFF;

    private int ac_speed = 150;

    private Handler mHandler;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ACSpeedBar.setMin(150);
        }
        ACSpeedBar.setMax(254);

        ac_state = BufferManager.TxBuffer[21];
        auto_ac_state = BufferManager.TxBuffer[22];
        ac_speed = BufferManager.TxBuffer[23] & 0xFF;

        mHandler = new Handler();
        startRepeatingTask();

        ACSpeedBar.setProgress(ac_speed);

        if(AC_ON == ac_state) {
            FanIcon.setImageResource(R.drawable.baseline_wind_power_200_open);
            ACSpeedBar.setVisibility(View.VISIBLE);
        }
        else {
            FanIcon.setImageResource(R.drawable.baseline_wind_power_200);
            ACSpeedBar.setVisibility(View.INVISIBLE);
        }

        if(AUTO_AC_ON == auto_ac_state) {
            AutoACSwitch.setChecked(true);
            AutoACSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(AirConditionerActivity.this, R.color.red_navigation_background)));
            if (BufferManager.RxBuffer[6] >= BufferManager.TxBuffer[26]) {
                FanIcon.setImageResource(R.drawable.baseline_wind_power_200_open);
            } else {
                FanIcon.setImageResource(R.drawable.baseline_wind_power_200);
            }
        }
        else {
            AutoACSwitch.setChecked(false);
            AutoACSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(AirConditionerActivity.this, R.color.gray_navigation_background)));
        }

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
                    BufferManager.TxBuffer[21] = (byte) ac_state;
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
                    FanIcon.setImageResource(R.drawable.baseline_wind_power_200);
                }
                BufferManager.TxBuffer[22] = (byte) auto_ac_state;
                BufferManager.TxBuffer[21] = (byte) ac_state;
            }
        });

        ACSpeedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ac_speed = i;
                BufferManager.TxBuffer[23] = (byte) ac_speed;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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
                if(AUTO_AC_ON == auto_ac_state) {
                    if (BufferManager.RxBuffer[6] >= BufferManager.TxBuffer[26]) {
                        FanIcon.setImageResource(R.drawable.baseline_wind_power_200_open);
                    } else {
                        FanIcon.setImageResource(R.drawable.baseline_wind_power_200);
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
