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
 * Die Klasse beinhaltet die Datenbank und bietet diverse Methoden zum Ausfuehren von Abfragen an die Datenbank.
 * @since 31.05.2016
 * @author Carolin Gellner
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

    /**
     * Der Konstruktur erstellt eine neue Instanz der Klasse Database.
     * @param context
     */
    public Database(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    //endregion


    //region Getter & Setter

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
     *Die Methode speichert ein Repayment-Datensatz in der Datenbank.
     * @param repayment Repayment-Datensatz
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
     * Die Methode schreibt eine erfasste Zahlung (Payment) in die Datenbank.
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
     * Die Methode ermittelt die Summe der getaetigten Zahlungen des uebergebenen Creditoren fuer den angegeben Debitor.
     * @param creditorId ID des Kreditors
     * @param debtorId ID des Debitors
     * @return Summe der Zahlungen
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
     * Die Methode ermittelt die Summe der getaetigten Rueckzahlungen des uebergebenen Debitoren fuer den angegeben Kreditor.
     * @param creditorId ID des Kreditors
     * @param debtorId ID des Debitors
     * @return Summe der Rueckzahlungen
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
     * @param creditorId
     * @param debtorId
     * @return
     */
    public ArrayList<Pay> getPaymentsAndRepayments(long creditorId, long debtorId, long position){

        ArrayList<Pay> list = new ArrayList<>();

        if(position == 0){

            ArrayList<Payment> payments = getPayments(creditorId, debtorId);
            ArrayList<Pay> repayments = getRepayments(creditorId, debtorId);

            for (Payment payment:payments) {

                list.add(payment);
            }

            for (Pay repayment : repayments) {

                list.add(repayment);
            }

        }else if(position == 1){

            ArrayList<Payment> payments = getPayments(creditorId, debtorId);

            for (Payment payment:payments) {

                list.add(payment);
            }

        }else if(position == 2){

            ArrayList<Pay> repayments = getRepayments(creditorId, debtorId);

            for (Pay repayment : repayments) {

                list.add(repayment);
            }

        }


        ArrayList<Pay> sortedList = sort(list);

        return sortedList;
    }


    /**
     *
     * @param mainpersonId
     * @param secondPersonId
     * @return
     */
    public ArrayList<Pay> getRepayments(long mainpersonId, long secondPersonId){

        ArrayList<Pay> list = new ArrayList<>();


        open();


        try {

            Cursor cursor = null;

            if(mainpersonId != -1 && secondPersonId == -1){


                    cursor = Database.query(Sql.NAME_TABLE_REPAYMENTS,
                            null,
                            Sql.NAME_COLUMN_CREDITOR + "=" + mainpersonId + " OR " + Sql.NAME_COLUMN_DEBTOR + "=" + mainpersonId,
                            null, null, null, null);



            }else if(mainpersonId != -1 && secondPersonId != -1){



                    cursor = Database.query(Sql.NAME_TABLE_REPAYMENTS,
                            null,
                            "(" + Sql.NAME_COLUMN_CREDITOR + "=" + mainpersonId + " AND "
                                    + Sql.NAME_COLUMN_DEBTOR + "=" + secondPersonId + ") OR (" + Sql.NAME_COLUMN_CREDITOR + "=" + secondPersonId + " AND "
                                    + Sql.NAME_COLUMN_DEBTOR + "=" + mainpersonId + ")",
                            null, null, null, null);




            }

            if (cursor != null) {

                if (cursor.moveToFirst()) {

                    do {

                        Pay repayment = new Pay();

                        SimpleDateFormat sdfToDate = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                        Date dateTime = sdfToDate.parse(cursor.getString((cursor.getColumnIndex(Sql.NAME_COLUMN_DATETIME))));

                        repayment.setID(cursor.getLong(cursor.getColumnIndex(Sql.NAME_COLUMN_ID)));
                        repayment.setDateTime(dateTime);
                        repayment.setValue(cursor.getFloat((cursor.getColumnIndex(Sql.NAME_COLUMN_VALUE))));
                        repayment.setCreditorId(cursor.getLong((cursor.getColumnIndex(Sql.NAME_COLUMN_CREDITOR))));
                        repayment.setDebtorId(cursor.getLong((cursor.getColumnIndex(Sql.NAME_COLUMN_DEBTOR))));
                        repayment.setDetails(cursor.getString((cursor.getColumnIndex(Sql.NAME_COLUMN_DETAILS))));

                        list.add(repayment);

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
     * Die Methode holt alle Payment-Datensaetze aus der Datenbank, welche die beiden angegeben Personen betreffen.
     * @param mainpersonId ID des Kreditors
     * @param secondPersonId ID des Debitors
     * @return Liste mit Payment-Datensaetzen
     */
    public ArrayList<Payment> getPayments(long mainpersonId, long secondPersonId){

        ArrayList<Payment> list = new ArrayList<>();


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
     * Die Methode holt alle Personennamen aus der Datenbank.
     * @return Liste mit allen Namen der Personen
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
     * Die Methode ermittelt den Namen der Kategorie zur uebergebenen Kategorie-Id.
     * @param categoryId ID der Kategorie
     * @return Name der Kategorie
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
     * Die Methode ermittelt den Namen der Person zur uebergebenen Person-ID.
     * @param personId ID der Person
     * @return Name der Person
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
     * Die Methode erfragt alle Kategorien und speichert diese in einer Liste.
     * @return Liste mit Kategorien
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
     * Die Methode fuert ein SQL-Befehl aus, welcher keine Rueckgabe erfordert (Insert, Update, Delete)
     * @param sql Sql-Befehl
     */
    private void insertData(String sql){

        open();

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


    /**
     * Die Methode updated den Namen der Kategorie mit der angegebenen KategorieId in den neuen Namen der uebergeben wurde.
     * @param categoryId ID der Kategorie
     * @param newCategoryName Neue Bezeichnung der Kategorie
     */
    public void updateCategoryName(long categoryId, String newCategoryName){

        String sql = "UPDATE " + Sql.NAME_TABLE_CATEGORIES + " SET " + Sql.NAME_COLUMN_NAME + " = '" + newCategoryName + "'" +
                " WHERE " + Sql.NAME_COLUMN_ID + " = " + categoryId;

        insertData(sql);


    }


    /**
     * Die Methode updated den Namen der Person mit der angegebenen Person-Id in den neuen Namen der uebergeben wurde.
     * @param personId ID der Person
     * @param newPersonName Neuer Name der Person
     */
    public void updatePersonName(long personId, String newPersonName){

        String sql = "UPDATE " + Sql.NAME_TABLE_PERSONS + " SET " + Sql.NAME_COLUMN_NAME + " = '" + newPersonName + "'" +
                " WHERE " + Sql.NAME_COLUMN_ID + " = " + personId;

        Log.d("Update", sql.toString());

        insertData(sql);

    }


    /**
     * Die Methode loescht eine Person aus der Datenbank.
     * @param personId
     */
    public void deletePerson(long personId){

        String sql = "DELETE FROM " +  Sql.NAME_TABLE_PERSONS + " WHERE " + Sql.NAME_COLUMN_ID + " = " + personId;

        insertData(sql);

    }


    /**
     * Die Methode loescht eine Kategorie aus der Datenbank.
     * @param categoryId
     */
    public void deleteCateogry(long categoryId){

        String sql = "DELETE FROM " +  Sql.NAME_TABLE_CATEGORIES + " WHERE " + Sql.NAME_COLUMN_ID + " = " + categoryId;

        insertData(sql);

    }


    /**
     * Die Kategorie loescht alle Payment-Datensaetze, welche die uebergebene Person-Id betreffen.
     * @param personId
     */
    public void deletePayments(long personId){

        String sql = "DELETE FROM " + Sql.NAME_TABLE_PAYMENTS +
                " WHERE " + Sql.NAME_COLUMN_CREDITOR + "=" + personId +  " OR " + Sql.NAME_COLUMN_DEBTOR + "=" + personId;

        insertData(sql);
    }


    /**
     * Die Kategorie loescht alle Repayment-Datensaetze, welche die uebergebene Person-Id betreffen.
     * @param personId
     */
    public void deleteRepayment(long personId){

        String sql = "DELETE FROM " + Sql.NAME_TABLE_REPAYMENTS +
                " WHERE " + Sql.NAME_COLUMN_CREDITOR + "=" + personId +  " OR " + Sql.NAME_COLUMN_DEBTOR + "=" + personId;

        insertData(sql);
    }

    //endregion



    /**
     * Die Methode sortiert die Liste der Pay-Datensaetze absteigend nach deren Zeitstempel.
     * @param pays Liste mit Pays-Datensaetze
     * @return Sortierte Liste
     */
    private ArrayList<Pay> sort(ArrayList<Pay> pays) {

        Pay p;
        for (int i = 0; i < pays.size() - 1; i++) {

            if (pays.get(i).getDateTime().getTime() > pays.get(i + 1).getDateTime().getTime()) {

                continue;
            }

            p = pays.get(i);
            pays.set(i,pays.get(i + 1));
            pays.set(i+1,p);

            sort(pays);
        }

        return pays;
    }

}
