package com.cgellner.yomm.Objects;

import com.cgellner.yomm.Database.Sql;

/**
 * Created by Carolin on 31.05.2016.
 */
public class Transaction {

    //region Fields

    private long ID;
    private int Type;
    private String Date;
    private String Time;
    private long  CreditorId;
    private long  DebtorId;
    private double Value;
    private long Category;
    private String Details;

    //endregion


    //region Properties

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public long getCreditorId() {
        return CreditorId;
    }

    public void setCreditorId(long creditorId) {
        CreditorId = creditorId;
    }

    public long getDebtorId() {
        return DebtorId;
    }

    public void setDebtorId(long debtorId) {
        DebtorId = debtorId;
    }

    public double getValue() {
        return Value;
    }

    public void setValue(double value) {
        Value = value;
    }

    public long getCategory() {
        return Category;
    }

    public void setCategory(long category) {
        Category = category;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }


    //endregion


    /**
     *
     * @return
     */
    public String getSqlInsert(){

        return  "INSERT INTO " + Sql.NAME_TABLE_TRANSACTIONS + " VALUES ( " +
                Sql.NAME_COLUMN_TYPE;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                " ID=" + ID +
                ", Type=" + Type +
                ", Date=" + Date +
                ", Time= " + Time +
                ", CreditorId =" + CreditorId +
                ", DebtorId ='" + DebtorId +
                ", Value =" + Value +
                ", CategoryId =" + Category +
                ", Details =" + Details +
                '}';
    }
}
