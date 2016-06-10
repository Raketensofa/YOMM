package com.cgellner.yomm.Objects;

import com.cgellner.yomm.Database.Sql;

/**
 * Created by Carolin on 31.05.2016.
 */
public class Transaction {

    //region Fields

    private long ID;
    private int Type;
    private String DateTime;
    private String DateTimeMaturity;
    private String PayingPersons;
    private String FallsAtPersons;
    private double Value;
    private int MainCategory;
    private int SubCategory;
    private String Description;
    private int Account;

    //endregion


    //region Properties

    /**
     * ID der Transaktion
     * @return
     */
    public long getID() {
        return ID;
    }

    /**
     * ID der Transaktion
     * @param ID
     */
    public void setID(long ID) {
        this.ID = ID;
    }

    /**
     * Typ (Einnahme / Ausgabe)
     * @return
     */
    public int getType() {
        return Type;
    }

    /**
     * Typ (Einnahme / Ausgabe)
     * @param type
     */
    public void setType(int type) {
        Type = type;
    }

    /**
     * Datum der Erfassung
     * @return
     */
    public String getDateTime() {
        return DateTime;
    }

    /**
     * Datum der Erfassung
     * @param dateTime
     */
    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    /**
     * Datum der Faelligkeit (Buchung)
     * @return
     */
    public String getDateTimeMaturity() {
        return DateTimeMaturity;
    }

    /**
     * Datum er Faelligkeit (Buchung)
     * @param dateTimeMaturity
     */
    public void setDateTimeMaturity(String dateTimeMaturity) {
        DateTimeMaturity = dateTimeMaturity;
    }

    /**
     * Personen die gezahlt haben
     * @return
     */
    public String getPayingPersons() {
        return PayingPersons;
    }

    /**
     * Personen die gezahlt haben
     * @param payingPersons
     */
    public void setPayingPersons(String payingPersons) {
        PayingPersons = payingPersons;
    }

    /**
     * Personen fuer die der Betrag anfaellt
     * @return
     */
    public String getFallsAtPersons() {
        return FallsAtPersons;
    }

    /**
     * Personen fuer die der Betrag anfaellt
     * @param fallsAtPersons
     */
    public void setFallsAtPersons(String fallsAtPersons) {
        FallsAtPersons = fallsAtPersons;
    }

    /**
     * Betrag
     * @return
     */
    public double getValue() {
        return Value;
    }

    /**
     * Betrag
     * @param value
     */
    public void setValue(double value) {
        Value = value;
    }

    /**
     * Hauptkategorie
     * @return
     */
    public int getMainCategory() {
        return MainCategory;
    }

    /**
     * Hauptkategorie
     * @param mainCategory
     */
    public void setMainCategory(int mainCategory) {
        MainCategory = mainCategory;
    }

    /**
     * Unterkategorie
     * @return
     */
    public int getSubCategory() {
        return SubCategory;
    }

    /**
     * Unterkategorie
     * @param subCategory
     */
    public void setSubCategory(int subCategory) {
        SubCategory = subCategory;
    }

    /**
     * Beschreibung / Details
     * @return
     */
    public String getDescription() {
        return Description;
    }

    /**
     * Beschreibung / Details
     * @param description
     */
    public void setDescription(String description) {
        Description = description;
    }

    /**
     * Konto
     * @return
     */
    public int getAccount() {
        return Account;
    }

    /**
     * Konto
     * @param account
     */
    public void setAccount(int account) {
        Account = account;
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
                "ID=" + ID +
                ", Type=" + Type +
                ", DateTime='" + DateTime +
                ", DateTimeMaturity='" + DateTimeMaturity +
                ", PayingPersons='" + PayingPersons +
                ", FallsAtPersons='" + FallsAtPersons +
                ", Value=" + Value +
                ", MainCategory=" + MainCategory +
                ", SubCategory=" + SubCategory +
                ", Description='" + Description +
                ", Account=" + Account +
                '}';
    }
}
