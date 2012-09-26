package com.org.iheartengineering.emofaces;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.*;
import android.widget.RelativeLayout.LayoutParams;

public class MainActivity extends Activity {

	private RelativeLayout relativeLayout = null;
	private EmoFacesAndroid app = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = new EmoFacesAndroid();
        app.Init(this);
        relativeLayout = new RelativeLayout(this);
        app.SetEmotion("Sad");
        relativeLayout.addView(app.GetCurrentEmotion(), setLayoutParams());
        setContentView(relativeLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add("Select new emotion");
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private RelativeLayout.LayoutParams setLayoutParams() 
    {
    	RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT, 1);        
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL, 1);
        lp.addRule(RelativeLayout.CENTER_VERTICAL, 1);
        return lp;
    }
}
