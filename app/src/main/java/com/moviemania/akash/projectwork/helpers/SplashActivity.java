package com.moviemania.akash.projectwork.helpers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.widget.ImageView;

import com.badoo.mobile.util.WeakHandler;
import com.daimajia.androidanimations.library.Techniques;
import com.moviemania.akash.projectwork.MainActivity;
import com.moviemania.akash.projectwork.R;
import com.moviemania.akash.projectwork.sign_in_or_register;
import com.moviemania.akash.projectwork.welcome;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import java.io.File;

/**
 * Created by varsovski on 28-Sep-15.
 */
public class SplashActivity extends AwesomeSplash {
    SharedPreferences.Editor editor;
    ImageView ivv;
    String s1;
    String s2;
    String s3;
    String s4;
    SharedPreferences sharedPreferences;
    android.os.Handler handler = new android.os.Handler();

    @Override
    public void initSplash(ConfigSplash configSplash) {
        getAndSetSplashValues(configSplash);
    }

    @Override
    public void animationsFinished() {
        //wait 5 sec and then go back to MainActivity
        final Activity a = this;
        WeakHandler handler = new WeakHandler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                File file1 = new File("/data/data/com.moviemania.akash.projectwork/shared_prefs/Register.xml");
                File file2 = new File("/data/data/com.moviemania.akash.projectwork/shared_prefs/Loggedin.xml");
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
                    } else {
                        startActivity(new Intent(SplashActivity.this.getApplicationContext(), sign_in_or_register.class));
                        finish();
                    }
                } else {
                    startActivity(new Intent(SplashActivity.this.getApplicationContext(), welcome.class));
                    overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
                    finish();
                }


                // ChangeActivityHelper.changeActivity(a, sign_in_or_register.class, true);
            }
        }, Constants.SPLASH_DELAY);
    }


    public void getAndSetSplashValues(ConfigSplash cs1) {

        ConfigSplash cs2 = new ConfigSplash();
        cs2.setLogoSplash(R.drawable.i);
        cs2.setPathSplashStrokeSize(10);

        cs2.setTitleSplash(""+Html.fromHtml(getString(R.string.ss)));
        cs2.setTitleTextSize(90.0f);

        // ConfigSplash cs2 = (ConfigSplash) getIntent().getExtras().getSerializable(Constants.CONFIG);
        if (cs2 != null) {
            cs1.setAnimCircularRevealDuration(2000);
            cs1.setRevealFlagX(4);
            cs1.setRevealFlagY(2);
            cs1.setBackgroundColor(R.color.DarkSlateBlue);
            cs1.setLogoSplash(cs2.getLogoSplash());
            cs1.setAnimLogoSplashTechnique(Techniques.FadeInDown);
            cs1.setAnimLogoSplashDuration(1200);

            /*cs1.setPathSplash("M3.015,4.779h1.164V3.615H3.015V4.779z M18.73,1.869H1.269c-0.322,0-0.582,0.26-0.582,0.582v15.133\n" +
                    "\t\t\t\t\t\t\t\tc0,0.322,0.26,0.582,0.582,0.582H18.73c0.321,0,0.582-0.26,0.582-0.582V2.451C19.312,2.129,19.052,1.869,18.73,1.869z\n" +
                    "\t\t\t\t\t\t\t\t M18.148,16.42c0,0.322-0.261,0.582-0.582,0.582H2.433c-0.322,0-0.582-0.26-0.582-0.582V6.525h16.297V16.42z M18.148,5.361H1.851\n" +
                    "\t\t\t\t\t\t\t\tV3.615c0-0.322,0.26-0.582,0.582-0.582h15.133c0.321,0,0.582,0.26,0.582,0.582V5.361z M7.671,4.779h1.165V3.615H7.671V4.779z\n" +
                    "\t\t\t\t\t\t\t\t M5.344,4.779h1.164V3.615H5.344V4.779z");
            cs1.setPathSplashStrokeSize(3);
            cs1.setPathSplashStrokeColor(R.color.white);
            cs1.setPathSplashFillColor(R.color.AliceBlue);
            cs1.setOriginalHeight(400);
            cs1.setOriginalWidth(400);
            cs1.setAnimPathStrokeDrawingDuration(1500);
            cs1.setAnimPathFillingDuration(1000);*/

            cs1.setTitleSplash(cs2.getTitleSplash());
            cs1.setAnimTitleDuration(1000);
            cs1.setAnimTitleTechnique(Techniques.SlideInUp);
            cs1.setTitleTextSize(45);
            cs1.setTitleTextColor(R.color.white);
              cs1.setTitleFont("fonts/comic.ttf");
        }
    }


}
