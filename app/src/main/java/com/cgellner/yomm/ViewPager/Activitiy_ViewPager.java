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
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Pay;
import com.cgellner.yomm.Objects.Payment;
import com.cgellner.yomm.R;
import com.cgellner.yomm.Start.MainActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class Activitiy_ViewPager extends AppCompatActivity {


    //region Fields

    private ViewPager viewPager;
    private String type;
    private ViewpagerElements vpElements;

    public static Button buttonSave;
    public static Button buttonCancel;

    //endregion


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_activitiy_viewpager);

        type = getIntent().getStringExtra("type");

        setActivityTitle();

        vpElements = new ViewpagerElements();

        setViewPager();

        setButtons();

        initSharedPreferences();

    }


    //region Private Methods

    /**
     *
     */
    private void setActivityTitle() {

        if (type.equals(GlobalVar.typePayment)) {

            this.setTitle("Neue Ausgabe");

        } else if (type.equals(GlobalVar.typeRePpayment)) {

            this.setTitle("Rückzahlung");
        }
    }


    /**
     *
     */
    private void initSharedPreferences() {

        //Zischenspeicher fuer eingegebene Daten

        vpElements.setSharedPreferences(PreferenceManager.getDefaultSharedPreferences(this));
        //Leere Variablen in den SharedPreferences anlegen
        setSharedPreferencesEmpty();

    }


    /**
     *
     */
    private void setButtons() {

        buttonCancel = (Button) findViewById(R.id.button_viewpager_cancel);
        buttonSave = (Button) findViewById(R.id.button_viewpager_save);

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
     *
     */
    private void setViewPager() {

        viewPager = (ViewPager) findViewById(R.id.activity_viewpager);
        setViewpagerLayout();
        PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                if (type.equals(GlobalVar.typeRePpayment)) {

                    vpElements.setRepaymentViewpagerElements(viewPager, position);

                } else if (type.equals(GlobalVar.typePayment)) {

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
     *
     */
    private void setButtonSaveClickListener() {

        if (buttonSave != null) {

            buttonSave.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (type.equals(GlobalVar.typeRePpayment)) {

                        saveRepayment();

                    } else if (type.equals(GlobalVar.typePayment)) {

                        savePayment();
                    }

                    //Viewpager schliessen
                    buttonCancel.callOnClick();

                }
            });
        }
    }


    /**
     *
     */
    private void savePayment() {

        ArrayList<Payment> payments = preparePaymentDatasets();

        addPaymentsToDb(payments);

    }


    /**
     *
     */
    private void saveRepayment() {

        long creditorId = vpElements.getSharedPreferences().getLong(GlobalVar.SpRepaymentCreditor, 0);
        long debtorId = vpElements.getSharedPreferences().getLong(GlobalVar.SpRepaymentDebtor, 0);
        float value = vpElements.getSharedPreferences().getFloat(GlobalVar.SpRepaymentMoneySum, 0);


        Pay repayment = new Pay();
        repayment.setCreditorId(creditorId);
        repayment.setDebtorId(debtorId);
        repayment.setValue(value);
        repayment.setDetails("");
        repayment.setDateTime(new Date());


        GlobalVar.Database.insertRepayment(repayment);

        Toast.makeText(this, "Gespeichert", Toast.LENGTH_LONG).show();

    }


    /**
     * @return
     */
    private ArrayList<Payment> preparePaymentDatasets() {


        //aktuelles Datum und Uhrzeit ermitteln
        Date currentTimestamp = new Date();


        //SharedPreferences auslesen...

        //Geldbetrag
        float value = vpElements.getSharedPreferences().getFloat(GlobalVar.SpPaymentMoneyValue, 0);

        //Details
        String details = vpElements.getSharedPreferences().getString(GlobalVar.SpPaymentDetails, "");

        //Debitoren
        String debtors = "";
        Set<String> persons = vpElements.getSharedPreferences().getStringSet(GlobalVar.SpPaymentDebtors, null);
        Object[] ar = persons.toArray();

        for (int i = 0; i < ar.length; i++) {

            if (i < ar.length - 1) {

                debtors += ar[i].toString() + ",";

            } else if (i == ar.length - 1) {

                debtors += ar[i].toString();
            }
        }

        //Kreditor
        long creditor = vpElements.getSharedPreferences().getLong(GlobalVar.SpPaymentCreditor, 0);

        //Kategorie
        long category = vpElements.getSharedPreferences().getLong(GlobalVar.SpPaymentCategory, 0);


        //Zahlungsdatensatz pro betreffende Person (Debtors) erzeugen
        ArrayList<Payment> payments = createPaymentDatasets(currentTimestamp, value, creditor, debtors, category, details);


        return payments;

    }


    /**
     *
     * @param datetime
     * @param value
     * @param creditor
     * @param debtors
     * @param category
     * @param details
     * @return
     */
    private ArrayList<Payment> createPaymentDatasets(Date datetime, float value, long creditor, String debtors, long category, String details) {

        ArrayList<Payment> payments = new ArrayList<>();

        String sumStr = String.valueOf(value).replace(",", ".");

        String[] debtorIds = debtors.split(",");

        float valuePerPerson = value / debtorIds.length;
        String valStr = GlobalVar.formatMoney(String.valueOf(valuePerPerson)).replace(",", ".");
        float money = new Float(valStr);


        for (String debtor : debtorIds) {

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
     * @param payments
     */
    private void addPaymentsToDb(ArrayList<Payment> payments) {

        if (payments != null) {

            for (Payment pay : payments) {

                GlobalVar.Database.insertPaymentDataset(pay);
            }

            buttonCancel.callOnClick();
        }
    }


    /**
     *
     */
    private void setButtonCancelClickListener() {

        if (buttonCancel != null) {

            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setSharedPreferencesEmpty();
                    onBackPressed();
                }
            });
        }
    }


    /**
     *
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
     *
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
     *
     */
    private void setButtonCancelLayout() {

        RelativeLayout.LayoutParams paramButtonCancel = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramButtonCancel.width = GlobalVar.Display_Width / 2;
        paramButtonCancel.height = GlobalVar.Display_Height / 100 * 20;
        paramButtonCancel.addRule(RelativeLayout.BELOW, viewPager.getId());
        buttonCancel.setLayoutParams(paramButtonCancel);

    }


    /**
     *
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
     *
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment_ViewPager fragment = new Fragment_ViewPager();

            if (type.equals(GlobalVar.typeRePpayment)) {

                fragment.setLayoutId(ViewpagerElements.layouts_viewpager_repayment[position]);

            } else if (type.equals(GlobalVar.typePayment)) {

                fragment.setLayoutId(ViewpagerElements.layouts_viewpager_payment[position]);
            }

            fragment.setType(type);
            fragment.setElements(vpElements);
            fragment.setPosition(position);

            return fragment;
        }


        @Override
        public int getCount() {

            int count = 0;

            if (type.equals(GlobalVar.typeRePpayment)) {

                count = ViewpagerElements.layouts_viewpager_repayment.length;

            } else if (type.equals(GlobalVar.typePayment)) {

                count = ViewpagerElements.layouts_viewpager_payment.length;
            }

            return count;
        }
    }

    //endregion

}
