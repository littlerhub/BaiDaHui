package com.littletools.main.launcher;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ListenerOnItemTwoViewClick implements OnItemClickListener {
	
	private static Activity mThis;
	private static MyGridView mMyView;
	public static DialogApps mApps;
	public static ProgressDialog mPD;
	public static Handler mHander = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			//���������֮�󣬻���յ��������̵߳���Ϣ��Ȼ�������߳��и���UI
				//1����ʾӦ���б�Dialog
			mApps.show();
				//2��ʹProgressDialog��ʧ			
			mPD.dismiss();
				//3��Ϊ�б�󶨼�����
			mApps.setOnItemClickListener(new ListenerOnItemAppsListClick(mThis, mMyView, mApps));
			
		}
		
	};
	
	@SuppressWarnings("static-access")
	public ListenerOnItemTwoViewClick(Activity a, MyGridView mv){ 
		
		this.mThis = a;
		this.mMyView = mv;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		
		HashMap<String, Object> itemMap = (HashMap<String, Object>)arg0.getItemAtPosition(arg2);
		
		String label = itemMap.get("label").toString();
		
		if(label != null && label.equals("���")){
						
			//��ʾ���г����б�ĶԻ���
			displayAppsDialog();
												
		}else{

			String PName = itemMap.get("PName").toString();
			String AName = itemMap.get("AName").toString();	
			System.out.println(PName);
			System.out.println(AName);
			if(PName.equals("com.littletools")){
				Toast.makeText(mThis, "�������~~~С������", Toast.LENGTH_LONG).show();	
				return;
			}
			Intent intent = new Intent();
			intent.setComponent(new ComponentName(PName, AName));
			try{
				mThis.startActivity(intent);
			}catch(Exception e){
				e.printStackTrace();
				Toast.makeText(mThis, "�Բ������Ѿ������ˣ�", Toast.LENGTH_LONG).show();
			}
			
		}
		
	}


	private void displayAppsDialog() {
		
		//1������ʾProgressDialog,�����û����飬��Ϊ���ع�����Ҫʱ��
		mPD = ProgressDialog.show(mThis, "���Ժ�...", "���ڼ���Ӧ�ó���...", true, false);
		
		//2.1���������̣߳���������Ӧ�ó���
		//2.2���������֮��֪ͨ���̸߳���UI
		mApps = new DialogApps(mThis);	
		
		
	}

}