package com.org.iheartengineering.emofaces;

import android.annotation.TargetApi;
import android.app.Activity;
import android.view.Gravity;
import android.widget.*;
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
	
	public EmoFacesAndroid() 
	{
	}
	
	public void Init(Activity activity)
	{
		this.activity = activity;
		if (emotions == null) {
			emotions = new EmoFaces();
			// Load default emotions // Also from file
			LoadDefaultEmotions();
			emotions.currentEmotion = emotions.emotions.get("default");
			// Add action listeners
			InitializeButtons();
		}
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
}
