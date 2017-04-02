package ie.barriekealyca1.mybudget.activities.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ie.barriekealyca1.mybudget.R;

public class Register extends Activity {

    private boolean mIsBackButtonPressed;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        settings = getSharedPreferences("loginPrefs", 0);
    }

    public void register(View v) {
        CharSequence username = ((TextView) findViewById(R.id.registerUsername)).getText();
        CharSequence password = ((TextView) findViewById(R.id.registerPassword)).getText();

        if(username.length() <= 0 || password.length() <= 0) {
            Toast.makeText(this, "You must enter a Username & Password", Toast.LENGTH_SHORT).show();
        }
        else if(!mIsBackButtonPressed) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("loggedin", true);
            editor.putString("username", username.toString());
            editor.putString("password", password.toString());
            editor.commit();

            Intent intent = new Intent(this, Home.class);
            Register.this.startActivity(intent);
            this.finish();
        }
    }
}
