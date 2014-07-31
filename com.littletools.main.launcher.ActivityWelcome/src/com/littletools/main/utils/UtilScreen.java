package com.littletools.main.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class UtilScreen {
	
	public static float[] getScreenSize(Activity aContext){
		// ȡ����Ļ�Ŀ�͸�
		DisplayMetrics dm=new DisplayMetrics();
		aContext.getWindowManager().getDefaultDisplay().getMetrics(dm);
	    float displayW = dm.widthPixels;
	    float displayH = dm.heightPixels;
	    
	    return new float[]{displayW, displayH};
	}
	
}
