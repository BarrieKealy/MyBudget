package ie.barriekealyca1.mybudget.activities.activities;

/**
 * Home.Java
 * Purpose: Allows a user to enter their income/outgoings and click a calculate button
 *
 * @author Barrie Kealy
 * @version 2.0
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
//import android.widget.Spinner;
import android.widget.TextView;
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

    private SharedPreferences settings;

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    //The nav bar toggle switch
    private ActionBarDrawerToggle mDrawerToggle;
    //Nav bar
    private DrawerLayout mDrawerLayout;
    //Title when nav drawer is open
    private String mActivityTitle;

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

        settings = getSharedPreferences("loginPrefs", 0);
        String username = settings.getString("username", "");

        //Sets text to Username
        TextView usernameBudget = (TextView) findViewById(R.id.usernameBudget);

        SpannableString spanString = new SpannableString(username);
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
        usernameBudget.setText(spanString + ", ");

        //Set the ListView for nav drawer
        mDrawerList = (ListView)findViewById(R.id.navList);
        addDrawerItems();

        //For nav drawer icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        setupDrawer();
    }

    private void addDrawerItems() {
        String[] navArray = { "Home", "Budget"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectItem(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(this, Home.class);
                break;
            case 1:
                intent = new Intent(this, Budget.class);
                break;
        }
        startActivity(intent);
    }

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
