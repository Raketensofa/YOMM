package com.cgellner.yomm.Activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;


import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.cgellner.yomm.Database.Database;
import com.cgellner.yomm.Fragments.Fragment_Overview;
import com.cgellner.yomm.Fragments.Fragment_Start;
import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Category;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.Objects.Transaction;
import com.cgellner.yomm.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    //FragmentTransaction zum Oeffnen verschiedener Fragmente innerhalb einer Activity
    public static FragmentTransaction fragmentTransaction;


    private static SharedPreferences preferences;

    Fragment_Start fragmentStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main3);

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



        //Neues Datenbankobjekt erstellen fuer globalen Zugriff innerhalb des Programmcodes
        GlobalVar.Database = new Database(this);


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
            intent = new Intent(this, Settings.class);
            intent.putExtra("object", Person.class.getName());
            startActivity(intent);

        } else if (id == R.id.nav_categories) {

            //neue Activity starten
            intent = new Intent(this, Settings.class);
            intent.putExtra("object", Category.class.getName());
            startActivity(intent);
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }






    public static void readData(){


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
        for (Transaction transaction : transactions) {

            GlobalVar.Database.insertTransaction(transaction);
        }


        Fragment_Start start = new Fragment_Start();
        fragmentTransaction = MainActivity.this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, start);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }





    /**
     *
     * @return
     */
    private static ArrayList<Transaction> getSplittedTransaction(int type, String date, String time, double value, long creditor, String debtors, long category, String details){

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
