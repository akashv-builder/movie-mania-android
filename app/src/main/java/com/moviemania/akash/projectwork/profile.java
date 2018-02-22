package com.moviemania.akash.projectwork;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;

import java.io.File;

public class profile extends AppCompatActivity
        implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    ImageView backimage;
    CircularImageView change;
    //String college;
    //String description;
    String dob;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editor1;
    String email;
    private AppBarLayout mAppBarLayout;
    private boolean mIsTheTitleContainerVisible;
    private boolean mIsTheTitleVisible;
    private TextView mTitle;
    private LinearLayout mTitleContainer;
    private Toolbar mToolbar;
    CircularImageView mainimage;
    String name;
    TextView namedescription;
    String phone;
    TextView profilename;
    //TextView setcollege;
    TextView setdescription;
    TextView setdob;
    TextView setemail;
    TextView setname;
    TextView setphone;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences1;
    SharedPreferences sharedPreferences2;
    SharedPreferences.Editor editor2;
    Button chan,del;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bindActivity();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAppBarLayout.addOnOffsetChangedListener(this);
        sharedPreferences = getSharedPreferences("Register", 0);
        email = this.sharedPreferences.getString("email", "N/A");
        phone = this.sharedPreferences.getString("phone", "N/A");
        name = this.sharedPreferences.getString("name", "N/A");
       // college = this.sharedPreferences.getString("college", "N/A");
       // description = this.sharedPreferences.getString("description", "N/A");
        dob = this.sharedPreferences.getString("dob", "N/A");
        profilename.setText(this.name);
       // namedescription.setText(this.description);
        setname.setText(this.name);
        setemail.setText(this.email);
        setphone.setText(this.phone);
        //setcollege.setText(this.college);
        setdob.setText(this.dob);
        mTitle.setText(this.name);
        sharedPreferences1 = getApplicationContext().getSharedPreferences("pathofpic1", 0);
        editor1 =sharedPreferences1.edit();
        if (new File("/data/data/com.moviemania.akash.projectwork/shared_prefs/pathofpic1.xml").exists()) {
            loadImageFromStorage(this.sharedPreferences1.getString("picpath1", "N/A"));
        } else {
            backimage.setImageResource(R.drawable.profile);
        }

        sharedPreferences2 = getApplicationContext().getSharedPreferences("pathofpic2", 0);
        editor2 =sharedPreferences2.edit();
        if (new File("/data/data/com.moviemania.akash.projectwork/shared_prefs/pathofpic2.xml").exists()) {
            loadImageFromStorageMain(sharedPreferences2.getString("picpath2", "N/A"));
        } else {
            change.setImageResource(R.drawable.profile);
        }


        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        //mToolbar.inflateMenu(R.menu.menu_main);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id ==android.R.id.home) {
           // NavUtils.navigateUpFromSameTask(this);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
            finish();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }
    private void bindActivity() {
        mToolbar        = (Toolbar) findViewById(R.id.main_toolbar);

        mTitle          = (TextView) findViewById(R.id.main_textview_title);
        mTitleContainer = (LinearLayout) findViewById(R.id.main_linearlayout_title);
        mAppBarLayout   = (AppBarLayout) findViewById(R.id.main_appbar);
        profilename = (TextView) findViewById(R.id.profilename);
        namedescription = (TextView) findViewById(R.id.namedescription);
        change= (CircularImageView) findViewById(R.id.change);
        setname = (TextView) findViewById(R.id.setname);
        //setcollege = (TextView) findViewById(R.id.setcollege);
        setdob = (TextView) findViewById(R.id.setdob);
        setemail = (TextView) findViewById(R.id.setemail);
        setphone = (TextView) findViewById(R.id.setphone);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mTitle = (TextView) findViewById(R.id.main_textview_title);
        mTitleContainer = (LinearLayout) findViewById(R.id.main_linearlayout_title);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.main_appbar);
        backimage = (ImageView) findViewById(R.id.main_imageview_placeholder);
        chan= (Button) findViewById(R.id.chan);
        del= (Button) findViewById(R.id.del);

        chan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                final ProgressDialog progress = new ProgressDialog(profile.this);
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
                        startActivity(new Intent(getApplicationContext(), change_password.class));
                        overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
                        finish();
                    }
                },2000);
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                final ProgressDialog progress = new ProgressDialog(profile.this);
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
                        startActivity(new Intent(getApplicationContext(), Delete_account.class));
                        overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
                        finish();
                    }
                },2000);
            }
        });

    }
    private void selectImage() {
       final CharSequence[] options = new CharSequence[]{"Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Choose from Gallery")) {
                    startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
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


    private void loadImageFromStorage(String path) {
        try {
            backimage.setImageBitmap(BitmapFactory.decodeFile(path));
        } catch (Exception e) {
        }
    }

    private void loadImageFromStorageMain(String path) {
        try {
            change.setImageBitmap(BitmapFactory.decodeFile(path));
        } catch (Exception e) {
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 2) {
            String[] filePath = new String[]{"_data"};
            Cursor c = getContentResolver().query(data.getData(), filePath, null, null, null);
            c.moveToFirst();
            String picturePath = c.getString(c.getColumnIndex(filePath[0]));
            editor1.putString("picpath1", picturePath);
            editor1.commit();
            c.close();
            Bitmap thumbnail = BitmapFactory.decodeFile(picturePath);
            Log.w("path of image", picturePath);
            backimage.setImageBitmap(thumbnail);
        }

        else if (resultCode == RESULT_OK && requestCode == 3) {
            String[] filePath = new String[]{"_data"};
            Cursor c = getContentResolver().query(data.getData(), filePath, null, null, null);
            c.moveToFirst();
            String picturePath = c.getString(c.getColumnIndex(filePath[0]));
            editor2.putString("picpath2", picturePath);
            editor2.commit();
            c.close();
            Bitmap thumbnail = BitmapFactory.decodeFile(picturePath);
            Log.w("path of image", picturePath);
            change.setImageBitmap(thumbnail);
        }
    }



    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    public void changee(View v)
    {
        selectImage2();

    }
}
