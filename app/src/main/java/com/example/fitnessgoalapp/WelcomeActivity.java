package com.example.fitnessgoalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {
    private Button btnUser;
    private Button btnTrainer;
    private Button btn_sendFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnUser = (Button) findViewById(R.id.btn_userLogin);
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });

        btnTrainer = (Button) findViewById(R.id.btn_trainerLogin);
        btnTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginTrainerActivity();
            }
        });

        btn_sendFeedback = (Button) findViewById(R.id.btn_sendFeedback);
        btn_sendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSendFeedbackActivity();
            }
        });
    }

    public void openLoginActivity(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }


    public void openLoginTrainerActivity(){
        Intent intent = new Intent(this, LoginTrainer.class);
        startActivity(intent);
    }

    public void openSendFeedbackActivity(){
        Intent intent = new Intent(this, SendFeedback.class);
        startActivity(intent);
    }
}