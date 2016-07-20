package com.cgellner.yomm;



/**
 * Created by Carolin on 31.05.2016.
 */
public abstract class GlobalVar {


    //region Database


        public static com.cgellner.yomm.Database.Database Database;


    //endregion


    //region Names of Shared Preferences (Sp) for Payment

    public static final String SpPaymentMoneyValue = "paymentMoneyValue";
    public static final String SpPaymentCreditor = "paymentCreditor";
    public static final String SpPaymentDebtors = "paymentDebtors";
    public static final String SpPaymentCategory = "paymentCategory";
    public static final String SpPaymentDetails = "paymentDetails";

    //endregion


    //region Names of Shared Preferences (Sp) for Repayment

    public static final String SpRepaymentMoneySum = "repaymentMoneySum";
    public static final String SpRepaymentCreditor = "repaymentCreditor";
    public static final String SpRepaymentDebtor = "repaymentDebtor";
    public static final String SpRepaymentPaymentIds = "repaymentPaymentIds";

    //endregion


    //region Display

    //Hoehe und Breite vom Display des Smartphones
    public static int Display_Width;
    public static int Display_Height;


    //endregion
}
