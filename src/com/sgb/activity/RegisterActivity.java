package com.sgb.activity;


import com.sgb.activity.adapter.UserDbAdapter;
import com.sgb.util.Utilities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class RegisterActivity extends Activity {
	private EditText mUserText;
	private EditText mPasswordText;
	private EditText mConPasswordText;
	private UserDbAdapter mDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper = new UserDbAdapter(this);
		mDbHelper.open();
		setContentView(R.layout.register_layout);

		mUserText = (EditText) findViewById(R.id.userNameAuto);
		mPasswordText = (EditText) findViewById(R.id.password);
		mPasswordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		mConPasswordText = (EditText) findViewById(R.id.conpasswordET);
		mConPasswordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		LinearLayout myLayout1 = (LinearLayout) findViewById(R.id.myLinear2);
		myLayout1.setAlpha(10);

		Button confirmButton = (Button) findViewById(R.id.regBT);
		Button cancelButton = (Button) findViewById(R.id.canBT);

		confirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String username = mUserText.getText().toString();
				String password = mPasswordText.getText().toString();
				String conPassword = mConPasswordText.getText().toString();
				if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(conPassword)) {
					Utilities.showToast("用户名或密码不能为空！", RegisterActivity.this);
				} else {
					Cursor cursor = mDbHelper.getDiary(username);
					if (cursor.moveToFirst()) {
						Utilities.showToast("用户已存在，不能重复注册！", RegisterActivity.this);
					} else if (!password.equals(conPassword)) {
						Utilities.showToast("两次密码输入不一致，请重新输入！", RegisterActivity.this);
					} else {
						mDbHelper.createUser(username, password);
						Utilities.showToast("注册成功，进入登录界面...", RegisterActivity.this);
						Intent intent = new Intent();
						intent.setClass(RegisterActivity.this, LoginActivity.class);
						startActivity(intent);
						finish();
					}
				}
			}
		});

		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(RegisterActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
