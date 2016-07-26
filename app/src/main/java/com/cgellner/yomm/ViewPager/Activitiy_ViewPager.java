package com.cgellner.yomm.ViewPager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Pay;
import com.cgellner.yomm.Objects.Payment;
import com.cgellner.yomm.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Die Klasse beinhaltet die Activity in welcher der Viewpager angezeigt wird,
 * welcher die Formulare zum Erfassen einer Ausgabe oder Rueckzahlung beinhaltet.
 * @author Carolin Gellner
 */
public class Activitiy_ViewPager extends AppCompatActivity {


    //region Fields

    private ViewPager viewPager;
    private String type;
    private ViewpagerElements vpElements;

    public static Button buttonSave;
    public static Button buttonCancel;

    //endregion


    //region Protected Methods


    /**
     * Die Methode erstellt die Ansicht der Activity, welche den Viewpager beinhaltet.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Layout der Activity
        setContentView(R.layout.layout_activitiy_viewpager);

        //Zurueck-Button in der Titelleiste anzeigen
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //uebergebenen Typ abfragen (Payment oder Repayment)
        type = getIntent().getStringExtra("type");

        //Titel festlegen
        setActivityTitle();

        //Die Elemente (Seiten des Viewpagers) initialisieren
        vpElements = new ViewpagerElements();

        //Viewpager erstellen
        setViewPager();

        //Buttons der Activity (Speichern und Abbrechen) initialisieren
        setButtons();


        //SharedPreferences initialiseren (dienen zum Zwischenspeichern der
        //Daten aus den Formularen bevor diese in die Datenbank geschrieben werden)
        initSharedPreferences();

    }


    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    //endregion


    //region Private Methods

    /**
     * Die Methode legt den Titel der Activity fest.
     */
    private void setActivityTitle() {

        if (type.equals(GlobalVar.typePayment)) {

            this.setTitle("Neue Ausgabe");

        } else if (type.equals(GlobalVar.typeRepayment)) {

            this.setTitle("Rückzahlung");
        }
    }

    /**
     * Die Methode initialsiert den Viewpager.
     */
    private void setViewPager() {

        viewPager = (ViewPager) findViewById(R.id.activity_viewpager);

        //Layout des Viewpagers
        setViewpagerLayout();


        //ViewpagerAdapter festlegen
        PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                if (type.equals(GlobalVar.typeRepayment)) {

                    //Die Elemente im Viewpager anzeigen, welche fuer die Erfassung einer Rueckzahlung noetig sind
                    vpElements.setRepaymentViewpagerElements(viewPager, position);

                } else if (type.equals(GlobalVar.typePayment)) {

                    //Die Elemente im Viewpager anzeigen, welche fuer die Erfassung einer Ausgabe noetig sind
                    vpElements.setPaymentViewPagerElements(viewPager, position);
                }
            }


