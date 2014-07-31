package com.littletools.tool.calculator;

import com.littletools.*;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.GridView;

public class ActivityCalculator extends Activity {

	public static EditText mEditText;
	private GridView mGridView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        
        init();
        
        addListeners();

        
        
        
        /*
         * = 按钮
         */
        /*btDeng.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				
				String str = mEditText.getText().toString();				
				
				String result = cal_exp(str);
				
				mEditText.setText(result);
				char[] ch = str.toCharArray();
				int start = 0;
				for(int i = 0; i < ch.length; i ++){

					if(ch[i] == '*'){
						
						System.out.println(new String(ch, start, i - start));
						double num1 = Double.parseDouble(new String(ch, start, i - start));
						start = i;

					}
				}
				//如果字符串中不出现该字符，则返回-1
				if(str.indexOf('(') > 0){
					start = str.indexOf('(');
					int end = str.lastIndexOf(')');
					//获取括号中的字符�?
					String subStr = str.substring(start + 1 , end);
					System.out.println(subStr);
					
				}else{
					
				}
				
				
			}
		});*/
    }

	private void addListeners() {

		mGridView.setOnItemClickListener(new ListenerOnItemClick(ActivityCalculator.this));
		
	}

	private void init() {
		
		mEditText = (EditText)findViewById(R.id.mEditText);
		mGridView = (GridView)findViewById(R.id.mGridView);     
		
		mEditText.setCursorVisible(true);
		mEditText.setInputType(InputType.TYPE_NULL);
		
		mGridView.setAdapter(new AdapterCalPad(ActivityCalculator.this));
	}
	

}