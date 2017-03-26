package ie.barriekealyca1.mybudget.activities.main;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ie.barriekealyca1.mybudget.activities.database.DBManager;
import ie.barriekealyca1.mybudget.activities.models.Income;

/**
 * Created by Barrie on 26/03/2017.
 */

public class BudgetApp extends Application {

    //A list populated with Income objects
    public static List<Income> income = new ArrayList<Income>();

    //The amount left after all outgoings are paid
    public double amountLeftOver = 0;

    public DBManager dbManager;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v("Budget", "Budget App Started");

        dbManager = new DBManager(this);
        Log.v("Budget", "Database Created");
    }

    /**
     * Add a new Income object to list. Called by clicking calculate button in Home.java
     *
     * @param incomeAdd - A new Income Object to be added to list
     */
    public void newIncome(Income incomeAdd) {
        dbManager.add(incomeAdd);
        amountLeftOver = incomeAdd.totalIncome - incomeAdd.savingsAmount - incomeAdd.bills
                - incomeAdd.rentMortgageAmount;
    }

    /**
     * sets the amount left after paying outgoings
     * @param amountLeftOver - The amount left
     *//*
    public void setAmountLeftOver(double amountLeftOver) {
        this.amountLeftOver = amountLeftOver;
    }*/

    /**
     * Gets the amount left over
     * @return - amountLeftOver
     */
    public double getAmountLeftOver() {
        return amountLeftOver;
    }

}
