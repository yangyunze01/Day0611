package com.example.zhoukao01;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 杨运泽 on 2018/6/11.
 */

public class MyAdapter extends BaseAdapter {
    private List<AdBean.DataBean> list;
    private Context context;
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=View.inflate(context,R.layout.activity_main,null);
            holder=new ViewHolder();
            holder.tv=convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        }else{
         holder= (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(list.get(position).getName());
        return convertView;
    }
    class ViewHolder{
        TextView tv;
    }
}
