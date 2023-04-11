package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import java.util.*;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class Leaderboard extends AppCompatActivity {
    protected Assignment1ApplicationClass app;
    Button btnBack;
    DBHelper DB;
    Bundle extra;
    String username;
    TextView txtUsername;
    String score;
    String leaderboard;
    TextView txtScore;
    int arrayLength;
    String[][] leaderboardArray;
    String[] leaderboardUser;
    String[] leaderboardName;
    int[] leaderboardScore;
    String[] leaderboardScore2;
    String[] leaderboardFinalArr;
    Boolean test;
    ListView nameLV;
    List<String> leaderboardlstview;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        txtUsername = (TextView)findViewById(R.id.Text_L_Name);
        txtScore = (TextView)findViewById(R.id.Text_L_Score);
        nameLV = (ListView)findViewById(R.id.List_L_LeaderboardName);
        app = (Assignment1ApplicationClass)getApplication();
        DB = new DBHelper(this);

        extra =getIntent().getExtras();
        username=extra.getString("username");
        txtUsername.setText("Username:"+username);
        score=DB.getTotalScore(username);
        txtScore.setText("Total Score:"+score);
        leaderboardArray =DB.getFriendNameScore(username);
        arrayLength = leaderboardArray.length;
        leaderboardName = new String[arrayLength+1];
        leaderboardScore2 = new String[arrayLength+1];
        leaderboardFinalArr = new String[arrayLength+1];
        leaderboardScore = new int[arrayLength+1];


        for (int i = 0; i < leaderboardArray.length; i++) {
            leaderboardName[i] = leaderboardArray[i][0];
            leaderboardScore[i] = Integer.parseInt(leaderboardArray[i][1]);
        }
        leaderboardName[arrayLength] =username ;
        leaderboardScore[arrayLength] = Integer.parseInt(score);


        selectionSort(leaderboardScore,leaderboardName);

        for (int i = 0; i < leaderboardArray.length+1; i++) {
            leaderboardScore2[i] =String.valueOf(leaderboardScore[i]);
        }

        for (int i = 0; i < leaderboardArray.length+1; i++) {
           leaderboard = String.valueOf(i+1) +")Name:" +leaderboardName[i] + "    Score:"+leaderboardScore2[i];
           leaderboardFinalArr[i] = leaderboard;
        }




        leaderboardlstview = new ArrayList<String>(Arrays.asList(leaderboardFinalArr));
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, leaderboardlstview);
        nameLV.setAdapter(arrayAdapter);



        btnBack=(Button)findViewById(R.id.Button_L_Back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                app.playGSoundCoin();
                Intent a=new Intent(Leaderboard.this, Home_Page.class);
                a.putExtra("username",username);
                startActivity(a);
                finish();
            }
        });

    }
    public static void selectionSort(int[] arr,String[] arr2){
        for (int i = 0; i < arr.length - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < arr.length; j++){
                if (arr[j] > arr[index]){
                    index = j;//searching for lowest index
                }
            }
            int biggerNumber = arr[index];
            arr[index] = arr[i];
            arr[i] = biggerNumber;

            String biggerName = arr2[index];
            arr2[index] = arr2[i];
            arr2[i] = biggerName;

        }

    }
}