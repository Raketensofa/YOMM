package com.cgellner.yomm.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Category;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.Objects.Transaction;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL;


/**
 * Created by Carolin on 31.05.2016.
 */
public class Database extends SQLiteOpenHelper {


    //region Fields

    private final String TAG = Database.class.getName();

    private static final String DATABASE_NAME = "Yomm_Database.db";
    private static final int DATABASE_VERSION = 5;

    private SQLiteDatabase Database;
    private Context context;

    //endregion


    //region Constructor

    public Database(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    //endregion


    //region Properties

    public SQLiteDatabase getDatabase() {
        return Database;
    }

    public void setDatabase(SQLiteDatabase database) {
        Database = database;
    }

    //endregion


    //region Public Methods
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try{

            Database = sqLiteDatabase;
            Database.execSQL(Sql.CREATE_TABLE_TRANSACTIONS);
            Database.execSQL(Sql.CREATE_TABLE_PERSONS);
            Database.execSQL(Sql.CREATE_TABLE_CATEGORIES);

            Log.d(TAG, "Datenbank wurde erstellt.");


        }catch (Exception ex){

            Log.e(TAG, ex.getMessage());
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        Log.d(TAG, "Upgrade der Datenbank");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Sql.NAME_TABLE_CATEGORIES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Sql.NAME_TABLE_PERSONS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Sql.NAME_TABLE_TRANSACTIONS);
       this.onCreate(sqLiteDatabase);

    }


    /**
     *
     */
    public void open(){

        Database = this.getWritableDatabase();
        Log.v(TAG, "Datenbank wurde geoeffnet");
    }


    /**
     *
     */
    public void close(){

        if(Database.isOpen()) {

            Database.close();
            Log.v(TAG, "Datenbank wurde geschlossen.");
        }
    }


    /**
     *
     * @param person
     */
    public void insertPerson(Person person){

        open();
        insertData(person.getSqlInsert());
        Log.v("Person", person.toString());
        close();
    }


    /**
     *
     * @param category
     */
    public void insertCategory(Category category){

        open();
        insertData(category.getSqlInsert());
        Log.v("Category", category.toString());
        close();
    }


    /**
     *
     * @param transaction
     */
    public void insertTransaction(Transaction transaction){

        open();
        insertData(transaction.getSqlInsert());
        Log.v("Transaction", transaction.toString());
        close();
    }



    /**
     *
     * @param object
     */
    public void update(Object object){

        open();


        close();

    }


    /**
     *
     * @param object
     */
    public void delete(Object object){

        open();



        close();

    }

    public ArrayList<Transaction> getOpenTransactions(long creditorId, long debtorId){

        ArrayList<Transaction> transactions = null;

        open();

        try {

                    Cursor cursor = Database.query(Sql.NAME_TABLE_TRANSACTIONS,
                    null,
                    Sql.NAME_COLUMN_TYPE + " = 0 AND "
                            + Sql.NAME_COLUMN_CREDITOR + "=" + creditorId + " AND "
                            + Sql.NAME_COLUMN_DEBTOR + "=" + debtorId,
                    null, null, null,null);


            if (cursor != null) {

                transactions = new ArrayList<>();

                if (cursor.moveToFirst()) {

                    do {

                        Transaction trans = new Transaction();

                        trans.setID(cursor.getLong(cursor.getColumnIndex(Sql.NAME_COLUMN_ID)));
                        trans.setType(cursor.getInt(cursor.getColumnIndex(Sql.NAME_COLUMN_TYPE)));
                        trans.setCategory(cursor.getLong(cursor.getColumnIndex(Sql.NAME_COLUMN_CATEGORY)));
                        trans.setValue(cursor.getFloat(cursor.getColumnIndex(Sql.NAME_COLUMN_VALUE)));
                        trans.setDate(cursor.getString(cursor.getColumnIndex(Sql.NAME_COLUMN_DATE)));
                        trans.setDebtorId(cursor.getLong(cursor.getColumnIndex(Sql.NAME_COLUMN_DEBTOR)));
                        trans.setCreditorId(cursor.getLong(cursor.getColumnIndex(Sql.NAME_COLUMN_CREDITOR)));

                        transactions.add(trans);
                        Log.d("Trans" , trans.toString());

                    } while (cursor.moveToNext());
                }
            }

            cursor.close();

        } catch (Exception ex) {


            return null;

        }finally {

            close();
        }

        return transactions;

    }


