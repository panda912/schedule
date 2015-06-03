package com.sgb.activity;

import java.util.ArrayList;

import com.sgb.clock.InitClock;
import com.sgb.util.ToDoDB;
import com.sgb.util.Tools;
import com.sgb.util.Utilities;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class ChangeCourseActivity extends Activity implements OnClickListener {

	private String WEEK[] = { "星期一", "星期二", "星期三", "星期四", "星期五" };
	private String[] course = new String[6];
	private String[] add = new String[6];
	private String[] teachers = new String[6];
	private ArrayList<EditText> et_2EditTexts = new ArrayList<EditText>();
	private ArrayList<EditText> et_3EditTexts = new ArrayList<EditText>();
	private ArrayList<EditText> et_4EditTexts = new ArrayList<EditText>();

	private String week = "1";

	private ToDoDB toDoDB;
	private Cursor mCursor;
	private SQLiteDatabase db;
	private int _id;
	
	private View status_bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		setContentView(R.layout.change_course_layout);
		// 设置spinner的显示
		Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, WEEK);
		spinner1.setAdapter(adapter1);
		// 设置按钮
		TextView bt1 = (TextView) findViewById(R.id.schedule_insert_bt1);
		TextView bt2 = (TextView) findViewById(R.id.schedule_insert_bt2);
		bt1.setOnClickListener(this);
		bt2.setOnClickListener(this);
		
		status_bar = findViewById(R.id.change_course_status_bar);
		status_bar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, Tools.getStatusBarHeight(this)));

		// 设置edittext
		et_2EditTexts.add((EditText) findViewById(R.id.scheduleInsert_et1_2));
		et_3EditTexts.add((EditText) findViewById(R.id.scheduleInsert_et1_3));
		et_2EditTexts.add((EditText) findViewById(R.id.scheduleInsert_et2_2));
		et_3EditTexts.add((EditText) findViewById(R.id.scheduleInsert_et2_3));
		et_2EditTexts.add((EditText) findViewById(R.id.scheduleInsert_et3_2));
		et_3EditTexts.add((EditText) findViewById(R.id.scheduleInsert_et3_3));
		et_2EditTexts.add((EditText) findViewById(R.id.scheduleInsert_et4_2));
		et_3EditTexts.add((EditText) findViewById(R.id.scheduleInsert_et4_3));
		et_2EditTexts.add((EditText) findViewById(R.id.scheduleInsert_et5_2));
		et_3EditTexts.add((EditText) findViewById(R.id.scheduleInsert_et5_3));
		et_2EditTexts.add((EditText) findViewById(R.id.scheduleInsert_et6_2));
		et_3EditTexts.add((EditText) findViewById(R.id.scheduleInsert_et6_3));

		et_4EditTexts.add((EditText) findViewById(R.id.scheduleInsert_et1_4));
		et_4EditTexts.add((EditText) findViewById(R.id.scheduleInsert_et2_4));
		et_4EditTexts.add((EditText) findViewById(R.id.scheduleInsert_et3_4));
		et_4EditTexts.add((EditText) findViewById(R.id.scheduleInsert_et4_4));
		et_4EditTexts.add((EditText) findViewById(R.id.scheduleInsert_et5_4));
		et_4EditTexts.add((EditText) findViewById(R.id.scheduleInsert_et6_4));

		toDoDB = new ToDoDB(this);
		db = toDoDB.getReadableDatabase();

		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				week = Integer.toString(position + 1);

				String sql = "select * from todo_schedule where todo_week=" + week;
				mCursor = db.rawQuery(sql, null);
				if (mCursor != null) {
					int i = 0, n = mCursor.getCount();
					mCursor.moveToFirst();

					while (!mCursor.isAfterLast()) {

						// 获得ID
						// int id = mCursor.getInt(0);
						// 获得用户名
						course[i] = mCursor.getString(3);
						// Log.i("", mCursor.getString(3));
						// 获得密码
						add[i] = mCursor.getString(4);
						teachers[i] = mCursor.getString(7);
						i++;
						mCursor.moveToNext();
					}
				}
				for (int i = 0; i < 6; i++) {
					et_3EditTexts.get(i).setHint("上课地点");
					et_2EditTexts.get(i).setHint("课程名称");
					et_4EditTexts.get(i).setHint("上课老师");
					et_2EditTexts.get(i).setText(course[i]);
					et_3EditTexts.get(i).setText(add[i]);
					et_4EditTexts.get(i).setText(teachers[i]);
				}
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.schedule_insert_bt1) {

			// summit
			// 插入测试课程
			editTodo();
			new InitClock().initClock(ChangeCourseActivity.this);
			Utilities.showToast("更新课程成功！", this);
			Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
			ChangeCourseActivity.this.sendBroadcast(intent);
		} else if (v.getId() == R.id.schedule_insert_bt2) {
			// back
			mCursor.close();
			toDoDB.close();
			finish();
			overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
		}
	}

	private void editTodo() {

		mCursor.moveToFirst();

		/* 修改数据 */
		for (int i = 0; i < 6; i++) {
			_id = mCursor.getInt(0);
			if (i < 5) {
				mCursor.moveToNext();
			}

			toDoDB.updateCourse(_id, getEditText_2(i));
			toDoDB.updatePlace(_id, getEditText_3(i));
			toDoDB.updateTeacher(_id, getEditText_4(i));
			_id++;
		}
		_id = 0;
	}

	private String getEditText_2(int index) {
		EditText etEditText = et_2EditTexts.get(index);
		String string = etEditText.getText().toString();
		return string;
	}

	private String getEditText_3(int index) {
		EditText etEditText = et_3EditTexts.get(index);
		String string = etEditText.getText().toString();
		return string;
	}

	private String getEditText_4(int index) {
		EditText etEditText = et_4EditTexts.get(index);
		String string = etEditText.getText().toString();
		return string;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mCursor.close();
		toDoDB.close();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 如果按下的是返回键，并且没有重复
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
			overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
			return false;
		}
		return false;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev)) {

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
			return super.dispatchTouchEvent(ev);
		}
		// 必不可少，否则所有的组件都不会有TouchEvent了
		if (getWindow().superDispatchTouchEvent(ev)) {
			return true;
		}
		return onTouchEvent(ev);
	}

	public boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = { 0, 0 };
			// 获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
}
