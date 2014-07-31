package com.littletools.tool.stopwatch;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.littletools.*;

public class ActivityStopwatch extends Activity implements Runnable {
	
	/**
	 * ��ǰ��������ʱ��
	 */
	private long time = 0;
	
	/**
	 * ��ʼʱ��
	 */
	private long startTime;

	private Handler handler;
	
	/**
	 * ������ʾʱ��
	 */
	private TextView timeView;
	
	/**
	 * �����б���ʾ�ּ�ʱ��
	 */
	private ListView listView;
	
	/**
	 * ��ʼ��ť
	 */
	private Button startButton;
	
	/**
	 * ��ͣ��ť
	 */
	private Button pauseButton;
	
	/**
	 * �ּǰ�ť
	 */
	private Button markButton;
	
	/**
	 * ���ð�ť
	 */
	private Button resetButton;
	
	/**
	 * �ּ�ʱ������
	 */
	private List<Long> marksList = new ArrayList<Long>();
	
	/**
	 * ���ĵ�ǰ״̬
	 * ��Ϊ�������С���ͣ��ֹͣ����״̬
	 */
	private int state = 0;
	
	private  static int STATE_STOP = 0;
	private  static int STATE_RUNNING = 1;	
	private  static int STATE_PAUSE = 2;
	
	
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        
        //��ȡ������Ϣ��ƫ�� ��
        readEnvironment();
               		
