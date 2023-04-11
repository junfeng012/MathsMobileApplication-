package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Game_Page3 extends AppCompatActivity {
    protected Assignment1ApplicationClass app;
    Button btnPolar;
    Button btnMCQ;
    Button btnSubjective;
    Button btnBack;
    String gameSubject;
    String gameMode;
    String username,score;
    String polarScore,mcqScore,subScore;
    Bundle extra;
    TextView txtUsername, txtScore ;
    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page3);
        app = (Assignment1ApplicationClass)getApplication();
        DB = new DBHelper(this);

        txtUsername=(TextView)findViewById(R.id.Text_GP3_Name);
        txtScore=(TextView)findViewById(R.id.Text_GP3_Score);
        extra =getIntent().getExtras();
        gameSubject=extra.getString("gameSubject");
        username=extra.getString("username");
        score=extra.getString("score");
        txtUsername.setText("Username:"+username);
        txtScore.setText("Total Score:"+score);

        Toast.makeText(Game_Page3.this,"The game subject "+gameSubject+" is selected",Toast.LENGTH_SHORT).show();

        btnPolar =(Button)findViewById(R.id.Button_GP3_Polar);
        polarScore=DB.getGameModeScore(username,gameSubject,"Polar");
        btnPolar.setText("Polar : "+polarScore);
        btnPolar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();
                Intent a=new Intent(Game_Page3.this, Game_Page_Polar.class);
                gameMode="Polar";
                a.putExtra("gameSubject",gameSubject);
                a.putExtra("username",username);
                startActivity(a);
                finish();
            }
        });

        btnMCQ =(Button)findViewById(R.id.Button_GP3_MCQ);
        mcqScore=DB.getGameModeScore(username,gameSubject,"MCQ");
        btnMCQ.setText("MCQ : "+mcqScore);
        btnMCQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();
                Intent b=new Intent(Game_Page3.this, Game_Page_MCQ.class);
                gameMode="MCQ";
                b.putExtra("gameSubject",gameSubject);
                b.putExtra("username",username);
                startActivity(b);
                finish();
            }
        });

        btnSubjective =(Button)findViewById(R.id.Button_GP3_Subjective);
        subScore=DB.getGameModeScore(username,gameSubject,"SUB");
        btnSubjective.setText("Subjective : "+subScore);
        btnSubjective.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();
                Intent c=new Intent(Game_Page3.this, Game_Page_Subjective.class);
                gameMode="Subjective";
                c.putExtra("gameSubject",gameSubject);
                c.putExtra("username",username);
                startActivity(c);
                finish();
            }
        });

        btnBack=(Button)findViewById(R.id.Button_GP3_Back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();
                Intent d=new Intent(Game_Page3.this, Game_Page2.class);
                d.putExtra("username",username);
                d.putExtra("score",score);
                startActivity(d);
                finish();
            }
        });

    }
}