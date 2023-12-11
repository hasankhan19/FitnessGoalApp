package com.example.fitnessgoalapp;
import com.google.firebase.FirebaseApp;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        // Other initialization code if needed
    }
}