package com.cgellner.yomm.Start;

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
import android.view.Menu;
import android.view.MenuItem;

import com.cgellner.yomm.Database.Database;
import com.cgellner.yomm.OverviewPayments.Fragment_OverviewCardViewList;
import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Category;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.R;
import com.cgellner.yomm.Settings.Activity_Settings;


/**
 * Die Klasse stellt die Hauptactivity der App dar und beinhaltet die Startseite und ein Menue.
 * @author Carolin Gellner
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    //region Fields

    private static FragmentTransaction fragmentTransaction;
    private static SharedPreferences preferences;
    private Fragment_Start fragmentStart;

    //endregion


    //region Public Methods

    /**
     * Die Methode erstellt die Activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Globale Variablen initialisieren
        //d.h. Groesse des Displays ermitteln und die Datenbank initialisieren
        initGlobalVariables();


        setContentView(R.layout.activity_main3);


        //SharedPreferences initialisieren
        preferences = PreferenceManager.getDefaultSharedPreferences(this);


        //Start-Fragment anzeigen mithilfe einer FragmentTransaction
        fragmentStart = new Fragment_Start();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragmentStart);
        fragmentTransaction.commit();



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("YOMM");
        setSupportActionBar(toolbar);


        //Navigation (Menue)
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


        if(id == R.id.nav_start){

            Fragment_Start fragment = new Fragment_Start();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            getSupportActionBar().setTitle("YOMM");


        }else
        if (id == R.id.nav_overview) {

            //Fragment wechseln (Uebersicht der Schulden aller Personen)

            Fragment_OverviewCardViewList fragment = new Fragment_OverviewCardViewList();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            getSupportActionBar().setTitle("Übersicht");


        } else if (id == R.id.nav_personens) {

            //neue Activity starten zum Erfassen, Bearbeiten, Loeschen einer neuen Person
            intent = new Intent(this, Activity_Settings.class);
            intent.putExtra("object", Person.class.getName());
            startActivity(intent);

        } else if (id == R.id.nav_categories) {

            //neue Activity starten zum Erfassen, Bearbeiten, Loeschen einer neuen Kategorie
            intent = new Intent(this, Activity_Settings.class);
            intent.putExtra("object", Category.class.getName());
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    //endregion


    //region Private Methods

    /**
     * Die Methode initialisiert Globale Variablen der App.
     */
    private void initGlobalVariables(){

        //Datenbank initialisieren
        GlobalVar.Database = new Database(this);


        //Displaygroesse ermitteln
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        GlobalVar.Display_Width = size.x;
        GlobalVar.Display_Height = size.y;
    }

    //endregion

}
