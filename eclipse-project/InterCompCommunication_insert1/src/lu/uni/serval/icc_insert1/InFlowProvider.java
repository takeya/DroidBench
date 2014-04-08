package lu.uni.serval.icc_insert1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class InFlowProvider extends ContentProvider {

	String NAME = "lu.uni.serval.icc_insert1";
	String URL = "content://" + NAME + "/deviceid";
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues cv) {
		
		if (uri.toString().equals(URL))
		{
			String deviceid = cv.getAsString("DroidBench");
			Log.i("DroidBench", deviceid);
		}
		
		return uri;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
