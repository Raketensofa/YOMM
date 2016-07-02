package com.cgellner.yomm.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cgellner.yomm.Fragments.TransactionDetailFragment;
import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Transaction;
import com.cgellner.yomm.R;

import com.cgellner.yomm.Objects.TransactionContent;

import java.util.ArrayList;
import java.util.List;


public class Activity_TransactionList extends AppCompatActivity {


    private boolean mTwoPane;
    private long mainpersonId;
    private long secondpersonId;

    private ArrayList<TransactionContent.TransactionItem> contentItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transaction_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        getPersonIds();

        ArrayList<Transaction> transactionArrayList = GlobalVar.Database.getTransactions(mainpersonId, secondpersonId);
        contentItems = prepareContentItems(transactionArrayList);



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


    private ArrayList<TransactionContent.TransactionItem> prepareContentItems(ArrayList<Transaction> transactions){

        ArrayList<TransactionContent.TransactionItem> items = new ArrayList<>();

        if(transactions != null) {

            for (Transaction trans : transactions) {

                String categoryName = GlobalVar.Database.getCategoryName(trans.getCategory());
                String debtorName = GlobalVar.Database.getPersonName(trans.getDebtorId());
                String creditorName = GlobalVar.Database.getPersonName(trans.getCreditorId());

                TransactionContent.TransactionItem item =
                        new TransactionContent.TransactionItem(String.valueOf(trans.getID()),
                                                                String.valueOf(trans.getValue()),
                                                                debtorName,
                                                                creditorName,
                                                                categoryName,
                                                                trans.getDetails(),
                                                                trans.getDate(),
                                                                trans.getTime(),
                                                                String.valueOf(trans.getType()));

                items.add(item);
            }
        }

        return  items;
    }





    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<TransactionContent.TransactionItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<TransactionContent.TransactionItem> items) {

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
            holder.listview_content_date.setText(mValues.get(position).date);
            holder.listview_content_value.setText(mValues.get(position).value);
            holder.listview_content_personwhopayed.setText(mValues.get(position).creditor);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mTwoPane) {

                        Bundle arguments = new Bundle();
                        arguments.putString(TransactionDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        TransactionDetailFragment fragment = new TransactionDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.transaction_detail_container, fragment)
                                .commit();

                    } else {

                        Context context = v.getContext();

                        Intent intent = new Intent(context, Activity_TransactionDetail.class);
                        intent.putExtra(TransactionDetailFragment.ARG_ITEM_ID, holder.mItem.id);

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

            public TransactionContent.TransactionItem mItem;

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
