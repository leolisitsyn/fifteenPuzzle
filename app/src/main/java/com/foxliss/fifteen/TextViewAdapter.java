package com.foxliss.fifteen;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;


public class TextViewAdapter extends BaseAdapter {
    private final Context mContext;
    final int[] field;
    final int size;
    final int screenHeight;
    final int screenWidth;


    // Constructor
    public TextViewAdapter(Context c, Field field, int screenHeight, int screenWidth){
        mContext = c;
        this.field = field.getField();
        size = field.getSize();
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
    }

    @Override
    public int getCount() {
        return size * size;
    }

    @Override
    public Object getItem(int position) {
        return field[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView block = new TextView(mContext);
        block.setBackgroundResource(R.drawable.wooden_block);

        if (field[position] != 0) block.setText(String.valueOf(field[position]));
        else block.setText(" ");
        block.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        block.setGravity(Gravity.CENTER);
        switch (size){
            case 2: block.setTextSize(75); break;
            case 3: block.setTextSize(50); break;
            case 4: block.setTextSize(50); break;
            case 5: block.setTextSize(40); break;
            case 6: block.setTextSize(40); break;
        }

        block.setLayoutParams(new GridView.LayoutParams((screenWidth - 20) /size,
                (screenWidth - 20) /size));
        return block;
    }
}