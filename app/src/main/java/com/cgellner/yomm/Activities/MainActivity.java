package com.cgellner.yomm.Activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.cgellner.yomm.Database.Database;
import com.cgellner.yomm.DialogFragments.DF_NewTrans;
import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.MainCategory;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.Objects.Transaction;
import com.cgellner.yomm.R;

import java.lang.reflect.Array;
import java.util.Set;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button buttonNewTrans;
    private Button buttonCleanDebts;

    private ViewPager viewPager;
    private FragmentStatePagerAdapter pagerAdapter;

    private static final int NUM_PAGES = 5;

    private MainActivity mainActivity = this;



    private double TransValue;
    private int[] TransPersonsWhoPayed;
    private int[] TransForPerson;
    private int TransCategory;
    private String TransDetails;



    private Bundle saveInstanceState;


    public void setTransValue(double transValue) {
        TransValue = transValue;
    }

    public void setTransPersonsWhoPayed(int[] transPersonsWhoPayed) {
        TransPersonsWhoPayed = transPersonsWhoPayed;
    }

    public void setTransForPerson(int[] transForPerson) {
        TransForPerson = transForPerson;
    }

    public void setTransCategory(int transCategory) {
        TransCategory = transCategory;
    }

    public void setTransDetails(String transDetails) {
        TransDetails = transDetails;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.saveInstanceState = savedInstanceState;

        setContentView(R.layout.activity_main3);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        init();
        addListenerOnButton();




    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        Intent intent = null;


        if (id == R.id.nav_overview) {


        } else if (id == R.id.nav_personens) {

            intent = new Intent(this, Settings.class);
            intent.putExtra("object", Person.class.getName());

        } else if (id == R.id.nav_categories) {

            intent = new Intent(this, Settings.class);
            intent.putExtra("object", MainCategory.class.getName());
        }


        if (intent != null) {

            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    /**
     *
     */
    public void addListenerOnButton() {


        buttonCleanDebts = (Button) this.findViewById(R.id.buttonClearDebts);
        buttonCleanDebts.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                android.app.FragmentManager fm = getFragmentManager();

                //DF_NewTrans newTrans = new DF_NewTrans();
                //newTrans.show(fm, "cleanDebts");

            }
        });


        buttonNewTrans = (Button)this.findViewById(R.id.buttonNewTrans);
        buttonNewTrans.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                viewPager = (ViewPager)findViewById(R.id.viewpager);
                pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
                viewPager.setAdapter(pagerAdapter);
            }
        });


    }


    /**
     *
     * @return
     */
    public void readData(){

        Transaction transaction = new Transaction();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        transaction.setValue(new Double(preferences.getString(GlobalVar.SpVarNameValue, "")));
        transaction.setDescription(preferences.getString(GlobalVar.SpVarNameDetails, ""));


        String perStr = "";
        Set<String> persons = preferences.getStringSet(GlobalVar.SpVarNamePersons, null);
        Object[] ar = persons.toArray();

        for (int i = 0; i < ar.length; i++) {

            if(i < ar.length - 1){

                perStr += ar[i].toString() + ",";

            }else if(i == ar.length - 1){

                perStr += ar[i].toString();
            }
        }

        transaction.setFallsAtPersons(perStr);



        Log.d("TRANSACTION", transaction.toString());










    }



    private void init(){

        GlobalVar.Database = new Database(this);

    }


    /**
     *
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            DF_NewTrans newTrans = new DF_NewTrans();
            newTrans.setLayoutId(position);
            newTrans.setMainActivity(mainActivity);

            return newTrans;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }




    }
}
