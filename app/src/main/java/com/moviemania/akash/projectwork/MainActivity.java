package com.moviemania.akash.projectwork;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pkmmte.view.CircularImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    ProgressDialog progress;
    Handler handler;
    TabLayout tabLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    TextView emailinheader;
    TextView nameinheader;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;
    Toast toast;
    boolean doubleBackToExitPressedOnce;
    SharedPreferences sharedPreferences1, sharedPreferences2;
    Context context;
    DrawerLayout dl;
    RelativeLayout rlh;
    CircularImageView circularImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);


        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.pager);

        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setTabsFromPagerAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                try {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                }
            }

            @Override
            public void onPageScrolled(int position, float offset, int offsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        navigationView = (NavigationView) findViewById(R.id.navigationView);
        View headerview = navigationView.getHeaderView(0);
        nameinheader = (TextView) headerview.findViewById(R.id.nameinheader);
        emailinheader = (TextView) headerview.findViewById(R.id.emailinheader);
        circularImageView = (CircularImageView) headerview.findViewById(R.id.civ);
        rlh = (RelativeLayout) headerview.findViewById(R.id.rlh);

        dl = (DrawerLayout) findViewById(R.id.drawerLayout);

        dl.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                try {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                try {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                        INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
        sharedPreferences1 = getSharedPreferences("pathofpic2", 0);
        if (new File("/data/data/com.moviemania.akash.projectwork/shared_prefs/pathofpic2.xml").exists()) {
            loadImageFromStorageMain(this.sharedPreferences1.getString("picpath2", "N/A"));
        } else {
            this.circularImageView.setImageResource(R.drawable.profile);
        }



        this.sharedPreferences = getSharedPreferences("Register", 0);
        String a = this.sharedPreferences.getString("name", "N/A");
        String b = this.sharedPreferences.getString("email", "N/A");
        this.nameinheader.setText(a);
        this.emailinheader.setText(b);
       /* tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.search);
        tabLayout.getTabAt(2).setIcon(R.drawable.camera);*/

        context = this;


    }


    private void loadImageFromStorageMain(String path) {
        try {
            circularImageView.setImageBitmap(BitmapFactory.decodeFile(path));
        } catch (Exception e) {
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            finish();
            android.os.Process.killProcess(Process.myPid());
            return;
        }
        doubleBackToExitPressedOnce = true;
        toast = Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT);
        toast.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;

            }
        }, 2000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 1000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item))

        {
            return true;
        }


        int id = item.getItemId();


        if (id == R.id.action_settings) {

            startActivity(new Intent(this, sub_activity.class));
            overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.rate:
                Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
                }
                break;

            case R.id.profile:
                Intent i = new Intent(MainActivity.this, profile.class);
                startActivity(i);
                overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
                finish();

                break;

            case R.id.share:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "MovieManiaAppShare");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + context.getPackageName());
                startActivity(Intent.createChooser(sharingIntent, "Share via"));


                break;

            case R.id.feedback:
                final Intent _Intent = new Intent(android.content.Intent.ACTION_SEND);
                _Intent.setType("text/html");
                _Intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{getString(R.string.mail_feedback_email)});
                _Intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.mail_feedback_subject));
                _Intent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.mail_feedback_message));
                startActivity(Intent.createChooser(_Intent, getString(R.string.title_send_feedback)));

                break;

            case R.id.signout:
                handler = new Handler();
                progress = new ProgressDialog(this);
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
                        SharedPreferences settings = context.getSharedPreferences("Loggedin", Context.MODE_PRIVATE);
                        settings.edit().clear().commit();
                        Intent intent = new Intent(getApplicationContext(), sign_in_or_register.class);
                        startActivity(intent);
                        finish();
                    }
                }, 2000);

                break;


        }

        if (dl.isDrawerOpen(GravityCompat.START))
            dl.closeDrawer(GravityCompat.START);


        return false;
    }


}


class ViewPagerAdapter extends FragmentStatePagerAdapter {


    public ViewPagerAdapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                tab1 tab1 = new tab1();

                return tab1;

            case 1:
                tab2 tab2 = new tab2();
                return tab2;

            case 2:
                tab3 tab3 = new tab3();
                return tab3;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "HOME";

            case 1:

                return "SEARCH";

            case 2:
                return "UPCOMING";


            default:
                return null;

        }

    }
}


