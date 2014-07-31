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
	 * 当前所经过的时间
	 */
	private long time = 0;
	
	/**
	 * 开始时间
	 */
	private long startTime;

	private Handler handler;
	
	/**
	 * 用于显示时间
	 */
	private TextView timeView;
	
	/**
	 * 用于列表显示分记时间
	 */
	private ListView listView;
	
	/**
	 * 开始按钮
	 */
	private Button startButton;
	
	/**
	 * 暂停按钮
	 */
	private Button pauseButton;
	
	/**
	 * 分记按钮
	 */
	private Button markButton;
	
	/**
	 * 重置按钮
	 */
	private Button resetButton;
	
	/**
	 * 分记时间数据
	 */
	private List<Long> marksList = new ArrayList<Long>();
	
	/**
	 * 秒表的当前状态
	 * 分为正在运行、暂停、停止三种状态
	 */
	private int state = 0;
	
	private  static int STATE_STOP = 0;
	private  static int STATE_RUNNING = 1;	
	private  static int STATE_PAUSE = 2;
	
	
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        
        //读取环境信息（偏好 ）
        readEnvironment();
               		
        //开始按钮
        startButton = (Button)findViewById(R.id.btn_start);
        startButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View view) {
				onStartClick(view);
			}
		});
        
        //暂停按钮
        pauseButton = (Button)findViewById(R.id.btn_pause);
        pauseButton.setOnClickListener(new OnClickListener() {
        	
        	public void onClick(View view) {
        		onPauseClick(view);
        	}
        });
        
        //分记按钮
        markButton = (Button) findViewById(R.id.btn_mark);
        markButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View view) {
				onMarkClick(view);
			}
		});
        
        //重置按钮
        resetButton = (Button) findViewById(R.id.btn_reset);
        resetButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View view) {
				onResetClick(view);
			}
		});
        
        timeView = (TextView) findViewById(R.id.tv_timeView);
        listView = (ListView) findViewById(R.id.lv_marks);
        
        //创建 handler
        handler = new Handler();
        
        // 设置各按钮
        setButtons();
        
        //设置时间显示
        if (state == STATE_STOP){
        	timeView.setText("Let's Run!");
        } else if(state == STATE_PAUSE){
        	timeView.setText(getFormatTime(time));
        }
		
        //显示列表
        refreshMarkList();
    }

    /**
     * 根据状态设置按钮显示于不显示
     */
	private void setButtons() {
		switch (state) {
		case 1://如果正在运行
			startButton.setVisibility(View.GONE);
			pauseButton.setVisibility(View.VISIBLE);
			markButton.setVisibility(View.VISIBLE);
			resetButton.setVisibility(View.GONE);
			resetButton.setEnabled(false);
			break;
		case 2://如果暂停中
			startButton.setVisibility(View.VISIBLE);
			pauseButton.setVisibility(View.GONE);
			markButton.setVisibility(View.GONE);
			resetButton.setVisibility(View.VISIBLE);
			resetButton.setEnabled(true);
			break;
		case 0://如果停止中
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
     * 读取环境
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
     * 保存环境
     */
    private void saveEnvironment() {
    	SharedPreferences sharedPreferences = getSharedPreferences("TimePreferences", MODE_PRIVATE);
    	Editor editor = sharedPreferences.edit();
    	editor.putInt("state", state);
    	editor.putLong("time", time);
    	editor.putLong("startTime", startTime);
    	editor.commit();
    	
    	//保存分记数据
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
		if (state == STATE_RUNNING){//如果正在运行
			handler.removeCallbacks(this);
		}
		
	}


	
	protected void onDestroy() {
		super.onDestroy();
		//保存环境
		saveEnvironment();
		Toast.makeText(this, "当前数据已保存", Toast.LENGTH_LONG).show();
	}

	
	protected void onResume() {
		super.onResume();
		if (state == STATE_RUNNING){//如果正在运行
			handler.post(this);
		}
	}

	/**
     * 点击重置
     * @param view
     */
    protected void onResetClick(View view) {
    	//设置状态为停止
    	state = STATE_STOP;
    	
    	//不再刷新
    	if (state == STATE_RUNNING){//如果正在运行
			handler.removeCallbacks(this);
		}
    	
    	//初始化分记数组
    	marksList = new ArrayList<Long>();
		refreshMarkList();
		
		//设置时间显示
		time = 0;
		timeView.setText(getFormatTime(time));
		
		// 设置各按钮
        setButtons();
	}

    /**
     * 点击暂停
     * @param view
     */
	protected void onPauseClick(View view) {
		//不再刷新
		if (state == STATE_RUNNING){//如果正在运行
			handler.removeCallbacks(this);
		}
    	
		//设置状态为暂停
		state = STATE_PAUSE;
		
		// 设置各按钮
        setButtons();
	}

	/**
     * 点击开始
     * @param view
     */
	protected void onStartClick(View view) {

		//开始时间 = 当前时间 - 已走时间 
		startTime = new Date().getTime() - time;
		
		handler.post(this);
		

		
		//设置状态为正在运行
		state = STATE_RUNNING;
		
		// 设置各按钮
        setButtons();
	}

	/**
	 * 点击分记
	 * 
	 * @param view
	 */
	protected void onMarkClick(View view) {
		
		if(time == 0){
			return;
		}
		// 添加分记数据, 最近的加在最前面
		marksList.add(0, time);
		
		//刷新列表
		refreshMarkList();
	}

	/**
	 * 刷新列表
	 */
	private void refreshMarkList() {
		//显示在列表中
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		int no = marksList.size();//序号
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
	 * 此方法很棒！
	 */
	public void run() {
		
		//当前时间 — 开始时间 = 已走时间
		time = new Date().getTime() - startTime;
		//显示此已走时间到UI上
		timeView.setText(getFormatTime(time));		
		//每隔5毫秒刷新一次,因为程序最小精确到10毫秒，所以两次刷新间隔要小于10毫秒
		handler.postDelayed(this, 5);
		

	}

	/**
	 * 得到一个格式化的时间
	 * 
	 * @param     
	 * 		time 毫秒
	 * 		
	 * @return 
	 * 		分：秒：毫秒 (00：00：00)
	 */
	private String getFormatTime(long time) {	//传入的long型变量time，其实就是毫秒	
		
		long millisecond = time % 1000;
		long second = (time / 1000) % 60;
		long minute = time / 1000 / 60;
		
		/**
		 * 很棒！
		 */
		//毫秒显示两位：最小精确到10毫秒
		String strMillisecond = ("00" + millisecond/10).substring(("00" + millisecond/10).length() - 2);
		//秒显示两位
		String strSecond = ("00" + second).substring(("00" + second).length() - 2);
		//分显示两位
		String strMinute = ("00" + minute).substring(("00" + minute).length() - 2);
		
		return strMinute + ":" + strSecond + ":" + strMillisecond;
		
	}
	
	
}