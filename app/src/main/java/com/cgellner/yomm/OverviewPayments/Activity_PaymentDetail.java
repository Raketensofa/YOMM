package com.cgellner.yomm.OverviewPayments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.R;



/**
 * Die Klasse repraesentiert eine Activity in welcher das Detail einer (Rueckzahlung) Repayment oder Ausgabe (Payment)  im Rahmen des
 * Master-Detail-Layouts angezeigt wird.
 * @author Carolin Gellner
 */
public class Activity_PaymentDetail extends AppCompatActivity {


    //region Fields

    private String type = null;
    private String elementId;

    //endregion


    //region Protected & Public Methods

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pay_detail);


        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        elementId = getIntent().getStringExtra(Fragment_PaymentDetail.ARG_ITEM_ID);
        type = getIntent().getStringExtra("type");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setClickable(true);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setMessage("Bist du dir sicher, dass du dieses Element löschen möchtest?").setTitle("Löschen");

                builder.setPositiveButton("Ja, Löschen", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        //Rueckzahlung (hat nie eine Kategorie)
                        if (type.length() == 0) {

                            GlobalVar.Database.deleteRepayment(Long.valueOf(elementId));

                        } else
                            //Ausgabe (hat immer eine Kategorie)
                            if (type.length() > 0) {

                                GlobalVar.Database.deletePayment(Long.valueOf(elementId));
                            }

                        onBackPressed();
                    }

                });
                builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });



        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();

            arguments.putString(Fragment_PaymentDetail.ARG_ITEM_ID, elementId);

            Fragment_PaymentDetail fragment = new Fragment_PaymentDetail();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.payment_detail_container, fragment)
                    .commit();
        }
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    //endregion
}
