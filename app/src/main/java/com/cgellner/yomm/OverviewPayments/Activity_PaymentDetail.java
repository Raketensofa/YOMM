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


public class Activity_PaymentDetail extends AppCompatActivity {

    private String type = null;
    private String elementId;

    public void setType(String type) {
        this.type = type;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }



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

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setMessage("Bist du dir sicher, dass du dieses Element löschen möchtest?").setTitle("Löschen");

                builder.setPositiveButton("Ja, Löschen", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        //Rueckzahlung (hat nie eine Kategorie)
                        if(type.length() == 0){

                            GlobalVar.Database.deleteRepayment(Long.valueOf(elementId));
                            Activity_PaymentsList.ITEM_MAP.remove(elementId);

                        }else
                        //Ausgabe (hat immer eine Kategorie)
                        if(type.length() > 0){

                            GlobalVar.Database.deleteRepayment(Long.valueOf(elementId));
                            Activity_PaymentsList.ITEM_MAP.remove(elementId);


                        }
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
}
