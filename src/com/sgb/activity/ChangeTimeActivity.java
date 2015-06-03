package com.sgb.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.sgb.activity.adapter.ChangeTimeAdapter;
import com.sgb.util.ToDoDB;
import com.sgb.util.Tools;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

/**
 * @author sgb
 * @since 2015-5-30 下午6:39:37
 */
public class ChangeTimeActivity extends Activity {

	private ListView myListView = null;
	private ChangeTimeAdapter myAdapter = null;
	
	private View status_bar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		setContentView(R.layout.change_time_layout);
		status_bar = findViewById(R.id.change_time_status_bar);
		status_bar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, Tools.getStatusBarHeight(this)));
		initView();
	}

	private void initView() {

		myListView = (ListView) this.findViewById(R.id.lv_time);
		ArrayList<HashMap<Integer, String>> myList = new ArrayList<HashMap<Integer, String>>();
		// 数据库操作
		ToDoDB myDb = new ToDoDB(this);
		SQLiteDatabase sqlDB = myDb.getReadableDatabase();
		String sql = "select * from todo_schedule where todo_week = 1";
		Cursor cursor = sqlDB.rawQuery(sql, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				HashMap<Integer, String> tempMap = new HashMap<Integer, String>();
				tempMap.put(0, cursor.getString(5));
				tempMap.put(1, cursor.getString(6));
				myList.add(tempMap);
				cursor.moveToNext();
			}
		}
		// 数据库操作完成
		myAdapter = new ChangeTimeAdapter(this, myList);
		myListView.setAdapter(myAdapter);
	}
}
