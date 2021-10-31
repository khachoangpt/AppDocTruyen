package com.example.doctruyenapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doctruyenapp.R;
import com.example.doctruyenapp.model.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterCategory extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Category> categoryList;

    public AdapterCategory(Context context, int layout, List<Category> categoryList) {
        this.context = context;
        this.layout = layout;
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(layout, null);

        ImageView img = convertView.findViewById(R.id.imgchuyenmuc);

        TextView txtCategoryName = convertView.findViewById(R.id.tvchuyenmuc);

        Category cm = (Category) getItem(i);

        txtCategoryName.setText(cm.categoryName);

        Picasso.get().load(cm.image).placeholder(R.drawable.ic_load).error(R.drawable.ic_baseline_image_24).into(img);

        return convertView;
    }
}
