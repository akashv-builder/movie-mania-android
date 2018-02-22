package com.moviemania.akash.projectwork;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class sign_in_or_register extends AppCompatActivity {

    private GradientBackgroundPainter gradientBackgroundPainter;
    Button login,register;
    TextView tvvvv;
    TextView tvvvvv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_or_register);
        View backgroundImage = findViewById(R.id.root_view);
        login= (Button) findViewById(R.id.b1);
        tvvvv = (TextView) findViewById(R.id.tvvvv);
        tvvvvv = (TextView) findViewById(R.id.tvvvvv);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/disney.ttf");
        this.tvvvv.setTypeface(tf);
        this.tvvvvv.setTypeface(tf);
        final int[] drawables = new int[3];
        drawables[0] = R.drawable.gradient_1;
        drawables[1] = R.drawable.gradient_2;
        drawables[2] = R.drawable.gradient_3;

        gradientBackgroundPainter = new GradientBackgroundPainter(backgroundImage, drawables);
        gradientBackgroundPainter.start();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Sign_In.class);

                startActivity(i);
                overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
                finish();
            }
        });
        register= (Button) findViewById(R.id.b2);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noti();
                Intent i=new Intent(getApplicationContext(),Register_screen.class);

                startActivity(i);
                overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
                finish();
            }
        });
    }

    public void noti()
    {Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        Intent intent1 = new Intent(this,NotifyService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);}
}

