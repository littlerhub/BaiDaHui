package com.littletools.main.launcher;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.littletools.*;
import com.littletools.main.settings.SettingPreference;
import com.littletools.tool.calculator.ActivityCalculator;
import com.littletools.tool.compass.ActivityCompass;
import com.littletools.tool.qrcode.ActivityQR;
import com.littletools.tool.search.ActivitySearch;
import com.littletools.tool.stopwatch.ActivityStopwatch;

public class ViewOne {
	
	private ImageView tileStopwatch;
	private ImageView tileCompass;
	private ImageView tileQRCode;
	private ImageView tileCalculator;
	private ImageView tileQuickSearch;
	private ImageView tileConfig;
	private ImageView tileAbout;
	private ImageView tilePop;
	private Activity mThis;
	private View mView;
	
    public ViewOne(Activity a, View v){	
    	//初始化
        this.init(a, v);
        //绑定监听器
        this.addListeners();
        
    }
      


	private void addListeners() {
				
		tileStopwatch.setOnClickListener(new ListenerOnTilesClick(mThis, ActivityStopwatch.class));
		tileCompass.setOnClickListener(new ListenerOnTilesClick(mThis, ActivityCompass.class));
		tileQRCode.setOnClickListener(new ListenerOnTilesClick(mThis, ActivityQR.class));
		tileCalculator.setOnClickListener(new ListenerOnTilesClick(mThis, ActivityCalculator.class));
		tileQuickSearch.setOnClickListener(new ListenerOnTilesClick(mThis, ActivitySearch.class));
		tileConfig.setOnClickListener(new ListenerOnTilesClick(mThis, SettingPreference.class));
		tileAbout.setOnClickListener(new ListenerOnTilesClick(mThis, ActivityAbout.class));
		tilePop.setOnClickListener(new MyOnPopClickListener());
		
	}

	private void init(Activity a, View v) {
	   	//初始化变量
    	this.mThis = a;
    	this.mView = v;    
		//找到各控件
		tileStopwatch = (ImageView)mView.findViewById(R.id.tile_stopwatch);	
		tileCompass = (ImageView)mView.findViewById(R.id.tile_compass);
		tileQRCode = (ImageView)mView.findViewById(R.id.tile_QRCode);
		tileCalculator = (ImageView)mView.findViewById(R.id.tile_calculator);
		tileQuickSearch = (ImageView)mView.findViewById(R.id.tile_quickSearch);
		tileConfig = (ImageView)mView.findViewById(R.id.iv_config);
		tileAbout = (ImageView)mView.findViewById(R.id.iv_about);
		tilePop = (ImageView)mView.findViewById(R.id.iv_pop);
		
	}
    	
	
	class MyOnPopClickListener implements OnClickListener{

		public void onClick(View arg0) {
			
			if(ActivityLaucher.mViewPager.getCurrentItem() == 0){
				ActivityLaucher.mViewPager.setCurrentItem(1);
			}
						
		}
		
	}

}
