package com.org.iheartengineering.emofaces;

import java.util.Random;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class RandomFaceThread extends Thread 
{		
	public Handler handler;
	EmoFacesAndroid app = null;
	Random rand = new Random();
	String[] faceSet = null;
	
	public RandomFaceThread(EmoFacesAndroid app) {
		this.app = app;
		faceSet = app.emotions.emotions.keySet().toArray(new String[0]);
	}
	
	public void run() {
		System.out.print("testing run");
		handler = new Handler(Looper.getMainLooper()) {
			public void handleMessage(Message msg) {					
				// Select face from random.
				int i = -1;
				while (i < 0) {
					i = rand.nextInt(faceSet.length) - 1;
					System.out.print(i);
				}
				
				app.emotions.currentEmotion = app.emotions.getEmotion(faceSet[i]);
				app.textView.setText(app.emotions.currentEmotion.Emoticon);

				try {
					Thread.sleep(app.interval);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		Looper.loop();
	}
}