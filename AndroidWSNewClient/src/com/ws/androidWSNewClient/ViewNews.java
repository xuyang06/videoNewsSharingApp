package com.ws.androidWSNewClient;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.*;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.util.Base64;

public class ViewNews extends Activity{
	//private ListView listView;
	//private String[] newsList = null;
	private String loginName = null;
	private String loginPassword = null;
	private String newsTitle =null;
	//private ListView contactsList;
	private Button morenewsBtn;
	private Button cancelBtn;	
	private TextView newTitleView;
	

    static final int REQUEST_CODE = 1;
    
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
	    if (extras != null) {
	    	loginName = extras.getString("loginName");
	    	loginPassword = extras.getString("loginPassword");
	    	newsTitle = extras.getString("newsTitle");
	    	
	    }
        setContentView(R.layout.viewnews);
        //buttonView=(Button)findViewById(R.id.view);
        morenewsBtn=(Button)findViewById(R.id.more);
        cancelBtn = (Button)findViewById(R.id.cancel);
        newTitleView = (TextView) findViewById(R.id.news_title);
        newTitleView.setText(newsTitle);
        
        String message = getMessage(loginName, loginPassword, newsTitle);
        byte[] buffer = Base64.decode(message, Base64.DEFAULT);
        File defaultDir = Environment.getExternalStorageDirectory();
        String dirPath = defaultDir.getAbsolutePath()+File.separator+"V"+File.separator; 
        File dir = new File(dirPath);
        if(!dir.exists()){
        	dir.mkdir();
        }
        String tempFilePath = dirPath + "received.3gp";
        File temp = new File(tempFilePath);
        if(temp.exists()){
        	temp.delete();
        }
        File file = Byte_File_Object.getFileFromBytes(buffer, tempFilePath);
        VideoView videoView = (VideoView) findViewById(R.id.VideoView);
    	MediaController mediaController = new MediaController(this);
        
        mediaController.setAnchorView(videoView);
        //Set video link (mp4 format )
        Uri video = Uri.parse(tempFilePath);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(video);
        videoView.requestFocus();
        videoView.start();
        
        
        
        
        morenewsBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent intent = new Intent(ViewNews.this, ViewAllNewsActivity.class);
            	intent.putExtra("loginName", loginName);
        		intent.putExtra("loginPassword", loginPassword);
        		//int nameLen = Integer.valueOf(nameSize).intValue();
        		startActivityForResult(intent, 1);
            }
        });
        
        cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent intent = new Intent(ViewNews.this, CommandTerminalActivity.class);
            	intent.putExtra("loginName", loginName);
        		intent.putExtra("loginPassword", loginPassword);
        		startActivityForResult(intent, 2);
            }
        });
    }

    private String getMessage(String user_name, String user_password, String message_title) {
    	String methodName = "getMessage";
		SoapObject rpc = new SoapObject(nameSpace, methodName);
    	// input parameters
		rpc.addProperty("user_name", user_name);
    	rpc.addProperty("user_password", user_password);
    	rpc.addProperty("message_title", message_title);
		SoapObject object = SOAPMethod.callWebservice(methodName, rpc);
    	String result = object.getProperty("getMessageReturn").toString();
    	return result;
    }
}
