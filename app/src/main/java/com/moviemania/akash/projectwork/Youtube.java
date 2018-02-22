package com.moviemania.akash.projectwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

public class Youtube extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {


    YouTubePlayer.OnInitializedListener onInitializedListener;
    Toolbar youtubebar;
    TextView trailername;
    LinearLayout youtube1,youtube2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);



        youtube1= (LinearLayout) findViewById(R.id.youtube1);
        youtube2= (LinearLayout) findViewById(R.id.youtube2);
        youtubebar = (Toolbar) findViewById(R.id.youtubebar);
        trailername= (TextView) findViewById(R.id.trailername);
        setSupportActionBar(youtubebar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if(!staticdata.key.equals("")) {
            youtube1.setVisibility(View.VISIBLE);
            youtube2.setVisibility(View.GONE);

            trailername.setText("Trailer:"+staticdata.moviename);

            YouTubePlayerSupportFragment frag =
                    (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);
            frag.initialize(PlayerConfig.API_KEY, this);

        }
        else
        {
            youtube1.setVisibility(View.GONE);
            youtube2.setVisibility(View.VISIBLE);


        }



      //  youTubePlayerView.initialize(PlayerConfig.API_KEY, onInitializedListener);


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo(staticdata.key);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(getApplicationContext(), R.string.error_player, Toast.LENGTH_LONG).show();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id ==android.R.id.home) {
            startActivity(new Intent(getApplicationContext(), click_on_list.class));
            overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
