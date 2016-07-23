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
import com.cgellner.yomm.Objects.Payment;
import com.cgellner.yomm.R;

import java.util.ArrayList;
import java.util.Set;

/**
 * Die Klasse beinhaltet die die Elemente der ViewPager-Seiten.
 *
 * @@author Carolin Gellner
 * @since 17.07.2016
 */
public class ViewpagerElements {


    //region Fields


    public static final int[] layouts_viewpager_payment =
            {R.layout.layout_viewpager_payment_firstpage,
                    R.layout.layout_viewpager_payment_secondpage,
                    R.layout.layout_viewpager_payment_thirdpage,
                    R.layout.layout_viewpager_payment_fourthpage,
                    R.layout.layout_viewpager_payment_fifthpage};


    public static final int[] layouts_viewpager_repayment =
            {R.layout.layout_viewpager_repayment_firstpage,
                    R.layout.layout_viewpager_repayment_secondpage,
                    R.layout.layout_viewpager_repayment_thirdpage
            };


    //Bestandteile der Views im Viewpager bei "Schulden begleichen" (Repayment)
    //RG: RadioGroup, LV: ListView, CB: Checkbox, TV: TextView
    private RadioGroup repaymentFirstPageRG;
    private RadioGroup repaymentSecondPageRG = null;
    private TextView repaymentThirdPageTV = null;


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
    private ArrayList<Payment> openPaymentsList;


    //endregion


    //region Getter & Setter

    /**
     * Getter fuer die Liste mit allen offenen Zahlungen
     *
     * @return Liste mit allen offenen Zahlungen zu den betreffenden Personen
     */
    public ArrayList<Payment> getOpenPaymentList() {
        return openPaymentsList;
    }

    /**
     * Getter fuer den Zwischenspeichern (Shared Preferences)
     *
     * @return SharedPreferences / Zwischenspeicher in dem die gewaehlten Daten in den ViewPager-Seiten gespeichert werden
     */
    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    /**
     * Setter fuer den Zwischenspeichern (Shared Preferences)
     *
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
     * @param view
     * @param position
     */
    public void setPaymentViewPagerElements(View view, int position) {

        if (position == 0) {

            setTvAtFirstPageOfPay(view);

        } else if (position == 1) {

            setRvAtSecondPageOfPay(view);

        } else if (position == 2) {

            setRbgAtThirdPageOfPay(view);

        } else if (position == 3) {

            setRgbAtFourthPageOfPay(view);

        } else if (position == 4) {

            setEtAtFifthPageOfPay(view);
        }
    }


    /**
     * @param view     View des Fragments im ViewPager
     * @param position Position der View im ViewPager
     */
    public void setRepaymentViewpagerElements(View view, int position) {

        if (position == 0) {

            //Radio Button Group welche die Personennamen beinhaltet der ersten ViewpagerSeite
            setRbgAtFirstPageOfRepay(view);

        } else if (position == 1) {

            //Radio Button Group welche die Personennamen beinhaltet der zweiten ViewpagerSeite
            setRbgAtSecondPageOfRepay(view);

        } else if (position == 2) {

            //TextView in welcher der Gesamtbetrag auf Seite 4 angezeigt wird
            setTvAtThirdPageOfRepay(view);

        }
    }

    //endregion


    //region Private Methods (Pages for Viewpager - Schulden begleichen (Repayment)


