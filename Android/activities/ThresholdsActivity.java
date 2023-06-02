package com.example.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ThresholdsActivity extends AppCompat {

    Button saveValuesButton;
    TextInputEditText temperatureThresholdText;
    TextInputEditText brightnessThresholdText;
    TextInputEditText gasThresholdText;
    TextInputEditText airQualityThresholdText;

    int temp_threshold;
    int bright_threshold;
    int co2_threshold;
    int aq_threshold;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thresholds);

        Window window = ThresholdsActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(ThresholdsActivity.this, R.color.purple_background));
        window.setNavigationBarColor(ContextCompat.getColor(ThresholdsActivity.this, R.color.purple_navigation_background));

        saveValuesButton = (Button) findViewById(R.id.save_values_button);
        temperatureThresholdText = (TextInputEditText) findViewById(R.id.temp_threshold_text);
        brightnessThresholdText = (TextInputEditText) findViewById(R.id.bright_threshold_text);
        gasThresholdText = (TextInputEditText) findViewById(R.id.co2_threshold_text);
        airQualityThresholdText = (TextInputEditText) findViewById(R.id.aq_threshold_text);

        temp_threshold = Integer.valueOf(temperatureThresholdText.getText().toString());
        bright_threshold = Integer.valueOf(brightnessThresholdText.getText().toString());
        co2_threshold = Integer.valueOf(gasThresholdText.getText().toString());
        aq_threshold = Integer.valueOf(airQualityThresholdText.getText().toString());

        temperatureThresholdText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!(temperatureThresholdText.getText().toString()).equals("")) {
                    temp_threshold = Integer.valueOf(editable.toString());
                }
                else {
                    temp_threshold = 0;
                    temperatureThresholdText.setText("0");
                }
            }
        });

        brightnessThresholdText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!(brightnessThresholdText.getText().toString()).equals("")) {
                    bright_threshold = Integer.valueOf(editable.toString());
                }
                else {
                    bright_threshold = 0;
                    brightnessThresholdText.setText("0");
                }
            }
        });

        gasThresholdText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!(gasThresholdText.getText().toString()).equals("")) {
                    co2_threshold = Integer.valueOf(editable.toString());
                }
                else {
                    co2_threshold = 0;
                    gasThresholdText.setText("0");
                }
            }
        });

        airQualityThresholdText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!(airQualityThresholdText.getText().toString()).equals("")) {
                    aq_threshold = Integer.valueOf(editable.toString());
                }
                else {
                    aq_threshold = 0;
                    airQualityThresholdText.setText("0");
                }
            }
        });

        saveValuesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(temp_threshold);
                System.out.println(bright_threshold);
                System.out.println(co2_threshold);
                System.out.println(aq_threshold);
            }
        });

    }
}
