package com.cgellner.yomm.Fragments;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cgellner.yomm.Activities.Activity_TransactionList;
import com.cgellner.yomm.R;
import com.cgellner.yomm.Objects.TransactionItem;


public class TransactionDetailFragment extends Fragment {


    public static final String ARG_ITEM_ID = "item_id";


    private TransactionItem mItem;


    public TransactionDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            mItem = Activity_TransactionList.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();

            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {

                appBarLayout.setTitle(mItem.getId());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.transaction_detail, container, false);

        if (mItem != null) {

            ((TextView) rootView.findViewById(R.id.trancaction_detail_value)).setText(mItem.getValue());
            ((TextView) rootView.findViewById(R.id.transaction_detail_details)).setText(mItem.getDetails());
            ((TextView) rootView.findViewById(R.id.transaction_details_category)).setText(mItem.getCategory());
        }

        return rootView;
    }
}
