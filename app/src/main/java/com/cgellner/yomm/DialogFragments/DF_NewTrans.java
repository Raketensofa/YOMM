package com.cgellner.yomm.DialogFragments;

import android.content.SharedPreferences;
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

import com.cgellner.yomm.Activities.MainActivity;
import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.MainCategory;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.Objects.RecyclerViewAdapter;
import com.cgellner.yomm.R;

import java.util.ArrayList;


/**
 * Created by Carolin on 31.05.2016.
 */

public class DF_NewTrans extends Fragment {

    private final String TAG = DF_NewTrans.class.getName();

    private int[] views = {R.layout.layout_new_first_whichvalue,
                    R.layout.layout_new_second_debtors,
                    R.layout.layout_new_third_creditor,
                    R.layout.layout_new_fourth_category,
                    R.layout.layout_new_fifth_object};

    private View view;

    private int layoutId;

    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;

    private EditText eTValue;
    private Button buttonSaveData;

    private SharedPreferences preferences;

    private MainActivity mainActivity;

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    private ArrayList<Person> personList;
    private ArrayList<MainCategory> mainCategoryList;

    private ArrayList<String> StrList;


    public MainActivity getContext() {
        return mainActivity;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }





    /**
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        ViewGroup rootView = (ViewGroup) inflater.inflate(getLayout(), container, false);
        view = rootView;

        preferences = PreferenceManager.getDefaultSharedPreferences(mainActivity);


        if(layoutId == 0){

            setTextChangeListener();
        }

        if(layoutId == 1){

            personList = GlobalVar.Database.getPersons();

            recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewSecondLayout);
            recyclerView.setHasFixedSize(true);

            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(llm);
            setPersonAdapter();

        }

        if(layoutId == 2){

            personList = GlobalVar.Database.getPersons();

            RadioGroup groupPersons = (RadioGroup)view.findViewById(R.id.radioButtonGroupCreditor);

            for (Person person : personList) {

                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setText(person.getName());
                radioButton.setTextSize(35);
                radioButton.setId(new Integer(String.valueOf(person.getID())));
                groupPersons.addView(radioButton);
            }

            groupPersons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    long personId = 0;
                    for (Person person : personList){

                        if(person.getID() == new Long(checkedId)){

                            personId = person.getID();
                        }
                    }


                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putLong(GlobalVar.SpVarNameCreditor, personId);
                    editor.commit();
                }
            });
        }


        if(layoutId == 3){

            mainCategoryList = GlobalVar.Database.getCategories();

            RadioGroup groupCategories = (RadioGroup)view.findViewById(R.id.radioGroupCategory);

            for (MainCategory category : mainCategoryList) {

                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setText(category.getName());
                radioButton.setTextSize(35);
                radioButton.setId(new Integer(String.valueOf(category.getID())));
                groupCategories.addView(radioButton);
            }

            groupCategories.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    long categoryId = 0;
                    for (MainCategory category : mainCategoryList){

                        if(category.getID() == new Long(checkedId)){

                            categoryId = category.getID();
                        }
                    }

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putLong(GlobalVar.SpVarNameCategory, categoryId);
                    editor.commit();
                }
            });
        }

        if(layoutId == 4) {

            setTextChangeListener();

            buttonSaveData = (Button) view.findViewById(R.id.buttonSaveTrans);
            buttonSaveData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mainActivity.readData();
                }
            });
        }

        return rootView;
    }

    /**
     *
     */
    private void setPersonAdapter(){

        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerViewAdapter.setPersonDataList(personList);
        recyclerViewAdapter.setPreferences(preferences);
        recyclerView.setAdapter(recyclerViewAdapter);

    }


    /**
     *
     * @return
     */
    private int getLayout(){

        int layout = 0;

        if(layoutId == 0){

            layout = views[0];

        }else if(layoutId == 1){

            layout = views[1];

        }else if(layoutId == 2){

            layout = views[2];

        }else if(layoutId == 3){

            layout = views[3];

        }else if(layoutId == 4){

            layout = views[4];
        }

        return layout;

    }


    /**
     *
     */
    private void setTextChangeListener(){

        if(layoutId == 0){

            eTValue = (EditText)view.findViewById(R.id.eTTransValue);

        }else if(layoutId == 4){

            eTValue = (EditText)view.findViewById(R.id.eTTransDetails);
        }

        if(eTValue != null) {

            eTValue.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {


                        String value = eTValue.getText().toString();
                        SharedPreferences.Editor editor = preferences.edit();

                        if (s.length() != 0) {

                            if(layoutId == 0){

                                editor.putString(GlobalVar.SpVarNameValue, value);

                            }else if(layoutId == 4){

                                editor.putString(GlobalVar.SpVarNameDetails, value);
                            }

                        editor.commit();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }


}
