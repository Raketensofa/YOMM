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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Category;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.Adapter.RecyclerViewAdapter;
import com.cgellner.yomm.R;

import java.util.ArrayList;
import java.util.Set;


/**
 * Created by Carolin on 31.05.2016.
 */

public class Fragment_NewTransaction extends Fragment {


    //region Fields

    private int layoutId;
    private int position;
    private RecyclerViewAdapter recyclerViewAdapter;
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
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        viewGroup = (ViewGroup)inflater.inflate(layoutId, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());

        if(position == 0){

            setValueTextChangeListener();

        }else
        if(position == 1){

            setDebtorChecklist();

        }else
        if(position == 2){

            setCreditorRadioButtonGroup();

        }else
        if(position == 3){

            setCategoryRadioButtonGroup();

        }else
        if(position == 4) {

            setDetailsTextChangeListener();
        }

        return viewGroup;
    }

    //endregion


    //region Private Methods

    /**
     *
     */
    private void setValueTextChangeListener(){

        eTValue = (EditText)viewGroup.findViewById(R.id.eTTransValue);

        if(eTValue != null) {

            eTValue.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {

                    Float value = new Float(eTValue.getText().toString());
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putFloat(GlobalVar.SpVarNameValue, value);
                    editor.commit();

                    checkInputData();

                }
            });
        }

    }

    /**
     *
     */
    private void setDetailsTextChangeListener(){

        eTDetails = (EditText)viewGroup.findViewById(R.id.eTTransDetails);

        if(eTDetails != null) {

            eTDetails.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    String value = eTDetails.getText().toString();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(GlobalVar.SpVarNameDetails, value);
                    editor.commit();
                    checkInputData();


                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

    }

    /**
     *
     */
    private void setDebtorChecklist() {


        //RecylerView aufbereiten
        recyclerView = (RecyclerView)viewGroup.findViewById(R.id.recyclerViewSecondLayout);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);

        //Adapter erstellen und der RecyclerView zuweisen
        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerViewAdapter.setPersonDataList(personList);
        recyclerViewAdapter.setPreferences(sharedPreferences);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    /**
     *
     */
    private void setCreditorRadioButtonGroup(){

        //RadioButtonGroup mit Personnamen befuellen
        RadioGroup groupPersons = (RadioGroup)viewGroup.findViewById(R.id.radioButtonGroupCreditor);
        for (Person person : personList) {

            RadioButton radioButton = new RadioButton(viewGroup.getContext());
            radioButton.setText(person.getName());
            radioButton.setTextSize(35);
            radioButton.setId(new Integer(String.valueOf(person.getID())));
            groupPersons.addView(radioButton);
        }

        //OnCheckedChangeListener der RadioButtonGroup
        groupPersons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                long personId = 0;
                for (Person person : personList){

                    if(person.getID() == new Long(checkedId)){

                        personId = person.getID();
                    }
                }


                //ID der Person (Creditor) in den SharedPreferences zwischenspeichern
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong(GlobalVar.SpVarNameCreditor, personId);
                editor.commit();
                checkInputData();
            }
        });


    }

    /**
     *
     */
    private void setCategoryRadioButtonGroup(){


        //RadioButtonGroup mit Kategorienamem befuellen
        RadioGroup groupCategories = (RadioGroup)viewGroup.findViewById(R.id.radioGroupCategory);
        for (Category category : categoryList) {

            RadioButton radioButton = new RadioButton(viewGroup.getContext());
            radioButton.setText(category.getName());
            radioButton.setTextSize(35);
            radioButton.setId(new Integer(String.valueOf(category.getID())));
            groupCategories.addView(radioButton);
        }

        //OnCheckedChangeListener der RadioButtonGroup
        groupCategories.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                long categoryId = 0;
                for (Category category : categoryList){

                    if(category.getID() == new Long(checkedId)){

                        categoryId = category.getID();
                    }
                }

                //ID der Kategorie in den SharedPreferences zwischenspeichern
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong(GlobalVar.SpVarNameCategory, categoryId);
                editor.commit();
                checkInputData();

            }
        });
    }


    /**
     *
     */
    private void checkInputData(){

        Set debtors = sharedPreferences.getStringSet(GlobalVar.SpVarNameDebtors, null);
        Float value = sharedPreferences.getFloat(GlobalVar.SpVarNameValue, 0);
        Long creditor = sharedPreferences.getLong(GlobalVar.SpVarNameCreditor, 0);
        Long category = sharedPreferences.getLong(GlobalVar.SpVarNameCategory, 0);

        Button button = (Button)getActivity().findViewById(R.id.button_newdata_viepager_save);


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



    //endregion

}
