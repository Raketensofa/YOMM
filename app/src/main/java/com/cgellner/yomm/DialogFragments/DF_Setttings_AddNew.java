package com.cgellner.yomm.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cgellner.yomm.Activities.Activity_Settings;
import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Category;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.R;


public class DF_Setttings_AddNew extends DialogFragment {


    private final String TAG = DF_Setttings_AddNew.class.getName();
    private View view;
    private String type;
    private TextView header;
    private EditText inputName;

    private Activity_Settings activitySettings;

    public void setActivity(Activity_Settings activitySettings) {

        this.activitySettings = activitySettings;

    }

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
        inputName = (EditText)view.findViewById(R.id.eTsettingName);

        setHeader();

        //Speichern-Button
        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                DF_Setttings_AddNew.this.getDialog().cancel();
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

        return dialogBuilder.create();

    }








    /**
     *
     */
    private void saveData(){

        String error = null;

        String name = ((EditText)view.findViewById(R.id.eTsettingName)).getText().toString();
        if(name.length() > 0){

            if(type.equals(Person.class.toString())){

                Person person = new Person();
                person.setName(name);
                GlobalVar.Database.insertPerson(person);


            }else if(type.equals(Category.class.toString())) {

                Category category = new Category();
                category.setName(name);
                GlobalVar.Database.insertCategory(category);

            }
        }
    }

    /**
     *
     */
    private void setHeader(){

        if(type != null && header != null) {

            if (type.equals(Person.class.toString())) {

                header.setText(R.string.LabelAddNewPersonHeadline);

            } else if (type.equals(Category.class.toString())) {

                header.setText(R.string.LabelAddNewMainCatHeadline);
            }
        }
    }

}
