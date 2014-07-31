package com.littletools.tool.calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.littletools.R;

public class AdapterCalPad extends BaseAdapter {

	private Context mContext;
	private List<HashMap<String, Object>> mList;
	private HashMap<String, Object> mMap;
	
	public AdapterCalPad(Context c){
		
		mContext = c;
		
		mList = new ArrayList<HashMap<String, Object>>();
		
		mMap = new HashMap<String, Object>();		
		mMap.put("label", "C");
		mMap.put("bgColor", R.color.purple);
		mList.add(mMap);
		
		mMap = new HashMap<String, Object>();
		mMap.put("label", "(");
		mMap.put("bgColor", R.color.purple);
		mList.add(mMap);
		
		mMap = new HashMap<String, Object>();
		mMap.put("label", ")");
		mMap.put("bgColor", R.color.purple);
		mList.add(mMap);
		
		mMap = new HashMap<String, Object>();
		mMap.put("label", "Del");
		mMap.put("bgColor", R.color.green);
		mList.add(mMap);

		mMap = new HashMap<String, Object>();
		mMap.put("label", "7");
		mMap.put("bgColor", R.color.lightgray);
		mList.add(mMap);
		
		mMap = new HashMap<String, Object>();
		mMap.put("label", "8");
		mMap.put("bgColor", R.color.lightgray);
		mList.add(mMap);
		
		mMap = new HashMap<String, Object>();
		mMap.put("label", "9");
		mMap.put("bgColor", R.color.lightgray);
		mList.add(mMap);
		
		mMap = new HashMap<String, Object>();
		mMap.put("label", "+");
		mMap.put("bgColor", R.color.purple);
		mList.add(mMap);
		
		mMap = new HashMap<String, Object>();
		mMap.put("label", "4");
		mMap.put("bgColor", R.color.lightgray);
		mList.add(mMap);
		
		mMap = new HashMap<String, Object>();
		mMap.put("label", "5");
		mMap.put("bgColor", R.color.lightgray);
		mList.add(mMap);
		
		mMap = new HashMap<String, Object>();
		mMap.put("label", "6");
		mMap.put("bgColor", R.color.lightgray);
		mList.add(mMap);
		
		mMap = new HashMap<String, Object>();
		mMap.put("label", "-");
		mMap.put("bgColor", R.color.purple);
		mList.add(mMap);
		
		mMap = new HashMap<String, Object>();
		mMap.put("label", "1");
		mMap.put("bgColor", R.color.lightgray);
		mList.add(mMap);
		
		mMap = new HashMap<String, Object>();
		mMap.put("label", "2");
		mMap.put("bgColor", R.color.lightgray);
		mList.add(mMap);
		
		mMap = new HashMap<String, Object>();
		mMap.put("label", "3");
		mMap.put("bgColor", R.color.lightgray);
		mList.add(mMap);
		
		mMap = new HashMap<String, Object>();
		mMap.put("label", "¡Á");
		mMap.put("bgColor", R.color.purple);
		mList.add(mMap);
		
		mMap = new HashMap<String, Object>();
		mMap.put("label", "0");
		mMap.put("bgColor", R.color.lightgray);
		mList.add(mMap);
		
		mMap = new HashMap<String, Object>();
		mMap.put("label", ".");
		mMap.put("bgColor", R.color.lightgray);
		mList.add(mMap);
		
		mMap = new HashMap<String, Object>();
		mMap.put("label", "=");
		mMap.put("bgColor", R.color.skyblue);
		mList.add(mMap);
		
		mMap = new HashMap<String, Object>();
		mMap.put("label", "¡Â");
		mMap.put("bgColor", R.color.purple);
		mList.add(mMap);
	
		
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		convertView = LayoutInflater.from(mContext).inflate(R.layout.item_calculator_pad, null);
		
		ImageView mImageView = (ImageView)convertView.findViewById(R.id.mItemBg);
		TextView mTextView = (TextView)convertView.findViewById(R.id.mLabel);
				
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>)this.getItem(position);
		
		int bgColor = (Integer)map.get("bgColor");
		String label = map.get("label").toString();
					
		mImageView.setBackgroundResource(bgColor);
		mTextView.setText(label);
		
		return convertView;
		
	}


}
