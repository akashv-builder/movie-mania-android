package com.moviemania.akash.projectwork;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.pkmmte.view.CircularImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

public class Register_screen extends AppCompatActivity
{
    //EditText college;
    EditText confirmpassword;
    //EditText description;
    EditText dob;
    SharedPreferences.Editor editor;
    EditText email;
    EditText name;
    Button nextforregister;
    EditText password;
    EditText phone;
    ProgressDialog progress;
    String regEx;
    SharedPreferences sharedPreferences;
    private DatePickerDialog DatePickerDialog;
    private SimpleDateFormat dateFormatter;
    CircularImageView regpic;
    SharedPreferences sharedPreferences2;
    SharedPreferences.Editor editor2;
    Button cal;
    private static String TAG = "PermissionDemo";
    private static final int REQUEST_WRITE_STORAGE = 112;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        setSupportActionBar((Toolbar) findViewById(R.id.app_barr));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nextforregister = (Button) findViewById(R.id.nextforregister);
        cal= (Button) findViewById(R.id.cal);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        regpic= (CircularImageView) findViewById(R.id.regpic);
       // college = (EditText) findViewById(R.id.college);
        //description = (EditText) findViewById(R.id.description);
        password = (EditText) findViewById(R.id.password);
        dob = (EditText) findViewById(R.id.dob);
        confirmpassword = (EditText) findViewById(R.id.confirmpassword);
        sharedPreferences = getApplicationContext().getSharedPreferences("Register", 0);
        editor = this.sharedPreferences.edit();
        sharedPreferences2 = getApplicationContext().getSharedPreferences("pathofpic2", 0);
        editor2 =sharedPreferences2.edit();


        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied");

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Permission to access the SD-CARD is required for this app.")
                        .setTitle("Permission required");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        Log.i(TAG, "Clicked");
                        makeRequest();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            } else {
                makeRequest();
            }
        }

       // dob.setEnabled(false);
      //  dob.setClickable(false);
        dob.setKeyListener(null);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setDateTimeField();
        dob.setInputType(InputType.TYPE_NULL);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                }

            }
        });
       // dob.requestFocus();
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.show();
            }
        });
        //dob.requestFocus();

        nextforregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean b = Boolean.valueOf(isEmailValid(email.getText().toString()));

                String aa = password.getText().toString();
                String bb = confirmpassword.getText().toString();
                int namelength = name.getText().length();
                int emaillength = email.getText().length();
             //   int collegelength = college.getText().length();
                int phonelength = phone.getText().length();
                int doblength = dob.getText().length();
               // int descriptionlength = description.getText().length();
                int passwordlength = password.getText().length();
                int confirmpasswordlength = confirmpassword.getText().length();
                try {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                }
                if (namelength == 0 || emaillength == 0 || phonelength == 0 || doblength == 0 || passwordlength == 0 || confirmpasswordlength == 0) {
                    Toast.makeText(getApplicationContext(), "Complete The Registration", Toast.LENGTH_SHORT).show();
                } else if (phonelength < 6 || phonelength > 13) {
                    Toast.makeText(Register_screen.this.getApplicationContext(), "Enter A Valid Phone Number", Toast.LENGTH_SHORT).show();
                    phone.requestFocus();
                } else if (!b.booleanValue()) {
                    Toast.makeText(Register_screen.this.getApplicationContext(), "Enter A Valid Email", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                } else if (!aa.equals(bb)) {
                    Toast.makeText(Register_screen.this.getApplicationContext(), "Password Does Not Match", Toast.LENGTH_SHORT).show();
                    confirmpassword.requestFocus();
                } else if (passwordlength < 6) {
                    Toast.makeText(Register_screen.this.getApplicationContext(), "Minimum Password Length Must Be 6 Characters", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                } else {
                    Handler handler = new Handler();

                    progress = new ProgressDialog(Register_screen.this);
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
                            editor.putString("name", Register_screen.this.name.getText().toString());
                            editor.putString("email", Register_screen.this.email.getText().toString());
                            editor.putString("phone", Register_screen.this.phone.getText().toString());
                            editor.putString("password", Register_screen.this.password.getText().toString());
                           // editor.putString("college", Register_screen.this.college.getText().toString());
                           // editor.putString("description", Register_screen.this.description.getText().toString());
                            editor.putString("confirmpassword", Register_screen.this.confirmpassword.getText().toString());
                            editor.putString("dob", Register_screen.this.dob.getText().toString());
                            editor.commit();
                            Toast.makeText(getApplicationContext(), "REGISTERED", Toast.LENGTH_SHORT).show();
                            Register_screen.this.startActivity(new Intent(getApplicationContext(), sign_in_or_register.class));
                            overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
                            finish();
                        }
                    }, 3000);
                }
            }
        });
    }

    public static boolean isEmailValid(String email) {
        if (Pattern.compile("^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE).matcher(email).matches()) {
            return true;
        }
        return false;
    }
    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_WRITE_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG, "Permission has been denied by user");
                    System.exit(0);

                } else {

                    Log.i(TAG, "Permission has been granted by user");

                }
                return;
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            startActivity(new Intent(getApplicationContext(), sign_in_or_register.class));
            overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog = new DatePickerDialog(this, new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dob.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        View focus = getCurrentFocus();
        if (focus != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            imm.hideSoftInputFromWindow(focus.getWindowToken(), 0);
        }
    }




    public void registerpic(View v)
    {

        selectImage2();
    }

    private void selectImage2() {
        final CharSequence[] options = new CharSequence[]{"Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Choose from Gallery")) {
                    startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 3);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && requestCode == 3) {
            String[] filePath = new String[]{"_data"};
            Cursor c = getContentResolver().query(data.getData(), filePath, null, null, null);
            c.moveToFirst();
            String picturePath = c.getString(c.getColumnIndex(filePath[0]));
            editor2.putString("picpath2", picturePath);
            editor2.commit();
            c.close();
            Bitmap thumbnail = BitmapFactory.decodeFile(picturePath);
            Log.w("path of image", picturePath);
            regpic.setImageBitmap(thumbnail);
        }
    }
}