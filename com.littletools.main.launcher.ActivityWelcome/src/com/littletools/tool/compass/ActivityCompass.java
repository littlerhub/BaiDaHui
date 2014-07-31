package com.littletools.tool.compass;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

public class ActivityCompass extends Activity {

   private SensorManager mManager;
   private Sensor mSensor;//---���򴫸���
   private ListenerOnSensor mListener;
   public static ViewCompass mView;

   @Override
   protected void onCreate(Bundle icicle) {
       super.onCreate(icicle);
       
       init();
       
       setContentView(mView);
       
   }

   private void init() {
       //---�йش������ĳ�ʼ��
	   mManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
	   mSensor = mManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);	  
	   //---ʹ��View��ܻ��ƽ���
	   mView = new ViewCompass(this);
	   mListener = new ListenerOnSensor(mView);
   }

   @Override
   protected void onStart(){
       super.onResume();
       //ע�������
       mManager.registerListener(mListener, mSensor, SensorManager.SENSOR_DELAY_GAME);
       
   }
   
   @Override
   protected void onDestroy(){
       //ȡ��������
       mManager.unregisterListener(mListener);
       super.onStop();
       
   }
   
   
}
