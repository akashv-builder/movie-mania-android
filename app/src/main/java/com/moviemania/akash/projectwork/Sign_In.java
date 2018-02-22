package com.moviemania.akash.projectwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Sign_In extends AppCompatActivity {

    Button button;
    CheckBox cb;
    SharedPreferences.Editor editor;
    String emaillocal;
    String emailsp;
    String passwordlocal;
    String passwordsp;
    private ProgressDialog progress;
    SharedPreferences sharedPreferences;
    LinearLayout signinlinear;
    EditText t1;
    EditText t2;
    TextView tv,fp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        button= (Button) findViewById(R.id.nextforsignin);
        fp= (TextView) findViewById(R.id.fp);
        t1= (EditText) findViewById(R.id.emailorphone);
        t2= (EditText) findViewById(R.id.signinpassword);
        setSupportActionBar((Toolbar) findViewById(R.id.app_barrr));
        getSupportActionBar().setHomeButtonEnabled(true);
        signinlinear = (LinearLayout) findViewById(R.id.signinlinear);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        button = (Button) findViewById(R.id.nextforsignin);
        t1 = (EditText) findViewById(R.id.emailorphone);
        t2 = (EditText) findViewById(R.id.signinpassword);
        tv = (TextView) findViewById(R.id.tv);
        cb = (CheckBox) findViewById(R.id.checkboxloggedin);
        sharedPreferences = getSharedPreferences("Register", 0);
        emailsp = this.sharedPreferences.getString("email", "N/A");
        passwordsp = this.sharedPreferences.getString("password", "N/A");

        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Handler handler = new Handler();
               final ProgressDialog progress = new ProgressDialog(Sign_In.this);
                progress.setMessage("Loading....");
                progress.setProgressStyle(0);
                progress.setIndeterminate(true);
                progress.setProgress(0);
                progress.setCancelable(false);
                progress.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progress.hide();
                        startActivity(new Intent(Sign_In.this.getApplicationContext(), forgot_password.class));
                        overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
                        finish();
                    }
                },1500);

            }
        });

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = Sign_In.this.t1.getText().toString();
                String s2 = Sign_In.this.t2.getText().toString();
                try {
                    ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                }
                Snackbar snackbar;
                if (s1.equals("") || s2.equals("")) {
                    snackbar = Snackbar.make(signinlinear,"Enter Email And Password", Snackbar.LENGTH_SHORT);
                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.orange));
                    snackbar.show();
                    cb.setChecked(false);
                    t1.requestFocus();
                } else if (!Sign_In.this.emailsp.equals(s1) || !Sign_In.this.passwordsp.equals(s2)) {
                    snackbar = Snackbar.make(signinlinear,"Invalid User", Snackbar.LENGTH_SHORT);
                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.orange));
                    snackbar.show();
                    cb.setChecked(false);
                } else if (!Sign_In.this.passwordsp.equals(s2)) {
                    snackbar = Snackbar.make(signinlinear,"Incorrect Password For This Email", 0);
                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.orange));
                    snackbar.show();
                    cb.setChecked(false);
                } else if (Sign_In.this.emailsp.equals(s1)) {
                    Sign_In.this.sharedPreferences =getApplicationContext().getSharedPreferences("Loggedin", 0);
                    editor = Sign_In.this.sharedPreferences.edit();
                    editor.putString("email", t1.getText().toString());
                    editor.putString("password", t2.getText().toString());
                    editor.commit();
                    snackbar = Snackbar.make(signinlinear,"Saved", 0);
                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.orange));
                    snackbar.show();
                    cb.setVisibility(View.GONE);
                    tv.setVisibility(View.GONE);
                } else {
                    snackbar = Snackbar.make(signinlinear,"Incorrect Password For This Email", 0);
                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.orange));
                    snackbar.show();
                    cb.setChecked(false);
                }
            }
        });



        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = Sign_In.this.t1.getText().toString();
                String s2 = Sign_In.this.t2.getText().toString();
                try {
                    ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                }
                Snackbar snackbar;
                if (s1.equals("") || s2.equals("")) {
                    snackbar = Snackbar.make(signinlinear,"Enter Email And Password", Snackbar.LENGTH_SHORT);
                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.orange));
                    snackbar.show();
                    cb.setChecked(false);
                    t1.requestFocus();
                } else if (!Sign_In.this.emailsp.equals(s1) || !Sign_In.this.passwordsp.equals(s2)) {
                    snackbar = Snackbar.make(signinlinear,"Invalid User", Snackbar.LENGTH_SHORT);
                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.orange));
                    snackbar.show();
                    cb.setChecked(false);
                } else if (!Sign_In.this.passwordsp.equals(s2)) {
                    snackbar = Snackbar.make(signinlinear,"Incorrect Password For This Email", 0);
                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.orange));
                    snackbar.show();
                    cb.setChecked(false);
                } else if (Sign_In.this.emailsp.equals(s1)) {
                    Sign_In.this.sharedPreferences =getApplicationContext().getSharedPreferences("Loggedin", 0);
                    editor = Sign_In.this.sharedPreferences.edit();
                    editor.putString("email", t1.getText().toString());
                    editor.putString("password", t2.getText().toString());
                    editor.commit();
                    snackbar = Snackbar.make(signinlinear,"Saved", 0);
                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.orange));
                    snackbar.show();
                    cb.setVisibility(View.GONE);
                    tv.setVisibility(View.GONE);
                } else {
                    snackbar = Snackbar.make(signinlinear,"Incorrect Password For This Email", 0);
                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.orange));
                    snackbar.show();
                    cb.setChecked(false);
                }
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                }
                emaillocal = t1.getText().toString();
                passwordlocal = t2.getText().toString();
                Handler handler;
                if (emailsp.equals(emaillocal) && passwordlocal.equals(passwordsp)) {
                    handler = new Handler();
                    progress = new ProgressDialog(Sign_In.this);
                    progress.setMessage("Loading....");
                    progress.setProgressStyle(0);
                    progress.setIndeterminate(true);
                    progress.setProgress(0);
                    progress.setCancelable(false);
                    progress.show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progress.hide();
                            startActivity(new Intent(Sign_In.this.getApplicationContext(), MainActivity.class));
                            overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
                            finish();
                        }
                    },2000);
                } else if (Sign_In.this.emaillocal.equals("") || passwordlocal.equals("")) {
                    Snackbar snackbar = Snackbar.make(signinlinear,"Enter Email And Password", Snackbar.LENGTH_SHORT);
                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.orange));
                    snackbar.show();
                    t1.requestFocus();
                } else {
                    handler = new Handler();
                    progress = new ProgressDialog(Sign_In.this);
                    progress.setMessage("Loading....");
                    progress.setProgressStyle(0);
                    progress.setIndeterminate(true);
                    progress.setProgress(0);
                    progress.setCancelable(false);
                    progress.show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progress.hide();
                            Snackbar snackbar = Snackbar.make(signinlinear,"Invalid User", Snackbar.LENGTH_SHORT);
                            snackbar.getView().setBackgroundColor(getResources().getColor(R.color.orange));
                            snackbar.show();
                        }
                    },2000);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id ==android.R.id.home) {
            startActivity(new Intent(getApplicationContext(), sign_in_or_register.class));
            overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
