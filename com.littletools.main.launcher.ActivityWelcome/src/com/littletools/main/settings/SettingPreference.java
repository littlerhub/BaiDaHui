package com.littletools.main.settings;

import cn.waps.AppConnect;

import com.littletools.*;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.widget.ListView;

public class SettingPreference extends PreferenceActivity{

	private Preference pref1 = null;
	private Preference pref2 = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		

		init();
		
		addListeners();				
		
	}

	private void addListeners() {
		
		pref1.setOnPreferenceClickListener(new MyOnPreferenceClickListener());
		pref2.setOnPreferenceClickListener(new MyOnPreferenceClickListener());
		
	}

	private void init() {
		
		//---加载整体布局
		addPreferencesFromResource(R.xml.settings);
		pref1 = findPreference("pref_apps1");
		pref2 = findPreference("pref_apps2");
		
		ListView listView = getListView();
		listView.setCacheColorHint(android.graphics.Color.TRANSPARENT);
		listView.setBackgroundResource(R.drawable.bg_vertical);
		listView.setDividerHeight(5);
		listView.setDivider(getResources().getDrawable(R.drawable.bg_list_line));		
				
	}

	class MyOnPreferenceClickListener implements OnPreferenceClickListener{

		public boolean onPreferenceClick(Preference preference) {
			/*if(preference.equals(pref1)){
				//---显示插屏广告
				AppConnect.getInstance(SettingPreference.this).initPopAd(SettingPreference.this);
				AppConnect.getInstance(SettingPreference.this).showPopAd(SettingPreference.this);
			}else if(preference.equals(pref2)){
				//---显示应用列表
				AppConnect.getInstance(SettingPreference.this).showOffers(SettingPreference.this);
			}*/
			
			return false;
		}
		
	}
	
}
