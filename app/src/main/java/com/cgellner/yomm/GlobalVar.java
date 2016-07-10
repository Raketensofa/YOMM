package com.cgellner.yomm;



/**
 * Created by Carolin on 31.05.2016.
 */
public abstract class GlobalVar {

    //region Databbase

    public static com.cgellner.yomm.Database.Database Database;


    //endregion


    //region Names of Shared Preferences (Activity_AddNewTrans)

    public static final String SpVarNameValue = "transValue";
    public static final String SpVarNameCreditor = "transCreditor";
    public static final String SpVarNameDebtors = "transDebtors";
    public static final String SpVarNameCategory = "transCategory";
    public static final String SpVarNameDetails = "transDetails";

    //endregion

    //region Names of Shared Preferences (Activity_ClearDebts)

    public static final String SpVarNameSumClearValue = "clearDebtSumValue";
    public static final String SpVarNameClearCreditor = "clearDebtCreditor";
    public static final String SpVarNameClearDebitor = "clearDebtDebtor";
    public static final String SpVarNameTransactions = "clearDebtTransactionIds";

    //endregion


    //region Display

    public static int Display_Width;
    public static int Display_Height;


    //endregion
}
