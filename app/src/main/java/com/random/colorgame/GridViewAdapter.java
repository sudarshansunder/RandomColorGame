package com.random.colorgame;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    int color1,color2,numItems,colorPos;

    public GridViewAdapter(Context context,int color1,int color2,int numItems,int colorPos) {
        this.context = context;
        this.color1 = color1;
        this.color2 = color2;
        this.numItems = numItems;
        this.colorPos = colorPos;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View rowView;
        LayoutInflater inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.grid_item, null);
        GridViewItem imageView = (GridViewItem) rowView.findViewById(R.id.gridViewItem);
        if(position == colorPos)
            imageView.setBackgroundColor(color2);
        else
            imageView.setBackgroundColor(color1);
        return rowView;
    }
    @Override
    public int getCount() {
        return numItems;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}