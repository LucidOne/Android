package com.org.iheartengineering.emofaces;

import android.annotation.TargetApi;
import android.app.Activity;
import android.view.Gravity;
import android.widget.*;
import android.widget.RelativeLayout.LayoutParams;

import java.io.*;

/**
 * Android implementation of the Emo-Faces Program
 * @author Errol Markland
 *
 */
public class EmoFacesAndroid {
	EmoFaces emotions = null;
	TextView textView = null;
	Activity activity = null;
	RelativeLayout layout = null;
	public boolean randomFaces = false;
	int interval = 1000;
	public EmoFacesAndroid() 
	{
	}
	
	public void Init(Activity activity, RelativeLayout layout)
	{
		this.activity = activity;
		this.layout = layout;
		if (emotions == null) {
			emotions = new EmoFaces();
			// Load default emotions // Also from file
			LoadDefaultEmotions();
			//layout.removeAllViews();
			emotions.currentEmotion = emotions.emotions.get("default");
			layout.addView(GetCurrentEmotion(), setLayoutParams());
			activity.setContentView(layout);
			// Add action listeners
			InitializeButtons();
			
			if (this.randomFaces) {
				if (t== null) {
					this.activity.runOnUiThread(t = new RandomFaceThread(this));
					t.start();
				}
			}
		}
	}
	
	private RelativeLayout.LayoutParams setLayoutParams() 
    {
    	RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT, 1);        
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL, 1);
        lp.addRule(RelativeLayout.CENTER_VERTICAL, 1);
        return lp;
    }
	
	public void SetRandomFaces(boolean isRandom, int interval) {
		this.randomFaces = isRandom;
		this.interval = interval;
	}
	
	public void SetEmotion(String name) {
		if (emotions.emotions.containsKey(name)) {
			emotions.currentEmotion = emotions.emotions.get(name);
		}
	}
	
	private void LoadDefaultEmotions() {
		try {
			InputStream s = this.activity.getAssets().open("face_packs/default.json");
			emotions.addEmotionsFromFile(s);
		} catch (IOException e) {
			System.out.println("Failed to load face packs");
			e.printStackTrace();
		}
	}
		
	private void InitializeButtons() {
		//TODO: Do stuff when setting button is pressed
		//textView = (Button) findViewById(R.id.button1);
	}
		
	@TargetApi(11)
	public TextView GetCurrentEmotion() {
        TextView view = new TextView(this.activity);
        String e = emotions.currentEmotion.Emoticon;
        if (e == null) {
        	e = "N/A";
        }
        view.setText(e);
        view.setTextSize(128.0f);
        view.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        view.setPadding(0, 0, 0, 0);
        return view;
	}
	
	static RandomFaceThread t = null;
	public void Run() {
		
	}
}
