package com.cgellner.yomm.Objects;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.cgellner.yomm.R;

import java.util.ArrayList;

/**
 * Created by carol on 12.06.2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = RecyclerView.class.getName();


    private ArrayList<String> dataList;

    public ArrayList<String> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<String> dataList) {
        this.dataList = dataList;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = null;
        ViewHolder pvh = null;

        try {

            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listviewitemwithcheckbox, parent, false);
            pvh = new ViewHolder(v);

        }catch (Exception ex){

            Log.e(TAG, "RecyclerViewAdapter.onCreateViewHolder(): " + ex.getMessage());
            return null;
        }

        return pvh;

    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {

        try{

            if(dataList != null){

                holder.checkBox.setText(dataList.get(position));
            }

        }catch (Exception ex){

            Log.e(TAG, "RecyclerViewAdapter.onBindViewHolder(): " + ex.getMessage());

        }



    }

    @Override
    public int getItemCount() {

        return dataList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        CheckBox checkBox;
        long ID;

        ViewHolder(final View itemView) {

            super(itemView);
            checkBox = (CheckBox)itemView.findViewById(R.id.checkBoxListView);
        }

    }



}
