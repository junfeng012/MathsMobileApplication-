package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main_Page extends AppCompatActivity {
    protected Assignment1ApplicationClass app;
    Button btnLogin, btnForgotPassword, btnRegister;
    EditText username, password;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        app = (Assignment1ApplicationClass)getApplication();
        username = (EditText) findViewById(R.id.Input_MP_LoginID);
        password = (EditText) findViewById(R.id.Input_MP_Password);
        DB = new DBHelper(this);

        btnLogin=(Button)findViewById(R.id.Button_MP_Login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();


                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("")||pass.equals(""))
                    Toast.makeText(Main_Page.this, "Please fill in the fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkUserPass = DB.checkUsernamePassword(user,pass);
                    if(checkUserPass==true){
                        Toast.makeText(Main_Page.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent a=new Intent(Main_Page.this, Home_Page.class);
                        a.putExtra("username",user);
                        startActivity(a);
                        finish();
                    }else{
                        Toast.makeText(Main_Page.this, "ID or Password is wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnForgotPassword=(Button)findViewById(R.id.Button_MP_ForgotPassword);
        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();
                Intent a=new Intent(Main_Page.this, Forgot_Password.class);
                startActivity(a);
                finish();
            }
        });
        btnRegister=(Button)findViewById(R.id.Button_MP_Register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.playGSoundCoin();
                Intent a=new Intent(Main_Page.this, Register.class);
                startActivity(a);
                finish();
            }
        });
    }
}