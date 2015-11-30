package com.ws.androidWSNewClient;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

import android.widget.VideoView;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoPlayActivity extends Activity  {
	private int width = 0;
	private int height = 0;
	private MediaPlayer mp = null;
	private SurfaceView surfaceView = null;
	private SurfaceHolder holder = null;
	private String loginName = null;
	private String loginPassword = null;
	private int nameSize = 0;
    private List<String> userNameList = new ArrayList<String>();
    private String videoFilePath = null;
    private Button buttonUpload;
    //private Button buttonView;
    //private Button buttonStop;
    private Button buttonCancel;
    static final int REQUEST_CODE = 1;
    
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
	    if (extras != null) {
	    	loginName = extras.getString("loginName");
	    	loginPassword = extras.getString("loginPassword");
	    	videoFilePath = extras.getString("videoPath");
	    	nameSize = extras.getInt("nameSize");
	    	for (int i = 0; i < nameSize; i++){
    			String key = "name" + i;
    			userNameList.add(extras.getString(key));
    		}
	    }
        setContentView(R.layout.video_play);
        
        //buttonView=(Button)findViewById(R.id.view);
        buttonUpload=(Button)findViewById(R.id.upload);
        buttonCancel = (Button)findViewById(R.id.cancel);
        VideoView videoView = (VideoView) findViewById(R.id.VideoView);
    	MediaController mediaController = new MediaController(this);
        
        mediaController.setAnchorView(videoView);
        //Set video link (mp4 format )
        Uri video = Uri.parse(videoFilePath);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(video);
        videoView.requestFocus();
        videoView.start();
        
        
        
        
        buttonUpload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent intent = new Intent(VideoPlayActivity.this, UploadFileActivity.class);
            	intent.putExtra("loginName", loginName);
        		intent.putExtra("loginPassword", loginPassword);
        		intent.putExtra("nameSize", nameSize);
        		//int nameLen = Integer.valueOf(nameSize).intValue();
        		for (int i = 0; i < nameSize; i++){
        			String key = "name" + i;
        			intent.putExtra(key, userNameList.get(i));
        		}
        		intent.putExtra("videoPath", videoFilePath);
        		startActivityForResult(intent, 1);
            }
        });
        
        buttonCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent intent = new Intent(VideoPlayActivity.this, VideoRecordActivity.class);
            	intent.putExtra("loginName", loginName);
        		intent.putExtra("loginPassword", loginPassword);
        		intent.putExtra("nameSize", nameSize);
        		//int nameLen = Integer.valueOf(nameSize).intValue();
        		for (int i = 0; i < nameSize; i++){
        			String key = "name" + i;
        			intent.putExtra(key, userNameList.get(i));
        		}
        		//intent.putExtra("videoPath", videoFilePath);
        		startActivityForResult(intent, 2);
            }
        });
        
    }
    
    
	
}
