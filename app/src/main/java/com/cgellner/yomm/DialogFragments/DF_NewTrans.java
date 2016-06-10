package com.cgellner.yomm.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.cgellner.yomm.Objects.Transaction;
import com.cgellner.yomm.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;


/**
 * Created by Carolin on 31.05.2016.
 */
public class DF_NewTrans extends DialogFragment {

    private final String TAG = DF_NewTrans.class.getName();


    CarouselView carouselView;
    int[] carouselViews = {R.layout.layout_new_first_whichvalue,
                    R.layout.layout_new_second_persons,
                    R.layout.layout_new_third_personpayed,
                    R.layout.layout_new_fourth_category,
                    R.layout.layout_new_fifth_object};

    View view;

    Transaction transaction;





    /**
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        view = inflater.inflate(R.layout.layout_carouselview, null);
        dialogBuilder.setView(view);

        setCarouselView();

        transaction = new Transaction();

        //Abbrechen-Button
        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                DF_NewTrans.this.getDialog().cancel();
            }
        });




       return dialogBuilder.create();

    }


    /**
     *
     * @return
     */
    private Transaction readData(){

        Transaction trans = new Transaction();

        //FELDER AUSLESEN
        //..............................




        return trans;
    }



    /**
     *
     */
    private void setCarouselView(){

        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        carouselView.setPageCount(carouselViews.length);
        carouselView.setViewListener(viewListener);
        carouselView.reSetSlideInterval(0);

    }





    private ViewListener viewListener = new ViewListener() {

        @Override
        public View setViewForPosition(int position) {

            View customView = getActivity().getLayoutInflater().inflate(carouselViews[position], null);

            if(position == 0){

               // setFirstLayout(customView);
            }


            return customView;
        }


    };


    private void setFirstLayout(View view){


        /**
        TextView textViewAdd = (TextView)view.findViewById(R.id.tVAnswerTransTypeAdd);
        TextView textViewSub = (TextView)view.findViewById(R.id.tVAnswerTransTypeSub);
        TextView textViewChange = (TextView)view.findViewById(R.id.tVAnswerTransTypeChange);

        textViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                transaction.setType(1);
                carouselView.setCurrentItem(1);

            }
        });

        textViewSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                transaction.setType(2);

            }
        });

        textViewChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                transaction.setType(3);

            }
        });

        */

    }









}
