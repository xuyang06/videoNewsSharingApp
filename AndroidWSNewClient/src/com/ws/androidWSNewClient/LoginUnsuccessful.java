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


public class LoginUnsuccessful extends Activity {

	private Button returnButton;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginwrong);
        
        returnButton = (Button) findViewById(R.id.return_btn);
        returnButton.setOnClickListener(new OnClickListener() { 
        	@Override 
        	public void onClick(View v) {
        		Intent intent = new Intent(LoginUnsuccessful.this, AndroidWSNewClientActivity.class);
				startActivity(intent);
        	}
        });   
    }
	
	
}
