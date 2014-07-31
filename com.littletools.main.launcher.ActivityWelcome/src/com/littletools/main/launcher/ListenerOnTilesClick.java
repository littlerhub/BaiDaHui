package com.littletools.main.launcher;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import com.littletools.*;

public class ListenerOnTilesClick implements OnClickListener{
	
	//产生事件的当前Activity对象
	private Activity aThis;
	//要跳转的Activity的Class对象
	private Class<?> bClass;
	
	public ListenerOnTilesClick(Activity aThis, Class<?> bClass){
		
		this.aThis = aThis;
		this.bClass = bClass;
		
	}

	public void onClick(View v) {
		
		//点击Tile时的动画效果
		//Animation animation = AnimationUtils.loadAnimation(aThis, R.anim.tile_click);
		//v.startAnimation(animation);

		//跳转到相应的Activity
		Intent intent = new Intent(aThis, bClass);
		aThis.startActivity(intent);
		aThis.overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
		
	}

}
