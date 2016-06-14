package com.cgellner.yomm.Activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.StrictMode;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button buttonNewTrans;
    private Button buttonCleanDebts;

    private ViewPager viewPager;
    private FragmentStatePagerAdapter pagerAdapter;

    private static final int NUM_PAGES = 5;

    private MainActivity mainActivity = this;


    private View firstView;


    private Bundle saveInstanceState;

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

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


        if (viewPager.getCurrentItem() == 0) {


            viewPager.clearFocus();
            /**Fragment fragment = new Fragment().set;
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();*/


            //super.onBackPressed();

        } else {

            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }

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

        //viewPager.setCurrentItem(0, false);

        //--------------------------------------------------------------------------------------------------- hier weitermachen
        //irgendwie ein Thread ... Session etc. erstellen und die Daten der Layouts direkt auslesen bevor ein anderes Layout angezeigt wird
        //können nur ausgelesen werden, wenn das Item das aktuelle ist
        EditText eTvalue = (EditText)findViewById(R.id.eTTransValue);
        String value = eTvalue.getText().toString();
        transaction.setValue(new Double(value));
        Log.d("VALUE", String.valueOf(transaction.getValue()));



        //Betrag


        //Personen die gezahlt haben
        //ListView personsPayed = (ListView)view.findViewById(R.id.listViewTransPersons);
        //long[] checkedIds = personsPayed.getCheckedItemIds();


        //Personen fuer die der Betrag anfaellt



        //Kategorie



        //Details der Ausgabe



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
