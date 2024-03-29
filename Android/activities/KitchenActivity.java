package com.example.home;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class KitchenActivity extends AppCompat {

    private static final int LIGHT_ON = 1;
    private static final int LIGHT_OFF = 0;

    private ImageView KitchenBulb1;
    private ImageView KitchenBulb2;

    private int light1_state = LIGHT_OFF;
    private int light2_state = LIGHT_OFF;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        Window window = KitchenActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(KitchenActivity.this, R.color.green_background));
        window.setNavigationBarColor(ContextCompat.getColor(KitchenActivity.this, R.color.green_navigation_background));

        KitchenBulb1 = (ImageView) findViewById(R.id.bulb_icon1_kitchen);
        KitchenBulb2 = (ImageView) findViewById(R.id.bulb_icon2_kitchen);

        light1_state = BufferManager.TxBuffer[3];
        light2_state = BufferManager.TxBuffer[4];

        if(LIGHT_OFF == light1_state) {
            KitchenBulb1.setImageResource(R.drawable.baseline_lightbulb_200_black);
        }
        else if(LIGHT_ON == light1_state) {
            KitchenBulb1.setImageResource(R.drawable.baseline_lightbulb_200_open);
        }

        if(LIGHT_OFF == light2_state) {
            KitchenBulb2.setImageResource(R.drawable.baseline_lightbulb_200_black);
        }
        else if(LIGHT_ON == light2_state) {
            KitchenBulb2.setImageResource(R.drawable.baseline_lightbulb_200_open);
        }

        KitchenBulb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LIGHT_OFF == light1_state) {
                    KitchenBulb1.setImageResource(R.drawable.baseline_lightbulb_200_open);
                    light1_state = LIGHT_ON;
                }
                else if(LIGHT_ON == light1_state) {
                    KitchenBulb1.setImageResource(R.drawable.baseline_lightbulb_200_black);
                    light1_state = LIGHT_OFF;
                }
                BufferManager.TxBuffer[3] = (byte) light1_state;
            }
        });

        KitchenBulb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LIGHT_OFF == light2_state) {
                    KitchenBulb2.setImageResource(R.drawable.baseline_lightbulb_200_open);
                    light2_state = LIGHT_ON;
                }
                else if(LIGHT_ON == light2_state) {
                    KitchenBulb2.setImageResource(R.drawable.baseline_lightbulb_200_black);
                    light2_state = LIGHT_OFF;
                }
                BufferManager.TxBuffer[4] = (byte) light2_state;
            }
        });
    }
}
