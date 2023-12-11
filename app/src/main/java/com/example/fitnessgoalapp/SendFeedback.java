package com.example.fitnessgoalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SendFeedback extends AppCompatActivity {

    private DatabaseReference feedbackRef;
    private EditText username, feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feedback);

        Log.d("SendFeedback", "SendFeedback activity created");
        // Initialize Firebase Database reference
        feedbackRef = FirebaseDatabase.getInstance().getReference().child("Feedback");

        // Initialize EditText fields
        username = findViewById(R.id.username);
        feedback = findViewById(R.id.feedback);
    }

    public void feedbacksent(View view) {
        String usernameInput = username.getText().toString().trim();
        String feedbackInput = feedback.getText().toString().trim();

        if (usernameInput.isEmpty() || feedbackInput.isEmpty()) {
            // Show error message if either username or feedback is empty
            Toast.makeText(SendFeedback.this, "Please fill in both username and feedback.", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseReference newFeedbackRef = feedbackRef.push(); // Generate a unique key
            newFeedbackRef.child("Username").setValue(usernameInput);
            newFeedbackRef.child("Feedback").setValue(feedbackInput)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Clear EditText boxes
                            username.getText().clear();
                            feedback.getText().clear();

                            // Display success message
                            Toast.makeText(SendFeedback.this, "Feedback sent!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle failure in sending feedback
                            Toast.makeText(SendFeedback.this, "Failed to send feedback. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
