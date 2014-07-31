package com.littletools.main.launcher;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import com.littletools.R;

public class DialogApps implements Runnable{
	
	private Activity mThis;
	private AlertDialog mDialog;
	private AlertDialog.Builder mBuilder;
	private GridView mGView;
	private View mView;
	private List<PackageInfo> mList;
	private Thread mThread;
	
	public DialogApps(Activity a) {

		this.mThis = a;

		//�������̣߳���������Ӧ�ó���
		mThread = new Thread(this);
		mThread.start();

	}
			
	
	private void initDialogBuilder() {
		
		mBuilder = new AlertDialog.Builder(mThis);		
		mBuilder.setTitle(R.string.dialog_apps_title);
		mBuilder.setView(mView);	
		mDialog = mBuilder.create();
		
	}

	private void initViewInDialog() {
		
		mView = LayoutInflater.from(mThis).inflate(R.layout.dialog_apps, null);

		mGView = (GridView) mView.findViewById(R.id.mGridView);	
		
		mGView.setAdapter(new AdapterAppsDialog(mThis, mList));
		mGView.setFocusableInTouchMode(true);
		mGView.setFocusable(true);
		
	}

	public void show(){
		
		initViewInDialog();
		initDialogBuilder();
		
		mDialog.show();
		
	}
	
	// ���ò˵�����������
	public void setOnItemClickListener(OnItemClickListener listener) {

		mGView.setOnItemClickListener(listener);
		
	}

	// ���ز˵�
	public void dismiss() {
		
		mDialog.dismiss();
		
	}

	public void run() {
		
		mList = mThis.getPackageManager().getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_ACTIVITIES);
		//�������֮��֪ͨ���̸߳���UI
		ListenerOnItemTwoViewClick.mHander.sendEmptyMessage(0);	
		
		
	}


}
