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

public class change_password extends AppCompatActivity {

    EditText oldpass;
    EditText newpass;
    EditText confirmnewpass;
    Button changepassword;
    String s1, s2, s3, s4;
    SharedPreferences sharedPreferences;
    LinearLayout pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_barrrrr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        oldpass = (EditText) findViewById(R.id.oldpass);
        newpass = (EditText) findViewById(R.id.newpass);
        confirmnewpass = (EditText) findViewById(R.id.confirmnewpass);
        changepassword = (Button) findViewById(R.id.changepassword);
        pass = (LinearLayout) findViewById(R.id.pass);

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                }

                sharedPreferences = getSharedPreferences("Register", 0);
                s1 = sharedPreferences.getString("password", "N/A");
                s2 = oldpass.getText().toString();
                s3 = newpass.getText().toString();
                s4 = confirmnewpass.getText().toString();

                if (s4.equals("") || s2.equals("") || s3.equals("")) {

                    Snackbar snackbar = Snackbar.make(pass, "Enter Password", Snackbar.LENGTH_SHORT);
                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.orange));
                    snackbar.show();
                } else if (newpass.getText().length() < 6) {
                    Snackbar snackbar = Snackbar.make(pass, "Minimum Password Length Must Be 6 Characters", Snackbar.LENGTH_SHORT);
                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.orange));
                    snackbar.show();

                    newpass.requestFocus();
                } else if (s2.equals(s1)) {
                    if (s3.equals(s4)) {

                        Handler handler = new Handler();
                        final ProgressDialog progress = new ProgressDialog(change_password.this);
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
                                SharedPreferences prefs = getApplicationContext().getSharedPreferences("Register", 0);
                                SharedPreferences prefs1 = getApplicationContext().getSharedPreferences("Loggedin", 0);

                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("password", s3);
                                editor.commit();

                                SharedPreferences.Editor editor2 = prefs1.edit();
                                editor2.putString("password", s3);
                                editor2.commit();

                                startActivity(new Intent(getApplicationContext(), profile.class));
                                overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
                                Toast.makeText(getApplicationContext(), "Password Changed", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }, 2000);


                    } else {


                        Snackbar snackbar = Snackbar.make(pass, "Password Do Not Match,Try Again.", Snackbar.LENGTH_SHORT);
                        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.orange));
                        snackbar.show();

                    }


                } else {
                    Snackbar snackbar = Snackbar.make(pass, "Password Do Not Match,Try Again.", Snackbar.LENGTH_SHORT);
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
            // NavUtils.navigateUpFromSameTask(this);
            startActivity(new Intent(getApplicationContext(), profile.class));
            overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
