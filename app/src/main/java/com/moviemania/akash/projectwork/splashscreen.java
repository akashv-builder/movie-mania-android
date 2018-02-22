package com.moviemania.akash.projectwork;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.File;

public class splashscreen extends AppCompatActivity {


    SharedPreferences.Editor editor;
    ImageView ivv;
    String s1;
    String s2;
    String s3;
    String s4;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);


        android.os.Handler handler=new android.os.Handler();
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.bounce);

        ivv= (ImageView) findViewById(R.id.iv);
        //ivv.setAnimation(animation);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                File file1=new File("/data/data/com.moviemania.akash.projectwork/shared_prefs/Register.xml");
                File file2=new File("/data/data/com.moviemania.akash.projectwork/shared_prefs/Loggedin.xml");
                if (file1.exists() && file2.exists()) {
                    sharedPreferences = getSharedPreferences("Register", 0);
                    s1 = sharedPreferences.getString("email", "N/A");
                    s2 = sharedPreferences.getString("password", "N/A");
                    sharedPreferences = getSharedPreferences("Loggedin", 0);
                    s3 = sharedPreferences.getString("email", "N/A");
                    s4 = sharedPreferences.getString("password", "N/A");

                    if (s1.equals(s3) && s2.equals(s4)) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                    else {
                        startActivity(new Intent(splashscreen.this.getApplicationContext(), sign_in_or_register.class));
                        finish();
                    }
                }
                else {
                    startActivity(new Intent(splashscreen.this.getApplicationContext(), sign_in_or_register.class));
                    overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
                    finish();
                }
            }
        },6000);

    }
}
