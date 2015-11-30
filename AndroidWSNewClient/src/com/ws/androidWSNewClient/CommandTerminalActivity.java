package com.ws.androidWSNewClient;

import java.util.StringTokenizer;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class CommandTerminalActivity extends Activity{
	private String loginName = null;
	private String loginPassword = null;
	private Button addFri_Button;
	private Button viewnews_Button;
	private Button broadcastnews_Button;
	static final int REQUEST_CODE = 1;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.commandterminal);
		Bundle extras = getIntent().getExtras();
	    if (extras != null) {
	    	loginName = extras.getString("loginName");
	    	loginPassword = extras.getString("loginPassword");
	    }
	    addFri_Button = (Button) findViewById(R.id.addfriend_btn);
	    viewnews_Button = (Button) findViewById(R.id.viewnews_btn);
	    broadcastnews_Button = (Button) findViewById(R.id.broadcastnews_btn);
	    
	    addFri_Button.setOnClickListener(new OnClickListener() { 
        	@Override 
        	public void onClick(View v) {
        		Intent intent1 = new Intent(CommandTerminalActivity.this, AddRelationActivity.class);
    			intent1.putExtra("loginName", loginName);
    			intent1.putExtra("loginPassword", loginPassword);
    			startActivityForResult(intent1, REQUEST_CODE);
        		}
        		
        	}
        ); 
	    
	    
	    viewnews_Button.setOnClickListener(new OnClickListener() { 
        	@Override 
        	public void onClick(View v) {
        		Intent intent1 = new Intent(CommandTerminalActivity.this, ViewAllNewsActivity.class);
    			intent1.putExtra("loginName", loginName);
    			intent1.putExtra("loginPassword", loginPassword);
    			startActivityForResult(intent1, REQUEST_CODE);
        		}
        		
        	}
        ); 
	    
	    broadcastnews_Button.setOnClickListener(new OnClickListener() { 
        	@Override 
        	public void onClick(View v) {
        		Intent intent1 = new Intent(CommandTerminalActivity.this, BuddyListShowActivity.class);
    			intent1.putExtra("loginName", loginName);
    			intent1.putExtra("loginPassword", loginPassword);
    			startActivityForResult(intent1, REQUEST_CODE);
        		}
        		
        	}
        ); 
    }
}
