package com.littletools.tool.compass;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

public class ActivityCompass extends Activity {

   private SensorManager mManager;
   private Sensor mSensor;//---方向传感器
   private ListenerOnSensor mListener;
   public static ViewCompass mView;

   @Override
   protected void onCreate(Bundle icicle) {
       super.onCreate(icicle);
       
       init();
       
       setContentView(mView);
       
   }

   private void init() {
       //---有关传感器的初始化
	   mManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
	   mSensor = mManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);	  
	   //---使用View框架绘制界面
	   mView = new ViewCompass(this);
	   mListener = new ListenerOnSensor(mView);
   }

   @Override
   protected void onStart(){
       super.onResume();
       //注册监听器
       mManager.registerListener(mListener, mSensor, SensorManager.SENSOR_DELAY_GAME);
       
   }
   
   @Override
   protected void onDestroy(){
       //取消监听器
       mManager.unregisterListener(mListener);
       super.onStop();
       
   }
   
   
}
