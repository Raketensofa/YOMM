package com.cgellner.yomm;


import java.text.DecimalFormat;

/**
 * Created by Carolin on 31.05.2016.
 */
public abstract class GlobalVar {


    //region Database


        public static com.cgellner.yomm.Database.Database Database;


    //endregion

    public static String typePayment = "payment";
    public static String typeRePpayment = "repayment";


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



    /**
     *
     * @param moneyValue
     * @return
     */
    public static String formatMoney(String moneyValue){

        String str = "";

        if(moneyValue.contains(",")){
            moneyValue.replace(",", ".");
        }

        DecimalFormat f = new DecimalFormat("#0.00");
        double toFormat = ((double)Math.round(new Double(moneyValue)*100))/100;

        return  f.format(toFormat);
    }
}
