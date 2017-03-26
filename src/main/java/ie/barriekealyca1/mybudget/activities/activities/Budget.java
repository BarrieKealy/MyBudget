package ie.barriekealyca1.mybudget.activities.activities;

/**
 * Budget.java
 * Purpose: Display the users outgoings in a ListView and allow them to see how much they have left.
 *
 * @author - Barrie Kealy
 * @version - 2.0
 */

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import ie.barriekealyca1.mybudget.R;
import ie.barriekealyca1.mybudget.activities.models.Income;

public class Budget extends Base {

    //The ListView to be used
    ListView listView;

    /*private Button saveButton;
    private ProgressBar progressBar;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        //ListView is formatted by the spendingList XML resource
        listView = (ListView) findViewById(R.id.spendingList);
        //Creates a new IncomeAdapter populated by income objects.
        IncomeAdapter adapter = new IncomeAdapter(this, app.dbManager.getAll());
        //Binds the adapter to the ListView
        listView.setAdapter(adapter);

        //Heading for amountLeft
        TextView amountLeftNum = (TextView) findViewById(R.id.amountLeftNum);
        //Calls the getAmountLeftOver() method from Base.java and sets the text to result after converting to String.
        amountLeftNum.setText(Double.toString(app.getAmountLeftOver()));

        //progressBar   = (ProgressBar)  findViewById(R.id.progressBar);
    }

    /*CODE NOT WORKING
    public void onSaveButtonPressed() {
        progressBar.setMax(getAmountToSave());
        progressBar.setProgress(getSavingsProgress());

    }*/
}

/**
 * Creates a custom ArrayAdapter to be used with the ListView
 */
class IncomeAdapter extends ArrayAdapter<Income> {

    private Context context;
    //The array of Income objects
    public List<Income> income;

    //Creates a new adapter with the row_layout layout and populates with income objects
    public IncomeAdapter(Context context, List<Income> income) {
        super(context, R.layout.row_layout, income);
        this.context = context;
        this.income = income;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Inflates the layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Inflates row_layout in current view
        View view = inflater.inflate(R.layout.row_layout, parent, false);
        //Gets the position of Income objects in Array to order them in ListView
        Income incomes = income.get(position);

        //Instantiates the ListView heading columns and data colums
        TextView savingsColumn = (TextView) view.findViewById(R.id.savingsColumn);
        TextView rentColumn = (TextView) view.findViewById(R.id.rentColumn);
        TextView billsColumn = (TextView) view.findViewById(R.id.billsColumn);
        TextView savingsView = (TextView) view.findViewById(R.id.savingsView);
        TextView rentMortgageView = (TextView) view.findViewById(R.id.rentMortgageView);
        TextView billsView = (TextView) view.findViewById(R.id.billsView);

        /*savingsColumn.setText(R.string.savingsColumnText);
        rentColumn.setText(R.string.rentColumnText);
        billsColumn.setText(R.string.billsColumnText);*/

        //Sets the text of data columns from Income objects
        savingsView.setText("€" + Double.toString(incomes.savingsAmount));
        rentMortgageView.setText("€" + Double.toString(incomes.rentMortgageAmount));
        billsView.setText("€" + Double.toString(incomes.bills));

        return view;
    }

    @Override
    public int getCount() {
        return income.size();
    }
}
