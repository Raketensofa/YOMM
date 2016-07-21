package com.cgellner.yomm.Adapter_ViewHolder;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cgellner.yomm.Activities.Activity_TransactionList;
import com.cgellner.yomm.Objects.Overview_Person;
import com.cgellner.yomm.R;

import java.util.ArrayList;

/**
 * Created by Carolin on 26-Jun-16.
 */
public class RecyclerViewAdapter_OverviewCards extends RecyclerView.Adapter<RecyclerViewAdapter_OverviewCards.ViewHolder_OverviewCards> {


    //region Fields

    private ArrayList<Overview_Person> dataList;
    private View v;
    private ViewHolder_OverviewCards pvh;
    //endregion


    //region Constructor

    /**
     * @param data
     */
    public RecyclerViewAdapter_OverviewCards(ArrayList<Overview_Person> data) {

        this.dataList = data;
    }

    //endregion


    @Override
    public ViewHolder_OverviewCards onCreateViewHolder(ViewGroup parent, int viewType) {

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cardview, parent, false);
        pvh = new ViewHolder_OverviewCards(v);

        return pvh;
    }


    @Override
    public void onBindViewHolder(ViewHolder_OverviewCards holder, int position) {

        holder.ID = dataList.get(position).getID();
        holder.personName.setText(dataList.get(position).getName());


        RecyclerViewAdapter_Cardview_Listview adapter = new RecyclerViewAdapter_Cardview_Listview(dataList.get(position).getDatalist());
        adapter.setMainPersonId(dataList.get(position).getID());
        LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
        holder.dataRecyclerView.setLayoutManager(llm);
        holder.dataRecyclerView.setHasFixedSize(true);
        holder.dataRecyclerView.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public static class ViewHolder_OverviewCards extends RecyclerView.ViewHolder {

        long ID;
        CardView cardView;
        TextView personName;
        RecyclerView dataRecyclerView;


        ViewHolder_OverviewCards(final View itemView) {

            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardview);
            personName = (TextView) itemView.findViewById(R.id.cv_personname);
            dataRecyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView_cardviewlist);

            cardView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View view) {

                    Intent intent = new Intent(itemView.getContext(), Activity_TransactionList.class);
                    intent.putExtra("mainperson", ID);
                    intent.putExtra("second_person", -1);
                    view.getContext().startActivity(intent);
                }
            });

        }
    }

}

