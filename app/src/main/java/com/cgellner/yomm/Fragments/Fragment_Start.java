package com.cgellner.yomm.Fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cgellner.yomm.Activities.MainActivity;
import com.cgellner.yomm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Start extends Fragment {


    private final String TAG = Fragment_Start.class.getName();


    private View rootView;
    private Button buttonCleanDebts;
    private Button buttonNewTrans;


    android.support.v4.app.FragmentTransaction transaction;

    private ViewPager viewPager;
    private FragmentStatePagerAdapter pagerAdapter;
    private static final int NUM_PAGES = 5;

    public Fragment_Start() {
        // Required empty public constructor
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public FragmentStatePagerAdapter getPagerAdapter() {
        return pagerAdapter;
    }

    public void setPagerAdapter(FragmentStatePagerAdapter pagerAdapter) {
        this.pagerAdapter = pagerAdapter;
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




                MainActivity.fragmentTransaction = getFragmentManager().beginTransaction();

                viewPager = (ViewPager)getActivity().findViewById(R.id.viewpager);
                pagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());

                viewPager.setAdapter(pagerAdapter);

                MainActivity.fragmentTransaction.commit();


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


            Fragment_NewTransaction newTrans = new Fragment_NewTransaction();
            newTrans.setLayoutId(position);

            MainActivity.fragmentTransaction.attach(newTrans);
        //            MainActivity.fragmentTransaction.addToBackStack(null);

            return newTrans;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }




    }


}
