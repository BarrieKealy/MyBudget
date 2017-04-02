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

public class Login extends Activity {

    private boolean mIsBackButtonPressed;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        settings = getSharedPreferences("loginPrefs", 0);
        if(settings.getBoolean("loggedin", false)) {
            startHomeScreen();
        }
    }

    public void register(View v) {
        startActivity(new Intent(this, Register.class));
    }

    private void startHomeScreen() {
        Intent intent = new Intent(this, Home.class);
        Login.this.startActivity(intent);
    }

    public void login(View v) {
        CharSequence username = ((TextView) findViewById(R.id.username_login)).getText();
        CharSequence password = ((TextView) findViewById(R.id.password_login)).getText();

        String validUsername = settings.getString("username", "");
        String validPassword = settings.getString("password", "");

        if(username.length() <= 0 || password.length() <= 0) {
            Toast.makeText(this, "You must enter a Username and Password",
                    Toast.LENGTH_SHORT).show();
        }

        else if(!username.toString().matches(validUsername)
                || !password.toString().matches(validPassword)) {
            Toast.makeText(this, "Invalid Username or Password Entered",
                    Toast.LENGTH_SHORT).show();
        }

        else if(!mIsBackButtonPressed) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("loggedin", true);
            editor.commit();

            startHomeScreen();
            this.finish();
        }
    }
}
