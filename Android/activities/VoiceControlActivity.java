package com.example.home;

import static android.Manifest.permission.RECORD_AUDIO;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class VoiceControlActivity extends AppCompat {

    private static final int VOICE_RECOGNITION_ACTIVE = 0;
    private static final int VOICE_RECOGNITION_PAUSED = 1;
    private SpeechRecognizer speechRecognizer;
    private Intent intentRecognizer;
    private TextView speechTextView;
    private ImageView startSpeechRecognition;

    TextView commandsListTextView;
    LinearLayout commandsListLayout;
    ImageView closeCommandsListButton;
    private AnimationDrawable speechAnimation = null;

    private int speech_recognition_state = VOICE_RECOGNITION_PAUSED;
    private String speechString = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voicecontrol);

        Window window = VoiceControlActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(VoiceControlActivity.this, R.color.red_background));
        window.setNavigationBarColor(ContextCompat.getColor(VoiceControlActivity.this, R.color.red_navigation_background));

        ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO}, PackageManager.PERMISSION_GRANTED);

        speechTextView = (TextView) findViewById(R.id.speech_text_view);
        startSpeechRecognition = (ImageView) findViewById(R.id.speechRecognitionAnimation);
        speechAnimation = (AnimationDrawable) startSpeechRecognition.getDrawable();

        commandsListTextView = (TextView) findViewById(R.id.see_all_commands_textview);
        commandsListLayout = (LinearLayout) findViewById(R.id.commands_list);
        closeCommandsListButton = (ImageView) findViewById(R.id.close_commands_list_button);

        commandsListLayout.setVisibility(View.INVISIBLE);

        intentRecognizer = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intentRecognizer.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        speechRecognizer.setRecognitionListener(new RecognitionListener() {

            boolean end_of_speech = false;
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
                end_of_speech = false;
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
                end_of_speech = true;
            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                if(matches != null) {
                    speechString = matches.get(0);
                    speechTextView.setText(speechString);
                    checkSpeechCommand();
                    if(end_of_speech == true) {
                        speechRecognizer.stopListening();
                        speechAnimation.stop();
                        speech_recognition_state = VOICE_RECOGNITION_PAUSED;
                    }
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        startSpeechRecognition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(VOICE_RECOGNITION_PAUSED == speech_recognition_state) {
                    speechAnimation.start();
                    speechRecognizer.startListening(intentRecognizer);
                    speech_recognition_state = VOICE_RECOGNITION_ACTIVE;
                }
                else if(VOICE_RECOGNITION_ACTIVE == speech_recognition_state) {
                    speechAnimation.stop();
                    speechRecognizer.stopListening();
                    speech_recognition_state = VOICE_RECOGNITION_PAUSED;
                }
            }
        });

        commandsListTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commandsListLayout.setVisibility(View.VISIBLE);
            }
        });

        closeCommandsListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commandsListLayout.setVisibility(View.INVISIBLE);
            }
        });

    }

    void checkSpeechCommand() {
        if(speechString.equalsIgnoreCase(getString(R.string.turn_on_living_room_light_command_text))) {
            BufferManager.TxBuffer[1] = (byte) 1;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.turn_off_living_room_light_command_text))) {
            BufferManager.TxBuffer[1] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.change_intensity_max_living_room_light_command_text))) {
            BufferManager.TxBuffer[2] = (byte) 254;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.change_intensity_min_living_room_light_command_text))) {
            BufferManager.TxBuffer[2] = (byte) 5;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.turn_on_kitchen_lights_command_text))) {
            BufferManager.TxBuffer[3] = (byte) 1;
            BufferManager.TxBuffer[4] = (byte) 1;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.turn_off_kitchen_lights_command_text))) {
            BufferManager.TxBuffer[3] = (byte) 0;
            BufferManager.TxBuffer[4] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.turn_on_room1_light_command_text)) || speechString.equalsIgnoreCase(getString(R.string.turn_on_roomone_light_command_text))) {
            BufferManager.TxBuffer[5] = (byte) 1;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.turn_off_room1_light_command_text)) || speechString.equalsIgnoreCase(getString(R.string.turn_off_roomone_light_command_text))) {
            BufferManager.TxBuffer[5] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.change_color_room1_light_command_text) + " " + getString(R.string.red_color)) || speechString.equalsIgnoreCase(getString(R.string.change_color_roomone_light_command_text) + " " + getString(R.string.red_color))) {
            BufferManager.TxBuffer[44] = (byte) 1;
            BufferManager.TxBuffer[6] = (byte) 254;
            BufferManager.TxBuffer[7] = (byte) 0;
            BufferManager.TxBuffer[8] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.change_color_room1_light_command_text) + " " + getString(R.string.yellow_color)) || speechString.equalsIgnoreCase(getString(R.string.change_color_roomone_light_command_text) + " " + getString(R.string.yellow_color))) {
            BufferManager.TxBuffer[44] = (byte) 0;
            BufferManager.TxBuffer[6] = (byte) 254;
            BufferManager.TxBuffer[7] = (byte) 254;
            BufferManager.TxBuffer[8] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.change_color_room1_light_command_text) + " " + getString(R.string.orange_color)) || speechString.equalsIgnoreCase(getString(R.string.change_color_roomone_light_command_text) + " " + getString(R.string.orange_color))) {
            BufferManager.TxBuffer[44] = (byte) 2;
            BufferManager.TxBuffer[6] = (byte) 254;
            BufferManager.TxBuffer[7] = (byte) 165;
            BufferManager.TxBuffer[8] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.change_color_room1_light_command_text) + " " + getString(R.string.aqua_color)) || speechString.equalsIgnoreCase(getString(R.string.change_color_roomone_light_command_text) + " " + getString(R.string.aqua_color))) {
            BufferManager.TxBuffer[44] = (byte) 3;
            BufferManager.TxBuffer[6] = (byte) 0;
            BufferManager.TxBuffer[7] = (byte) 254;
            BufferManager.TxBuffer[8] = (byte) 254;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.change_color_room1_light_command_text) + " " + getString(R.string.blue_color)) || speechString.equalsIgnoreCase(getString(R.string.change_color_roomone_light_command_text) + " " + getString(R.string.blue_color))) {
            BufferManager.TxBuffer[44] = (byte) 4;
            BufferManager.TxBuffer[6] = (byte) 0;
            BufferManager.TxBuffer[7] = (byte) 0;
            BufferManager.TxBuffer[8] = (byte) 254;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.change_color_room1_light_command_text) + " " + getString(R.string.green_color)) || speechString.equalsIgnoreCase(getString(R.string.change_color_roomone_light_command_text) + " " + getString(R.string.green_color))) {
            BufferManager.TxBuffer[44] = (byte) 5;
            BufferManager.TxBuffer[6] = (byte) 0;
            BufferManager.TxBuffer[7] = (byte) 254;
            BufferManager.TxBuffer[8] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.change_color_room1_light_command_text) + " " + getString(R.string.pink_color)) || speechString.equalsIgnoreCase(getString(R.string.change_color_roomone_light_command_text) + " " + getString(R.string.pink_color))) {
            BufferManager.TxBuffer[44] = (byte) 6;
            BufferManager.TxBuffer[6] = (byte) 254;
            BufferManager.TxBuffer[7] = (byte) 0;
            BufferManager.TxBuffer[8] = (byte) 254;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.change_color_room1_light_command_text) + " " + getString(R.string.purple_color)) || speechString.equalsIgnoreCase(getString(R.string.change_color_roomone_light_command_text) + " " + getString(R.string.purple_color))) {
            BufferManager.TxBuffer[44] = (byte) 7;
            BufferManager.TxBuffer[6] = (byte) 128;
            BufferManager.TxBuffer[7] = (byte) 0;
            BufferManager.TxBuffer[8] = (byte) 128;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.turn_on_room2_light_command_text)) || speechString.equalsIgnoreCase(getString(R.string.turn_on_roomtwo_light_command_text))) {
            BufferManager.TxBuffer[9] = (byte) 1;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.turn_off_room2_light_command_text)) || speechString.equalsIgnoreCase(getString(R.string.turn_off_roomtwo_light_command_text))) {
            BufferManager.TxBuffer[9] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.change_color_room2_light_command_text) + " " + getString(R.string.red_color)) || speechString.equalsIgnoreCase(getString(R.string.change_color_roomtwo_light_command_text) + " " + getString(R.string.red_color))) {
            BufferManager.TxBuffer[45] = (byte) 1;
            BufferManager.TxBuffer[10] = (byte) 254;
            BufferManager.TxBuffer[11] = (byte) 0;
            BufferManager.TxBuffer[12] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.change_color_room2_light_command_text) + " " + getString(R.string.yellow_color)) || speechString.equalsIgnoreCase(getString(R.string.change_color_roomtwo_light_command_text) + " " + getString(R.string.yellow_color))) {
            BufferManager.TxBuffer[45] = (byte) 0;
            BufferManager.TxBuffer[10] = (byte) 254;
            BufferManager.TxBuffer[11] = (byte) 254;
            BufferManager.TxBuffer[12] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.change_color_room2_light_command_text) + " " + getString(R.string.orange_color)) || speechString.equalsIgnoreCase(getString(R.string.change_color_roomtwo_light_command_text) + " " + getString(R.string.orange_color))) {
            BufferManager.TxBuffer[45] = (byte) 2;
            BufferManager.TxBuffer[10] = (byte) 254;
            BufferManager.TxBuffer[11] = (byte) 165;
            BufferManager.TxBuffer[12] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.change_color_room2_light_command_text) + " " + getString(R.string.aqua_color)) || speechString.equalsIgnoreCase(getString(R.string.change_color_roomtwo_light_command_text) + " " + getString(R.string.aqua_color))) {
            BufferManager.TxBuffer[45] = (byte) 3;
            BufferManager.TxBuffer[10] = (byte) 0;
            BufferManager.TxBuffer[11] = (byte) 254;
            BufferManager.TxBuffer[12] = (byte) 254;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.change_color_room2_light_command_text) + " " + getString(R.string.blue_color)) || speechString.equalsIgnoreCase(getString(R.string.change_color_roomtwo_light_command_text) + " " + getString(R.string.blue_color))) {
            BufferManager.TxBuffer[45] = (byte) 4;
            BufferManager.TxBuffer[10] = (byte) 0;
            BufferManager.TxBuffer[11] = (byte) 0;
            BufferManager.TxBuffer[12] = (byte) 254;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.change_color_room2_light_command_text) + " " + getString(R.string.green_color)) || speechString.equalsIgnoreCase(getString(R.string.change_color_roomtwo_light_command_text) + " " + getString(R.string.green_color))) {
            BufferManager.TxBuffer[45] = (byte) 5;
            BufferManager.TxBuffer[10] = (byte) 0;
            BufferManager.TxBuffer[11] = (byte) 254;
            BufferManager.TxBuffer[12] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.change_color_room2_light_command_text) + " " + getString(R.string.pink_color)) || speechString.equalsIgnoreCase(getString(R.string.change_color_roomtwo_light_command_text) + " " + getString(R.string.pink_color))) {
            BufferManager.TxBuffer[45] = (byte) 6;
            BufferManager.TxBuffer[10] = (byte) 254;
            BufferManager.TxBuffer[11] = (byte) 0;
            BufferManager.TxBuffer[12] = (byte) 254;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.change_color_room2_light_command_text) + " " + getString(R.string.purple_color)) || speechString.equalsIgnoreCase(getString(R.string.change_color_roomtwo_light_command_text) + " " + getString(R.string.purple_color))) {
            BufferManager.TxBuffer[45] = (byte) 7;
            BufferManager.TxBuffer[10] = (byte) 128;
            BufferManager.TxBuffer[11] = (byte) 0;
            BufferManager.TxBuffer[12] = (byte) 128;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.turn_on_bathroom_light_command_text))) {
            BufferManager.TxBuffer[15] = (byte) 1;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.turn_off_bathroom_light_command_text))) {
            BufferManager.TxBuffer[15] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.turn_on_alley_lights_on_dark_command_text))) {
            BufferManager.TxBuffer[14] = (byte) 2;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.turn_on_alley_lights_on_move_command_text))) {
            BufferManager.TxBuffer[14] = (byte) 1;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.turn_on_alley_lights_command_text))) {
            BufferManager.TxBuffer[14] = (byte) 0;
            BufferManager.TxBuffer[13] = (byte) 1;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.turn_off_alley_lights_command_text))) {
            BufferManager.TxBuffer[14] = (byte) 0;
            BufferManager.TxBuffer[13] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.open_front_door_command_text))) {
            BufferManager.TxBuffer[16] = (byte) 1;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.open_gates_command_text))) {
            BufferManager.TxBuffer[19] = (byte) 1;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.close_front_door_command_text))) {
            BufferManager.TxBuffer[16] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.close_gates_command_text))) {
            BufferManager.TxBuffer[19] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.lock_front_door_command_text))) {
            BufferManager.TxBuffer[18] = (byte) 1;
            BufferManager.TxBuffer[16] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.unlock_front_door_command_text))) {
            BufferManager.TxBuffer[18] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.activate_automatic_closing_door_command_text))) {
            BufferManager.TxBuffer[17] = (byte) 1;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.activate_automatic_closing_gates_command_text))) {
            BufferManager.TxBuffer[20] = (byte) 1;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.deactivate_automatic_closing_door_command_text))) {
            BufferManager.TxBuffer[17] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.deactivate_automatic_closing_gates_command_text))) {
            BufferManager.TxBuffer[20] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.turn_on_ac_command_text))) {
            BufferManager.TxBuffer[21] = (byte) 1;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.turn_off_ac_command_text))) {
            BufferManager.TxBuffer[21] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.activate_automatic_ac_command_text))) {
            BufferManager.TxBuffer[22] = (byte) 1;
            BufferManager.TxBuffer[21] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.deactivate_automatic_ac_command_text))) {
            BufferManager.TxBuffer[22] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.increase_ac_speed_command_text))) {
            int ac_speed = BufferManager.TxBuffer[23] & 0xFF;
            ac_speed = ac_speed + 25;
            if(ac_speed > 254) {
                ac_speed = 254;
            }
            BufferManager.TxBuffer[23] = (byte) ac_speed;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.decrease_ac_speed_command_text))) {
            int ac_speed = BufferManager.TxBuffer[23] & 0xFF;
            ac_speed = ac_speed - 25;
            if(ac_speed < 5) {
                ac_speed = 5;
            }
            BufferManager.TxBuffer[23] = (byte) ac_speed;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.turn_on_diplay_command_text))) {
            BufferManager.TxBuffer[24] = (byte) 1;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.turn_off_diplay_command_text))) {
            BufferManager.TxBuffer[24] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.show_inside_temp_command_text))) {
            BufferManager.TxBuffer[24] = (byte) 1;
            BufferManager.TxBuffer[25] = (byte) 0;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.show_outside_temp_command_text))) {
            BufferManager.TxBuffer[24] = (byte) 1;
            BufferManager.TxBuffer[25] = (byte) 1;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.show_inside_hum_command_text))) {
            BufferManager.TxBuffer[24] = (byte) 1;
            BufferManager.TxBuffer[25] = (byte) 2;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.show_outside_hum_command_text))) {
            BufferManager.TxBuffer[24] = (byte) 1;
            BufferManager.TxBuffer[25] = (byte) 3;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.show_inside_temp_hum_command_text))) {
            BufferManager.TxBuffer[24] = (byte) 1;
            BufferManager.TxBuffer[25] = (byte) 4;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.show_outside_temp_hum_command_text))) {
            BufferManager.TxBuffer[24] = (byte) 1;
            BufferManager.TxBuffer[25] = (byte) 5;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.show_aq_command_text))) {
            BufferManager.TxBuffer[24] = (byte) 1;
            BufferManager.TxBuffer[25] = (byte) 6;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.show_co2_command_text))) {
            BufferManager.TxBuffer[24] = (byte) 1;
            BufferManager.TxBuffer[25] = (byte) 7;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.show_rainfall_command_text))) {
            BufferManager.TxBuffer[24] = (byte) 1;
            BufferManager.TxBuffer[25] = (byte) 8;
        }
        else if(speechString.equalsIgnoreCase(getString(R.string.show_custom_screen_command_text))) {
            BufferManager.TxBuffer[24] = (byte) 1;
            BufferManager.TxBuffer[25] = (byte) 9;
        }
        else {
            Toast.makeText(getBaseContext(), R.string.command_not_found, Toast.LENGTH_SHORT).show();
        }
    }
}
