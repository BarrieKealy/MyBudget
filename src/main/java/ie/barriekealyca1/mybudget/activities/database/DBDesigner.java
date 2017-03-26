package ie.barriekealyca1.mybudget.activities.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Barrie on 26/03/2017.
 */

public class DBDesigner extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "budgets.db";
    private static final int 	DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE_TABLE_BUDGET = "create table budgets "
            + "(id integer primary key autoincrement,"
            + "totalIncome double not null,"
            + "savingsAmount double,"
            + "rentMortgageAmount double,"
            + "bills double);";

    public DBDesigner(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_TABLE_BUDGET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBDesigner.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS budgets");
        onCreate(db);
    }
}
