package com.cgellner.yomm.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.MainCategory;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.Objects.RecyclerViewAdapter;
import com.cgellner.yomm.Objects.Transaction;
import com.cgellner.yomm.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL;


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

    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;



    private ArrayList<Person> personList;
    private ArrayList<MainCategory> mainCategoryList;

    private ArrayList<String> StrList;

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



        transaction = new Transaction();
        personList = GlobalVar.Database.getPersons();
        mainCategoryList = GlobalVar.Database.getCategories();


        setCarouselView();


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
    private void readData(){

        //Betrag
        EditText eTvalue = (EditText)view.findViewById(R.id.eTTransValue);
        String value = eTvalue.getText().toString();
        transaction.setValue(new Double(value));

        //Personen die gezahlt haben
        ListView personsPayed = (ListView)view.findViewById(R.id.listViewTransPersons);
        long[] checkedIds = personsPayed.getCheckedItemIds();


        //Personen fuer die der Betrag anfaellt



        //Kategorie



        //Details der Ausgabe



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

            if(position == 1){

                recyclerView = (RecyclerView)customView.findViewById(R.id.recyclerViewSecondLayout);

                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(llm);
                recyclerView.setHasFixedSize(true);
                setSecondLayout();
                setPersonListView();
            }


            return customView;
        }


    };

    private void setSecondLayout(){

         StrList = new ArrayList<String>();

        if(personList != null) {

            for (Person person : personList) {

                StrList.add(person.getName());
                Log.v(TAG, person.getName());
            }
        }

    }


    private void setPersonListView(){

        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerViewAdapter.setDataList(StrList);
        recyclerView.setAdapter(recyclerViewAdapter);



    }

}
