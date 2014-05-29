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
import android.view.LayoutInflater;   
import android.view.View;   
import android.view.ViewGroup;   
import android.widget.Checkable;   
import android.widget.ImageView;   
import android.widget.SimpleAdapter;   
import android.widget.TextView;   
  
public class ImageSimpleAdapter extends SimpleAdapter {   
	public List<Boolean> mChecked;
    private int[] mTo;   
    private String[] mFrom;   
    private ViewBinder mViewBinder;   
    private List<? extends Map<String, ?>> mData;   
    private int mResource;   
    private int mDropDownResource;   
    private LayoutInflater mInflater;  
    HashMap<Integer,View> map = new HashMap<Integer,View>(); 
    Context context = null;
    int index=0;
    public ImageSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {   
        super(context, data, resource, from, to);   
        mChecked = new ArrayList<Boolean>();
		for(int i=0;i<data.size();i++){
			mChecked.add(false);
		}
        mTo = to;   
        mFrom = from;   
        mData = data;   
        mResource = mDropDownResource = resource;   
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
        this.context= context;
    }   
    public View getView(int position, View convertView, ViewGroup parent) {   
    	View view;
		ViewHolder holder = null;
		
		if (map.get(position) == null) {
			System.out.println("contextcontext  "+context);
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = mInflater.inflate(R.layout.message_list, null);
			holder = new ViewHolder();
			holder.selected = (CheckBox)view.findViewById(R.id.listSelect);
			holder.name = (TextView)view.findViewById(R.id.listName);
			holder.price = (TextView)view.findViewById(R.id.listPrice);
			holder.count = (TextView)view.findViewById(R.id.listCount);
			holder.iv = (ImageView)view.findViewById(R.id.ml_icon);
			final int p = position;
			System.out.println("pppppppppppppppppp   "+p);
			map.put(position, view);
			holder.selected.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					CheckBox cb = (CheckBox)v;
					mChecked.set(p, cb.isChecked());
					System.out.println("####$$$$$$$$$$$%%%%^^^^^&&&&&********");
				}
			});
			view.setTag(holder);
		}else{
			view = map.get(position);
			holder = (ViewHolder)view.getTag();
		}
		
		holder.selected.setChecked(mChecked.get(position));
		holder.name.setText(mData.get(position).get("name").toString());
		holder.price.setText(mData.get(position).get("price").toString());
		holder.count.setText(mData.get(position).get("count").toString());
		holder.iv.setImageBitmap((Bitmap)mData.get(position).get("icon"));
		index++;
		System.out.println("indexindexindexindexindexindexindex   "+index);
		return view;  
    }   
    
    static class ViewHolder{
    	ImageView iv;
    	CheckBox selected;
    	TextView name;
    	TextView price;
    	TextView count;
    }

    /**  
     * 添加这个方法来处理Bitmap类型参数  
     * @param v  
     * @param bitmap  
     */  
    public void setViewImage(ImageView v, Bitmap bitmap) {   
        v.setImageBitmap(bitmap);   
    }   
}  
