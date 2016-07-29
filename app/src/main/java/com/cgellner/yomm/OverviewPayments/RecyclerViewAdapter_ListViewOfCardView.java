package com.cgellner.yomm.OverviewPayments;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cgellner.yomm.R;

import java.util.ArrayList;


/**
 * Die Klasse reprasentiert den RecyclerViewAdapter fuer die Liste mit den CardViews fuer die Uebersichtsliste.
 * @since 28.06.2016
 * @author Carolin Gellner
 */
public class RecyclerViewAdapter_ListViewOfCardView extends RecyclerView.Adapter<RecyclerViewAdapter_ListViewOfCardView.ViewHolder_Cardview_RecyclerView> {


    //region Fields

    private ArrayList<String[]> data;
    ViewHolder_Cardview_RecyclerView pvh;
    View v;
    private long mainPersonId;


    //endregion


    //region Getter & Setter

    public RecyclerViewAdapter_ListViewOfCardView(ArrayList<String[]> data) {

        this.data = data;
    }


    /**
     * @param mainPersonId
     */
    public void setMainPersonId(long mainPersonId) {
        this.mainPersonId = mainPersonId;
    }

    //endregion


    //region Public Methods


    /**
     * Die Methode erstellt die Ansicht der CardView.
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder_Cardview_RecyclerView onCreateViewHolder(ViewGroup parent, int viewType) {

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cardview_listview_item, parent, false);

        pvh = new ViewHolder_Cardview_RecyclerView(v);

        return pvh;

    }


    /**
     * Die Methode uebergibt der CardView die Daten, welche angezeigt werden sollen.
     * @param holder CardView
     * @param position Position der CardView in der Liste (RecylcerView)
     */
    @Override
    public void onBindViewHolder(ViewHolder_Cardview_RecyclerView holder, int position) {

        if (data != null) {

            holder.mainPersonId = mainPersonId;
            holder.personId = new Long(data.get(position)[0]);
            holder.textViewPersonName.setText(data.get(position)[1]);
            holder.textViewValue.setText(data.get(position)[2]);

            if (new Double(data.get(position)[2].toString().replace(",", ".")) < 0) {

                //rot, da die Hauptperson der Cardview Schulden an die betreffende Person hat, hinter der der rote Betrag steht
                holder.textViewValue.setTextColor(Color.parseColor("#EE2C2C"));

            } else if (new Double(data.get(position)[2].toString().replace(",", ".")) > 0) {

                //gruen, da die Hauptperson der Cardview Geld von der betreffenden Person noch zurueck bekommt, hinter der der rote Betrag steht
                holder.textViewValue.setTextColor(Color.parseColor("#2e5f48"));

            } else {

                holder.textViewValue.setTextColor(Color.DKGRAY);
            }
        }
    }


    /**
     * Die Methode gibt die Anzahl der Elemente in der Liste wieder.
     * @return Elementanzahl
     */
    @Override
    public int getItemCount() {

        return data.size();
    }

    //endregion


    //region RecyclerView.ViewHolder

    /**
     * Die Klasse reprasentiert eine CardView in der Uebersichtsliste.
     */
    public static class ViewHolder_Cardview_RecyclerView extends RecyclerView.ViewHolder {

        long personId;
        long mainPersonId;

        TextView textViewPersonName;
        TextView textViewValue;


        /**
         * @param itemView
         */
        public ViewHolder_Cardview_RecyclerView(View itemView) {

            super(itemView);

            textViewPersonName = (TextView) itemView.findViewById(R.id.cv_listview_item_personname);
            textViewValue = (TextView) itemView.findViewById(R.id.cv_listview_item_value);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), Activity_PaymentsList.class);
                    intent.putExtra("mainperson", mainPersonId);
                    intent.putExtra("second_person", personId);
                    v.getContext().startActivity(intent);
                }
            });

        }
    }

    //endregion
}
