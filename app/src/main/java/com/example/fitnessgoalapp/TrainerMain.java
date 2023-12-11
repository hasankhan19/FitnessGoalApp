package com.example.fitnessgoalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessgoalapp.AssignExercises;
import com.example.fitnessgoalapp.ChatScreen;
import com.example.fitnessgoalapp.R;

public class TrainerMain extends AppCompatActivity {

    private ListView exercisesListView;
    private Button assignExercisesButton;
    private Button messagesButton; // Add this line

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_main);

        exercisesListView = findViewById(R.id.exercisesListView);
        assignExercisesButton = findViewById(R.id.assignExercisesButton);
        messagesButton = findViewById(R.id.messagesButton); // Add this line

        // Create a sample list of exercises
        String[] exercises = {"Exercise 1", "Exercise 2", "Exercise 3"};

        // Create an ArrayAdapter to populate the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, exercises);
        exercisesListView.setAdapter(adapter);

        // Set an item click listener for the exercisesListView
        exercisesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle exercise item click, e.g., show exercise details
                String selectedExercise = exercises[position];
                Toast.makeText(TrainerMain.this, "Selected Exercise: " + selectedExercise, Toast.LENGTH_SHORT).show();
            }
        });

        // Set an OnClickListener for the assignExercisesButton to navigate to the exercise assignment page
        assignExercisesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the page for assigning exercises
                Intent intent = new Intent(TrainerMain.this, AssignExercises.class);
                startActivity(intent);
            }
        });

        // Set an OnClickListener for the messagesButton to navigate to the ChatScreen activity
        messagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the ChatScreen activity
                Intent intent = new Intent(TrainerMain.this, ChatScreen.class);
                startActivity(intent);
            }
        });
    }
}
