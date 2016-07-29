package com.cgellner.yomm.OverviewPayments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.R;

import java.util.ArrayList;


/**
 * Die Klasse repraesentiert die Uebersichtsansicht, welche eine Liste mit Karten (CardViews) darstellt,
 * die pro angelegter Person dargestellt werden und eine Auflistung der Zahlungsdifferenzen je Person beinhaltet.
 */
public class Fragment_OverviewCardViewList extends Fragment {


    //region Fields

        private RecyclerView recyclerView;

    //endregion


    //region Public Methods


    /**
     * Die Methode erstellt die Uebersichtsansicht.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
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

    //endregion


    //region Private Methods

    /**
     * Die Methode bereitet die RecyclerView-Ansicht auf.
     */
    private void setRecyclerViewItems() {

        //Daten ermitteln
        ArrayList<OverviewCardViewContent> data = getCalculatedData();

        if(data != null){

            //Daten der RecyclerView uebergeben
            RecyclerViewAdapter_OverviewCardView adapter = new RecyclerViewAdapter_OverviewCardView(data);
            recyclerView.setAdapter(adapter);
        }

    }


    /**
     * Ermittelt fuer jede Person welchen Personen in welcher Hoehe Geld "geliehen" bzw. fuer diese Personen etwas bezahlt wurde
     *
     * @return Liste mit den Zahlungsdifferezen pro Person
     */
    private ArrayList<OverviewCardViewContent> getCalculatedData() {

        //Personen aus Datenbank abfragen
        ArrayList<Person> persons = GlobalVar.Database.getPersons();

        //Inhalt der CardView zu jeder Person erstellen
        ArrayList<OverviewCardViewContent> data = new ArrayList<>();

        if (persons != null) {

            for (Person person : persons) {

                OverviewCardViewContent overview_cardViewContent = new OverviewCardViewContent();
                overview_cardViewContent.setID(person.getID());
                overview_cardViewContent.setName(person.getName());

                ArrayList<String[]> valueList = new ArrayList<>();

                for (Person second_person : persons) {

                    if(person.getID() != second_person.getID()) {

                        //Zahlungsdifferenz ermitteln
                        Double money = GlobalVar.getPaymentDifference(person.getID(), second_person.getID());

                        valueList.add(new String[]{String.valueOf(second_person.getID()), second_person.getName(), GlobalVar.formatMoney(String.valueOf(money))});

                    }
                }

                overview_cardViewContent.setDatalist(valueList);
                data.add(overview_cardViewContent);
            }
        }

        return data;
    }

    //endregion
}
