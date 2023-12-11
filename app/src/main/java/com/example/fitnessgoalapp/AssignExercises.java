package com.example.fitnessgoalapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssignExercises extends AppCompatActivity {

    private TextView userNameTextView;
    private TextView fitnessGoalsTextView;
    private Spinner userSpinner;
    private Spinner exerciseSpinner;
    private Button saveButton;

    private FirebaseFirestore firestore;
    private List<String> dummyExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_exercises);

        userNameTextView = findViewById(R.id.userNameTextView);
        fitnessGoalsTextView = findViewById(R.id.fitnessGoalsTextView);
        userSpinner = findViewById(R.id.userSpinner);
        exerciseSpinner = findViewById(R.id.exerciseSpinner);
        saveButton = findViewById(R.id.saveButton);

        firestore = FirebaseFirestore.getInstance();

        // Dummy exercise data (replace with actual exercises)
        dummyExercises = Arrays.asList("Exercise 1", "Exercise 2", "Exercise 3");

        // Set up the exercise spinner
        ArrayAdapter<String> exerciseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dummyExercises);
        exerciseSpinner.setAdapter(exerciseAdapter);


        // Set up onClickListener for the saveButton
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExercise();
            }
        });
    }



    private void saveExercise() {
        String selectedUser = userSpinner.getSelectedItem().toString();
        String selectedExercise = exerciseSpinner.getSelectedItem().toString();

        // TODO: Implement the logic to save the selected exercise for the chosen user in Firestore
        // For now, let's print the selected user and exercise
        Toast.makeText(this, "Selected User: " + selectedUser + "\nSelected Exercise: " + selectedExercise, Toast.LENGTH_SHORT).show();
    }
}
