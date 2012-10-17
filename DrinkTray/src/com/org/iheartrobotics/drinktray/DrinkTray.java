package com.org.iheartrobotics.drinktray;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.*;
import android.view.View.OnTouchListener;
import android.widget.*;
import android.widget.RelativeLayout.LayoutParams;

import java.io.*;
import java.util.*;


/**
 * Android implementation of the Emo-Faces Program
 * @author Errol Markland
 */
public class DrinkTray extends Activity {
	
	private RelativeLayout relativeLayout = null;
	private Handler handler = null;
	TextView textView = null;
	EmoFaces emotions = null;
	
	static Random random = new Random();
	static int fontSize = 128;
	int interval = 1500;
	
	Runnable appThread = new Runnable() {
		public void run() {
			update();
			handler.postDelayed(this, interval);
		}
	};
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    try {
			Init();
		} catch (EmoFacesException e) {
			e.printStackTrace();
			if (handler != null) {
				Stop();
			} else {
				finish();
			}
		}
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
        
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
    	int scale = 32;
        switch (item.getItemId()) {
        	case R.id.quit:
        		Stop();
        		return true;
        	case R.id.bigger:
        		changeFontSize(scale);
        		return true;
        	case R.id.smaller:
        		changeFontSize(-scale);
        		return true;        		
        }
        return false;
    }
    
    public void Init() throws EmoFacesException
	{
		if (emotions == null) {
	        relativeLayout = new RelativeLayout(this);
	        handler = new Handler();
			emotions = new EmoFaces();
/*
			// Load default emotions and set it
			LoadDefaultEmotions();
			textView = getCurrentEmotion();
		  	RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	        lp.addRule(RelativeLayout.CENTER_IN_PARENT, 1);        
	        lp.addRule(RelativeLayout.CENTER_HORIZONTAL, 1);
	        lp.addRule(RelativeLayout.CENTER_VERTICAL, 1);
			relativeLayout.addView(textView, lp);
			setContentView(relativeLayout);
			
			// Set listeners
			handler.postDelayed(appThread, interval);
			relativeLayout.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						update();
						return true;
					}
					return false;
				}
			});
			*/
			showImage();
		}
	}
    
    /**
	 * Loads the default emotions.
     * @throws EmoFacesException 
	 */
	private void LoadDefaultEmotions() throws EmoFacesException {
		try {
			EmoFaceReader r = new EmoFaceReader();
			for (Emo emo : r.readEmoticonsFromStream(new InputStreamReader(getAssets().open("face_packs/default.json")))) {
				emotions.addEmotion(emo);
			}
			emotions.currentEmotion = emotions.emotions.get("default");
		} catch (IOException e) {
			throw EmoFacesException.InitError(e);
		}
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
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
		setContentView(relativeLayout);
	}
		
	@TargetApi(11)
	public TextView getCurrentEmotion() {
		TextView view = new TextView(this);
        String e = emotions.currentEmotion.Emoticon;
        if (e == null) {
        	e = "N/A";
        }
        view.setText(e);
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        view.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        view.setPadding(0, 0, 0, 0);
        return view;
	}
	
	/**
	 * Set the current emotion
	 * @param name - The name of the emotion to set.
	 * @return - True, if the current emotion was changed. False, if otherwise.
	 */
	public boolean setCurrentEmotion(String name) {
		if (emotions.emotions.containsKey(name)) {
			emotions.currentEmotion = emotions.emotions.get(name);
			return true;
		}
		return false;
	}
	
	/**
	 * Adds a new emotion to the app.
	 * @param name - The name of the emotion to be added
	 * @param emoticon - The textual representation of the emotion
	 * @return - True, if the emotion was added. False, if otherwise.
	 */
	public boolean addEmotion(String name, String emoticon) {
		if (emotions.getAvailableEmotions().contains(name)) {
			return false;
		}
		emotions.addEmotion(Emo.CreateEmo(name, emoticon, null));
		return true;
	}
	
	/**
	 * Removes an existing emotion from the app.
	 * @param name - The name of the emotion to be removed.
	 * @return - True, if the emotion was removed. False if otherwise.
	 */
	public boolean removeEmotion(String name) {
		return emotions.deleteEmo(name);
	}

	
	public void changeFontSize(int fontSizeChange) {
		int scale = 32;
		fontSize += fontSizeChange;
		if (fontSize < scale) {
			fontSize = scale;
		}
	}
	
	/**
	 * Stops the application.
	 */
	public void Stop() {
		handler.removeCallbacks(appThread);
		finish();
	}
	
	ImageView iv;
	@TargetApi(13)
	public void showImage() {
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		
		iv = new ImageView(this);
		Drawable image = getResources().getDrawable(R.drawable.drinktray_01_temp);
		iv.setImageDrawable(image);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width, height);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT, 1);        
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL, 1);
        lp.addRule(RelativeLayout.CENTER_VERTICAL, 1);
		relativeLayout.addView(iv, lp);
		setContentView(relativeLayout);
	}
}
