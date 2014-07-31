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
	//����ͼƬ
	private ImageView mBgImage;
	// �ٰ�һ�Ρ����ؼ����˳�
	private int backCount = 0;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laucher);
        //��ʼ��
        this.init();
        //�󶨼�����
        this.addListeners();
    }

    
	private void addListeners() {
		
		//��ViewPager�󶨼�����
		mViewPager.setOnPageChangeListener(new ListenerOnPagerChange());
		
	}


	private void init() {
		//�ҵ����ؼ�
		mBgImage = (ImageView)findViewById(R.id.mainBgImage);
		mViewPager = (ViewPager)findViewById(R.id.mViewPager);
		//������������
		mBgImage.startAnimation(AnimationManager.getBackgroundAnim(mBgImage));

		//������XML��Ⱦ��View
		LayoutInflater inflater = this.getLayoutInflater();
		oneView = inflater.inflate(R.layout.view_launcher_one, null);
		TwoView = inflater.inflate(R.layout.view_launcher_two, null);
		
		//������View����List�У���Ϊ����Դ����������
		viewsList = new ArrayList<View>();
		viewsList.add(oneView);
		viewsList.add(TwoView);
		
		//��ViewPager��װ������
		mViewPager.setAdapter(new AdapterPager(viewsList));
		
		//��ʼ��ViewPager������ҳ��
		mOneView = new ViewOne(this, oneView);
		mTwoView = new ViewTwo(this, TwoView);
		
	}
	
	

	@Override
	protected void onStart() {
		
		backCount = 0;
		super.onStart();
		
	}

	// �ٰ�һ�η��ؼ��˳�
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
								
			if(keyCode == 4){
				
				backCount++;
				
				if(backCount == 1){
					
					Toast.makeText(ActivityLaucher.this, R.string.toast_exit, Toast.LENGTH_SHORT).show();

				}else if(backCount == 2){
					/*//���·����������ͷ�SDKռ�õ�ϵͳ��Դ(���)
					AppConnect.getInstance(this).finalize();*/
					ActivityLaucher.this.finish();
					
				}				
				
			}				

		return false;
		
	}
	
}
