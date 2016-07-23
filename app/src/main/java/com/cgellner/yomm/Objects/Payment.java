package com.cgellner.yomm.Objects;


/**
 * Die Klasse stellt eine Zahlung in Form einer Ausgabe dar und enthaelt die Oberklasse Pay.
 * @since 31.05.2016
 * @author Carolin Gellner
 */
public class Payment extends Pay {


    //region Fields

    private long CategoryId;
    private double MoneySum;


    //endregion


    //region Getter & Setter

    public long getCategoryId() {
        return CategoryId;

    }

    public void setCategoryId(long categoryId) {
        CategoryId = categoryId;
    }

    public double getMoneySum() {
        return MoneySum;
    }

    public void setMoneySum(double moneySum) {
        MoneySum = moneySum;
    }


    //endregion

    


}
