package com.cgellner.yomm.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.cgellner.yomm.Activities.Activitiy_ViewPager;
import com.cgellner.yomm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Start extends Fragment {


    private final String TAG = Fragment_Start.class.getName();

    private Button buttonCleanDebts;
    private Button buttonNewTrans;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_main3, container, false);

        buttonCleanDebts = (Button) view.findViewById(R.id.buttonClearDebts);
        buttonNewTrans = (Button)view.findViewById(R.id.buttonNewTrans);
        addListenerOnButton();

        return view;
    }


    /**
     *
     * @return
    /**
     *
     */
    public void addListenerOnButton() {

        if (buttonCleanDebts != null) {

            buttonCleanDebts.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(), Activitiy_ViewPager.class);
                    intent.putExtra("type", "clean");
                    startActivity(intent);
                }
            });

        }

        if (buttonNewTrans != null) {

            buttonNewTrans.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(), Activitiy_ViewPager.class);
                    intent.putExtra("type", "new");
                    startActivity(intent);

                }
            });
        }
    }

}
