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

public class Room2Activity extends AppCompatActivity {

    static final int LIGHT_ON = 1;
    static final int LIGHT_OFF = 0;

    ImageView Room2Bulb;
    LinearLayout ColorsBar;

    Button RedButton;
    Button OrangeButton;
    Button YellowButton;
    Button AquaButton;
    Button BlueButton;
    Button GreenButton;
    Button PinkButton;
    Button PurpleButton;

    Button CurrentButton = null;
    int light_state = LIGHT_OFF;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room2);

        Window window = Room2Activity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(Room2Activity.this, R.color.beige_background));
        window.setNavigationBarColor(ContextCompat.getColor(Room2Activity.this, R.color.beige_navigation_background));

        Room2Bulb = (ImageView) findViewById(R.id.bulb_icon_room2);
        ColorsBar = (LinearLayout) findViewById(R.id.colors_bar);

        RedButton = (Button) findViewById(R.id.red_button);
        OrangeButton = (Button) findViewById(R.id.orange_button);
        YellowButton = (Button) findViewById(R.id.yellow_button);
        AquaButton = (Button) findViewById(R.id.aqua_button);
        BlueButton = (Button) findViewById(R.id.blue_button);
        GreenButton = (Button) findViewById(R.id.green_button);
        PinkButton = (Button) findViewById(R.id.pink_button);
        PurpleButton = (Button) findViewById(R.id.purple_button);

        CurrentButton = RedButton;

        if(LIGHT_OFF == light_state) {
            ColorsBar.setVisibility(View.INVISIBLE);
            Room2Bulb.setImageResource(R.drawable.baseline_lightbulb_200_black);
        }
        else if(LIGHT_ON == light_state) {
            ColorsBar.setVisibility(View.VISIBLE);
//            Room1Bulb.setImageResource(R.drawable.baseline_lightbulb_200_red);
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) RedButton.getLayoutParams();
//            params.weight = 0.0f;
//            RedButton.setLayoutParams(params);
        }

        Room2Bulb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LIGHT_OFF == light_state) {
                    light_state = LIGHT_ON;
                    ColorsBar.setVisibility(View.VISIBLE);
                    setColorBulb();
                    getActiveColorButton();
                }
                else if(LIGHT_ON == light_state) {
                    Room2Bulb.setImageResource(R.drawable.baseline_lightbulb_200_black);
                    light_state = LIGHT_OFF;
                    ColorsBar.setVisibility(View.INVISIBLE);
                }
            }
        });

        RedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentButton = RedButton;
                setColorBulb();
                getActiveColorButton();
            }
        });

        YellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentButton = YellowButton;
                setColorBulb();
                getActiveColorButton();
            }
        });

        OrangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentButton = OrangeButton;
                setColorBulb();
                getActiveColorButton();
            }
        });

        AquaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentButton = AquaButton;
                setColorBulb();
                getActiveColorButton();
            }
        });

        BlueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentButton = BlueButton;
                setColorBulb();
                getActiveColorButton();
            }
        });

        GreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentButton = GreenButton;
                setColorBulb();
                getActiveColorButton();
            }
        });

        PinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentButton = PinkButton;
                setColorBulb();
                getActiveColorButton();
            }
        });

        PurpleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentButton = PurpleButton;
                setColorBulb();
                getActiveColorButton();
            }
        });
    }
    void setColorBulb() {
        if(CurrentButton == RedButton) {
            Room2Bulb.setImageResource(R.drawable.baseline_lightbulb_200_red);
        }
        if(CurrentButton == YellowButton) {
            Room2Bulb.setImageResource(R.drawable.baseline_lightbulb_200_yellow);
        }
        if(CurrentButton == OrangeButton) {
            Room2Bulb.setImageResource(R.drawable.baseline_lightbulb_200_orange);
        }
        if(CurrentButton == AquaButton) {
            Room2Bulb.setImageResource(R.drawable.baseline_lightbulb_200_aqua);
        }
        if(CurrentButton == BlueButton) {
            Room2Bulb.setImageResource(R.drawable.baseline_lightbulb_200_blue);
        }
        if(CurrentButton == GreenButton) {
            Room2Bulb.setImageResource(R.drawable.baseline_lightbulb_200_green);
        }
        if(CurrentButton == PinkButton) {
            Room2Bulb.setImageResource(R.drawable.baseline_lightbulb_200_pink);
        }
        if(CurrentButton == PurpleButton) {
            Room2Bulb.setImageResource(R.drawable.baseline_lightbulb_200_purple);
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
}
