package com.cgellner.yomm.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
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

import com.badoualy.stepperindicator.StepperIndicator;
import com.cgellner.yomm.Database.Database;
import com.cgellner.yomm.DialogFragments.DF_NewTrans;
import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.MainCategory;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.Objects.Transaction;
import com.cgellner.yomm.R;
import com.synnapps.carouselview.CirclePageIndicator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button buttonNewTrans;
    private Button buttonCleanDebts;

    private ViewPager viewPager;
    private FragmentStatePagerAdapter pagerAdapter;
    private StepperIndicator stepperIndicator;

    private static final int NUM_PAGES = 5;

    private MainActivity mainActivity = this;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

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

                stepperIndicator = (StepperIndicator) findViewById(R.id.viewpageindicator);
                viewPager = (ViewPager)findViewById(R.id.viewpager);
                pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
                viewPager.setAdapter(pagerAdapter);

                stepperIndicator.setViewPager(viewPager);

            }
        });


    }


    /**
     *
     * @return
     */
    public void readData(){


        //Typ (Ausgabe == 1; Schuldbegleichung == 2)
        int type = 1;
        Log.d("Type", String.valueOf(type));



        //aktuelles Datum und Uhrzeit ermitteln
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        Date currentTimestamp = new Date();
        String date = dateFormatter.format(currentTimestamp);
        String time = timeFormatter.format(currentTimestamp);

        Log.d("Date", String.valueOf(date));
        Log.d("Time", String.valueOf(time));



        //SharedPreferences auslesen
             SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

            //Geldbetrag
            double value = new Double(preferences.getString(GlobalVar.SpVarNameValue, ""));
            Log.d("Value", String.valueOf(value));


        //Details
            String details = preferences.getString(GlobalVar.SpVarNameDetails, "");
            Log.d("Details", String.valueOf(details));


        //Debitoren
            String debtors = "";
            Set<String> persons = preferences.getStringSet(GlobalVar.SpVarNameDebtors, null);
            Object[] ar = persons.toArray();

            for (int i = 0; i < ar.length; i++) {

                if(i < ar.length - 1){

                    debtors += ar[i].toString() + ",";

                }else if(i == ar.length - 1){

                    debtors += ar[i].toString();
                }
            }

        Log.d("Debtors", String.valueOf(debtors));


        //Kreditor
         long creditor = preferences.getLong(GlobalVar.SpVarNameCreditor, 0);
        Log.d("Creditor", String.valueOf(creditor));


        //Kategorie
        long category = preferences.getLong(GlobalVar.SpVarNameCategory, 0);
        Log.d("Catrgory", String.valueOf(category));



        ArrayList<Transaction> transactions = getSplittedTransaction(type, date, time, value, creditor, debtors, category, details );

        Log.d("Transactions Size", String.valueOf(transactions.size()));


        //...... Transaktionen in der Liste in die Datenbank schreiben

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

    /**
     *
     * @return
     */
    private ArrayList<Transaction> getSplittedTransaction(int type, String date, String time, double value, long creditor, String debtors, long category, String details){

       ArrayList<Transaction> transactions = new ArrayList<>();

        String[] debtorIds = debtors.split(",");

        double valuePerPerson = Math.round(((value / debtorIds.length) * 100.0)) / 100.0;

        for (String debtor : debtorIds) {

            if(new Long(debtor) !=  creditor) {

                Transaction trans = new Transaction();
                trans.setType(type);
                trans.setDate(date);
                trans.setTime(time);
                trans.setCreditorId(creditor);
                trans.setDebtorId(new Long(debtor));
                trans.setCategory(category);
                trans.setDetails(details);
                trans.setValue(valuePerPerson);

                transactions.add(trans);
                Log.d("", trans.toString());
            }
        }



        return transactions;
    }
}
