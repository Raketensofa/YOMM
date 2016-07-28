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
    public static String typeRepayment = "repayment";


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
    public static final String SpRepaymentDetails = "repaymentDetails";



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


    /**
     *
     * @param debtorId
     * @param creditorId
     * @return
     */
    public static double getPaymentDifference(long debtorId, long creditorId){

        double diff = 0d;

        double creditorPaySum =  Database.getPaymentSum(creditorId, debtorId);
        double debtorPaySum =  Database.getPaymentSum(debtorId, creditorId);

        double debtorRepaySum =  Database.getRepaymentSum(debtorId, creditorId);
        double creditorRepaySum =  Database.getRepaymentSum(creditorId, debtorId);


        //Differenz aus Sicht des Debtors ermitteln
        //Wie viel muss der Debtor dem Creditor noch zurueckzahlen
        //wenn der Wert negativ, dann hat er Schulden, wenn der Wert positiv, dann bekommt der
        //Debtor Geld vom Creditor
        diff = (debtorPaySum - debtorRepaySum) - (creditorPaySum - creditorRepaySum);


        return diff;
    }

}
