package com.cgellner.yomm.OverviewPayments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.cgellner.yomm.Database.Database;
import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Category;
import com.cgellner.yomm.Objects.Pay;
import com.cgellner.yomm.Objects.Payment;
import com.cgellner.yomm.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Die Klasse repraesentiert eine Activity, welche Teil den Master-Teil des sogenannten Master-Details-Layouts beinhaltet und
 * in diesem Fall eine Liste von Payment- und Repayment-Datensaetzen beinhaltet.
 * @author Carolin Gellner
 */
public class Activity_PaymentsList extends AppCompatActivity {


    //region Fields

    private boolean mTwoPane;
    private long mainpersonId;
    private long secondpersonId;
    private Spinner categorySpinner;
    private List<PaymentItem> contentItems;
    private ArrayList<Pay> payDatasets;
    private View recyclerView;


    public static Map<String, PaymentItem> ITEM_MAP;

    //endregion


    //region Protected Method

    /**
     * Die Methode erstellt die Ansicht.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pay_list);

        GlobalVar.Database = new Database(this);

        //uebergebene IDs der Personen abfragen
        getPersonIds();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setActivtityTitle();


        //Spinner erstellen zur Auswahl der Kategorie
        categorySpinner = (Spinner)findViewById(R.id.activity_pay_list_spinner_category);
        setCategorySpinner(categorySpinner);

        //Daten der Ausgaben und Rueckzahlungen aus der Datenbank abfragen
        payDatasets = GlobalVar.Database.getPaymentsAndRepayments(mainpersonId, secondpersonId, 0);
        //Datensaetze aufbereiten
        contentItems = prepareContentItems(payDatasets);

        ITEM_MAP = new HashMap<String, PaymentItem>();
        for (PaymentItem item : contentItems) {

            ITEM_MAP.put(item.getId(), item);
        }


        recyclerView = findViewById(R.id.activity_pay_list_recyclerView_paylist);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);


        if (findViewById(R.id.payment_detail_container) != null) {

            mTwoPane = true;
        }
    }

    //endregion


    //region Public Method

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


    /**
     * Die Methode erstellt den Spinner, mit welchem ausgewaehlt werden kann, welche Elemente (Ausgabe, Rueckzahlung oder beides) angezeigt werden soll.
     * @param spinner Spinner
     */
    public void setCategorySpinner(Spinner spinner){

        String[] payTypes = new String[3];
        payTypes[0] = "Alle anzeigen";
        payTypes[1] = "Nur Ausgaben";
        payTypes[2] = "Nur Rückzahlungen";


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, payTypes);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    //Ansicht aktualisieren
                    updateRecyclerView(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    /**
     * Die Methode erstellt die RecyclerView.
     * Position 0 bedeutet "alle Elemente", Position 1 bedeutet "nur Ausgaben" und Positon 3 bedeutet "nur Rueckzahlungen"
     * @param position Position im Spinner
     */
    public void updateRecyclerView(int position){

        //Datensaetze abfragen
        payDatasets = GlobalVar.Database.getPaymentsAndRepayments(mainpersonId, secondpersonId, position);

        //Datensaetze aufbereiten
        contentItems = prepareContentItems(payDatasets);

        ITEM_MAP = new HashMap<String, PaymentItem>();
        for (PaymentItem item : contentItems) {

            ITEM_MAP.put(item.getId(), item);
        }

        setupRecyclerView((RecyclerView) recyclerView);
    }


    //endregion



    //region Private Method

    /**
     * Die Methode uebergibt der RecyclerView die Daten, welche angezeigt werden sollen.
     * @param recyclerView RecyclerView
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(contentItems));
        recyclerView.setBackgroundColor(Color.WHITE);
    }


    /**
     * Ruft die uebergebenen IDs der Personen aus den Extras ab.
     */
    private void getPersonIds(){

        try {

            mainpersonId = Long.valueOf(getIntent().getExtras().get("mainperson").toString());
            secondpersonId = Long.valueOf(getIntent().getExtras().get("second_person").toString());

        }catch (Exception ex){

            mainpersonId = -1;
            secondpersonId = -1;
        }
    }


    /**
     * Legt den Titel der Activity fest.
     */
    private void setActivtityTitle(){

        String title = "";

        if(mainpersonId > 0 && secondpersonId > 0){

            String nameMainPerson = GlobalVar.Database.getPersonName(mainpersonId);
            String nameSecondPerson = GlobalVar.Database.getPersonName(secondpersonId);

            title = nameMainPerson + " <> " + nameSecondPerson;

        }else if(mainpersonId > 0 && secondpersonId <= 0){

            String namePerson = GlobalVar.Database.getPersonName(mainpersonId);

            title = namePerson;

        }else{

            title = "Zahlungen";
        }

        getSupportActionBar().setTitle(title);
    }


    /**
     * Die Methode bereitet die ContentItems vor.
     * @param payments Liste mit Pay-Datensaetzen (sind Ausgaben und Rueckzahlungen)
     * @return Liste mit aufbereiteten PaymentItem-Objekten fuer die Darstellung im Master-Detail
     */
    private List<PaymentItem> prepareContentItems(List<Pay> payments){

        List<PaymentItem> items = new ArrayList<>();

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");

        if(payments != null) {

            for (int i = 0; i< payments.size(); i++) {

                String categoryName = "";
                String debtorName = "";
                String creditorName = "";
                String moneySum = "";


                if(payments.get(i) instanceof Payment){
                //Dann einen Datensatz aufbereiten der eine Ausgabe darstellt

                    Payment paym = (Payment)payments.get(i);

                     categoryName = GlobalVar.Database.getCategoryName(paym.getCategoryId());
                     debtorName = GlobalVar.Database.getPersonName(paym.getDebtorId());
                     creditorName = GlobalVar.Database.getPersonName(paym.getCreditorId());
                     moneySum = String.valueOf(paym.getMoneySum());


                }else{

                    //Dann einen Datensatz aufbereiten der eine Rueckzahlung darstellt
                     debtorName = GlobalVar.Database.getPersonName(payments.get(i).getDebtorId());
                     creditorName = GlobalVar.Database.getPersonName(payments.get(i).getCreditorId());

                }


                PaymentItem item = new PaymentItem();
                item.setId(String.valueOf(payments.get(i).getID()));
                item.setCreditor(creditorName);
                item.setDebtor(debtorName);
                item.setCategory(categoryName);
                item.setValue(String.valueOf(payments.get(i).getValue()));
                item.setDetails( payments.get(i).getDetails());
                item.setDate(dateFormatter.format(payments.get(i).getDateTime()));
                item.setTime(timeFormatter.format(payments.get(i).getDateTime()));
                item.setMainMoneyValue(moneySum);

                items.add(item);

            }
        }

        return  items;
    }


    //endregion


    //region RecyclerViewAdapter


    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<PaymentItem> mValues;



        public SimpleItemRecyclerViewAdapter(List<PaymentItem> items) {

            mValues = items;
        }


        /**
         *
         * @param parent
         * @param viewType
         * @return
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pay_list_content, parent, false);

            return new ViewHolder(view);
        }


        /**
         *
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            holder.mItem = mValues.get(position);

            holder.listview_content_date.setText(formatDate(mValues.get(position).getDate()));
            holder.listview_content_value.setText(GlobalVar.formatMoney(mValues.get(position).getValue()));


            //wenn es eine Rueckzahlung ist (Rueckzahlung hat keine Kategorie) die Schriftfarbe in gruen machen
            if(holder.mItem.getCategory().length() == 0){

                holder.listview_content_date.setTextColor(Color.parseColor("#008B45"));
                holder.listview_content_value.setTextColor(Color.parseColor("#008B45"));
            }

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (mTwoPane) {

                        Bundle arguments = new Bundle();
                        arguments.putString(Fragment_PaymentDetail.ARG_ITEM_ID, holder.mItem.getId());

                        Fragment_PaymentDetail fragment = new Fragment_PaymentDetail();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.payment_detail_container, fragment)
                                .commit();

                    } else {

                        Context context = v.getContext();

                        Intent intent = new Intent(context, Activity_PaymentDetail.class);
                        intent.putExtra(Fragment_PaymentDetail.ARG_ITEM_ID, holder.mItem.getId());
                        intent.putExtra("type", holder.mItem.getCategory());
                        context.startActivity(intent);
                    }
                }
            });
        }

        /**
         *
         * @return
         */
        @Override
        public int getItemCount() {

            return contentItems.size();
        }


        /**
         * Die Klasse enthaelt den ViewHolder
         */
        public class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;
            public final TextView listview_content_date;
            public final TextView listview_content_value;


            public PaymentItem mItem;

            public ViewHolder(View view) {

                super(view);

                mView = view;
                listview_content_date = (TextView) view.findViewById(R.id.listview_date);
                listview_content_value = (TextView) view.findViewById(R.id.listview_value);

            }
        }


        /**
         * Formatiert das Datum ins Format dd.MM.
         * @param date Datum im Format dd.MM.yyyy HH:mm:ss
         * @return Formatiertes Datum
         */
        private String formatDate(String date){

            return date.substring(0, 2) + "." + date.substring(3, 5) + ".";
        }

    }

    //endregion

}
