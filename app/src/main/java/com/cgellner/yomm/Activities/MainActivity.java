package com.cgellner.yomm.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;

import com.cgellner.yomm.Database.Database;
import com.cgellner.yomm.DialogFragments.DF_NewTrans;
import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.MainCategory;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button buttonNewTrans;
    private Button buttonCleanDebts;


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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

                android.app.FragmentManager fm = getFragmentManager();
                DF_NewTrans newTrans = new DF_NewTrans();
                newTrans.show(fm, "newTrans");
            }
        });


    }



    private void init(){

        GlobalVar.Database = new Database(this);

    }
}
