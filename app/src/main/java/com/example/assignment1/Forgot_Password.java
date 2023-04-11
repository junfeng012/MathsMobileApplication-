package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class Forgot_Password extends AppCompatActivity {
    protected Assignment1ApplicationClass app;
    Button btnReqPW, btnSendOTP;
    EditText email;
    DBHelper DB;
    String subject;
    char[] otp;

    private static char[] generateOTP(int length) {
        String numbers = "1234567890";
        Random random = new Random();
        char[] otp = new char[length];

        for(int i = 0; i< length ; i++) {
            otp[i] = numbers.charAt(random.nextInt(numbers.length()));
        }
        return otp;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email = (EditText) findViewById(R.id.Input_FG_Email);
        subject = "Request Password Verification Code";
        otp = generateOTP(6);
        String OTP = new String(otp);
        app = (Assignment1ApplicationClass)getApplication();
        DB = new DBHelper(this);

        btnSendOTP=(Button)findViewById(R.id.Button_FG_Send);
        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();
                String mail = email.getText().toString().trim();
                Boolean CheckEmail = DB.checkEmailExist(mail);
                if (CheckEmail == true) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + email.getText().toString()));
                    intent.putExtra(Intent.EXTRA_TEXT, OTP);
                    startActivity(intent);
                } else {
                    Toast.makeText(Forgot_Password.this, "This email does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReqPW=(Button)findViewById(R.id.Button_FG_Reset);
        btnReqPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();
                String mail = email.getText().toString().trim();
                Boolean CheckEmail = DB.checkEmailExist(mail);
                if (CheckEmail == true) {
                    Toast.makeText(Forgot_Password.this, "Email Exist", Toast.LENGTH_SHORT).show();
                    //sendEmail(OTP);
                    Intent a=new Intent(Forgot_Password.this, Forgot_Password2.class);
                    a.putExtra("otp",OTP);
                    a.putExtra("email",mail);

                    startActivity(a);
                    finish();
                } else {
                    Toast.makeText(Forgot_Password.this, "This email does not exist", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void sendEmail(String OTP) {
        String mEmail = email.getText().toString();
        String mSubject = subject;
        String mMessage = OTP;

        JavaMailAPI javaMailAPI = new JavaMailAPI(this, mEmail, mSubject, mMessage);

        javaMailAPI.execute();
    }


}