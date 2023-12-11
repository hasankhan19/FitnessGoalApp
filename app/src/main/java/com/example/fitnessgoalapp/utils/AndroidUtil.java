package com.example.fitnessgoalapp.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fitnessgoalapp.model.UserModel;

public class AndroidUtil {
    public static  void showToast(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    public static void passUserModelAsIntent(Intent intent, UserModel model){
        intent.putExtra("firstname",model.getFirstname());
        intent.putExtra("email",model.getEmail());
        intent.putExtra("userId",model.getUserId());
     //   intent.putExtra("fcmToken",model.getFcmToken());

    }

    public static UserModel getUserModelFromIntent(Intent intent){
        UserModel userModel = new UserModel();
        userModel.setFirstname(intent.getStringExtra("firstname"));
        userModel.setEmail(intent.getStringExtra("email"));
        userModel.setUserId(intent.getStringExtra("userId"));
      //  userModel.setFcmToken(intent.getStringExtra("fcmToken"));
        return userModel;
    }


}
