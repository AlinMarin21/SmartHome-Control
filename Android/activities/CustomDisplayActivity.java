package com.example.home;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class CustomDisplayActivity extends AppCompat {

    static final int SWITCH_ON = 1;
    static final int SWITCH_OFF = 0;

    Switch InsideTemperatureSwitch;
    Switch OutsideTemperatureSwitch;
    Switch InsideHumiditySwitch;
    Switch OutsideHumiditySwitch;
    Switch AirQualitySwitch;
    Switch GasSwitch;
    Switch RainfallSwitch;
    Switch BrightnessSwitch;
    Switch MotionSwitch;

    TextView titleTextView;
    TextView infoTextView;

    int inside_temp_switch_state = SWITCH_ON;
    int outside_temp_switch_state = SWITCH_ON;
    int inside_hum_switch_state = SWITCH_ON;
    int outside_hum_switch_state = SWITCH_ON;
    int air_quality_switch_state = SWITCH_ON;
    int gas_switch_state = SWITCH_OFF;
    int rainfall_switch_state = SWITCH_OFF;
    int brightness_switch_state = SWITCH_OFF;
    int motion_switch_state = SWITCH_OFF;

    int no_of_selections = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customdisplay);

        Window window = CustomDisplayActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(CustomDisplayActivity.this, R.color.purple_background));
        window.setNavigationBarColor(ContextCompat.getColor(CustomDisplayActivity.this, R.color.purple_navigation_background));

        InsideTemperatureSwitch = (Switch) findViewById(R.id.temp_inside_switch);
        OutsideTemperatureSwitch = (Switch) findViewById(R.id.temp_outside_switch);
        InsideHumiditySwitch = (Switch) findViewById(R.id.hum_inside_switch);
        OutsideHumiditySwitch = (Switch) findViewById(R.id.hum_outside_switch);
        AirQualitySwitch = (Switch) findViewById(R.id.air_quality_switch);
        GasSwitch = (Switch) findViewById(R.id.gas_switch);
        RainfallSwitch = (Switch) findViewById(R.id.rainfall_switch);
        BrightnessSwitch = (Switch) findViewById(R.id.brightness_switch);
        MotionSwitch = (Switch) findViewById(R.id.motion_switch);

        titleTextView = (TextView) findViewById(R.id.title_textview);
        infoTextView = (TextView) findViewById(R.id.info_textview);

        if(BufferManager.currentLanguage.equals("fr")
                || BufferManager.currentLanguage.equals("it")
                || BufferManager.currentLanguage.equals("de")
                || BufferManager.currentLanguage.equals("ru")) {
            titleTextView.setTextSize(26);
            infoTextView.setTextSize(18);
        }
        if(BufferManager.currentLanguage.equals("es")) {
            titleTextView.setTextSize(26);
        }

        if(SWITCH_ON == inside_temp_switch_state) {
            InsideTemperatureSwitch.setChecked(true);
            InsideTemperatureSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.purple_navigation_background)));
            no_of_selections++;
        }
        else {
            InsideTemperatureSwitch.setChecked(false);
            InsideTemperatureSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
        }

        if(SWITCH_ON == outside_temp_switch_state) {
            OutsideTemperatureSwitch.setChecked(true);
            OutsideTemperatureSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.purple_navigation_background)));
            no_of_selections++;
        }
        else {
            OutsideTemperatureSwitch.setChecked(false);
            OutsideTemperatureSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
        }

        if(SWITCH_ON == inside_hum_switch_state) {
            InsideHumiditySwitch.setChecked(true);
            InsideHumiditySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.purple_navigation_background)));
            no_of_selections++;
        }
        else {
            InsideHumiditySwitch.setChecked(false);
            InsideHumiditySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
        }

        if(SWITCH_ON == outside_hum_switch_state) {
            OutsideHumiditySwitch.setChecked(true);
            OutsideHumiditySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.purple_navigation_background)));
            no_of_selections++;
        }
        else {
            OutsideHumiditySwitch.setChecked(false);
            OutsideHumiditySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
        }

        if(SWITCH_ON == air_quality_switch_state) {
            AirQualitySwitch.setChecked(true);
            AirQualitySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.purple_navigation_background)));
            no_of_selections++;
        }
        else {
            AirQualitySwitch.setChecked(false);
            AirQualitySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
        }

        if(SWITCH_ON == gas_switch_state) {
            GasSwitch.setChecked(true);
            GasSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.purple_navigation_background)));
            no_of_selections++;
        }
        else {
            GasSwitch.setChecked(false);
            GasSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
        }

        if(SWITCH_ON == rainfall_switch_state) {
            RainfallSwitch.setChecked(true);
            RainfallSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.purple_navigation_background)));
            no_of_selections++;
        }
        else {
            RainfallSwitch.setChecked(false);
            RainfallSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
        }

        if(SWITCH_ON == brightness_switch_state) {
            BrightnessSwitch.setChecked(true);
            BrightnessSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.purple_navigation_background)));
            no_of_selections++;
        }
        else {
            BrightnessSwitch.setChecked(false);
            BrightnessSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
        }

        if(SWITCH_ON == motion_switch_state) {
            MotionSwitch.setChecked(true);
            MotionSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.purple_navigation_background)));
            no_of_selections++;
        }
        else {
            MotionSwitch.setChecked(false);
            MotionSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
        }

        InsideTemperatureSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SWITCH_OFF == inside_temp_switch_state) {
                    if(no_of_selections < 5) {
                        InsideTemperatureSwitch.setChecked(true);
                        InsideTemperatureSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.purple_navigation_background)));
                        inside_temp_switch_state = SWITCH_ON;
                        no_of_selections++;
                    }
                    else {
                        InsideTemperatureSwitch.setChecked(false);
                        InsideTemperatureSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
                        Toast.makeText(getBaseContext(), R.string.max_limit_text, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    InsideTemperatureSwitch.setChecked(false);
                    InsideTemperatureSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
                    inside_temp_switch_state = SWITCH_OFF;
                    no_of_selections--;
                }
            }
        });

        OutsideTemperatureSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SWITCH_OFF == outside_temp_switch_state) {
                    if(no_of_selections < 5) {
                        OutsideTemperatureSwitch.setChecked(true);
                        OutsideTemperatureSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.purple_navigation_background)));
                        outside_temp_switch_state = SWITCH_ON;
                        no_of_selections++;
                    }
                    else {
                        OutsideTemperatureSwitch.setChecked(false);
                        OutsideTemperatureSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
                        Toast.makeText(getBaseContext(), R.string.max_limit_text, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    OutsideTemperatureSwitch.setChecked(false);
                    OutsideTemperatureSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
                    outside_temp_switch_state = SWITCH_OFF;
                    no_of_selections--;
                }
            }
        });

        InsideHumiditySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SWITCH_OFF == inside_hum_switch_state) {
                    if(no_of_selections < 5) {
                        InsideHumiditySwitch.setChecked(true);
                        InsideHumiditySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.purple_navigation_background)));
                        inside_hum_switch_state = SWITCH_ON;
                        no_of_selections++;
                    }
                    else {
                        InsideHumiditySwitch.setChecked(false);
                        InsideHumiditySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
                        Toast.makeText(getBaseContext(), R.string.max_limit_text, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    InsideHumiditySwitch.setChecked(false);
                    InsideHumiditySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
                    inside_hum_switch_state = SWITCH_OFF;
                    no_of_selections--;
                }
            }
        });

        OutsideHumiditySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SWITCH_OFF == outside_hum_switch_state) {
                    if(no_of_selections < 5) {
                        OutsideHumiditySwitch.setChecked(true);
                        OutsideHumiditySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.purple_navigation_background)));
                        outside_hum_switch_state = SWITCH_ON;
                        no_of_selections++;
                    }
                    else {
                        OutsideHumiditySwitch.setChecked(false);
                        OutsideHumiditySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
                        Toast.makeText(getBaseContext(), R.string.max_limit_text, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    OutsideHumiditySwitch.setChecked(false);
                    OutsideHumiditySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
                    outside_hum_switch_state = SWITCH_OFF;
                    no_of_selections--;
                }
            }
        });

        AirQualitySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SWITCH_OFF == air_quality_switch_state) {
                    if(no_of_selections < 5) {
                        AirQualitySwitch.setChecked(true);
                        AirQualitySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.purple_navigation_background)));
                        air_quality_switch_state = SWITCH_ON;
                        no_of_selections++;
                    }
                    else {
                        AirQualitySwitch.setChecked(false);
                        AirQualitySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
                        Toast.makeText(getBaseContext(), R.string.max_limit_text, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    AirQualitySwitch.setChecked(false);
                    AirQualitySwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
                    air_quality_switch_state = SWITCH_OFF;
                    no_of_selections--;
                }
            }
        });

        GasSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SWITCH_OFF == gas_switch_state) {
                    if(no_of_selections < 5) {
                        GasSwitch.setChecked(true);
                        GasSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.purple_navigation_background)));
                        gas_switch_state = SWITCH_ON;
                        no_of_selections++;
                    }
                    else {
                        GasSwitch.setChecked(false);
                        GasSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
                        Toast.makeText(getBaseContext(), R.string.max_limit_text, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    GasSwitch.setChecked(false);
                    GasSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
                    gas_switch_state = SWITCH_OFF;
                    no_of_selections--;
                }
            }
        });

        RainfallSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SWITCH_OFF == rainfall_switch_state) {
                    if(no_of_selections < 5) {
                        RainfallSwitch.setChecked(true);
                        RainfallSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.purple_navigation_background)));
                        rainfall_switch_state = SWITCH_ON;
                        no_of_selections++;
                    }
                    else {
                        RainfallSwitch.setChecked(false);
                        RainfallSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
                        Toast.makeText(getBaseContext(), R.string.max_limit_text, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    RainfallSwitch.setChecked(false);
                    RainfallSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
                    rainfall_switch_state = SWITCH_OFF;
                    no_of_selections--;
                }
            }
        });

        BrightnessSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SWITCH_OFF == brightness_switch_state) {
                    if(no_of_selections < 5) {
                        BrightnessSwitch.setChecked(true);
                        BrightnessSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.purple_navigation_background)));
                        brightness_switch_state = SWITCH_ON;
                        no_of_selections++;
                    }
                    else {
                        BrightnessSwitch.setChecked(false);
                        BrightnessSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
                        Toast.makeText(getBaseContext(), R.string.max_limit_text, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    BrightnessSwitch.setChecked(false);
                    BrightnessSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
                    brightness_switch_state = SWITCH_OFF;
                    no_of_selections--;
                }
            }
        });

        MotionSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SWITCH_OFF == motion_switch_state) {
                    if(no_of_selections < 5) {
                        MotionSwitch.setChecked(true);
                        MotionSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.purple_navigation_background)));
                        motion_switch_state = SWITCH_ON;
                        no_of_selections++;
                    }
                    else {
                        MotionSwitch.setChecked(false);
                        MotionSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
                        Toast.makeText(getBaseContext(), R.string.max_limit_text, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    MotionSwitch.setChecked(false);
                    MotionSwitch.setTrackTintList(ColorStateList.valueOf(ContextCompat.getColor(CustomDisplayActivity.this, R.color.gray_navigation_background)));
                    motion_switch_state = SWITCH_OFF;
                    no_of_selections--;
                }
            }
        });
    }
}
