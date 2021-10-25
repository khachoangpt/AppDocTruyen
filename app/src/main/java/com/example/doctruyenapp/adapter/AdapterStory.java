package com.example.doctruyenapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doctruyenapp.R;
import com.example.doctruyenapp.model.Story;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterStory extends BaseAdapter {
    private Context context;
    private ArrayList<Story> listStory;

    public AdapterStory(Context context, ArrayList<Story> listStory) {
        this.context = context;
        this.listStory = listStory;
    }

    @Override
    public int getCount() {
        return listStory.size();
    }

    @Override
    public Object getItem(int i) {
        return listStory.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listStory.get(i).getId();
    }

    //filter
    public void filterList(ArrayList<Story> filterList) {
        listStory = filterList;
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

        Story story = (Story) getItem(i);
        viewHolder.txtStoryName.setText(story.getTitle());

        Picasso.get().load(story.getImage()).placeholder(R.drawable.ic_load).error(R.drawable.ic_baseline_image_24).into(viewHolder.imgStory);

        return convertView;
    }
}
