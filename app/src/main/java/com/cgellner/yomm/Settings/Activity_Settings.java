package com.cgellner.yomm.Settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Category;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.R;

import java.util.ArrayList;

/**
 * Die Klasse stellt eine Activity dar, in welcher Elemente wie Personen oder Kategorien verwaltet werden koennen.
 * @author Carolin Gellner
 */
public class Activity_Settings extends AppCompatActivity {


    //region Fields

    private  Object object;
    private  ArrayList<Person> listPersons;
    private  ArrayList<Category> listCategories;

    private  Button button;
    private  ListView listview;

    //endregion


    //region Protected Methods

    /**
     * Die Methode erstellt die Ansicht in der Activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button = (Button)findViewById(R.id.settings_button_new);
        listview = (ListView)findViewById(R.id.settings_listview);

        setListView();

        setButton();

    }

    //endregion


    //region Public Methods

    /**
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuInflater inflater = getMenuInflater();

        String[] names = new String[getNames().size()];
        for (int i = 0; i <getNames().size(); i++){
            names[i] = getNames().get(i);
        }

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.setHeaderTitle(names[info.position]);
        inflater.inflate( R.menu.contextmenu, menu);

    }


    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;


        switch (item.getItemId()) {

            case R.id.contentmenu_item_edit:

                DialogFrag_AddSetting dialogFragEdit = new DialogFrag_AddSetting();
                dialogFragEdit.setActivity(Activity_Settings.this);
                dialogFragEdit.setId(getId(position));
                dialogFragEdit.show(getFragmentManager(), object.toString() + "+edit");

                return true;

            case R.id.contentmenu_item_delete:

                DialogFrag_AddSetting dialogFragDelete = new DialogFrag_AddSetting();
                dialogFragDelete.setActivity(Activity_Settings.this);
                dialogFragDelete.setId(getId(position));
                dialogFragDelete.setPersons(listPersons);
                dialogFragDelete.show(getFragmentManager(), object.toString() + "+delete");

                return true;

            default:

                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    //endregion


    //region Private Methods

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

            registerForContextMenu(listview);

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
     * @param position
     * @return
     */
    private long getId(int position) {

        long id = 0;

        if (listPersons != null && object == Person.class) {

            id = listPersons.get(position).getID();

        } else if (listCategories != null && object == Category.class) {

            id = listCategories.get(position).getID();
        }

        return  id;
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

                DialogFrag_AddSetting newData = new DialogFrag_AddSetting();
                newData.setActivity(Activity_Settings.this);
                newData.show(getFragmentManager(), object.toString());
            }
        });
    }

    //endregion

}
