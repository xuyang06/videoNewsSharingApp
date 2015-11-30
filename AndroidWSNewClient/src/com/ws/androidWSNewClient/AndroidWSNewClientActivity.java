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

public class AndroidWSNewClientActivity extends Activity {
	/** Called when the activity is first created. */
	private EditText loginNameEditText;
	private EditText loginPasswordEditText;
	private TextView resultView;
	private Button loginButton;
	private Button signupButton;
	static final int REQUEST_CODE = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        loginNameEditText = (EditText) findViewById(R.id.login_name);
        loginPasswordEditText = (EditText) findViewById(R.id.login_password);
        resultView = (TextView) findViewById(R.id.result_text);
        loginButton = (Button) findViewById(R.id.login_btn);
        signupButton = (Button) findViewById(R.id.sign_up_btn);
        loginButton.setOnClickListener(new OnClickListener() { 
        	@Override 
        	public void onClick(View v) {
        		
        		String loginName = loginNameEditText.getText().toString().trim();
        		String loginPassword = loginPasswordEditText.getText().toString().trim();
        		String loginInfo = loginInInfo(loginName, loginPassword);
        		
        		if (loginInfo.equals("1")){
        			Intent intent1 = new Intent(AndroidWSNewClientActivity.this, CommandTerminalActivity.class);
        			intent1.putExtra("loginName", loginName);
    				intent1.putExtra("loginPassword", loginPassword);
    				startActivityForResult(intent1, REQUEST_CODE);
        		}else{
        			Intent falseIntent = new Intent(AndroidWSNewClientActivity.this, LoginUnsuccessful.class);
    				startActivity(falseIntent);
        		}
        	}
        });
        signupButton.setOnClickListener(new OnClickListener() { 
        	@Override 
        	public void onClick(View v) {
        		Intent intent = new Intent(AndroidWSNewClientActivity.this, SignUpActivity.class);
				startActivity(intent);
        	}
        });
        
    }
    
    
    public String loginInInfo(String loginName, String loginPassword) {
    	String methodName = "logIn";
		SoapObject rpc = new SoapObject(nameSpace, methodName);
    	// input parameters
		rpc.addProperty("user_name", loginName);
    	rpc.addProperty("user_password", loginPassword);
		SoapObject object = SOAPMethod.callWebservice(methodName, rpc);
    	String result = object.getProperty("logInReturn").toString();
    	// show result
    	resultView.setText(result);
    	return result;
    }
}