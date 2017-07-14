package com.example.stefany.paradigmas20171.view_control.access;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.model.infrastructure.Session;
import com.example.stefany.paradigmas20171.model.infrastructure.Subject;
import com.example.stefany.paradigmas20171.view_control.results.WaitingForResultsActivity;
import com.example.stefany.paradigmas20171.view_control.steps.StepFirstAccessActivity;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressBar progress;
    private ArrayList<Subject> mySubjects;
    private double progressPercentual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        mySubjects = Session.getSubjectManager().getMySubjects();
        progressPercentual = (mySubjects.size()/50.0);
        progressPercentual = progressPercentual*100;

        Double p = progressPercentual;
        Log.d("PROGR_ESS", p.toString());

        Integer i = (int) progressPercentual;
        Log.d("PROG_RESS", i.toString());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progress.setProgress((int) progressPercentual, true);
        } else {
            progress.setProgress((int) progressPercentual);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_subjects) {
            Intent intentGoSteps = new Intent(ProfileActivity.this, MySubjectsActivity.class);
            finish();
            startActivity(intentGoSteps);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        } else if (id == R.id.nav_results) {
            Intent intentGoSteps = new Intent(ProfileActivity.this, WaitingForResultsActivity.class);
            finish();
            startActivity(intentGoSteps);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        } else if (id == R.id.nav_calculate) {
            Intent intentGoSteps = new Intent(ProfileActivity.this, StepFirstAccessActivity.class);
            finish();
            startActivity(intentGoSteps);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        } else if (id == R.id.nav_log_out) {
            Intent intentBackLogin = new Intent(ProfileActivity.this, LoginActivity.class);
            finish();
            startActivity(intentBackLogin);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
