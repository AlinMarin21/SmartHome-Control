package com.example.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class LanguageActivity extends AppCompat {

    static final String ENGLISH = "en";
    static final String ROMANIAN = "ro";
    static final String FRENCH = "fr";
    static final String ITALIAN = "it";
    static final String SPANISH = "es";
    static final String GERMAN = "de";
    static final String RUSSIAN = "ru";
    static final String JAPANESE = "ja";
    static final String CHINESE = "zh";

    LinearLayout EnLangButton;
    LinearLayout RoLangButton;
    LinearLayout FrLangButton;
    LinearLayout ItLangButton;
    LinearLayout SpLangButton;
    LinearLayout GeLangButton;
    LinearLayout RuLangButton;
    LinearLayout JpLangButton;
    LinearLayout CnLangButton;

    TextView EnLangTextView;
    TextView RoLangTextView;
    TextView FrLangTextView;
    TextView ItLangTextView;
    TextView SpLangTextView;
    TextView GeLangTextView;
    TextView RuLangTextView;
    TextView JpLangTextView;
    TextView CnLangTextView;

    LinearLayout currentLangButton = null;
    TextView currentTextView = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        Window window = LanguageActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_background));
        window.setNavigationBarColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_navigation_background));

        EnLangButton = (LinearLayout) findViewById(R.id.en_lang_button);
        RoLangButton = (LinearLayout) findViewById(R.id.ro_lang_button);
        FrLangButton = (LinearLayout) findViewById(R.id.fr_lang_button);
        ItLangButton = (LinearLayout) findViewById(R.id.it_lang_button);
        SpLangButton = (LinearLayout) findViewById(R.id.sp_lang_button);
        GeLangButton = (LinearLayout) findViewById(R.id.ge_lang_button);
        RuLangButton = (LinearLayout) findViewById(R.id.ru_lang_button);
        JpLangButton = (LinearLayout) findViewById(R.id.jp_lang_button);
        CnLangButton = (LinearLayout) findViewById(R.id.cn_lang_button);

        EnLangTextView = (TextView) findViewById(R.id.en_lang);
        RoLangTextView = (TextView) findViewById(R.id.ro_lang);
        FrLangTextView = (TextView) findViewById(R.id.fr_lang);
        ItLangTextView = (TextView) findViewById(R.id.it_lang);
        SpLangTextView = (TextView) findViewById(R.id.sp_lang);
        GeLangTextView = (TextView) findViewById(R.id.ge_lang);
        RuLangTextView = (TextView) findViewById(R.id.ru_lang);
        JpLangTextView = (TextView) findViewById(R.id.jp_lang);
        CnLangTextView = (TextView) findViewById(R.id.cn_lang);

        LanguageManager languageManager = new LanguageManager(this);

        getCurrentLang();

        EnLangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_fade_background));
                currentTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.gray_navigation_background));

                currentLangButton = EnLangButton;
                currentTextView = EnLangTextView;

                currentLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_navigation_background));
                currentTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.white));

                languageManager.updateResource(ENGLISH);
                recreate();
            }
        });

        RoLangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_fade_background));
                currentTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.gray_navigation_background));

                currentLangButton = RoLangButton;
                currentTextView = RoLangTextView;

                currentLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_navigation_background));
                currentTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.white));

                languageManager.updateResource(ROMANIAN);
                recreate();
            }
        });

        FrLangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_fade_background));
                currentTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.gray_navigation_background));

                currentLangButton = FrLangButton;
                currentTextView = FrLangTextView;

                currentLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_navigation_background));
                currentTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.white));

                languageManager.updateResource(FRENCH);
                recreate();

            }
        });

        ItLangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_fade_background));
                currentTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.gray_navigation_background));

                currentLangButton = ItLangButton;
                currentTextView = ItLangTextView;

                currentLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_navigation_background));
                currentTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.white));

                languageManager.updateResource(ITALIAN);
                recreate();

            }
        });

        SpLangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_fade_background));
                currentTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.gray_navigation_background));

                currentLangButton = SpLangButton;
                currentTextView = SpLangTextView;

                currentLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_navigation_background));
                currentTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.white));

                languageManager.updateResource(SPANISH);
                recreate();

            }
        });

        GeLangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_fade_background));
                currentTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.gray_navigation_background));

                currentLangButton = GeLangButton;
                currentTextView = GeLangTextView;

                currentLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_navigation_background));
                currentTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.white));

                languageManager.updateResource(GERMAN);
                recreate();

            }
        });

        RuLangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_fade_background));
                currentTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.gray_navigation_background));

                currentLangButton = RuLangButton;
                currentTextView = RuLangTextView;

                currentLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_navigation_background));
                currentTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.white));

                languageManager.updateResource(RUSSIAN);
                recreate();

            }
        });

        JpLangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_fade_background));
                currentTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.gray_navigation_background));

                currentLangButton = JpLangButton;
                currentTextView = JpLangTextView;

                currentLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_navigation_background));
                currentTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.white));

                languageManager.updateResource(JAPANESE);
                recreate();

            }
        });

        CnLangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_fade_background));
                currentTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.gray_navigation_background));

                currentLangButton = CnLangButton;
                currentTextView = CnLangTextView;

                currentLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_navigation_background));
                currentTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.white));

                languageManager.updateResource(CHINESE);
                recreate();

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LanguageActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    void getCurrentLang() {
        if(ENGLISH.equals(BufferManager.currentLanguage)) {
            EnLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_navigation_background));
            EnLangTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.white));
            currentLangButton = EnLangButton;
            currentTextView = EnLangTextView;
        }
        if(ROMANIAN.equals(BufferManager.currentLanguage)) {
            RoLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_navigation_background));
            RoLangTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.white));
            currentLangButton = RoLangButton;
            currentTextView = RoLangTextView;
        }
        if(FRENCH.equals(BufferManager.currentLanguage)) {
            FrLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_navigation_background));
            FrLangTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.white));
            currentLangButton = FrLangButton;
            currentTextView = FrLangTextView;
        }
        if(ITALIAN.equals(BufferManager.currentLanguage)) {
            ItLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_navigation_background));
            ItLangTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.white));
            currentLangButton = ItLangButton;
            currentTextView = ItLangTextView;
        }
        if(SPANISH.equals(BufferManager.currentLanguage)) {
            SpLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_navigation_background));
            SpLangTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.white));
            currentLangButton = SpLangButton;
            currentTextView = SpLangTextView;
        }
        if(GERMAN.equals(BufferManager.currentLanguage)) {
            GeLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_navigation_background));
            GeLangTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.white));
            currentLangButton = GeLangButton;
            currentTextView = GeLangTextView;
        }
        if(RUSSIAN.equals(BufferManager.currentLanguage)) {
            RuLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_navigation_background));
            RuLangTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.white));
            currentLangButton = RuLangButton;
            currentTextView = RuLangTextView;
        }
        if(JAPANESE.equals(BufferManager.currentLanguage)) {
            JpLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_navigation_background));
            JpLangTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.white));
            currentLangButton = JpLangButton;
            currentTextView = JpLangTextView;
        }
        if(CHINESE.equals(BufferManager.currentLanguage)) {
            CnLangButton.setBackgroundColor(ContextCompat.getColor(LanguageActivity.this, R.color.purple_navigation_background));
            CnLangTextView.setTextColor(ContextCompat.getColor(LanguageActivity.this, R.color.white));
            currentLangButton = CnLangButton;
            currentTextView = CnLangTextView;
        }

    }
}
