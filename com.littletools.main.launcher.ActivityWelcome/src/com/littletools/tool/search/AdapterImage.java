package com.littletools.tool.search;

import java.util.List;
import java.util.Map;

import com.littletools.*;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class AdapterImage extends BaseAdapter {
	
	private List<Map<String, Object>> datas;
	
	private Context context;
	
	public AdapterImage(Context context, List<Map<String, Object>> datas){
		this.context = context;
		this.datas = datas;
	}

	
	public int getCount() {

		return this.datas.size();
	}

	
	public Object getItem(int position) {

		return this.datas.get(position);
	}

	
	public long getItemId(int position) {

		return position;
	}

	
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_list_ems, null);
			holder.itemLeftImage = (ImageView)convertView.findViewById(R.id.item_left);
			holder.itemText = (TextView)convertView.findViewById(R.id.item_text);
			holder.itemRightImage = (ImageView)convertView.findViewById(R.id.item_right);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		Map<String, Object> item = this.datas.get(position);
		
		holder.itemLeftImage.setImageResource(Integer.parseInt(item.get("left").toString()));
		holder.itemText.setText(item.get("text").toString());
		holder.itemRightImage.setImageResource(Integer.parseInt(item.get("right").toString()));
		
		return convertView;
	}
	
	static class ViewHolder{
		ImageView itemLeftImage;
		TextView itemText;
		ImageView itemRightImage;
	}

}
