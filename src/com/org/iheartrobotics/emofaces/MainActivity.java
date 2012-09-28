package com.org.iheartrobotics.emofaces;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.org.iheartrobotics.emofaces.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.*;

public class MainActivity extends Activity {

	private RelativeLayout relativeLayout = null;
	private EmoFacesAndroid app = null;
	private Handler handler = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        relativeLayout = new RelativeLayout(this);
        if (app == null) {
	        app = new EmoFacesAndroid();
	        app.SetRandomFaces(true, 500);
	        app.Init(this, relativeLayout);
	     //   app.Run();
        }
        
        setContentView(relativeLayout);
        handler = new Handler();
        handler.postAtTime(r, 3000);
       // relativeLayout.setOnTouchListener(new TouchListener(app)); 
        //Timer test = new Timer();
        //test.scheduleAtFixedRate(t, 0, 1000);
        
    }
	
	 TimerTask t = new TimerTask() {
		@Override
		public void run() {
			update();
		}
	};
	
	private Runnable r = new Runnable() {
		public void run() {
			update();
			handler.postDelayed(this, 3000);
		}
	};

	 void update() {
		String nextEmo = getNextEmotion(this.app.emotions.emotions.keySet(), this.app.emotions.currentEmotion.name);
		if (nextEmo == null) {
			throw new IllegalArgumentException("Failed to find argument.");
		}
		
		this.app.emotions.currentEmotion = this.app.emotions.emotions.get(nextEmo);
		this.app.textView.setText(this.app.emotions.currentEmotion.Emoticon);
		setContentView(this.app.layout);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add("Select new emotion");
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }
    
    class TouchListener implements OnTouchListener {
    	EmoFacesAndroid app = null;
    	public TouchListener(EmoFacesAndroid app)
    	{
    		this.app = app;
    	}
    	
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				String nextEmo = getNextEmotion(this.app.emotions.emotions.keySet(), this.app.emotions.currentEmotion.name);
				if (nextEmo == null) {
					throw new IllegalArgumentException("Failed to find argument.");
				}
				this.app.emotions.currentEmotion = this.app.emotions.emotions.get(nextEmo);
				this.app.textView.setText(this.app.emotions.currentEmotion.Emoticon);
				setContentView(this.app.layout);
				return true;
			}
			return false;
		}
    }
    
    private String getNextEmotion(Set<String> keySet, String currentEmotion) {
		String[] keys = keySet.toArray(new String[0]);
		for (int i = 0; i < keys.length; i++) {
			if (keys[i].equals(currentEmotion)) {
				return keys[(i + 1) % keys.length];
			}
		}
		return "";
	}
}
