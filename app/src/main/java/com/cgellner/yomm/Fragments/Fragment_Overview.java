package com.cgellner.yomm.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cgellner.yomm.Adapter_ViewHolder.RecyclerViewAdapter_OverviewCards;
import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.R;

import java.security.Key;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;


public class Fragment_Overview extends Fragment {

    private RecyclerView recyclerView;


    public Fragment_Overview() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.layout_fragment_overview, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView_overview);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);





        setRecyclerViewItems();

        return view;
    }


    private void setRecyclerViewItems(){

        //Daten aufbereiten .........

        Hashtable results = getDataFromDb();
        Hashtable preparedList = prepareData(results);

        RecyclerViewAdapter_OverviewCards adapter = new RecyclerViewAdapter_OverviewCards();
        recyclerView.setAdapter(adapter);


    }


    private Hashtable<String, Hashtable> prepareData(Hashtable results){

        Hashtable<String, Hashtable> data = new Hashtable<>();

        Enumeration keys = results.keys();

        while(keys.hasMoreElements()){

            Object creditorId = keys.nextElement();

            Object debtor = results.get(creditorId);


        }

        return data;
    }


    /**
     * Ermittelt fuer jede Person welchen Personen in welcher Hoehe Geld "geliehen" bzw. fuer diese Personen etwas bezahlt wurde
     * @return
     */
    private HashMap<Long, HashMap > getDataFromDb(){

        ArrayList<Person> persons = GlobalVar.Database.getPersons();

        Hashtable<Long, Hashtable> valuesPerCreditor = new Hashtable<>();

        if(persons != null){

            for (Person creditor : persons) {

                Hashtable<Long, Double> valuePerDebtor = new Hashtable<>();

                for (Person debtor : persons){

                    if(debtor.getID() != creditor.getID()) {

                        GlobalVar.Database.open();
                        double value = GlobalVar.Database.getTransactionSum(creditor.getID(), debtor.getID());
                        GlobalVar.Database.close();

                        valuePerDebtor.put(debtor.getID(), value);
                    }

                }

                valuesPerCreditor.put(creditor.getID(), valuePerDebtor);
            }
        }
    }


}
