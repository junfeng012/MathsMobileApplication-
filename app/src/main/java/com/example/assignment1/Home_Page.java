package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Home_Page extends AppCompatActivity {
    protected Assignment1ApplicationClass app;
    Button btnGame, btnFriend, btnLeaderboard, btnAchievement, btnSettings, btnLogout;
    TextView txtUsername, txtScore;
    Bundle extra;
    String username,score;
     DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        DB = new DBHelper(this);
        app = (Assignment1ApplicationClass)getApplication();
        txtUsername=(TextView)findViewById(R.id.Text_HP_Name);
        txtScore=(TextView)findViewById(R.id.Text_HP_Score);
        extra =getIntent().getExtras();
        username=extra.getString("username");
        txtUsername.setText("Username:"+username);
        score=DB.getTotalScore(username);
        txtScore.setText("Total Score:"+score);


        btnGame=(Button)findViewById(R.id.Button_HP_Game);
        btnGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();
                Intent a=new Intent(Home_Page.this, Game_Page2.class);
                a.putExtra("username",username);
                a.putExtra("score",score);
                startActivity(a);
                finish();
            }
        });
        btnFriend=(Button)findViewById(R.id.Button_HP_Friend);
        btnFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();
                Intent b=new Intent(Home_Page.this, Friends_Page.class);
                b.putExtra("username",username);
                b.putExtra("score",score);
                startActivity(b);
                finish();
            }
        });
        btnLeaderboard=(Button)findViewById(R.id.Button_HP_Leaderboard);
        btnLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();
                Intent c=new Intent(Home_Page.this, Leaderboard.class);
                c.putExtra("username",username);
                c.putExtra("score",score);
                startActivity(c);
                finish();
            }
        });
        btnAchievement=(Button)findViewById(R.id.Button_HP_Achievement);
        btnAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();
                Intent d=new Intent(Home_Page.this, Achievement.class);
                d.putExtra("username",username);
                startActivity(d);
                finish();
            }
        });
        btnSettings=(Button)findViewById(R.id.Button_HP_Settings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();
                Intent e=new Intent(Home_Page.this, Settings.class);
                e.putExtra("username", username);
                startActivity(e);
                finish();
            }
        });
        btnLogout=(Button)findViewById(R.id.Button_HP_Logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent f=new Intent(Home_Page.this, Main_Page.class);
                startActivity(f);
                app.playGSoundCoin();
                finish();
            }
        });
    }
}