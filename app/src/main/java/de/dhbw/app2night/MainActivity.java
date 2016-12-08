package de.dhbw.app2night;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import de.dhbw.model.Party;
import de.dhbw.utils.ContextManager;


/**
 * Created by Bro on 26.10.2016.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnItemClickListener,
        AddEventFragment.OnPostPartySuccessful, DetailFragment.OnChangePartyListener, ChangeEventFragment.OnPutPartySuccessful{

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // display the home fragment view on app launch
        displayView(R.layout.fragment_home);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //TODO: Modifiziertes Back -> Wenn !Home -> HomeFragment, schließe sonst
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
            case R.id.nav_findEvent:
                displayView(R.layout.fragment_findevent);
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
            case R.id.nav_test:
                displayView(R.layout.fragment_test);
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
            case R.layout.fragment_test:
                fragment = new VoteDialog();
                title = getString(R.string.title_test);
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
            FragmentManager fragmentManager = getFragmentManager();
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

        FragmentManager fragmentManager = getFragmentManager();
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

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container_body, fragment).addToBackStack("detailView");
        fragmentTransaction.commit();
    }

    @Override
    public void putPartySuccessful() {
        displayView(R.layout.fragment_home);
    }
}
