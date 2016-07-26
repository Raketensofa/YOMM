package com.cgellner.yomm.Settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Category;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.R;

import java.util.ArrayList;

/**
 * Die Klasse stellt eine Ansicht dar, in welcher Elemente wie Personen oder Kategorien angezeigt und verwaltet werden koennen.
 * @author Carolin Gellner
 */
public class DialogFrag_AddSetting extends DialogFragment {

    //region Fields

    private View view;
    private String type;
    private TextView header;
    private TextView inputName;
    private ArrayList<Person> persons;

    private  Dialog alertDialog;

    private long id = 0;

    private Activity_Settings activitySettings;

    //endregion


    //region Setter

    public void setActivity(Activity_Settings activitySettings) {

        this.activitySettings = activitySettings;

    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public void setId(long id) {
        this.id = id;
    }

    //endregion


    //region Public Methods

    /**
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        type = getTag();

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        view = inflater.inflate(R.layout.layout_newsetting, null);
        dialogBuilder.setView(view);


        header = (TextView)view.findViewById(R.id.tVnewSetting);
        inputName = (TextView)view.findViewById(R.id.tVsettingName);




        //Speichern-Button
        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                DialogFrag_AddSetting.this.getDialog().cancel();
            }
        });


        //Abbrechen-Button
        dialogBuilder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                saveData();
                activitySettings.setListView();
            }
        });

        if(type.contains("delete") == false){

            inputName.setFocusable(true);
            inputName.setEnabled(true);
            inputName.setClickable(true);
            inputName.setTextColor(Color.BLACK);
            inputName.setFocusableInTouchMode(true);

        }else{

            inputName.setEnabled(true);
            inputName.setTextColor(Color.BLACK);
        }

        setHeader();


        setNameIntoEditText();
        setInfoTextIntoEditText();

        alertDialog = dialogBuilder.create();


        return dialogBuilder.create();

    }

    //endregion


    //region Private Methods

    /**
     * Die Methode speichert, aendert oder loescht den Namen der Person oder der Kategorie in der Datenbank.
     */
    private void saveData(){

        //EDIT: Element in Datenbank updaten
        if(type.contains("edit")){

            String name = ((TextView) view.findViewById(R.id.tVsettingName)).getText().toString();

            if (name.length() > 0) {

                if (type.contains(Person.class.toString())) {

                    GlobalVar.Database.updatePersonName(id, name);

                } else if (type.contains(Category.class.toString())) {

                    GlobalVar.Database.updateCategoryName(id, name);
                }
            }


            //DELETE: Element in Datenbank loeschen
        }else if(type.contains("delete")){

            if (type.contains(Person.class.toString())) {

                boolean save = true;

                for (Person person : persons){

                    double debts = GlobalVar.getPaymentDifference(id, person.getID());

                    if(debts != 0){

                        save = false;
                        break;
                    }
                }

                if(save == true) {

                    GlobalVar.Database.deletePayments(id);
                    GlobalVar.Database.deleteRepayment(id);
                    GlobalVar.Database.deletePerson(id);

                }else{

                    Toast.makeText(getActivity(), "Person nicht gelöscht!", Toast.LENGTH_SHORT).show();

                }

            } else if (type.contains(Category.class.toString())) {

                GlobalVar.Database.deleteCateogry(id);
            }

        }else{

            //WEDER DELETE NOCH EDIT: Element in Datenbank speichern
            String name = ((TextView) view.findViewById(R.id.tVsettingName)).getText().toString();
            if (name.length() > 0) {

                if (type.contains(Person.class.toString())) {

                    Person person = new Person();
                    person.setName(name);
                    GlobalVar.Database.insertPerson(person);


                } else if (type.contains(Category.class.toString())) {

                    Category category = new Category();
                    category.setName(name);
                    GlobalVar.Database.insertCategory(category);

                }
            }
        }
    }

    /**
     *Die Methode legt den Titel des DialogFragments fest.
     */
    private void setHeader(){

            if (type.equals(Person.class.toString())) {

                header.setText(R.string.LabelAddNewPersonHeadline);

            } else if (type.equals(Category.class.toString()) && id  == 0) {

                header.setText(R.string.LabelAddNewMainCatHeadline);

            }else  if (type.contains(Person.class.toString()) && type.contains("edit")) {

                header.setText(R.string.LabelUpdatePersonHeadline);

            } else if (type.contains(Category.class.toString()) && type.contains("edit")) {

                header.setText(R.string.LabelUpdateNewMainCatHeadline);

            }else if (type.contains(Person.class.toString()) && type.contains("delete")) {

                header.setText(R.string.LabelDeletePersonHeadline);

            } else if (type.contains(Category.class.toString()) && type.contains("delete")) {

                header.setText(R.string.LabelDeleteNewMainCatHeadline);
            }
    }

    /**
     * Name in der TextView anzeigen zum aendern (Wenn Element bearbeiten wird)
     */
    private void setNameIntoEditText(){

        if(type.contains("edit") && id != 0){

            String name = "";

            if(type.contains(Person.class.toString())){

                name = GlobalVar.Database.getPersonName(id);

            }else if(type.contains(Category.class.toString())){

                name = GlobalVar.Database.getCategoryName(id);
            }

            inputName.setText(name);
        }
    }


    /**
     * Die Methode zeigt einen Infotext im DialogFragment an, wenn eine Kategorie oder Person geloescht werden soll.
     */
    private void setInfoTextIntoEditText(){

        if(type.contains("delete") && id != 0){

            String text = "";

            if(type.contains(Person.class.toString())){

                for (Person person : persons) {

                    double debts = GlobalVar.getPaymentDifference(id, person.getID());

                    //Person kann erst geloescht werden, wenn alle Zahlungen ausgeglichen sind, die diese Person betreffen
                    if(debts != 0){

                        text = "Die Person '" +  GlobalVar.Database.getPersonName(id) + "' kann nicht  gelöscht werden, " +
                                "da diese Person mindestens einer Person Geld schuldet oder von mindestens einer Person noch Geld bekommt." +
                                "Nach dem Ausgleichen der offenen Beträge, kann die Person gelöscht werden.";

                        break;

                    }else{

                        text = "Bist du dir sicher, dass die Person '" + GlobalVar.Database.getPersonName(id) + "' gelöscht werden soll?" +
                                "Bitte beachte, dass alle erfassten Ausgaben und Rückzahlungen, welche diese Person betreffen, dann auch gelöscht werden.";
                    }
                }

            }else if(type.contains(Category.class.toString())){

                text = "Bist du dir sicher, dass die Kategorie '" + GlobalVar.Database.getCategoryName(id) + "' gelöscht werden soll?";

            }

            inputName.setText(text);
            inputName.setTextSize(15);
            inputName.setEnabled(false);
        }
    }

    //endregion

}
