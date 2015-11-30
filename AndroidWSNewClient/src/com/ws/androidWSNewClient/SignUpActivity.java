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


public class SignUpActivity extends Activity{
	private EditText nameEditText;
	private EditText passwordEditText;
	private EditText rePasswordEditText;
	private TextView resultView;
	private Button signupButton;
	private Button cancelButton;
	static final int REQUEST_CODE = 1;
	//private String name = null;
	//private String password = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        nameEditText = (EditText) findViewById(R.id.login_name);
        passwordEditText = (EditText) findViewById(R.id.login_password1);
        rePasswordEditText = (EditText) findViewById(R.id.login_password2);
        resultView = (TextView) findViewById(R.id.result_text);
        signupButton = (Button) findViewById(R.id.sign_up_btn);
        cancelButton = (Button) findViewById(R.id.cancel_btn);
        
        
        signupButton.setOnClickListener(new OnClickListener() { 
        	@Override 
        	public void onClick(View v) {
        		
        		String loginName = nameEditText.getText().toString().trim();
        		String loginPassword1 = passwordEditText.getText().toString().trim();
        		String loginPassword2 = rePasswordEditText.getText().toString().trim();
        		if ( !loginPassword1.equals(loginPassword2)) {
        			// show errors
        			passwordEditText.setError("Your passwords are not the same£¡");
        			passwordEditText.requestFocus();
        			resultView.setText("");
        			return;
        		}
        		
        		String signupInfo = addUser(loginName, loginPassword1);
        		
        		String loginPassword = loginPassword1;
        		/*if (loginInfo.equals("1")){
        			Intent intent1 = new Intent(AndroidWSNewClientActivity.this, CommandTerminalActivity.class);
    				intent1.putExtra("loginName", loginName);
    				intent1.putExtra("loginPassword", loginPassword);
    				startActivityForResult(intent1, REQUEST_CODE);
        		}else{
        			Intent falseIntent = new Intent(AndroidWSNewClientActivity.this, LoginUnsuccessful.class);
    				startActivity(falseIntent);
        		}*/
        		
        		/*Intent intent = new Intent(AndroidWSNewClientActivity.this, VideoRecordActivity.class);
				startActivity(intent);*/
        		Intent intent1 = new Intent(SignUpActivity.this, AddRelationActivity.class);
				intent1.putExtra("loginName", loginName);
				intent1.putExtra("loginPassword", loginPassword);
				startActivityForResult(intent1, REQUEST_CODE);
        		
        		
        		
        		
        	}
        });
        cancelButton.setOnClickListener(new OnClickListener() { 
        	@Override 
        	public void onClick(View v) {
        		Intent intent = new Intent(SignUpActivity.this, AndroidWSNewClientActivity.class);
				startActivity(intent);
        	}
        });
    }
    
   
    public String addUser(String user_name, String user_password) {
    	String methodName = "addUser";
		SoapObject rpc = new SoapObject(nameSpace, methodName);
    	// input parameters
		rpc.addProperty("user_name", loginName);
    	rpc.addProperty("user_password", loginPassword);
		SoapObject object = SOAPMethod.callWebservice(methodName, rpc);
    	String result = object.getProperty("addUserReturn").toString();
    	resultView.setText(result);
    	return result;
    }
}
