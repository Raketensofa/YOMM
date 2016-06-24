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
    public static final String NAME_TABLE_CATEGORIES = "categories";
    public static final String NAME_TABLE_PERSONS = "persons";

    //endregion


    //region Database Column Names

    public static final String NAME_COLUMN_ID = "id";
    public static final String NAME_COLUMN_CREDITOR = "creditorId";
    public static final String NAME_COLUMN_DEBTOR = "debtorId";
    public static final String NAME_COLUMN_TYPE = "type";
    public static final String NAME_COLUMN_VALUE = "value";
    public static final String NAME_COLUMN_CATEGORY = "categoryId";
    public static final String NAME_COLUMN_DETAILS = "details";
    public static final String NAME_COLUMN_NAME = "name";
    public static final String NAME_COLUMN_DATE = "date";
    public static final String NAME_COLUMN_TIME = "time";

    //endregion


    //region CreateTables

    public static final String CREATE_TABLE_TRANSACTIONS = "CREATE TABLE " +
                NAME_TABLE_TRANSACTIONS + "(" +
                NAME_COLUMN_ID + " " + DATATYPE_INTEGER + " " + PRIMARYKEY_AUTO + "," +
                NAME_COLUMN_TYPE + " " + DATATYPE_INTEGER + " " + NOTNULL + ", " +
                NAME_COLUMN_DATE + " " + DATATYPE_TEXT + " " + NOTNULL + ", " +
                NAME_COLUMN_TIME + " " + DATATYPE_TEXT + " " + NOTNULL + ", " +
                NAME_COLUMN_CREDITOR + " " + DATATYPE_INTEGER + " " + NOTNULL + ", " +
                NAME_COLUMN_DEBTOR + " " + DATATYPE_INTEGER + " " + NOTNULL + ", " +
                NAME_COLUMN_VALUE + " " + DATATYPE_DECIMAL + " " + NOTNULL + ", " +
                NAME_COLUMN_CATEGORY + " " + DATATYPE_INTEGER + ", " +
                NAME_COLUMN_DETAILS + " " + DATATYPE_TEXT + " )";


    public static final String CREATE_TABLE_PERSONS = "CREATE TABLE " +
            NAME_TABLE_PERSONS + "(" +
            NAME_COLUMN_ID + " " + DATATYPE_INTEGER + " " + PRIMARYKEY_AUTO + "," +
            NAME_COLUMN_NAME + " " + DATATYPE_TEXT + " " + NOTNULL +  " )";



    public static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE " +
            NAME_TABLE_CATEGORIES + "(" +
            NAME_COLUMN_ID + " " + DATATYPE_INTEGER + " " + PRIMARYKEY_AUTO + "," +
            NAME_COLUMN_NAME + " " + DATATYPE_TEXT + " " + NOTNULL +  " )";




    //endregion


}
