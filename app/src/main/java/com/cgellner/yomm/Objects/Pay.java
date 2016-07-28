package com.cgellner.yomm.Objects;

import java.util.Date;

/**
 *
 * Die Klasse Pay stellt die Oberklasse fuer ein Zahlungsobjekt dar, welche gleichzeitig auch eine Rueckzahlung darstellen kann.
 * @since 23.06.2016
 * @author Carolin Gellner
 */
public class Pay {

    //region Fields

    private long ID;
    private Date DateTime;
    private long  CreditorId;
    private long  DebtorId;
    private double Value;
    private String Details;

    //endregion


    //region Getter & Setter

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public Date getDateTime() {
        return DateTime;
    }

    public void setDateTime(Date dateTime) {
        DateTime = dateTime;
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

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }


    //endregion

}
