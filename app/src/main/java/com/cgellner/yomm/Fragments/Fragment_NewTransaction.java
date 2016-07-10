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

    long debtorid;
    long creditorid;
    private int option;

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

    public void setOption(int option) {

        this.option = option;
    }

    public void setDebtorid(long debtorid) {
        this.debtorid = debtorid;
    }

    public void setCreditorid(long creditorid) {
        this.creditorid = creditorid;
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

        //SharedPreferences holen
        //Dort werden die gewaehlten Daten zwischengespeichert
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());

        if(option == 0){

            setPageForCleaningDebts(position);

        }else if(option == 1){

            setPageForAddNewTransaction(position);
        }

        return viewGroup;
    }

    //endregion


    //region Private Methods

    private void setPageForCleaningDebts(int position){

        if(position == 0){

            setCleanDebtsRadioButtonFirst();

        }else
        if(position == 1){

            setCleanDebtsRadioButtonSecond();

        }else
        if(position == 2){


        }else
        if(position == 3){


        }
    }


    private void setPageForAddNewTransaction(int position){

        //Layoutelemente wie z.B. Listen der  Layouts des ViewPagers mit Daten befuellen
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
    }








    /**
     * Die Methode uebergibt den TextChangedListener dem EditText des Betrags.
     * Die eingegeben Details im EditText-Feld werden ausgelesen und im Zwischenspeicher der SharedPreferences hinterlegt.
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

                    Float value = 0f;

                    if(eTValue.getText().toString().length() > 0){

                        value = new Float(eTValue.getText().toString());

                    }

                    //Betrag in den SharedPreferences zwischenspeichern
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putFloat(GlobalVar.SpVarNameValue, value);
                    editor.commit();


                    //SharedPreferences auf Vollstaendigkeit ueberpruefen, um ggf. den Speicher-Button freigeben zu koennen
                    checkInputDataNewTrans();

                }
            });
        }

    }



    /**
     * Die Methode uebergibt den TextChangedListener dem EditText-Feld der Details.
     * Die eingegeben Details im EditText-Feld werden ausgelesen und im Zwischenspeicher der SharedPreferences hinterlegt.
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

                    //Details aus dem EditText-Feld auslesen und in den SharedPreferences zwischenspeichern
                    String value = eTDetails.getText().toString();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(GlobalVar.SpVarNameDetails, value);
                    editor.commit();

                    //SharedPreferences auf Vollstaendigkeit pruefen um ggf. den Speicher-Button freigeben zu koennen
                    checkInputDataNewTrans();

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

    }

    /**
     * Die Methode erstellt mithilfe einer RecyclerView und eines RecyclerViewAdapters eine Liste mit allen Personen,
     * welche in der Datenbank hinterlegt sind.
     * Die gewaehlte(n) Person(en) werden in den Zwischenspeicher der SharedPreferences hinterlegt.
     */
    private void setDebtorChecklist() {


        //RecylerView aufbereiten
        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recyclerViewSecondLayout);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);


        //Adapter erstellen und der RecyclerView zuweisen
        recyclerViewAdapter = new RecyclerViewAdapter_CheckboxList();
        recyclerViewAdapter.setPersonDataList(personList);
        recyclerViewAdapter.setSharedPreferences(sharedPreferences);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    /**
     *  Die Methode legt eine RadioButtonGroup mit allen Personen an, die in der Datenabnk hinterlegt sind.
     * Die gewaehlte(n) Person(en) wird/werden in den Zwischenspeicher der SharedPreferences hinterlegt.
     */
    private void setCreditorRadioButtonGroup(){

        //RadioButtonGroup mit Personnamen befuellen
        RadioGroup groupPersons = (RadioGroup)viewGroup.findViewById(R.id.radioButtonGroupCreditor);


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


                //SharedPreferences auf Vollstaendigkeit pruefen, um ggf. Speicher-Button freigeben zu koennen
                checkInputDataNewTrans();
            }
        });


    }

    /**
     * Die Methode legt eine RadioButtonGroup mit allen Kategorien an, die in der Datenabnk hinterlegt sind.
     * Die gewaehlte Kategorie wird in den Zwischenspeicher der SharedPreferences hinterlegt.
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


                //SharedPreferences auf Vollstaendigkeit pruefen um ggf. Speicher-Button freigeben zu koennen
                checkInputDataNewTrans();

            }
        });
    }

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


    private void setCleanDebtsRadioButtonFirst(){


        RadioGroup groupPersons = (RadioGroup)viewGroup.findViewById(R.id.layout_viewpager_cleardebts_first_radioButtonGroup);

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
                editor.putLong(GlobalVar.SpVarNameClearDebitor, personId);
                editor.putLong(GlobalVar.SpVarNameClearCreditor, 0);
                editor.commit();



                //new Transaction
                editor.putLong(GlobalVar.SpVarNameCreditor, 0);

                //SharedPreferences auf Vollstaendigkeit pruefen, um ggf. Speicher-Button freigeben zu koennen
                checkInputDataCleanDebts();

            }
        });

    }

    private void setCleanDebtsRadioButtonSecond(){

            RadioGroup groupPersons = (RadioGroup)viewGroup.findViewById(R.id.layout_viewpager_cleardebts_second_radioButtonGroup);


            //OnCheckedChangeListener der RadioButtonGroup
            groupPersons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    long personId = group.getCheckedRadioButtonId();

                    //ID der Person (Creditor) in den SharedPreferences zwischenspeichern
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong(GlobalVar.SpVarNameClearCreditor, personId);
                    editor.commit();


                    //SharedPreferences auf Vollstaendigkeit pruefen, um ggf. Speicher-Button freigeben zu koennen
                    checkInputDataCleanDebts();

                }
            });
    }


    //endregion

}
