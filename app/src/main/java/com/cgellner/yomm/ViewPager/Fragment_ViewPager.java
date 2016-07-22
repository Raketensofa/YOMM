package com.cgellner.yomm.ViewPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cgellner.yomm.ViewPager.ViewpagerElements;

/**
 * Die Klasse stellt ein Fragment innerhalb eines ViewPagers dar.
 *
 * @author Carolin Gellner
 * @since 17.07.2016
 */
public class Fragment_ViewPager extends Fragment {

    //region Fields

    private View view;
    private int layoutId;
    private ViewpagerElements elements;
    private int position;
    private String type;

    //endregion


    //region Getter & Setter

    public void setType(String type) {
        this.type = type;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public void setElements(ViewpagerElements elements) {
        this.elements = elements;
    }

    //endregion


    //region Methods

    /**
     * Die Methode erstellt eine Ansicht im ViewPager.
     *
     * @param savedInstanceState
     * @return view : Ansicht im ViewPager
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(layoutId, container, false);

        if (type.equals("repayment")) {

            elements.setRepaymentViewpagerElements(view, position);

        } else if (type.equals("payment")) {

            elements.setPaymentViewPagerElements(view, position);
        }

        return view;
    }

    //endregion

}
