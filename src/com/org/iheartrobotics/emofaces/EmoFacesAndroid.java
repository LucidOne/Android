package com.org.iheartrobotics.emofaces;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.*;
import android.widget.RelativeLayout.LayoutParams;

import java.io.*;
import java.util.Random;

/**
 * Android implementation of the Emo-Faces Program
 * @author Errol Markland
 * TODO: Improve this....overall structure...it's rather messy =x
 */
public class EmoFacesAndroid {
	EmoFaces emotions = null;
	TextView textView = null;
	Activity activity = null;
	RelativeLayout layout = null;
	public boolean randomFaces = false;
	public int interval = 1500;
	public Handler handle = null;
	Random random = new Random();
	
	public EmoFacesAndroid(Handler handle) 
	{
		this.handle = handle; 
	}
	
	public void Init(Activity activity, RelativeLayout layout)
	{
		this.activity = activity;
		this.layout = layout;
		if (emotions == null) {
			emotions = new EmoFaces();
			// Load default emotions
			LoadDefaultEmotions();
			emotions.currentEmotion = emotions.emotions.get("default");
			textView = GetCurrentEmotion();
			layout.addView(textView, setLayoutParams());
			activity.setContentView(layout);
			
			setListeners();
		}
	}
	
	void setListeners() {
		// random face guy
		handle.postDelayed(new Runnable() {
			public void run() {
				update();
				handle.postDelayed(this, interval);
			}
		}, interval);
		
		layout.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					update();
					return true;
				}
				return false;
			}
		});
	}

	/**
	 * Sets the next random face.
	 */
	private void update() {
		String[] keys = emotions.emotions.keySet().toArray(new String[0]);
		int i = -1;
		while (i < 0) {
			i = random.nextInt(keys.length) - 1;
		}
		String nextEmo = keys[i];
		emotions.currentEmotion = emotions.emotions.get(nextEmo);
		textView.setText(emotions.currentEmotion.Emoticon);
		activity.setContentView(layout);
	}
	
	private RelativeLayout.LayoutParams setLayoutParams() 
    {
    	RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT, 1);        
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL, 1);
        lp.addRule(RelativeLayout.CENTER_VERTICAL, 1);
        return lp;
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
}
