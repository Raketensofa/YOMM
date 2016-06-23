package com.cgellner.yomm;



/**
 * Created by Carolin on 31.05.2016.
 */
public abstract class GlobalVar {

    //region Databbase

    public static com.cgellner.yomm.Database.Database Database;


    //endregion


    //region Names of Shared Preferences (ActivityMain)

    public static String SpVarNameValue = "transValue";
    public static String SpVarNameCreditor = "transCreditor";
    public static String SpVarNameDebtors = "transDebtors";
    public static String SpVarNameCategory = "transCategory";
    public static String SpVarNameDetails = "transDetails";

    //endregion

}
