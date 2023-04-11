package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class Game_Page_Polar extends AppCompatActivity {
    protected Assignment1ApplicationClass app;
    private RadioGroup radioYesNo;
    private RadioButton rbTrue;
    private RadioButton rbFalse;
    private ImageView questionImage;
    private Bundle extra;
    private TextView txtPolarQuestionNumber;
    private TextView txtCurrentScore;
    private TextView txtTimer;
    private CountDownTimer countDownTimer;
    private Boolean timerRunning;
    private Button btnAnswer;

    private DBHelper DB;
    private String username;
    private  String gameSubject;
    private String gameMode;
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
        setContentView(R.layout.activity_game_page_polar);
        startTimer();

        app = (Assignment1ApplicationClass)getApplication();
        txtPolarQuestionNumber =(TextView)findViewById(R.id.Text_Polar_QuestionNumber);
        txtCurrentScore =(TextView)findViewById(R.id.Text_Polar_CurrentScore);
        txtTimer=(TextView)findViewById(R.id.Text_Polar_Timer);
        radioYesNo=(RadioGroup)findViewById(R.id.radioYesNo);
        rbTrue=(RadioButton)findViewById(R.id.radioYes);
        rbFalse=(RadioButton)findViewById(R.id.radioNo);
        questionImage=(ImageView) findViewById(R.id.polarQuestionImage);
        DB = new DBHelper(this);
        gameMode="Polar";
        questionNumber = 1;
        currentScore=0;


        extra =getIntent().getExtras();
        gameSubject=extra.getString("gameSubject");
        username=extra.getString("username");

        Toast.makeText(Game_Page_Polar.this,"The game subject "+gameSubject+" and the game mode "+gameMode+ " is selected",Toast.LENGTH_SHORT).show();


        getQuestion(String.valueOf(questionNumber),gameSubject,gameMode);

        btnAnswer =(Button) findViewById(R.id.Button_Polar_Answer);
        btnAnswer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                app.playGSoundCoin();
                if(rbTrue.isChecked()||rbFalse.isChecked())
                {
                    if (btnAnswer.getText().toString().equals("Answer")) {

                        checkAnswer(String.valueOf(questionNumber), gameSubject, gameMode);

                        btnAnswer.setText("Next");



                    } else if (btnAnswer.getText().toString().equals("Next") && questionNumber < 5) {
                        questionNumber += 1;

                        getQuestion(String.valueOf(questionNumber), gameSubject, gameMode);

                        btnAnswer.setText("Answer");
                        txtPolarQuestionNumber.setText(String.valueOf(questionNumber) + "/5");

                        radioYesNo.clearCheck();
                        resetButtonColor();

                    } else if (btnAnswer.getText().toString().equals("Next") && questionNumber >= 5){
                        pauseTimer();
                        timerSecondLeft=Integer.parseInt(txtTimer.getText().toString());
                        totalScore=currentScore*400+currentScore*timerSecondLeft*2 ;
                        Toast.makeText(Game_Page_Polar.this, "END OF QUESTION \n Total Score:"+totalScore, Toast.LENGTH_LONG).show();
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
                        Intent a=new Intent(Game_Page_Polar.this, Home_Page.class);
                        a.putExtra("username",username);
                        startActivity(a);
                        finish();

                    }
                }
                else
                {
                    Toast.makeText(Game_Page_Polar.this,"Please Select an Answer",Toast.LENGTH_SHORT).show();
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
        if(rbTrue.isChecked())
        {
            selectedAnswer="1";

        }
        else
        {
           selectedAnswer="2";
        }

        correctAnswer=DB.getQuestionAnswerDB(questionNumber,gameSubject,gameMode);

        if(selectedAnswer.equals(correctAnswer) && rbTrue.isChecked())
        {
            currentScore +=1;
            txtCurrentScore.setText(String.valueOf(currentScore));
            rbTrue.setTextColor(Color.GREEN);
        }
        else if(selectedAnswer.equals(correctAnswer)&& rbFalse.isChecked())
        {
            currentScore +=1;
            txtCurrentScore.setText(String.valueOf(currentScore));
            rbFalse.setTextColor(Color.GREEN);
        }
        else if (!(selectedAnswer.equals(correctAnswer)) && rbTrue.isChecked())
        {
            rbTrue.setTextColor(Color.RED);
        }
        else
        {
            rbFalse.setTextColor(Color.RED);
        }

    }

    public void resetButtonColor()
    {
        rbTrue.setTextColor(Color.BLACK);
        rbFalse.setTextColor(Color.BLACK);
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
                Toast.makeText(Game_Page_Polar.this, "Timeout \n Total Score:"+totalScore, Toast.LENGTH_LONG).show();
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