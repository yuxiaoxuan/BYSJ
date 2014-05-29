package com.dy.activity;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import com.dy.app.Declare;
import com.dy.util.GwcSimpleAdapter;
import com.dy.util.HttpUtil;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class CopyOfGwcListActivity extends Activity {

	GwcSimpleAdapter adapter;
	ListView lv;
	EditText et;
	String question;
	List<Map<String, Object>> list;
	List<Integer> listItemID = new ArrayList<Integer>();
	String goodsName; 
	int goodsCount; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gwclist);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("当前位置---购物车");
		} else {
			setTitle("您好：" + username + "   当前位置---购物车");
		}

		setViews();
	}

	private void setViews() {
		lv = (ListView) findViewById(R.id.gwc_list_view);
		list= getDatas();
		adapter = new GwcSimpleAdapter(this,list,
				R.layout.message_list, new String[] { "icon", "name", "price",
						"count" }, new int[] { R.id.ml_icon, R.id.listName,
						R.id.listPrice, R.id.listCount });
		lv.setAdapter(adapter);
		// lv.setOnItemClickListener();

		lv.setOnItemClickListener(myListener1);

		// 添加长按点击
		lv.setOnCreateContextMenuListener(myListener2);

	}

	private OnCreateContextMenuListener myListener2 = new OnCreateContextMenuListener() {

		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "删除");
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {

		if (item.getItemId() == 0) {//删除
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取问题内容
			HashMap<String, Object> itemd = (HashMap<String, Object>) list.get(position);
			question = (String) itemd.get("name");
			dialog();
		} 
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(CopyOfGwcListActivity.this);
		builder.setMessage("确认删除吗？");

		builder.setTitle("提示");

		builder.setPositiveButton("确认", new OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				try {
					Declare declare = (Declare) getApplicationContext();
					int myid = declare.getId();
					String url = HttpUtil.BASE_URL
							+ "DeleteGwcServlet?myid="
							+ myid
							+ "&name="
							+ URLEncoder.encode(
									URLEncoder.encode(question, "UTF-8"),
									"UTF-8");
					// 查询返回结果
					String result = HttpUtil.queryStringForPost(url);
					if (!result.equals("0")) {
						setViews();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				dialog.dismiss();

			}
		});

		builder.setNegativeButton("取消", new OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
			}
		});

		builder.create().show();
	}

	private OnItemClickListener myListener1 = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			try {
			HashMap<String, Object> item = (HashMap<String, Object>) arg0
					.getItemAtPosition(arg2);
			goodsName = item.get("name")+"";
			//goodsCount = item.get("count");
			goodsCount= Integer.parseInt(item.get("count")+"");
			et = new EditText(CopyOfGwcListActivity.this);
			 // 取得自定义View  
			
				 LayoutInflater layoutInflater = LayoutInflater.from(CopyOfGwcListActivity.this);
			        View inputNumView = layoutInflater.inflate(R.layout.input_num,null); 
			        Dialog alertDialog = new AlertDialog.Builder(CopyOfGwcListActivity.this).setTitle("请输入购买数量")
							.setIcon(android.R.drawable.ic_dialog_info)
							.setView(et)
							.setPositiveButton("确定", myListener3)
							.setNegativeButton("取消", null).create();
			        alertDialog.show();
			} catch (Exception e) {
				Log.i("GwcListActivity",e.toString());
			}
	       
		}
	};
	
	
	// 输入购买数量
		private OnClickListener myListener3 = new OnClickListener() {

			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				String count = et.getText().toString();
				int intCount = Integer.parseInt(count);
				if(intCount>goodsCount){
					Toast.makeText(getApplicationContext(), "库存不足", 1).show();
				}else{
					try {
						Declare declare = (Declare) getApplicationContext();
						int myid = declare.getId();
						String url = HttpUtil.BASE_URL
								+ "UpdateGwcCountServlet?myid="
								+ myid
								+ "&name="
								+ URLEncoder.encode(
										URLEncoder.encode(goodsName, "UTF-8"),
										"UTF-8")+"&count="+count;
						// 查询返回结果
						String result = HttpUtil.queryStringForPost(url);
						if (!result.equals("0")) {
							setViews();
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				
			}

		};

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			Declare declare = (Declare) getApplicationContext();
			int myid = declare.getId();
			System.out.println("myid          =" + myid);
			String url = HttpUtil.BASE_URL + "GwcListServlet?myid="+myid;

			// 查询返回结果
			String result = HttpUtil.queryStringForPost(url);
			System.out.println("=========================  " + result);
			String[] results = result.split("@");
			for (int i = 0; i < results.length; i++) {
				String[] photos = results[i].split(",");

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("name", photos[0]);
					map.put("price", photos[1]);
					map.put("count", photos[2]);
					map.put("gwccount", photos[3]);
					list.add(map);
			}
		} catch (Exception e) {

			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 1, "继续购物");
		menu.add(0, 2, 2, "清空购物车");
		menu.add(0, 3, 3, "生成订单");
		menu.add(0, 4, 4, "退出/重新登入");
	
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == 1) {//确认提交
			
			Intent intent = new Intent();
			intent.setClass(CopyOfGwcListActivity.this,
					GoodsListActivity.class);
			startActivity(intent);
			
		} else if (item.getItemId() == 2) {
			Declare declare = (Declare) getApplicationContext();
			int myid = declare.getId();
			String url = HttpUtil.BASE_URL
					+ "DeleteAllGwcServlet?myid="+ myid;
					
			// 查询返回结果
			String result = HttpUtil.queryStringForPost(url);
			System.out.println("resultresultresult  "+result);
			if (!result.equals("0")) {//成功 //在这里跳转
				setViews();
			}
			
			
		} else if (item.getItemId() == 3) {//生成订单
			Intent intent = new Intent();
			intent.setClass(CopyOfGwcListActivity.this,
					DingdanActivity.class);
			startActivity(intent);
			
		} else if (item.getItemId() == 4) {
			System.exit(0);  
		} 
		return true;

	}
}