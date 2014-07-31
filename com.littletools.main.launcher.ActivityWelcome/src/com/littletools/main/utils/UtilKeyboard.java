package com.littletools.main.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class UtilKeyboard {
	
	/**
	 * 使弹出的软键盘消失
	 */
	public static void dismissSoftInput(EditText mEditText, Activity mThis) {
		// TODO Auto-generated method stub
		((InputMethodManager)mThis.getSystemService(Context.INPUT_METHOD_SERVICE))  
        .hideSoftInputFromWindow(  
        		mEditText.getWindowToken(),  
                InputMethodManager.HIDE_NOT_ALWAYS); 
	}
	
}
