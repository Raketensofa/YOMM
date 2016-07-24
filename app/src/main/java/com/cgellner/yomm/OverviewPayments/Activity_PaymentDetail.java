package com.cgellner.yomm.OverviewPayments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.cgellner.yomm.R;


public class Activity_PaymentDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pay_detail);


        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });


        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {

            actionBar.setDisplayHomeAsUpEnabled(true);
        }



        if (savedInstanceState == null) {


            Bundle arguments = new Bundle();

            arguments.putString(Fragment_PaymentDetail.ARG_ITEM_ID,
                    getIntent().getStringExtra(Fragment_PaymentDetail.ARG_ITEM_ID));


            Fragment_PaymentDetail fragment = new Fragment_PaymentDetail();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.payment_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {

            navigateUpTo(new Intent(this, Activity_PaymentsList.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
