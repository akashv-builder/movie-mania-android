package com.moviemania.akash.projectwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Delete_account extends AppCompatActivity {
    String s1, s2, s3;
    EditText currpass;
    EditText confirmcurrpass;
    SharedPreferences sharedPreferences;
    LinearLayout passu;
    Button delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_barrrrrr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currpass = (EditText) findViewById(R.id.currpass);
        confirmcurrpass = (EditText) findViewById(R.id.confirmcurrpass);
        delete = (Button) findViewById(R.id.delete);
        passu = (LinearLayout) findViewById(R.id.passu);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                }



                sharedPreferences = getSharedPreferences("Register", 0);
                s1 = sharedPreferences.getString("password", "N/A");
                s2 = currpass.getText().toString();
                s3 = confirmcurrpass.getText().toString();

                if (s2.equals("") || s3.equals("")) {

                    Snackbar snackbar = Snackbar.make(passu, "Enter Password", Snackbar.LENGTH_SHORT);
                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.orange));
                    snackbar.show();
                    currpass.requestFocus();
                }
                else if (s2.equals(s3))
                {
                    if(s2.equals(s1))
                    {

                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        Handler handler = new Handler();
                                        final ProgressDialog progress = new ProgressDialog(Delete_account.this);
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
                                                SharedPreferences settings = getSharedPreferences("Loggedin", Context.MODE_PRIVATE);
                                                SharedPreferences settings1 = getSharedPreferences("Register", Context.MODE_PRIVATE);
                                                settings.edit().clear().commit();
                                                settings1.edit().clear().commit();
                                                Intent intent = new Intent(getApplicationContext(), sign_in_or_register.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }, 2000);
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:

                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder ab = new AlertDialog.Builder(Delete_account.this);
                        ab.setMessage("Are you sure to delete?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).setCancelable(false).show();



                    }

                    else
                    {

                        Snackbar snackbar = Snackbar.make(passu, "Password Do Not Match,Try Again.", Snackbar.LENGTH_SHORT);
                        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.orange));
                        snackbar.show();

                    }


                }

                else
                {

                    Snackbar snackbar = Snackbar.make(passu, "Password Do Not Match,Try Again.", Snackbar.LENGTH_SHORT);
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
