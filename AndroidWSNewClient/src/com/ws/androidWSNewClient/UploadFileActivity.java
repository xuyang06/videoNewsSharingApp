package com.ws.androidWSNewClient;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.*;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.util.Base64;


public class UploadFileActivity extends Activity{
	
	private static final String TAG = "UploadFileActivity";
	static final int REQUEST_CODE = 1;
    
    private String loginName = null;
	private String loginPassword = null;
	private int nameSize = 0;
    private List<String> userNameList = new ArrayList<String>();
    private String videoFilePath = null;
    
    private EditText newsTitleEditText;
    private TextView filePathView;
    private Button uploadButton;
    private Button cancelButton;
    private TextView resultView;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
	    if (extras != null) {
	    	loginName = extras.getString("loginName");
	    	loginPassword = extras.getString("loginPassword");
	    	nameSize = extras.getInt("nameSize");
	    	Log.v(TAG, "nameSize=" + nameSize);
	    	for (int i = 0; i < nameSize; i++){
    			String key = "name" + i;
    			Log.v(TAG, "name key" + key);
    			userNameList.add(extras.getString(key));
    		}
	    	videoFilePath = extras.getString("videoPath");
	    }
        setContentView(R.layout.upload);
	    filePathView = (TextView) findViewById(R.id.file_path);
	    filePathView.setText("begin:"+videoFilePath+"");
	    newsTitleEditText = (EditText) findViewById(R.id.news_title);
	    uploadButton = (Button) findViewById(R.id.upload_btn);
	    cancelButton = (Button) findViewById(R.id.cancel_btn);
	    resultView = (TextView) findViewById(R.id.result_text);
	    
	    uploadButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	String newsTitle = newsTitleEditText.getText().toString().trim();
            	if ("".equals(newsTitle)) {
    			// show Errors
            		newsTitleEditText.setError("You need input news title!");
            		newsTitleEditText.requestFocus();
    			// clear TextView
            		resultView.setText("");
            		return;
            	}
            	
            	File file = new File(videoFilePath);
            	byte[] message = Byte_File_Object.getBytesFromFile(file);
            	String messageString = Base64.encodeToString(message, Base64.DEFAULT);
            	for (int i=0; i < nameSize; i++){
            		String result = addMessage(newsTitle, loginName, 
            				loginPassword, userNameList.get(i), messageString);
            	}
            	
            	Intent intent = new Intent(UploadFileActivity.this, CommandTerminalActivity.class);
	            intent.putExtra("loginName", loginName);
	            intent.putExtra("loginPassword", loginPassword);
	        		//intent.putExtra("videoPath", videoFilePath);
	            startActivityForResult(intent, REQUEST_CODE);              
            }
        });
	    
	    
	    
	    
	    cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
	             Intent intent = new Intent(UploadFileActivity.this, CommandTerminalActivity.class);
	             intent.putExtra("loginName", loginName);
	             intent.putExtra("loginPassword", loginPassword);
	        		//intent.putExtra("videoPath", videoFilePath);
	             startActivityForResult(intent, REQUEST_CODE);             
            }
        });
    }
    
    private String addMessage(String message_title, String from_user_name, 
    		String from_user_password, String to_user_name, String message) {
    	String methodName = "addMessage";
		SoapObject rpc = new SoapObject(nameSpace, methodName);
    	// input parameters
		rpc.addProperty("message_title", message_title);
    	rpc.addProperty("from_user_name", from_user_name);
    	rpc.addProperty("from_user_password", from_user_password);
    	rpc.addProperty("to_user_name", to_user_name);
    	rpc.addProperty("message", message);
		SoapObject object = SOAPMethod.callWebservice(methodName, rpc);
    	String result = object.getProperty("addMessageReturn").toString();
    	resultView.setText(result);
    	return result;
    }
    
    
	
}
