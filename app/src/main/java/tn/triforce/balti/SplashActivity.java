package tn.triforce.balti;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Calendar;


public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        try {
            String pInfoVersion  = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            int year = Calendar.getInstance().get(Calendar.YEAR);
            TextView version = (TextView) findViewById(R.id.version);
            version.setText("V "+pInfoVersion+"  ©"+year);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
        startActivity(mainIntent);
        finish();
        /*
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
//*/

    }
}
