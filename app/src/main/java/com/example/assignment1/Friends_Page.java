package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Friends_Page extends AppCompatActivity {
    protected Assignment1ApplicationClass app;
    Button btnAdd ;
    Button btnBack;
    ListView friends ;
    EditText frndaddtxt ;
    DBHelper DB;
    Bundle extra;
    String username;
    TextView txtUsername;
    String score;
    TextView txtScore;
    String[] friendlist;
    Boolean test;
    String input;
    List<String> friendlstview;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_page);
        app = (Assignment1ApplicationClass)getApplication();
        frndaddtxt = (EditText) findViewById(R.id.Input_FP_Name) ;
        friends = (ListView)findViewById(R.id.listviewtext);
        txtUsername = (TextView)findViewById(R.id.Text_FP_Name);
        txtScore = (TextView)findViewById(R.id.Text_FP_Score);
        DB = new DBHelper(this);

        extra =getIntent().getExtras();
        username=extra.getString("username");
        txtUsername.setText("Username:"+username);
        score=DB.getTotalScore(username);
        txtScore.setText("Total Score:"+score);
        friendlist = DB.getFriendName(username);

        friendlstview = new ArrayList<String>(Arrays.asList(friendlist));
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, friendlstview);
        friends.setAdapter(arrayAdapter);

        btnAdd = (Button)findViewById(R.id.Button_FP_Add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                app.playGSoundCoin();
                input = frndaddtxt.getText().toString().trim();

                    if(input.equals(username))
                    {Toast.makeText(Friends_Page.this, "You can not add yourself", Toast.LENGTH_SHORT).show();}
                    else {
                        test = DB.insertFriend(username, input);
                        if (test == true) { //this line
                            Toast.makeText(Friends_Page.this, "Friend Added", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Friends_Page.this, "Friend Does Not Exist", Toast.LENGTH_SHORT).show();
                        }
                        friendlist = DB.getFriendName(username);
                        friendlstview = new ArrayList<String>(Arrays.asList(friendlist));
                        updatedData(friendlist);
                        friends.setAdapter(arrayAdapter);
                    }

            }
        });
        btnBack=(Button)findViewById(R.id.bttnbackFP);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                app.playGSoundCoin();
                Intent d=new Intent(Friends_Page.this, Home_Page.class);
                d.putExtra("username",username);
                startActivity(d);
                finish();
            }
        });
    }
    public void updatedData(String[] itemsArrayList) {
        arrayAdapter.clear();
        if (itemsArrayList != null){
            for (String x : itemsArrayList) {
                arrayAdapter.insert(x, arrayAdapter.getCount());
            }
        }
        arrayAdapter.notifyDataSetChanged();

    }
}