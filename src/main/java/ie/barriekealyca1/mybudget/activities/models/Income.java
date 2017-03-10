package ie.barriekealyca1.mybudget.activities.models;

/**
 * Income.java
 * Purpose: To be able to model an individual Income from a user
 *
 * @author Barrie Kealy
 * @version 1.0
 */

public class Income {

    private double totalIncome;
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
}
