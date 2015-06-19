package edu.mit.icc_action_string_operations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;

/*
 * This class is used to make sure that privacy detecting tools can distinguish the different components.
 */
public class IsolateActivity extends Activity 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		Intent i = getIntent();
		String imei = i.getStringExtra("DroidBench");
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage("+49 1234", null, imei, null, null); //sink, leak
		Log.i("DroidBench", imei);
	}
	
}