    public double getDebtSum(long creditorId, long debtorId){

        double value = 0d;

        open();

        try {

            Cursor cursor = Database.query(Sql.NAME_TABLE_TRANSACTIONS,
                    new String[]{Sql.NAME_COLUMN_VALUE},
                    Sql.NAME_COLUMN_TYPE + " = 0 AND "
                            + Sql.NAME_COLUMN_CREDITOR + "=" + creditorId + " AND "
                            + Sql.NAME_COLUMN_DEBTOR + "=" + debtorId,
                    null, null, null,null);



            if (cursor != null) {

                if (cursor.moveToFirst()) {

                    do {

                        value += cursor.getFloat(cursor.getColumnIndex(Sql.NAME_COLUMN_VALUE));

                    } while (cursor.moveToNext());
                }
            }

            cursor.close();

        } catch (Exception ex) {


            return -1d;

        }finally {

            close();
        }

        return value;

    }



    public List<Transaction> getTransactions(long mainpersonId, long secondPersonId){

        List<Transaction> list = new ArrayList<>();


        open();


        try {

            Cursor cursor = null;

            if(mainpersonId != -1 && secondPersonId == -1){

                 cursor = Database.query(Sql.NAME_TABLE_TRANSACTIONS,
                        null,
                        Sql.NAME_COLUMN_CREDITOR + "=" + mainpersonId + " OR " + Sql.NAME_COLUMN_DEBTOR + "=" + mainpersonId,
                        null, null, null,null);


            }else if(mainpersonId != -1 && secondPersonId != -1){

                 cursor = Database.query(Sql.NAME_TABLE_TRANSACTIONS,
                        null,
                        "(" + Sql.NAME_COLUMN_CREDITOR + "=" + mainpersonId + " AND "
                                + Sql.NAME_COLUMN_DEBTOR + "=" + secondPersonId + ") OR (" + Sql.NAME_COLUMN_CREDITOR + "=" + secondPersonId + " AND "
                                + Sql.NAME_COLUMN_DEBTOR + "=" + mainpersonId + ")",
                        null, null, null, null);

            }

            if (cursor != null) {

                if (cursor.moveToFirst()) {

                    do {

                        Transaction trans = new Transaction();

                        trans.setID(cursor.getLong(cursor.getColumnIndex(Sql.NAME_COLUMN_ID)));
                        trans.setType(cursor.getInt((cursor.getColumnIndex(Sql.NAME_COLUMN_TYPE))));
                        trans.setDate(cursor.getString((cursor.getColumnIndex(Sql.NAME_COLUMN_DATE))));
                        trans.setTime(cursor.getString((cursor.getColumnIndex(Sql.NAME_COLUMN_TIME))));
                        trans.setValue(cursor.getFloat((cursor.getColumnIndex(Sql.NAME_COLUMN_VALUE))));
                        trans.setCreditorId(cursor.getLong((cursor.getColumnIndex(Sql.NAME_COLUMN_CREDITOR))));
                        trans.setDebtorId(cursor.getLong((cursor.getColumnIndex(Sql.NAME_COLUMN_DEBTOR))));
                        trans.setCategory(cursor.getLong((cursor.getColumnIndex(Sql.NAME_COLUMN_CATEGORY))));
                        trans.setDetails(cursor.getString((cursor.getColumnIndex(Sql.NAME_COLUMN_DETAILS))));

                        list.add(trans);

                    } while (cursor.moveToNext());

                }
            }

            cursor.close();

        } catch (Exception ex) {


            return null;

        }finally {

            close();
        }

        return list;

    }



    /**
     *
     * @return
     */
    public ArrayList<Person> getPersons(){

        ArrayList<Person> list = new ArrayList<>();

        String[] columns = {Sql.NAME_COLUMN_ID, Sql.NAME_COLUMN_NAME};

        open();

        if(Database.isOpen()) {

            try {

                Cursor cursor = Database.query(Sql.NAME_TABLE_PERSONS, columns, null, null, null, null, null);

                if (cursor != null) {

                    if (cursor.moveToFirst()) {
                        do {

                            Person person = new Person();

                            person.setID(cursor.getLong(cursor.getColumnIndex(columns[0])));
                            person.setName(cursor.getString(cursor.getColumnIndex(columns[1])));

                            list.add(person);

                            Log.d("PERSON", person.toString());

                        } while (cursor.moveToNext());
                    }
                }

                cursor.close();

            } catch (Exception ex) {

                Log.e("Database.getPersons()", ex.getMessage());
                return null;

            }finally {

                close();
            }
        }

        return list;

    }


