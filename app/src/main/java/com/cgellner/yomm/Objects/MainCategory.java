package com.cgellner.yomm.Objects;

import com.cgellner.yomm.Database.Sql;

/**
 * Created by Carolin on 31.05.2016.
 */
public class MainCategory {


    //region Fields

    private long ID;
    private String Name;

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


    //endregion


    //region Methods


    @Override
    public String toString() {
        return "MainCategory{" +
                "ID=" + ID +
                ", Name='" + Name +
                '}';
    }


    /**
     *
     * @return
     */
    public String getSqlInsert(){

        return  "INSERT INTO " + Sql.NAME_TABLE_MAINCATEGORIES + " ( "+  Sql.NAME_COLUMN_NAME + " ) VALUES ('" + Name + "')";
    }

    //endregion


}
