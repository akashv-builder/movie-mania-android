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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


public class forgot_password extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText forgotemail, newpass1, confirmnewwpass1;
    Button forgotpassbutton, forgotpassword;
    String e1, e2, t1, t2;
    LinearLayout pass1, forgotpasswordll1, forgotpasswordll2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Toolbar toolbar = (Toolbar) findViewById(R.id.barrr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        forgotemail = (EditText) findViewById(R.id.forgotemail);
        newpass1 = (EditText) findViewById(R.id.newpass1);
        confirmnewwpass1 = (EditText) findViewById(R.id.confirmnewpass1);
        forgotpassbutton = (Button) findViewById(R.id.forgotpassbutton);
        forgotpassword = (Button) findViewById(R.id.forgotpassword);

        sharedPreferences = getSharedPreferences("Register", 0);
        editor = sharedPreferences.edit();


        e1 = sharedPreferences.getString("email", "N/A");

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                }
                t1 = newpass1.getText().toString();
                t2 = confirmnewwpass1.getText().toString();
                if (t1.equals("") || t2.equals("")) {
                    Snackbar snackbar = Snackbar.make(pass1, "Enter New Password", Snackbar.LENGTH_SHORT);
                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.orange));
                    snackbar.show();

                } else if (t1.length() < 6 || t1.length() < 6) {

                    Snackbar snackbar = Snackbar.make(pass1, "Password should contain atleast 6 characters!", Snackbar.LENGTH_SHORT);
                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.orange));
                    snackbar.show();

                } else if (!t1.equals(t2)) {
                    Snackbar snackbar = Snackbar.make(pass1, "New Password Dosen't Match!", Snackbar.LENGTH_SHORT);
                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.orange));
                    snackbar.show();

                } else {
                    Handler handler = new Handler();
                    final ProgressDialog progress = new ProgressDialog(forgot_password.this);
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
                            editor.putString("password", t1);
                            editor.commit();
                            startActivity(new Intent(getApplicationContext(), Sign_In.class));
                            overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
                            finish();
                            Toast.makeText(getApplicationContext(), "Password Recoverd Successfully", Toast.LENGTH_SHORT).show();


                        }
                    }, 1500);


                }

            }
        });

        pass1 = (LinearLayout) findViewById(R.id.pass1);
        forgotpasswordll1 = (LinearLayout) findViewById(R.id.forgotpasswordll1);
        forgotpasswordll2 = (LinearLayout) findViewById(R.id.forgotpasswordll2);

        forgotpassbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e2 = forgotemail.getText().toString();
                try {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                }
                if (e2.equals("")) {
                    Snackbar snackbar = Snackbar.make(pass1, "Enter Email", Snackbar.LENGTH_SHORT);
                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.orange));
                    snackbar.show();
                    forgotemail.requestFocus();

                } else if (e1.equals(e2)) {
                    Handler handler = new Handler();
                    final ProgressDialog progress = new ProgressDialog(forgot_password.this);
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

                            forgotpasswordll1.setVisibility(View.GONE);
                            forgotpasswordll2.setVisibility(View.VISIBLE);


                        }
                    }, 1500);


                } else {
                    Snackbar snackbar = Snackbar.make(pass1, "Invalid User", Snackbar.LENGTH_SHORT);
                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.orange));
                    snackbar.show();

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
        if (id == android.R.id.home) {
           /* forgotpasswordll1.setVisibility(View.VISIBLE);
            forgotpasswordll2.setVisibility(View.GONE);*/
            startActivity(new Intent(getApplicationContext(), Sign_In.class));
            overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
