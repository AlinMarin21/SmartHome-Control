package com.example.home;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class BathroomActivity extends AppCompat {

    static final int LIGHT_ON = 1;
    static final int LIGHT_OFF = 0;

    ImageView BathroomBulb;

    int light_state = LIGHT_OFF;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bathroom);

        Window window = BathroomActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(BathroomActivity.this, R.color.green_background));
        window.setNavigationBarColor(ContextCompat.getColor(BathroomActivity.this, R.color.green_navigation_background));

        BathroomBulb = (ImageView) findViewById(R.id.bulb_icon1_kitchen);

        if(LIGHT_OFF == light_state) {
            BathroomBulb.setImageResource(R.drawable.baseline_lightbulb_300_black);
        }
        else if(LIGHT_ON == light_state) {
            BathroomBulb.setImageResource(R.drawable.baseline_lightbulb_300_open);
        }

        BathroomBulb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LIGHT_OFF == light_state) {
                    BathroomBulb.setImageResource(R.drawable.baseline_lightbulb_300_open);
                    light_state = LIGHT_ON;
                }
                else if(LIGHT_ON == light_state) {
                    BathroomBulb.setImageResource(R.drawable.baseline_lightbulb_300_black);
                    light_state = LIGHT_OFF;
                }
            }
        });

    }
}
