package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    protected Assignment1ApplicationClass app;
    Button btnBack, btnCreate;
    EditText email,username, password;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = (EditText) findViewById(R.id.Input_R_Email);
        username = (EditText) findViewById(R.id.Input_R_LoginID);
        password = (EditText) findViewById(R.id.Input_R_Password);
        DB = new DBHelper(this);
        app = (Assignment1ApplicationClass)getApplication();
        btnBack=(Button)findViewById(R.id.Button_R_Back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a=new Intent(Register.this, Main_Page.class);
                startActivity(a);
                app.playGSoundCoin();
                finish();
            }
        });

        btnCreate=(Button)findViewById(R.id.Button_R_NewAccount);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString().trim();
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                app.playGSoundCoin();


                if (user.equals("") || pass.equals("") || mail.equals(""))
                    Toast.makeText(Register.this, "Please enter details", Toast.LENGTH_SHORT).show();
                else {
                        if(checkEmailValid(mail)) {
                            Boolean checkUser = DB.checkEmailUsername(mail, user);
                            if (checkUser == false) {
                                Boolean insert = DB.insertData(mail, user, pass);
                                if (insert == true) {
                                    Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    Intent a = new Intent(Register.this, Main_Page.class);
                                    startActivity(a);
                                    finish();
                                } else {
                                    Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Register.this, "email or user already exists", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(Register.this, "Invalid email", Toast.LENGTH_SHORT).show();
                        }
                }



            }});

    }
    public boolean checkEmailValid(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(emailPattern))
        {return true;}
        else
        {
            return false;
        }
    }
}