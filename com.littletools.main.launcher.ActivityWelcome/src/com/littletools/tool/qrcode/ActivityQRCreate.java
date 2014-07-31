package com.littletools.tool.qrcode;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.littletools.*;
import com.littletools.main.datas.DataApplication;
import com.littletools.main.utils.UtilKeyboard;

public class ActivityQRCreate extends Activity {

	//显示生成二维码图像
	private ImageView mQRView;
	//输入框：获取用户的输入
	private EditText mQREdit;
	//按钮：点击生成二维码
	private ImageView mQRCreate;
	//按钮：点击改变二维码风格
	private ImageView mQRStyle;
	//按钮：点击保存图片到SD卡上
	private ImageView mQRSave;
	//弹出MENU
	private ImageView mPopMenu;
	//MENU的父控件
	private LinearLayout mMenuLayout;
	//图片宽度的一般
	private static final int IMAGE_HALFWIDTH = 20;
	//插入到二维码里面的图片对象
	private Bitmap mBitmap;
	//需要插图图片的大小 这里设定为40*40
	int[] pixels = new int[2*IMAGE_HALFWIDTH * 2*IMAGE_HALFWIDTH];

	private int[] colors = {DataApplication.COLOR_BLUE, DataApplication.COLOR_RED, DataApplication.COLOR_PURPLE, DataApplication.COLOR_BLACK};
	
	public void setQrColor(int qrColor) {
		this.qrColor = qrColor;
	}
	// 二维码颜色
	private int qrColor = DataApplication.COLOR_BLACK;
	//保存生成的二维码
	private Bitmap QRBmp = null;
	//保存字符串
	private String QRStr = null;
	
	public String getQRStr() {
		return QRStr;
	}
	//flag
	private boolean isPoped = false;

	//判断保存的二维码是否一样
	private String strSave = null;
	private Bitmap bmpSave = null;
	
	//---二维码风格类型
	private int qrStyle;
	
