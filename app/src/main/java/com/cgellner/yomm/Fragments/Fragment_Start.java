package com.cgellner.yomm.Fragments;



import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cgellner.yomm.Activities.Activity_AddNewData;
import com.cgellner.yomm.Activities.Settings;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Start extends Fragment {


    private final String TAG = Fragment_Start.class.getName();


    private View rootView;
    private Button buttonCleanDebts;
    private Button buttonNewTrans;



    private ViewPager viewPager;
    private FragmentStatePagerAdapter pagerAdapter;
    private static final int NUM_PAGES = 5;

    public Fragment_Start() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.content_main3, container, false);

        addListenerOnButton();

        return rootView;
    }


    /**
     *
     * @return
    /**
     *
     */
    public void addListenerOnButton() {

        buttonCleanDebts = (Button) rootView.findViewById(R.id.buttonClearDebts);
        buttonCleanDebts.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                //Fragment_NewTransaction newTrans = new Fragment_NewTransaction();
                //newTrans.show(fm, "cleanDebts");

            }
        });


        buttonNewTrans = (Button)rootView.findViewById(R.id.buttonNewTrans);
        buttonNewTrans.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                viewPager = (ViewPager)getActivity().findViewById(R.id.viewpager);
                pagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
                viewPager.setAdapter(pagerAdapter);




            }
        });


    }

    /**
     *
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {


            android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

            Fragment_NewTransaction newTrans = new Fragment_NewTransaction();
            newTrans.setLayoutId(position);


            transaction.add(position, newTrans);
            transaction.addToBackStack(null);
            //transaction.commit();

            //newTrans.setMainActivity();




            return newTrans;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }




    }


}