            //Diese beiden Methoden muessen von der Entwicklungsumgebung her implementiert sein,
            //diese werden aber nicht gebraucht von mir,
            //daher sind diese beiden Methoden leer
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });
    }

    /**
     * Die Methode initialisiert die Buttons (Speichern und Abbrechen).
     */
    private void setButtons() {

        buttonCancel = (Button) findViewById(R.id.button_viewpager_cancel);
        buttonSave = (Button) findViewById(R.id.button_viewpager_save);

        //Speichern-Button zunaechst nicht klickbar machen
        buttonSave.setClickable(false);
        buttonSave.setEnabled(false);

        buttonCancel.setClickable(true);

        //Button-Layouts ergaenzen
        setButtonCancelLayout();
        setButtonSaveLayout();


        //ClickListener der Abbrechen und Speichern-Buttons zuweisen
        setButtonSaveClickListener();
        setButtonCancelClickListener();

    }

    /**
     * Die Methode initialisiert die SharedPreferences, welche die eingebenen Daten des Benutzers zwischenspeichern,
     * bevor diese in die Datenbank geschrieben werden.
     */
    private void initSharedPreferences() {

        vpElements.setSharedPreferences(PreferenceManager.getDefaultSharedPreferences(this));

        //Leere Variablen in den SharedPreferences anlegen
        setSharedPreferencesEmpty();

    }



    /**
     * Die Methode beinhaltet den OnClickListener des Speichern-Buttons.
     */
    private void setButtonSaveClickListener() {

        if (buttonSave != null) {

            buttonSave.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (type.equals(GlobalVar.typeRepayment)) {

                        //RePayment-Datensatz speichern
                        saveRepayment();

                    } else if (type.equals(GlobalVar.typePayment)) {

                        //Payment-Datensatz speichern
                        savePayment();
                    }

                    //Viewpageransicht schliessen
                    buttonCancel.callOnClick();
                }
            });
        }
    }

    /**
     * Die Methode beinhaltet den OnClickListener des Abbrechen-Buttons.
     */
    private void setButtonCancelClickListener() {

        if (buttonCancel != null) {

            buttonCancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    //SharedPreferences leeren
                    setSharedPreferencesEmpty();

                    //Viewpageransicht schliessen
                    onBackPressed();
                }
            });
        }
    }



    /**
     * Die Methode fuert die Speicherung der eingegeben Daten des Benutzers zu einer Ausgabe aus.
     */
    private void savePayment() {

        //Daten aufbereiten fuer Datenbank
        ArrayList<Payment> payments = preparePaymentDatasets();

        //Datensaetze in die Datenbank schreiben
        addPaymentsToDb(payments);
    }

    /**
     * Die Methode fuert die Speicherung der eingegeben Daten des Benutzers zu einer Rueckzahlung aus.
     */
    private void saveRepayment() {

        //Gewaehlte Daten des Benutzers aus den SharedPreferences auslesen
        long creditorId = vpElements.getSharedPreferences().getLong(GlobalVar.SpRepaymentCreditor, 0);
        long debtorId = vpElements.getSharedPreferences().getLong(GlobalVar.SpRepaymentDebtor, 0);
        float value = vpElements.getSharedPreferences().getFloat(GlobalVar.SpRepaymentMoneySum, 0);
        String details = vpElements.getSharedPreferences().getString(GlobalVar.SpRepaymentDetails, "");


        //Neues Pay(ment)-Objekt erstellen
        Pay repayment = new Pay();
        repayment.setCreditorId(creditorId);
        repayment.setDebtorId(debtorId);
        repayment.setValue(value);
        repayment.setDetails(details);
        repayment.setDateTime(new Date());

        //Repayment-Datensatz in der Datenbank speichern
        GlobalVar.Database.insertRepayment(repayment);
    }


    /**
     * Die Methode bereitet die Daten, welche vom Benutzer fuer eine Ausgabe erfasst wurden, fuer die Speicherung in der Datenbank vor.
     * @return Liste mit Payment-Datensaetzen
     */
    private ArrayList<Payment> preparePaymentDatasets() {

        //aktuelles Datum und Uhrzeit ermitteln
        Date currentTimestamp = new Date();

        //Gewaehlte Daten des Benutzers aus den SharedPreferences auslesen
        float value = vpElements.getSharedPreferences().getFloat(GlobalVar.SpPaymentMoneyValue, 0);
        String details = vpElements.getSharedPreferences().getString(GlobalVar.SpPaymentDetails, "");
        long creditor = vpElements.getSharedPreferences().getLong(GlobalVar.SpPaymentCreditor, 0);
        long category = vpElements.getSharedPreferences().getLong(GlobalVar.SpPaymentCategory, 0);
        Set<String> persons = vpElements.getSharedPreferences().getStringSet(GlobalVar.SpPaymentDebtors, null);
        String[] debtors = (String[])persons.toArray();


        //Zahlungsdatensatze erzeugen (Pro gewaehlten Debitor ein Payment-Datensatz)
        ArrayList<Payment> payments = createPaymentDatasets(currentTimestamp, value, creditor, debtors, category, details);

        return payments;

    }

    /**
     * Die Methode erstellt aus den erfassten Daten des Benutzers pro gewaehlten Debtitor ein Payment-Datensatz fuer die Datenbank
     * und speichert diese in einer Liste, welche von der Methode zurueckgegeben wird.
     * @param datetime Zeitstempel
     * @param value Geldbetrag
     * @param creditor ID des Kreditors
     * @param debtors IDs der Debitoren
     * @param category ID der Kategorie
     * @param details Details / Sonstige Informationen
     * @return Liste mit Payment-Datensaetzen
     */
    private ArrayList<Payment> createPaymentDatasets(Date datetime, float value, long creditor, String[] debtors, long category, String details) {

        ArrayList<Payment> payments = new ArrayList<>();

        //Komma im Geldbetrag durch einen Punkt ersetzen
        String sumStr = String.valueOf(value).replace(",", ".");
        //Berechnung, welcher Betrag pro Kreditor anfaellt (Betrag dividiert durch die Anzahl der Debitoren)
        float valuePerPerson = value / debtors.length;
        //Den Betrag auf zwei Nachkommastellen runden und wieder in einen float-Wert umwandeln
        String valStr = GlobalVar.formatMoney(String.valueOf(valuePerPerson));
        float money = new Float(valStr);



        //Pro Debitor einen Payment-Datensatz erstellen und in der Payments-Liste speichern
        for (String debtor : debtors) {

            if (new Long(debtor) != creditor) {

                Payment payment = new Payment();
                payment.setDateTime(datetime);
                payment.setCreditorId(creditor);
                payment.setDebtorId(new Long(debtor));
                payment.setCategoryId(category);
                payment.setDetails(details);
                payment.setValue(money);
                payment.setMoneySum(new Float(sumStr));

                payments.add(payment);
            }
        }

        return payments;
    }

    /**
     * Die Methode fuert die Speicherung der uebergebenen Payment-Datensatze (Liste) in der Datenbank aus.
     * @param payments
     */
    private void addPaymentsToDb(ArrayList<Payment> payments) {

        if (payments != null) {

            for (Payment pay : payments) {

                //Datensatz in der Datenbank speichern
                GlobalVar.Database.insertPaymentDataset(pay);
            }

            //Activity bzw. Viewpager schliessen (zum Starbildschirm der App zurueck)
            buttonCancel.callOnClick();
        }
    }




    /**
     * Die Methode initialisiert die Variablen der SharedPreferences bzw. loescht deren gespeicherte Werte.
     */
    private void setSharedPreferencesEmpty() {

        SharedPreferences.Editor editor = vpElements.getSharedPreferences().edit();

        //Payment
        editor.putLong(GlobalVar.SpPaymentCreditor, 0);
        editor.putString(GlobalVar.SpPaymentDetails, "");
        editor.putStringSet(GlobalVar.SpPaymentDebtors, new HashSet<String>());
        editor.putLong(GlobalVar.SpPaymentCategory, 0);
        editor.putFloat(GlobalVar.SpPaymentMoneyValue, 0);


        //Repayment
        editor.putFloat(GlobalVar.SpRepaymentMoneySum, 0);
        editor.putLong(GlobalVar.SpRepaymentCreditor, 0);
        editor.putLong(GlobalVar.SpRepaymentDebtor, 0);
        editor.putString(GlobalVar.SpRepaymentDetails, "");


        editor.commit();
    }

    /**
     * Die Methode uebergibt dem Speicher-Button seine Layout-Einstellungen.
     */
    private void setButtonSaveLayout() {

        RelativeLayout.LayoutParams paramButtonSave = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramButtonSave.width = GlobalVar.Display_Width / 2;
        paramButtonSave.height = GlobalVar.Display_Height / 100 * 20;
        paramButtonSave.addRule(RelativeLayout.RIGHT_OF, buttonCancel.getId());
        paramButtonSave.addRule(RelativeLayout.BELOW, viewPager.getId());
        buttonSave.setLayoutParams(paramButtonSave);
    }

    /**
     *  Die Methode uebergibt dem Abbrechen-Button seine Layout-Einstellungen
     */
    private void setButtonCancelLayout() {

        RelativeLayout.LayoutParams paramButtonCancel = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramButtonCancel.width = GlobalVar.Display_Width / 2;
        paramButtonCancel.height = GlobalVar.Display_Height / 100 * 20;
        paramButtonCancel.addRule(RelativeLayout.BELOW, viewPager.getId());
        buttonCancel.setLayoutParams(paramButtonCancel);

    }

    /**
     * Die Methode uebergibt dem Viewpager sein Layout.
     */
    private void setViewpagerLayout() {

        RelativeLayout.LayoutParams paramsViewPager = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsViewPager.width = GlobalVar.Display_Width;
        paramsViewPager.height = GlobalVar.Display_Height / 100 * 80;
        viewPager.setLayoutParams(paramsViewPager);

    }


    //endregion


    //region Class ScreenSlidePagerAdapter fuer ViewPager

    /**
     * Die Klasse beinhaltet den PageAdapter des ViewPagers,
     * welcher das Aufrufen der einzelnen Seiten ermoeglicht.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Die Methode erstellt eine neue Seite fuer den ViewPager.
         * @param position Position der Seite im ViewPager
         * @return Seite im ViewPager
         */
        @Override
        public Fragment getItem(int position) {

            //Neue ViewPager-Seite
            Fragment_ViewPager fragment = new Fragment_ViewPager();

            //Layout der Seite zuwweisen
            if (type.equals(GlobalVar.typeRepayment)) {

                fragment.setLayoutId(ViewpagerElements.layouts_viewpager_repayment[position]);

            } else if (type.equals(GlobalVar.typePayment)) {

                fragment.setLayoutId(ViewpagerElements.layouts_viewpager_payment[position]);
            }

            //Typ uebergeben (Repayment oder Payment)
            fragment.setType(type);

            //Klasse mit den Viewpager-Elementen uebergeben
            fragment.setElements(vpElements);

            //Position der Seite im ViewPager mit uebergeben
            fragment.setPosition(position);

            return fragment;
        }

        /**
         * Die Methode gibt die Anzahl der Seiten im ViewPager zurueck.
         * @return Anzahl der Seiten
         */
        @Override
        public int getCount() {

            int count = 0;

            if (type.equals(GlobalVar.typeRepayment)) {

                count = ViewpagerElements.layouts_viewpager_repayment.length;

            } else if (type.equals(GlobalVar.typePayment)) {

                count = ViewpagerElements.layouts_viewpager_payment.length;
            }

            return count;
        }
    }

    //endregion

}
