package de.dhbw.app2night;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

import de.dhbw.model.Party;
import de.dhbw.utils.ContextManager;


/**
 * Created by Bro on 26.10.2016.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnItemClickListener,
        AddEventFragment.OnPostPartySuccessful, DetailFragment.OnChangePartyListener, ChangeEventFragment.OnPutPartySuccessful,
        DetailFragment.OpenVoteDialog, DetailFragment.ReturnToHomeFragment, HomeFragment.OnFloatButtonClickListener{

    //Variables
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ContextManager.getInstance().setContext(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},1);
        }


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getFragmentManager();
        //Starte HomeFragment mit Argument, um GetPartyListTask beim ersten Mal zu starten
        Fragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putSerializable(HomeFragment.ARG_STARTUP, true);
        fragment.setArguments(args);

        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container_body, fragment);
        fragmentTransaction.commit();
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},1);
                }
                return;
            }
            //Andere permissions
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.main_container_body);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(fragmentManager.getBackStackEntryCount() > 0) {
            super.onBackPressed();
        }else if(currentFragment.getClass() == HomeFragment.class){
            super.onBackPressed();
        }else{
            returnToHomeFragment();
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
            displayView(R.layout.fragment_settings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                displayView(R.layout.fragment_home);
                break;
            case R.id.nav_addEvent:
                displayView(R.layout.fragment_addchangeevent);
                break;
            case R.id.nav_profile:
                displayView(R.layout.fragment_profile);
                break;
            case R.id.nav_contact:
                displayView(R.layout.fragment_contact);
                break;
            case R.id.nav_settings:
                displayView(R.layout.fragment_settings);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayView(int id) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (id) {
            case R.layout.fragment_home:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case R.layout.fragment_addchangeevent:
                fragment = new AddEventFragment();
                title = getString(R.string.title_addevent);
                break;
            case R.layout.fragment_findevent:
                fragment = new FindEventFragment();
                title = getString(R.string.title_findevent);
                break;
            case R.layout.fragment_profile:
                fragment = new ProfileFragment();
                title = getString(R.string.title_profile);
                break;
            case R.layout.fragment_settings:
                fragment = new SettingsFragment();
                title = getString(R.string.title_settings);
                break;
            case R.layout.fragment_contact:
                fragment = new ContactFragment();
                title = getString(R.string.title_contact);
                break;
            case R.layout.fragment_detail_view:
                fragment = new DetailFragment();
                break;
            default:
                break;
        }
        if (fragment != null) {
            fragmentManager = getFragmentManager();
            for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                fragmentManager.popBackStack();
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onClick(Party party) {
        Fragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(DetailFragment.ARG_PARTY, party);
        fragment.setArguments(args);

        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container_body, fragment).addToBackStack("home");
        fragmentTransaction.commit();
    }

    @Override
    public void postedPartySuccessful() {
        displayView(R.layout.fragment_home);
    }

    @Override
    public void onClickChangePartyButton(Party partyToChange) {
        Fragment fragment = new ChangeEventFragment();
        Bundle args = new Bundle();
        args.putSerializable(ChangeEventFragment.ARG_PARTY, partyToChange);
        fragment.setArguments(args);

        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container_body, fragment).addToBackStack("detailView");
        fragmentTransaction.commit();
    }

    @Override
    public void putPartySuccessful() {
        displayView(R.layout.fragment_home);
    }

    @Override
    public void openVoteDialog(String partyId) {
        fragmentManager = getFragmentManager();
        VoteDialog voteDialog = new VoteDialog();
        Bundle args = new Bundle();
        args.putString(VoteDialog.ARG_PARTYID, partyId);
        voteDialog.setArguments(args);
        voteDialog.show(fragmentManager, getString(R.string.vote_dialog_title));
    }

    /**
     * Führt die Rückkehr zum HomeFragment durch
     */
    @Override
    public void returnToHomeFragment() {
            Fragment fragment = new HomeFragment();
            fragmentManager = getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.main_container_body, fragment);
            ft.commit();

            getSupportActionBar().setTitle(getString(R.string.title_home));
    }

    /**
     * Öffnet die FindEvent-Übersicht mit Markierungen der Parties
     * @param parties
     */
    @Override
    public void onFloatButtonClick(ArrayList<Party> parties) {
        Fragment fragment = new FindEventFragment();
        Bundle args = new Bundle();
        args.putSerializable(FindEventFragment.ARG_PARTYLIST, parties);
        fragment.setArguments(args);

        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container_body, fragment).addToBackStack("HomeFragment");
        fragmentTransaction.commit();
    }
}
