package ie.barriekealyca1.mybudget.activities.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ie.barriekealyca1.mybudget.activities.activities.Base;
import ie.barriekealyca1.mybudget.activities.models.Income;

/**
 * Created by Barrie on 26/03/2017.
 */

public class DBManager {

    private SQLiteDatabase database;
    private DBDesigner dbHelper;

    public DBManager(Context context) {
        dbHelper = new DBDesigner(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public void add(Income d) {
        ContentValues values = new ContentValues();
        values.put("totalIncome", d.totalIncome);
        values.put("savingsAmount", d.savingsAmount);
        values.put("rentMortgageAmount", d.rentMortgageAmount);
        values.put("bills", d.bills);

        database.insert("budgets", null, values);
    }

    public List<Income> getAll() {
        List<Income> income = new ArrayList<Income>();
        Cursor cursor = database.rawQuery("SELECT * FROM budgets", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Income d = toIncome(cursor);
            income.add(d);
            cursor.moveToNext();
        }
        cursor.close();
        return income;
    }

    private Income toIncome(Cursor cursor) {
        Income pojo = new Income();
        pojo.id = cursor.getInt(0);
        pojo.totalIncome = cursor.getDouble(1);
        pojo.savingsAmount = cursor.getDouble(2);
        pojo.rentMortgageAmount = cursor.getDouble(3);
        pojo.bills = cursor.getDouble(4);
        return pojo;
    }

    /*public void setAmountLeftOver(Base base) {
        Cursor c = database.rawQuery("SELECT LAST(id) FROM budgets", null);
        c.moveToFirst();
        double income =
        if (!c.isAfterLast())
            base.app.amountLeftOver = c.getInt(0);
    }*/

    public void reset() {
        database.delete("budgets", null, null);
    }
}
