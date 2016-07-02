package com.cgellner.yomm.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.cgellner.yomm.Fragments.TransactionDetailFragment;
import com.cgellner.yomm.R;


public class Activity_TransactionDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transaction_detail);


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

            arguments.putString(TransactionDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(TransactionDetailFragment.ARG_ITEM_ID));


            TransactionDetailFragment fragment = new TransactionDetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.transaction_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {

            navigateUpTo(new Intent(this, Activity_TransactionList.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
