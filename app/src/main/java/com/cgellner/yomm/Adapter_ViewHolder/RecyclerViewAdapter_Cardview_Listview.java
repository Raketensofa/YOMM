package com.cgellner.yomm.Adapter_ViewHolder;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cgellner.yomm.Activities.Activity_TransactionList;
import com.cgellner.yomm.R;

import java.util.ArrayList;


/**
 * Created by Carolin on 28-Jun-16.
 */
public class RecyclerViewAdapter_Cardview_Listview extends RecyclerView.Adapter<RecyclerViewAdapter_Cardview_Listview.ViewHolder_Cardview_RecyclerView> {


    //region Fields

    private ArrayList<String[]> data;
    ViewHolder_Cardview_RecyclerView pvh;
    View v;
    private long mainPersonId;


    //endregion


    //region Getter & Setter

    public RecyclerViewAdapter_Cardview_Listview(ArrayList<String[]> data) {

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
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder_Cardview_RecyclerView holder, int position) {

        if (data != null) {

            holder.mainPersonId = mainPersonId;
            holder.personId = new Long(data.get(position)[0]);
            holder.textViewPersonName.setText(data.get(position)[1]);
            holder.textViewValue.setText(data.get(position)[2]);

            if (new Double(data.get(position)[2].toString().replace(",", ".")) < 0) {

                holder.textViewValue.setTextColor(Color.RED);

            } else if (new Double(data.get(position)[2].toString().replace(",", ".")) > 0) {

                holder.textViewValue.setTextColor(Color.BLUE);

            } else {

                holder.textViewValue.setTextColor(Color.DKGRAY);
            }
        }
    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {

        return data.size();
    }


    /**
     *
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

                    Intent intent = new Intent(v.getContext(), Activity_TransactionList.class);
                    intent.putExtra("mainperson", mainPersonId);
                    intent.putExtra("second_person", personId);
                    v.getContext().startActivity(intent);
                }
            });

        }
    }

    //endregion
}
