package com.sgb.clock;

import com.sgb.activity.MainActivity;
import com.sgb.activity.R;
import com.sgb.clock.AlarmHelper;
import com.sgb.myWidget.dialog.CommonShowInfoDialog;
import com.sgb.myWidget.dialog.DialogListenerInfo;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TipAvtivity extends Activity {

	private AsyncTask<String, String, String> myTask;
	String currentMusicPath;
	MediaPlayer mMediaPlayer;
	Button stopBtn;
	Button openBtn;

	public static AlarmHelper myhHelper;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tip_layout);
		Intent myIntent = this.getIntent();
		String content = myIntent.getStringExtra("content");
		mMediaPlayer = new MediaPlayer();
		myhHelper = new AlarmHelper(this);
		TextView textView = (TextView) findViewById(R.id.tip_text1);
		textView.setText(content);

		myTask = new AsyncTask<String, String, String>() {

			@Override
			protected String doInBackground(String... arg0) {
				/* 重置MediaPlayer */
				mMediaPlayer.reset();
				/* 设置要播放的文件的路径 */
				mMediaPlayer = MediaPlayer.create(TipAvtivity.this, R.raw.music);// 播放资源文件中的歌曲
				mMediaPlayer.start();

				mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
							public void onCompletion(MediaPlayer arg0) {
								doInBackground("");
							}
						});
				return null;
			}
		};

		myTask.execute("");
		stopBtn = (Button) findViewById(R.id.tip_button1);
		openBtn = (Button) findViewById(R.id.tip_button2);
		stopBtn.setText("停止并退出");
		
		stopBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				CommonShowInfoDialog dialog = new CommonShowInfoDialog(TipAvtivity.this, new DialogListenerInfo() {
					
					@Override
					public void refreshUI(String sType) {
						if(sType.equals(DialogListenerInfo.BTN_LEFT)){
						}else{
							if (TipAvtivity.this.myTask != null) {
								if (mMediaPlayer != null) {
									mMediaPlayer.stop();
								}
								myTask.cancel(true);
							}
							new InitClock().initClock(TipAvtivity.this);
							finish();
							Intent startMain = new Intent(Intent.ACTION_MAIN);
							startMain.addCategory(Intent.CATEGORY_HOME);
							startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(startMain);
							System.exit(0);
						}
					}
				}, View.VISIBLE, "停止提醒并退出？", "否", "是");
				dialog.showdialogWithoutClose();
			}
		});
		
		openBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				CommonShowInfoDialog dialog = new CommonShowInfoDialog(TipAvtivity.this,new DialogListenerInfo() {
					
					@Override
					public void refreshUI(String sType) {
						if (sType.equals(DialogListenerInfo.BTN_LEFT)) {
						} else if (sType.equals(DialogListenerInfo.BTN_RIGHT)) {
							if (TipAvtivity.this.myTask != null) {
								if (mMediaPlayer != null) {
									mMediaPlayer.stop();
								}
								myTask.cancel(true);
							}
							new InitClock().initClock(TipAvtivity.this);
							Intent myIntent = new Intent(TipAvtivity.this, MainActivity.class);
							startActivity(myIntent);
							finish();
						}
					}
				}, View.VISIBLE, "退出提醒并启动主界面？", "否", "是");
				dialog.showdialogWithoutClose();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			
			CommonShowInfoDialog dialog = new CommonShowInfoDialog(TipAvtivity.this, new DialogListenerInfo() {
				
				@Override
				public void refreshUI(String sType) {
					if(sType.equals(DialogListenerInfo.BTN_LEFT)) {
					} else if (sType.equals(DialogListenerInfo.BTN_RIGHT)) {
						if (TipAvtivity.this.myTask != null) {
							if (mMediaPlayer != null) {
								mMediaPlayer.stop();
							}
							myTask.cancel(true);
						}
						new InitClock().initClock(TipAvtivity.this);
						finish();
						Intent startMain = new Intent(Intent.ACTION_MAIN);
						startMain.addCategory(Intent.CATEGORY_HOME);
						startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(startMain);
						System.exit(0);
					}
				}
			}, View.VISIBLE, "确定退出吗？", "否", "是");
			dialog.showdialogWithoutClose();
		}
		return false;
	}
}
