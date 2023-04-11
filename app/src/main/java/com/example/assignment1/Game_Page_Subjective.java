package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;

public class Game_Page_Subjective extends AppCompatActivity {
    protected Assignment1ApplicationClass app;

    private ImageView questionImage;
    private Bundle extra;
    private TextView txtSubjectiveQuestionNumber;
    private TextView txtCurrentScore;
    private TextView txtTimer;
    private TextView txtAnswer;
    private EditText edAnswer;
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
        app = (Assignment1ApplicationClass)getApplication();
        setContentView(R.layout.activity_game_page_subjective);
        startTimer();


        txtSubjectiveQuestionNumber =(TextView)findViewById(R.id.Text_Subjective_QuestionNumber);
        txtCurrentScore =(TextView)findViewById(R.id.Text_Subjective_CurrentScore);
        txtTimer=(TextView)findViewById(R.id.Text_Subjective_Timer);
        txtAnswer=(TextView) findViewById(R.id.Text_Subjective_Answer);
        edAnswer=(EditText)findViewById(R.id.Input_Subjective_Answer);
        questionImage=(ImageView) findViewById(R.id.subjectiveQuestionImage);
        DB = new DBHelper(this);
        gameMode="SUB";
        questionNumber = 1;
        currentScore=0;


        extra =getIntent().getExtras();
        Log.d("Subject",extra.getString("gameSubject"));
        gameSubject=extra.getString("gameSubject");
        username=extra.getString("username");

        Toast.makeText(Game_Page_Subjective.this,"The game subject "+gameSubject+" and the game mode "+gameMode+ " is selected",Toast.LENGTH_SHORT).show();


        getQuestion(String.valueOf(questionNumber),gameSubject,gameMode);

        btnAnswer =(Button) findViewById(R.id.Button_Subjective_Answer);
        btnAnswer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                app.playGSoundCoin();
                selectedAnswer=edAnswer.getText().toString();

                if(selectedAnswer.length()>0)
                {
                    if (btnAnswer.getText().toString().equals("Answer")) {

                        checkAnswer(String.valueOf(questionNumber), gameSubject, gameMode);

                        btnAnswer.setText("Next");



                    } else if (btnAnswer.getText().toString().equals("Next") && questionNumber < 5) {
                        questionNumber += 1;

                        getQuestion(String.valueOf(questionNumber), gameSubject, gameMode);

                        btnAnswer.setText("Answer");
                        txtSubjectiveQuestionNumber.setText(String.valueOf(questionNumber) + "/5");


                        resetColor();

                    } else if (btnAnswer.getText().toString().equals("Next") && questionNumber >= 5){
                        pauseTimer();
                        timerSecondLeft=Integer.parseInt(txtTimer.getText().toString());
                        totalScore=currentScore*400+currentScore*timerSecondLeft*2 ;
                        Toast.makeText(Game_Page_Subjective.this, "END OF QUESTION \n Total Score:"+totalScore, Toast.LENGTH_LONG).show();
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
                        Intent a=new Intent(Game_Page_Subjective.this, Home_Page.class);
                        a.putExtra("username",username);
                        startActivity(a);
                        finish();

                    }
                }
                else
                {
                    Toast.makeText(Game_Page_Subjective.this,"Please enter an Answer",Toast.LENGTH_SHORT).show();
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

        selectedAnswer=selectedAnswer.replaceAll("\\s+","");

        if(selectedAnswer.equals(correctAnswer))
        {
            currentScore +=1;
            txtCurrentScore.setText(String.valueOf(currentScore));
            txtAnswer.setTextColor(Color.GREEN);
        }
        else
        {
            txtAnswer.setTextColor(Color.RED);
        }


    }

    public void resetColor()
    {
        edAnswer.setText("");
        txtAnswer.setTextColor(Color.BLACK);
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
                Toast.makeText(Game_Page_Subjective.this, "Timeout \n Total Score:"+totalScore, Toast.LENGTH_LONG).show();
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