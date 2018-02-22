package com.moviemania.akash.projectwork;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

public class click_on_list extends AppCompatActivity {
    private TextView audiencescore;
    TextView description;
    TextView descriptiontv;
    private TextView details;
    private TextView moviename;
    private TextView releasedate;
    ImageView movieTumbnali1;
    VolleySingleton volleySingleton;
    ImageLoader imageLoader;
    ProgressBar pb;
    Button play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_on_list);
        setSupportActionBar((Toolbar) findViewById(R.id.tb));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        volleySingleton = VolleySingleton.getInstance();

        imageLoader = volleySingleton.getImageLoader();

        play= (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler;
                handler = new Handler();
               final ProgressDialog progress = new ProgressDialog(click_on_list.this);
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
                        startActivity(new Intent(getApplicationContext(), Youtube.class));
                        overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
                        finish();
                    }
                },2000);

            }
        });
        moviename = (TextView) findViewById(R.id.moviename);
        releasedate = (TextView) findViewById(R.id.releasedate);
        audiencescore = (TextView) findViewById(R.id.audiencescore);
        //details = (TextView) findViewById(R.id.details);
        description = (TextView) findViewById(R.id.description);
        /*descriptiontv = (TextView) findViewById(R.id.descriptiontv);
        Animation animation = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.bounce);
        descriptiontv.setAnimation(animation);*/
//        details.setAnimation(animation);
        moviename.setText("" + staticdata.moviename);
        releasedate.setText("" + staticdata.releasedate);
        moviename.setText("" + staticdata.moviename);
        releasedate.setText("" + staticdata.releasedate);
        movieTumbnali1 = (ImageView) findViewById(R.id.movieTumbnail1);
        pb = (ProgressBar) findViewById(R.id.pb);

       // L.t(getApplicationContext(),staticdata.key);

        int i;
        if (staticdata.audiencescore == 0) {
            audiencescore.setText("Not Available");
        } else {
            audiencescore.setText("" + staticdata.audiencescore + "/10");
        }


        description.setText("" + staticdata.description);

        final String urlThumbnail = staticdata.b;
        if (urlThumbnail != null) {


            imageLoader.get(urlThumbnail, new ImageLoader.ImageListener()


            {
                @Override
                public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {

                    staticdata.i = 0;

                    movieTumbnali1.setImageBitmap(imageContainer.getBitmap());

                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    staticdata.i = 1;
                    movieTumbnali1.setImageResource(R.drawable.thumbnail1);

                }
            });


        }


        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (staticdata.i == 0) {
                    movieTumbnali1.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.GONE);
                } else {
                    pb.setVisibility(View.GONE);
                    movieTumbnali1.setVisibility(View.VISIBLE);

                }


            }
        }, 5000);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
