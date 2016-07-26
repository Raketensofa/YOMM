package com.cgellner.yomm.Start;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.cgellner.yomm.ViewPager.Activitiy_ViewPager;
import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.R;

/**
 * Die Klasse beinhaltet das Fragment, welches die Startseite der App darstellt.
 * @author Carolin Gellner
 */
public class Fragment_Start extends Fragment {


    //region Fields

    private Button buttonRepayment;
    private Button buttonPayment;

    //endregion


    //region Public Methods

    /**
     * Die Methode erstellt das Fragment.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_main3, container, false);

        buttonRepayment = (Button) view.findViewById(R.id.buttonRepayment);
        buttonPayment = (Button) view.findViewById(R.id.buttonPayment);

        addListenerOnButton();

        return view;
    }


    //endregion


    //region Private Method

    /**
     * Die Methode uebergibt den beiden Buttons im Fragment deren OnClickListener.
     */
    private void addListenerOnButton() {

        //Button zum Oeffnen eines Formulars (ViewPager) zum Erfassen einer neuen Ausgabe
        if (buttonPayment != null) {

            buttonPayment.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    //ViewPager starten zum Erfassen einer neuen Ausgabe (Payment)
                    Intent intent = new Intent(getActivity(), Activitiy_ViewPager.class);
                    intent.putExtra("type", GlobalVar.typePayment);
                    startActivity(intent);
                }
            });
        }


        //Button zum Oeffnen eines Formulars (ViewPager) zum Erfassen einer neuen Rueckzahlung
        if (buttonRepayment != null) {

            buttonRepayment.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    //ViewPager starten zum Erfassen einer neuen Rueckzahlung (Repayment)
                    Intent intent = new Intent(getActivity(), Activitiy_ViewPager.class);
                    intent.putExtra("type", GlobalVar.typeRepayment);
                    startActivity(intent);
                }
            });
        }
    }

    //endregion

}
