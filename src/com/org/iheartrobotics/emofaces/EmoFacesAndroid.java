package com.org.iheartrobotics.emofaces;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.*;
import android.widget.RelativeLayout.LayoutParams;

import java.io.*;
import java.util.Random;
import java.util.Set;

/**
 * Android implementation of the Emo-Faces Program
 * @author Errol Markland
 * TODO: Improve this....overall structure...it's rather messy =x
 */
public class EmoFacesAndroid extends Activity {
	public static int fontSize = 128;
	EmoFaces emotions = null;
	TextView textView = null;
	public boolean randomFaces = false;
	public int interval = 1500;
	Runnable r = new Runnable() {
		public void run() {
			update();
			handler.postDelayed(this, interval);
		}
	};
	
	private RelativeLayout relativeLayout = null;
	private Handler handler = null;
	static Random random = new Random();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    Init();
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
        		fontSize += scale;
        		return true;
        	case R.id.smaller:
        		fontSize -= scale;
        		if (fontSize < 0) {
        			fontSize = scale;
        		}
        		return true;        		
        }
        return false;
    }
    
    class TouchListener implements OnTouchListener {
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				String nextEmo = getNextEmotion(emotions.emotions.keySet(), emotions.currentEmotion.name);
				if (nextEmo == null) {
					throw new IllegalArgumentException("Failed to find argument.");
				}
				emotions.currentEmotion = emotions.emotions.get(nextEmo);
				textView.setText(emotions.currentEmotion.Emoticon);
				setContentView(relativeLayout);
				return true;
			}
			return false;
		}
    }
    
    private String getNextEmotion(Set<String> keySet, String currentEmotion) {
		String[] keys = keySet.toArray(new String[0]);
		int i = -1;
		while (i < 0) {
			i = random.nextInt(keys.length) - 1;
		}
		return keys[i];
	}
	
	public void Init()
	{
		if (emotions == null) {
	        relativeLayout = new RelativeLayout(this);
	        handler = new Handler();
			emotions = new EmoFaces();
			// Load default emotions
			LoadDefaultEmotions();
			emotions.currentEmotion = emotions.emotions.get("default");
			textView = GetCurrentEmotion();
			relativeLayout.addView(textView, setLayoutParams());
			setContentView(relativeLayout);
			
			setListeners();
		}
	}
	
	void setListeners() {
		// random face guy
		handler.postDelayed(new Runnable() {
			public void run() {
				update();
				handler.postDelayed(this, interval);
			}
		}, interval);
		
		relativeLayout.setOnTouchListener(new OnTouchListener() {
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
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
		setContentView(relativeLayout);
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
			InputStream s = getAssets().open("face_packs/default.json");
			emotions.addEmoList(s);
		} catch (IOException e) {
			System.out.println("Failed to load face packs");
			e.printStackTrace();
		}
	}
		
	@TargetApi(11)
	public TextView GetCurrentEmotion() {
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
	 * Stops the application.
	 */
	public void Stop() {
		handler.removeCallbacks(r);
		finish();
	}
}
