package com.ws.androidWSNewClient;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import java.util.*;

public class VideoRecordActivity extends Activity {

	
	private static final String TAG = "VideoRecordActivity";
	static final int REQUEST_CODE = 1;
    private File myRecAudioFile;
    private SurfaceView mSurfaceView;   
    private SurfaceHolder mSurfaceHolder; 
    private Button buttonStart;
    private Button buttonStop;
    private Button buttonCancel;
    private File dir;
    private MediaRecorder recorder;
    
    private String loginName = null;
	private String loginPassword = null;
	private int nameSize = 0;
    private List<String> userNameList = new ArrayList<String>();
    private String videoFilePath = null;
    
    
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
	    }
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.video_record);
        mSurfaceView = (SurfaceView) findViewById(R.id.videoView);   
        mSurfaceHolder = mSurfaceView.getHolder();   
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); 
        buttonStart=(Button)findViewById(R.id.start);
        buttonStop=(Button)findViewById(R.id.stop);
        buttonCancel = (Button)findViewById(R.id.cancel);
        File defaultDir = Environment.getExternalStorageDirectory();
        String path = defaultDir.getAbsolutePath()+File.separator+"V"+File.separator;//创建文件夹存放视频
        dir = new File(path);
        if(!dir.exists()){
        	dir.mkdir();
        }
        recorder = new MediaRecorder();
        
        buttonStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	recorder();
            	buttonStop.setEnabled(true);
            }
        });
        
        buttonStop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
	             recorder.stop();
	             recorder.reset();
	             recorder.release();
	             recorder=null;
	             Intent intent = new Intent(VideoRecordActivity.this, VideoPlayActivity.class);
	        		intent.putExtra("loginName", loginName);
	        		intent.putExtra("loginPassword", loginPassword);
	        		intent.putExtra("nameSize", nameSize);
	        		//int nameLen = Integer.valueOf(nameSize).intValue();
	        		for (int i = 0; i < nameSize; i++){
	        			String key = "name" + i;
	        			intent.putExtra(key, userNameList.get(i));
	        		}
	        		intent.putExtra("videoPath", videoFilePath);
	        		startActivityForResult(intent, REQUEST_CODE);
	             
            }
        });
        
        
        buttonCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
	             Intent intent = new Intent(VideoRecordActivity.this, CommandTerminalActivity.class);
	        		intent.putExtra("loginName", loginName);
	        		intent.putExtra("loginPassword", loginPassword);
	        		intent.putExtra("nameSize", nameSize);
	        		//int nameLen = Integer.valueOf(nameSize).intValue();
	        		for (int i = 0; i < nameSize; i++){
	        			String key = "name" + i;
	        			intent.putExtra(key, userNameList.get(i));
	        		}
	        		startActivityForResult(intent, REQUEST_CODE);
	             
            }
        });
    }
    
    
    
    public void recorder() {
        try {
            myRecAudioFile = File.createTempFile("video", ".3gp",dir);//create temp file
            videoFilePath = myRecAudioFile.getAbsolutePath();
            recorder.setPreviewDisplay(mSurfaceHolder.getSurface());//preview
            recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);//video
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC); //audio source
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//output format 3gp
            recorder.setVideoSize(640, 480);
            recorder.setVideoFrameRate(15);// frame rate
            recorder.setVideoEncoder(MediaRecorder.VideoEncoder.H263);//video codec
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//audio codec
            recorder.setMaxDuration(10000);//maximum duration
            recorder.setOutputFile(myRecAudioFile.getAbsolutePath());//stored path
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

