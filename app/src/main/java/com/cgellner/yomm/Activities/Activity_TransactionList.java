package com.cgellner.yomm.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cgellner.yomm.Fragments.TransactionDetailFragment;
import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Transaction;
import com.cgellner.yomm.R;

import com.cgellner.yomm.Objects.TransactionItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Activity_TransactionList extends AppCompatActivity {


    private boolean mTwoPane;
    private long mainpersonId;
    private long secondpersonId;

    private List<TransactionItem> contentItems;
    public static Map<String, TransactionItem> ITEM_MAP;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transaction_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        getPersonIds();

        List<Transaction> transactionArrayList = GlobalVar.Database.getTransactions(mainpersonId, secondpersonId);
        contentItems = prepareContentItems(transactionArrayList);

        ITEM_MAP = new HashMap<String, TransactionItem>();
        for (TransactionItem item : contentItems) {

            ITEM_MAP.put(item.getId(), item);
        }


        View recyclerView = findViewById(R.id.transaction_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);


        if (findViewById(R.id.transaction_detail_container) != null) {

            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(contentItems));
    }


    private void getPersonIds(){

        try {

            mainpersonId = Long.valueOf(getIntent().getExtras().get("mainperson").toString());
            secondpersonId = Long.valueOf(getIntent().getExtras().get("second_person").toString());

        }catch (Exception ex){

            mainpersonId = -1;
            secondpersonId = -1;
        }
    }


    private List<TransactionItem> prepareContentItems(List<Transaction> transactions){

        List<TransactionItem> items = new ArrayList<>();

        if(transactions != null) {

            for (int i= 0; i<transactions.size(); i++) {

                String categoryName = GlobalVar.Database.getCategoryName(transactions.get(i).getCategory());
                String debtorName = GlobalVar.Database.getPersonName(transactions.get(i).getDebtorId());
                String creditorName = GlobalVar.Database.getPersonName(transactions.get(i).getCreditorId());

                TransactionItem item =
                        new TransactionItem(String.valueOf(transactions.get(i).getID()),
                                                                String.valueOf(transactions.get(i).getValue()),
                                                                debtorName,
                                                                creditorName,
                                                                categoryName,
                                                                transactions.get(i).getDetails(),
                                                                transactions.get(i).getDate(),
                                                                transactions.get(i).getTime(),
                                                                String.valueOf(transactions.get(i).getType()));

                items.add(item);
                Log.d("", item.toString());

            }
        }

        return  items;
    }





    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<TransactionItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<TransactionItem> items) {

            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list_content, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            holder.mItem = mValues.get(position);
            holder.listview_content_date.setText(mValues.get(position).getDate());
            holder.listview_content_value.setText(mValues.get(position).getValue());
            holder.listview_content_personwhopayed.setText(mValues.get(position).getCreditor());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mTwoPane) {

                        Bundle arguments = new Bundle();
                        arguments.putString(TransactionDetailFragment.ARG_ITEM_ID, holder.mItem.getId());

                        TransactionDetailFragment fragment = new TransactionDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.transaction_detail_container, fragment)
                                .commit();

                    } else {

                        Context context = v.getContext();

                        Intent intent = new Intent(context, Activity_TransactionDetail.class);
                        intent.putExtra(TransactionDetailFragment.ARG_ITEM_ID, holder.mItem.getId());
                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {

            return contentItems.size();
        }




        public class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;
            public final TextView listview_content_date;
            public final TextView listview_content_value;
            public final TextView listview_content_personwhopayed;

            public TransactionItem mItem;

            public ViewHolder(View view) {

                super(view);

                mView = view;
                listview_content_date = (TextView) view.findViewById(R.id.listview_date);
                listview_content_value = (TextView) view.findViewById(R.id.listview_value);
                listview_content_personwhopayed = (TextView)view.findViewById(R.id.listview_person_who_payed_name);
            }

        }
    }
}
