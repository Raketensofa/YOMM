package com.cgellner.yomm.Objects;

import com.cgellner.yomm.Database.Sql;

/**
 * Die Klasse repaesentiert eine Kategorie.
 * @since 31.05.2016
 * @author Carolin Gellner
 */
public class Category {


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


    @Override
    public String toString() {
        return "Category{" +
                "ID=" + ID +
                ", Name='" + Name +
                '}';
    }


    /**
     *
     * @return
     */
    public String getSqlInsert(){

        return  "INSERT INTO " + Sql.NAME_TABLE_CATEGORIES + " ( "+  Sql.NAME_COLUMN_NAME + " ) VALUES ('" + Name + "')";
    }

    //endregion

}
