package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class Forgot_Password2 extends AppCompatActivity {
    protected Assignment1ApplicationClass app;
    Button btnResetPW;
    EditText codes, passcode;
    DBHelper DB;
    Bundle extra;
    String OTP, mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password2);
        codes = (EditText) findViewById(R.id.Input_FG2_Code);
        passcode = (EditText) findViewById(R.id.Input_FG2_NewPassword);
        extra =getIntent().getExtras();
        OTP=extra.getString("otp");
        mail=extra.getString("email");
        app = (Assignment1ApplicationClass)getApplication();
        DB = new DBHelper(this);

        btnResetPW=(Button)findViewById(R.id.Button_FG2_Reset);
        btnResetPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();
                String code = codes.getText().toString().trim();
                String password = passcode.getText().toString().trim();
                if (code .equals (OTP)) {
                    if (DB.updatePassword(mail, password)) {
                        Toast.makeText(Forgot_Password2.this, "OTP Correct", Toast.LENGTH_SHORT).show();
                        Intent a = new Intent(Forgot_Password2.this, Main_Page.class);
                        startActivity(a);
                        finish();
                    } else {
                        Toast.makeText(Forgot_Password2.this, "Password Invalid", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Forgot_Password2.this, "OTP Incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}