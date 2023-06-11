package com.example.home;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

public class MeasurementUnitsActivity extends AppCompat {

    private static final int CELSIUS_UNIT = 0;
    private static final int FAHRENHEIT_UNIT = 1;

    private TextView CelsiusLayout;
    private TextView FahrenheitLayout;

    private int temperature_unit = CELSIUS_UNIT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurementunits);

        Window window = MeasurementUnitsActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(MeasurementUnitsActivity.this, R.color.purple_background));
        window.setNavigationBarColor(ContextCompat.getColor(MeasurementUnitsActivity.this, R.color.purple_navigation_background));

        CelsiusLayout = (TextView) findViewById(R.id.celsius_textview);
        FahrenheitLayout = (TextView) findViewById(R.id.fahrenheit_textview);

        temperature_unit = BufferManager.TxBuffer[30];

        if(CELSIUS_UNIT == temperature_unit) {
            CelsiusLayout.setBackgroundColor(ContextCompat.getColor(MeasurementUnitsActivity.this, R.color.purple_navigation_background));
            FahrenheitLayout.setBackgroundColor(ContextCompat.getColor(MeasurementUnitsActivity.this, R.color.purple_fade_background));

            CelsiusLayout.setTextColor(ContextCompat.getColor(MeasurementUnitsActivity.this, R.color.white));
            FahrenheitLayout.setTextColor(ContextCompat.getColor(MeasurementUnitsActivity.this, R.color.black));
        }
        else {
            CelsiusLayout.setBackgroundColor(ContextCompat.getColor(MeasurementUnitsActivity.this, R.color.purple_fade_background));
            FahrenheitLayout.setBackgroundColor(ContextCompat.getColor(MeasurementUnitsActivity.this, R.color.purple_navigation_background));

            CelsiusLayout.setTextColor(ContextCompat.getColor(MeasurementUnitsActivity.this, R.color.black));
            FahrenheitLayout.setTextColor(ContextCompat.getColor(MeasurementUnitsActivity.this, R.color.white));
        }

        CelsiusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CelsiusLayout.setBackgroundColor(ContextCompat.getColor(MeasurementUnitsActivity.this, R.color.purple_navigation_background));
                FahrenheitLayout.setBackgroundColor(ContextCompat.getColor(MeasurementUnitsActivity.this, R.color.purple_fade_background));

                CelsiusLayout.setTextColor(ContextCompat.getColor(MeasurementUnitsActivity.this, R.color.white));
                FahrenheitLayout.setTextColor(ContextCompat.getColor(MeasurementUnitsActivity.this, R.color.black));

                temperature_unit = CELSIUS_UNIT;
                BufferManager.TxBuffer[30] = (byte) temperature_unit;
            }
        });

        FahrenheitLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CelsiusLayout.setBackgroundColor(ContextCompat.getColor(MeasurementUnitsActivity.this, R.color.purple_fade_background));
                FahrenheitLayout.setBackgroundColor(ContextCompat.getColor(MeasurementUnitsActivity.this, R.color.purple_navigation_background));

                CelsiusLayout.setTextColor(ContextCompat.getColor(MeasurementUnitsActivity.this, R.color.black));
                FahrenheitLayout.setTextColor(ContextCompat.getColor(MeasurementUnitsActivity.this, R.color.white));

                temperature_unit = FAHRENHEIT_UNIT;
                BufferManager.TxBuffer[30] = (byte) temperature_unit;
            }
        });
    }
}
