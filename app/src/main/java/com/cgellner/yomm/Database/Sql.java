package com.cgellner.yomm.Database;

/**
 * Created by Carolin on 31.05.2016.
 */
public abstract class Sql {


    //region Datatypes

    public static final String DATATYPE_TEXT = "TEXT";
    public static final String DATATYPE_INTEGER = "INTEGER";
    public static final String DATATYPE_DECIMAL = "DECIMAL";

    public static final String PRIMARYKEY_AUTO = "PRIMARY KEY AUTOINCREMENT";
    public static final String NOTNULL = "NOT NULL";

    //endregion



    //region Database Table Names

    public static final String NAME_TABLE_TRANSACTIONS = "transactions";
    public static final String NAME_TABLE_MAINCATEGORIES = "maincategories";
    public static final String NAME_TABLE_SUBCATEGORIES = "subcategories";
    public static final String NAME_TABLE_PERSONS = "persons";
    public static final String NAME_TABLE_ACCOUNTS = "accounts";

    //endregion


    //region Database Column Names

    public static final String NAME_COLUMN_ID = "id";
    public static final String NAME_COLUMN_PAYINGPERSONS = "paying_persons";
    public static final String NAME_COLUMN_FALLSATPERSONS = "falls_at_persons";
    public static final String NAME_COLUMN_TYPE = "type";
    public static final String NAME_COLUMN_DATETIME = "datetime";
    public static final String NAME_COLUMN_DATE_MATURITY = "date_maturity";
    public static final String NAME_COLUMN_VALUE = "value";
    public static final String NAME_COLUMN_MAINCATEGORY = "maincategory";
    public static final String NAME_COLUMN_SUBCATEGORY = "subcategory";
    public static final String NAME_COLUMN_DESCRIPTION = "descritpion";
    public static final String NAME_COLUMN_ACCOUNT = "account";
    public static final String NAME_COLUMN_NAME = "name";
    public static final String NAME_COLUMN_STARTVALUE = "startvalue";
    public static final String NAME_COLUMN_MAINCATEGORY_ID = "maincategory_id";

    //endregion


    //region CreateTables

    public static final String CREATE_TABLE_TRANSACTIONS = "CREATE TABLE " +
                NAME_TABLE_TRANSACTIONS + "(" +
                NAME_COLUMN_ID + " " + DATATYPE_INTEGER + " " + PRIMARYKEY_AUTO + "," +
                NAME_COLUMN_TYPE + " " + DATATYPE_INTEGER + " " + NOTNULL + ", " +
                NAME_COLUMN_DATETIME + " " + DATATYPE_TEXT + " " + NOTNULL + ", " +
                NAME_COLUMN_DATE_MATURITY + " " + DATATYPE_TEXT + " " + NOTNULL + ", " +
                NAME_COLUMN_ACCOUNT + " " + DATATYPE_INTEGER + " " + NOTNULL + ", " +
                NAME_COLUMN_VALUE + " " + DATATYPE_DECIMAL + " " + NOTNULL + ", " +
                NAME_COLUMN_PAYINGPERSONS + " " + DATATYPE_TEXT + " " + NOTNULL + ", " +
                NAME_COLUMN_FALLSATPERSONS + " " + DATATYPE_TEXT + " " + NOTNULL + ", " +
                NAME_COLUMN_MAINCATEGORY + " " + DATATYPE_TEXT + ", " +
                NAME_COLUMN_SUBCATEGORY + " " + DATATYPE_TEXT + ", " +
                NAME_COLUMN_DESCRIPTION + " " + DATATYPE_TEXT + " )";


    public static final String CREATE_TABLE_PERSONS = "CREATE TABLE " +
            NAME_TABLE_PERSONS + "(" +
            NAME_COLUMN_ID + " " + DATATYPE_INTEGER + " " + PRIMARYKEY_AUTO + "," +
            NAME_COLUMN_NAME + " " + DATATYPE_TEXT + " " + NOTNULL +  ", " +
            NAME_COLUMN_TYPE + " " + DATATYPE_INTEGER + " " + NOTNULL + " )";


    public static final String CREATE_TABLE_ACCOUNTS = "CREATE TABLE " +
            NAME_TABLE_ACCOUNTS + "(" +
            NAME_COLUMN_ID + " " + DATATYPE_INTEGER + " " + PRIMARYKEY_AUTO + "," +
            NAME_COLUMN_NAME + " " + DATATYPE_TEXT + " " + NOTNULL +  ", " +
            NAME_COLUMN_TYPE + " " + DATATYPE_INTEGER + " " + NOTNULL + ", " +
            NAME_COLUMN_STARTVALUE + " " + DATATYPE_DECIMAL + " " + NOTNULL +
            ")";

    public static final String CREATE_TABLE_MAINCATEGORIES = "CREATE TABLE " +
            NAME_TABLE_MAINCATEGORIES + "(" +
            NAME_COLUMN_ID + " " + DATATYPE_INTEGER + " " + PRIMARYKEY_AUTO + "," +
            NAME_COLUMN_NAME + " " + DATATYPE_TEXT + " " + NOTNULL +  " )";


    public static final String CREATE_TABLE_SUBCATEGORIES = "CREATE TABLE " +
            NAME_TABLE_SUBCATEGORIES + "(" +
            NAME_COLUMN_ID + " " + DATATYPE_INTEGER + " " + PRIMARYKEY_AUTO + "," +
            NAME_COLUMN_NAME + " " + DATATYPE_TEXT + " " + NOTNULL +  ", " +
            NAME_COLUMN_MAINCATEGORY_ID + " " + DATATYPE_INTEGER + " " + NOTNULL + " )";


    //endregion


}
