package com.dy.activity;

import java.net.URLEncoder;


import com.dy.app.Declare;
import com.dy.util.HttpUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	// 声明登录、取消按钮
	private Button cancelBtn,loginBtn,exitBtn,registerBtn;
	// 声明用户名、密码输入框
	private EditText userEditText,pwdEditText;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		
		cancelBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				userEditText.setText("");
				pwdEditText.setText("");
			}
		});
		
		exitBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				System.exit(0);   
			}
		});
		
		registerBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
		
		loginBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				try {
					String url = HttpUtil.BASE_URL
							+ "LoginServlet?userName="
							+ URLEncoder.encode(
									URLEncoder.encode(userEditText.getText().toString(), "UTF-8"), "UTF-8")+"&password="
									+ URLEncoder.encode(
									URLEncoder.encode(pwdEditText.getText().toString(), "UTF-8"), "UTF-8");
					// 查询返回结果
					String result = HttpUtil.queryStringForPost(url);
					System.out.println("=========================  "+result);
					if(!result.equals("0")){
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@");
						Declare declare = (Declare) getApplicationContext();
						declare.setId(Integer.parseInt(result));
						declare.setUserName(userEditText.getText().toString());
						Toast.makeText(getApplicationContext(), "登入成功", 1).show();
						Intent intent = new Intent();
						intent.setClass(LoginActivity.this,
								MainMenuActivity.class);
						startActivity(intent);
						
					}else{
						Toast.makeText(getApplicationContext(), "账户或密码错误", 1).show();
					}
				} catch (Exception e) {
					// TODO: handle exception
					Log.i("LoginActivity",e.toString());
				}
				
			}
		});
	}
	private void init() {
		// TODO Auto-generated method stub
		// 设置标题
				setTitle("无线购物系统-登入");
				// 设置当前Activity界面布局
				setContentView(R.layout.login_system);
				// 通过findViewById方法实例化组件
				cancelBtn = (Button)findViewById(R.id.cancelButton);
				// 通过findViewById方法实例化组件
				loginBtn = (Button)findViewById(R.id.loginButton);
				exitBtn = (Button)findViewById(R.id.exitButton);
				registerBtn = (Button)findViewById(R.id.registerButton);
				// 通过findViewById方法实例化组件
				userEditText = (EditText)findViewById(R.id.userEditText);
				// 通过findViewById方法实例化组件
				pwdEditText = (EditText)findViewById(R.id.pwdEditText);
		
	}
}