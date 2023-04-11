package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Game_Page_MCQ extends AppCompatActivity {
    protected Assignment1ApplicationClass app;

    private ImageView questionImage;
    private Bundle extra;
    private TextView txtMCQQuestionNumber;
    private TextView txtCurrentScore;
    private TextView txtTimer;
    private CountDownTimer countDownTimer;
    private Button btnA;
    private Button btnB;
    private Button btnC;
    private Button btnD;
    private Button btnAnswer;


    private DBHelper DB;
    private String gameSubject;
    private String gameMode;
    private String username;
    private String questionName;
    private String selectedAnswer;
    private String correctAnswer;
    private int questionNumber;
    private int currentScore;
    private int totalScore;
    private int timerSecondLeft;
    private static final long START_TIME_IN_MILLIS = 300000;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;






    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page_mcq);
        startTimer();


        txtMCQQuestionNumber =(TextView)findViewById(R.id.Text_MCQ_QuestionNumber);
        txtCurrentScore =(TextView)findViewById(R.id.Text_MCQ_CurrentScore);
        txtTimer=(TextView)findViewById(R.id.Text_MCQ_Timer);
        app = (Assignment1ApplicationClass)getApplication();
        questionImage=(ImageView) findViewById(R.id.mcqQuestionImage);
        DB = new DBHelper(this);
        gameMode="MCQ";
        questionNumber = 1;
        currentScore=0;


        extra =getIntent().getExtras();
        gameSubject=extra.getString("gameSubject");
        username=extra.getString("username");

        Toast.makeText(Game_Page_MCQ.this,"The game subject "+gameSubject+" and the game mode "+gameMode+ " is selected",Toast.LENGTH_LONG).show();

        getQuestion(String.valueOf(questionNumber),gameSubject,gameMode);

        btnA=(Button)findViewById(R.id.Button_MCQ_A);
        btnA.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                app.playGSoundCoin();
                selectedAnswer="A";
                Toast.makeText(Game_Page_MCQ.this, "Answer A is selected", Toast.LENGTH_SHORT).show();
            }
        });

        btnB=(Button)findViewById(R.id.Button_MCQ_B);
        btnB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                app.playGSoundCoin();
                selectedAnswer="B";
                Toast.makeText(Game_Page_MCQ.this, "Answer B is selected", Toast.LENGTH_SHORT).show();
            }
        });

        btnC=(Button)findViewById(R.id.Button_MCQ_C);
        btnC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                app.playGSoundCoin();
                selectedAnswer="C";
                Toast.makeText(Game_Page_MCQ.this, "Answer C is selected", Toast.LENGTH_SHORT).show();
            }
        });

        btnD=(Button)findViewById(R.id.Button_MCQ_D);
        btnD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                app.playGSoundCoin();
                selectedAnswer="D";
                Toast.makeText(Game_Page_MCQ.this, "Answer D is selected", Toast.LENGTH_SHORT).show();
            }
        });

        btnAnswer =(Button) findViewById(R.id.Button_MCQ_Answer);
        btnAnswer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                app.playGSoundCoin();
                if(selectedAnswer!="0")
                {
                    if (btnAnswer.getText().toString().equals("Answer")) {

                        checkAnswer(String.valueOf(questionNumber), gameSubject, gameMode);

                        btnAnswer.setText("Next");



                    } else if (btnAnswer.getText().toString().equals("Next") && questionNumber < 5) {
                        questionNumber += 1;

                        getQuestion(String.valueOf(questionNumber), gameSubject, gameMode);
                        selectedAnswer="0";
                        btnAnswer.setText("Answer");
                        txtMCQQuestionNumber.setText(String.valueOf(questionNumber) + "/5");


                        resetButtonColor();

                    } else if (btnAnswer.getText().toString().equals("Next") && questionNumber >= 5){
                        pauseTimer();
                        timerSecondLeft=Integer.parseInt(txtTimer.getText().toString());
                        totalScore=currentScore*400+currentScore*timerSecondLeft*2 ;
                        Toast.makeText(Game_Page_MCQ.this, "END OF QUESTION \n Total Score:"+totalScore, Toast.LENGTH_LONG).show();
                        btnAnswer.setText("End");
                    }
                    else if (btnAnswer.getText().toString().equals("End"))
                    {
                        //save data
                        DB.updateScore(username,gameSubject,gameMode,totalScore);
                        if (totalScore > 4000) {
                            DB.setAchievement(username, gameMode);
                        }

                        //back to homepage
                        Intent a=new Intent(Game_Page_MCQ.this, Home_Page.class);
                        a.putExtra("username",username);
                        startActivity(a);
                        finish();

                    }
                }
                else
                {
                    Toast.makeText(Game_Page_MCQ.this,"Please Select an Answer",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getQuestion(String questionNumber,String gameSubject,String gameMode)
    {
        questionName=DB.getFirstQuestionDB(questionNumber,gameSubject,gameMode);
        int resID = getResources().getIdentifier(questionName,"drawable",getPackageName());
        questionImage.setImageResource(resID);

    }



    public void checkAnswer(String questionNumber,String gameSubject,String gameMode)
    {
        correctAnswer=DB.getQuestionAnswerDB(questionNumber,gameSubject,gameMode);

        if(selectedAnswer.equals(correctAnswer) && selectedAnswer.equals("A"))
        {
            currentScore +=1;
            txtCurrentScore.setText(String.valueOf(currentScore));
            btnA.setBackgroundColor(Color.GREEN);
        }
        else if (selectedAnswer.equals(correctAnswer) && selectedAnswer.equals("B"))
        {
            currentScore +=1;
            txtCurrentScore.setText(String.valueOf(currentScore));
            btnB.setBackgroundColor(Color.GREEN);
        }
        else if (selectedAnswer.equals(correctAnswer) && selectedAnswer.equals("C"))
        {
            currentScore +=1;
            txtCurrentScore.setText(String.valueOf(currentScore));
            btnC.setBackgroundColor(Color.GREEN);
        }
        else if (selectedAnswer.equals(correctAnswer) && selectedAnswer.equals("D"))
        {
            currentScore +=1;
            txtCurrentScore.setText(String.valueOf(currentScore));
            btnD.setBackgroundColor(Color.GREEN);
        }
        else if (!selectedAnswer.equals(correctAnswer) && selectedAnswer.equals("A"))
        {
            btnA.setBackgroundColor(Color.RED);
        }
        else if (!selectedAnswer.equals(correctAnswer) && selectedAnswer.equals("B"))
        {
            btnB.setBackgroundColor(Color.RED);
        }
        else if (!selectedAnswer.equals(correctAnswer)&& selectedAnswer.equals("C"))
        {
            btnC.setBackgroundColor(Color.RED);
        }
        else if (!selectedAnswer.equals(correctAnswer) && selectedAnswer.equals("D"))
        {
            btnD.setBackgroundColor(Color.RED);
        }

    }

    private void resetButtonColor()
    {
        btnA.setBackgroundColor(Color.rgb(98,0,238));
        btnB.setBackgroundColor(Color.rgb(98,0,238));
        btnC.setBackgroundColor(Color.rgb(98,0,238));
        btnD.setBackgroundColor(Color.rgb(98,0,238));
    }





    private void startTimer()
    {
        countDownTimer = new CountDownTimer(timeLeftInMillis,1000)
        {

            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis=millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                totalScore=currentScore*400+currentScore*timerSecondLeft*2 ;
                Toast.makeText(Game_Page_MCQ.this, "Timeout \n Total Score:"+totalScore, Toast.LENGTH_SHORT).show();
                btnAnswer.setText("End");
            }
        }.start();
    }
    private void pauseTimer() {
        countDownTimer.cancel();
    }

    private void updateCountDownText()
    {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        int totalSeconds = minutes*60 +seconds;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%03d", totalSeconds);
        txtTimer.setText(timeLeftFormatted);
    }

}