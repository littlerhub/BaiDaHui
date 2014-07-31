package com.littletools.tool.search;

import com.littletools.R;
import com.littletools.main.utils.UtilScreen;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ActivitySize extends Activity {
	
	private ImageView mNumImg = null;
	
	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;
	
	private int mode = NONE;
	private float oldDist;
	private Matrix matrix;
	private Matrix manMatrix;
	private Matrix womanMatrix;
	private Matrix childMatrix;
	private Matrix savedMatrix = new Matrix();
	private PointF start = new PointF();
	private PointF mid = new PointF();

	private ImageView man = null;
	private ImageView woman = null;	
	private ImageView child = null;
	
	private ImageView left = null;
	private ImageView right = null;
	
	private int num = 1;
	
	private Animation lEnterAnim = null;
	private Animation lExitAnim = null;
	private Animation rEnterAnim = null;
	private Animation rExitAnim = null;
		
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_size);
		
		init();
		
		addListeners();
		
	}
	

	private void addListeners() {
		
		left.setOnClickListener(new MyClickLeftListener());
		right.setOnClickListener(new MyClickRightListener());
		man.setOnTouchListener(new MyTouchListener());
		woman.setOnTouchListener(new MyTouchListener());
		child.setOnTouchListener(new MyTouchListener());
		
	}


	private void init() {
		
		man = (ImageView)findViewById(R.id.man);
		woman = (ImageView)findViewById(R.id.woman);
		child = (ImageView)findViewById(R.id.child);
		
		left = (ImageView)findViewById(R.id.iv_left);
		right = (ImageView)findViewById(R.id.iv_right);
		
		lEnterAnim = AnimationUtils.loadAnimation(this, R.anim.enter_left);
		rEnterAnim = AnimationUtils.loadAnimation(this, R.anim.enter_right);
		lExitAnim = AnimationUtils.loadAnimation(this, R.anim.exit_left);
		rExitAnim = AnimationUtils.loadAnimation(this, R.anim.exit_right);
		
		mNumImg = (ImageView)findViewById(R.id.iv_num);
		//---设置默认页码
		setPageNum(1);		
		//---设置图片默认显示位置
		setImgPosition();		
		
	}


	class MyClickLeftListener implements OnClickListener{

		public void onClick(View v) {			
			
			num--;
			
			if(num < 1){
				num = 1;
				return;
			}else if(num > 3){
				num = 3;
				return;
			}
			
			setPageNum(num);
			
			switch(num){
				case 1:{				
					man.setVisibility(View.VISIBLE);
					man.startAnimation(lEnterAnim);
					woman.setVisibility(View.GONE);
					woman.startAnimation(rExitAnim);
					child.setVisibility(View.GONE);				
					break;
				}case 2:{			
					woman.setVisibility(View.VISIBLE);
					woman.startAnimation(lEnterAnim);
					man.setVisibility(View.GONE);					
					child.setVisibility(View.GONE);	
					child.startAnimation(rExitAnim);
					break;
				}case 3:{
					child.setVisibility(View.VISIBLE);
					child.startAnimation(lEnterAnim);
					man.setVisibility(View.GONE);
					woman.setVisibility(View.GONE);		
					woman.startAnimation(rExitAnim);
					break;
				}
			}
			
		}
		
	}
	
	class MyClickRightListener implements OnClickListener{

		public void onClick(View v) {			
			
			num++;
			
			if(num < 1){
				num = 1;
				return;
			}else if(num > 3){
				num = 3;
				return;
			}						
			setPageNum(num);
			switch(num){
				case 1:{										
					man.setVisibility(View.VISIBLE);
					man.startAnimation(rEnterAnim);
					woman.setVisibility(View.GONE);
					woman.startAnimation(lExitAnim);
					child.setVisibility(View.GONE);	
					matrix = manMatrix;
					break;
				}case 2:{										
					woman.setVisibility(View.VISIBLE);
					woman.startAnimation(rEnterAnim);
					man.setVisibility(View.GONE);
					man.startAnimation(lExitAnim);
					child.setVisibility(View.GONE);				
					break;
				}case 3:{
					child.setVisibility(View.VISIBLE);	
					child.startAnimation(rEnterAnim);
					man.setVisibility(View.GONE);
					woman.setVisibility(View.GONE);	
					woman.startAnimation(lExitAnim);
					break;
				}
			}
			
		}
		
	}
	
	class MyTouchListener implements OnTouchListener {

		public boolean onTouch(View v, MotionEvent event) {
			ImageView view = (ImageView) v;
			if(view.equals(man)){
				matrix = manMatrix;
				System.out.println("man");
			}else if(view.equals(woman)){
				matrix = womanMatrix;
			}else if(view.equals(child)){
				matrix = childMatrix;
			}
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					savedMatrix.set(matrix);
					start.set(event.getX(), event.getY());
					mode = DRAG;
					System.out.println("ACTION_DOWN");
					break;
				case MotionEvent.ACTION_UP:
					System.out.println("ACTION_UP");
				case MotionEvent.ACTION_POINTER_UP:
					mode = NONE;
					System.out.println("ACTION_POINTER_UP");
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					oldDist = spacing(event);
					if (oldDist > 10f) {
						savedMatrix.set(matrix);
						midPoint(mid, event);
						mode = ZOOM;
					}
					System.out.println("ACTION_POINTER_DOWN");
					break;
				case MotionEvent.ACTION_MOVE:
					if (mode == DRAG) {
						matrix.set(savedMatrix);
						matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
					} else if (mode == ZOOM) {
						float newDist = spacing(event);
						if (newDist > 10f) {
							matrix.set(savedMatrix);
							float scale = newDist / oldDist;
							matrix.postScale(scale, scale, mid.x, mid.y);
						}
					}
					System.out.println("ACTION_MOVE");
					break;
			}

			view.setImageMatrix(matrix);

			return true;
		}
			//---两触点间�?
			private float spacing(MotionEvent event) {
				float x = event.getX(0) - event.getX(1);
				float y = event.getY(0) - event.getY(1);
				return FloatMath.sqrt(x * x + y * y);
			}
			//---两触点的中点
			private void midPoint(PointF point, MotionEvent event) {
				float x = event.getX(0) + event.getX(1);
				float y = event.getY(0) + event.getY(1);
				point.set(x / 2, y / 2);
				
			}
	}

	public void setPageNum(int pageNum){
		// 当页面改变时，修改页面图�?
		switch(pageNum){			
			case 1:{
				mNumImg.setBackgroundResource(R.drawable.ic_search_size_num1);
				break;
			}case 2:{
				mNumImg.setBackgroundResource(R.drawable.ic_search_size_num2);
				break;
			}case 3:{
				mNumImg.setBackgroundResource(R.drawable.ic_search_size_num3);
				break;
			}
		}
		
	}
	
	private int[] getImageSize(int id) {
		
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), id);
		int imgW = bmp.getWidth();
		int imgH = bmp.getHeight();
		return new int[]{imgW, imgH};		
	}
	
	// 设置图片默认显示位置
	private void setImgPosition() {
		float[] screenSize = UtilScreen.getScreenSize(this);
			
		int[] imgChildSize = getImageSize(R.drawable.ic_size_child);
		childMatrix = new Matrix();
		childMatrix.postTranslate((screenSize[0] - imgChildSize[0]) / 2, (screenSize[1] - imgChildSize[1]) / 2);
		child.setImageMatrix(childMatrix);
			
		int[] imgWomanSize = getImageSize(R.drawable.ic_size_woman);
		womanMatrix = new Matrix();
		womanMatrix.postTranslate((screenSize[0] - imgWomanSize[0]) / 2, (screenSize[1] - imgWomanSize[1]) / 2);
		woman.setImageMatrix(womanMatrix);
			
		int[] imgManSize = getImageSize(R.drawable.ic_size_man);
		manMatrix = new Matrix();
		manMatrix.postTranslate((screenSize[0] - imgManSize[0]) / 2, (screenSize[1] - imgManSize[1]) / 2);
		man.setImageMatrix(manMatrix);

	}
	
}