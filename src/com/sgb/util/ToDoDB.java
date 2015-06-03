package com.sgb.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ToDoDB extends SQLiteOpenHelper {
	
	private final static String DATABASE_NAME = "todo_db";
	private final static int DATABASE_VERSION = 3;
	public final static String SCHEDULE_TABLE = "todo_schedule";

	public final static String FIELD_id = "_id";
	public final static String SCHEDULE_WEEK = "todo_week";
	public final static String SCHEDULE_SECTION = "todo_section";
	public final static String SCHEDULE_COURSE = "todo_course";
	public final static String SCHEDULE_PLACE = "todo_place";

	public ToDoDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql = "CREATE TABLE " + SCHEDULE_TABLE + " (" + FIELD_id
				+ " INTEGER primary key autoincrement, " + " " + SCHEDULE_WEEK
				+ " text, " + SCHEDULE_SECTION + " text, " + SCHEDULE_COURSE
				+ " text, " + SCHEDULE_PLACE + " text, "
				+ "start_time char(10),end_time char(10),teacher text )";
		db.execSQL(sql);
		try {
			db.execSQL("drop table todo_schedule");
			db.execSQL("create table if not exists todo_schedule(_id int primary key,todo_week int,todo_section int,todo_course varchar,todo_place varchar,start_time char(10),end_time char(10),teacher text)");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(1,1,1,'','','08:00','09:50','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(2,1,2,'','','10:10','12:00','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(3,1,3,'','','14:30','16:20','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(4,1,4,'','','16:30','18:20','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(5,1,5,'','','19:00','20:50','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(26,1,6,'','','00:00','23:59','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(6,2,1,'','','','','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(7,2,2,'','','','','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(8,2,3,'','','','','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(9,2,4,'','','','','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(10,2,5,'','','','','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(27,2,6,'','','','','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(11,3,1,'','','','','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(12,3,2,'','','','','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(13,3,3,'','','','','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(14,3,4,'','','','','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(15,3,5,'','','','','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(28,3,6,'','','','','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(16,4,1,'','','','','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(17,4,2,'','','','','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(18,4,3,'','','','','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(19,4,4,'','','','','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(20,4,5,'','','','','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(29,4,6,'','','','','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(21,5,1,'','','','','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(22,5,2,'','','','','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(23,5,3,'','','','','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(24,5,4,'','','','','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(25,5,5,'','','','','')");
			db.execSQL("insert into todo_schedule(_id,todo_week,todo_section,todo_course,todo_place,start_time,end_time,teacher) values(30,5,6,'','','','','')");

			Log.i("", "已初始化数据库");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void delete(int id, String table) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = FIELD_id + " = ?";
		String[] whereValue = { Integer.toString(id) };
		db.delete(table, where, whereValue);
	}

	public void updateCourse(int id, String text) {

		SQLiteDatabase db = this.getWritableDatabase();
		String where = FIELD_id + " = ?";
		String[] whereValue = { Integer.toString(id) };
		/* 将修改的值放入ContentValues */
		ContentValues cv = new ContentValues();
		cv.put(SCHEDULE_COURSE, text);
		db.update(SCHEDULE_TABLE, cv, where, whereValue);
	}

	public void updatePlace(int id, String text) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = FIELD_id + " = ?";
		String[] whereValue = { Integer.toString(id) };
		/* 将修改的值放入ContentValues */
		ContentValues cv = new ContentValues();
		cv.put(SCHEDULE_PLACE, text);
		db.update(SCHEDULE_TABLE, cv, where, whereValue);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public void updateTeacher(int id, String text) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		String where = FIELD_id + " = ?";
		String[] whereValue = { Integer.toString(id) };
		/* 将修改的值放入ContentValues */
		ContentValues cv = new ContentValues();
		cv.put("teacher", text);
		db.update(SCHEDULE_TABLE, cv, where, whereValue);
	}

	public void updateStartTime(int id, String text) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = FIELD_id + " = ?";
		String[] whereValue = { Integer.toString(id) };
		/* 将修改的值放入ContentValues */
		ContentValues cv = new ContentValues();
		cv.put("start_time", text);
		db.update(SCHEDULE_TABLE, cv, where, whereValue);
	}

	public void updateEndTime(int id, String text) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = FIELD_id + " = ?";
		String[] whereValue = { Integer.toString(id) };
		/* 将修改的值放入ContentValues */
		ContentValues cv = new ContentValues();
		cv.put("end_time", text);
		db.update(SCHEDULE_TABLE, cv, where, whereValue);
	}
}