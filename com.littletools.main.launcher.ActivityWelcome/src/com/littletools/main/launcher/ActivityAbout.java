package com.littletools.main.launcher;

import com.littletools.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Gallery;
import android.widget.ImageView;

public class ActivityAbout extends Activity {
	private ImageView iv_back = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_about);
		
		initViews();
		addListeners();
		
	}

	private void addListeners() {

		iv_back.setOnClickListener(new ListenerOnBackClick(this));		
		
	}

	private void initViews() {
		
		iv_back = (ImageView)findViewById(R.id.mAboutBack);
		
	}	

}
