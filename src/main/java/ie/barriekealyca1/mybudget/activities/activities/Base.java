package ie.barriekealyca1.mybudget.activities.activities;

/**
 * Base.java
 * Purpose: Used as a superclass to both Home.java and Budget.java to hold common elements.
 *
 * @author Barrie Kealy
 * @version 2.0
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import ie.barriekealyca1.mybudget.R;
import ie.barriekealyca1.mybudget.activities.main.BudgetApp;
import ie.barriekealyca1.mybudget.activities.models.Income;

public class Base extends AppCompatActivity {

    public BudgetApp app;
    //private static int desiredSavings;
    //private static int savingsProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (BudgetApp) getApplication();
        app.dbManager.open();
        //app.dbManager.setAmountLeftOver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.dbManager.close();
    }

    /**
     * Creates the Option Menu
     * @param menu - The menu to be inflated.
     * @return - true when menu is created.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * Prepares the elements in the options menu
     * @param menu - The Menu to be used
     * @return - true when elements are prepared
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem home = menu.findItem(R.id.menuHome);
        MenuItem budget = menu.findItem(R.id.menuBudget);
        MenuItem reset = menu.findItem(R.id.menuReset);

        //If income List is empty don't allow us to go to Budget.java
        if(app.dbManager.getAll().isEmpty()) {
            budget.setEnabled(false);
            reset.setEnabled(false);
        }
        //If the list is populated(not empty) allow us to select option
        else {
            budget.setEnabled(true);
            reset.setEnabled(true);
        }

        //If we are in the Home.java activity don't allow us to select the Home option
        if(this instanceof Home) {
            home.setEnabled(false);
            //If the list is populated(not empty) allow us to select option
            if(!app.dbManager.getAll().isEmpty())
                budget.setVisible(true);
                reset.setEnabled(true);
        }
        else {
            budget.setVisible(false);
            home.setVisible(true);
        }

        return true;
    }

    /**
     * Shows a pop up box when info item is selected.
     * @param current - The current activity.
     */
    public void openInfoDialog(Activity current) {
        //New dialog box
        Dialog dialog = new Dialog(current);
        dialog.setTitle("About My Budget App");
        dialog.setContentView(R.layout.info);

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    /**
     * Opens the info dialoh
     * @param m - The menuItem to be clicked
     */
    public void menuInfo(MenuItem m)
    {
        openInfoDialog(this);
    }

    /**
     * Shows a Toast on selection of Settings MenuItem
     * @param item - The menuItem to be clicked
     */
    public void settings(MenuItem item)
    {
        Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show();
    }

    /**
     * Opens to Budget activity on selection
     * @param m - The menuItem to be clicked
     */
    public void menuBudget(MenuItem m) {
        startActivity (new Intent(this, Budget.class));
    }

    /**
     * Opens to Home activity on selection
     * @param m - the MenuItem to be clicked
     */
    public void menuHome(MenuItem m) {
        startActivity (new Intent(this, Home.class));
    }

    public void reset(MenuItem item) {
        app.amountLeftOver = 0;
        app.dbManager.reset();
    }

    /*public void setAmountToSave(double desiredSavings) {
        this.desiredSavings = (int) desiredSavings;
    }

    public int getAmountToSave() {
        return desiredSavings;
    }

    public int getSavingsProgress() {
        savingsProgress = (int) amountLeftOver / 2;
        return savingsProgress;
    }*/
}
