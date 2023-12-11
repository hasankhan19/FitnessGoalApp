package com.example.fitnessgoalapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CalorieCalculator extends AppCompatActivity {

    private EditText daysToLoseWeightEditText, heightEditText, weightEditText;
    private Button calculateButton;
    private TextView calorieLimitTextView, timeframeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_calculator);

        daysToLoseWeightEditText = findViewById(R.id.daysToLoseWeightEditText);
        heightEditText = findViewById(R.id.heightEditText);
        weightEditText = findViewById(R.id.weightEditText);
        calculateButton = findViewById(R.id.calculateButton);
        calorieLimitTextView = findViewById(R.id.calorieLimitTextView);
        timeframeTextView = findViewById(R.id.timeframeTextView);

        // Fetch user data from Firebase when the activity is created
        fetchUserDataFromFirebase();

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCalorieLimitAndTimeframe();
            }
        });
    }

    private void fetchUserDataFromFirebase() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

            // Attach a ValueEventListener to retrieve the user's data
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // Check if the data snapshot exists
                    if (snapshot.exists()) {
                        // Retrieve height and weight from the snapshot
                        String height = snapshot.child("height").getValue(String.class);
                        String weight = snapshot.child("weight").getValue(String.class);

                        // Update the EditTexts with retrieved data
                        heightEditText.setText(height);
                        weightEditText.setText(weight);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle errors if needed
                    Toast.makeText(CalorieCalculator.this, "Error fetching user data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void calculateCalorieLimitAndTimeframe() {
        // Retrieve user inputs
        String daysToLoseWeightStr = daysToLoseWeightEditText.getText().toString().trim();
        String heightStr = heightEditText.getText().toString().trim();
        String weightStr = weightEditText.getText().toString().trim();

        if (daysToLoseWeightStr.isEmpty() || heightStr.isEmpty() || weightStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse input values
        int daysToLoseWeight = Integer.parseInt(daysToLoseWeightStr);
        double height = Double.parseDouble(heightStr);
        double weight = Double.parseDouble(weightStr);

        // Calculate calorie limit and timeframe (replace with your own calculation logic)
        double calorieLimit = calculateCalorieLimit(height, weight);
        String timeframe = calculateTimeframe(daysToLoseWeight);

        // Display results
        calorieLimitTextView.setText("Calorie Limit: " + calorieLimit);
        timeframeTextView.setText("Timeframe: " + timeframe);

        // Save data to Firebase Realtime Database
        saveDataToFirebase(daysToLoseWeight, height, weight, calorieLimit, timeframe);
    }

    private double calculateCalorieLimit(double height, double weight) {
        // Replace with your own calorie limit calculation logic
        // Example calculation (replace this with a proper formula)
        return 10 * weight + 6.25 * height - 5;
    }

    private String calculateTimeframe(int daysToLoseWeight) {
        // Replace with your own timeframe calculation logic
        // Example calculation (replace this with a proper formula)
        return daysToLoseWeight + " days";
    }

    private void saveDataToFirebase(int daysToLoseWeight, double height, double weight, double calorieLimit, String timeframe) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Save user data to Firebase Realtime Database under 'calorie_data' node
            DatabaseReference calorieDataRef = FirebaseDatabase.getInstance().getReference().child("calorie_data").child(userId);

            // Create a map to represent the data
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("daysToLoseWeight", daysToLoseWeight);
            dataMap.put("height", height);
            dataMap.put("weight", weight);
            dataMap.put("calorieLimit", calorieLimit);
            dataMap.put("timeframe", timeframe);

            // Update the data in the database
            calorieDataRef.updateChildren(dataMap);

            Toast.makeText(this, "Data saved to Firebase", Toast.LENGTH_SHORT).show();
        }
    }
}
