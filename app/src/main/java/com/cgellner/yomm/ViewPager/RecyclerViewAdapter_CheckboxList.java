package com.cgellner.yomm.ViewPager;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.cgellner.yomm.GlobalVar;
import com.cgellner.yomm.Objects.Person;
import com.cgellner.yomm.R;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * Die Klasse beinhaltet den Adapter der RecyclerView fuer eine Liste mit Checkboxen (Liste zum Auswaehlen der Debitoren)
 * @since 12.06.2016
 * @author Carolin Gellner
 */
public class RecyclerViewAdapter_CheckboxList extends RecyclerView.Adapter<RecyclerViewAdapter_CheckboxList.ViewHolder_CheckboxList>{


    //region Fields

    private ArrayList<Person> personsList;
    private SharedPreferences sharedPreferences;
    private ViewHolder_CheckboxList viewholder;

    //endregion


    //region Setter

    /**
     * Setter der Liste mit den Personen
     * @param dataList Liste mit den Personen
     */
    public void setPersonDataList(ArrayList<Person> dataList) {
        this.personsList = dataList;
    }

    /**
     * Setter der SharedPreferences
     * @param sharedPreferences SharedPreferences
     */
    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    //endregion


    //region Public Methods

    /**
     * Die Methode erstellt die Ansicht eines Elements in der RecyclerView.
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder_CheckboxList onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listviewitemwithcheckbox, parent, false);

        viewholder = new ViewHolder_CheckboxList(v);

        return viewholder;

    }


    /**
     * Die Methode weisst den Bestandteilen wie TextViews etc. eines RecyclerView-Elements deren Werte zu.
     * @param holder Listenelement in der RecylcerView
     * @param position Position des Listenelements in der RecyclerView
     */
    @Override
    public void onBindViewHolder(ViewHolder_CheckboxList holder, int position) {

        if( personsList != null) {

            //Name der Person, ID der Person und allgemein die SharedPreferences uebergeben
            holder.checkBox.setText(personsList.get(position).getName());
            holder.ID = personsList.get(position).getID();
            holder.preferences = sharedPreferences;
            holder.checkBox.setTextSize(30);


            //Ermitteln, ob bereits Debitoren erfasst wurden und entsprechend deren Checkboxen aktivieren
            Set<String> persons = sharedPreferences.getStringSet(GlobalVar.SpPaymentDebtors, null);
            //Sollte schon Debtitoren gespeichert sein, deren Checkboxen aktivieren
            if (persons.size() > 0) {

                if (persons.contains(String.valueOf(holder.ID))) {

                    holder.checkBox.setChecked(true);
                }
            }
        }
    }


    /**
     * Die Methode gibt die Anzahl der Items in der RecyclerView zurueck,
     * welche in diesem Fall die Anzahl der Personen widerspiegelt.
     * @return Anzahl der Items in der RecyclerView
     */
    @Override
    public int getItemCount() {

        int count = 0;

        if(personsList != null){

            count = personsList.size();
        }

        return count;
    }

    //endregion


    //region RecyclerView.ViewHolder

    /**
     * Die Klasse beinhaltet den ViewHolder der RecylcerView.
     * Sie stellt somit ein Item (der Name einer Person) in der RecyclerView (Liste zum Auswaehlen der Debitoren) dar,
     * welche auf einer Seite des ViewPagers (Formular zum Erfassen von Ausgaben / Rueckzahlungen) angezeigt wird.
     */
    public static class ViewHolder_CheckboxList extends RecyclerView.ViewHolder{

        //region Fields

        public CheckBox checkBox;
        public long ID;
        public SharedPreferences preferences;

        //endregion


        //region Constructor

        /**
         * Der Konstruktur erstellt eine neue Instanz der Klasse ViewHolder_CheckboxList.
         * Die Instanz beinhaltet die Ansicht eines Items
         * @param itemView
         */
        ViewHolder_CheckboxList(final View itemView) {

            super(itemView);

            checkBox = (CheckBox)itemView.findViewById(R.id.checkBoxListView);


            //OnCheckedChangeListener der Checkbox
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    Set<String> debtors = null;

                    try {


                        debtors  = preferences.getStringSet(GlobalVar.SpPaymentDebtors, null);

                        if(isChecked == true){

                            //wenn Checkbox anktivert, dann ID der Person in den SharedPreferences speichern
                            debtors.add(String.valueOf(ID));

                        }else if(isChecked == false){

                            //wenn Checkbox deaktivert wurde, dann ID der Person in den SharedPreferences loeschen
                            debtors.remove(String.valueOf(ID));
                        }

                        //Neue Liste der Debitoren-Ids den SharedPrefs uebergeben
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putStringSet(GlobalVar.SpPaymentDebtors, debtors);
                        editor.commit();

                    }catch (Exception ex){

                        if(isChecked == true) {

                            HashSet<String> personHash = new HashSet<String>();
                            personHash.add(String.valueOf(String.valueOf(ID)));
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putStringSet(GlobalVar.SpPaymentDebtors, personHash);
                            editor.commit();

                        }
                    }
                }
            });
        }

        //endregion
    }

    //endregion

}
