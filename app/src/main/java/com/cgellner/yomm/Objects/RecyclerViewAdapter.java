package com.cgellner.yomm.Objects;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.R;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by carol on 12.06.2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder_Checkbox>{


    //region Fields

    private static final String TAG = RecyclerView.class.getName();
    private ArrayList<Person> personsList;
    private ArrayList<MainCategory> categoryList;
    private SharedPreferences preferences;

    //endregion


    //region Properties

    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }
    public void setPersonDataList(ArrayList<Person> dataList) {
        this.personsList = dataList;
    }

    //endregion


    //region Public Methods
    @Override
    public RecyclerViewAdapter.ViewHolder_Checkbox onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = null;
        ViewHolder_Checkbox pvh = null;

        try {

            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listviewitemwithcheckbox, parent, false);
            pvh = new ViewHolder_Checkbox(v);

        }catch (Exception ex){

            Log.e(TAG, "RecyclerViewAdapter.onCreateViewHolder(): " + ex.getMessage());
            return null;
        }

        return pvh;

    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder_Checkbox holder, int position) {

        try{

            if( personsList != null){

                holder.checkBox.setText(personsList.get(position).getName());
                holder.ID = personsList.get(position).getID();
                holder.preferences = preferences;

            }

        }catch (Exception ex){

            Log.e(TAG, "RecyclerViewAdapter.onBindViewHolder(): " + ex.getMessage());
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


    //region ViewHolder Class

    public static class ViewHolder_Checkbox extends RecyclerView.ViewHolder{

        CheckBox checkBox;
        long ID;
        SharedPreferences preferences;


        ViewHolder_Checkbox(final View itemView) {

            super(itemView);

            checkBox = (CheckBox)itemView.findViewById(R.id.checkBoxListView);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    Set<String> persons = null;

                    try {

                       persons  = preferences.getStringSet(GlobalVar.SpVarNameDebtors, null);

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
                        editor.putStringSet(GlobalVar.SpVarNameDebtors, persons);
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

    //endregion

}
