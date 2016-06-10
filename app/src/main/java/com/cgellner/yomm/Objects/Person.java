package com.cgellner.yomm.Objects;

import com.cgellner.yomm.Database.Sql;

/**
 * Created by Carolin on 31.05.2016.
 */
public class Person {

    //region Fields

    private long ID;
    private String Name;
    private int Type;

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

    //endregion


    //region Methods

    /**
     *
     * @return
     */
    public String getSqlInsert(){

        return  "INSERT INTO " + Sql.NAME_TABLE_PERSONS + "( name, type) VALUES ( " +
                "'" + Name + "',"  + Type + ")";
    }


    //endregion
}
