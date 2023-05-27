package com.example.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class LightsActivity extends AppCompatActivity {

    Button LivingRoomActivityButton = null;
    Button KitchenActivityButton = null;
    Button Room1ActivityButton = null;
    Button Room2ActivityButton = null;
    Button BathroomActivityButton = null;
    Button TerraceActivityButton = null;

    Intent living_room_activity_intent = null;
    Intent kitchen_activity_intent = null;
    Intent room1_activity_intent = null;
    Intent room2_activity_intent = null;
    Intent bathroom_activity_intent = null;
    Intent terrace_activity_intent = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lights);

        Window window = LightsActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(LightsActivity.this, R.color.green_background));
        window.setNavigationBarColor(ContextCompat.getColor(LightsActivity.this, R.color.green_navigation_background));

        LivingRoomActivityButton = (Button) findViewById(R.id.living_room_button);
        KitchenActivityButton = (Button) findViewById(R.id.kitchen_button);
        Room1ActivityButton = (Button) findViewById(R.id.room1_button);
        Room2ActivityButton = (Button) findViewById(R.id.room2_button);
        BathroomActivityButton = (Button) findViewById(R.id.bathroom_button);
        TerraceActivityButton = (Button) findViewById(R.id.terrace_button);

        LivingRoomActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                living_room_activity_intent = new Intent(LightsActivity.this, LivingRoomActivity.class);
                startActivity(living_room_activity_intent);
            }
        });

        KitchenActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kitchen_activity_intent = new Intent(LightsActivity.this, KitchenActivity.class);
                startActivity(kitchen_activity_intent);
            }
        });

        Room1ActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                room1_activity_intent = new Intent(LightsActivity.this, Room1Activity.class);
                startActivity(room1_activity_intent);
            }
        });

        Room2ActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                room2_activity_intent = new Intent(LightsActivity.this, Room2Activity.class);
                startActivity(room2_activity_intent);
            }
        });

        BathroomActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bathroom_activity_intent = new Intent(LightsActivity.this, BathroomActivity.class);
                startActivity(bathroom_activity_intent);
            }
        });

        TerraceActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terrace_activity_intent = new Intent(LightsActivity.this, TerraceActivity.class);
                startActivity(terrace_activity_intent);
            }
        });
    }
}
