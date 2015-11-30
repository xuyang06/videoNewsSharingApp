package com.ws.androidWSNewClient;

import android.app.Activity;
import android.os.Bundle;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddRelationActivity extends Activity{
	private EditText friendNameEditText;
	private TextView resultView;
	private Button addButton;
	private Button cancelButton;
	private String loginName = null;
	private String loginPassword = null;
	static final int REQUEST_CODE = 1;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.addrelation);
		Bundle extras = getIntent().getExtras();
	    if (extras != null) {
	    	loginName = extras.getString("loginName");
	    	loginPassword = extras.getString("loginPassword");
	    }
	    addButton = (Button) findViewById(R.id.addfriend_btn);
	    cancelButton = (Button) findViewById(R.id.cancel_btn);
	    friendNameEditText = (EditText) findViewById(R.id.friend_name);
        resultView = (TextView) findViewById(R.id.result_text);
        
        
        addButton.setOnClickListener(new OnClickListener() { 
        	@Override 
        	public void onClick(View v) {
        		
        		String friendName = friendNameEditText.getText().toString().trim();
        		
        		String result = addRelation(loginName, friendName, loginPassword);
        		}
        		//Intent intent = new Intent(AndroidWSNewClientActivity.this, VideoRecordActivity.class);
				//startActivity(intent);
        	}
        ); 
	    
        cancelButton.setOnClickListener(new OnClickListener() { 
        	@Override 
        	public void onClick(View v) {
        		Intent intent1 = new Intent(AddRelationActivity.this, CommandTerminalActivity.class);
    			intent1.putExtra("loginName", loginName);
    			intent1.putExtra("loginPassword", loginPassword);
    			startActivityForResult(intent1, REQUEST_CODE);
        		}
        		//Intent intent = new Intent(AndroidWSNewClientActivity.this, VideoRecordActivity.class);
				//startActivity(intent);
        	}
        ); 
    }
	
	
	public String addRelation(String from_user_name, String to_user_name, String from_user_password) {
		String methodName = "addRelation";
		SoapObject rpc = new SoapObject(nameSpace, methodName);
    	// input parameters
    	rpc.addProperty("from_user_name", from_user_name);
    	rpc.addProperty("to_user_name", to_user_name);
    	rpc.addProperty("from_user_password", from_user_password);
		SoapObject object = SOAPMethod.callWebservice(methodName, rpc);
    	String result = object.getProperty("addRelationReturn").toString();
    	return result;
    }
}
