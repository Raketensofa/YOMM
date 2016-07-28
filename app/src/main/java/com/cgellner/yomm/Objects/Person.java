package com.cgellner.yomm.Objects;

import com.cgellner.yomm.Database.Sql;

/**
 * Die Klasse reprasentiert eine Person.
 * @since 31.05.2016
 * @author Carolin Gellner
 */
public class Person {

    //region Fields

    private long ID;
    private String Name;

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

    //endregion


    //region Methods

    /**
     *
     * @return
     */
    public String getSqlInsert(){

        return  "INSERT INTO " + Sql.NAME_TABLE_PERSONS + "( name) VALUES ( " +
                "'" + Name + "')";
    }


    //endregion
}
