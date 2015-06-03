package com.sgb.activity;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Random;

import com.nineoldandroids.view.ViewHelper;
import com.sgb.myWidget.dialog.CommonShowInfoDialog;
import com.sgb.myWidget.dialog.DialogListenerInfo;
import com.sgb.myWidget.drag.DragLayout;
import com.sgb.myWidget.drag.DragLayout.DragListener;
import com.sgb.util.DateDay;
import com.sgb.util.ToDoDB;
import com.sgb.util.Tools;
import com.sgb.util.Utilities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author sgb
 * @since 2015-5-30 上午12:05:33
 */
public class MainActivity extends Activity {

	private int colors[] = { Color.rgb(0xee, 0xbf, 0xa9),
			Color.rgb(0xf0, 0x96, 0x09), Color.rgb(0x8c, 0xbf, 0x26),
			Color.rgb(0xcc, 0xcc, 0xcc), Color.rgb(0x00, 0xab, 0xa9),
			Color.rgb(0x99, 0x6c, 0x33), Color.rgb(0x3b, 0x92, 0xbc),
			Color.rgb(0xd5, 0x4d, 0x34), };

	private long exitTime = 0;
	
	boolean isLogin;
	String userName;

	private Bundle myBundle = null;

	private LinearLayout ll1, ll2, ll3, ll4, ll5;
	private TextView tv_month, tv_today, tv_title;
	private DragLayout dl;
	private ListView lv;
	private ImageView iv_icon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(LayoutParams.FLAG_TRANSLUCENT_STATUS, LayoutParams.FLAG_TRANSLUCENT_STATUS);
		setContentView(R.layout.main_layout);
		initDragLayout();
		initDataFromBundle();
		initView();
		initData();
		registerBroadcastReceiver();
	}
	
	private void initDataFromBundle(){
		 userName = getIntent().getStringExtra("username");
		 isLogin = getIntent().getBooleanExtra("isLogin", false);
	}
	
	private void showDialog() {

		CommonShowInfoDialog dialog = new CommonShowInfoDialog(
				MainActivity.this, new DialogListenerInfo() {
					@Override
					public void refreshUI(String sType) {
						if (sType.equals(DialogListenerInfo.BTN_LEFT)) {
						} else if (sType.equals(DialogListenerInfo.BTN_RIGHT)) {
							Intent intent = new Intent();
							intent.setClass(MainActivity.this, LoginActivity.class);
							startActivity(intent);
							finish();
						}
					}
				}, View.VISIBLE, "还未登录，请先登录！", "取消", "确定");
		dialog.showdialogWithoutClose();
	}

	private void initView() {

		View view = findViewById(R.id.all_table);
		// 分别表示周一到周五
		ll1 = (LinearLayout) view.findViewById(R.id.ll1);
		ll2 = (LinearLayout) view.findViewById(R.id.ll2);
		ll3 = (LinearLayout) view.findViewById(R.id.ll3);
		ll4 = (LinearLayout) view.findViewById(R.id.ll4);
		ll5 = (LinearLayout) view.findViewById(R.id.ll5);
		tv_month = (TextView) view.findViewById(R.id.tv_month);
		tv_today = (TextView) findViewById(R.id.tv_today);
		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_icon = (ImageView) findViewById(R.id.iv_icon);
		lv = (ListView) findViewById(R.id.lv);
		lv.setAdapter(new ArrayAdapter<String>(MainActivity.this,
				R.layout.item_text, new String[] { "星期一", "星期二", "星期三", "星期四",
						"星期五", "修改课程表", "修改上课时间" }));
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				Intent intent = new Intent();

				switch (position) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
					intent.setClass(MainActivity.this, ScheduleShow.class);
					intent.putExtra("POSITION", position + 1);
					startActivity(intent);
					break;
				case 5:
					if (isLogin) {
						intent.setClass(MainActivity.this, ChangeCourseActivity.class);
						startActivity(intent);
					} else {
						showDialog();
					}

					break;
				case 6:
					if (isLogin) {
						intent.setClass(MainActivity.this, ChangeTimeActivity.class);
						startActivity(intent);
					} else {
						showDialog();
					}
					break;
				default:
					break;
				}
			}
		});

		iv_icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dl.open();
			}
		});
	}
	
	private void initData() {
		
		Time time = new Time("GMT+8");
		time.setToNow();
		tv_month.setText((time.month + 1) + "月");
		
		if (TextUtils.isEmpty(userName)) {
			tv_title.setText("未登录");
		} else {
			tv_title.setText(userName);
		}
		
		tv_today.setText(getTime());

		LinearLayout[] layouts = { ll1, ll2, ll3, ll4, ll5 };
		// 每天的课程设置
		SQLiteDatabase db;
		ToDoDB toDoDB = new ToDoDB(this);
		db = toDoDB.getReadableDatabase();
		String[] course = new String[6];
		String[] add = new String[6];
		String[] timeStrings = new String[6];
		for (int j = 1; j <= layouts.length; j++) {
			String sql = "select * from todo_schedule where todo_week=" + j;// 1为星期一
			Cursor mCursor = db.rawQuery(sql, null);
			Log.i("", sql);
			// 判断游标是否为空
			if (mCursor != null) {
				int i = 0;
				mCursor.moveToFirst();

				// 遍历游标
				while (!mCursor.isAfterLast()) {

					// 获得ID
					// int id = mCursor.getInt(0);
					// 获得用户名
					course[i] = mCursor.getString(3);
					// 获得密码
					add[i] = (course[i] == null || course[i] == "" || course[i]
							.length() <= 0) ? "" : "上课地点: "
							+ mCursor.getString(4) + "\n任课老师: "
							+ mCursor.getString(7);

					if (j == 1) {
						String tt = mCursor.getString(5) + "-" + mCursor.getString(6);
						timeStrings[i] = tt;
					}
					// 上课时间
					String tempTT = (course[i] == null || course[i] == "" || course[i]
							.length() <= 0) ? "" : timeStrings[i];

					setClass(layouts[j - 1], course[i], add[i], tempTT, 2, new Random().nextInt(7));

					mCursor.moveToNext();
					i++;
				}
			}
		}
	}

	private void initDragLayout() {

		dl = (DragLayout) findViewById(R.id.dl);
		dl.setDragListener(new DragListener() {
			@Override
			public void onOpen() {
				lv.smoothScrollToPosition(new Random().nextInt(30));
			}

			@Override
			public void onClose() {
				shake();
			}

			@Override
			public void onDrag(float percent) {
				ViewHelper.setAlpha(iv_icon, 1 - percent);
			}
		});
	}

	private void shake() {
		iv_icon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
	}

	/**
	 * 设置课程的方法
	 * 
	 * @param ll
	 * @param title
	 *            课程名称
	 * @param place
	 *            地点
	 * @param time
	 *            周次
	 * @param classes
	 *            节数
	 * @param color
	 *            背景色
	 */
 private void setClass(LinearLayout ll, String title, String place,
			String time, int classes, int color) {
		View view = LayoutInflater.from(this).inflate(R.layout.course_item, null);
		view.setMinimumHeight(Tools.dip2px(this, classes * 48));
		view.setBackgroundColor(colors[color]);
		view.setAlpha(0.8f);
		((TextView) view.findViewById(R.id.title)).setText(title);
		((TextView) view.findViewById(R.id.place)).setText(place);
		((TextView) view.findViewById(R.id.time)).setText(time);
		// 为课程View设置点击的监听器
		view.setOnClickListener(new OnClickClassListener());
		TextView blank1 = new TextView(this);
		TextView blank2 = new TextView(this);
		blank1.setHeight(Tools.dip2px(this, classes));
		blank2.setHeight(Tools.dip2px(this, classes));
		ll.addView(blank1);
		ll.addView(view);
		ll.addView(blank2);
	}
 
	private String getTime() {
		
		DateDay dd = new DateDay(this);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Date date = new Date(System.currentTimeMillis());
		String dateStr = sdf.format(date);
		return  dateStr + " " + dd.getDays1() + " ";
	}

	// 点击课程的监听器
	private class OnClickClassListener implements OnClickListener {

		public void onClick(View v) {
			String title, place, time, content;
			title = (String) ((TextView) v.findViewById(R.id.title)).getText();
			place = (String) ((TextView) v.findViewById(R.id.place)).getText();
			time = (String) ((TextView) v.findViewById(R.id.time)).getText();
			content = "课程: " + title + "\n" + place + "\n时间: " + time;

			CourseDetailInfoDialog dialog = new CourseDetailInfoDialog(MainActivity.this);
			dialog.setTitle("课程信息");
			dialog.setContent(content);
			dialog.show();
		}
	}

	private void registerBroadcastReceiver() {

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.appwidget.action.APPWIDGET_UPDATE");
		registerReceiver(broadcastReceiver, intentFilter);
	}

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			String action = intent.getAction();
			if ("android.appwidget.action.APPWIDGET_UPDATE".equals(action)) {
				ll1.removeAllViews();
				ll2.removeAllViews();
				ll3.removeAllViews();
				ll4.removeAllViews();
				ll5.removeAllViews();
				initData();
			}
		}
	};

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		menu.add(1, 1, 3, "退出");
		menu.add(1, 2, 2, "设置");
		menu.add(1, 3, 1, "关于");
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case 1:
			finish();
			break;

		case 2:
			Intent intent = new Intent(this, TodayDateSetting.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
			break;

		case 3:
			Intent intent2 = new Intent(this, TodayDateAbout.class);
			this.startActivity(intent2);
			overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStart() {
		super.onStart();
		this.onCreate(myBundle);
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.onCreate(myBundle);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		this.onCreate(myBundle);
	}

	/** 双击两次退出程序 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Utilities.showToast("再按一次退出程序", this);
				exitTime = System.currentTimeMillis();
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
