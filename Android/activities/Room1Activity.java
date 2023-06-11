package com.example.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Room1Activity extends AppCompat {

    private static final int LIGHT_ON = 1;
    private static final int LIGHT_OFF = 0;

    private static final int YELLOW = 0;
    private static final int RED = 1;
    private static final int ORANGE = 2;
    private static final int AQUA = 3;
    private static final int BLUE = 4;
    private static final int GREEN = 5;
    private static final int PINK = 6;
    private static final int PURPLE = 7;

    private ImageView Room1Bulb;
    private LinearLayout ColorsBar;

    private Button RedButton;
    private Button OrangeButton;
    private Button YellowButton;
    private Button AquaButton;
    private Button BlueButton;
    private Button GreenButton;
    private Button PinkButton;
    private Button PurpleButton;

    private Button CurrentButton = null;
    private int light_state = LIGHT_OFF;
    private int current_light_color = YELLOW;

    private int red_rgb_color;
    private int green_rgb_color;
    private int blue_rgb_color;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room1);

        Window window = Room1Activity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(Room1Activity.this, R.color.green_background));
        window.setNavigationBarColor(ContextCompat.getColor(Room1Activity.this, R.color.green_navigation_background));

        Room1Bulb = (ImageView) findViewById(R.id.bulb_icon_roo1);
        ColorsBar = (LinearLayout) findViewById(R.id.colors_bar);

        RedButton = (Button) findViewById(R.id.red_button);
        OrangeButton = (Button) findViewById(R.id.orange_button);
        YellowButton = (Button) findViewById(R.id.yellow_button);
        AquaButton = (Button) findViewById(R.id.aqua_button);
        BlueButton = (Button) findViewById(R.id.blue_button);
        GreenButton = (Button) findViewById(R.id.green_button);
        PinkButton = (Button) findViewById(R.id.pink_button);
        PurpleButton = (Button) findViewById(R.id.purple_button);

        light_state = BufferManager.TxBuffer[5];
        current_light_color = BufferManager.TxBuffer[44];
        red_rgb_color = BufferManager.TxBuffer[6];
        green_rgb_color = BufferManager.TxBuffer[7];
        blue_rgb_color = BufferManager.TxBuffer[8];

        getInitialActiveColorButton();

        if(LIGHT_OFF == light_state) {
            ColorsBar.setVisibility(View.INVISIBLE);
            Room1Bulb.setImageResource(R.drawable.baseline_lightbulb_200_black);
        }
        else if(LIGHT_ON == light_state) {
            ColorsBar.setVisibility(View.VISIBLE);
        }

        Room1Bulb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LIGHT_OFF == light_state) {
                    light_state = LIGHT_ON;
                    ColorsBar.setVisibility(View.VISIBLE);
                    setColorBulb();
                    getActiveColorButton();
                }
                else if(LIGHT_ON == light_state) {
                    Room1Bulb.setImageResource(R.drawable.baseline_lightbulb_200_black);
                    light_state = LIGHT_OFF;
                    ColorsBar.setVisibility(View.INVISIBLE);
                }
                BufferManager.TxBuffer[5] = (byte) light_state;
            }
        });

        RedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentButton = RedButton;
                current_light_color = RED;
                red_rgb_color = 254;
                green_rgb_color = 0;
                blue_rgb_color = 0;

                setColorBulb();
                getActiveColorButton();

                BufferManager.TxBuffer[44] = (byte) current_light_color;
                BufferManager.TxBuffer[6] = (byte) red_rgb_color;
                BufferManager.TxBuffer[7] = (byte) green_rgb_color;
                BufferManager.TxBuffer[8] = (byte) blue_rgb_color;
            }
        });

        YellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentButton = YellowButton;
                current_light_color = YELLOW;
                red_rgb_color = 254;
                green_rgb_color = 254;
                blue_rgb_color = 0;

                setColorBulb();
                getActiveColorButton();

                BufferManager.TxBuffer[44] = (byte) current_light_color;
                BufferManager.TxBuffer[6] = (byte) red_rgb_color;
                BufferManager.TxBuffer[7] = (byte) green_rgb_color;
                BufferManager.TxBuffer[8] = (byte) blue_rgb_color;
            }
        });

        OrangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentButton = OrangeButton;
                current_light_color = ORANGE;
                red_rgb_color = 254;
                green_rgb_color = 165;
                blue_rgb_color = 0;

                setColorBulb();
                getActiveColorButton();

                BufferManager.TxBuffer[44] = (byte) current_light_color;
                BufferManager.TxBuffer[6] = (byte) red_rgb_color;
                BufferManager.TxBuffer[7] = (byte) green_rgb_color;
                BufferManager.TxBuffer[8] = (byte) blue_rgb_color;
            }
        });

        AquaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentButton = AquaButton;
                current_light_color = AQUA;
                red_rgb_color = 0;
                green_rgb_color = 254;
                blue_rgb_color = 254;

                setColorBulb();
                getActiveColorButton();

                BufferManager.TxBuffer[44] = (byte) current_light_color;
                BufferManager.TxBuffer[6] = (byte) red_rgb_color;
                BufferManager.TxBuffer[7] = (byte) green_rgb_color;
                BufferManager.TxBuffer[8] = (byte) blue_rgb_color;
            }
        });

        BlueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentButton = BlueButton;
                current_light_color = BLUE;
                red_rgb_color = 0;
                green_rgb_color = 0;
                blue_rgb_color = 254;

                setColorBulb();
                getActiveColorButton();

                BufferManager.TxBuffer[44] = (byte) current_light_color;
                BufferManager.TxBuffer[6] = (byte) red_rgb_color;
                BufferManager.TxBuffer[7] = (byte) green_rgb_color;
                BufferManager.TxBuffer[8] = (byte) blue_rgb_color;
            }
        });

        GreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentButton = GreenButton;
                current_light_color = GREEN;
                red_rgb_color = 0;
                green_rgb_color = 254;
                blue_rgb_color = 0;

                setColorBulb();
                getActiveColorButton();

                BufferManager.TxBuffer[44] = (byte) current_light_color;
                BufferManager.TxBuffer[6] = (byte) red_rgb_color;
                BufferManager.TxBuffer[7] = (byte) green_rgb_color;
                BufferManager.TxBuffer[8] = (byte) blue_rgb_color;
            }
        });

        PinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentButton = PinkButton;
                current_light_color = PINK;
                red_rgb_color = 254;
                green_rgb_color = 0;
                blue_rgb_color = 254;

                setColorBulb();
                getActiveColorButton();

                BufferManager.TxBuffer[44] = (byte) current_light_color;
                BufferManager.TxBuffer[6] = (byte) red_rgb_color;
                BufferManager.TxBuffer[7] = (byte) green_rgb_color;
                BufferManager.TxBuffer[8] = (byte) blue_rgb_color;
            }
        });

        PurpleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentButton = PurpleButton;
                current_light_color = PURPLE;
                red_rgb_color = 128;
                green_rgb_color = 0;
                blue_rgb_color = 128;

                setColorBulb();
                getActiveColorButton();

                BufferManager.TxBuffer[44] = (byte) current_light_color;
                BufferManager.TxBuffer[6] = (byte) red_rgb_color;
                BufferManager.TxBuffer[7] = (byte) green_rgb_color;
                BufferManager.TxBuffer[8] = (byte) blue_rgb_color;
            }
        });
    }
    void setColorBulb() {
        if(CurrentButton == RedButton) {
            Room1Bulb.setImageResource(R.drawable.baseline_lightbulb_200_red);
        }
        if(CurrentButton == YellowButton) {
            Room1Bulb.setImageResource(R.drawable.baseline_lightbulb_200_yellow);
        }
        if(CurrentButton == OrangeButton) {
            Room1Bulb.setImageResource(R.drawable.baseline_lightbulb_200_orange);
        }
        if(CurrentButton == AquaButton) {
            Room1Bulb.setImageResource(R.drawable.baseline_lightbulb_200_aqua);
        }
        if(CurrentButton == BlueButton) {
            Room1Bulb.setImageResource(R.drawable.baseline_lightbulb_200_blue);
        }
        if(CurrentButton == GreenButton) {
            Room1Bulb.setImageResource(R.drawable.baseline_lightbulb_200_green);
        }
        if(CurrentButton == PinkButton) {
            Room1Bulb.setImageResource(R.drawable.baseline_lightbulb_200_pink);
        }
        if(CurrentButton == PurpleButton) {
            Room1Bulb.setImageResource(R.drawable.baseline_lightbulb_200_purple);
        }
    }
    void getActiveColorButton() {
        if(CurrentButton == RedButton) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) RedButton.getLayoutParams();
            params.weight = 0.0f;
            RedButton.setLayoutParams(params);

            LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) YellowButton.getLayoutParams();
            params_1.weight = 1.0f;
            YellowButton.setLayoutParams(params_1);

            LinearLayout.LayoutParams params_2 = (LinearLayout.LayoutParams) OrangeButton.getLayoutParams();
            params_2.weight = 1.0f;
            OrangeButton.setLayoutParams(params_2);

            LinearLayout.LayoutParams params_3 = (LinearLayout.LayoutParams) AquaButton.getLayoutParams();
            params_3.weight = 1.0f;
            AquaButton.setLayoutParams(params_3);

            LinearLayout.LayoutParams params_4 = (LinearLayout.LayoutParams) BlueButton.getLayoutParams();
            params_4.weight = 1.0f;
            BlueButton.setLayoutParams(params_4);

            LinearLayout.LayoutParams params_5 = (LinearLayout.LayoutParams) GreenButton.getLayoutParams();
            params_5.weight = 1.0f;
            GreenButton.setLayoutParams(params_5);

            LinearLayout.LayoutParams params_6 = (LinearLayout.LayoutParams) PurpleButton.getLayoutParams();
            params_6.weight = 1.0f;
            PurpleButton.setLayoutParams(params_6);

            LinearLayout.LayoutParams params_7 = (LinearLayout.LayoutParams) PinkButton.getLayoutParams();
            params_7.weight = 1.0f;
            PinkButton.setLayoutParams(params_7);
        }
        if(CurrentButton == YellowButton) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) RedButton.getLayoutParams();
            params.weight = 1.0f;
            RedButton.setLayoutParams(params);

            LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) YellowButton.getLayoutParams();
            params_1.weight = 0.0f;
            YellowButton.setLayoutParams(params_1);

            LinearLayout.LayoutParams params_2 = (LinearLayout.LayoutParams) OrangeButton.getLayoutParams();
            params_2.weight = 1.0f;
            OrangeButton.setLayoutParams(params_2);

            LinearLayout.LayoutParams params_3 = (LinearLayout.LayoutParams) AquaButton.getLayoutParams();
            params_3.weight = 1.0f;
            AquaButton.setLayoutParams(params_3);

            LinearLayout.LayoutParams params_4 = (LinearLayout.LayoutParams) BlueButton.getLayoutParams();
            params_4.weight = 1.0f;
            BlueButton.setLayoutParams(params_4);

            LinearLayout.LayoutParams params_5 = (LinearLayout.LayoutParams) GreenButton.getLayoutParams();
            params_5.weight = 1.0f;
            GreenButton.setLayoutParams(params_5);

            LinearLayout.LayoutParams params_6 = (LinearLayout.LayoutParams) PurpleButton.getLayoutParams();
            params_6.weight = 1.0f;
            PurpleButton.setLayoutParams(params_6);

            LinearLayout.LayoutParams params_7 = (LinearLayout.LayoutParams) PinkButton.getLayoutParams();
            params_7.weight = 1.0f;
            PinkButton.setLayoutParams(params_7);
        }
        if(CurrentButton == OrangeButton) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) RedButton.getLayoutParams();
            params.weight = 1.0f;
            RedButton.setLayoutParams(params);

            LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) YellowButton.getLayoutParams();
            params_1.weight = 1.0f;
            YellowButton.setLayoutParams(params_1);

            LinearLayout.LayoutParams params_2 = (LinearLayout.LayoutParams) OrangeButton.getLayoutParams();
            params_2.weight = 0.0f;
            OrangeButton.setLayoutParams(params_2);

            LinearLayout.LayoutParams params_3 = (LinearLayout.LayoutParams) AquaButton.getLayoutParams();
            params_3.weight = 1.0f;
            AquaButton.setLayoutParams(params_3);

            LinearLayout.LayoutParams params_4 = (LinearLayout.LayoutParams) BlueButton.getLayoutParams();
            params_4.weight = 1.0f;
            BlueButton.setLayoutParams(params_4);

            LinearLayout.LayoutParams params_5 = (LinearLayout.LayoutParams) GreenButton.getLayoutParams();
            params_5.weight = 1.0f;
            GreenButton.setLayoutParams(params_5);

            LinearLayout.LayoutParams params_6 = (LinearLayout.LayoutParams) PurpleButton.getLayoutParams();
            params_6.weight = 1.0f;
            PurpleButton.setLayoutParams(params_6);

            LinearLayout.LayoutParams params_7 = (LinearLayout.LayoutParams) PinkButton.getLayoutParams();
            params_7.weight = 1.0f;
            PinkButton.setLayoutParams(params_7);
        }
        if(CurrentButton == AquaButton) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) RedButton.getLayoutParams();
            params.weight = 1.0f;
            RedButton.setLayoutParams(params);

            LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) YellowButton.getLayoutParams();
            params_1.weight = 1.0f;
            YellowButton.setLayoutParams(params_1);

            LinearLayout.LayoutParams params_2 = (LinearLayout.LayoutParams) OrangeButton.getLayoutParams();
            params_2.weight = 1.0f;
            OrangeButton.setLayoutParams(params_2);

            LinearLayout.LayoutParams params_3 = (LinearLayout.LayoutParams) AquaButton.getLayoutParams();
            params_3.weight = 0.0f;
            AquaButton.setLayoutParams(params_3);

            LinearLayout.LayoutParams params_4 = (LinearLayout.LayoutParams) BlueButton.getLayoutParams();
            params_4.weight = 1.0f;
            BlueButton.setLayoutParams(params_4);

            LinearLayout.LayoutParams params_5 = (LinearLayout.LayoutParams) GreenButton.getLayoutParams();
            params_5.weight = 1.0f;
            GreenButton.setLayoutParams(params_5);

            LinearLayout.LayoutParams params_6 = (LinearLayout.LayoutParams) PurpleButton.getLayoutParams();
            params_6.weight = 1.0f;
            PurpleButton.setLayoutParams(params_6);

            LinearLayout.LayoutParams params_7 = (LinearLayout.LayoutParams) PinkButton.getLayoutParams();
            params_7.weight = 1.0f;
            PinkButton.setLayoutParams(params_7);
        }
        if(CurrentButton == BlueButton) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) RedButton.getLayoutParams();
            params.weight = 1.0f;
            RedButton.setLayoutParams(params);

            LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) YellowButton.getLayoutParams();
            params_1.weight = 1.0f;
            YellowButton.setLayoutParams(params_1);

            LinearLayout.LayoutParams params_2 = (LinearLayout.LayoutParams) OrangeButton.getLayoutParams();
            params_2.weight = 1.0f;
            OrangeButton.setLayoutParams(params_2);

            LinearLayout.LayoutParams params_3 = (LinearLayout.LayoutParams) AquaButton.getLayoutParams();
            params_3.weight = 1.0f;
            AquaButton.setLayoutParams(params_3);

            LinearLayout.LayoutParams params_4 = (LinearLayout.LayoutParams) BlueButton.getLayoutParams();
            params_4.weight = 0.0f;
            BlueButton.setLayoutParams(params_4);

            LinearLayout.LayoutParams params_5 = (LinearLayout.LayoutParams) GreenButton.getLayoutParams();
            params_5.weight = 1.0f;
            GreenButton.setLayoutParams(params_5);

            LinearLayout.LayoutParams params_6 = (LinearLayout.LayoutParams) PurpleButton.getLayoutParams();
            params_6.weight = 1.0f;
            PurpleButton.setLayoutParams(params_6);

            LinearLayout.LayoutParams params_7 = (LinearLayout.LayoutParams) PinkButton.getLayoutParams();
            params_7.weight = 1.0f;
            PinkButton.setLayoutParams(params_7);
        }
        if(CurrentButton == GreenButton) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) RedButton.getLayoutParams();
            params.weight = 1.0f;
            RedButton.setLayoutParams(params);

            LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) YellowButton.getLayoutParams();
            params_1.weight = 1.0f;
            YellowButton.setLayoutParams(params_1);

            LinearLayout.LayoutParams params_2 = (LinearLayout.LayoutParams) OrangeButton.getLayoutParams();
            params_2.weight = 1.0f;
            OrangeButton.setLayoutParams(params_2);

            LinearLayout.LayoutParams params_3 = (LinearLayout.LayoutParams) AquaButton.getLayoutParams();
            params_3.weight = 1.0f;
            AquaButton.setLayoutParams(params_3);

            LinearLayout.LayoutParams params_4 = (LinearLayout.LayoutParams) BlueButton.getLayoutParams();
            params_4.weight = 1.0f;
            BlueButton.setLayoutParams(params_4);

            LinearLayout.LayoutParams params_5 = (LinearLayout.LayoutParams) GreenButton.getLayoutParams();
            params_5.weight = 0.0f;
            GreenButton.setLayoutParams(params_5);

            LinearLayout.LayoutParams params_6 = (LinearLayout.LayoutParams) PurpleButton.getLayoutParams();
            params_6.weight = 1.0f;
            PurpleButton.setLayoutParams(params_6);

            LinearLayout.LayoutParams params_7 = (LinearLayout.LayoutParams) PinkButton.getLayoutParams();
            params_7.weight = 1.0f;
            PinkButton.setLayoutParams(params_7);
        }
        if(CurrentButton == PinkButton) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) RedButton.getLayoutParams();
            params.weight = 1.0f;
            RedButton.setLayoutParams(params);

            LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) YellowButton.getLayoutParams();
            params_1.weight = 1.0f;
            YellowButton.setLayoutParams(params_1);

            LinearLayout.LayoutParams params_2 = (LinearLayout.LayoutParams) OrangeButton.getLayoutParams();
            params_2.weight = 1.0f;
            OrangeButton.setLayoutParams(params_2);

            LinearLayout.LayoutParams params_3 = (LinearLayout.LayoutParams) AquaButton.getLayoutParams();
            params_3.weight = 1.0f;
            AquaButton.setLayoutParams(params_3);

            LinearLayout.LayoutParams params_4 = (LinearLayout.LayoutParams) BlueButton.getLayoutParams();
            params_4.weight = 1.0f;
            BlueButton.setLayoutParams(params_4);

            LinearLayout.LayoutParams params_5 = (LinearLayout.LayoutParams) GreenButton.getLayoutParams();
            params_5.weight = 1.0f;
            GreenButton.setLayoutParams(params_5);

            LinearLayout.LayoutParams params_6 = (LinearLayout.LayoutParams) PurpleButton.getLayoutParams();
            params_6.weight = 1.0f;
            PurpleButton.setLayoutParams(params_6);

            LinearLayout.LayoutParams params_7 = (LinearLayout.LayoutParams) PinkButton.getLayoutParams();
            params_7.weight = 0.0f;
            PinkButton.setLayoutParams(params_7);
        }
        if(CurrentButton == PurpleButton) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) RedButton.getLayoutParams();
            params.weight = 1.0f;
            RedButton.setLayoutParams(params);

            LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) YellowButton.getLayoutParams();
            params_1.weight = 1.0f;
            YellowButton.setLayoutParams(params_1);

            LinearLayout.LayoutParams params_2 = (LinearLayout.LayoutParams) OrangeButton.getLayoutParams();
            params_2.weight = 1.0f;
            OrangeButton.setLayoutParams(params_2);

            LinearLayout.LayoutParams params_3 = (LinearLayout.LayoutParams) AquaButton.getLayoutParams();
            params_3.weight = 1.0f;
            AquaButton.setLayoutParams(params_3);

            LinearLayout.LayoutParams params_4 = (LinearLayout.LayoutParams) BlueButton.getLayoutParams();
            params_4.weight = 1.0f;
            BlueButton.setLayoutParams(params_4);

            LinearLayout.LayoutParams params_5 = (LinearLayout.LayoutParams) GreenButton.getLayoutParams();
            params_5.weight = 1.0f;
            GreenButton.setLayoutParams(params_5);

            LinearLayout.LayoutParams params_6 = (LinearLayout.LayoutParams) PurpleButton.getLayoutParams();
            params_6.weight = 0.0f;
            PurpleButton.setLayoutParams(params_6);

            LinearLayout.LayoutParams params_7 = (LinearLayout.LayoutParams) PinkButton.getLayoutParams();
            params_7.weight = 1.0f;
            PinkButton.setLayoutParams(params_7);
        }
    }

    void getInitialActiveColorButton() {
        if(RED == current_light_color) {

            CurrentButton = RedButton;
            Room1Bulb.setImageResource(R.drawable.baseline_lightbulb_200_red);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) RedButton.getLayoutParams();
            params.weight = 0.0f;
            RedButton.setLayoutParams(params);

            LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) YellowButton.getLayoutParams();
            params_1.weight = 1.0f;
            YellowButton.setLayoutParams(params_1);

            LinearLayout.LayoutParams params_2 = (LinearLayout.LayoutParams) OrangeButton.getLayoutParams();
            params_2.weight = 1.0f;
            OrangeButton.setLayoutParams(params_2);

            LinearLayout.LayoutParams params_3 = (LinearLayout.LayoutParams) AquaButton.getLayoutParams();
            params_3.weight = 1.0f;
            AquaButton.setLayoutParams(params_3);

            LinearLayout.LayoutParams params_4 = (LinearLayout.LayoutParams) BlueButton.getLayoutParams();
            params_4.weight = 1.0f;
            BlueButton.setLayoutParams(params_4);

            LinearLayout.LayoutParams params_5 = (LinearLayout.LayoutParams) GreenButton.getLayoutParams();
            params_5.weight = 1.0f;
            GreenButton.setLayoutParams(params_5);

            LinearLayout.LayoutParams params_6 = (LinearLayout.LayoutParams) PurpleButton.getLayoutParams();
            params_6.weight = 1.0f;
            PurpleButton.setLayoutParams(params_6);

            LinearLayout.LayoutParams params_7 = (LinearLayout.LayoutParams) PinkButton.getLayoutParams();
            params_7.weight = 1.0f;
            PinkButton.setLayoutParams(params_7);
        }
        if(YELLOW == current_light_color) {

            CurrentButton = YellowButton;
            Room1Bulb.setImageResource(R.drawable.baseline_lightbulb_200_yellow);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) RedButton.getLayoutParams();
            params.weight = 1.0f;
            RedButton.setLayoutParams(params);

            LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) YellowButton.getLayoutParams();
            params_1.weight = 0.0f;
            YellowButton.setLayoutParams(params_1);

            LinearLayout.LayoutParams params_2 = (LinearLayout.LayoutParams) OrangeButton.getLayoutParams();
            params_2.weight = 1.0f;
            OrangeButton.setLayoutParams(params_2);

            LinearLayout.LayoutParams params_3 = (LinearLayout.LayoutParams) AquaButton.getLayoutParams();
            params_3.weight = 1.0f;
            AquaButton.setLayoutParams(params_3);

            LinearLayout.LayoutParams params_4 = (LinearLayout.LayoutParams) BlueButton.getLayoutParams();
            params_4.weight = 1.0f;
            BlueButton.setLayoutParams(params_4);

            LinearLayout.LayoutParams params_5 = (LinearLayout.LayoutParams) GreenButton.getLayoutParams();
            params_5.weight = 1.0f;
            GreenButton.setLayoutParams(params_5);

            LinearLayout.LayoutParams params_6 = (LinearLayout.LayoutParams) PurpleButton.getLayoutParams();
            params_6.weight = 1.0f;
            PurpleButton.setLayoutParams(params_6);

            LinearLayout.LayoutParams params_7 = (LinearLayout.LayoutParams) PinkButton.getLayoutParams();
            params_7.weight = 1.0f;
            PinkButton.setLayoutParams(params_7);
        }
        if(ORANGE == current_light_color) {

            CurrentButton = OrangeButton;
            Room1Bulb.setImageResource(R.drawable.baseline_lightbulb_200_orange);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) RedButton.getLayoutParams();
            params.weight = 1.0f;
            RedButton.setLayoutParams(params);

            LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) YellowButton.getLayoutParams();
            params_1.weight = 1.0f;
            YellowButton.setLayoutParams(params_1);

            LinearLayout.LayoutParams params_2 = (LinearLayout.LayoutParams) OrangeButton.getLayoutParams();
            params_2.weight = 0.0f;
            OrangeButton.setLayoutParams(params_2);

            LinearLayout.LayoutParams params_3 = (LinearLayout.LayoutParams) AquaButton.getLayoutParams();
            params_3.weight = 1.0f;
            AquaButton.setLayoutParams(params_3);

            LinearLayout.LayoutParams params_4 = (LinearLayout.LayoutParams) BlueButton.getLayoutParams();
            params_4.weight = 1.0f;
            BlueButton.setLayoutParams(params_4);

            LinearLayout.LayoutParams params_5 = (LinearLayout.LayoutParams) GreenButton.getLayoutParams();
            params_5.weight = 1.0f;
            GreenButton.setLayoutParams(params_5);

            LinearLayout.LayoutParams params_6 = (LinearLayout.LayoutParams) PurpleButton.getLayoutParams();
            params_6.weight = 1.0f;
            PurpleButton.setLayoutParams(params_6);

            LinearLayout.LayoutParams params_7 = (LinearLayout.LayoutParams) PinkButton.getLayoutParams();
            params_7.weight = 1.0f;
            PinkButton.setLayoutParams(params_7);
        }
        if(AQUA == current_light_color) {

            CurrentButton = AquaButton;
            Room1Bulb.setImageResource(R.drawable.baseline_lightbulb_200_aqua);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) RedButton.getLayoutParams();
            params.weight = 1.0f;
            RedButton.setLayoutParams(params);

            LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) YellowButton.getLayoutParams();
            params_1.weight = 1.0f;
            YellowButton.setLayoutParams(params_1);

            LinearLayout.LayoutParams params_2 = (LinearLayout.LayoutParams) OrangeButton.getLayoutParams();
            params_2.weight = 1.0f;
            OrangeButton.setLayoutParams(params_2);

            LinearLayout.LayoutParams params_3 = (LinearLayout.LayoutParams) AquaButton.getLayoutParams();
            params_3.weight = 0.0f;
            AquaButton.setLayoutParams(params_3);

            LinearLayout.LayoutParams params_4 = (LinearLayout.LayoutParams) BlueButton.getLayoutParams();
            params_4.weight = 1.0f;
            BlueButton.setLayoutParams(params_4);

            LinearLayout.LayoutParams params_5 = (LinearLayout.LayoutParams) GreenButton.getLayoutParams();
            params_5.weight = 1.0f;
            GreenButton.setLayoutParams(params_5);

            LinearLayout.LayoutParams params_6 = (LinearLayout.LayoutParams) PurpleButton.getLayoutParams();
            params_6.weight = 1.0f;
            PurpleButton.setLayoutParams(params_6);

            LinearLayout.LayoutParams params_7 = (LinearLayout.LayoutParams) PinkButton.getLayoutParams();
            params_7.weight = 1.0f;
            PinkButton.setLayoutParams(params_7);
        }
        if(BLUE == current_light_color) {

            CurrentButton = BlueButton;
            Room1Bulb.setImageResource(R.drawable.baseline_lightbulb_200_blue);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) RedButton.getLayoutParams();
            params.weight = 1.0f;
            RedButton.setLayoutParams(params);

            LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) YellowButton.getLayoutParams();
            params_1.weight = 1.0f;
            YellowButton.setLayoutParams(params_1);

            LinearLayout.LayoutParams params_2 = (LinearLayout.LayoutParams) OrangeButton.getLayoutParams();
            params_2.weight = 1.0f;
            OrangeButton.setLayoutParams(params_2);

            LinearLayout.LayoutParams params_3 = (LinearLayout.LayoutParams) AquaButton.getLayoutParams();
            params_3.weight = 1.0f;
            AquaButton.setLayoutParams(params_3);

            LinearLayout.LayoutParams params_4 = (LinearLayout.LayoutParams) BlueButton.getLayoutParams();
            params_4.weight = 0.0f;
            BlueButton.setLayoutParams(params_4);

            LinearLayout.LayoutParams params_5 = (LinearLayout.LayoutParams) GreenButton.getLayoutParams();
            params_5.weight = 1.0f;
            GreenButton.setLayoutParams(params_5);

            LinearLayout.LayoutParams params_6 = (LinearLayout.LayoutParams) PurpleButton.getLayoutParams();
            params_6.weight = 1.0f;
            PurpleButton.setLayoutParams(params_6);

            LinearLayout.LayoutParams params_7 = (LinearLayout.LayoutParams) PinkButton.getLayoutParams();
            params_7.weight = 1.0f;
            PinkButton.setLayoutParams(params_7);
        }
        if(GREEN == current_light_color) {

            CurrentButton = GreenButton;
            Room1Bulb.setImageResource(R.drawable.baseline_lightbulb_200_green);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) RedButton.getLayoutParams();
            params.weight = 1.0f;
            RedButton.setLayoutParams(params);

            LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) YellowButton.getLayoutParams();
            params_1.weight = 1.0f;
            YellowButton.setLayoutParams(params_1);

            LinearLayout.LayoutParams params_2 = (LinearLayout.LayoutParams) OrangeButton.getLayoutParams();
            params_2.weight = 1.0f;
            OrangeButton.setLayoutParams(params_2);

            LinearLayout.LayoutParams params_3 = (LinearLayout.LayoutParams) AquaButton.getLayoutParams();
            params_3.weight = 1.0f;
            AquaButton.setLayoutParams(params_3);

            LinearLayout.LayoutParams params_4 = (LinearLayout.LayoutParams) BlueButton.getLayoutParams();
            params_4.weight = 1.0f;
            BlueButton.setLayoutParams(params_4);

            LinearLayout.LayoutParams params_5 = (LinearLayout.LayoutParams) GreenButton.getLayoutParams();
            params_5.weight = 0.0f;
            GreenButton.setLayoutParams(params_5);

            LinearLayout.LayoutParams params_6 = (LinearLayout.LayoutParams) PurpleButton.getLayoutParams();
            params_6.weight = 1.0f;
            PurpleButton.setLayoutParams(params_6);

            LinearLayout.LayoutParams params_7 = (LinearLayout.LayoutParams) PinkButton.getLayoutParams();
            params_7.weight = 1.0f;
            PinkButton.setLayoutParams(params_7);
        }
        if(PINK == current_light_color) {

            CurrentButton = PinkButton;
            Room1Bulb.setImageResource(R.drawable.baseline_lightbulb_200_pink);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) RedButton.getLayoutParams();
            params.weight = 1.0f;
            RedButton.setLayoutParams(params);

            LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) YellowButton.getLayoutParams();
            params_1.weight = 1.0f;
            YellowButton.setLayoutParams(params_1);

            LinearLayout.LayoutParams params_2 = (LinearLayout.LayoutParams) OrangeButton.getLayoutParams();
            params_2.weight = 1.0f;
            OrangeButton.setLayoutParams(params_2);

            LinearLayout.LayoutParams params_3 = (LinearLayout.LayoutParams) AquaButton.getLayoutParams();
            params_3.weight = 1.0f;
            AquaButton.setLayoutParams(params_3);

            LinearLayout.LayoutParams params_4 = (LinearLayout.LayoutParams) BlueButton.getLayoutParams();
            params_4.weight = 1.0f;
            BlueButton.setLayoutParams(params_4);

            LinearLayout.LayoutParams params_5 = (LinearLayout.LayoutParams) GreenButton.getLayoutParams();
            params_5.weight = 1.0f;
            GreenButton.setLayoutParams(params_5);

            LinearLayout.LayoutParams params_6 = (LinearLayout.LayoutParams) PurpleButton.getLayoutParams();
            params_6.weight = 1.0f;
            PurpleButton.setLayoutParams(params_6);

            LinearLayout.LayoutParams params_7 = (LinearLayout.LayoutParams) PinkButton.getLayoutParams();
            params_7.weight = 0.0f;
            PinkButton.setLayoutParams(params_7);
        }
        if(PURPLE == current_light_color) {

            CurrentButton = PurpleButton;
            Room1Bulb.setImageResource(R.drawable.baseline_lightbulb_200_purple);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) RedButton.getLayoutParams();
            params.weight = 1.0f;
            RedButton.setLayoutParams(params);

            LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) YellowButton.getLayoutParams();
            params_1.weight = 1.0f;
            YellowButton.setLayoutParams(params_1);

            LinearLayout.LayoutParams params_2 = (LinearLayout.LayoutParams) OrangeButton.getLayoutParams();
            params_2.weight = 1.0f;
            OrangeButton.setLayoutParams(params_2);

            LinearLayout.LayoutParams params_3 = (LinearLayout.LayoutParams) AquaButton.getLayoutParams();
            params_3.weight = 1.0f;
            AquaButton.setLayoutParams(params_3);

            LinearLayout.LayoutParams params_4 = (LinearLayout.LayoutParams) BlueButton.getLayoutParams();
            params_4.weight = 1.0f;
            BlueButton.setLayoutParams(params_4);

            LinearLayout.LayoutParams params_5 = (LinearLayout.LayoutParams) GreenButton.getLayoutParams();
            params_5.weight = 1.0f;
            GreenButton.setLayoutParams(params_5);

            LinearLayout.LayoutParams params_6 = (LinearLayout.LayoutParams) PurpleButton.getLayoutParams();
            params_6.weight = 0.0f;
            PurpleButton.setLayoutParams(params_6);

            LinearLayout.LayoutParams params_7 = (LinearLayout.LayoutParams) PinkButton.getLayoutParams();
            params_7.weight = 1.0f;
            PinkButton.setLayoutParams(params_7);
        }
    }
}
