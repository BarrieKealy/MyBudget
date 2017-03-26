package ie.barriekealyca1.mybudget.activities.activities;

/**
 * Home.Java
 * Purpose: Allows a user to enter their income/outgoings and click a calculate button
 *
 * @author Barrie Kealy
 * @version 2.0
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
//import android.widget.Spinner;
import android.widget.Toast;

import ie.barriekealyca1.mybudget.R;
import ie.barriekealyca1.mybudget.activities.activities.Base;
import ie.barriekealyca1.mybudget.activities.models.Income;

//import static ie.barriekealyca1.mybudget.R.id.monthSpinner;

public class Home extends Base {

    //The amount entered in payment EditText box
    public double paymentAmountNum;
    //The amount entered in amount to save EditText box
    public double savingsAmount;
    //The amount entered in rent/mortgage EditText box
    public double rentMortgageAmount;
    //The amount entered in bills EditText box
    public double bills;
    //The amount entered in savings goal EditText box
    public double desiredSavings;
    //Boolean for if car checkbox is checked or not
    boolean car;

    //Adds the outgoings
    double totalOutGoing;
    //Takes Outgoings from amount payed
    double amountLeftOver;

    private Button calculateButton;
    private RadioGroup periodRadioGroup;
    private CheckBox carCheckBox;
    private EditText paymentAmount, savingsAmountIn, rentMortgage, billsIn, savingsGoal;
    //private Spinner monthSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Instantiates the widgets
        calculateButton = (Button) findViewById(R.id.calculateButton);
        periodRadioGroup = (RadioGroup) findViewById(R.id.periodRadioGroup);
        carCheckBox = (CheckBox) findViewById(R.id.carCheckBox);
        paymentAmount = (EditText) findViewById(R.id.paymentAmount);
        savingsAmountIn = (EditText) findViewById(R.id.savingsAmount);
        rentMortgage = (EditText) findViewById(R.id.rentMortgageNum);
        billsIn = (EditText) findViewById(R.id.bills);
        savingsGoal = (EditText) findViewById(R.id.savingsGoal);
    }

        /*THIS CODE NOT WORKING. INTENDED TO BE A SPINNER TO ALLOW USER TO SELECT MONTH, BUT WON'T APPEAR ON VIEW.
        monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.monthArray, android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);
    }

    //@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
    }*/

    /**
     * Method called when user clicks the calculate button. Does all required calculations and assignments,
     * then opens the Budget class to show their data.
     *
     * @param view - The view to be used
     */
    public void calculatePressed(View view) {
        //Gets the text from payment EditText box and converts it to String
        String paymentText = paymentAmount.getText().toString();
        //If payment text isn't empty
        if(!paymentText.equals("")) {
            //Converts payment amount to type double
            paymentAmountNum = Double.parseDouble(paymentText);
            //Ternary Operator. If weeklyRadio is selected, multiply paymentAmountNum * 4 else leave as is.
            paymentAmountNum = periodRadioGroup.getCheckedRadioButtonId() == R.id.weeklyRadio ? paymentAmountNum * 4 : paymentAmountNum;
        }
        else {
            Toast.makeText(this, "Please enter your payment amount", Toast.LENGTH_SHORT).show();
        }

        //Gets the text from amountToSave EditText box and converts it to String
        String amountToSaveText = savingsAmountIn.getText().toString();
        //If amountToSave text isn't empty
        if(!amountToSaveText.equals("")) {
            //Converts savingsAmount to type double
            savingsAmount = Double.parseDouble(amountToSaveText);
        }
        else {
            Toast.makeText(this, "Please enter your savings per month", Toast.LENGTH_SHORT).show();
        }

        //Gets the text from rent/mortgage EditText box and converts it to String
        String rentMortgageText = rentMortgage.getText().toString();
        //If rent/mortgage text isn't empty
        if(!rentMortgageText.equals("")) {
            //Converts rent/mortgage to type double
            rentMortgageAmount = Double.parseDouble(rentMortgageText);
        }
        else {
            Toast.makeText(this, "Please enter your rent/mortgage per month", Toast.LENGTH_SHORT).show();
        }

        //Gets the text from bills EditText box and converts it to String
        String billsText = billsIn.getText().toString();
        //If bills text isn't empty
        if(!billsText.equals("")) {
            //Converts bills to type double
            bills = Double.parseDouble(billsText);
        }
        else {
            Toast.makeText(this, "Please enter your bills per month", Toast.LENGTH_SHORT).show();
        }

        //Gets the text from saving goal EditText box and converts it to String
        String savingsGoalText = savingsGoal.getText().toString();
        //If savings goal text isn't empty
        if(!savingsGoalText.equals("")) {
            //Converts savings goal to type double
            desiredSavings = Integer.parseInt(rentMortgageText);
        }
        else {
            Toast.makeText(this, "Please enter your desired amount to have saved", Toast.LENGTH_SHORT).show();
        }

        //Checks if carCheckBox is ticked. If so, set car to true and add €150 to monthly bills.
        if(carCheckBox.isChecked()){
            car = true;
            bills += 150;
        }

        //Checks if paymentAmount > 0
        //I considered putting more checks in but decided that maybe there are people with absolutely no outgoings.
        //Unlikely, but possible.
        if(paymentAmountNum > 0) {
            //Creates a new Income model using data provided above as pararmeters.
            app.newIncome(new Income(paymentAmountNum, savingsAmount, rentMortgageAmount, bills));
            //Used for calculations in Base.java
            totalOutGoing = savingsAmount + rentMortgageAmount + bills;
            //Used for calculations in BudgetApp.java
            //app.amountLeftOver = paymentAmountNum - totalOutGoing;
            //Calls setAmountLeftOver from BudgetApp.java with amountLeftover as param.
            //app.setAmountLeftOver(amountLeftOver);
            //Opens the  Budget Activity.
            startActivity (new Intent(this, Budget.class));

            //setAmountToSave(desiredSavings);
            //Toast.makeText(this, "" + getAmountLeftOver(), Toast.LENGTH_SHORT).show();
        }
    }

}
