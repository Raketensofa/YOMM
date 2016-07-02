package com.cgellner.yomm.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;


import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;

import com.cgellner.yomm.Database.Database;
import com.cgellner.yomm.Fragments.Fragment_Overview;
import com.cgellner.yomm.Fragments.Fragment_Start;
import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Category;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.R;


/**
 *
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    //region Fields

    //FragmentTransaction zum Oeffnen verschiedener Fragmente innerhalb einer Activity
    private static FragmentTransaction fragmentTransaction;
    private static SharedPreferences preferences;
    private Fragment_Start fragmentStart;

    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        initGlobalVariables();

        setContentView(R.layout.activity_main3);

        //SharedPreferences
        preferences = PreferenceManager.getDefaultSharedPreferences(this);


        //Start-Fragment anzeigen
        fragmentStart = new Fragment_Start();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragmentStart);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Navigation
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        Intent intent = null;


        if (id == R.id.nav_overview) {

            //Fragment wechseln
            Fragment_Overview fragment = new Fragment_Overview();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_personens) {

            //neue Activity starten
            intent = new Intent(this, Activity_Settings.class);
            intent.putExtra("object", Person.class.getName());
            startActivity(intent);

        } else if (id == R.id.nav_categories) {

            //neue Activity starten
            intent = new Intent(this, Activity_Settings.class);
            intent.putExtra("object", Category.class.getName());
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }


    /**
     *
     */
    private void initGlobalVariables(){

        //Datenbank
        GlobalVar.Database = new Database(this);

        //Displaygroesse
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        GlobalVar.Display_Width = size.x;
        GlobalVar.Display_Height = size.y;
    }


}
