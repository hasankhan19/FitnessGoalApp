package com.example.fitnessgoalapp;

public class TrainerModel {
        private String email;

        // Empty constructor required for Firestore
        public TrainerModel() {
        }

        public TrainerModel(String email) {
            this.email = email;
        }

        // Getter and setter for the email field
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

}
