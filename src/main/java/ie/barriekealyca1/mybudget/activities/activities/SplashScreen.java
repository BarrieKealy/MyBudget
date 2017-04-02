package ie.barriekealyca1.mybudget.activities.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import ie.barriekealyca1.mybudget.R;

/**
 * Created by Barrie on 23/03/2017.
 */

public class SplashScreen extends Activity {

    //Timer for splash screen
    private static int SPLASH_TIMER = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            /**
             * This method runs once the timer has finished
             * It launches Home.java
             */
            @Override
            public void run() {
                SplashScreen.this.startActivity(new Intent(SplashScreen.this, Login.class));

                //Closes the SpashScreen activity
                finish();
            }
        }, SPLASH_TIMER);
    }
}
