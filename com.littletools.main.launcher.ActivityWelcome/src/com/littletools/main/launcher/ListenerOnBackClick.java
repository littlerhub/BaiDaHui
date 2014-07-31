package com.littletools.main.launcher;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.littletools.*;

public class ListenerOnBackClick implements OnClickListener{
	
	//产生事件的Activity对象
	private Activity aThis;
	
	public ListenerOnBackClick(Activity aThis){
		
		this.aThis = aThis;
		
	}

	public void onClick(View v) {
		
		Animation animation = AnimationUtils.loadAnimation(aThis, R.anim.tile_click);
		v.startAnimation(animation);
		
		aThis.finish();
		aThis.overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
		
	}

}
