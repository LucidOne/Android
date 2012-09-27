package com.org.iheartengineering.emofaces;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.*;

public class MainActivity extends Activity {

	private RelativeLayout relativeLayout = null;
	private EmoFacesAndroid app = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        relativeLayout = new RelativeLayout(this);
        if (app == null) {
	        app = new EmoFacesAndroid();
	        app.SetRandomFaces(true, 500);
	        app.Init(this, relativeLayout);	      
        }
        
        setContentView(relativeLayout);
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
}
