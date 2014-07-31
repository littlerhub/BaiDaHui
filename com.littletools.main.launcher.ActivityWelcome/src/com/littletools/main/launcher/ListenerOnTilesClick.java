package com.littletools.main.launcher;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import com.littletools.*;

public class ListenerOnTilesClick implements OnClickListener{
	
	//�����¼��ĵ�ǰActivity����
	private Activity aThis;
	//Ҫ��ת��Activity��Class����
	private Class<?> bClass;
	
	public ListenerOnTilesClick(Activity aThis, Class<?> bClass){
		
		this.aThis = aThis;
		this.bClass = bClass;
		
	}

	public void onClick(View v) {
		
		//���Tileʱ�Ķ���Ч��
		//Animation animation = AnimationUtils.loadAnimation(aThis, R.anim.tile_click);
		//v.startAnimation(animation);

		//��ת����Ӧ��Activity
		Intent intent = new Intent(aThis, bClass);
		aThis.startActivity(intent);
		aThis.overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
		
	}

}
