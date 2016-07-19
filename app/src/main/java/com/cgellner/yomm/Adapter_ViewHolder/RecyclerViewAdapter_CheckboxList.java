package com.cgellner.yomm.Adapter_ViewHolder;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.R;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by carol on 12.06.2016.
 */
public class RecyclerViewAdapter_CheckboxList extends RecyclerView.Adapter<RecyclerViewAdapter_CheckboxList.ViewHolder_CheckboxList>{


    //region Fields

    private static final String TAG = RecyclerView.class.getName();
    private ArrayList<Person> personsList;

    private SharedPreferences sharedPreferences;
    private ViewHolder_CheckboxList viewholder;

    //endregion


    //region Properties

    public void setPersonDataList(ArrayList<Person> dataList) {
        this.personsList = dataList;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    //endregion


    //region Public Methods
    @Override
    public ViewHolder_CheckboxList onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = null;


        try {

            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listviewitemwithcheckbox, parent, false);
            viewholder = new ViewHolder_CheckboxList(v);


        }catch (Exception ex){

            Log.e(TAG, "RecyclerViewAdapter_CheckboxList.onCreateViewHolder(): " + ex.getMessage());
            return null;
        }

        return viewholder;

    }

    @Override
    public void onBindViewHolder(ViewHolder_CheckboxList holder, int position) {

        try{

            if( personsList != null) {

                holder.checkBox.setText(personsList.get(position).getName());
                holder.ID = personsList.get(position).getID();
                holder.preferences = sharedPreferences;

                Set<String> persons = sharedPreferences.getStringSet(GlobalVar.SpVarNameDebtors, null);


                if (persons.size() > 0) {

                    if (persons.contains(String.valueOf(holder.ID))) {

                        holder.checkBox.setChecked(true);
                    }
                }
            }

        }catch (Exception ex){

            Log.e(TAG, "RecyclerViewAdapter_CheckboxList.onBindViewHolder(): " + ex.getMessage());
        }
    }

    /**
     *
     * @return
     */
    @Override
    public int getItemCount() {

        int count = 0;

        if(personsList != null){

            count = personsList.size();

        }

        return count;
    }

    //endregion



    public static class ViewHolder_CheckboxList extends RecyclerView.ViewHolder{


        CheckBox checkBox;
        long ID;
        SharedPreferences preferences;


        ViewHolder_CheckboxList(final View itemView) {

            super(itemView);

            checkBox = (CheckBox)itemView.findViewById(R.id.checkBoxListView);


            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    Set<String> debtors = null;

                    try {

                        debtors  = preferences.getStringSet(GlobalVar.SpVarNameDebtors, null);

                        if(isChecked == true){

                            debtors.add(String.valueOf(ID));

                        }else if(isChecked == false){

                            debtors.remove(String.valueOf(ID));
                        }

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putStringSet(GlobalVar.SpVarNameDebtors, debtors);
                        editor.commit();

                    }catch (Exception ex){

                        if(isChecked == true) {

                            HashSet<String> personHash = new HashSet<String>();
                            personHash.add(String.valueOf(String.valueOf(ID)));
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putStringSet(GlobalVar.SpVarNameDebtors, personHash);
                            editor.commit();

                        }
                    }
                }
            });
        }

    }

}
