package com.cgellner.yomm.OverviewPayments;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cgellner.yomm.R;


public class Fragment_PaymentDetail extends Fragment {


    public static final String ARG_ITEM_ID = "item_id";


    private PaymentItem mItem;


    public Fragment_PaymentDetail() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            mItem = Activity_PaymentsList.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

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

        View rootView = inflater.inflate(R.layout.pay_detail, container, false);

        if (mItem != null) {

            ((TextView) rootView.findViewById(R.id.payment_detail_value)).setText(mItem.getValue());
            ((TextView) rootView.findViewById(R.id.payment_detail_details)).setText(mItem.getDetails());
            ((TextView) rootView.findViewById(R.id.payment_details_category)).setText(mItem.getCategory());
            ((TextView) rootView.findViewById(R.id.payment_detail_debtor)).setText(mItem.getDebtor());
            ((TextView) rootView.findViewById(R.id.payment_detail_creditor)).setText(mItem.getCreditor());
            ((TextView) rootView.findViewById(R.id.payment_detail_sum)).setText(mItem.getMainMoneyValue());
            ((TextView) rootView.findViewById(R.id.payment_detail_date)).setText(mItem.getDate());
            ((TextView) rootView.findViewById(R.id.payment_detail_time)).setText(mItem.getTime());
        }

        return rootView;
    }
}
