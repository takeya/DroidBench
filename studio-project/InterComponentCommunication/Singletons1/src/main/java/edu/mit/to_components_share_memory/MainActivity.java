package edu.mit.to_components_share_memory;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;

import edu.mit.R;

/**
 * @testcase_name Two-Components-Share-Memory
 * 
 * @description Test case where two activities share a singleton, and a flow is activited on a 
  particular ordering of the Activities
 * @dataflow source -> sink
 * @number_of_leaks 1
 * @challenges - Analysis has to reason about all legal interleavings of separate components.
 */
public class MainActivity extends Activity {    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                         
	Singleton.v().s = "";
    }
    
    protected void onStop() {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage("+49", null, Singleton.v().s, null, null);  //sink, leak
    }
    
}
