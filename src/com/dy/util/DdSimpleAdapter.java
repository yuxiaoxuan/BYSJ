package com.dy.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dy.activity.R;

import android.widget.CheckBox;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class DdSimpleAdapter extends SimpleAdapter {
	public List<String> mChecked;
	private int[] mTo;
	private String[] mFrom;
	private ViewBinder mViewBinder;
	private List<? extends Map<String, ?>> mData;
	private int mResource;
	private int mDropDownResource;
	private LayoutInflater mInflater;
	public String[] aa = new String[100];
	int a = 0;
	HashMap<Integer, View> map = new HashMap<Integer, View>();
	Context context = null;
	int index = 0;

	public DdSimpleAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		mChecked = new ArrayList<String>();

		mTo = to;
		mFrom = from;
		mData = data;
		mResource = mDropDownResource = resource;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final int p = position;

		View view;
		final GwcViewHolder holder =new GwcViewHolder();

		if (map.get(position) == null) {
			System.out.println("contextcontext  " + context);
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = mInflater.inflate(R.layout.ddmessage_list, null);
			holder.ddid = (TextView) view.findViewById(R.id.ddid);
			holder.ddtime = (TextView) view.findViewById(R.id.ddtime);
			System.out.println("pppppppppppppppppp   " + p);
			map.put(position, view);
			view.setTag(holder);
		} else {
			view = map.get(position);
			//holder = (GwcViewHolder) view.getTag();
		}

		// holder.button.
		holder.ddid.setText(mData.get(position).get("ddid").toString());
		holder.ddtime.setText(mData.get(position).get("ddtime").toString());
		index++;
		System.out.println("indexindexindexindexindexindexindex   " + index);
		return view;
	}

	static class GwcViewHolder {
		TextView ddid;
		TextView ddtime;
	}

	/**
	 * 添加这个方法来处理Bitmap类型参数
	 * 
	 * @param v
	 * @param bitmap
	 */
	public void setViewImage(ImageView v, Bitmap bitmap) {
		v.setImageBitmap(bitmap);
	}
}
