package com.littletools.main.launcher;

import cn.waps.AppConnect;

import com.littletools.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
/**
 * ��ӭ����
 * @author LittleBoy
 * 16:35 2013-04-12
 */
public class ActivityWelcome extends Activity{
	
	//�ӳ�2.5�� 
	private final long DISPLAY_DURATION = 2500; 
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        
        //��ʽ�٣�ͨ��AndroidManifest�ļ���ȡWAPS_ID��WAPS_PID	
        //AppConnect.getInstance(this);	//����ȷ��AndroidManifest�ļ���������WAPS_ID
        //��ʽ�ڣ�ͨ����������WAPS_ID��WAPS_PID
        //AppConnect.getInstance("WAPS_ID","WAPS_PID",this);	
        
        new Handler().postDelayed(new Runnable(){   
                
            public void run() {   
                Intent intent = new Intent(ActivityWelcome.this, ActivityLaucher.class);   
                ActivityWelcome.this.startActivity(intent);  
                ActivityWelcome.this.overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
                ActivityWelcome.this.finish();   
            }   
                 
           }, DISPLAY_DURATION);   
		
    }
}
