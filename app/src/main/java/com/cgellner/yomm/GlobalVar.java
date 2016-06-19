package com.cgellner.yomm;

import com.cgellner.yomm.Objects.Account;
import com.cgellner.yomm.Objects.MainCategory;
import com.cgellner.yomm.Objects.Person;

import java.util.ArrayList;


/**
 * Created by Carolin on 31.05.2016.
 */
public abstract class GlobalVar {


    public static com.cgellner.yomm.Database.Database Database;

    public static final String DatabaseName = "Mymo_Database.db";
    public static final int DATABASE_VERSION = 3;




    public static final String[] MainCategories = {"Lebensmittel","Miete+NK", "Freizeit", "Versicherung", "Einnahmen", "Anschaffung","Drogerie", "Diverse"};
    public static final String[][] SubCategories = {{"Supermarkt", "Fastfood", "Restaurant", "Kantine"},
                                                    {"Miete", "Telefon/Internet", "GEZ", "Strom", "Gas"},
                                                    {"Kino", "Bowling", "Party", "Museum", "Sport"},
                                                    {"Kleidung", "Schuhe", "Accessoires", "Taschen"},
                                                    {"Haftpflicht", "Hausrat", "Krankenkasse"},
                                                    {"Gehalt", "Eltern", "Verkäufe", "Gutschrift"},
                                                    {"Technik", "Kleidung", "Bücher"},
                                                    {"Kosmetik", "Haushaltsmittel"},
                                                    {"Geschenk", "Smartphone", "Musik", "Apps"}};




    public static ArrayList<MainCategory> ListMainCateories;
    public static ArrayList<Person> ListPersons;
    public static ArrayList<Account> ListAccounts;


    //region Names of Shared Preferences (ActivityMain)

    public static String SpVarNameValue = "transValue";
    public static String SpVarNamePersonsPayed = "transPersonPayed";
    public static String SpVarNamePersons = "transPersons";
    public static String SpVarNameCategory = "transCategory";
    public static String SpVarNameDetails = "transDetails";

    //endregion



}
