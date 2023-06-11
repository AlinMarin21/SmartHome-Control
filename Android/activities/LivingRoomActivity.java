package com.example.home;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class LivingRoomActivity extends AppCompat {

    private static final int LIGHT_ON = 1;
    private static final int LIGHT_OFF = 0;

    private ImageView LivingRoomBulb;
    private SeekBar LightIntensityBar;

    private int light_state = LIGHT_OFF;
    private int light_intensity = 5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livingroom);

        Window window = LivingRoomActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(LivingRoomActivity.this, R.color.green_background));
        window.setNavigationBarColor(ContextCompat.getColor(LivingRoomActivity.this, R.color.green_navigation_background));

        LivingRoomBulb = (ImageView) findViewById(R.id.bulb_icon);
        LightIntensityBar = (SeekBar) findViewById(R.id.light_intensity_bar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LightIntensityBar.setMin(5);
        }
        LightIntensityBar.setMax(254);

        light_state = BufferManager.TxBuffer[1];
        light_intensity = BufferManager.TxBuffer[2] & 0xFF;

        LightIntensityBar.setProgress(light_intensity);

        if(LIGHT_OFF == light_state) {
            LightIntensityBar.setVisibility(View.INVISIBLE);
            LivingRoomBulb.setImageResource(R.drawable.baseline_lightbulb_300_black);
        }
        else if(LIGHT_ON == light_state) {
            LightIntensityBar.setVisibility(View.VISIBLE);
            LivingRoomBulb.setImageResource(R.drawable.baseline_lightbulb_300_open);
        }

        LivingRoomBulb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LIGHT_OFF == light_state) {
                    LivingRoomBulb.setImageResource(R.drawable.baseline_lightbulb_300_open);
                    light_state = LIGHT_ON;
                    LightIntensityBar.setVisibility(View.VISIBLE);
                }
                else if(LIGHT_ON == light_state) {
                    LivingRoomBulb.setImageResource(R.drawable.baseline_lightbulb_300_black);
                    light_state = LIGHT_OFF;
                    LightIntensityBar.setVisibility(View.INVISIBLE);
                }
                BufferManager.TxBuffer[1] = (byte) light_state;
            }
        });

        LightIntensityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                light_intensity = i;
                BufferManager.TxBuffer[2] = (byte) light_intensity;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
