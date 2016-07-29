package com.cgellner.yomm.OverviewPayments;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cgellner.yomm.R;

import java.util.ArrayList;

/**
 * Die Klasse reprasentiert den Adapter der Liste innerhalb einer CardView, welche alle Zahlungsdifferenzen pro Person beinhaltet.
 * @since 26.06.2016
 * @author Carolin Gellner
 */
public class RecyclerViewAdapter_OverviewCardView extends RecyclerView.Adapter<RecyclerViewAdapter_OverviewCardView.ViewHolder_OverviewCards> {


    //region Fields

    private ArrayList<OverviewCardViewContent> dataList;
    private View v;
    private ViewHolder_OverviewCards pvh;

    //endregion


    //region Constructor

    /**
     * Erstellt eine neue Instanz der Klasse.
     * @param data Liste mit Zahlungsdifferenzen
     */
    public RecyclerViewAdapter_OverviewCardView(ArrayList<OverviewCardViewContent> data) {

        this.dataList = data;
    }

    //endregion


    //region Publis Methods

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


        RecyclerViewAdapter_ListViewOfCardView adapter = new RecyclerViewAdapter_ListViewOfCardView(dataList.get(position).getDatalist());
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

    //endregion


    //region Class ViewHolder

    /**
     * Die Klasse reprasentiert ein Item in der RecyclerView-Liste innerhalb einer CardView.
     */
    public static class ViewHolder_OverviewCards extends RecyclerView.ViewHolder {

        long ID;
        CardView cardView;
        TextView personName;
        RecyclerView dataRecyclerView;

        /**
         *
         * @param itemView
         */
        ViewHolder_OverviewCards(final View itemView) {

            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardview);
            personName = (TextView) itemView.findViewById(R.id.cv_personname);
            dataRecyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView_cardviewlist);


            cardView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View view) {

                    Intent intent = new Intent(itemView.getContext(), Activity_PaymentsList.class);
                    intent.putExtra("mainperson", ID);
                    intent.putExtra("second_person", -1);
                    view.getContext().startActivity(intent);
                }
            });

        }
    }

    //endregion

}

