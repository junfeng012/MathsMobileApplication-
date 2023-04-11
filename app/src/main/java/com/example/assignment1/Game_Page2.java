package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Game_Page2 extends AppCompatActivity {
    protected Assignment1ApplicationClass app;
    Button btnTrigo;
    Button btnCal;
    Button btnFactor;
    Button btnBack;
    TextView txtUsername, txtScore;
    Bundle extra;
    String username,score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page2);
        app = (Assignment1ApplicationClass)getApplication();
        txtUsername=(TextView)findViewById(R.id.Text_GP2_Name);
        txtScore=(TextView)findViewById(R.id.Text_GP2_Score);
        extra =getIntent().getExtras();
        username=extra.getString("username");
        score=extra.getString("score");
        txtUsername.setText("Username:"+username);
        txtScore.setText("Total Score:"+score);


        btnTrigo=(Button)findViewById(R.id.Button_GP2_Trigonometry);
        btnCal=(Button)findViewById(R.id.Button_GP2_Calculus);
        btnFactor=(Button)findViewById(R.id.Button_GP2_Factorization);
        btnBack=(Button)findViewById(R.id.Button_GP2_Back);
        btnTrigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();
                Intent a=new Intent(Game_Page2.this, Game_Page3.class);
                a.putExtra("gameSubject","Trigo");
                a.putExtra("username",username);
                a.putExtra("score",score);
                startActivity(a);
                finish();
            }
        });
        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();
                Intent b=new Intent(Game_Page2.this, Game_Page3.class);
                b.putExtra("gameSubject","Cal");
                b.putExtra("username",username);
                b.putExtra("score",score);
                startActivity(b);
                finish();
            }
        });
        btnFactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();
                Intent c=new Intent(Game_Page2.this, Game_Page3.class);
                c.putExtra("gameSubject","Factor");
                c.putExtra("username",username);
                c.putExtra("score",score);
                startActivity(c);
                finish();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();
                Intent d=new Intent(Game_Page2.this, Home_Page.class);
                d.putExtra("username",username);
                startActivity(d);
                finish();
            }
        });
    }
}