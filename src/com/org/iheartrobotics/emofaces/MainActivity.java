package com.org.iheartrobotics.emofaces;

import java.util.Random;
import java.util.Set;
import com.org.iheartrobotics.emofaces.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.*;

public class MainActivity extends Activity {

	private RelativeLayout relativeLayout = null;
	private EmoFacesAndroid app = null;
	private Handler handler = null;
	static Random random = new Random();
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        relativeLayout = new RelativeLayout(this);
        handler = new Handler();
        if (app == null) {
	        app = new EmoFacesAndroid(handler);
	        app.Init(this, relativeLayout);
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
    	
        switch (item.getItemId()) {
        	case R.id.quit:
        		app.Stop();
        		return true;
        }
        return false;
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
		int i = -1;
		while (i < 0) {
			i = random.nextInt(keys.length) - 1;
		}
		return keys[i];
	}
}
