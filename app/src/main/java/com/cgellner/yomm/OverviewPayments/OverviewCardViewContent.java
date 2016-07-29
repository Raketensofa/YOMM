package com.cgellner.yomm.OverviewPayments;

import java.util.ArrayList;


/**
 * Die Klasse repraesentiert ein Objekt, welches die Daten fuer eine CardView in der Uebersichtsansicht beinhaltet.
 * @since 28.06.2016
 * @author Carolin Gellner
 */
public class OverviewCardViewContent {


    //region Fields

    private long ID;
    private String Name;
    private ArrayList<String[]> datalist;

    //endregion


    //region Getter & Setter

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ArrayList<String[]> getDatalist() {
        return datalist;
    }

    public void setDatalist(ArrayList<String[]> datalist) {
        this.datalist = datalist;
    }

    //endregion
}
