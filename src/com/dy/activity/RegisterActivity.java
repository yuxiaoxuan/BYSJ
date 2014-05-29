package com.dy.activity;

import java.net.URLEncoder;
import com.dy.util.HttpUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	// ������¼��ȡ����ť
	private Button Btn1,Btn2;
	// �����û��������������
	private EditText userName,pwd,phone,address;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("���߹���ϵͳ-ע��");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.register);
		// ͨ��findViewById����ʵ�������
		Btn1 = (Button)findViewById(R.id.registerButton1);
		// ͨ��findViewById����ʵ�������
		Btn2 = (Button)findViewById(R.id.registerButton2);
		// ͨ��findViewById����ʵ�������
		userName = (EditText)findViewById(R.id.userName);
		// ͨ��findViewById����ʵ�������
		pwd = (EditText)findViewById(R.id.pwd);
		phone = (EditText)findViewById(R.id.phone);
		address = (EditText)findViewById(R.id.address);
		
		Btn1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					
					if(userName.getText()!=null&&pwd.getText()!=null&&phone.getText()!=null&&address.getText()!=null){
							String url = HttpUtil.BASE_URL
									+ "RegisterServlet?userName="
									+ URLEncoder.encode(
											URLEncoder.encode(userName.getText().toString(), "UTF-8"), "UTF-8")+"&password="
											+ URLEncoder.encode(
											URLEncoder.encode(pwd.getText().toString(), "UTF-8"), "UTF-8")+"&phone="
													+ URLEncoder.encode(
															URLEncoder.encode(phone.getText().toString(), "UTF-8"), "UTF-8")+"&address="
																	+ URLEncoder.encode(
																			URLEncoder.encode(address.getText().toString(), "UTF-8"), "UTF-8");
							// ��ѯ���ؽ��
							String result = HttpUtil.queryStringForPost(url);
							if(result.equals("1")){
								Toast.makeText(getApplicationContext(), "ע��ɹ�", 1).show();
								Intent intent = new Intent();
								intent.setClass(RegisterActivity.this,
										LoginActivity.class);
								startActivity(intent);
							}else if(result.equals("2")){
								Toast.makeText(getApplicationContext(), "�û����ظ�", 1).show();
							}else{
								Toast.makeText(getApplicationContext(), "ע��ʧ��", 1).show();
							}

					}else{
						Toast.makeText(getApplicationContext(), "����Ϊ��", 1).show();
					}
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		
		Btn2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				userName.setText("");
				pwd.setText("");
				phone.setText("");
				address.setText("");
			}
		});
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 1, "���µ���");
		menu.add(0, 2, 2, "�˳�");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == 1) {//���µ���
			
			Intent intent = new Intent();
			intent.setClass(RegisterActivity.this,
					LoginActivity.class);
			startActivity(intent);
		} else if (item.getItemId() == 2) {//�˳�
			System.exit(0);  
		} 
		return true;
	}
}