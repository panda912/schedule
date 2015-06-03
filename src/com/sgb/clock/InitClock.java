package com.sgb.clock;

import java.sql.Timestamp;

import com.sgb.clock.AlarmHelper;
import com.sgb.util.ToDoDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class InitClock {

	public static AlarmHelper myhHelper;
	public static final long SEVEN_LONG_TIMES = StringToTimeStape("2011-05-23 00:00:00").getTime() - StringToTimeStape("2011-05-16 00:00:00").getTime();
	public static final long FIVE_MINITE_LONG_TIMES = StringToTimeStape("2011-05-16 00:05:00").getTime() - StringToTimeStape("2011-05-16 00:00:00").getTime();
	private String[] timeStrings = new String[6];

	public void initClock(Context context) {
		myhHelper = new AlarmHelper(context);
		SQLiteDatabase db;
		ToDoDB toDoDB = new ToDoDB(context);
		db = toDoDB.getReadableDatabase();
		String[] course = new String[6];
		String[] add = new String[6];
		String[] contents = new String[6];
		int[] ids = new int[6];
		long[] time1s = new long[6];
		for (int j = 1; j <= 5; j++) {
			String sql = "select * from todo_schedule where todo_week=" + j;// 1为星期一
			Cursor mCursor = db.rawQuery(sql, null);
			if (mCursor != null) {
				int i = 0;
				mCursor.moveToFirst();
				// 遍历游标 11.
				while (!mCursor.isAfterLast()) {

					// 获得ID
					// int id = mCursor .getInt(0);
					// 获得用户名
					ids[i] = mCursor.getInt(0);
					course[i] = mCursor.getString(3);
					if (j == 1) {
						String tt = mCursor.getString(5) + "-" + mCursor.getString(6);
						timeStrings[i] = tt;
					}
					String tempTT = (course[i] == null || course[i] == "" || course[i].length() <= 0) ? "22" : timeStrings[i];
					
					contents[i] = "课程名称：" + mCursor.getString(3) + "\n上课地点：" + mCursor.getString(4) 
							+ "\n上课时间：" + tempTT + "\n任课老师：" + mCursor.getString(7);
					myhHelper.closeAlarm(ids[i], "", "");

					if (j == 1) {
						time1s[i] = StringToTimeStape("1980-01-07 " + mCursor.getString(5) + ":00").getTime();
					}
					long time1 = time1s[i] - StringToTimeStape("1980-01-07 00:00:00").getTime() + (j - 1)
							* (SEVEN_LONG_TIMES / 7) - FIVE_MINITE_LONG_TIMES;
					
					time1 = time1 < 0 ? SEVEN_LONG_TIMES + time1 : time1;

					if (!(course[i] == null || course[i] == "" || course[i].length() <= 0)) {
						myhHelper.openAlarm(ids[i], "", contents[i], getStratTime(time1));
					}
					mCursor.moveToNext();
					i++;
				}
			}
		}
	}

	public static Timestamp StringToTimeStape(String dateAndTime) {// 将字符串数据转换为TimeStamp类型的数据，

		try {
			Timestamp tt = Timestamp.valueOf(dateAndTime);
			return tt;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public static long getStratTime(long time) {
		long t1980_01_07 = StringToTimeStape("1980-01-07 00:00:00").getTime();
		long currentLongTime = (System.currentTimeMillis() - t1980_01_07) % SEVEN_LONG_TIMES;
		long time1 = time - currentLongTime;
		if (time1 >= 0) {
			return System.currentTimeMillis() + time1;
		} else {
			return System.currentTimeMillis() + time1 + SEVEN_LONG_TIMES;
		}
	}
}
