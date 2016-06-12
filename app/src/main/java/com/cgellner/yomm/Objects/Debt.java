package com.cgellner.yomm.Objects;

import com.cgellner.yomm.Activities.MainActivity;
import com.cgellner.yomm.GlobalVar;

import java.security.Key;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.List;

/**
 * Created by carol on 12.06.2016.
 */
public class Debt {

    private final String TAG = Debt.class.getName();

    /**
     *
     *
     * @param personId
     * @return
     */
    public HashMap<Long, Double> get(long personId){

        HashMap<Long, Double> debts = new HashMap<>();




        return debts;

    }




    /**
     *
     * @return
     */
    public double getDebtsForPerson(HashMap<Long, Double> debts, long personId){

        double debt = 0.0;

        debt = debts.get(personId);

        return debt;

    }



}
