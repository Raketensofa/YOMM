package com.cgellner.yomm.Objects;

import com.cgellner.yomm.Database.Sql;

/**
 *
 * @since 31.05.2016
 * @author Carolin Gellner
 */
public class Payment {


    //region Fields

    private long ID;
    private int State;
    private String Date;
    private String Time;
    private long  CreditorId;
    private long  DebtorId;
    private float Value;
    private long Category;
    private String Details;
    private float Moneysum;
    private String Repayment_date;
    private String Repayment_time;

    //endregion


    //region Getter & Setter


    public float getMoneysum() {
        return Moneysum;
    }

    public void setMoneysum(float moneysum) {
        Moneysum = moneysum;
    }

    public String getRepayment_date() {
        return Repayment_date;
    }

    public void setRepayment_date(String repayment_date) {
        Repayment_date = repayment_date;
    }

    public String getRepayment_time() {
        return Repayment_time;
    }

    public void setRepayment_time(String repayment_time) {
        Repayment_time = repayment_time;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
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

    public void setValue(float value) {
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


    //region Methods

    /**
     *
     * @return
     */
    public String getSqlInsert(){




        return  "INSERT INTO " + Sql.NAME_TABLE_PAYMENTS +
                " (" + Sql.NAME_COLUMN_STATE + "," +
                Sql.NAME_COLUMN_PAYMENT_DATE + ", " +
                Sql.NAME_COLUMN_PAYMENT_TIME + ", " +
                Sql.NAME_COLUMN_CREDITOR + ", " +
                Sql.NAME_COLUMN_DEBTOR + ", " +
                Sql.NAME_COLUMN_VALUE + ", " +
                Sql.NAME_COLUMN_SUM + ", " +
                Sql.NAME_COLUMN_CATEGORY + ", " +
                Sql.NAME_COLUMN_DETAILS +

                ") VALUES (" +

                State + "," +
                "'" + Date + "'," +
                "'" + Time + "'," +
                CreditorId + "," +
                DebtorId + "," +
                Value + "," +
                Moneysum + ", " +
                Category + "," +
                "'" + Details + "'" +
            ")";
    }


    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Payment{" +
                "ID=" + ID +
                ", State=" + State +
                ", Date='" + Date + '\'' +
                ", Time='" + Time + '\'' +
                ", CreditorId=" + CreditorId +
                ", DebtorId=" + DebtorId +
                ", Value=" + Value +
                ", Category=" + Category +
                ", Details='" + Details + '\'' +
                ", Moneysum=" + Moneysum +
                ", Repayment_date='" + Repayment_date + '\'' +
                ", Repayment_time='" + Repayment_time + '\'' +
                '}';
    }

    //endregion
}
