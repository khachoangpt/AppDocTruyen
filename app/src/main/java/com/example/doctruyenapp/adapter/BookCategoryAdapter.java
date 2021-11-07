package com.example.doctruyenapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctruyenapp.R;
import com.example.doctruyenapp.model.BookCategory;
import com.example.doctruyenapp.model.ItemClickListener;
import com.example.doctruyenapp.BookCategoryActivity;
import com.squareup.picasso.Picasso;

import java.util.List;


public class BookCategoryAdapter extends RecyclerView.Adapter<BookCategoryAdapter.MyViewHolder> {
    private List<BookCategory> bookCategories;
    private Context context;

    public BookCategoryAdapter(Context context) {
        this.context = context;
    }

    public BookCategoryAdapter(List<BookCategory> bookCategories, Context context) {
        this.bookCategories = bookCategories;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = LayoutInflater.from(context).inflate(R.layout.row_book_category, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(bookCategories.get(position).getImage())
                .placeholder(R.drawable.ic_load).error(R.drawable.ic_baseline_image_24).into(holder.imv);
        holder.tv_title.setText(bookCategories.get(position).getName());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(context, BookCategoryActivity.class);
                intent.putExtra("categoryId", bookCategories.get(position).getId());
                intent.putExtra("categoryName", bookCategories.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookCategories == null ? 0 : bookCategories.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView imv;
        TextView tv_title;

        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imv = itemView.findViewById(R.id.imv_chap);
            tv_title = itemView.findViewById(R.id.tv_title);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), true);
            return true;
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

    }
}
