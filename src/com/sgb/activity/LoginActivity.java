package com.sgb.activity;


import com.sgb.activity.adapter.UserDbAdapter;
import com.sgb.clock.InitClock;
import com.sgb.util.Utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginActivity extends Activity {

	AutoCompleteTextView cardNumAuto;
	EditText passwordET;
	Button logBT;
	Button regBT;
	Button gotoMap;

	CheckBox savePasswordCB;
	SharedPreferences sp;
	String cardNumStr;
	String passwordStr;
	private UserDbAdapter mDbHelper;

	/** Called when the activity is first created. */
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);

		new InitClock().initClock(this);
		cardNumAuto = (AutoCompleteTextView) findViewById(R.id.cardNumAuto);
		passwordET = (EditText) findViewById(R.id.passwordET);
		logBT = (Button) findViewById(R.id.logBT);
		regBT = (Button) findViewById(R.id.resBT);
		gotoMap = (Button) findViewById(R.id.goto_map);
		LinearLayout myLayout1 = (LinearLayout) findViewById(R.id.myLinear1);
		myLayout1.setAlpha(90);

		sp = this.getSharedPreferences("passwordFile", MODE_PRIVATE);
		savePasswordCB = (CheckBox) findViewById(R.id.savePasswordCB);
		savePasswordCB.setChecked(true);// 默认为记住密码
		cardNumAuto.setThreshold(1);// 输入1个字母就开始自动提示
		passwordET.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);

		cardNumAuto.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String[] allUserName = new String[sp.getAll().size()];
				allUserName = sp.getAll().keySet().toArray(new String[0]);

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						LoginActivity.this,
						android.R.layout.simple_dropdown_item_1line,
						allUserName);

				cardNumAuto.setAdapter(adapter);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				passwordET.setText(sp.getString(cardNumAuto.getText().toString(), ""));// 自动输入密码
			}
		});

		gotoMap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Utilities.showToast("直接进入界面", LoginActivity.this);
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, MainActivity.class);
				intent.putExtra("isLogin", false);
				startActivity(intent);
				finish();
			}
		});
		// 登陆
		logBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				cardNumStr = cardNumAuto.getText().toString();
				passwordStr = passwordET.getText().toString();
				if (TextUtils.isEmpty(cardNumStr) || TextUtils.isEmpty(passwordStr)) {
					Utilities.showToast("用户名和密码不能为空！", LoginActivity.this);
				} else {
					Cursor cursor = mDbHelper.getDiary(cardNumStr);

					if (!cursor.moveToFirst()) {
						Utilities.showToast("用户不存在", LoginActivity.this);
					} else if (!passwordStr.equals(cursor.getString(2))) {
						Utilities.showToast("密码不正确，请再输一次", LoginActivity.this);
					} else {
						if (savePasswordCB.isChecked()) {// 登陆成功才保存密码
							sp.edit().putString(cardNumStr, passwordStr).commit();
						}
						Utilities.showToast("登录成功，进入界面", LoginActivity.this);
						Intent intent = new Intent();
						intent.putExtra("username", cardNumStr);
						intent.putExtra("isLogin", true);
						intent.setClass(LoginActivity.this, MainActivity.class);
						startActivity(intent);
						finish();
					}
				}
			}
		});
		
		regBT.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
				finish();
			}
		});

		mDbHelper = new UserDbAdapter(this);
		mDbHelper.open();
	}
}