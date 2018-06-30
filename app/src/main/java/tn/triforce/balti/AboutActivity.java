package tn.triforce.balti;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Calendar;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        try {
            String pInfoVersion  = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            int year = Calendar.getInstance().get(Calendar.YEAR);
            TextView version = (TextView) findViewById(R.id.version);
            version.setText("V "+pInfoVersion+"  ©"+year);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
