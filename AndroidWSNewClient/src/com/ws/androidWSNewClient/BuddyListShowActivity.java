package com.ws.androidWSNewClient;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.*;

public class BuddyListShowActivity extends Activity {
	private ListView listView;
	private static final String TAG = "BuddyListShowActivity";
	private String[] userNameList = null;
	private String loginName = null;
	private String loginPassword = null;
	//private ListView contactsList;
	private Button contactsBtn;
	private Button contactsCancelBtn;
	static final int REQUEST_CODE = 1;
	private List<Integer> selectList = new ArrayList<Integer>();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
	    if (extras != null) {
	    	loginName = extras.getString("loginName");
	    	loginPassword = extras.getString("loginPassword");
	    }
	    
	    String result = getBuddyListInfo(loginName, loginPassword);
	    StringTokenizer resultToken = new StringTokenizer(result, ";");
	    int tokenNumber = resultToken.countTokens();
	    userNameList = new String[tokenNumber];
	    int i = 0;
	    while ( resultToken.hasMoreTokens()){
	    	String str = resultToken.nextToken();
	    	userNameList[i] = str;
	    	i ++ ;
	    }
	    Log.i(TAG, "here");
	    setContentView(R.layout.contacts_activity);
	    listView = (ListView) findViewById(R.id.contacts_list);
		contactsBtn = (Button) findViewById(R.id.contacts_btn);
		contactsCancelBtn = (Button) findViewById(R.id.contacts_cancel_btn);
		Log.i(TAG, "ihere");

		listView.setAdapter(new ArrayAdapter<String>(this,
		android.R.layout.simple_list_item_single_choice, userNameList));	
        listView.setItemsCanFocus(true);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		
		listView.setOnItemClickListener(new OnItemClickListener() {  
	        public void onItemClick(AdapterView arg0, View arg1, int arg2,long arg3)  
	        {  
	            List<Integer> list = new ArrayList<Integer>();  
	            SparseBooleanArray a = listView.getCheckedItemPositions();  
	            for(int i = 0; i < userNameList.length ; i++)  
	            {  
	                if (a.valueAt(i))  
	                {  
	                    Long val = listView.getAdapter().getItemId(a.keyAt(i));  
	                    Log.v(TAG, "index=" + val.toString());  
	                    list.add(Integer.parseInt(val.toString()));
	                    
	                }  
	            }
	            Log.v(TAG, "list size=" + list.size());
                Log.v(TAG, "list =" + list.toString());
                if (list.size()>0){
                	contactsBtn.setEnabled(true);
                }else{
                	contactsBtn.setEnabled(false);
                }
                selectList = list;
	        }  
	    }); // End of Listener  
		
		contactsBtn.setOnClickListener(new OnClickListener() { 
        	@Override 
        	public void onClick(View v) {
        		Intent intent = new Intent(BuddyListShowActivity.this, VideoRecordActivity.class);
        		intent.putExtra("loginName", loginName);
        		intent.putExtra("loginPassword", loginPassword);
        		int nameSize = selectList.size();
        		//Log.v(TAG, "nameSize=" + nameSize);
        		intent.putExtra("nameSize", nameSize);
        		for (int i = 0; i < nameSize; i++){
        			String key = "name" + i;
        			String object = userNameList[selectList.get(i)];
        			intent.putExtra(key, object);
        		}
				startActivityForResult(intent, REQUEST_CODE);
        		
        	}
        }); 
		
		
		contactsCancelBtn.setOnClickListener(new OnClickListener() { 
        	@Override 
        	public void onClick(View v) {
        		Intent returnIntent = new Intent(BuddyListShowActivity.this, CommandTerminalActivity.class);
        		returnIntent.putExtra("loginName", loginName);
        		returnIntent.putExtra("loginPassword", loginPassword);
				startActivityForResult(returnIntent, REQUEST_CODE);
        		
        	}
        }); 
   
    }

	
	
	
	
	private String getBuddyListInfo(String loginName, String loginPassword) {
		String methodName = "getFriendList";
		SoapObject rpc = new SoapObject(nameSpace, methodName);
    	// input parameters
		rpc.addProperty("user_name", loginName);
    	rpc.addProperty("user_password", loginPassword);
		SoapObject object = SOAPMethod.callWebservice(methodName, rpc);
    	String result = object.getProperty("getFriendListReturn").toString();
    	return result;
    }
}
