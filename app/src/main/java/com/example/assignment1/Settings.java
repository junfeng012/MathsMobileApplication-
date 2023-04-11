package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Settings extends Activity {
    Button btnBack;

    protected Assignment1ApplicationClass app;
    private Switch BGMSwitch;
    private Switch SFXSwitch;
    Bundle extra;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        extra =getIntent().getExtras();
        username=extra.getString("username");
        app = (Assignment1ApplicationClass)getApplication();

        btnBack = (Button) findViewById(R.id.Button_S_Back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Settings.this, Home_Page.class);
                a.putExtra("username",username);
                app.playGSoundCoin();
                startActivity(a);
                finish();

            }

        });

        SFXSwitch=(Switch)findViewById(R.id.Switch_S_SoundEffects);

        if(app.getsFX_state()){
            SFXSwitch.setChecked(true);
        }else{
            SFXSwitch.setChecked(false);
        }

        BGMSwitch=(Switch)findViewById(R.id.Switch_S_Music);
        if(app.getBGM_state()){
            BGMSwitch.setChecked(true);
        }else{
            BGMSwitch.setChecked(false);
        }


        SFXSwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            app.setsFX_state(true);


                        }else{
                            app.setsFX_state(false);

                        }
                    }
                }


        );

        BGMSwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            app.setBGM_state(true);
                            app.musicPlay();
                        }else{
                            app.setBGM_state(false);
                            app.musicStop();
                        }
                    }
                }


        );
    }






}