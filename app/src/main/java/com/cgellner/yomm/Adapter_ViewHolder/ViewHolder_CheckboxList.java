package com.cgellner.yomm.Adapter_ViewHolder;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.R;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Carolin on 26-Jun-16.
 */
public class ViewHolder_CheckboxList extends RecyclerView.ViewHolder{


        public CheckBox checkBox;
        public long ID;
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
