package com.littletools.main.launcher;

import cn.waps.AppConnect;

import com.littletools.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
/**
 * 欢迎界面
 * @author LittleBoy
 * 16:35 2013-04-12
 */
public class ActivityWelcome extends Activity{
	
	//延迟2.5秒 
	private final long DISPLAY_DURATION = 2500; 
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        
        //方式①：通过AndroidManifest文件读取WAPS_ID和WAPS_PID	
        //AppConnect.getInstance(this);	//必须确保AndroidManifest文件内配置了WAPS_ID
        //方式②：通过代码设置WAPS_ID和WAPS_PID
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
