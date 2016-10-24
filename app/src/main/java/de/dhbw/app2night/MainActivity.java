package de.dhbw.app2night;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import de.dhbw.backendTasks.party.ChangePartyByIdTask;
import de.dhbw.backendTasks.party.DeletePartyByIdTask;
import de.dhbw.backendTasks.party.GetPartyListTask;
import de.dhbw.backendTasks.party.PostPartyTask;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public Context context = this;
    public TextView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        view = (TextView) findViewById(R.id.textview1);
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
        getMenuInflater().inflate(R.menu.main, menu);
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

            // Intent erzeugen und Starten der AktiendetailActivity mit explizitem Intent
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_addEvent) {
            Intent settingsIntent = new Intent(this, AddEventActivity.class);
            startActivity(settingsIntent);
        }
        else if (id == R.id.nav_findEvent) {
            new GetPartyListTask(this);
        }
        else if (id == R.id.nav_profile){
            new PostPartyTask(this,"{\"partyName\": \"string\", "+
                    "\"partyDate\": \"2016-10-24T17:58:34.538Z\", "+
                    " \"musicGenre\": 0," +
                    " \"location\": {" +
                    "\"countryName\": \"string\","+
                    "\"cityName\": \"string\","+
                    "\"streetName\": \"string\","+
                    "\"houseNumber\": 0,"+
                    "\"houseNumberAdditional\": \"string\","+
                    "\"zipcode\": 0,"+
                    " \"latitude\": 0,"+
                    " \"longitude\": 0}," +
                    "\"partyType\": 0," +
                    "\"description\": \"string\"}");
        }
        else  if (id == R.id.nav_settings) {

            new DeletePartyByIdTask(this, "2acec5b0-37e1-4c88-4692-08d3fc41e1f5");

            new ChangePartyByIdTask(this,"2acec5b0-37e1-4c88-4692-08d3fc41e1f5","{\"partyName\": \"string\", "+
                    "\"partyDate\": \"2016-10-24T17:58:34.538Z\", "+
                    " \"musicGenre\": 0," +
                    " \"location\": {" +
                    "\"countryName\": \"string\","+
                    "\"cityName\": \"string\","+
                    "\"streetName\": \"string\","+
                    "\"houseNumber\": 0,"+
                    "\"houseNumberAdditional\": \"string\","+
                    "\"zipcode\": 0,"+
                    " \"latitude\": 0,"+
                    " \"longitude\": 0}," +
                    "\"partyType\": 0," +
                    "\"description\": \"string\"}");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}



