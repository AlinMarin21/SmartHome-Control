package com.example.home;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
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

public class DoorsActivity extends AppCompat {

    private static final int DOOR_OPEN = 1;
    private static final int DOOR_CLOSE = 0;

    private static final int DOOR_LOCKED = 1;
    private static final int DOOR_UNLOCKED = 0;

    private static final int AUTO_CLOSE_ON = 1;
    private static final int AUTO_CLOSE_OFF = 0;

    private static final int TIME_EXPIRED = 1;
    private static final int TIME_NOT_EXPIRED = 0;
    private LinearLayout DoorButton;
    private LinearLayout GatesButton;

    private TextView DoorTextView;
    private TextView GatesTextView;

    private LinearLayout FrontDoorLayout;
    private LinearLayout GatesLayout;

    private ImageView DoorIcon;
    private ImageView GatesIcon;

    private Switch DoorLockSwitch;
    private Switch DoorClosingSwitch;
    private Switch GatesClosingSwitch;

    private int door_state = DOOR_CLOSE;
    private int gates_state = DOOR_CLOSE;

    private int locking_state = DOOR_UNLOCKED;
    private int auto_closing_door_state = AUTO_CLOSE_OFF;
    private int auto_closing_gate_state = AUTO_CLOSE_OFF;

    private Handler mHandler;

    private int door_time_expired = TIME_NOT_EXPIRED;
    private int gate_time_expired = TIME_NOT_EXPIRED;

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

        door_state = BufferManager.TxBuffer[16];
        auto_closing_door_state = BufferManager.TxBuffer[17];
        locking_state = BufferManager.TxBuffer[18];
        gates_state = BufferManager.TxBuffer[19];
        auto_closing_gate_state = BufferManager.TxBuffer[20];

        door_time_expired = BufferManager.RxBuffer[11];
        gate_time_expired = BufferManager.RxBuffer[12];

        if(BufferManager.currentLanguage.equals("fr")) {
            DoorClosingSwitch.setTextSize(16);
            GatesClosingSwitch.setTextSize(16);
        }
        if(BufferManager.currentLanguage.equals("de")) {
            DoorClosingSwitch.setTextSize(13);
            GatesClosingSwitch.setTextSize(13);
        }
        if(BufferManager.currentLanguage.equals("ru")) {
            DoorClosingSwitch.setTextSize(11);
            GatesClosingSwitch.setTextSize(11);
        }

        mHandler = new Handler();
        startRepeatingTask();

        GatesLayout.setVisibility(View.INVISIBLE);

        if(DOOR_OPEN == door_state) {
            DoorIcon.setImageResource(R.drawable.baseline_door_sliding_200_open);
        }
        else {
            DoorIcon.setImageResource(R.drawable.baseline_door_sliding_200);
        }

        if(DOOR_OPEN == gates_state) {
            GatesIcon.setImageResource(R.drawable.baseline_door_sliding_200_open);
        }
        else {
            GatesIcon.setImageResource(R.drawable.baseline_door_sliding_200);
        }

        if(DOOR_LOCKED == locking_state) {
            DoorLockSwitch.setChecked(true);
            DoorLockSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DoorsActivity.this, R.color.purple_navigation_background)));
        }
        else {
            DoorLockSwitch.setChecked(false);
            DoorLockSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DoorsActivity.this, R.color.gray_navigation_background)));
        }

        if(AUTO_CLOSE_ON == auto_closing_door_state) {
            DoorClosingSwitch.setChecked(true);
            DoorClosingSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DoorsActivity.this, R.color.purple_navigation_background)));
        }
        else {
            DoorClosingSwitch.setChecked(false);
            DoorClosingSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DoorsActivity.this, R.color.gray_navigation_background)));
        }

        if(AUTO_CLOSE_ON == auto_closing_gate_state) {
            GatesClosingSwitch.setChecked(true);
            GatesClosingSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DoorsActivity.this, R.color.purple_navigation_background)));
        }
        else {
            GatesClosingSwitch.setChecked(false);
            GatesClosingSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(DoorsActivity.this, R.color.gray_navigation_background)));
        }


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
                BufferManager.TxBuffer[16] = (byte) door_state;
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
                BufferManager.TxBuffer[19] = (byte) gates_state;
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
                BufferManager.TxBuffer[18] = (byte) locking_state;
                BufferManager.TxBuffer[16] = (byte) door_state;
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
                BufferManager.TxBuffer[17] = (byte) auto_closing_door_state;
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
                BufferManager.TxBuffer[20] = (byte) auto_closing_gate_state;
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
                door_time_expired = BufferManager.RxBuffer[11];
                gate_time_expired = BufferManager.RxBuffer[12];

                if(TIME_EXPIRED == door_time_expired) {
                    BufferManager.TxBuffer[16] = DOOR_CLOSE;
                    DoorIcon.setImageResource(R.drawable.baseline_door_sliding_200);
                }
                if(TIME_EXPIRED == gate_time_expired) {
                    BufferManager.TxBuffer[19] = DOOR_CLOSE;
                    GatesIcon.setImageResource(R.drawable.baseline_door_sliding_200);
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
