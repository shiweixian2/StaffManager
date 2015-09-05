package com.bestcode95.staffmanage.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mima123 on 15/8/7.
 */
public class ListBtAdapter extends BaseAdapter
{

    /**
     * 内部类
     */
    private class ButtonViewHolder
    {
        ImageView clockIcon;
        TextView timeText;
        TextView mealText;
        ImageButton editBt;
    }

    private ArrayList<HashMap<String, Object>> mAppList;
    private LayoutInflater mInflater;
    private Context mContext;
    private String[] keyString;
    private int[] valueViewId;
    private ButtonViewHolder holder;
    private int mResource;

    /**
     * 构造方法
     *
     * @param context
     * @param appList
     * @param resource
     * @param from
     * @param to
     */
    public ListBtAdapter(Context context, ArrayList<HashMap<String, Object>> appList, int resource, String[] from, int[] to)
    {
        mContext = context;
        mAppList = appList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResource = resource;
        keyString = new String[from.length];
        valueViewId = new int[to.length];
        System.arraycopy(from, 0, keyString, 0, from.length);
        System.arraycopy(to, 0, valueViewId, 0, to.length);
    }

    @Override
    public int getCount()
    {
        return mAppList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mAppList.get(position);
    }

    public void removeItem(int position)
    {
        mAppList.remove(position);
    }

    public void removeAllItems()
    {
        for (int i = 0; i < mAppList.size(); i++)
        {
            mAppList.remove(0);
        }
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView != null)
        {
            holder = (ButtonViewHolder) convertView.getTag();
        } else
        {
            //初始化界面组件
            convertView = mInflater.inflate(mResource, null);
            holder = new ButtonViewHolder();
            holder.clockIcon = (ImageView) convertView.findViewById(valueViewId[0]);
            holder.timeText = (TextView) convertView.findViewById(valueViewId[1]);
            holder.mealText = (TextView) convertView.findViewById(valueViewId[2]);
            holder.editBt = (ImageButton) convertView.findViewById(valueViewId[3]);
            convertView.setTag(holder);
        }
        HashMap<String, Object> appInfo = mAppList.get(position);
        if (appInfo != null)
        {
            int clockId = (Integer) appInfo.get(keyString[0]);
            String time = (String) appInfo.get(keyString[1]);
            String meal = (String) appInfo.get(keyString[2]);
            int editId = (Integer) appInfo.get(keyString[3]);
            holder.timeText.setText(time);
            holder.mealText.setText(meal);
            holder.clockIcon.setImageDrawable(holder.clockIcon.getResources().getDrawable(clockId));
            holder.editBt.setImageDrawable(holder.editBt.getResources().getDrawable(editId));
            holder.editBt.setOnClickListener(new ListBtListener(position));
        }
        return convertView;
    }

    class ListBtListener implements View.OnClickListener
    {

        private int position;

        ListBtListener(int position)
        {
            this.position = position;
        }

        @Override
        public void onClick(View v)
        {
            int vid = v.getId();
            if (vid == holder.editBt.getId())
            {
                /*
                *书写按钮的监听事件
                 */
                //传递跳转的信息
                Log.e("listView", "跳转成功");
            }
        }
    }

    @Override
    public void notifyDataSetChanged()
    {
        super.notifyDataSetChanged();
    }
}
