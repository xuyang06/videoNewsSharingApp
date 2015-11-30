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
import android.widget.AdapterView.OnItemSelectedListener;
import android.app.ListActivity;

public class ViewAllNewsActivity extends ListActivity{
	private ListView listView;
	private String[] newsList = null;
	private String loginName = null;
	private String loginPassword = null;
	//private ListView contactsList;
	//private Button viewnewsBtn;
	//private Button cancelBtn;
	private String selectNews = null;
	//private int selectIndex = -1;
	static final int REQUEST_CODE = 1;
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
	    if (extras != null) {
	    	loginName = extras.getString("loginName");
	    	loginPassword = extras.getString("loginPassword");
	    }
	    
	    String result = getAllMessageList(loginName, loginPassword);
	    StringTokenizer resultToken = new StringTokenizer(result, ";");
	    int tokenNumber = resultToken.countTokens();
	    newsList = new String[tokenNumber];
	    int i = 0;
	    while ( resultToken.hasMoreTokens()){
	    	String str = resultToken.nextToken();
	    	newsList[i] = str;
	    	i ++ ;
	    }
	    //Log.i(TAG, "here");
	    setContentView(R.layout.all_new_message);
	    //listView = (ListView) findViewById(R.id.messages_list);
	   // viewnewsBtn = (Button) findViewById(R.id.viewnews_btn);
	    //cancelBtn = (Button) findViewById(R.id.cancel_btn);
		//Log.i(TAG, "ihere");
//		listView.setAdapter(new ArrayAdapter<String>(this,
//				android.R.layout.simple_list_item_1, data));
		//listView.setAdapter(new ArrayAdapter<String>(this,
		//android.R.layout.simple_list_item_1, newsList));	
		
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, newsList));
        //listView.setItemsCanFocus(true);
        //listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		//setContentView(listView);
		//setContentView(contactsList);
		
		/*listView.setOnItemClickListener(new OnItemClickListener() {  
	        public void onItemClick(AdapterView arg0, View arg1, int arg2,long arg3)  
	        {  	
	        	int select = -1;
	            //List<Integer> list = new ArrayList<Integer>();  
	            SparseBooleanArray a = listView.getCheckedItemPositions();  
	            for(int i = 0; i < newsList.length ; i++)  
	            {  
	                if (a.valueAt(i))  
	                {  
	                    Long val = listView.getAdapter().getItemId(a.keyAt(i));  
	                    //Log.v(TAG, "index=" + val.toString());  
	                    select = Integer.parseInt(val.toString());
	                    
	                }  
	            }
	            //Log.v(TAG, "list size=" + list.size());
                //Log.v(TAG, "list =" + list.toString());
                if (select != -1){
                	viewnewsBtn.setEnabled(true);
                }else{
                	viewnewsBtn.setEnabled(false);
                }
                selectIndex = select;
	        }  
	    }); // End of Listener  
*/		
        /*OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				selectNews = parent.getItemAtPosition(position).toString();
				viewnewsBtn.setEnabled(true);
				Intent intent = new Intent(ViewAllNewsActivity.this, ViewNews.class);
        		intent.putExtra("loginName", loginName);
        		intent.putExtra("loginPassword", loginPassword);
        		//String object = newsList[selectIndex];
        		intent.putExtra("newsTitle", selectNews);
        		
				startActivityForResult(intent, REQUEST_CODE);
				
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				selectNews = null;
				viewnewsBtn.setEnabled(false);
			}
			
		};
		listView.setOnItemSelectedListener(itemSelectedListener);*/
		
		
        
        
		/*viewnewsBtn.setOnClickListener(new OnClickListener() { 
        	@Override 
        	public void onClick(View v) {
        		Intent intent = new Intent(ViewAllNewsActivity.this, ViewNews.class);
        		intent.putExtra("loginName", loginName);
        		intent.putExtra("loginPassword", loginPassword);
        		//String object = newsList[selectIndex];
        		intent.putExtra("newsTitle", selectNews);
        		
				startActivityForResult(intent, REQUEST_CODE);
        		Intent intent = new Intent(AndroidWSNewClientActivity.this, VideoRecordActivity.class);
				startActivity(intent);
        	}
        }); 
		
		
		cancelBtn.setOnClickListener(new OnClickListener() { 
        	@Override 
        	public void onClick(View v) {
        		Intent returnIntent = new Intent(ViewAllNewsActivity.this, CommandTerminalActivity.class);
        		returnIntent.putExtra("loginName", loginName);
        		returnIntent.putExtra("loginPassword", loginPassword);
				startActivityForResult(returnIntent, REQUEST_CODE);
        		Intent intent = new Intent(AndroidWSNewClientActivity.this, VideoRecordActivity.class);
				startActivity(intent);
        	}
        }); */
	}
	
	protected void onListItemClick(ListView listView, View v, int position, long id) {
    	super.onListItemClick(listView, v, position, id);
    	selectNews = listView.getItemAtPosition(position).toString();
		//viewnewsBtn.setEnabled(true);
		Intent intent = new Intent(ViewAllNewsActivity.this, ViewNews.class);
		intent.putExtra("loginName", loginName);
		intent.putExtra("loginPassword", loginPassword);
		//String object = newsList[selectIndex];
		intent.putExtra("newsTitle", selectNews);
		
		startActivityForResult(intent, REQUEST_CODE);
	}
	
	private String getAllMessageList(String user_name, String user_password) {
		String methodName = "getAllMessageList";
		SoapObject rpc = new SoapObject(nameSpace, methodName);
    	// input parameters
		rpc.addProperty("user_name", loginName);
    	rpc.addProperty("user_password", loginPassword);
		SoapObject object = SOAPMethod.callWebservice(methodName, rpc);
    	String result = object.getProperty("getAllMessageListReturn").toString();
    	return result;
		
    }
	
}