    /**
     * @param view Seite im Viewpager
     */
    private void setRbgAtFirstPageOfRepay(View view) {

        long debtor = sharedPreferences.getLong(GlobalVar.SpRepaymentDebtor, 0);

        repaymentFirstPageRG = (RadioGroup) view.findViewById(R.id.layout_viewpager_cleardebts_first_radioButtonGroup);

        repaymentFirstPageRG.removeAllViews();

        //Daten
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

        //Eventhandler
        repaymentFirstPageRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong(GlobalVar.SpRepaymentDebtor, checkedId);
                editor.commit();


                checkRepaymentData();
            }
        });
    }

    /**
     * @param view Eine Seite im Viewpager
     */
    private void setRbgAtSecondPageOfRepay(View view) {

        long debtor = sharedPreferences.getLong(GlobalVar.SpRepaymentDebtor, 0);
        long creditor = sharedPreferences.getLong(GlobalVar.SpRepaymentCreditor, 0);

        repaymentSecondPageRG = (RadioGroup) view.findViewById(R.id.layout_viewpager_cleardebts_second_radioButtonGroup);
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

                        if (creditor == person.getID()) {

                            radioButton.setChecked(true);
                        }

                        repaymentSecondPageRG.addView(radioButton);
                    }
                }
            }


            //OnChecked-Event
            repaymentSecondPageRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong(GlobalVar.SpRepaymentCreditor, checkedId);
                    editor.commit();

                    checkRepaymentData();

                }
            });
        }
    }


    /**
     * @param view Seite vom Viewpager
     */
    private void setTvAtThirdPageOfRepay(View view) {

        repaymentThirdPageTV = (TextView) view.findViewById(R.id.layout_viewpager_repayment_fourth_sum);

        long debtorId = sharedPreferences.getLong(GlobalVar.SpRepaymentDebtor, 0);
        long creditorId = sharedPreferences.getLong(GlobalVar.SpRepaymentCreditor, 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(creditorId != 0 && debtorId != 0) {

            double sum = GlobalVar.getPaymentDifference(debtorId, creditorId);
            repaymentThirdPageTV.setText(GlobalVar.formatMoney(String.valueOf(sum * -1)) + " Euro");

            editor.putFloat(GlobalVar.SpRepaymentMoneySum, new Float(sum * -1));

        }else{

            repaymentThirdPageTV.setText("0.00 Euro");
            editor.putFloat(GlobalVar.SpRepaymentMoneySum, 0.00f);

        }

        editor.commit();
        checkRepaymentData();
    }

    /**
     * Die Methode ueberprueft die Variablen, die in den SharedPreferences hinterlegt sind fuer ein Rueckzahlungs-Datensatz, auf ihre Vollstaendigkeit.
     * Hat der Anwender einen Debitor, einen Kreditor und offene Zahlungen ausgewaehlt dann wird der Speichern-Button
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


    //region Private Methods (Pages for Viewpager - Neue Ausgabe erfassen (Payment))

    /**
     * @param view
     */
    private void setTvAtFirstPageOfPay(View view) {

        paymentFirstPageET = (EditText) view.findViewById(R.id.layout_viewpager_newTrans_first_editText);

        Float val = sharedPreferences.getFloat(GlobalVar.SpPaymentMoneyValue, 0);

        if (val > 0) {

            paymentFirstPageET.setText(String.valueOf(val));
        }

        paymentFirstPageET.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                Float value = 0f;

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

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

    }

    /**
     * @param view
     */
    private void setRvAtSecondPageOfPay(View view) {


        paymentSecondPageRV = (RecyclerView) view.findViewById(R.id.layout_viewpager_newTrans_second_recyclerview_debtors);
        paymentSecondPageRV.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        paymentSecondPageRV.setLayoutManager(llm);


        //Adapter erstellen und der RecyclerView zuweisen
        RecyclerViewAdapter_CheckboxList recyclerViewAdapter = new RecyclerViewAdapter_CheckboxList();
        recyclerViewAdapter.setPersonDataList(personsList);
        recyclerViewAdapter.setSharedPreferences(sharedPreferences);
        paymentSecondPageRV.setAdapter(recyclerViewAdapter);

    }

    /**
     * @param view
     */
    private void setRbgAtThirdPageOfPay(View view) {

        paymentThirdPageRG = (RadioGroup) view.findViewById(R.id.layout_viewpager_newTrans_third_radioGroupCreditor);
        paymentThirdPageRG.removeAllViews();

        long creditor = sharedPreferences.getLong(GlobalVar.SpPaymentCreditor, 0);

        for (Person person : personsList) {

            RadioButton radioButton = new RadioButton(view.getContext());
            radioButton.setText(person.getName());
            radioButton.setTextSize(35);
            radioButton.setId(new Integer(String.valueOf(person.getID())));

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
     * @param view
     */
    private void setRgbAtFourthPageOfPay(View view) {

        paymentFourthPageRG = (RadioGroup) view.findViewById(R.id.layout_viewpager_newTrans_fourth_radioGroup_Category);
        paymentFourthPageRG.removeAllViews();

        long cat = sharedPreferences.getLong(GlobalVar.SpPaymentCategory, 0);


        for (Category category : categoriesList) {

            RadioButton radioButton = new RadioButton(view.getContext());
            radioButton.setText(category.getName());
            radioButton.setTextSize(35);
            radioButton.setId(new Integer(String.valueOf(category.getID())));

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
     * @param view
     */
    private void setEtAtFifthPageOfPay(View view) {

        paymentFifthPageET = (EditText) view.findViewById(R.id.layout_viewpager_newtrans_fifth_edittext_details);

        String detail = sharedPreferences.getString(GlobalVar.SpPaymentDetails, null);

        if (detail.length() > 0) {

            paymentFifthPageET.setText(detail);
        }


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

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

        });
    }


    //endregion

}
