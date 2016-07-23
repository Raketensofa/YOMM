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
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Payment;
import com.cgellner.yomm.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Activity_PaymentsList extends AppCompatActivity {


    //region Fields

    private boolean mTwoPane;
    private long mainpersonId;
    private long secondpersonId;

    private List<PaymentItem> contentItems;
    public static Map<String, PaymentItem> ITEM_MAP;

    //endregion


    //region Methods

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payments_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getPersonIds();

        setActivtityTitle();

        List<Payment> paymentArrayList = GlobalVar.Database.getPayments(mainpersonId, secondpersonId);
        contentItems = prepareContentItems(paymentArrayList);

        ITEM_MAP = new HashMap<String, PaymentItem>();
        for (PaymentItem item : contentItems) {

            ITEM_MAP.put(item.getId(), item);
        }


        View recyclerView = findViewById(R.id.payment_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);


        if (findViewById(R.id.payment_detail_container) != null) {

            mTwoPane = true;
        }
    }

    /**
     *
     * @param recyclerView
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(contentItems));
        recyclerView.setBackgroundColor(Color.WHITE);
    }


    /**
     *
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

        this.setTitle(title);
    }


    /**
     *
     * @param payments
     * @return
     */
    private List<PaymentItem> prepareContentItems(List<Payment> payments){

        List<PaymentItem> items = new ArrayList<>();

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");

        if(payments != null) {

            for (int i = 0; i< payments.size(); i++) {

                String categoryName = GlobalVar.Database.getCategoryName(payments.get(i).getCategoryId());
                String debtorName = GlobalVar.Database.getPersonName(payments.get(i).getDebtorId());
                String creditorName = GlobalVar.Database.getPersonName(payments.get(i).getCreditorId());

                PaymentItem item = new PaymentItem();
                item.setId(String.valueOf(payments.get(i).getID()));
                item.setCreditor(creditorName);
                item.setDebtor(debtorName);
                item.setCategory(categoryName);
                item.setValue(String.valueOf(payments.get(i).getValue()));
                item.setDetails( payments.get(i).getDetails());
                item.setDate(dateFormatter.format(payments.get(i).getDateTime()));
                item.setTime(timeFormatter.format(payments.get(i).getDateTime()));
                item.setMainMoneyValue(String.valueOf(payments.get(i).getMoneySum()));

                items.add(item);

            }
        }

        return  items;
    }


    //endregion


    //region RecyclerViewAdapter

    /**
     *
     */
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

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_list_content, parent, false);

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
            holder.listview_content_creditor.setText(mValues.get(position).getDebtor());

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
         *
         */
        public class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;
            public final TextView listview_content_date;
            public final TextView listview_content_value;
            public final TextView listview_content_creditor;

            public final TextView listview_content_creditorheader;

            public PaymentItem mItem;

            public ViewHolder(View view) {

                super(view);

                mView = view;
                listview_content_date = (TextView) view.findViewById(R.id.listview_date);
                listview_content_value = (TextView) view.findViewById(R.id.listview_value);
                listview_content_creditor = (TextView)view.findViewById(R.id.listview_creditor);
                listview_content_creditorheader = (TextView)view.findViewById(R.id.listview_creditorheader);



                RelativeLayout.LayoutParams paramDate = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                paramDate.width = (GlobalVar.Display_Width / 100) * 20;
                paramDate.height = 60;
                paramDate.setMargins(5,5,5,5);
                listview_content_date.setLayoutParams(paramDate);

                RelativeLayout.LayoutParams paramValue = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                paramValue.width = (GlobalVar.Display_Width / 100) * 35;
                paramValue.height = 60;
                paramValue.addRule(RelativeLayout.RIGHT_OF, listview_content_date.getId());
                paramValue.setMargins(5,5,5,5);
                listview_content_value.setLayoutParams(paramValue);

                RelativeLayout.LayoutParams paramcreditorheader = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                paramcreditorheader.width = (GlobalVar.Display_Width / 100) * 45;
                paramcreditorheader.addRule(RelativeLayout.RIGHT_OF, listview_content_value.getId());
                paramcreditorheader.setMargins(5,5,5,0);
                paramcreditorheader.height = 60;
                listview_content_creditorheader.setLayoutParams(paramcreditorheader);

                RelativeLayout.LayoutParams paramcreditor = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                paramcreditor.width = (GlobalVar.Display_Width / 100) * 45;
                paramcreditor.addRule(RelativeLayout.RIGHT_OF, listview_content_value.getId());
                paramcreditor.addRule(RelativeLayout.BELOW, listview_content_creditorheader.getId());
                paramcreditor.setMargins(5, 0, 5, 5);
                paramcreditor.height = 60;
                listview_content_creditor.setLayoutParams(paramcreditor);

            }
        }

        /**
         *
         * @param date
         * @return
         */
        private String formatDate(String date){

            return date.substring(0, 2) + "." + date.substring(3, 5) + ".";
        }



    }

    //endregion

}
