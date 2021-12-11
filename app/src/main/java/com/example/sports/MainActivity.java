package com.example.sports;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;
import java.util.concurrent.RunnableFuture;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech mTTS;
    Button btn_stop, btn_start, btn_restart;
    TextView tv_set, tv_cunter;
    CircularProgressIndicator progressIndicator;
    int progres;
    Handler handler;
    ImageView img_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_restart = findViewById(R.id.btn_restart);
        btn_start = findViewById(R.id.btn_start);
        btn_stop = findViewById(R.id.btn_stop);
        tv_cunter = findViewById(R.id.tv_cunter);
        tv_set = findViewById(R.id.tv_set);
        progressIndicator = findViewById(R.id.proogres);
        img_settings = findViewById(R.id.img_seting);
        progres = 0;

        tv_cunter.setText(G.cunter+" / "+0);
        tv_set.setText(G.set+" / "+0);

        progressIndicator.setMax(G.cunter);

        handler = new Handler();

        img_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));
                startActivity(new Intent(MainActivity.this,SettingActivity2.class));
                finish();
            }
        });

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }

        });

        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainActivity.class));
                finish();
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 1; i <=G.set ; i++) {
                            int finalI = i;
                            progres=0;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    tv_set.setText(G.set+" / "+finalI);
                                    speak("set"+finalI );
                                }
                            });

                            for (int a = 1; a <=G.cunter ; a++) {
                                try {
                                    Thread.sleep(1500);
                                    int finalA = a;
                                    handler.post(new Runnable() {
                                        @RequiresApi(api = Build.VERSION_CODES.N)
                                        @Override
                                        public void run() {
                                            progres+=1;
                                            progressIndicator.setProgress(progres,true);
                                            tv_cunter.setText(G.cunter+" / "+finalA);
                                            speak(finalA +"");
                                        }
                                    });



                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            try {
                                Thread.sleep(3500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                });
                thread.start();
            }
        });

    }

    private void speak(String text) {

        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }


    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }

        super.onDestroy();
    }
}