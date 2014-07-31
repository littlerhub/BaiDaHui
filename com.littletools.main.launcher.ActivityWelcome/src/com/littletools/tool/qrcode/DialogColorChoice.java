package com.littletools.tool.qrcode;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.littletools.*;
import com.littletools.main.datas.DataApplication;

public class DialogColorChoice {
	
	private ActivityQRCreate mThis;
	private Dialog mDialog;
	private View mDialotView;
	private ImageView mQRColor1;
	private ImageView mQRColor2;
	private ImageView mQRColor3;
	//点击纯色按钮的次数
	private int clickCount = 0;
	//默认二维码颜色为蓝色
	private int colorType = 0;
	//---二维码风格类型
	private int type;
	// 二维码颜色
	private int qrColor = DataApplication.COLOR_BLACK;

	public DialogColorChoice(Activity mThis) {

		this.mThis = (ActivityQRCreate) mThis;
			    
		init();
		
		addListeners();
		
		
	}
	private void addListeners() {

		mQRColor1.setOnClickListener(new MyOnQRColorListener());
		mQRColor2.setOnClickListener(new MyOnQRColorListener());
		mQRColor3.setOnClickListener(new MyOnQRColorListener());
		
	}
	private void init() {

		mDialotView = LayoutInflater.from(mThis).inflate(R.layout.dialog_qrcreate_color, null);
	    mQRColor1 = (ImageView)mDialotView.findViewById(R.id.iv_colorQR1);
	    mQRColor1.setBackgroundColor(qrColor);
	    mQRColor2 = (ImageView)mDialotView.findViewById(R.id.iv_colorQR2);
	    mQRColor3 = (ImageView)mDialotView.findViewById(R.id.iv_colorQR3);
		
        mDialog = new Dialog(mThis, R.style.DialogColorStyle);  
        mDialog.setContentView(mDialotView);   
        mDialog.setCancelable(true); 
        Window mWindow = mDialog.getWindow();   
        WindowManager.LayoutParams lp = mWindow.getAttributes();    
        lp.y = 140;
        mDialog.onWindowAttributesChanged(lp);  

	}
		
	public void show(){
	
		mDialog.show();		
		
	}
	
	class MyOnQRColorListener implements android.view.View.OnClickListener{

		public void onClick(View v) {
			if(v.equals(mQRColor1)){
				type = 0;
				clickCount++;
				colorType = clickCount % 4;
				switch(colorType){
					case 0:{
						qrColor = DataApplication.COLOR_BLUE;
						break;
						
					}case 1:{
						qrColor = DataApplication.COLOR_RED;
						break;
						
					}case 2:{
						qrColor = DataApplication.COLOR_PURPLE;			
						break;
						
					}case 3:{
						qrColor = DataApplication.COLOR_BLACK;				
						break;
						
					}		
				}
				
				mQRColor1.setBackgroundColor(qrColor);		
				
			}else if(v.equals(mQRColor2)){
				type = 1;
				
			}else if(v.equals(mQRColor3)){
				type = 2;
			}

			//---设置二维码颜色
			mThis.setQrColor(qrColor);

			//---只要QRStr中保存的内容不为空
			if(mThis.getQRStr() != null && !mThis.getQRStr().equals("")){
				
				mThis.mCreateQRcode(mThis.getQRStr(), type);
								
			}	
			
		}
		
	}
	
}
