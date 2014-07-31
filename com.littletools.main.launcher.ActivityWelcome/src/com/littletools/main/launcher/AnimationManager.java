package com.littletools.main.launcher;

import android.view.View;
import android.view.animation.Animation;

public class AnimationManager {

	//获取释放MyGridView中的Item时的动画
	public static Animation getDropItemAnim(float x, float y){
		
		return new AnimFreeTile().getAnim(x, y);
	}
	
	//获取背景图片的动�?
	public static Animation getBackgroundAnim(View v){
		
		return new AnimLaucherBg(v).getAnim();
	}
	
}
