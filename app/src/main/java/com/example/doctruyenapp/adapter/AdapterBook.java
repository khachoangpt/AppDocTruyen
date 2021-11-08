package com.example.doctruyenapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doctruyenapp.R;
import com.example.doctruyenapp.model.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterBook extends BaseAdapter {
    private Context context;
    private ArrayList<Book> listBook;

    public AdapterBook(Context context, ArrayList<Book> listBook) {
        this.context = context;
        this.listBook = listBook;
    }

    @Override
    public int getCount() {
        return listBook.size();
    }

    @Override
    public Object getItem(int i) {
        return listBook.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listBook.get(i).id;
    }

    //filter
    public void filterList(ArrayList<Book> filterList) {
        this.listBook = filterList;
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView txtStoryName;
        ImageView imgStory;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        viewHolder = new ViewHolder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.new_story, null);
        viewHolder.txtStoryName = convertView.findViewById(R.id.tvTenTruyenNew);
        viewHolder.imgStory = convertView.findViewById(R.id.imgNewTruyen);
        convertView.setTag(viewHolder);
        Book book = (Book) getItem(i);
        viewHolder.txtStoryName.setText(book.title);
        Picasso.get().load(book.getImage()).placeholder(R.drawable.ic_load).error(R.drawable.ic_baseline_image_24).into(viewHolder.imgStory);
        return convertView;
    }
}
