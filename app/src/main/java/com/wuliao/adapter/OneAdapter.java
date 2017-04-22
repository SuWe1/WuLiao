package com.wuliao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wuliao.R;
import com.wuliao.interfaze.OnRecyclerViewOnClickListener;
import com.wuliao.source.one.OneBean;

import java.util.ArrayList;

/**
 * Created by Swy on 2017/4/16.
 */

public class OneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<OneBean> list;
    private Context context;
    private LayoutInflater inflater;

    private OnRecyclerViewOnClickListener listener;

    public OneAdapter(ArrayList<OneBean> list, Context context) {
        this.list = list;
        this.context = context;
        this.inflater=LayoutInflater.from(context);
    }
    public void setItemOnClickListener(OnRecyclerViewOnClickListener listener){
        this.listener=listener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalViewHolder(inflater.inflate(R.layout.one_list_item,parent,false),listener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalViewHolder){
            OneBean item=list.get(position);
            Glide.with(context)
                    .load(item.getImg_url())
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .into(((NormalViewHolder)holder).one_list_Img);
            ((NormalViewHolder) holder).one_list_title.setText(item.getTitle());
            ((NormalViewHolder) holder).one_list_author.setText("文 / "+item.getAuthor().getUser_name());
            ((NormalViewHolder) holder).one_list_text.setText(item.getForward());
//            String time=item.getPost_date().substring(0,10);
            ((NormalViewHolder) holder).one_list_time.setText(item.getPost_date());
            /** setText(item.getLike_count()) ==>  setText(String.valueOf(item.getLike_count()))
             * There are different versions of setText - one takes a String and one takes an int resource id.
             * If you pass it an integer it will try to look for the corresponding string resource id - which it can't find, which is your error.
             */
            ((NormalViewHolder) holder).one_list_likecount.setText(String.valueOf(item.getLike_count()));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

         OnRecyclerViewOnClickListener listener;
         TextView one_list_title,one_list_author,one_list_text,one_list_time,one_list_likecount;
         ImageView one_list_Img,one_list_dot,one_list_share;
        public NormalViewHolder(View itemView, OnRecyclerViewOnClickListener listener) {
            super(itemView);
            this.listener = listener;
            one_list_title= (TextView) itemView.findViewById(R.id.one_list_title);
            one_list_author= (TextView) itemView.findViewById(R.id.one_list_author);
            one_list_text= (TextView) itemView.findViewById(R.id.one_list_text);
            one_list_time= (TextView) itemView.findViewById(R.id.one_list_time);
            one_list_likecount= (TextView) itemView.findViewById(R.id.one_list_likecount);
            one_list_Img= (ImageView) itemView.findViewById(R.id.one_list_Img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener!=null){
                listener.onItemClick(v,getLayoutPosition());
            }
        }
    }
}
