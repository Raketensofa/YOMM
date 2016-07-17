package com.cgellner.yomm.Fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cgellner.yomm.Adapter_ViewHolder.RecyclerViewAdapter_CheckboxList;
import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Category;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.R;

import java.util.ArrayList;
import java.util.Set;


/**
 * Die Klasse repraesentiert ein Formular zum Erfassen einer neuen Ausgabe (Transaktion).
 * @author Carolin Gellner
 * @since 31.05.2016
 */
public class Fragment_NewTransaction extends Fragment {


    //region Fields

    private int layoutId;
    private int position;
    private RecyclerViewAdapter_CheckboxList recyclerViewAdapter;
    private RecyclerView recyclerView;
    private EditText eTValue;
    private EditText eTDetails;

    private ArrayList<Person> personList;
    private ArrayList<Category> categoryList;

    private  ViewGroup viewGroup;
    private SharedPreferences sharedPreferences;


    //endregion


    //region Getter & Setter

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public void setCategoryList(ArrayList<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public void setPersonList(ArrayList<Person> personList) {
        this.personList = personList;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    //endregion


    //region Public Methods

    /**
     * Die Methode erstellt eine Ansicht im ViewPager.
     * @param savedInstanceState
     * @return view : Ansicht im ViewPager
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        //Layout uebergeben
        viewGroup = (ViewGroup)inflater.inflate(layoutId, container, false);








        return viewGroup;
    }

    //endregion


    //region Private Methods



    /**
     * Die Methode ueberprueft die Variablen die in den SharedPreferences hinterlegt sind auf ihre Vollstaendigkeit.
     * Hat der Anwender einen Betrag, einen Kreditor, Debitor(en) und eine Kategorie gewaehlt wird der Speichern-Button
     * freigeschaltet und in roter Farbe angezeigt
     */
    private void checkInputDataNewTrans(){

        Set debtors = sharedPreferences.getStringSet(GlobalVar.SpVarNameDebtors, null);
        Float value = sharedPreferences.getFloat(GlobalVar.SpVarNameValue, 0);
        Long creditor = sharedPreferences.getLong(GlobalVar.SpVarNameCreditor, 0);
        Long category = sharedPreferences.getLong(GlobalVar.SpVarNameCategory, 0);

        Button button = (Button)getActivity().findViewById(R.id.button_viewpager_save);


        if(value != 0 && creditor != 0 && debtors.size() > 0 && category != 0){

            button.setBackgroundColor(Color.parseColor("#d50000"));
            button.setClickable(true);
            button.setEnabled(true);

        }else{

            button.setEnabled(false);
            button.setClickable(false);
            button.setBackgroundColor(Color.parseColor("#5a595b"));

        }
    }


    private void checkInputDataCleanDebts(){

        Set transactions = sharedPreferences.getStringSet(GlobalVar.SpVarNameTransactions, null);
        Float sum = sharedPreferences.getFloat(GlobalVar.SpVarNameSumClearValue, 0);
        Long creditor = sharedPreferences.getLong(GlobalVar.SpVarNameClearCreditor, 0);
        Long debtor = sharedPreferences.getLong(GlobalVar.SpVarNameClearDebitor, 0);

        Button button = (Button)getActivity().findViewById(R.id.button_viewpager_save);


        if(sum > 0 && creditor != 0 && debtor != 0 && transactions != null){

            button.setBackgroundColor(Color.parseColor("#d50000"));
            button.setClickable(true);
            button.setEnabled(true);

        }else{

            button.setEnabled(false);
            button.setClickable(false);
            button.setBackgroundColor(Color.parseColor("#5a595b"));

        }
    }




    //endregion

}
