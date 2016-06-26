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


    public double getDebtSum(long creditorId, long debtorId){

        double value = 0d;




        return value;

    }

    public double getTransactionSum(long creditorId, long debtorId){

        double value = 0d;

        try {

            Cursor cursor = Database.query(Sql.CREATE_TABLE_TRANSACTIONS,
                            new String[]{Sql.NAME_COLUMN_VALUE},
                            Sql.NAME_COLUMN_TYPE + "=1 AND "
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


    /**
     *
     * @return
     */
    public ArrayList<Person> getPersons(){

        ArrayList<Person> list = null;

        String[] columns = {Sql.NAME_COLUMN_ID, Sql.NAME_COLUMN_NAME};

        open();

        if(Database.isOpen()) {

            try {

                list = new ArrayList<>();

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


                return null;

            }finally {

                close();
            }
        }

        return list;

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
