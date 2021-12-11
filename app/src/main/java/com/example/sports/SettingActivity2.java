package com.example.sports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;

public class SettingActivity2 extends AppCompatActivity {

    Button btn;
    TextInputEditText et_set,et_cunter;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting2);

        btn = findViewById(R.id.btn_seting);
        et_cunter = findViewById(R.id.et_cunter);
        et_set = findViewById(R.id.et_set);
        back = findViewById(R.id.img_back_seting);

        et_set.setText(G.set+"");
        et_cunter.setText(G.cunter+"");


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_set.getText().toString().equals("")){
                    et_set.setError("Enter set:");
                }if(et_cunter.getText().toString().equals("")){
                    et_cunter.setError("Enter Cunter:");
                }else {
                    G.cunter = Integer.parseInt(et_cunter.getText().toString());
                    G.set = Integer.parseInt(et_set.getText().toString());
                    startActivity(new Intent(SettingActivity2.this,MainActivity.class));
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(SettingActivity2.this, android.R.anim.fade_in));
                startActivity(new Intent(SettingActivity2.this,MainActivity.class));
                finish();
            }
        });

    }
}