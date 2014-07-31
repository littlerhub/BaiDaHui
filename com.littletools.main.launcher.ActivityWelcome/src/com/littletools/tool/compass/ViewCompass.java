package com.littletools.tool.compass;

import java.io.InputStream;

import com.littletools.R;
import com.littletools.main.utils.UtilScreen;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;

public class ViewCompass extends View{
	public float mDegree = 0.0f;
	private String mMessage = "";
	private final float SIZE_MESSAGE = 50.0f;
	private Bitmap bmpBg;
	private float screenW;
    private float screenH;
	private float bmpBgW;
	private float bmpBgH;
	private float bmpWScale;
	private float bmpHScale;
	private Paint mPaint;
	
    private Bitmap[] mBitmaps = new Bitmap[2];
    private InputStream mInputStream;
    
    int[] mBitmapW = new int[2];
    int[] mBitmapH = new int[2];
    private Resources mRes;
    
    private Activity aContext;

    public ViewCompass(Activity aContext) {
    	
        super(aContext);  
        
        this.aContext = aContext;
        
        init();
        
    }
    
    private void init() {
               
        mRes = aContext.getResources();
        
        mPaint = new Paint();
        //---设置为无锯齿
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);           
        //---将三张图片的Bitmap对象装到一个数组中
        setBitmapArray(0, R.drawable.ic_compass_0); 
        setBitmapArray(1, R.drawable.ic_compass_1);
        
    	//---设置背景图片缩放比例
 	   	bmpBg = BitmapFactory.decodeResource(getResources(), R.drawable.bg_vertical);
        bmpBgW = bmpBg.getWidth();
        bmpBgH = bmpBg.getHeight();
        float[] screenSize = UtilScreen.getScreenSize(aContext);
        screenW = screenSize[0];
        screenH = screenSize[1];
        bmpWScale = screenW / bmpBgW;
        bmpHScale = screenH / bmpBgH;
 	
    }
    
    private void setBitmapArray(int index, int resId){
    	//---取得图片的输入流
    	mInputStream = mRes.openRawResource(resId);
 	   	//---再从流中取得图片的Bitmap对象
 	   	mBitmaps[index] = BitmapFactory.decodeStream(mInputStream);
        mBitmapW[index] = mBitmaps[index].getWidth();
        mBitmapH[index] = mBitmaps[index].getHeight();
        
    }
    
    @Override 
    protected void onDraw(Canvas canvas) {       
    	
        Matrix matrix = new Matrix();
        matrix.setScale(bmpWScale, bmpHScale);
        canvas.drawBitmap(bmpBg, matrix, mPaint);
        
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        int cx = w / 2;
        int cy = h / 2;
        
        int mCurrentOrientation = getResources().getConfiguration().orientation;
        if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT) { 
        
     	   canvas.save();
     	   canvas.translate(cx, cy);
     	   drawPictures(canvas);
     	   canvas.restore();
     	   drawText(canvas);
     	   
        }

    }
    
    private void drawText(Canvas canvas) {
		
    	
    	mPaint.setTextSize(SIZE_MESSAGE);
    	float textWidth = mPaint.measureText(mMessage);
    	float textX = (screenW - textWidth) / 2;
    	float textY = screenH / 2;
    	canvas.drawText(mMessage, textX, (textY + SIZE_MESSAGE / 2), mPaint);
		
	}

	private void drawPictures(Canvas canvas){    	
		//---检测到的度数是手机Y轴指向的度数
 		canvas.rotate(-mDegree);
 		Matrix matrix = new Matrix();
 		matrix.postTranslate(-mBitmapW[0] / 2, -mBitmapH[0] / 2);
 		matrix.postScale(screenW / mBitmapW[0], screenW / mBitmapW[0]);
        canvas.drawBitmap(mBitmaps[1], matrix, mPaint);
        canvas.rotate(360 + mDegree);        
        canvas.drawBitmap(mBitmaps[0], matrix, mPaint);

    }
    
	
    public void setDegree(float degree) {
    	//---经测试，从传感器取到的数值范围是0~362，所以要换算一下
    	this.mDegree = degree / (362.0f / 360.0f);  
  
    	int range = 22;  

    	String degreeStr = String.valueOf(Math.round(mDegree));  
        
    	if("360".equals(degreeStr)){
    		degreeStr = "0";
    	}
    	
    	if(mDegree >= (360-range) || mDegree <= range){  
            
    		mMessage = "北 " + degreeStr + "°";  
                
        }  
              

    	if(mDegree >= (90 - range) && mDegree <= (90 + range)) {  

    		mMessage = "东 " + degreeStr + "°";  

    	}  
   	

    	if(mDegree >= (180 - range) && mDegree <= (180 + range)){  

    		mMessage = "南 " + degreeStr + "°";  

    	}  

    	if(mDegree >= (270 - range) && mDegree <= (270 + range)){  

    		mMessage = "西 " + degreeStr + "°";  

    	}  

    	if(mDegree > (45 - range) && mDegree < (45 + range)){  

    		mMessage = "东北 " + degreeStr + "°";  

    	}  


    	if(mDegree > (135 - range) && mDegree < (135 + range)){  

    		mMessage = "东南 " + degreeStr + "°";  

    	}  

    	if(mDegree > (225 - range) && mDegree < (225 + range)){  

    		mMessage = "西南 " + degreeStr + "°";  
    	
    	}  


    	if(mDegree > (315 - range) && mDegree < (315 + range)){  

    		mMessage = "西北 " + degreeStr + "°";  

    	}  
               	
    }  
	
}
