package com.cgellner.yomm.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.cgellner.yomm.Objects.Category;
import com.cgellner.yomm.Objects.Pay;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.Objects.Payment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Carolin on 31.05.2016.
 */
public class Database extends SQLiteOpenHelper {


    //region Fields

    private final String TAG = Database.class.getName();

    private static final String DATABASE_NAME = "Yomm_Database.db";
    private static final int DATABASE_VERSION = 7;

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

            Database.execSQL(Sql.CREATE_TABLE_PAYMENTS);
            Database.execSQL(Sql.CREATE_TABLE_REPAYMENTS);
            Database.execSQL(Sql.CREATE_TABLE_PERSONS);
            Database.execSQL(Sql.CREATE_TABLE_CATEGORIES);

            Log.d(TAG, "Datenbank wurde erstellt.");


        }catch (Exception ex){

            Log.e(TAG, ex.getMessage());
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Sql.NAME_TABLE_PAYMENTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Sql.NAME_TABLE_REPAYMENTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Sql.NAME_TABLE_CATEGORIES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Sql.NAME_TABLE_PERSONS);

        this.onCreate(sqLiteDatabase);

    }


    /**
     * Die Methode oeffnet die SQLite-Datenbank.
     */
    public void open(){

        Database = this.getWritableDatabase();
        Log.v(TAG, "Datenbank wurde geoeffnet");
    }



    /**
     * Die Methode schliesst die SQLite-Datenbank.
     */
    public void close(){

        if(Database.isOpen()) {

            Database.close();
            Log.v(TAG, "Datenbank wurde geschlossen.");
        }
    }



    /**
     * Die Methode speichert die uebergebe Person in der Datenbank.
     * @param person Person
     */
    public void insertPerson(Person person){

        open();
        insertData(person.getSqlInsert());
        Log.v("Person", person.toString());
        close();
    }



    /**
     * Die Methode speichert die uebergebene Kategorie in der Datenbank.
     * @param category Kategorie
     */
    public void insertCategory(Category category){

        open();
        insertData(category.getSqlInsert());
        Log.v("Category", category.toString());
        close();
    }

    /**
     *
     * @param repayment
     */
    public void insertRepayment(Pay repayment){

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        open();

        String sql = "INSERT INTO " + Sql.NAME_TABLE_REPAYMENTS + " (" +
                Sql.NAME_COLUMN_VALUE + ", " +
                Sql.NAME_COLUMN_DATETIME + ", " +
                Sql.NAME_COLUMN_DEBTOR + ", " +
                Sql.NAME_COLUMN_CREDITOR + ", " +
                Sql.NAME_COLUMN_DETAILS + ")" +
                " VALUES (" +
                repayment.getValue() + ", " +
                "'" + dateFormatter.format(repayment.getDateTime()) + "'," +
                repayment.getDebtorId() + ", " +
                repayment.getCreditorId() + ", " +
                "'" + repayment.getDetails() + "')";

        insertData(sql);

        close();
    }


    /**
     * Die Methode schreibt eine erfasste Zahlung in die Datenbank.
     * @param payment Datensatz einer Zahlung
     */
    public void insertPaymentDataset(Payment payment){

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        open();

        String sql = "INSERT INTO " + Sql.NAME_TABLE_PAYMENTS + " (" +
                Sql.NAME_COLUMN_VALUE + ", " +
                Sql.NAME_COLUMN_DATETIME + ", " +
                Sql.NAME_COLUMN_DEBTOR + ", " +
                Sql.NAME_COLUMN_CREDITOR + ", " +
                Sql.NAME_COLUMN_SUM + ", " +
                Sql.NAME_COLUMN_CATEGORY + ", " +
                Sql.NAME_COLUMN_DETAILS + ")" +
                " VALUES (" +
                payment.getValue() + ", " +
                "'" + dateFormatter.format(payment.getDateTime()) + "'," +
                payment.getDebtorId() + ", " +
                payment.getCreditorId() + ", " +
                payment.getMoneySum() + ", " +
                payment.getCategoryId() + ", " +
                "'" + payment.getDetails() + "')";


        insertData(sql);

        close();
    }



    /**
     *
     * @param creditorId
     * @param debtorId
     * @return
     */
    public double getPaymentSum(long creditorId, long debtorId){

        double sum = 0d;

        open();

        try {

            Cursor cursor = Database.query(Sql.NAME_TABLE_PAYMENTS, new String[]{Sql.NAME_COLUMN_VALUE},
                    Sql.NAME_COLUMN_CREDITOR + "=" + creditorId + " AND " +
                            Sql.NAME_COLUMN_DEBTOR + "=" + debtorId, null, null, null,null);


            if (cursor != null) {

                if (cursor.moveToFirst()) {

                    do {

                        sum += cursor.getDouble(cursor.getColumnIndex(Sql.NAME_COLUMN_VALUE));

                    } while (cursor.moveToNext());
                }
            }

            cursor.close();

        } catch (Exception ex) {


            return 0;

        }finally {

            close();
        }

        return sum;

    }


    /**
     *
     * @param creditorId
     * @param debtorId
     * @return
     */
    public double getRepaymentSum(long creditorId, long debtorId){

        double sum = 0d;

        open();

        try {

              Cursor cursor = Database.query(Sql.NAME_TABLE_REPAYMENTS, new String[]{Sql.NAME_COLUMN_VALUE},
                            Sql.NAME_COLUMN_CREDITOR + "=" + creditorId + " AND " +
                            Sql.NAME_COLUMN_DEBTOR + "=" + debtorId, null, null, null,null);


            if (cursor != null) {

                if (cursor.moveToFirst()) {

                    do {

                        sum += cursor.getDouble(cursor.getColumnIndex(Sql.NAME_COLUMN_VALUE));

                    } while (cursor.moveToNext());
                }
            }

            cursor.close();

        } catch (Exception ex) {


            return -1;

        }finally {

            close();
        }

        return sum;

    }



    /**
     *
     * @param mainpersonId
     * @param secondPersonId
     * @return
     */
    public List<Payment> getPayments(long mainpersonId, long secondPersonId){

        List<Payment> list = new ArrayList<>();


        open();


        try {

            Cursor cursor = null;

            if(mainpersonId != -1 && secondPersonId == -1){

                 cursor = Database.query(Sql.NAME_TABLE_PAYMENTS,
                        null,
                        Sql.NAME_COLUMN_CREDITOR + "=" + mainpersonId + " OR " + Sql.NAME_COLUMN_DEBTOR + "=" + mainpersonId,
                        null, null, null,null);


            }else if(mainpersonId != -1 && secondPersonId != -1){

                 cursor = Database.query(Sql.NAME_TABLE_PAYMENTS,
                        null,
                        "(" + Sql.NAME_COLUMN_CREDITOR + "=" + mainpersonId + " AND "
                                + Sql.NAME_COLUMN_DEBTOR + "=" + secondPersonId + ") OR (" + Sql.NAME_COLUMN_CREDITOR + "=" + secondPersonId + " AND "
                                + Sql.NAME_COLUMN_DEBTOR + "=" + mainpersonId + ")",
                        null, null, null, null);

            }

            if (cursor != null) {

                if (cursor.moveToFirst()) {

                    do {

                        Payment payment = new Payment();

                        SimpleDateFormat sdfToDate = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                        Date dateTime = sdfToDate.parse(cursor.getString((cursor.getColumnIndex(Sql.NAME_COLUMN_DATETIME))));

                        payment.setID(cursor.getLong(cursor.getColumnIndex(Sql.NAME_COLUMN_ID)));
                        payment.setDateTime(dateTime);
                        payment.setValue(cursor.getFloat((cursor.getColumnIndex(Sql.NAME_COLUMN_VALUE))));
                        payment.setCreditorId(cursor.getLong((cursor.getColumnIndex(Sql.NAME_COLUMN_CREDITOR))));
                        payment.setDebtorId(cursor.getLong((cursor.getColumnIndex(Sql.NAME_COLUMN_DEBTOR))));
                        payment.setCategoryId(cursor.getLong((cursor.getColumnIndex(Sql.NAME_COLUMN_CATEGORY))));
                        payment.setDetails(cursor.getString((cursor.getColumnIndex(Sql.NAME_COLUMN_DETAILS))));
                        payment.setMoneySum(cursor.getFloat(cursor.getColumnIndex(Sql.NAME_COLUMN_SUM)));

                        list.add(payment);

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



    /**
     *
     * @param categoryId
     * @return
     */
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



    /**
     *
     * @param personId
     * @return
     */
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

    //endregion

}
