package com.cgellner.yomm.Objects;

import java.util.ArrayList;


/**
 * Created by Carolin on 28-Jun-16.
 */
public class Overview_Person {

    private long ID;
    private String Name;
    //private HashMap<String, Double> Data;

    private ArrayList<String[]> datalist;


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
}
