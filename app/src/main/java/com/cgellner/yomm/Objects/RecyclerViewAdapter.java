package com.cgellner.yomm.Objects;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Created by carol on 12.06.2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{


    private static final String TAG = RecyclerView.class.getName();


    private ArrayList<Person> dataListPersons;


    SharedPreferences preferences;

    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void setPersonDataList(ArrayList<Person> dataList) {
        this.dataListPersons = dataList;
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

            if(dataListPersons != null){

                holder.checkBox.setText(dataListPersons.get(position).getName());
                holder.ID = dataListPersons.get(position).getID();
                holder.preferences = preferences;
            }

        }catch (Exception ex){

            Log.e(TAG, "RecyclerViewAdapter.onBindViewHolder(): " + ex.getMessage());

        }
    }

    @Override
    public int getItemCount() {

        return dataListPersons.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        CheckBox checkBox;
        long ID;
        SharedPreferences preferences;


        ViewHolder(final View itemView) {

            super(itemView);

            checkBox = (CheckBox)itemView.findViewById(R.id.checkBoxListView);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    Set<String> persons = null;

                    try {

                       persons  = preferences.getStringSet(GlobalVar.SpVarNamePersons, null);

                        if (persons.contains(ID)) {

                            if (isChecked == false) {

                                persons.remove(ID);
                            }
                        } else{

                            if (isChecked == true) {

                                persons.add(String.valueOf(ID));
                            }
                        }

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putStringSet(GlobalVar.SpVarNamePersons, persons);
                        editor.commit();

                    }catch (Exception ex){

                        if(isChecked == true) {

                            HashSet<String> personHash = new HashSet<String>();
                            personHash.add(String.valueOf(String.valueOf(ID)));
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putStringSet(GlobalVar.SpVarNamePersons, personHash);
                            editor.commit();
                        }
                    }
                }
            });
        }

    }


}
