package com.cgellner.yomm.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Account;
import com.cgellner.yomm.Objects.MainCategory;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.Objects.Transaction;

import java.util.ArrayList;


/**
 * Created by Carolin on 31.05.2016.
 */
public class Database extends SQLiteOpenHelper {


    private final String TAG = Database.class.getName();
    private SQLiteDatabase Database;
    private Context context;


    public Database(Context context) {

        super(context, GlobalVar.DatabaseName, null, GlobalVar.DATABASE_VERSION);
        this.context = context;

    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try{

            Database = sqLiteDatabase;
            Database.execSQL(Sql.CREATE_TABLE_TRANSACTIONS);
            Database.execSQL(Sql.CREATE_TABLE_PERSONS);
            Database.execSQL(Sql.CREATE_TABLE_MAINCATEGORIES);

            Log.d(TAG, "Datenbank wurde erstellt.");


        }catch (Exception ex){

            Log.e(TAG, ex.getMessage());
        }


    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        Log.d(TAG, "Upgrade der Datenbank");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Sql.NAME_TABLE_MAINCATEGORIES );
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
     * @param object
     */
    public void insert(Object object){

        open();
        addData(object, "insert");
        close();
    }


    /**
     *
     * @param object
     */
    public void update(Object object){

        open();
        addData(object, "update");
        close();

    }


    /**
     *
     * @param object
     */
    public void delete(Object object){

        open();
        addData(object, "delete");
        close();

    }




    /**
     *
     * @param object
     */
    private void addData(Object object, String option){

        String sql = getSqlStr(object, option);

        if(sql != null){

            try{

                if(Database.isOpen()) {

                    Database.execSQL(sql);
                    setToast(option);

                }else{

                    Log.e(TAG, "Datenbank ist geschlossen.");
                }

            }catch (Exception ex){

                Log.e(TAG, ex.getMessage());

                setToast("Fehler aufgetreten. Daten wurden nicht gespeichert.");
            }
        }

    }


    /**
     *
     * @param option
     */
    private void setToast(String option){

        Toast toast = null;
        if(option == "insert" || option == "update") {

            toast  = Toast.makeText(context, "Gespeichert", Toast.LENGTH_LONG);

        }else if(option == "delete"){

            toast  = Toast.makeText(context, "Gelöscht", Toast.LENGTH_LONG);

        }else{

            toast  = Toast.makeText(context, option, Toast.LENGTH_LONG);
        }

        toast.show();
    }


    /**
     *
     * @param object
     */
    private String getSqlStr(Object object, String option){

        String str = null;

        if(object != null && option != null) {

            //TRANSACTION------------------------------------
            if (object.getClass() == Transaction.class) {

                if(option == "insert"){

                    str = ((Transaction)object).getSqlInsert();

                }else if(option == "update"){


                }else if(option == "delete"){


                }

            //ACCOUNT---------------------------------------
            }else if(object.getClass() == Account.class){

                if(option == "insert"){

                    str = ((Account)object).getSqlInsert();

                }else if(option == "update"){


                }else if(option == "delete"){


                }
            //PERSON----------------------------------------
            }else if(object.getClass() == Person.class){

                if(option == "insert"){

                    str = ((Person)object).getSqlInsert();
                    Log.v(TAG, str);

                }else if(option == "update"){


                }else if(option == "delete"){


                }
            //MAINCATEGORY----------------------------------
            }else if(object.getClass() == MainCategory.class){

                if(option == "insert"){

                    str = ((MainCategory)object).getSqlInsert();

                }else if(option == "update"){


                }else if(option == "delete"){


                }
            }
        }

        return str;
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
    public ArrayList<MainCategory> getCategories(){

        ArrayList<MainCategory> list = null;

        String[] columns = {Sql.NAME_COLUMN_ID, Sql.NAME_COLUMN_NAME};

        open();
        if(Database.isOpen()) {

            try {

                list = new ArrayList<>();

                Cursor cursor = Database.query(Sql.NAME_TABLE_MAINCATEGORIES, columns, null, null, null, null, null);

                if (cursor != null) {

                    if (cursor.moveToFirst()) {
                        do {

                            MainCategory category = new MainCategory();

                            category.setID(cursor.getLong(cursor.getColumnIndex(columns[0])));
                            category.setName(cursor.getString(cursor.getColumnIndex(columns[1])));

                            list.add(category);

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


}
