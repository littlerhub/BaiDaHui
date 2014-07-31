package com.littletools.tool.compass;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class ListenerOnSensor implements SensorEventListener{

	private ViewCompass mViewCompass;
	
	public ListenerOnSensor(ViewCompass mViewCompass){
		
		this.mViewCompass = mViewCompass;
		
	}
	
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	public void onSensorChanged(SensorEvent event) {
		
		//---将所监听的传感器的第一个值(方向值)传入ViewCompass中，用来更新UI
		mViewCompass.setDegree(event.values[0]);
        
		//System.out.println(event.values[1] + "=====================================");
		//System.out.println(event.values[2] + "+++++++++++++++++++++++++++++++++++++");
		
        if (ActivityCompass.mView != null) {
     	   //---重绘画布
           ActivityCompass.mView.invalidate();
        }
		
	}

}
