package com.sgb.util;


import com.sgb.activity.R;
import com.sgb.util.ToDoDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * 给viewPager上不同数据,周一到周五
 * 
 * @author sgb
 * 
 */
public class GetSchedule {
	private static String[] days1 = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
	private TextView tv0, tv1_2, tv1_3, tv2_2, tv2_3, tv3_2, tv3_3, tv4_2,
			tv4_3, tv5_2, tv5_3, tv6_2, tv6_3, tv1_1, tv2_1, tv3_1, tv4_1,
			tv5_1, tv6_1;
	private String[] course = new String[6];
	private String[] add = new String[6];
	private String[] startTimes = new String[6];
	private String[] endTimes = new String[6];
	private ToDoDB toDoDB;
	private Cursor mCursor;
	private Context context;

	public GetSchedule(Context context) {
		this.context = context;
	}

	public View getScheduleView(int week) {// 1为星期一

		LayoutInflater mInflater = LayoutInflater.from(context);
		View myView = mInflater.inflate(R.layout.app_schedule_show_page, null);

		tv0 = (TextView) myView.findViewById(R.id.show_tv0);

		tv1_1 = (TextView) myView.findViewById(R.id.show_tv1_1);
		tv1_2 = (TextView) myView.findViewById(R.id.show_tv1_2);
		tv1_3 = (TextView) myView.findViewById(R.id.show_tv1_3);

		tv2_1 = (TextView) myView.findViewById(R.id.show_tv2_1);
		tv2_2 = (TextView) myView.findViewById(R.id.show_tv2_2);
		tv2_3 = (TextView) myView.findViewById(R.id.show_tv2_3);

		tv3_1 = (TextView) myView.findViewById(R.id.show_tv3_1);
		tv3_2 = (TextView) myView.findViewById(R.id.show_tv3_2);
		tv3_3 = (TextView) myView.findViewById(R.id.show_tv3_3);

		tv4_1 = (TextView) myView.findViewById(R.id.show_tv4_1);
		tv4_2 = (TextView) myView.findViewById(R.id.show_tv4_2);
		tv4_3 = (TextView) myView.findViewById(R.id.show_tv4_3);

		tv5_1 = (TextView) myView.findViewById(R.id.show_tv5_1);
		tv5_2 = (TextView) myView.findViewById(R.id.show_tv5_2);
		tv5_3 = (TextView) myView.findViewById(R.id.show_tv5_3);

		tv6_1 = (TextView) myView.findViewById(R.id.show_tv6_1);
		tv6_2 = (TextView) myView.findViewById(R.id.show_tv6_2);
		tv6_3 = (TextView) myView.findViewById(R.id.show_tv6_3);

		SQLiteDatabase db;
		toDoDB = new ToDoDB(context);
		db = toDoDB.getReadableDatabase();
		String sql = "select * from todo_schedule where todo_week=" + week;// 1为星期一
		mCursor = db.rawQuery(sql, null);

		// 判断游标是否为空
		if (mCursor != null) {
			int i = 0, n = mCursor.getCount();
			mCursor.moveToFirst();
			Log.i("", "mCursor !=null");
			Log.i("n=?", n + "");

			// 遍历游标 11.
			while (!mCursor.isAfterLast()) {

				// 获得ID
				// int id = mCursor .getInt(0);
				// 获得用户名
				if (mCursor.getString(3) == null || mCursor.getString(3) == ""
						|| mCursor.getString(3).length() <= 0) {
					course[i] = null;
					add[i] = null;
					mCursor.moveToNext();
					i++;
					continue;
				}
				course[i] = "课程名:" + mCursor.getString(3);

				add[i] = "上课地点:" + mCursor.getString(4) + "\n上课老师:"
						+ mCursor.getString(7);
				i++;
				mCursor.moveToNext();
			}
		}

		Cursor timeCursor = db.rawQuery(
				"select * from todo_schedule where todo_week=1", null);
		if (timeCursor != null) {
			int i = 0, n = timeCursor.getCount();
			timeCursor.moveToFirst();
			Log.i("", "timeCursor !=null");
			Log.i("n=?", n + "");

			// 遍历游标 11.
			while (!timeCursor.isAfterLast()) {

				// 获得ID
				// int id = timeCursor .getInt(0);
				// 获得用户名
				startTimes[i] = timeCursor.getString(5);
				endTimes[i] = timeCursor.getString(6);
				i++;
				timeCursor.moveToNext();
			}
		}

		tv0.setText(days1[week - 1]);

		tv1_1.setText("1,2节\n" + startTimes[0] + "-" + endTimes[0]);
		tv1_2.setText(course[0]);
		tv1_3.setText(add[0]);

		tv2_1.setText("3,4节\n" + startTimes[1] + "-" + endTimes[1]);
		tv2_2.setText(course[1]);
		tv2_3.setText(add[1]);

		tv3_1.setText("5,6节\n" + startTimes[2] + "-" + endTimes[2]);
		tv3_2.setText(course[2]);
		tv3_3.setText(add[2]);

		tv4_1.setText("7,8节\n" + startTimes[3] + "-" + endTimes[3]);
		tv4_2.setText(course[3]);
		tv4_3.setText(add[3]);

		tv5_1.setText("9,10节\n" + startTimes[4] + "-" + endTimes[4]);
		tv5_2.setText(course[4]);
		tv5_3.setText(add[4]);

		tv6_1.setText("选修\n" + startTimes[5] + "-" + endTimes[5]);
		tv6_2.setText(course[5]);
		tv6_3.setText(add[5]);

		Log.i("tv", "已设置tv");
		mCursor.close();
		timeCursor.close();
		toDoDB.close();
		return myView;
	}
}
