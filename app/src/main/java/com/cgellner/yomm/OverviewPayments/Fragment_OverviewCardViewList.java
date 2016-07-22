package com.cgellner.yomm.OverviewPayments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.R;

import java.util.ArrayList;


public class Fragment_OverviewCardViewList extends Fragment {

    private RecyclerView recyclerView;


    public Fragment_OverviewCardViewList() {

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

        ArrayList<OverviewCardViewContent> data = getCalculatedData();

        if(data != null){

            RecyclerViewAdapter_OverviewCardView adapter = new RecyclerViewAdapter_OverviewCardView(data);
            recyclerView.setAdapter(adapter);
        }

    }



    /**
     * Ermittelt fuer jede Person welchen Personen in welcher Hoehe Geld "geliehen" bzw. fuer diese Personen etwas bezahlt wurde
     *
     * @return
     */
    private ArrayList<OverviewCardViewContent> getCalculatedData() {

        ArrayList<Person> persons = new ArrayList<>();
        persons = GlobalVar.Database.getPersons();

        ArrayList<OverviewCardViewContent> data = new ArrayList<>();

        if (persons != null) {

            for (Person person : persons) {

                OverviewCardViewContent overview_cardViewContent = new OverviewCardViewContent();
                overview_cardViewContent.setID(person.getID());
                overview_cardViewContent.setName(person.getName());

                ArrayList<String[]> valueList = new ArrayList<>();

                for (Person second_person : persons) {

                    if(person.getID() != second_person.getID()) {

                        Double money = getCalculatedMoneyValue(person.getID(), second_person.getID());

                        valueList.add(new String[]{String.valueOf(second_person.getID()), second_person.getName(), String.format( "%.2f", money )});

                    }
                }

                overview_cardViewContent.setDatalist(valueList);
                data.add(overview_cardViewContent);
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

        double valuePersonOne = GlobalVar.Database.getOpenPaymentSum(personId, secondPersonId);
        double valuePersoTwo = GlobalVar.Database.getOpenPaymentSum(secondPersonId, personId);

        if(valuePersonOne != -1 && valuePersoTwo != -1){

            money = valuePersonOne - valuePersoTwo;
        }

        return money;
    }





}
