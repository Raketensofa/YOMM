package com.cgellner.yomm.ViewPager;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Category;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.R;

import java.util.ArrayList;
import java.util.Set;

/**
 * Die Klasse beinhaltet die Elemente der ViewPager-Seiten, welche die Formulare zum Erfassen einer Ausgabe oder Rueckzahlung repraesentieren.
 * Elemente stellen z.B. RadioGroups, RecyclerViews, etc. dar, die auf den einzelnen Seiten verwendet werden.
 * @@author Carolin Gellner
 * @since 17.07.2016
 */
public class ViewpagerElements {


    //region Layouts

    public static final int[] layouts_viewpager_payment =
                    {R.layout.layout_viewpager_payment_firstpage,
                    R.layout.layout_viewpager_payment_secondpage,
                    R.layout.layout_viewpager_payment_thirdpage,
                    R.layout.layout_viewpager_payment_fourthpage,
                    R.layout.layout_viewpager_payment_fifthpage};


    public static final int[] layouts_viewpager_repayment =
                    {R.layout.layout_viewpager_repayment_firstpage,
                    R.layout.layout_viewpager_repayment_secondpage,
                    R.layout.layout_viewpager_repayment_thirdpage,
                    R.layout.layout_viewpager_repayment_fourthpage,
            };


    //endregion


    //region Fields

    //Bestandteile der Views im Viewpager bei "Schulden begleichen" (Repayment)
    //RG: RadioGroup, LV: ListView, CB: Checkbox, TV: TextView, ET: EditText
    private RadioGroup repaymentFirstPageRG;
    private RadioGroup repaymentSecondPageRG = null;
    private TextView repaymentFourthPageTV = null;
    private EditText repaymentFourthPageET = null;


    //Bestandteile der Views im Viewpager bei "Neue Ausgabe" (Payment)
    //ET: EditText, RV: RecyclerView, RG: RadioGroup
    private EditText paymentFirstPageET = null;
    private RecyclerView paymentSecondPageRV = null;
    private RadioGroup paymentThirdPageRG = null;
    private RadioGroup paymentFourthPageRG = null;
    private EditText paymentFifthPageET = null;


    private SharedPreferences sharedPreferences = null;


    private ArrayList<Person> personsList;
    private ArrayList<Category> categoriesList;


    //endregion


    //region Getter & Setter

