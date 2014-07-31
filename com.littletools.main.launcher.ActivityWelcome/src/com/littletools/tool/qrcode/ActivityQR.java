package com.littletools.tool.qrcode;

import com.littletools.R;
import com.littletools.main.launcher.ListenerOnTilesClick;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class ActivityQR extends Activity{

	private ImageView createQR;
	private ImageView scanQR;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_qrcode);
		
		init();
		
		addListeners();
		
	}
	
	private void addListeners() {

		createQR.setOnClickListener(new ListenerOnTilesClick(this, ActivityQRCreate.class));
		scanQR.setOnClickListener(new ListenerOnTilesClick(this, ActivityQRScan.class));
		
	}

	private void init() {
		
		createQR = (ImageView)findViewById(R.id.createQRCode);
		scanQR = (ImageView)findViewById(R.id.scanQRCode);
		
	}

}