    public String getCategoryName(long categoryId){

        String category = null;

        open();

        if(Database.isOpen()) {

            try {

                Cursor cursor = Database.query(Sql.NAME_TABLE_CATEGORIES, new String[]{Sql.NAME_COLUMN_NAME}, Sql.NAME_COLUMN_ID + "=" + categoryId, null, null, null, null);

                if (cursor != null) {

                    if (cursor.moveToFirst()) {
                        do {

                            category = cursor.getString(cursor.getColumnIndex(Sql.NAME_COLUMN_NAME));

                        } while (cursor.moveToNext());
                    }
                }

                cursor.close();

            } catch (Exception ex) {


                return null;

            } finally {

                close();
            }

        }

        return category;
    }


    public String getPersonName(long personId){

        String person = null;

        open();

        if(Database.isOpen()) {

            try {

                Cursor cursor = Database.query(Sql.NAME_TABLE_PERSONS, new String[]{Sql.NAME_COLUMN_NAME}, Sql.NAME_COLUMN_ID + "=" + personId, null, null, null, null);

                if (cursor != null) {

                    if (cursor.moveToFirst()) {
                        do {

                            person = cursor.getString(cursor.getColumnIndex(Sql.NAME_COLUMN_NAME));

                        } while (cursor.moveToNext());
                    }
                }

                cursor.close();

            } catch (Exception ex) {


                return null;

            }finally {

                close();
            }

        }

        return person;
    }

    public boolean havingDebts(long debtorId, long creditorId){

        boolean hasDebts = false;

        open();

        if(Database.isOpen()) {

            try {

                Cursor c = Database.rawQuery("SELECT Sum(" + Sql.NAME_COLUMN_ID  + ") AS total FROM " + Sql.NAME_TABLE_TRANSACTIONS + " WHERE " + Sql.NAME_COLUMN_DEBTOR
                        + " = " + debtorId + " AND " + Sql.NAME_COLUMN_CREDITOR + " = " + creditorId, null);

                if (c != null) {

                    if (c.moveToFirst()) {
                        do {

                             long count = c.getLong(c.getColumnIndex("total"));

                              if(count > 0){

                                  hasDebts = true;
                              }

                        } while (c.moveToNext());
                    }
                }

                c.close();

            } catch (Exception ex) {

                return false;

            }finally {

                close();
            }
        }

        return hasDebts;

    }



    /**
     *
     * @return
     */
    public ArrayList<Category> getCategories(){


        ArrayList<Category> list = null;

        String[] columns = {Sql.NAME_COLUMN_ID, Sql.NAME_COLUMN_NAME};

        open();

        if(Database.isOpen()) {

            try {

                list = new ArrayList<>();

                Cursor cursor = Database.query(Sql.NAME_TABLE_CATEGORIES, columns, null, null, null, null, null);

                if (cursor != null) {

                    if (cursor.moveToFirst()) {
                        do {

                            Category category = new Category();

                            category.setID(cursor.getLong(cursor.getColumnIndex(columns[0])));
                            category.setName(cursor.getString(cursor.getColumnIndex(columns[1])));

                            list.add(category);
                            Log.d("Category", category.toString());

                        } while (cursor.moveToNext());
                    }
                }

                cursor.close();

            } catch (Exception ex) {


                return null;

            }finally {

                close();
            }
        }

        return list;

    }


    //endregion


    /**
     *
     * @param sql
     */
    private void insertData(String sql){

        if(sql != null){

            try{

                if(Database.isOpen()) {

                    Database.execSQL(sql);

                    Toast.makeText(context, "Gespeichert", Toast.LENGTH_LONG);

                }else{

                    Log.e(TAG, "Datenbank ist geschlossen.");
                }

            }catch (Exception ex){

                Log.e(TAG, ex.getMessage());

                Toast.makeText(context, "Fehler aufgetreten. Daten wurden nicht gespeichert.", Toast.LENGTH_LONG);

            }finally {

                close();
            }
        }
    }

}
