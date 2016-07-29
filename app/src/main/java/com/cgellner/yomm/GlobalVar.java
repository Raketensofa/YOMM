package com.cgellner.yomm;


import com.cgellner.yomm.Objects.Pay;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Die Klasse repraesentiert eine Sammlung von Variablen und Methoden die global im Programmcode verwendet werden.
 * @since 31.05.2016
 * @author Carolin Gellner
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


    //region Methods


    /**
     * Die Methode formatiert einen Geldwert in ein entsprechendes Format um.
     * @param moneyValue Geldwert
     * @return formatierter Geldwert
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
     * Die Methode ermittelt die Zahlungsdiffernz zwischen den beiden angegebenen Personen.
     * @param debtorId ID des Kreditors (erste Person)
     * @param creditorId ID der Debitors (zweite Person)
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


    /**
     * Die Methode sortiert die Liste der Pay-Datensaetze absteigend nach deren Zeitstempel.
     * @param pays Liste mit Pays-Datensaetze
     * @return Sortierte Liste
     */
    public static ArrayList<Pay> sort(ArrayList<Pay> pays) {

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

    //endregion

}
