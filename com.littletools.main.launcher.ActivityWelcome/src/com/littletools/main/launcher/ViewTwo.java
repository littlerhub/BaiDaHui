package com.littletools.main.launcher;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;

import cn.waps.AdView;

import com.littletools.main.datas.DataHelper;
import com.littletools.main.launcher.ListenerOnItemTwoViewClick;
import com.littletools.main.launcher.MyGridView;
import com.littletools.R;

public class ViewTwo {
	
	private Activity mThis;
	private View mView;
	
	//�Զ����GridView�������϶�
	public static MyGridView mMyView;
	private ArrayList<HashMap<String, Object>> itemList;
	private HashMap<String, Object> itemMap;
	
    public ViewTwo(Activity a, View v){	  
    	
    	//��ʼ������
		init(a, v);		
		//�󶨼�����
		addListeners();
		
    }
	
	private void addListeners() {
		
		//����MyGridView��item����¼�
		mMyView.setOnItemClickListener(new ListenerOnItemTwoViewClick(mThis, mMyView));
				
	}

	private void init(Activity a, View v) {
    	
    	mThis = a;
    	mView = v;		
		//ʵ�������ؼ�
    	mMyView = (MyGridView) mView.findViewById(R.id.mGridview);
		
		itemList = new ArrayList<HashMap<String, Object>>();

		itemMap = new HashMap<String, Object>();
		itemMap.put("label", "���");
		itemMap.put("icon", mThis.getResources().getDrawable(R.drawable.ic_add));
				
		itemList.add(itemMap);
		
		Cursor cursor = DataHelper.getInstance(mThis).queryData();
		
		while(cursor.moveToNext()){

			String pName = cursor.getString(cursor.getColumnIndex("pname"));
			String aName = cursor.getString(cursor.getColumnIndex("aname"));
			String label = cursor.getString(cursor.getColumnIndex("label"));
			byte[] byteIcon = cursor.getBlob(cursor.getColumnIndex("icon"));
			ByteArrayInputStream inputStream=new ByteArrayInputStream(byteIcon);				   
    		Bitmap bmpIcon = BitmapFactory.decodeStream(inputStream);	
    		Drawable icon = new BitmapDrawable(bmpIcon);
			
    		itemMap = new HashMap<String, Object>();
    		//��������
    		itemMap.put("label", label);
    		//����ͼ��
    		itemMap.put("icon", icon);
    		//�������
    		itemMap.put("AName", aName);
    		//��������Activity������
    		itemMap.put("PName", pName);
    		
    		itemList.add(itemMap);
			
		}
		
		mMyView.setAdapter(new AdapterMyView(mThis, itemList));
		try{
			/*//---��ʾ�������
			LinearLayout container = (LinearLayout) mView.findViewById(R.id.AdLinearLayout);
			new AdView(mThis, container).DisplayAd();*/
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}