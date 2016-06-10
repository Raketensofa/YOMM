package com.cgellner.yomm.Objects;

import com.cgellner.yomm.Database.Sql;

/**
 * Created by Carolin on 31.05.2016.
 */
public class Account {

    //region Fields

    private long ID;
    private String Name;
    private int Type;
    private double StartValue;

    //endregion


    //region Properties

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

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public double getStartValue() {
        return StartValue;
    }

    public void setStartValue(double startValue) {
        StartValue = startValue;
    }

    //endregion


    //region Methods

    @Override
    public String toString() {
        return "Account{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                ", Type=" + Type +
                ", StartValue=" + StartValue +
                '}';
    }


    /**
     *
     * @return
     */
    public String getSqlInsert(){

        return  "INSERT INTO " + Sql.NAME_TABLE_ACCOUNTS +
                "(" + Sql.NAME_COLUMN_NAME + ", " + Sql.NAME_COLUMN_TYPE + ", " + Sql.NAME_COLUMN_STARTVALUE + ") VALUES( '" + Name + "', " + Type + ", " + StartValue + ")";
    }

    //endregion
}
