package com.example.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class HomeAmbientActivity extends AppCompat {

    LinearLayout TemperatureInsideLayout;
    LinearLayout HumidityInsideLayout;
    LinearLayout TemperatureOutsideLayout;
    LinearLayout HumidityOutsideLayout;
    LinearLayout AirQualityLayout;
    LinearLayout GasLayout;

    TextView TemperatureInsideHiddenLayout;
    TextView HumidityInsideHiddenLayout;
    TextView TemperatureOutsideHiddenLayout;
    TextView HumidityOutsideHiddenLayout;
    TextView AirQualityHiddenLayout;
    TextView GasHiddenLayout;

    TextView tempInsideTextView;

    private float temperature_inside;
    private int humidity_inside;
    private float temperature_outside;
    private int humidity_outside;
    private int air_quality;
    private int co2;

    private Handler mHandler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeambient);

        Window window = HomeAmbientActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(HomeAmbientActivity.this, R.color.blue_background));
        window.setNavigationBarColor(ContextCompat.getColor(HomeAmbientActivity.this, R.color.blue_navigation_background));

        TemperatureInsideLayout = (LinearLayout) findViewById(R.id.temp_inside_layout);
        HumidityInsideLayout = (LinearLayout) findViewById(R.id.hum_inside_layout);
        TemperatureOutsideLayout = (LinearLayout) findViewById(R.id.temp_outside_layout);
        HumidityOutsideLayout = (LinearLayout) findViewById(R.id.hum_outside_layout);
        AirQualityLayout = (LinearLayout) findViewById(R.id.air_quality_layout);
        GasLayout = (LinearLayout) findViewById(R.id.gas_layout);

        TemperatureInsideHiddenLayout = (TextView) findViewById(R.id.temp_inside_hidden);
        HumidityInsideHiddenLayout = (TextView) findViewById(R.id.hum_inside_hidden);
        TemperatureOutsideHiddenLayout = (TextView) findViewById(R.id.temp_outside_hidden);
        HumidityOutsideHiddenLayout = (TextView) findViewById(R.id.hum_outside_hidden);
        AirQualityHiddenLayout = (TextView) findViewById(R.id.air_quality_hidden);
        GasHiddenLayout = (TextView) findViewById(R.id.gas_hidden);

        tempInsideTextView = (TextView) findViewById(R.id.temp_inside_text);

        if(BufferManager.currentLanguage.equals("ru")) {
            tempInsideTextView.setTextSize(26);
        }

        temperature_inside = (float)((BufferManager.RxBuffer[3] & 0xFF) + ((BufferManager.RxBuffer[4] & 0xFF) * 0.1));
        temperature_outside = (float)((BufferManager.RxBuffer[6] & 0xFF) + ((BufferManager.RxBuffer[7] & 0xFF) * 0.1));
        humidity_inside = BufferManager.RxBuffer[5] & 0xFF;
        humidity_outside = BufferManager.RxBuffer[8] & 0xFF;
        air_quality = ((BufferManager.RxBuffer[9] & 0xFF) * 256) + (BufferManager.RxBuffer[13] & 0xFF);
        co2 = ((BufferManager.RxBuffer[10] & 0xFF) * 256) + (BufferManager.RxBuffer[14] & 0xFF);

        if(BufferManager.TxBuffer[30] == 0) {
            TemperatureInsideHiddenLayout.setText(String.valueOf(temperature_inside) + " °C");
            TemperatureOutsideHiddenLayout.setText(String.valueOf(temperature_outside) + " °C");
        }
        else if(BufferManager.TxBuffer[30] == 1)
        {
            TemperatureInsideHiddenLayout.setText(String.valueOf(temperature_inside) + " °F");
            TemperatureOutsideHiddenLayout.setText(String.valueOf(temperature_outside) + " °F");
        }
        HumidityInsideHiddenLayout.setText(String.valueOf(humidity_inside) + " %");
        HumidityOutsideHiddenLayout.setText(String.valueOf(humidity_outside) + " %");
        AirQualityHiddenLayout.setText(String.valueOf(air_quality) + " ppm");
        GasHiddenLayout.setText(String.valueOf(co2) + " ppm");

        TemperatureInsideLayout.setVisibility(View.VISIBLE);
        HumidityInsideHiddenLayout.setVisibility(View.INVISIBLE);
        TemperatureOutsideHiddenLayout.setVisibility(View.INVISIBLE);
        HumidityOutsideHiddenLayout.setVisibility(View.INVISIBLE);
        AirQualityHiddenLayout.setVisibility(View.INVISIBLE);
        GasHiddenLayout.setVisibility(View.INVISIBLE);

        mHandler = new Handler();
        startRepeatingTask();

        TemperatureInsideLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TemperatureInsideHiddenLayout.setVisibility(View.VISIBLE);
                HumidityInsideHiddenLayout.setVisibility(View.INVISIBLE);
                TemperatureOutsideHiddenLayout.setVisibility(View.INVISIBLE);
                HumidityOutsideHiddenLayout.setVisibility(View.INVISIBLE);
                AirQualityHiddenLayout.setVisibility(View.INVISIBLE);
                GasHiddenLayout.setVisibility(View.INVISIBLE);

                LinearLayout.LayoutParams params_11 = (LinearLayout.LayoutParams) TemperatureInsideHiddenLayout.getLayoutParams();
                params_11.weight = 1.3f;
                TemperatureInsideHiddenLayout.setLayoutParams(params_11);

                LinearLayout.LayoutParams params_12 = (LinearLayout.LayoutParams) HumidityInsideHiddenLayout.getLayoutParams();
                params_12.weight = 99.0f;
                HumidityInsideHiddenLayout.setLayoutParams(params_12);

                LinearLayout.LayoutParams params_13 = (LinearLayout.LayoutParams) TemperatureOutsideHiddenLayout.getLayoutParams();
                params_13.weight = 99.0f;
                TemperatureOutsideHiddenLayout.setLayoutParams(params_13);

                LinearLayout.LayoutParams params_14 = (LinearLayout.LayoutParams) HumidityOutsideHiddenLayout.getLayoutParams();
                params_14.weight = 99.0f;
                HumidityOutsideHiddenLayout.setLayoutParams(params_14);

                LinearLayout.LayoutParams params_15 = (LinearLayout.LayoutParams) AirQualityHiddenLayout.getLayoutParams();
                params_15.weight = 99.0f;
                AirQualityHiddenLayout.setLayoutParams(params_15);

                LinearLayout.LayoutParams params_16 = (LinearLayout.LayoutParams) GasHiddenLayout.getLayoutParams();
                params_16.weight = 99.0f;
                GasHiddenLayout.setLayoutParams(params_16);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) TemperatureInsideLayout.getLayoutParams();
                params.weight = 0.8f;
                TemperatureInsideLayout.setLayoutParams(params);

                LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) HumidityInsideLayout.getLayoutParams();
                params_1.weight = 1.0f;
                HumidityInsideLayout.setLayoutParams(params_1);

                LinearLayout.LayoutParams params_2 = (LinearLayout.LayoutParams) TemperatureOutsideLayout.getLayoutParams();
                params_2.weight = 1.0f;
                TemperatureOutsideLayout.setLayoutParams(params_2);

                LinearLayout.LayoutParams params_3 = (LinearLayout.LayoutParams) HumidityOutsideLayout.getLayoutParams();
                params_3.weight = 1.0f;
                HumidityOutsideLayout.setLayoutParams(params_3);

                LinearLayout.LayoutParams params_4 = (LinearLayout.LayoutParams) AirQualityLayout.getLayoutParams();
                params_4.weight = 1.0f;
                AirQualityLayout.setLayoutParams(params_4);

                LinearLayout.LayoutParams params_5 = (LinearLayout.LayoutParams) GasLayout.getLayoutParams();
                params_5.weight = 1.0f;
                GasLayout.setLayoutParams(params_5);
            }
        });

        HumidityInsideLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TemperatureInsideHiddenLayout.setVisibility(View.INVISIBLE);
                HumidityInsideHiddenLayout.setVisibility(View.VISIBLE);
                TemperatureOutsideHiddenLayout.setVisibility(View.INVISIBLE);
                HumidityOutsideHiddenLayout.setVisibility(View.INVISIBLE);
                AirQualityHiddenLayout.setVisibility(View.INVISIBLE);
                GasHiddenLayout.setVisibility(View.INVISIBLE);

                LinearLayout.LayoutParams params_11 = (LinearLayout.LayoutParams) TemperatureInsideHiddenLayout.getLayoutParams();
                params_11.weight = 99.0f;
                TemperatureInsideHiddenLayout.setLayoutParams(params_11);

                LinearLayout.LayoutParams params_12 = (LinearLayout.LayoutParams) HumidityInsideHiddenLayout.getLayoutParams();
                params_12.weight = 1.3f;
                HumidityInsideHiddenLayout.setLayoutParams(params_12);

                LinearLayout.LayoutParams params_13 = (LinearLayout.LayoutParams) TemperatureOutsideHiddenLayout.getLayoutParams();
                params_13.weight = 99.0f;
                TemperatureOutsideHiddenLayout.setLayoutParams(params_13);

                LinearLayout.LayoutParams params_14 = (LinearLayout.LayoutParams) HumidityOutsideHiddenLayout.getLayoutParams();
                params_14.weight = 99.0f;
                HumidityOutsideHiddenLayout.setLayoutParams(params_14);

                LinearLayout.LayoutParams params_15 = (LinearLayout.LayoutParams) AirQualityHiddenLayout.getLayoutParams();
                params_15.weight = 99.0f;
                AirQualityHiddenLayout.setLayoutParams(params_15);

                LinearLayout.LayoutParams params_16 = (LinearLayout.LayoutParams) GasHiddenLayout.getLayoutParams();
                params_16.weight = 99.0f;
                GasHiddenLayout.setLayoutParams(params_16);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) TemperatureInsideLayout.getLayoutParams();
                params.weight = 1.0f;
                TemperatureInsideLayout.setLayoutParams(params);

                LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) HumidityInsideLayout.getLayoutParams();
                params_1.weight = 0.8f;
                HumidityInsideLayout.setLayoutParams(params_1);

                LinearLayout.LayoutParams params_2 = (LinearLayout.LayoutParams) TemperatureOutsideLayout.getLayoutParams();
                params_2.weight = 1.0f;
                TemperatureOutsideLayout.setLayoutParams(params_2);

                LinearLayout.LayoutParams params_3 = (LinearLayout.LayoutParams) HumidityOutsideLayout.getLayoutParams();
                params_3.weight = 1.0f;
                HumidityOutsideLayout.setLayoutParams(params_3);

                LinearLayout.LayoutParams params_4 = (LinearLayout.LayoutParams) AirQualityLayout.getLayoutParams();
                params_4.weight = 1.0f;
                AirQualityLayout.setLayoutParams(params_4);

                LinearLayout.LayoutParams params_5 = (LinearLayout.LayoutParams) GasLayout.getLayoutParams();
                params_5.weight = 1.0f;
                GasLayout.setLayoutParams(params_5);
            }
        });

        TemperatureOutsideLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TemperatureInsideHiddenLayout.setVisibility(View.INVISIBLE);
                HumidityInsideHiddenLayout.setVisibility(View.INVISIBLE);
                TemperatureOutsideHiddenLayout.setVisibility(View.VISIBLE);
                HumidityOutsideHiddenLayout.setVisibility(View.INVISIBLE);
                AirQualityHiddenLayout.setVisibility(View.INVISIBLE);
                GasHiddenLayout.setVisibility(View.INVISIBLE);

                LinearLayout.LayoutParams params_11 = (LinearLayout.LayoutParams) TemperatureInsideHiddenLayout.getLayoutParams();
                params_11.weight = 99.0f;
                TemperatureInsideHiddenLayout.setLayoutParams(params_11);

                LinearLayout.LayoutParams params_12 = (LinearLayout.LayoutParams) HumidityInsideHiddenLayout.getLayoutParams();
                params_12.weight = 99.0f;
                HumidityInsideHiddenLayout.setLayoutParams(params_12);

                LinearLayout.LayoutParams params_13 = (LinearLayout.LayoutParams) TemperatureOutsideHiddenLayout.getLayoutParams();
                params_13.weight = 1.3f;
                TemperatureOutsideHiddenLayout.setLayoutParams(params_13);

                LinearLayout.LayoutParams params_14 = (LinearLayout.LayoutParams) HumidityOutsideHiddenLayout.getLayoutParams();
                params_14.weight = 99.0f;
                HumidityOutsideHiddenLayout.setLayoutParams(params_14);

                LinearLayout.LayoutParams params_15 = (LinearLayout.LayoutParams) AirQualityHiddenLayout.getLayoutParams();
                params_15.weight = 99.0f;
                AirQualityHiddenLayout.setLayoutParams(params_15);

                LinearLayout.LayoutParams params_16 = (LinearLayout.LayoutParams) GasHiddenLayout.getLayoutParams();
                params_16.weight = 99.0f;
                GasHiddenLayout.setLayoutParams(params_16);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) TemperatureInsideLayout.getLayoutParams();
                params.weight = 1.0f;
                TemperatureInsideLayout.setLayoutParams(params);

                LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) HumidityInsideLayout.getLayoutParams();
                params_1.weight = 1.0f;
                HumidityInsideLayout.setLayoutParams(params_1);

                LinearLayout.LayoutParams params_2 = (LinearLayout.LayoutParams) TemperatureOutsideLayout.getLayoutParams();
                params_2.weight = 0.8f;
                TemperatureOutsideLayout.setLayoutParams(params_2);

                LinearLayout.LayoutParams params_3 = (LinearLayout.LayoutParams) HumidityOutsideLayout.getLayoutParams();
                params_3.weight = 1.0f;
                HumidityOutsideLayout.setLayoutParams(params_3);

                LinearLayout.LayoutParams params_4 = (LinearLayout.LayoutParams) AirQualityLayout.getLayoutParams();
                params_4.weight = 1.0f;
                AirQualityLayout.setLayoutParams(params_4);

                LinearLayout.LayoutParams params_5 = (LinearLayout.LayoutParams) GasLayout.getLayoutParams();
                params_5.weight = 1.0f;
                GasLayout.setLayoutParams(params_5);
            }
        });

        HumidityOutsideLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TemperatureInsideHiddenLayout.setVisibility(View.INVISIBLE);
                HumidityInsideHiddenLayout.setVisibility(View.INVISIBLE);
                TemperatureOutsideHiddenLayout.setVisibility(View.INVISIBLE);
                HumidityOutsideHiddenLayout.setVisibility(View.VISIBLE);
                AirQualityHiddenLayout.setVisibility(View.INVISIBLE);
                GasHiddenLayout.setVisibility(View.INVISIBLE);

                LinearLayout.LayoutParams params_11 = (LinearLayout.LayoutParams) TemperatureInsideHiddenLayout.getLayoutParams();
                params_11.weight = 99.0f;
                TemperatureInsideHiddenLayout.setLayoutParams(params_11);

                LinearLayout.LayoutParams params_12 = (LinearLayout.LayoutParams) HumidityInsideHiddenLayout.getLayoutParams();
                params_12.weight = 99.0f;
                HumidityInsideHiddenLayout.setLayoutParams(params_12);

                LinearLayout.LayoutParams params_13 = (LinearLayout.LayoutParams) TemperatureOutsideHiddenLayout.getLayoutParams();
                params_13.weight = 99.0f;
                TemperatureOutsideHiddenLayout.setLayoutParams(params_13);

                LinearLayout.LayoutParams params_14 = (LinearLayout.LayoutParams) HumidityOutsideHiddenLayout.getLayoutParams();
                params_14.weight = 1.3f;
                HumidityOutsideHiddenLayout.setLayoutParams(params_14);

                LinearLayout.LayoutParams params_15 = (LinearLayout.LayoutParams) AirQualityHiddenLayout.getLayoutParams();
                params_15.weight = 99.0f;
                AirQualityHiddenLayout.setLayoutParams(params_15);

                LinearLayout.LayoutParams params_16 = (LinearLayout.LayoutParams) GasHiddenLayout.getLayoutParams();
                params_16.weight = 99.0f;
                GasHiddenLayout.setLayoutParams(params_16);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) TemperatureInsideLayout.getLayoutParams();
                params.weight = 1.0f;
                TemperatureInsideLayout.setLayoutParams(params);

                LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) HumidityInsideLayout.getLayoutParams();
                params_1.weight = 1.0f;
                HumidityInsideLayout.setLayoutParams(params_1);

                LinearLayout.LayoutParams params_2 = (LinearLayout.LayoutParams) TemperatureOutsideLayout.getLayoutParams();
                params_2.weight = 1.0f;
                TemperatureOutsideLayout.setLayoutParams(params_2);

                LinearLayout.LayoutParams params_3 = (LinearLayout.LayoutParams) HumidityOutsideLayout.getLayoutParams();
                params_3.weight = 0.8f;
                HumidityOutsideLayout.setLayoutParams(params_3);

                LinearLayout.LayoutParams params_4 = (LinearLayout.LayoutParams) AirQualityLayout.getLayoutParams();
                params_4.weight = 1.0f;
                AirQualityLayout.setLayoutParams(params_4);

                LinearLayout.LayoutParams params_5 = (LinearLayout.LayoutParams) GasLayout.getLayoutParams();
                params_5.weight = 1.0f;
                GasLayout.setLayoutParams(params_5);
            }
        });

        AirQualityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TemperatureInsideHiddenLayout.setVisibility(View.INVISIBLE);
                HumidityInsideHiddenLayout.setVisibility(View.INVISIBLE);
                TemperatureOutsideHiddenLayout.setVisibility(View.INVISIBLE);
                HumidityOutsideHiddenLayout.setVisibility(View.INVISIBLE);
                AirQualityHiddenLayout.setVisibility(View.VISIBLE);
                GasHiddenLayout.setVisibility(View.INVISIBLE);

                LinearLayout.LayoutParams params_11 = (LinearLayout.LayoutParams) TemperatureInsideHiddenLayout.getLayoutParams();
                params_11.weight = 99.0f;
                TemperatureInsideHiddenLayout.setLayoutParams(params_11);

                LinearLayout.LayoutParams params_12 = (LinearLayout.LayoutParams) HumidityInsideHiddenLayout.getLayoutParams();
                params_12.weight = 99.0f;
                HumidityInsideHiddenLayout.setLayoutParams(params_12);

                LinearLayout.LayoutParams params_13 = (LinearLayout.LayoutParams) TemperatureOutsideHiddenLayout.getLayoutParams();
                params_13.weight = 99.0f;
                TemperatureOutsideHiddenLayout.setLayoutParams(params_13);

                LinearLayout.LayoutParams params_14 = (LinearLayout.LayoutParams) HumidityOutsideHiddenLayout.getLayoutParams();
                params_14.weight = 99.0f;
                HumidityOutsideHiddenLayout.setLayoutParams(params_14);

                LinearLayout.LayoutParams params_15 = (LinearLayout.LayoutParams) AirQualityHiddenLayout.getLayoutParams();
                params_15.weight = 1.3f;
                AirQualityHiddenLayout.setLayoutParams(params_15);

                LinearLayout.LayoutParams params_16 = (LinearLayout.LayoutParams) GasHiddenLayout.getLayoutParams();
                params_16.weight = 99.0f;
                GasHiddenLayout.setLayoutParams(params_16);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) TemperatureInsideLayout.getLayoutParams();
                params.weight = 1.0f;
                TemperatureInsideLayout.setLayoutParams(params);

                LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) HumidityInsideLayout.getLayoutParams();
                params_1.weight = 1.0f;
                HumidityInsideLayout.setLayoutParams(params_1);

                LinearLayout.LayoutParams params_2 = (LinearLayout.LayoutParams) TemperatureOutsideLayout.getLayoutParams();
                params_2.weight = 1.0f;
                TemperatureOutsideLayout.setLayoutParams(params_2);

                LinearLayout.LayoutParams params_3 = (LinearLayout.LayoutParams) HumidityOutsideLayout.getLayoutParams();
                params_3.weight = 1.0f;
                HumidityOutsideLayout.setLayoutParams(params_3);

                LinearLayout.LayoutParams params_4 = (LinearLayout.LayoutParams) AirQualityLayout.getLayoutParams();
                params_4.weight = 0.8f;
                AirQualityLayout.setLayoutParams(params_4);

                LinearLayout.LayoutParams params_5 = (LinearLayout.LayoutParams) GasLayout.getLayoutParams();
                params_5.weight = 1.0f;
                GasLayout.setLayoutParams(params_5);
            }
        });

        GasLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TemperatureInsideHiddenLayout.setVisibility(View.INVISIBLE);
                HumidityInsideHiddenLayout.setVisibility(View.INVISIBLE);
                TemperatureOutsideHiddenLayout.setVisibility(View.INVISIBLE);
                HumidityOutsideHiddenLayout.setVisibility(View.INVISIBLE);
                AirQualityHiddenLayout.setVisibility(View.INVISIBLE);
                GasHiddenLayout.setVisibility(View.VISIBLE);

                LinearLayout.LayoutParams params_11 = (LinearLayout.LayoutParams) TemperatureInsideHiddenLayout.getLayoutParams();
                params_11.weight = 99.0f;
                TemperatureInsideHiddenLayout.setLayoutParams(params_11);

                LinearLayout.LayoutParams params_12 = (LinearLayout.LayoutParams) HumidityInsideHiddenLayout.getLayoutParams();
                params_12.weight = 99.0f;
                HumidityInsideHiddenLayout.setLayoutParams(params_12);

                LinearLayout.LayoutParams params_13 = (LinearLayout.LayoutParams) TemperatureOutsideHiddenLayout.getLayoutParams();
                params_13.weight = 99.0f;
                TemperatureOutsideHiddenLayout.setLayoutParams(params_13);

                LinearLayout.LayoutParams params_14 = (LinearLayout.LayoutParams) HumidityOutsideHiddenLayout.getLayoutParams();
                params_14.weight = 99.0f;
                HumidityOutsideHiddenLayout.setLayoutParams(params_14);

                LinearLayout.LayoutParams params_15 = (LinearLayout.LayoutParams) AirQualityHiddenLayout.getLayoutParams();
                params_15.weight = 99.0f;
                AirQualityHiddenLayout.setLayoutParams(params_15);

                LinearLayout.LayoutParams params_16 = (LinearLayout.LayoutParams) GasHiddenLayout.getLayoutParams();
                params_16.weight = 1.3f;
                GasHiddenLayout.setLayoutParams(params_16);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) TemperatureInsideLayout.getLayoutParams();
                params.weight = 1.0f;
                TemperatureInsideLayout.setLayoutParams(params);

                LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) HumidityInsideLayout.getLayoutParams();
                params_1.weight = 1.0f;
                HumidityInsideLayout.setLayoutParams(params_1);

                LinearLayout.LayoutParams params_2 = (LinearLayout.LayoutParams) TemperatureOutsideLayout.getLayoutParams();
                params_2.weight = 1.0f;
                TemperatureOutsideLayout.setLayoutParams(params_2);

                LinearLayout.LayoutParams params_3 = (LinearLayout.LayoutParams) HumidityOutsideLayout.getLayoutParams();
                params_3.weight = 1.0f;
                HumidityOutsideLayout.setLayoutParams(params_3);

                LinearLayout.LayoutParams params_4 = (LinearLayout.LayoutParams) AirQualityLayout.getLayoutParams();
                params_4.weight = 1.0f;
                AirQualityLayout.setLayoutParams(params_4);

                LinearLayout.LayoutParams params_5 = (LinearLayout.LayoutParams) GasLayout.getLayoutParams();
                params_5.weight = 0.8f;
                GasLayout.setLayoutParams(params_5);
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
                if((BufferManager.RxBuffer[0] & 0xFF) == BufferManager.SOB && (BufferManager.RxBuffer[48] & 0xFF) == BufferManager.EOB) {
                    temperature_inside = (float) ((BufferManager.RxBuffer[3] & 0xFF) + ((BufferManager.RxBuffer[4] & 0xFF) * 0.1));
                    temperature_outside = (float) ((BufferManager.RxBuffer[6] & 0xFF) + ((BufferManager.RxBuffer[7] & 0xFF) * 0.1));
                    humidity_inside = BufferManager.RxBuffer[5] & 0xFF;
                    humidity_outside = BufferManager.RxBuffer[8] & 0xFF;
                    air_quality = ((BufferManager.RxBuffer[9] & 0xFF) * 256) + (BufferManager.RxBuffer[13] & 0xFF);
                    co2 = ((BufferManager.RxBuffer[10] & 0xFF) * 256) + (BufferManager.RxBuffer[14] & 0xFF);
                }

                if(BufferManager.TxBuffer[30] == 0) {
                    TemperatureInsideHiddenLayout.setText(String.valueOf(temperature_inside) + " °C");
                    TemperatureOutsideHiddenLayout.setText(String.valueOf(temperature_outside) + " °C");
                }
                else if(BufferManager.TxBuffer[30] == 1)
                {
                    TemperatureInsideHiddenLayout.setText(String.valueOf(temperature_inside) + " °F");
                    TemperatureOutsideHiddenLayout.setText(String.valueOf(temperature_outside) + " °F");
                }
                HumidityInsideHiddenLayout.setText(String.valueOf(humidity_inside) + " %");
                HumidityOutsideHiddenLayout.setText(String.valueOf(humidity_outside) + " %");
                AirQualityHiddenLayout.setText(String.valueOf(air_quality) + " ppm");
                GasHiddenLayout.setText(String.valueOf(co2) + " ppm");
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