        //��ʼ��ť
        startButton = (Button)findViewById(R.id.btn_start);
        startButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View view) {
				onStartClick(view);
			}
		});
        
        //��ͣ��ť
        pauseButton = (Button)findViewById(R.id.btn_pause);
        pauseButton.setOnClickListener(new OnClickListener() {
        	
        	public void onClick(View view) {
        		onPauseClick(view);
        	}
        });
        
        //�ּǰ�ť
        markButton = (Button) findViewById(R.id.btn_mark);
        markButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View view) {
				onMarkClick(view);
			}
		});
        
        //���ð�ť
        resetButton = (Button) findViewById(R.id.btn_reset);
        resetButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View view) {
				onResetClick(view);
			}
		});
        
        timeView = (TextView) findViewById(R.id.tv_timeView);
        listView = (ListView) findViewById(R.id.lv_marks);
        
        //���� handler
        handler = new Handler();
        
        // ���ø���ť
        setButtons();
        
        //����ʱ����ʾ
        if (state == STATE_STOP){
        	timeView.setText("Let's Run!");
        } else if(state == STATE_PAUSE){
        	timeView.setText(getFormatTime(time));
        }
		
        //��ʾ�б�
        refreshMarkList();
    }

    /**
     * ����״̬���ð�ť��ʾ�ڲ���ʾ
     */
	private void setButtons() {
		switch (state) {
		case 1://�����������
			startButton.setVisibility(View.GONE);
			pauseButton.setVisibility(View.VISIBLE);
			markButton.setVisibility(View.VISIBLE);
			resetButton.setVisibility(View.GONE);
			resetButton.setEnabled(false);
			break;
		case 2://�����ͣ��
			startButton.setVisibility(View.VISIBLE);
			pauseButton.setVisibility(View.GONE);
			markButton.setVisibility(View.GONE);
			resetButton.setVisibility(View.VISIBLE);
			resetButton.setEnabled(true);
			break;
		case 0://���ֹͣ��
			startButton.setVisibility(View.VISIBLE);
			pauseButton.setVisibility(View.GONE);
			markButton.setVisibility(View.GONE);
			resetButton.setVisibility(View.VISIBLE);
			resetButton.setEnabled(false);
			break;
		default:
			break;
		}
	}

    /**
     * ��ȡ����
     */
    @SuppressWarnings("unchecked")
	private void readEnvironment() {
    	SharedPreferences sharedPreferences = getSharedPreferences("TimePreferences", MODE_PRIVATE);
    	state = sharedPreferences.getInt("state", STATE_STOP);
    	startTime = sharedPreferences.getLong("startTime", 0);
    	time = sharedPreferences.getLong("time", 0);
    	
    	marksList = new ArrayList<Long>();
    	SharedPreferences sharedPreferencesMarks = getSharedPreferences("MarksPreferences", MODE_PRIVATE);
    	Map<String, Long> mapMarks = (Map<String, Long>) sharedPreferencesMarks.getAll();
    	for (int i = 0; i < mapMarks.size(); i++){
    		Long mark = mapMarks.get("" + i);
    		marksList.add(mark);
    	}
    	
	}
    /**
     * ���滷��
     */
    private void saveEnvironment() {
    	SharedPreferences sharedPreferences = getSharedPreferences("TimePreferences", MODE_PRIVATE);
    	Editor editor = sharedPreferences.edit();
    	editor.putInt("state", state);
    	editor.putLong("time", time);
    	editor.putLong("startTime", startTime);
    	editor.commit();
    	
    	//����ּ�����
    	SharedPreferences sharedPreferencesMarks = getSharedPreferences("MarksPreferences", MODE_PRIVATE);
    	Editor editorMarks = sharedPreferencesMarks.edit();
    	editorMarks.clear();
    	for(Long mark : marksList){
    		editorMarks.putLong(""  + marksList.indexOf(mark), mark.longValue());
    	}
    	editorMarks.commit();
    }

	
	protected void onPause() {
		super.onPause();
		if (state == STATE_RUNNING){//�����������
			handler.removeCallbacks(this);
		}
		
	}


	
	protected void onDestroy() {
		super.onDestroy();
		//���滷��
		saveEnvironment();
		Toast.makeText(this, "��ǰ�����ѱ���", Toast.LENGTH_LONG).show();
	}

	
	protected void onResume() {
		super.onResume();
		if (state == STATE_RUNNING){//�����������
			handler.post(this);
		}
	}

	/**
     * �������
     * @param view
     */
    protected void onResetClick(View view) {
    	//����״̬Ϊֹͣ
    	state = STATE_STOP;
    	
    	//����ˢ��
    	if (state == STATE_RUNNING){//�����������
			handler.removeCallbacks(this);
		}
    	
    	//��ʼ���ּ�����
    	marksList = new ArrayList<Long>();
		refreshMarkList();
		
		//����ʱ����ʾ
		time = 0;
		timeView.setText(getFormatTime(time));
		
		// ���ø���ť
        setButtons();
	}

    /**
     * �����ͣ
     * @param view
     */
	protected void onPauseClick(View view) {
		//����ˢ��
		if (state == STATE_RUNNING){//�����������
			handler.removeCallbacks(this);
		}
    	
		//����״̬Ϊ��ͣ
		state = STATE_PAUSE;
		
		// ���ø���ť
        setButtons();
	}

	/**
     * �����ʼ
     * @param view
     */
	protected void onStartClick(View view) {

		//��ʼʱ�� = ��ǰʱ�� - ����ʱ�� 
		startTime = new Date().getTime() - time;
		
		handler.post(this);
		

		
		//����״̬Ϊ��������
		state = STATE_RUNNING;
		
		// ���ø���ť
        setButtons();
	}

	/**
	 * ����ּ�
	 * 
	 * @param view
	 */
	protected void onMarkClick(View view) {
		
		if(time == 0){
			return;
		}
		// ��ӷּ�����, ����ļ�����ǰ��
		marksList.add(0, time);
		
		//ˢ���б�
		refreshMarkList();
	}

	/**
	 * ˢ���б�
	 */
	private void refreshMarkList() {
		//��ʾ���б���
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		int no = marksList.size();//���
		for(long mark : marksList){
			Map<String, Object> map = new HashMap<String, Object>();			
			map.put("no", no + this.getString(R.string.stopw_btn_mark) + " : ");
			map.put("time", getFormatTime(mark));
			no--;
			list.add(map);
			
		}
		String[] from = new String[]{"no","time"};
		int[] to = new int[]{R.id.tv_markNo, R.id.tv_markTime};
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.item_list_stopwatch, from, to);
		listView.setAdapter(simpleAdapter);
		
	}
	
	/**
	 * �˷����ܰ���
	 */
	public void run() {
		
		//��ǰʱ�� �� ��ʼʱ�� = ����ʱ��
		time = new Date().getTime() - startTime;
		//��ʾ������ʱ�䵽UI��
		timeView.setText(getFormatTime(time));		
		//ÿ��5����ˢ��һ��,��Ϊ������С��ȷ��10���룬��������ˢ�¼��ҪС��10����
		handler.postDelayed(this, 5);
		

	}

	/**
	 * �õ�һ����ʽ����ʱ��
	 * 
	 * @param     
	 * 		time ����
	 * 		
	 * @return 
	 * 		�֣��룺���� (00��00��00)
	 */
	private String getFormatTime(long time) {	//�����long�ͱ���time����ʵ���Ǻ���	
		
		long millisecond = time % 1000;
		long second = (time / 1000) % 60;
		long minute = time / 1000 / 60;
		
		/**
		 * �ܰ���
		 */
		//������ʾ��λ����С��ȷ��10����
		String strMillisecond = ("00" + millisecond/10).substring(("00" + millisecond/10).length() - 2);
		//����ʾ��λ
		String strSecond = ("00" + second).substring(("00" + second).length() - 2);
		//����ʾ��λ
		String strMinute = ("00" + minute).substring(("00" + minute).length() - 2);
		
		return strMinute + ":" + strSecond + ":" + strMillisecond;
		
	}
	
	
}