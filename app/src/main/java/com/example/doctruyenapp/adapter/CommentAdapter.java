package com.example.doctruyenapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctruyenapp.R;
import com.example.doctruyenapp.model.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    private List<Comment> comments;
    private Context context;

    public CommentAdapter(List<Comment> Comments, Context context) {
        this.comments = Comments;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_comment, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvcmtUsername.setText(comments.get(position).getName());
        holder.tvTime.setText(comments.get(position).getCreateAt());
        holder.tvcmtContent.setText(comments.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return comments == null ? 0 : comments.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvcmtUsername;
        TextView tvTime;
        TextView tvcmtContent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvcmtUsername = itemView.findViewById(R.id.tvcmtUsername);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvcmtContent = itemView.findViewById(R.id.tvcmtContent);
        }
    }
}
