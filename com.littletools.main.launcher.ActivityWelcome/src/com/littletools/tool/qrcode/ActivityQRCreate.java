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

	//��ʾ���ɶ�ά��ͼ��
	private ImageView mQRView;
	//����򣺻�ȡ�û�������
	private EditText mQREdit;
	//��ť��������ɶ�ά��
	private ImageView mQRCreate;
	//��ť������ı��ά����
	private ImageView mQRStyle;
	//��ť���������ͼƬ��SD����
	private ImageView mQRSave;
	//����MENU
	private ImageView mPopMenu;
	//MENU�ĸ��ؼ�
	private LinearLayout mMenuLayout;
	//ͼƬ��ȵ�һ��
	private static final int IMAGE_HALFWIDTH = 20;
	//���뵽��ά�������ͼƬ����
	private Bitmap mBitmap;
	//��Ҫ��ͼͼƬ�Ĵ�С �����趨Ϊ40*40
	int[] pixels = new int[2*IMAGE_HALFWIDTH * 2*IMAGE_HALFWIDTH];

	private int[] colors = {DataApplication.COLOR_BLUE, DataApplication.COLOR_RED, DataApplication.COLOR_PURPLE, DataApplication.COLOR_BLACK};
	
	public void setQrColor(int qrColor) {
		this.qrColor = qrColor;
	}
	// ��ά����ɫ
	private int qrColor = DataApplication.COLOR_BLACK;
	//�������ɵĶ�ά��
	private Bitmap QRBmp = null;
	//�����ַ���
	private String QRStr = null;
	
	public String getQRStr() {
		return QRStr;
	}
	//flag
	private boolean isPoped = false;

	//�жϱ���Ķ�ά���Ƿ�һ��
	private String strSave = null;
	private Bitmap bmpSave = null;
	
	//---��ά��������
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
	 * @param str ��Ҫ���ɶ�ά����ַ���
	 * @return �����ɵĶ�ά��ת����Bitmap������
	 * @throws WriterException
	 */
	public Bitmap createQRBmp(String str, int qrStyle) throws WriterException {
		//���ɶ�ά����,����ʱָ����С,��Ҫ������ͼƬ�Ժ��ٽ�������,������ģ������ʶ��ʧ��
		//����BitMatrix������ʵ����һ�������͵�����
		//encode()����������Ĳ�����1��������ַ���  2�����������(��ά�룬������...)   3����������Ĵ�С(�����ڷֱ���)
		BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 300, 300);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		// ��ά����תΪһά��������,Ҳ����һֱ��������
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
				
				//��ͼƬ���Ƶ�ָ��������
				//���ǽ�ͼƬ���ص���ɫֵд�뵽��Ӧ�±��������
				if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH && y > halfH - IMAGE_HALFWIDTH
						&& y < halfH + IMAGE_HALFWIDTH) {
					pixels[y * width + x] = mBitmap.getPixel(x - halfW + IMAGE_HALFWIDTH, y
							- halfH + IMAGE_HALFWIDTH);
				} else {
					//�жϵ�ǰλ���ڶ�ά�����д洢��booleanֵ				
					if (matrix.get(x, y)) {
						
						if(qrStyle == 1){
							//---���½�
							if(y >= 150 && x <= a){
								qrColor = leftBottom;
								
							//---���Ͻ�
							}else if(y <= 150 && x >= b){							
								qrColor = rightTop;
							
							//---���Ͻ�
							}else if(y <= 150 && x <= c){							
								qrColor = leftTop;
								
							//---���½�
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
		// ͨ��������������bitmap
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		
		QRBmp = bitmap;
		return bitmap;
		
	}
	
	class MyOnQRCreateListener implements OnClickListener{

		public void onClick(View arg0) {
			// ע�⣺mQREdit.getText()���ص���һ��Editable��������Ҫȡ��EditText�е����ݱ���Ҫ.toString()һ�¡�
			if(mQREdit.getText().toString() != null && !mQREdit.getText().toString().equals("")){
				
				QRStr = mQREdit.getText().toString();
				mCreateQRcode(QRStr, qrStyle);
				
			}else{
				
				Toast.makeText(ActivityQRCreate.this, R.string.QRCreate_noContent, Toast.LENGTH_SHORT).show();
				Animation shakeAnimY = AnimationUtils.loadAnimation(ActivityQRCreate.this, R.anim.et_shake_y);
				mQREdit.startAnimation(shakeAnimY);
			}
						
			//ʹ�������ʧ
			//UtilKeyboard.dismissSoftInput(mQREdit, ActivityQRCreate.this);();		
			UtilKeyboard.dismissSoftInput(mQREdit, ActivityQRCreate.this);
			
		}
		
	}
	
	public void mCreateQRcode(String qrStr, int qrStyle){
		
		if(qrStr == null || qrStr.equals("")){
			
			Toast.makeText(ActivityQRCreate.this, R.string.QRCreate_noContent, Toast.LENGTH_SHORT).show();
			return;
			
		}
		
		 // ������Ҫ�����ͼƬ����
		mBitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
		// ����ͼƬ
		Matrix m = new Matrix();
		float sx = (float) 2*IMAGE_HALFWIDTH / mBitmap.getWidth();
		float sy = (float) 2*IMAGE_HALFWIDTH / mBitmap.getHeight();

		m.setScale(sx, sy);
		// ���¹���һ��40*40��ͼƬ
		mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(),
				mBitmap.getHeight(), m, false);

		try {			
			//s.getBytes();ʹ��ϵͳĬ���ַ���(UTF-8)���ַ���������ֽ�������
			mQRView.setImageBitmap(createQRBmp(new String(qrStr.getBytes(), "ISO-8859-1"), qrStyle));
			
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	class MyOnQRColorListener implements OnClickListener{

		public void onClick(View arg0) {
			
			//ʹ�������ʧ
			UtilKeyboard.dismissSoftInput(mQREdit, ActivityQRCreate.this);
			
			mColorDialog.show();
						
		}
		
	}
	
	class MyOnQRSaveListener implements OnClickListener{

		public void onClick(View arg0) {
				
			String result = null;
			
			try {
				if(QRBmp == null || QRStr == null || QRStr.equals("")){
					Toast.makeText(ActivityQRCreate.this, "ͼƬ���ַ��������޷����棡", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(strSave.equals(QRStr) && bmpSave == QRBmp){
					Toast.makeText(ActivityQRCreate.this, "�ձ����,��ô��Ҫ���棡������", Toast.LENGTH_SHORT).show();
					return;
				}
				result = saveFile(QRBmp, QRStr);
				// ����֮�󣬽��˴α�����ַ�����ͼƬ�ݴ�һ��
				strSave = QRStr;
				bmpSave = QRBmp;
				
			} catch (IOException e) {

				e.printStackTrace();
				Toast.makeText(ActivityQRCreate.this, "������SD��ʧ�ܣ�", Toast.LENGTH_SHORT).show();
				return;
			}
			if(result != null){
				Toast.makeText(ActivityQRCreate.this, "�ѱ�����SD����" + result, Toast.LENGTH_LONG).show();
			}
			
			//ʹ�������ʧ
			UtilKeyboard.dismissSoftInput(mQREdit, ActivityQRCreate.this);
			
		}
		
	}
	 public String saveFile(Bitmap qrBmp, String qrStr) throws IOException {  
		 //�����ж�SD������
		 if(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			 Toast.makeText(this, "�޷�ʶ��SD��!", Toast.LENGTH_SHORT).show();
			 return null;
		 }else{
			 long fileNamePath = new Date().getTime();
			 //����Ŀ¼
			 File fileDir = new File(Environment.getExternalStorageDirectory() , "/LittleTools/ScreenShot/" + fileNamePath +"/");
			 if(!fileDir.exists()){
				 fileDir.mkdirs();
			 }
			 //�����ļ�			 
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