	private DialogColorChoice mColorDialog = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qrcreate);
		
		init();
		
		addListeners();
		
	}

	private void addListeners() {

		mQRCreate.setOnClickListener(new MyOnQRCreateListener());
		mQRStyle.setOnClickListener(new MyOnQRColorListener());
		mQRSave.setOnClickListener(new MyOnQRSaveListener());
		mPopMenu.setOnClickListener(new MyOnPopMenuListener());
		
	}

	private void init() {
		isPoped = false;
		strSave = "";
		bmpSave = BitmapFactory.decodeResource(getResources(), R.drawable.bg_qrcreate_def);
		mQRView = (ImageView)findViewById(R.id.iv_QRbmp);
		mQREdit = (EditText)findViewById(R.id.et_strQR);
		mQRCreate = (ImageView)findViewById(R.id.iv_createQR);
		mMenuLayout = (LinearLayout)findViewById(R.id.ll_menuQR);
		mQRStyle = (ImageView)findViewById(R.id.iv_styleQR);
		mQRSave = (ImageView)findViewById(R.id.iv_saveQR);
		mPopMenu = (ImageView)findViewById(R.id.iv_popQRMenu);
		
		mColorDialog = new DialogColorChoice(ActivityQRCreate.this);		
		
	}
 
	/**
	 * 
	 * @param str 需要生成二维码的字符串
	 * @return 将生成的二维码转换成Bitmap并返回
	 * @throws WriterException
	 */
	public Bitmap createQRBmp(String str, int qrStyle) throws WriterException {
		//生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
		//返回BitMatrix对象，其实就是一个布尔型的数组
		//encode()方法穿传入的参数：1、编码的字符串  2、编码的类型(二维码，条形码...)   3、返回数组的大小(类似于分辨率)
		BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 300, 300);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		// 二维矩阵转为一维像素数组,也就是一直横着排了
		int halfW = width / 2;
		int halfH = height / 2;
		int[] pixels = new int[width * height];

		int a = -150;
		int b = 150;
		int c = 150;
		int d = 450;
		Random random = new Random();
		int leftTop = colors[random.nextInt(4)];
		int leftBottom = colors[random.nextInt(4)];
		int rightTop = colors[random.nextInt(4)];
		int rightBottom = colors[random.nextInt(4)];
		int center = colors[random.nextInt(4)];
		System.out.println(leftTop + ":" + leftBottom + ":" + rightTop + ":" + rightBottom + ":" + center);
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				
				//将图片绘制到指定区域中
				//就是将图片像素的颜色值写入到相应下标的数组中
				if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH && y > halfH - IMAGE_HALFWIDTH
						&& y < halfH + IMAGE_HALFWIDTH) {
					pixels[y * width + x] = mBitmap.getPixel(x - halfW + IMAGE_HALFWIDTH, y
							- halfH + IMAGE_HALFWIDTH);
				} else {
					//判断当前位置在二维矩阵中存储的boolean值				
					if (matrix.get(x, y)) {
						
						if(qrStyle == 1){
							//---左下角
							if(y >= 150 && x <= a){
								qrColor = leftBottom;
								
							//---右上角
							}else if(y <= 150 && x >= b){							
								qrColor = rightTop;
							
							//---左上角
							}else if(y <= 150 && x <= c){							
								qrColor = leftTop;
								
							//---右下角
							}else if(y >= 150 && x >= d){							
								qrColor = rightBottom;
															
							}else{							
								qrColor = center;															
							}
						}else if(qrStyle == 2){
							qrColor = colors[random.nextInt(4)];
						}
						
						pixels[y * width + x] = qrColor;
						
					}else{
						
						pixels[y * width + x] = 0xffffffff;
						
					}
				}

			}
			a++;
			b++;
			c--;
			d--;
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		// 通过像素数组生成bitmap
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		
		QRBmp = bitmap;
		return bitmap;
		
	}
	
	class MyOnQRCreateListener implements OnClickListener{

		public void onClick(View arg0) {
			// 注意：mQREdit.getText()返回的是一个Editable对象，所以要取得EditText中的内容必须要.toString()一下。
			if(mQREdit.getText().toString() != null && !mQREdit.getText().toString().equals("")){
				
				QRStr = mQREdit.getText().toString();
				mCreateQRcode(QRStr, qrStyle);
				
			}else{
				
				Toast.makeText(ActivityQRCreate.this, R.string.QRCreate_noContent, Toast.LENGTH_SHORT).show();
				Animation shakeAnimY = AnimationUtils.loadAnimation(ActivityQRCreate.this, R.anim.et_shake_y);
				mQREdit.startAnimation(shakeAnimY);
			}
						
			//使软键盘消失
			//UtilKeyboard.dismissSoftInput(mQREdit, ActivityQRCreate.this);();		
			UtilKeyboard.dismissSoftInput(mQREdit, ActivityQRCreate.this);
			
		}
		
	}
	
	public void mCreateQRcode(String qrStr, int qrStyle){
		
		if(qrStr == null || qrStr.equals("")){
			
			Toast.makeText(ActivityQRCreate.this, R.string.QRCreate_noContent, Toast.LENGTH_SHORT).show();
			return;
			
		}
		
		 // 构造需要插入的图片对象
		mBitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
		// 缩放图片
		Matrix m = new Matrix();
		float sx = (float) 2*IMAGE_HALFWIDTH / mBitmap.getWidth();
		float sy = (float) 2*IMAGE_HALFWIDTH / mBitmap.getHeight();

		m.setScale(sx, sy);
		// 重新构造一个40*40的图片
		mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(),
				mBitmap.getHeight(), m, false);

		try {			
			//s.getBytes();使用系统默认字符集(UTF-8)将字符串编码成字节型数组
			mQRView.setImageBitmap(createQRBmp(new String(qrStr.getBytes(), "ISO-8859-1"), qrStyle));
			
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	class MyOnQRColorListener implements OnClickListener{

		public void onClick(View arg0) {
			
			//使软键盘消失
			UtilKeyboard.dismissSoftInput(mQREdit, ActivityQRCreate.this);
			
			mColorDialog.show();
						
		}
		
	}
	
	class MyOnQRSaveListener implements OnClickListener{

		public void onClick(View arg0) {
				
			String result = null;
			
			try {
				if(QRBmp == null || QRStr == null || QRStr.equals("")){
					Toast.makeText(ActivityQRCreate.this, "图片或字符串有误，无法保存！", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(strSave.equals(QRStr) && bmpSave == QRBmp){
					Toast.makeText(ActivityQRCreate.this, "刚保存过,怎么又要保存！不给。", Toast.LENGTH_SHORT).show();
					return;
				}
				result = saveFile(QRBmp, QRStr);
				// 保存之后，将此次保存的字符串和图片暂存一下
				strSave = QRStr;
				bmpSave = QRBmp;
				
			} catch (IOException e) {

				e.printStackTrace();
				Toast.makeText(ActivityQRCreate.this, "保存至SD卡失败！", Toast.LENGTH_SHORT).show();
				return;
			}
			if(result != null){
				Toast.makeText(ActivityQRCreate.this, "已保存至SD卡：" + result, Toast.LENGTH_LONG).show();
			}
			
			//使软键盘消失
			UtilKeyboard.dismissSoftInput(mQREdit, ActivityQRCreate.this);
			
		}
		
	}
	 public String saveFile(Bitmap qrBmp, String qrStr) throws IOException {  
		 //首先判断SD卡存在
		 if(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			 Toast.makeText(this, "无法识别SD卡!", Toast.LENGTH_SHORT).show();
			 return null;
		 }else{
			 long fileNamePath = new Date().getTime();
			 //创建目录
			 File fileDir = new File(Environment.getExternalStorageDirectory() , "/LittleTools/ScreenShot/" + fileNamePath +"/");
			 if(!fileDir.exists()){
				 fileDir.mkdirs();
			 }
			 //创建文件			 
			 File pngFile = new File(fileDir.getAbsolutePath() + File.separator + fileNamePath + ".PNG");
			 File txtFile = new File(fileDir.getAbsolutePath() + File.separator + fileNamePath + ".TXT");
			 if(!pngFile.exists()){
				 pngFile.createNewFile();
			 }
			 if(!txtFile.exists()){
				 txtFile.createNewFile();
			 }
			 BufferedOutputStream bosPNG = new BufferedOutputStream(new FileOutputStream(pngFile)); 
			 PrintWriter bosTXT = new PrintWriter(new FileWriter(txtFile));
			 bosTXT.write(qrStr);
			 bosTXT.close();
			 qrBmp.compress(Bitmap.CompressFormat.PNG, 80, bosPNG);  
			 bosPNG.flush();  
			 bosPNG.close(); 
			 
			 return fileDir.getPath() + fileNamePath;
		 }
	        	         
	 }  
	 
	 class MyOnPopMenuListener implements OnClickListener{

		public void onClick(View v) {
			
			if(isPoped){

				mMenuLayout.setVisibility(View.GONE);
				isPoped = false;
				
			}else{

				mMenuLayout.setVisibility(View.VISIBLE);
				isPoped = true;		
				
			}			
			
		}
		 
	 }
	 	
	 
}

