package com.cgellner.yomm.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cgellner.yomm.DialogFragments.DF_AddNewData;
import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.MainCategory;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.R;

import java.util.ArrayList;


public class Settings extends AppCompatActivity {

    private  Object object;
    private  ArrayList<Person> listPersons;
    private  ArrayList<MainCategory> listCategories;


    private  Button button;
    private  ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        button = (Button)findViewById(R.id.settings_button_new);
        listview = (ListView)findViewById(R.id.settings_listview);

        setListView();
        setButton();

    }

    /**
     *
     */
    private  void setObject(){

       Intent intent = getIntent();

        String ob = intent.getStringExtra("object");

        if(ob.equals(Person.class.getName())){

            object = Person.class;

        }else if(ob.equals(MainCategory.class.getName())){

            object = MainCategory.class;
        }

    }


    /**
     *
     */
    private void getObjects(){

        if(object != null) {

            if (object == Person.class) {

                listPersons = new ArrayList<>();
                listPersons = GlobalVar.Database.getPersons();

            } else if (object == MainCategory.class) {

                listCategories = new ArrayList<>();
                listCategories = GlobalVar.Database.getCategories();
            }
        }
    }

    /**
     *
     */
    public void setListView(){

        setObject();

        getObjects();

        ArrayList<String> list = getNames();

        if(list.size() > 0) {

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    R.layout.layout_settings_listview,
                    R.id.listview_settings_item,
                    list);


            listview.setAdapter(adapter);

        }
    }


    /**
     *
     * @return
     */
    private ArrayList<String> getNames() {

        ArrayList<String> list = new ArrayList<>();

        if(listPersons != null && object == Person.class){

            for (Person per : listPersons) {

                list.add(per.getName());
            }


        }else if(listCategories != null && object == MainCategory.class){

            for (MainCategory  cat : listCategories) {

                list.add(cat.getName());
            }
        }

        return list;

    }

    /**
     *
     */
    private void setButton(){

        if(object == Person.class){

            button.setText("Neue Person");

        }else if(object == MainCategory.class){

            button.setText("Neue Kategorie");
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DF_AddNewData newData = new DF_AddNewData();
                newData.setActivity(Settings.this);
                newData.show(getFragmentManager(), object.toString());
            }
        });
    }
}
