package com.cgellner.yomm.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cgellner.yomm.DialogFragments.DF_Setttings_AddNew;
import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Category;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.R;

import java.util.ArrayList;


public class Activity_Settings extends AppCompatActivity {

    private final String TAG = Activity_Settings.class.getName();


    private  Object object;
    private  ArrayList<Person> listPersons;
    private  ArrayList<Category> listCategories;


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

        }else if(ob.equals(Category.class.getName())){

            object = Category.class;
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

            } else if (object == Category.class) {

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


        }else if(listCategories != null && object == Category.class){

            for (Category cat : listCategories) {

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

        }else if(object == Category.class){

            button.setText("Neue Kategorie");
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DF_Setttings_AddNew newData = new DF_Setttings_AddNew();
                newData.setActivity(Activity_Settings.this);
                newData.show(getFragmentManager(), object.toString());
            }
        });
    }
}
