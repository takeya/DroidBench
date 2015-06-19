package edu.mit.icc_unresolvable_intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;

public class InFlowActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent i = getIntent();
		String imei = i.getStringExtra("DroidBench");
		Log.i("DroidBench", imei);
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage("+49", null, imei, null, null);  //sink, leak
	}

}
