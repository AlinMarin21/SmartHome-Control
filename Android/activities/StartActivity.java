package com.example.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class StartActivity extends AppCompatActivity {

    AnimationDrawable connectivityAnimation = null;
    ImageView startPageConnectivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        startPageConnectivity = (ImageView) findViewById(R.id.startPageConnectivity);
        connectivityAnimation = (AnimationDrawable) startPageConnectivity.getDrawable();
        Window window = StartActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(StartActivity.this, R.color.gray_backround));
        window.setNavigationBarColor(ContextCompat.getColor(StartActivity.this, R.color.gray_navigation_backround));

        connectivityAnimation.start();

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                connectivityAnimation.stop();
            }
        }, 6000);


    }
}