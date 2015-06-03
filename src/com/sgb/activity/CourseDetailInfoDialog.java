package com.sgb.activity;

/**
 * @author sgb
 * @since 2015-5-30 上午9:59:00
 */


import com.sgb.util.Utilities;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CourseDetailInfoDialog extends Dialog implements android.view.View.OnClickListener{
	
	private Activity activity;
	private DisplayMetrics dm;
	private TextView tv_title,tv_content;
	private LinearLayout ll_bg;
	private ProgressBar pb_loading;

	public CourseDetailInfoDialog(Activity activity) {
		super(activity, R.style.courseDetailInfoDialogStyle);
		this.activity = activity;
		Window win = this.getWindow();
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		win.setAttributes(lp);
		this.setContentView(R.layout.course_detail_info_layout);
		this.setCanceledOnTouchOutside(true);
		initUI();
	}
	
	private void initUI(){
		if (Utilities.dm == null) {
			dm = new DisplayMetrics();
			this.activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			Utilities.dm = dm;
		} else {
			dm = Utilities.dm;
		}
		ll_bg = (LinearLayout)findViewById(R.id.ll_bg);
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(dm.widthPixels * 4 / 5, LayoutParams.WRAP_CONTENT);
		ll_bg.setLayoutParams(layoutParams);
		ll_bg.setOnClickListener(this);
		tv_title = (TextView)findViewById(R.id.tv_title);
		tv_content = (TextView)findViewById(R.id.tv_content);
		pb_loading = (ProgressBar) this.findViewById(R.id.pb_loading);
	}

	@Override
	public void onClick(View v) {
		dismiss();
	}
	
	public void setTitle(CharSequence title){
		tv_title.setText(title);
	}
	
	/**
	 * 设置dialog的内容
	 * @param content 内容描述
	 */
	public void setContent(CharSequence content){
		pb_loading.setVisibility(View.GONE);
		tv_content.setText(content);
		tv_content.setVisibility(View.VISIBLE);
	}
}
