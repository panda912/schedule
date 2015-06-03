package com.sgb.util;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author sgb
 * @since 2015-5-30 上午10:14:12
 */
public class Tools {

	/**
	 * get the screen height by pixel
	 * 
	 * @param activity
	 * @return
	 */
	public static int getSreenHeight(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/**
	 * get the screen width by pixel
	 * 
	 * @param activity
	 * @return
	 */
	public static int getScreenWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * get the screen density
	 * 
	 * @param activity
	 * @return
	 */
	public static int getScreenDensity(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.densityDpi;
	}

	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);// 小数点四舍五入取整
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	// 获取状态栏的高度
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusBarHeight;
	}

	// 获取从1970年到当前日期的天数
	public static int getDayOfYear(int year, int month, int day) {

		int sum = 0;
		month += 1;
		for (int y = 1970; y < year; y++) {
			if (((y % 4 == 0) & (y % 100 != 0)) | (y % 400 == 0)) {
				sum += 366;
			} else {
				sum += 365;
			}
		}

		for (int i = 1; i < month; i++) {
			switch (i) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				sum += 31;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				sum += 30;
				break;
			case 2:
				if (((year % 4 == 0) & (year % 100 != 0)) | (year % 400 == 0))
					sum += 29;
				else
					sum += 28;
			}
		}
		return sum = sum + day;
	}
}
