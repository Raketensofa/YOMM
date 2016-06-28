package com.cgellner.yomm.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cgellner.yomm.Adapter_ViewHolder.RecyclerViewAdapter_OverviewCards;
import com.cgellner.yomm.Database.Database;
import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Overview_Person;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.R;

import java.lang.reflect.Array;
import java.security.Key;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

import javax.microedition.khronos.opengles.GL;


public class Fragment_Overview extends Fragment {

    private RecyclerView recyclerView;


    public Fragment_Overview() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_fragment_overview, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_overview);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        setRecyclerViewItems();

        return view;
    }


    private void setRecyclerViewItems() {

        ArrayList<Overview_Person> data = getCalculatedData();

        Log.v("ArrayListPersons.size", String.valueOf(data.size()));

        RecyclerViewAdapter_OverviewCards adapter = new RecyclerViewAdapter_OverviewCards();

        if(data != null){

            Log.v("ArrayListPersons.size", "not null");

            adapter.setDataList(data);
            recyclerView.setAdapter(adapter);
        }

    }



    /**
     * Ermittelt fuer jede Person welchen Personen in welcher Hoehe Geld "geliehen" bzw. fuer diese Personen etwas bezahlt wurde
     *
     * @return
     */
    private ArrayList<Overview_Person> getCalculatedData() {

        ArrayList<Person> persons = new ArrayList<>();
        persons = GlobalVar.Database.getPersons();

        ArrayList<Overview_Person> data = new ArrayList<>();

        if (persons != null) {

            for (Person person : persons) {

                Overview_Person overview_person = new Overview_Person();
                overview_person.setID(person.getID());
                overview_person.setName(person.getName());

                HashMap<String, Double> valueList = new HashMap<>();

                for (Person second_person : persons) {

                    Double money = getCalculatedMoneyValue(person.getID(), second_person.getID());

                    Pair<String, Double> dataset = new Pair<>(second_person.getName(), money);
                    valueList.put(dataset.first, dataset.second);
                }

                overview_person.setData(valueList);
                data.add(overview_person);
            }
        }

        return data;
    }

    /**
     *
     * @param personId
     * @param secondPersonId
     * @return
     */
    private Double getCalculatedMoneyValue(long personId, long secondPersonId){

        double money = -1d;

        double valuePersonOne = GlobalVar.Database.getDebtSum(personId, secondPersonId);
        double valuePersoTwo = GlobalVar.Database.getDebtSum(secondPersonId, personId);

        if(valuePersonOne != -1 && valuePersoTwo != -1){

            money = valuePersonOne - valuePersoTwo;
        }

        return money;
    }



}