    /**
     * Getter fuer den Zwischenspeicher (Shared Preferences)
     * @return SharedPreferences / Zwischenspeicher in dem die gewaehlten Daten in den ViewPager-Seiten gespeichert werden
     */
    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }


    /**
     * Setter fuer den Zwischenspeichern (Shared Preferences)
     * @param sharedPreferences SharedPreferences / Zwischenspeicher in dem die gewaehlten Daten in den ViewPager-Seiten gespeichert werden
     */
    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    //endregion


    //region Constructor

    /**
     * Der Konstruktor erstellt eine neue Instanz der Klasse ViewpagerElements.
     * Bei der Instanzierung werden alle Personen und Kategorien aus der Datenbank abgefragt und zwischengespeichert.
     */
    public ViewpagerElements() {

        personsList = GlobalVar.Database.getPersons();
        categoriesList = GlobalVar.Database.getCategories();
    }

    //endregion


    //region Public Methods


    /**
     * Die Methode initialsiert die Elemente bzw. erstellt die Ansicht der Seite zur uebergebenen Position.
     * @param view Ansicht / Seite im ViewPager
     * @param position Position der Ansicht im ViewPager
     */
    public void setPaymentViewPagerElements(View view, int position) {

        if (position == 0) {

            //Elemente der ersten Seite des ViewPagers fuer die Erfassung einer Ausgabe (Payment)
            setTvAtFirstPageOfPay(view);

        } else if (position == 1) {

            //Elemente der zweiten Seite des ViewPagers fuer die Erfassung einer Ausgabe (Payment)
            setRvAtSecondPageOfPay(view);

        } else if (position == 2) {

            //Elemente der dritten Seite des ViewPagers fuer die Erfassung einer Ausgabe (Payment)
            setRbgAtThirdPageOfPay(view);

        } else if (position == 3) {

            //Elemente der vierten Seite des ViewPagers fuer die Erfassung einer Ausgabe (Payment)
            setRgbAtFourthPageOfPay(view);

        } else if (position == 4) {

            //Elemente der fuenften Seite des ViewPagers fuer die Erfassung einer Ausgabe (Payment)
            setEtAtFifthPageOfPay(view);
        }
    }


    /**
     * Die Methode initialsiert die Elemente bzw. erstellt die Ansicht der Seite zur uebergebenen Position.
     * @param view Ansicht / Seite im ViewPager
     * @param position Position der Ansicht im ViewPager
     */
    public void setRepaymentViewpagerElements(View view, int position) {

        if (position == 0) {

            //Elemente der ersten Seite des ViewPagers fuer die Erfassung einer Rueckzahlung (Repayment)
            setRbgAtFirstPageOfRepay(view);

        } else if (position == 1) {

            //Elemente der zweiten Seite des ViewPagers fuer die Erfassung einer Rueckzahlung (Repayment)
            setRbgAtSecondPageOfRepay(view);

        } else if (position == 2) {

            //Elemente der dritten Seite des ViewPagers fuer die Erfassung einer Rueckzahlung (Repayment)
            setEtAtThirdPageOfRepay(view);

        }else if(position == 3){

            //Elemente der vierten Seite des ViewPagers fuer die Erfassung einer Rueckzahlung (Repayment)
            setTvAtFourthPageOfRepay(view);
        }
    }

    //endregion


    //region Private Methods (Pages for Viewpager - Neue Ausgabe erfassen (Payment))

    /**
     * Die Methode initialsiert die Elemente der Ansicht auf der ersten Seite des ViewPagers bei der Erfassung einer Ausgabe (Payment)
     * @param view Ansicht der ersten Seite im ViewPager
     */
    private void setTvAtFirstPageOfPay(View view) {

        //Seitenanzahl anzeigen
        TextView page = (TextView)view.findViewById(R.id.layout_viewpager_payment_first_pagenumber);
        showNumberOfPage(1, layouts_viewpager_payment.length, page);


        //EditText zum Erfassen des Geldbetrages
         paymentFirstPageET = (EditText) view.findViewById(R.id.layout_viewpager_payment_first_editText);


        //Wenn schon ein Wert in den SharedPrefs gespeichert ist, dann dieses in der TextView anzeigen
        Float val = sharedPreferences.getFloat(GlobalVar.SpPaymentMoneyValue, 0);
        if (val > 0) {

            paymentFirstPageET.setText(String.valueOf(val));
        }


        //TextChangesListener
        paymentFirstPageET.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                Float value = 0f;

                //Eingetippten Betrag auslesen
                if (paymentFirstPageET.getText().toString().length() > 0) {

                    value = new Float(paymentFirstPageET.getText().toString());
                }

                //Betrag in den SharedPreferences zwischenspeichern
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat(GlobalVar.SpPaymentMoneyValue, value);
                editor.commit();


                //SharedPreferences auf Vollstaendigkeit ueberpruefen, um ggf. den Speicher-Button freigeben zu koennen
                checkPaymentData();

            }


            //diese beiden Methoden muessen implementiert sein, ansonsten wird von der Entwicklungsumgebung eine Fehlermeldung
            //angezeigt - sie werden aber eigentlich nicht benoetigt
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

    }

    /**
     * Die Methode initialsiert die Elemente der Ansicht auf der zweiten Seite des ViewPagers bei der Erfassung einer Ausgabe (Payment)
     * @param view Ansicht der zweiten Seite im ViewPager
     */
    private void setRvAtSecondPageOfPay(View view) {

        //Seitenanzahl anzeigen
        TextView page = (TextView)view.findViewById(R.id.layout_viewpager_payment_second_pagenumber);
        showNumberOfPage(2, layouts_viewpager_payment.length, page);


        //RecyclerView erstellen
        paymentSecondPageRV = (RecyclerView) view.findViewById(R.id.layout_viewpager_payment_second_recyclerview);
        paymentSecondPageRV.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        paymentSecondPageRV.setLayoutManager(llm);


        //Adapter erstellen und der RecyclerView zuweisen
        //Die RecylcerView zeigt alle verfuegbaren Personen an und der Benutzer kann dort die Debitoren auswaehlen
        RecyclerViewAdapter_CheckboxList recyclerViewAdapter = new RecyclerViewAdapter_CheckboxList();
        recyclerViewAdapter.setPersonDataList(personsList);
        recyclerViewAdapter.setSharedPreferences(sharedPreferences);
        paymentSecondPageRV.setAdapter(recyclerViewAdapter);

    }

    /**
     * Die Methode initialsiert die Elemente der Ansicht auf der dritten Seite des ViewPagers bei der Erfassung einer Ausgabe (Payment)
     * @param view Ansicht der dritten Seite im ViewPager
     */
    private void setRbgAtThirdPageOfPay(View view) {

        //Seitenanzahl anzeigen
        TextView page = (TextView)view.findViewById(R.id.layout_viewpager_payment_third_pagenumber);
        showNumberOfPage(3, layouts_viewpager_payment.length, page);

        //RadioGroup, in welcher aller verfuegbaren Personen gelistet sind und der Kreditor ausgewaehlt werden kann
        paymentThirdPageRG = (RadioGroup) view.findViewById(R.id.layout_viewpager_payment_third_radioGroupCreditor);
        paymentThirdPageRG.removeAllViews();

        long creditor = sharedPreferences.getLong(GlobalVar.SpPaymentCreditor, 0);

        for (Person person : personsList) {

            RadioButton radioButton = new RadioButton(view.getContext());
            radioButton.setText(person.getName());
            radioButton.setTextSize(30);
            radioButton.setId(new Integer(String.valueOf(person.getID())));

            //Falls bereits ein Creditor in den SharedPrefs hinterlegt ist, diesen RadioButton aktivieren
            if (creditor == person.getID()) {

                radioButton.setChecked(true);
            }

            paymentThirdPageRG.addView(radioButton);
        }


        //OnCheckedChangeListener der RadioButtonGroup
        paymentThirdPageRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                long personId = 0;
                for (Person person : personsList) {

                    if (person.getID() == new Long(checkedId)) {

                        personId = person.getID();
                    }
                }


                //ID der Person (Creditor) in den SharedPreferences zwischenspeichern
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong(GlobalVar.SpPaymentCreditor, personId);
                editor.commit();


                //SharedPreferences auf Vollstaendigkeit pruefen, um ggf. Speicher-Button freigeben zu koennen
                checkPaymentData();
            }
        });


    }

    /**
     * Die Methode initialsiert die Elemente der Ansicht auf der vierten Seite des ViewPagers bei der Erfassung einer Ausgabe (Payment)
     * @param view Ansicht der vierten Seite im ViewPager
     */
    private void setRgbAtFourthPageOfPay(View view) {

        //Seitenanzahl anzeigen
        TextView page = (TextView)view.findViewById(R.id.layout_viewpager_payment_fourth_pagenumber);
        showNumberOfPage(4, layouts_viewpager_payment.length, page);

        //RadioGoup in welcher alle verfuegbaren Kategorien angezeigt wird und der Benutzer
        //eine Kategorie auswaehlen kann
        paymentFourthPageRG = (RadioGroup) view.findViewById(R.id.layout_viewpager_payment_fourth_radioGroup_Category);
        paymentFourthPageRG.removeAllViews();

        long cat = sharedPreferences.getLong(GlobalVar.SpPaymentCategory, 0);


        for (Category category : categoriesList) {

            RadioButton radioButton = new RadioButton(view.getContext());
            radioButton.setText(category.getName());
            radioButton.setTextSize(25);
            radioButton.setId(new Integer(String.valueOf(category.getID())));


            //Falls bereits eine KategorieId in den SharedPrefs hinterlegt ist, den RadioButton aktivieren
            if (cat == category.getID()) {

                radioButton.setChecked(true);
            }

            paymentFourthPageRG.addView(radioButton);
        }

        //OnCheckedChangeListener der RadioButtonGroup
        paymentFourthPageRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                long categoryId = 0;
                for (Category category : categoriesList) {

                    if (category.getID() == new Long(checkedId)) {

                        categoryId = category.getID();
                    }
                }

                //ID der Kategorie in den SharedPreferences zwischenspeichern
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong(GlobalVar.SpPaymentCategory, categoryId);
                editor.commit();


                //SharedPreferences auf Vollstaendigkeit pruefen um ggf. Speicher-Button freigeben zu koennen
                checkPaymentData();

            }
        });


    }

    /**
     * Die Methode initialsiert die Elemente der Ansicht auf der fuenften Seite des ViewPagers bei der Erfassung einer Ausgabe (Payment)
     * @param view Ansicht der fuenften Seite im ViewPager
     */
    private void setEtAtFifthPageOfPay(View view) {

        //Seitenanzahl anzeigen
        TextView page = (TextView)view.findViewById(R.id.layout_viewpager_payment_fifth_pagenumber);
        showNumberOfPage(5, layouts_viewpager_payment.length, page);

        //EditText zum Erfassen von Bemerkungen
        paymentFifthPageET = (EditText) view.findViewById(R.id.layout_viewpager_payment_fifth_edittext_details);

        String detail = sharedPreferences.getString(GlobalVar.SpPaymentDetails, null);

        //Falls bereits eine Bemerkung in den SharedPrefs gespeichert ist, diese im EditText anzeigen
        if (detail.length() > 0) {

            paymentFifthPageET.setText(detail);
        }


        //TextChangedListenera
        paymentFifthPageET.addTextChangedListener(new TextWatcher() {


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Details aus dem EditText-Feld auslesen und in den SharedPreferences zwischenspeichern
                String value = paymentFifthPageET.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(GlobalVar.SpPaymentDetails, value);
                editor.commit();

                //SharedPreferences auf Vollstaendigkeit pruefen um ggf. den Speicher-Button freigeben zu koennen
                checkPaymentData();
            }


            //Diese Methoden mussten mit integriert werden, werden aber nicht gebraucht
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

        });
    }

    /**
     * Die Methode ueberprueft die Variablen, die in den SharedPreferences hinterlegt sind fuer ein Zahlungs-Datensatz, auf ihre Vollstaendigkeit.
     * Hat der Anwender einen einen einen Betrag, einen Creditor, ein oder mehrere Debitoren und eine Kategorie ausgewaehlt dann wird der Speichern-Button
     * freigeschaltet und in roter Farbe angezeigt
     */
    private void checkPaymentData() {

        //Zwischengespeicherte Daten abfragen
        Set debtors = sharedPreferences.getStringSet(GlobalVar.SpPaymentDebtors, null);
        Float value = sharedPreferences.getFloat(GlobalVar.SpPaymentMoneyValue, 0);
        Long creditor = sharedPreferences.getLong(GlobalVar.SpPaymentCreditor, 0);
        Long category = sharedPreferences.getLong(GlobalVar.SpPaymentCategory, 0);


        if (value != 0 && creditor != 0 && debtors.size() > 0 && category != 0) {

            //Button freischalten
            Activitiy_ViewPager.buttonSave.setBackgroundColor(Color.parseColor("#d50000"));
            Activitiy_ViewPager.buttonSave.setClickable(true);
            Activitiy_ViewPager.buttonSave.setEnabled(true);

        } else {

            //Button sperren
            Activitiy_ViewPager.buttonSave.setEnabled(false);
            Activitiy_ViewPager.buttonSave.setClickable(false);
            Activitiy_ViewPager.buttonSave.setBackgroundColor(Color.parseColor("#5a595b"));

        }
    }

    //endregion


    //region Private Methods (Pages for Viewpager - Schulden begleichen (Repayment)

    /**
     * Die Methode initialsiert die Elemente der Ansicht auf der erste Seite des ViewPagers bei der Erfassung einer Rueckzahlung (Repayment)
     * @param view Ansicht der ersten Seite im ViewPager
     */
    private void setRbgAtFirstPageOfRepay(View view) {

        //Seitenanzahl anzeigen
        TextView page = (TextView)view.findViewById(R.id.layout_viewpager_repayment_first_pagenumber);
        showNumberOfPage(1, layouts_viewpager_repayment.length, page);


        long debtor = sharedPreferences.getLong(GlobalVar.SpRepaymentDebtor, 0);

        repaymentFirstPageRG = (RadioGroup) view.findViewById(R.id.layout_viewpager_repayments_first_radioButtonGroup);
        repaymentFirstPageRG.removeAllViews();

        //Daten (Namen der Personen)
        if (personsList != null) {

            for (Person person : personsList) {

                for (Person secondPerson : personsList){

                    double debts = GlobalVar.getPaymentDifference(person.getID(), secondPerson.getID());

                    if(debts < 0){

                        RadioButton radioButton = new RadioButton(view.getContext());
                        radioButton.setText(person.getName());
                        radioButton.setTextSize(30);
                        radioButton.setId(new Integer(String.valueOf(person.getID())));

                        if (person.getID() == debtor) {

                            radioButton.setChecked(true);
                        }

                        repaymentFirstPageRG.addView(radioButton);

                        break;
                    }
                }
            }
        }

        //OnCheckedChangeListener
        repaymentFirstPageRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                //ID des Debitoren in den SharedPrefs speichern
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong(GlobalVar.SpRepaymentDebtor, checkedId);
                editor.commit();


                //Daten auf Vollstaendigkeit ueberpruefen
                checkRepaymentData();
            }
        });
    }

    /**
     * Die Methode initialsiert die Elemente der Ansicht auf der zweiten Seite des ViewPagers bei der Erfassung einer Rueckzahlung (Repayment)
     * @param view Ansicht der zweiten Seite im ViewPager
     */
    private void setRbgAtSecondPageOfRepay(View view) {

        //Seitenanzahl anzeigen
        TextView page = (TextView)view.findViewById(R.id.layout_viewpager_repayment_second_pagenumber);
        showNumberOfPage(2, layouts_viewpager_repayment.length, page);


        long debtor = sharedPreferences.getLong(GlobalVar.SpRepaymentDebtor, 0);
        long creditor = sharedPreferences.getLong(GlobalVar.SpRepaymentCreditor, 0);

        //RadioGroup zum Auswaehlen des Kreditors
        repaymentSecondPageRG = (RadioGroup) view.findViewById(R.id.layout_viewpager_repayment_second_radioButtonGroup);
        repaymentSecondPageRG.removeAllViews();

        if (debtor != 0) {

            for (Person person : personsList) {

                if (person.getID() != debtor) {

                    double debts = GlobalVar.getPaymentDifference(person.getID(), debtor);

                    if (debts > 0) {

                        RadioButton radioButton = new RadioButton(view.getContext());
                        radioButton.setText(person.getName());
                        radioButton.setTextSize(30);
                        radioButton.setId(new Integer(String.valueOf(person.getID())));


                        //Falls bereits eine KreditorId in den SharedPrefs hinterlegt ist, diesen RadioButton aktivieren
                        if (creditor == person.getID()) {

                            radioButton.setChecked(true);
                        }

                        repaymentSecondPageRG.addView(radioButton);
                    }
                }
            }


            //OnCheckedChangeListener der RadioGroup
            repaymentSecondPageRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    //ID des Kreditoren in den SharedPrefs speichern
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong(GlobalVar.SpRepaymentCreditor, checkedId);
                    editor.commit();

                    //Daten auf Vollstaendigkeit pruefen
                    checkRepaymentData();

                }
            });
        }
    }

    /**
     * Die Methode initialsiert die Elemente der Ansicht auf der dritten Seite des ViewPagers bei der Erfassung einer Rueckzahlung (Repayment)
     * @param view Ansicht der dritten Seite im ViewPager
     */
    private void setEtAtThirdPageOfRepay(View view){

        //Seitenanzahl anzeigen
        TextView page = (TextView)view.findViewById(R.id.layout_viewpager_repayment_third_pagenumber);
        showNumberOfPage(3, layouts_viewpager_repayment.length, page);

        //EditText zum Erfassen von Bemerkungen
        repaymentFourthPageET = (EditText) view.findViewById(R.id.layout_viewpager_repayment_third_details);

        String details = sharedPreferences.getString(GlobalVar.SpRepaymentDetails, "");
        if (details.length() > 0) {

            repaymentFourthPageET.setText(details);
        }


        //TextChangedListener
        repaymentFourthPageET.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                String details = repaymentFourthPageET.getText().toString();

                //Bemerkung in den SharedPreferences zwischenspeichern
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(GlobalVar.SpRepaymentDetails, details);
                editor.commit();

            }

            //diese beiden Methoden muessen implementiert sein, ansonsten wird von der Entwicklungsumgebung eine Fehlermeldung
            //angezeigt - sie werden aber eigentlich nicht benoetigt
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    /**
     * Die Methode initialsiert die Elemente der Ansicht auf der vierten Seite des ViewPagers bei der Erfassung einer Rueckzahlung (Repayment)
     * @param view Ansicht der vierten Seite im ViewPager
     */
    private void setTvAtFourthPageOfRepay(View view) {

        //Seitenanzahl anzeigen
        TextView page = (TextView)view.findViewById(R.id.layout_viewpager_repayment_fourth_pagenumber);
        showNumberOfPage(4, layouts_viewpager_repayment.length, page);

        //TextView zum Anzeigen der Geldsumme, die zureckgezahlt werden muss
        repaymentFourthPageTV = (TextView) view.findViewById(R.id.layout_viewpager_repayment_fourth_sum);

        long debtorId = sharedPreferences.getLong(GlobalVar.SpRepaymentDebtor, 0);
        long creditorId = sharedPreferences.getLong(GlobalVar.SpRepaymentCreditor, 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(creditorId != 0 && debtorId != 0) {

            double sum = GlobalVar.getPaymentDifference(debtorId, creditorId);
            repaymentFourthPageTV.setText(GlobalVar.formatMoney(String.valueOf(sum * -1)) + " Euro");

            editor.putFloat(GlobalVar.SpRepaymentMoneySum, new Float(sum * -1));

        }else{

            repaymentFourthPageTV.setText("0.00 Euro");
            editor.putFloat(GlobalVar.SpRepaymentMoneySum, 0.00f);

        }

        editor.commit();
        checkRepaymentData();
    }

    /**
     * Die Methode ueberprueft die Variablen, die in den SharedPreferences hinterlegt sind fuer ein Rueckzahlungs-Datensatz, auf ihre Vollstaendigkeit.
     * Hat der Anwender einen Debitor, einen Kreditor gewaehlt und die angezeigte Summe ist nicht 0, dann wird der Button
     * freigeschaltet und in roter Farbe angezeigt
     */
    private void checkRepaymentData() {

        //Zwischengespeicherte Daten abfragen
        long creditor = sharedPreferences.getLong(GlobalVar.SpRepaymentCreditor, 0);
        long debtor = sharedPreferences.getLong(GlobalVar.SpRepaymentDebtor, 0);
        float value = sharedPreferences.getFloat(GlobalVar.SpRepaymentMoneySum, 0);


        if (value > 0 && creditor != 0 && debtor != 0) {

            //Button freischalten und rot faerben
            Activitiy_ViewPager.buttonSave.setBackgroundColor(Color.parseColor("#d50000"));
            Activitiy_ViewPager.buttonSave.setClickable(true);
            Activitiy_ViewPager.buttonSave.setEnabled(true);

        } else {

            //Button sperren
            Activitiy_ViewPager.buttonSave.setEnabled(false);
            Activitiy_ViewPager.buttonSave.setClickable(false);
            Activitiy_ViewPager.buttonSave.setBackgroundColor(Color.parseColor("#5a595b"));

        }
    }

    //endregion


    /**
     * Zeitgt die aktuelle Seitenanzahl in einer TextView an.
     * @param position Position der Seite im ViewPager
     * @param sumOfPages Gesamtanzahl der Seiten im ViewPager
     * @param textView TextView in welcher die Seitenanzahl angezeigt werden soll
     */
    private void showNumberOfPage(int position, int sumOfPages, TextView textView){

        textView.setText(String.valueOf(position + " / " + sumOfPages));
    }


}
