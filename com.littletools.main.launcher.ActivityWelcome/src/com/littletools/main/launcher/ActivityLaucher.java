package com.littletools.main.launcher;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v4.view.ViewPager;

import cn.waps.AppConnect;

import com.littletools.*;

public class ActivityLaucher extends Activity {
	
	public static ViewPager mViewPager;
	private ArrayList<View> viewsList;
	private View oneView;
	private View TwoView;
	
	@SuppressWarnings("unused")
	private ViewOne mOneView;
	@SuppressWarnings("unused")
	private ViewTwo mTwoView;
	//背景图片
	private ImageView mBgImage;
	// 再按一次“返回键”退出
	private int backCount = 0;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laucher);
        //初始化
        this.init();
        //绑定监听器
        this.addListeners();
    }

    
	private void addListeners() {
		
		//给ViewPager绑定监听器
		mViewPager.setOnPageChangeListener(new ListenerOnPagerChange());
		
	}


	private void init() {
		//找到各控件
		mBgImage = (ImageView)findViewById(R.id.mainBgImage);
		mViewPager = (ViewPager)findViewById(R.id.mViewPager);
		//开启背景动画
		mBgImage.startAnimation(AnimationManager.getBackgroundAnim(mBgImage));

		//将两个XML渲染成View
		LayoutInflater inflater = this.getLayoutInflater();
		oneView = inflater.inflate(R.layout.view_launcher_one, null);
		TwoView = inflater.inflate(R.layout.view_launcher_two, null);
		
		//将两个View放入List中，作为数据源传入适配器
		viewsList = new ArrayList<View>();
		viewsList.add(oneView);
		viewsList.add(TwoView);
		
		//给ViewPager安装适配器
		mViewPager.setAdapter(new AdapterPager(viewsList));
		
		//初始化ViewPager中两个页面
		mOneView = new ViewOne(this, oneView);
		mTwoView = new ViewTwo(this, TwoView);
		
	}
	
	

	@Override
	protected void onStart() {
		
		backCount = 0;
		super.onStart();
		
	}

	// 再按一次返回键退出
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
								
			if(keyCode == 4){
				
				backCount++;
				
				if(backCount == 1){
					
					Toast.makeText(ActivityLaucher.this, R.string.toast_exit, Toast.LENGTH_SHORT).show();

				}else if(backCount == 2){
					/*//以下方法将用于释放SDK占用的系统资源(广告)
					AppConnect.getInstance(this).finalize();*/
					ActivityLaucher.this.finish();
					
				}				
				
			}				

		return false;
		
	}
	
}
