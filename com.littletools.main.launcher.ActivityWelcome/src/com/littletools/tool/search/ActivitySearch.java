package com.littletools.tool.search;

import com.littletools.R;
import com.littletools.main.launcher.ListenerOnTilesClick;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class ActivitySearch extends Activity {

	private ImageView searchExp;
	private ImageView searchSize;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_search);
		
		init();
		
		addListeners();
		
	}
	
	private void addListeners() {

		searchExp.setOnClickListener(new ListenerOnTilesClick(this, ActivityEMS.class));
		searchSize.setOnClickListener(new ListenerOnTilesClick(this, ActivitySize.class));
		
	}

	private void init() {
		
		searchExp = (ImageView)findViewById(R.id.searchExpress);
		searchSize = (ImageView)findViewById(R.id.searchSize);
		
	}


}
