package com.dy.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MainMenuActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("无线购物系统-主页面");
        setContentView(R.layout.main_menu);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
    }
    
    // 继承BaseAdapter
    public class ImageAdapter extends BaseAdapter {
    	// 上下文
        private Context mContext;
        // 构造方法
        public ImageAdapter(Context c) {
            mContext = c;
        }
        // 组件个数
        public int getCount() {
            return mThumbIds.length;
        }
        // 当前组件
        public Object getItem(int position) {
            return null;
        }
        // 当前组件id
        public long getItemId(int position) {
            return 0;
        }
        // 获得当前视图
        public View getView(int position, View convertView, ViewGroup parent) {
        	// 声明图片视图
            ImageView imageView;
            if (convertView == null) {
            	// 实例化图片视图
                imageView = new ImageView(mContext);
                // 设置图片视图属性
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }
            // 设置图片视图图片资源
            imageView.setImageResource(mThumbIds[position]);
            // 为当前视图添加监听器
            switch (position) {
			case 0:
				// 添加点餐监听器
				imageView.setOnClickListener(shoppingLinstener);
				break;
			case 1:
				// 并台监听器
				imageView.setOnClickListener(cartLinstener);
				break;
			case 2:
				// 添加转台监听器
				imageView.setOnClickListener(ordersLinstener);
				break;
			default:
				break;
			}
            
            return imageView;
        }
        // 图片资源数组
        private Integer[] mThumbIds = {
                R.drawable.shopping, R.drawable.cart,
                R.drawable.orders
        };
    }
    
    OnClickListener shoppingLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动结算Activity
			intent.setClass(MainMenuActivity.this, GoodsListActivity.class);
			startActivity(intent);
		}
	};
	
    OnClickListener cartLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动结算Activity
			intent.setClass(MainMenuActivity.this, GwcListActivity.class);
			startActivity(intent);
		}
	};
	
    OnClickListener ordersLinstener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动结算Activity
			intent.setClass(MainMenuActivity.this, DdListActivity.class);
			startActivity(intent);
		}
	};
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 1, "重新登入");
		menu.add(0, 2, 2, "退出");
	
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == 1) {//重新登入
			
			Intent intent = new Intent();
			intent.setClass(MainMenuActivity.this,
					LoginActivity.class);
			startActivity(intent);
		} else if (item.getItemId() == 2) {//退出
			System.exit(0);  
		} 
		return true;

	}
}