package com.example.home;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class DoorsActivity extends AppCompatActivity {

    static final int DOOR_OPEN = 1;
    static final int DOOR_CLOSE = 0;

    static final int DOOR_LOCKED = 1;
    static final int DOOR_UNLOCKED = 0;

    static final int AUTO_CLOSE_ON = 1;
    static final int AUTO_CLOSE_OFF = 0;
    LinearLayout DoorButton;
    LinearLayout GatesButton;

    TextView DoorTextView;
    TextView GatesTextView;

    LinearLayout FrontDoorLayout;
    LinearLayout GatesLayout;

    ImageView DoorIcon;
    ImageView GatesIcon;

    Switch DoorLockSwitch;
    Switch DoorClosingSwitch;
    Switch GatesClosingSwitch;

    int door_state = DOOR_CLOSE;
    int gates_state = DOOR_CLOSE;

    int locking_state = DOOR_UNLOCKED;
    int auto_closing_door_state = AUTO_CLOSE_OFF;
    int auto_closing_gate_state = AUTO_CLOSE_OFF;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doors);

        Window window = DoorsActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(DoorsActivity.this, R.color.purple_background));
        window.setNavigationBarColor(ContextCompat.getColor(DoorsActivity.this, R.color.purple_navigation_background));

        DoorButton = (LinearLayout) findViewById(R.id.front_door_selector);
        GatesButton = (LinearLayout) findViewById(R.id.gates_selector);

        DoorTextView = (TextView) findViewById(R.id.front_door_text);
        GatesTextView = (TextView) findViewById(R.id.gates_text);

        FrontDoorLayout = (LinearLayout) findViewById(R.id.front_door_layout);
        GatesLayout = (LinearLayout) findViewById(R.id.gates_layout);

        DoorIcon = (ImageView) findViewById(R.id.door_icon);
        GatesIcon = (ImageView) findViewById(R.id.gates_icon);

        DoorLockSwitch = (Switch) findViewById(R.id.lock_switch);
        DoorClosingSwitch = (Switch) findViewById(R.id.closing_door_switch);
        GatesClosingSwitch = (Switch) findViewById(R.id.closing_gate_switch);

        GatesLayout.setVisibility(View.INVISIBLE);


        DoorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoorButton.setBackgroundColor(ContextCompat.getColor(DoorsActivity.this, R.color.purple_navigation_background));
                GatesButton.setBackgroundColor(ContextCompat.getColor(DoorsActivity.this, R.color.purple_fade_background));

                DoorTextView.setTextColor(ContextCompat.getColor(DoorsActivity.this, R.color.white));
                GatesTextView.setTextColor(ContextCompat.getColor(DoorsActivity.this, R.color.black));

                GatesLayout.setVisibility(View.INVISIBLE);
                FrontDoorLayout.setVisibility(View.VISIBLE);
            }
        });

        GatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoorButton.setBackgroundColor(ContextCompat.getColor(DoorsActivity.this, R.color.purple_fade_background));
                GatesButton.setBackgroundColor(ContextCompat.getColor(DoorsActivity.this, R.color.purple_navigation_background));

                DoorTextView.setTextColor(ContextCompat.getColor(DoorsActivity.this, R.color.black));
                GatesTextView.setTextColor(ContextCompat.getColor(DoorsActivity.this, R.color.white));

                FrontDoorLayout.setVisibility(View.INVISIBLE);
                GatesLayout.setVisibility(View.VISIBLE);
            }
        });

        DoorIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DOOR_CLOSE == door_state) {
                    if(DOOR_LOCKED != locking_state) {
                        DoorIcon.setImageResource(R.drawable.baseline_door_sliding_200_open);
                        door_state = DOOR_OPEN;
                    }
                }
                else if(DOOR_OPEN == door_state) {
                    DoorIcon.setImageResource(R.drawable.baseline_door_sliding_200);
                    door_state = DOOR_CLOSE;
                }
            }
        });

        GatesIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DOOR_CLOSE == gates_state) {
                    GatesIcon.setImageResource(R.drawable.baseline_door_sliding_200_open);
                    gates_state = DOOR_OPEN;
                }
                else if(DOOR_OPEN == gates_state) {
                    GatesIcon.setImageResource(R.drawable.baseline_door_sliding_200);
                    gates_state = DOOR_CLOSE;
                }
            }
        });

        DoorLockSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DOOR_UNLOCKED == locking_state) {
                    locking_state = DOOR_LOCKED;
                    DoorLockSwitch.setChecked(true);
                    DoorLockSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DoorsActivity.this, R.color.purple_navigation_background)));

                    DoorIcon.setImageResource(R.drawable.baseline_door_sliding_200);
                    door_state = DOOR_CLOSE;
                }
                else {
                    locking_state = DOOR_UNLOCKED;
                    DoorLockSwitch.setChecked(false);
                    DoorLockSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DoorsActivity.this, R.color.gray_navigation_background)));
                }
            }
        });

        DoorClosingSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AUTO_CLOSE_OFF == auto_closing_door_state) {
                    auto_closing_door_state = AUTO_CLOSE_ON;
                    DoorClosingSwitch.setChecked(true);
                    DoorClosingSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DoorsActivity.this, R.color.purple_navigation_background)));

                }
                else {
                    auto_closing_door_state = AUTO_CLOSE_OFF;
                    DoorClosingSwitch.setChecked(false);
                    DoorClosingSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DoorsActivity.this, R.color.gray_navigation_background)));
                }
            }
        });

        GatesClosingSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AUTO_CLOSE_OFF == auto_closing_gate_state) {
                    auto_closing_gate_state = AUTO_CLOSE_ON;
                    GatesClosingSwitch.setChecked(true);
                    GatesClosingSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DoorsActivity.this, R.color.purple_navigation_background)));

                }
                else {
                    auto_closing_gate_state = AUTO_CLOSE_OFF;
                    GatesClosingSwitch.setChecked(false);
                    GatesClosingSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DoorsActivity.this, R.color.gray_navigation_background)));
                }
            }
        });
    }
}
