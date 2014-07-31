package com.littletools.main.launcher;

import com.littletools.R;

import java.util.List;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class AdapterAppsDialog extends BaseAdapter {

	private List<PackageInfo> mList;
	private Activity mThis;
	private int size = 0;
	
	public AdapterAppsDialog(Activity a, List<PackageInfo> l){
		
		this.mThis = a;
		this.mList = l;
				
	}
	
	public int getCount() {

		return mList.size();
	}

	public Object getItem(int position) {
	
		return mList.get(position);
	}

	public long getItemId(int position) {

		return position;
	}

	public View getView(int position, View view, ViewGroup parent) {
		
		view = LayoutInflater.from(mThis).inflate(R.layout.item_dialog_apps, null);
			
		TextView mLabel = (TextView) view.findViewById(R.id.mLabel);
		ImageView mIcon = (ImageView)view.findViewById(R.id.mIcon);
		RelativeLayout mBg = (RelativeLayout)view.findViewById(R.id.mBg);
				
		if(size % 2 == 0){
			mBg.setBackgroundResource(R.color.skyblue);
		}else{
			mBg.setBackgroundResource(R.color.purple);
		}
			
		size++;
		
		PackageInfo mPI = (PackageInfo)mList.get(position);
		String label = (String) mPI.applicationInfo.loadLabel(mThis.getPackageManager());
		Drawable icon = mPI.applicationInfo.loadIcon(mThis.getPackageManager());
				
		mLabel.setText(label);
		mIcon.setBackgroundDrawable(icon);
		
		return view;
		
	}
	
				
}