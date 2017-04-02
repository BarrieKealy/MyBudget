package ie.barriekealyca1.mybudget.activities.models;

/**
 * Income.java
 * Purpose: To be able to model an individual Income from a user
 *
 * @author Barrie Kealy
 * @version 2.0
 */

public class Income {

    public int id;
    public double totalIncome;
    public double savingsAmount;
    public double rentMortgageAmount;
    public double bills;

    /**
     * Takes the data supplied by user in Home.java
     * @param totalIncome - the income user receives
     * @param savingsAmount - How much the user saves a month
     * @param rentMortgageAmount - How much the user spends on rent/mortgage a month
     * @param bills - How much the user spends on bills a month
     */
    public Income(double totalIncome, double savingsAmount, double rentMortgageAmount, double bills) {
        this.totalIncome = totalIncome;
        this.savingsAmount = savingsAmount;
        this.rentMortgageAmount = rentMortgageAmount;
        this.bills = bills;
    }

    public Income() {
        this.totalIncome = 0.0;
        this.savingsAmount = 0.0;
        this.rentMortgageAmount = 0.0;
        this.bills = 0.0;
    }
}
