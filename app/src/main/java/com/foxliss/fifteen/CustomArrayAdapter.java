package com.foxliss.fifteen;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class CustomArrayAdapter<Object> extends ArrayAdapter<Object> {

    public CustomArrayAdapter(@NonNull Context context, int resource, @NonNull Object[] objects) {
        super(context, resource, objects);
    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        super.getView(position, convertView, parent);
        View view = super.getView(position, convertView, parent);
        TextView tv = view.findViewById(android.R.id.text1);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        return view;
    }
}
