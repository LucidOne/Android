package com.org.iheartrobotics.test.androidtcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import com.org.iheartrobotics.Ros.*;
import com.org.iheartrobotics.test.androidtcp.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.widget.*;

public class AndroidTCPDriver extends Activity {

	static TcpRosClient client = null;
	static TextView textView = null;
	static RelativeLayout relativeLayout = null;
	
	private BufferedReader reader = null;
	private DataOutputStream writer = null;
	private Socket serverSocket = null;
	
	private String connectionString = "192.168.1.6";
	private int port = 6789;
	
	private AsyncTask appThread = new AsyncTask() {
		@Override
		protected Object doInBackground(Object... params) {
			try {
	    		// TODO Auto-generated method stub
	    		// Establish the initial handshake with the server in question
	    		serverSocket = new Socket(connectionString, port);
	    		reader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
	    		writer = new DataOutputStream(serverSocket.getOutputStream());
	    		
	    		// Send a greeting to the client
	    		//TODO: Write a proper message to the ros server! (using the ROS/TCP connection guide)
	    		/*
	    		callerid: name of subscriber
	    		topic: name of the topic the subscriber is connecting to
	    		md5sum: md5sum of the message type
	    		type: message type
	    		*/
	    		
	    		writer.writeBytes("howdy\n");
	    		writer.flush();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// TODO Auto-generated method stub
			return null;
		}
		
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        
        
    	if (relativeLayout == null) {
    		relativeLayout = new RelativeLayout(this);
    	}
    	setContentView(relativeLayout);
    	
    	if (textView == null) {
    		TextView view = new TextView(this);
            /*String e = emotions.currentEmotion.Emoticon;
            if (e == null) {
            	e = "N/A";
            }
            view.setText(e);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            view.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            view.setPadding(0, 0, 0, 0);
            return view;*/
    	}
    	
    	appThread.execute(new Object());	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_android_tcpdriver, menu);
        return true;
    }
}
