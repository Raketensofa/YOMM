package com.cgellner.yomm.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cgellner.yomm.Activities.MainActivity;
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
import java.util.zip.Inflater;

import javax.microedition.khronos.opengles.GL;


/**
 * Created by Carolin on 31.05.2016.
 */

public class DF_NewTrans extends Fragment {

    private final String TAG = DF_NewTrans.class.getName();

    int[] views = {R.layout.layout_new_first_whichvalue,
                    R.layout.layout_new_second_persons,
                    R.layout.layout_new_third_personpayed,
                    R.layout.layout_new_fourth_category,
                    R.layout.layout_new_fifth_object};

    View view;

    private int layoutId;

    Transaction transaction;

    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;


    private EditText eTValue;
    private Button buttonSaveData;

    private MainActivity mainActivity;

    @Override
    public MainActivity getContext() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    private ArrayList<Person> personList;
    private ArrayList<MainCategory> mainCategoryList;

    private ArrayList<String> StrList;


    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    /**
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        ViewGroup rootView = (ViewGroup) inflater.inflate(getLayout(), container, false);

        transaction = new Transaction();

        //Personen und Kategorien aus der Datenbank holen
        personList = GlobalVar.Database.getPersons();
        mainCategoryList = GlobalVar.Database.getCategories();


        if(layoutId == 4) {

            buttonSaveData = (Button) rootView.findViewById(R.id.buttonSaveTrans);
            buttonSaveData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mainActivity.readData();

                }
            });

        }

        return rootView;

    }




    private ViewListener viewListener = new ViewListener() {

        @Override
        public View setViewForPosition(int position) {


            if(position == 1){

                View view = getView().findViewById(views[1]);
                recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewSecondLayout);
                recyclerView.setHasFixedSize(true);

                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(llm);
                recyclerView.setHasFixedSize(true);
                setSecondLayout();
                setPersonListView();
            }


            return view;
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


    private int getLayout(){

        Log.d("LAYOUTID", String.valueOf(layoutId));

        int layout = 0;

        if(layoutId == 0){

            layout = views[0];

        }else if(layoutId == 1){

            layout = views[1];

        }else if(layoutId == 2){

            layout = views[2];

        }else if(layoutId == 3){

            layout = views[3];

        }else if(layoutId == 4){

            layout = views[4];
        }

        return layout;

    }

}
