package com.example.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class StartActivity extends AppCompatActivity {

    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    static final int BLUETOOTH_OK = 0;
    static final int BLUETOOTH_PERMISSION_NOT_GRANTED = 1;
    static final int BLUETOOTH_NOT_SUPPORTED = 2;
    static final int BLUETOOTH_NOT_ENABLED = 3;
    static final int DEVICE_NOT_PAIRED = 4;
    static final int CONNECTION_NOT_ESTABLISHED = 5;

    int bluetooth_action_result = BLUETOOTH_OK;

    BluetoothAdapter btAdapter = null;
    BluetoothDevice hc05 = null;
    BluetoothSocket btSocket = null;
    OutputStream TX_data = null;
    InputStream RX_data = null;

    RelativeLayout backgroundLayout = null;
    RelativeLayout bluetoothInfoLayout = null;
    TextView bluetoothMessage = null;
    ImageView bluetoothImage = null;
    Button tryAgainButton = null;
    ImageView bottomPage = null;

    AnimationDrawable connectivityAnimation = null;
    ImageView startPageConnectivity = null;

    Intent homeMenu_intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        startPageConnectivity = (ImageView) findViewById(R.id.startPageConnectivity);
        connectivityAnimation = (AnimationDrawable) startPageConnectivity.getDrawable();
        backgroundLayout = (RelativeLayout) findViewById(R.id.backgroundLayout);
        bluetoothInfoLayout = (RelativeLayout) findViewById(R.id.text_layout);
        bluetoothMessage = (TextView) findViewById(R.id.message_BT);
        bluetoothImage = (ImageView) findViewById(R.id.bluetoothActionImage);
        tryAgainButton = (Button) findViewById(R.id.try_again_button);
        bottomPage = (ImageView) findViewById(R.id.bottom_page);

        startPageConnectivity.setVisibility(View.VISIBLE);
        bluetoothImage.setVisibility(View.INVISIBLE);
        bluetoothInfoLayout.setVisibility(View.INVISIBLE);

        Window window = StartActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(StartActivity.this, R.color.gray_background));
        window.setNavigationBarColor(ContextCompat.getColor(StartActivity.this, R.color.gray_navigation_background));

        new bluetoothConnectionTask().execute();
    }

    private class bluetoothConnectionTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            connectivityAnimation.start();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (ActivityCompat.checkSelfPermission(StartActivity.this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                bluetooth_action_result = BLUETOOTH_PERMISSION_NOT_GRANTED;
            }

            btAdapter = BluetoothAdapter.getDefaultAdapter();

            if (btAdapter == null) {
                bluetooth_action_result = BLUETOOTH_NOT_SUPPORTED;
            } else {
                if (!btAdapter.isEnabled()) {
                    bluetooth_action_result = BLUETOOTH_NOT_ENABLED;
                }
                if (BLUETOOTH_OK == bluetooth_action_result) {
                    Set<BluetoothDevice> all_devices = btAdapter.getBondedDevices();

                    hc05 = btAdapter.getRemoteDevice("00:22:06:01:78:5E");

                    if (!all_devices.contains(hc05)) {
                        bluetooth_action_result = DEVICE_NOT_PAIRED;
                    }

                    int btChecks_counter = 0;
                    do {
                        try {
                            btSocket = hc05.createRfcommSocketToServiceRecord(mUUID);
                            btSocket.connect();
                        } catch (IOException e) {
                            bluetooth_action_result = CONNECTION_NOT_ESTABLISHED;
                        }
                        btChecks_counter++;
                    } while (!btSocket.isConnected() && btChecks_counter < 5);

                    if (bluetooth_action_result != CONNECTION_NOT_ESTABLISHED) {
                        try {
                            TX_data = btSocket.getOutputStream();
                            RX_data = btSocket.getInputStream();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            connectivityAnimation.stop();

            Window window = StartActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            if (BLUETOOTH_OK != bluetooth_action_result) {
                startPageConnectivity.setVisibility(View.INVISIBLE);
                bluetoothInfoLayout.setVisibility(View.VISIBLE);
                bluetoothImage.setVisibility(View.VISIBLE);

                if (BLUETOOTH_PERMISSION_NOT_GRANTED == bluetooth_action_result) {
                    bluetoothImage.setBackgroundResource(R.drawable.permission_not_granted_image);
                    backgroundLayout.setBackgroundResource(R.color.beige_background);
                    bottomPage.setImageResource(R.drawable.beige_bottom_image);
                    window.setStatusBarColor(ContextCompat.getColor(StartActivity.this, R.color.beige_background));
                    window.setNavigationBarColor(ContextCompat.getColor(StartActivity.this, R.color.beige_navigation_background));
                    bluetoothMessage.setText(R.string.permission_not_granted);
                } else if (BLUETOOTH_NOT_SUPPORTED == bluetooth_action_result) {
                    bluetoothImage.setBackgroundResource(R.drawable.bt_not_supported_image);
                    backgroundLayout.setBackgroundResource(R.color.red_background);
                    bottomPage.setImageResource(R.drawable.red_bottom_image);
                    window.setStatusBarColor(ContextCompat.getColor(StartActivity.this, R.color.red_background));
                    window.setNavigationBarColor(ContextCompat.getColor(StartActivity.this, R.color.red_navigation_background));
                    bluetoothMessage.setText(R.string.device_not_supported);
                } else if (BLUETOOTH_NOT_ENABLED == bluetooth_action_result) {
                    bluetoothImage.setBackgroundResource(R.drawable.bt_not_enabled_image);
                    backgroundLayout.setBackgroundResource(R.color.blue_background);
                    bottomPage.setImageResource(R.drawable.blue_bottom_image);
                    window.setStatusBarColor(ContextCompat.getColor(StartActivity.this, R.color.blue_background));
                    window.setNavigationBarColor(ContextCompat.getColor(StartActivity.this, R.color.blue_navigation_background));
                    bluetoothMessage.setText(R.string.bluetooth_not_connected);
                } else if (DEVICE_NOT_PAIRED == bluetooth_action_result) {
                    bluetoothImage.setBackgroundResource(R.drawable.device_not_paired_image);
                    backgroundLayout.setBackgroundResource(R.color.green_background);
                    bottomPage.setImageResource(R.drawable.green_bottom_image);
                    window.setStatusBarColor(ContextCompat.getColor(StartActivity.this, R.color.green_background));
                    window.setNavigationBarColor(ContextCompat.getColor(StartActivity.this, R.color.green_navigation_background));
                    bluetoothMessage.setText(R.string.device_not_paired);
                } else if (CONNECTION_NOT_ESTABLISHED == bluetooth_action_result) {
                    bluetoothImage.setBackgroundResource(R.drawable.connection_not_established_image);
                    backgroundLayout.setBackgroundResource(R.color.purple_background);
                    bottomPage.setImageResource(R.drawable.purple_bottom_image);
                    window.setStatusBarColor(ContextCompat.getColor(StartActivity.this, R.color.purple_background));
                    window.setNavigationBarColor(ContextCompat.getColor(StartActivity.this, R.color.purple_navigation_background));
                    bluetoothMessage.setText((R.string.connection_not_established));
                }

                tryAgainButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(StartActivity.this, StartActivity.class));
                    }
                });
            } else {
//                    transmission_allowed = true;
//                    recovery_action = true;
//
                homeMenu_intent = new Intent(StartActivity.this, HomeMenuActivity.class);
                startActivity(homeMenu_intent);
                }
            }
        }

}