package com.org.iheartengineering.emofaces;

import android.annotation.TargetApi;
import android.app.Activity;
import android.view.Gravity;
import android.widget.*;

import java.util.*;

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
		emotions = new EmoFaces();
		// Load default emotions // Also from file
		LoadDefaultEmotions();
		
		// Load files from pictures directory
		
		//TODO: Create directory to load from
		/*for(String file : files) {
			// do stuff
		}*/
		
		String firstEmo = emotions.emotions.keySet().iterator().next();
		emotions.currentEmotion = emotions.emotions.get(firstEmo);
		// Add action listeners
		InitializeButtons();
	}
	
	public void SetEmotion(String name) {
		if (emotions.emotions.containsKey(name)) {
			emotions.currentEmotion = emotions.emotions.get(name);
		}
	}
	
	
	private void LoadDefaultEmotions() {
		ArrayList<Emo> emos = new ArrayList<Emo>();
		emos.add(new Emo("Excited", ":D"));
		emos.add(new Emo("Happy", ":)"));
		emos.add(new Emo("Indifferent", ":|"));
		emos.add(new Emo("Sad", ":("));
		emos.add(new Emo("Angry", ">:O"));
		emotions.addEmotions(emos);
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
        view.setTextSize(192.0f);
        view.setRotation(90);
        view.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        view.setPadding(0, 0, 0, 0);
        return view;
	}
}
