package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Achievement extends AppCompatActivity {
    protected Assignment1ApplicationClass app;
    Button btnBack;
    TextView txtUsername;
    DBHelper DB;
    Bundle extra;
    String username, score;
    ImageButton Ach1, Ach2, Ach3, Ach4, Ach5, Ach6, BAch1, BAch2, BAch3, BAch4, BAch5, BAch6;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        DB = new DBHelper(this);
        app = (Assignment1ApplicationClass)getApplication();
        txtUsername = (TextView)findViewById(R.id.Text_A_Name);
        extra =getIntent().getExtras();
        username=extra.getString("username");
        txtUsername.setText("Username:"+username);
        score=DB.getTotalScore(username);
        counter=0;

        String[] ach = DB.getAchievement(username);

        Ach1=(ImageButton)findViewById(R.id.Image_Button_1);
        BAch1=(ImageButton)findViewById(R.id.Image_Button_1_Black);
        Ach1.setVisibility(View.GONE);
        BAch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();
                ShowStatus(ach, 0);
                if (counter > 0) {
                    BAch1.setVisibility(View.GONE);
                    Ach1.setVisibility(View.VISIBLE);
                }
                counter = 0;
            }
        });
        Ach1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowStatus(ach, 0);
                app.playGSoundCoin();
                counter = 0;
            }
        });

        Ach2=(ImageButton)findViewById(R.id.Image_Button_2);
        BAch2=(ImageButton)findViewById(R.id.Image_Button_2_Black);
        Ach2.setVisibility(View.GONE);
        BAch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();
                ShowStatus(ach, 1);
                if (counter > 0) {
                    BAch2.setVisibility(View.GONE);
                    Ach2.setVisibility(View.VISIBLE);
                }
                counter = 0;
            }
        });
        Ach2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowStatus(ach, 0);
                app.playGSoundCoin();
                counter = 0;
            }
        });

        Ach3=(ImageButton)findViewById(R.id.Image_Button_3);
        BAch3=(ImageButton)findViewById(R.id.Image_Button_3_Black);
        Ach3.setVisibility(View.GONE);
        BAch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();
                ShowStatus(ach, 2);
                if (counter > 0) {
                    BAch3.setVisibility(View.GONE);
                    Ach3.setVisibility(View.VISIBLE);
                }
                counter = 0;
            }
        });
        Ach3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowStatus(ach, 0);
                app.playGSoundCoin();
                counter = 0;
            }
        });

        Ach4=(ImageButton)findViewById(R.id.Image_Button_4);
        BAch4=(ImageButton)findViewById(R.id.Image_Button_4_Black);
        Ach4.setVisibility(View.GONE);
        BAch4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();
                ShowStatus(ach, 3);
                if (counter > 0) {
                    BAch4.setVisibility(View.GONE);
                    Ach4.setVisibility(View.VISIBLE);
                }
                counter = 0;
            }
        });
        Ach4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowStatus(ach, 0);
                app.playGSoundCoin();
                counter = 0;
            }
        });

        Ach5=(ImageButton)findViewById(R.id.Image_Button_5);
        BAch5=(ImageButton)findViewById(R.id.Image_Button_5_Black);
        Ach5.setVisibility(View.GONE);
        BAch5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();
                ShowStatus(ach, 4);
                if (counter > 0) {
                    BAch5.setVisibility(View.GONE);
                    Ach5.setVisibility(View.VISIBLE);
                }
                counter = 0;
            }
        });
        Ach5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowStatus(ach, 0);
                app.playGSoundCoin();
                counter = 0;
            }
        });

        Ach6=(ImageButton)findViewById(R.id.Image_Button_6);
        BAch6=(ImageButton)findViewById(R.id.Image_Button_6_Black);
        Ach6.setVisibility(View.GONE);
        BAch6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowStatus(ach, 5);
                if (counter > 0) {
                    BAch6.setVisibility(View.GONE);
                    Ach6.setVisibility(View.VISIBLE);
                }
                counter = 0;
            }
        });
        Ach6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowStatus(ach, 0);
                app.playGSoundCoin();
                counter = 0;
            }
        });

        btnBack=(Button)findViewById(R.id.Button_A_Back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a=new Intent(Achievement.this, Home_Page.class);
                a.putExtra("username",username);
                startActivity(a);
                finish();
            }
        });
    }

    private void ShowStatus(String[] arr, int i) {
        if (arr[i].equals("0")) {
            Toast.makeText(Achievement.this, "Please obtain a higher score on this section.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Achievement.this, "You have accomplish this achievement.", Toast.LENGTH_SHORT).show();
            counter++;
        }
    }
}