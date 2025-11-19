package com.xaiu.team2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {
    private List<NoteBook> list;
    private Context mContext;
    private ItemClickListener itemClickListener;
    public NoteAdapter(Context context, ItemClickListener itemClickListener) {
        this.mContext = context;
        this.itemClickListener = itemClickListener;
    }
    public void setData(List<NoteBook> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.list_item,
                parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        View itemView = holder.itemView;
        NoteBook bean=list.get(position);
        if (bean==null)return;
        holder.tv_title.setText(bean.getTitle());
        holder.tv_content.setText(bean.getContent());
        holder.tv_time.setText(bean.getTime());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(itemView, position);
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemClickListener.onItemLongClick(itemView,position);
                return true;
            }
        });
    }
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_content, tv_time;
        public MyViewHolder(View view) {
            super(view);
            tv_title = view.findViewById(R.id.tv_title);
            tv_content = view.findViewById(R.id.tv_content);
            tv_time = view.findViewById(R.id.tv_time);
        }
    }
    public interface ItemClickListener {
        void onItemClick(View v, int position);
        void onItemLongClick(View v, int position);
    }
}

