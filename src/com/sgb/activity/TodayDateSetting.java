package com.sgb.activity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sgb.util.Tools;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.DatePicker;

public class TodayDateSetting extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences share = getSharedPreferences("INIT", Context.MODE_WORLD_READABLE);
		String str = share.getString("SET", "0");

		if (str.equals("0")) {
			showDialog(2);
		}
		addPreferencesFromResource(R.xml.preference);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 在欢迎界面设置BACK键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
		}
		return false;
	}

	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {

		if (preference.getKey().equals("SET")) {

			showDialog(1);
		} else if (preference.getKey().equals("ABOUT")) {
			Intent intent = new Intent();
			intent.setClass(TodayDateSetting.this, TodayDateAbout.class);
			TodayDateSetting.this.startActivity(intent);
			overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
		} else if (preference.getKey().equals("EXIT")) {
			finish();
			overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
		}

		return false;
	}

	protected Dialog onCreateDialog(int id, Bundle args) {
		switch (id) {
		case 1:
			SharedPreferences share = getSharedPreferences("INIT", MODE_WORLD_WRITEABLE);
			DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
			try {
				Date date = df.parse(share.getString("SET_DATE", "2012-0-1"));
				DatePickerDialog dpd = new DatePickerDialog(this,
						onDateSetListener, date.getYear() + 1900,
						date.getMonth() + 1, date.getDate());

				return dpd;
			} catch (ParseException e) {
				e.printStackTrace();
			}

		case 2:
			return new AlertDialog.Builder(this).setTitle("您还没有定义第一周").create();
		default:
			break;
		}
		return super.onCreateDialog(id);
	}

	DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			String dateString = Integer.toString(year) + "-"
					+ Integer.toString(monthOfYear + 1) + "-"
					+ Integer.toString(dayOfMonth);
			int setInt;
			setInt = Tools.getDayOfYear(year, monthOfYear, dayOfMonth);

			SharedPreferences share = getSharedPreferences("INIT",Context.MODE_WORLD_WRITEABLE);
			Editor editor = share.edit();
			editor.putString("SET", Integer.toString(setInt)).commit();
			editor.putString("SET_DATE", dateString).commit();
			Log.e("TodayDateEdit", "相差天数为:" + Integer.toString(setInt));
			Log.e("TodayDateEdit", "当前已选择" + dateString);
			Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
			sendBroadcast(intent);
		}
	};
}
