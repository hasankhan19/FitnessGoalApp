package com.example.fitnessgoalapp;
import com.example.fitnessgoalapp.model.UserModel;
import com.example.fitnessgoalapp.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserData extends AppCompatActivity {
    private EditText firstNameEditText, lastNameEditText, heightEditText, weightEditText, ageEditText, healthConditionEditText;
    private Button saveButton;

    UserModel userModel;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        heightEditText = findViewById(R.id.heightEditText);
        weightEditText = findViewById(R.id.weightEditText);
        ageEditText = findViewById(R.id.ageEditText);
        healthConditionEditText = findViewById(R.id.healthConditionEditText);
        saveButton = findViewById(R.id.saveButton);
        //  email = getIntent().getExtras().getString("email");
        getFirstname();


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFirstname(); // Call the setFirstname method for validation
                saveUserData(); // Save user data
                Intent intent = new Intent(getApplicationContext(), GoalChoice.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void setFirstname() {
        String firstname = firstNameEditText.getText().toString();
        if (firstname.isEmpty() || firstname.length() < 3) {
            firstNameEditText.setError("FirstName length should be at least 3 chars");
            return;
        }

        if (userModel != null) {
            userModel.setFirstname(firstname);
        }else {
            userModel = new UserModel(email,firstname,FirebaseUtil.currentUserId()); // Instantiate userModel if null
        }



        FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Handle completion if needed
                }
            }
        });
    }

    void getFirstname() {
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    userModel = task.getResult().toObject(UserModel.class);
                    if (userModel != null) {
                        firstNameEditText.setText(userModel.getFirstname());
                    }
                }
            }
        });
    }

    private void saveUserData() {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String height = heightEditText.getText().toString();
        String weight = weightEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String healthCondition = healthConditionEditText.getText().toString();

        // Perform validation if needed

        // Save user data to Firestore
        if (userModel == null) {
            userModel = new UserModel(); // Instantiate userModel if null
        }

        userModel.setFirstname(firstName);
        userModel.setLastName(lastName);
        userModel.setHeight(height);
        userModel.setWeight(weight);
        userModel.setAge(age);
        userModel.setHealthCondition(healthCondition);

        // Save to Firestore
        FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Display a Toast message
                    Toast.makeText(UserData.this, "User data saved to Firestore", Toast.LENGTH_SHORT).show();

                    // Save to Firebase Realtime Database
                    saveToRealtimeDatabase(firstName, lastName, height, weight, age, healthCondition);

                    // Move to the next activity
                    Intent intent = new Intent(getApplicationContext(), GoalChoice.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Log the error
                    Log.e("UserData", "Error saving user data to Firestore", task.getException());

                    // Display an error message to the user
                    Toast.makeText(UserData.this, "Failed to save user data. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveToRealtimeDatabase(String firstName, String lastName, String height, String weight, String age, String healthCondition) {
        // Save user data to Firebase Realtime Database
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").push();
        userRef.child("firstName").setValue(firstName);
        userRef.child("lastName").setValue(lastName);
        userRef.child("height").setValue(height);
        userRef.child("weight").setValue(weight);
        userRef.child("age").setValue(age);
        userRef.child("healthCondition").setValue(healthCondition);
    }
}