package com.wuliao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuliao.R;
import com.wuliao.interfaze.OnRecyclerViewOnClickListener;
import com.wuliao.source.ChatBean;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.codeboy.android.aligntextview.CBAlignTextView;

/**
 * Created by Swy on 2017/4/1.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ChatBean> list=new ArrayList<>();
    private  LayoutInflater inflater;

    private OnRecyclerViewOnClickListener listener;

    private static final int TYPE_LEFT=0;
    private static final int TYPE_RIGHT=1;

    public ChatAdapter(Context context) {
        this.context = context;
        this.inflater=LayoutInflater.from(context);
    }

    public ChatAdapter(Context context, List<ChatBean> list) {
        this.context = context;
        this.list = list;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_LEFT:
                return new LeftHolder(inflater.inflate(R.layout.turing_chat_left,parent,false),listener);
            case TYPE_RIGHT:
                return new RightHolder(inflater.inflate(R.layout.turing_chat_right,parent,false),listener);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatBean item=list.get(position);
        if (holder instanceof LeftHolder){
            ((LeftHolder) holder).cbAlignTextView.setText(item.getText());
        }else if (holder instanceof RightHolder){
            ((RightHolder) holder).cbAlignTextView.setText(item.getText());
        }
    }

    @Override
    public int getItemCount() {
        return list==null? 0:list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    public void addMsg(ChatBean msg){
        list.add(msg);
//        Log.d(TAG,"items.size = " + list.size());
        notifyItemInserted(list.size());
    }

    class LeftHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView circleImageView;
        CBAlignTextView cbAlignTextView;
        OnRecyclerViewOnClickListener listener;

        public LeftHolder(View itemView,OnRecyclerViewOnClickListener listener) {
            super(itemView);
            this.listener=listener;
            circleImageView= (CircleImageView) itemView.findViewById(R.id.chat_img_left);
            cbAlignTextView= (CBAlignTextView) itemView.findViewById(R.id.chat_text_left);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener!=null){
                listener.onItemClick(v,getLayoutPosition());
            }
        }
    }

    class  RightHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnRecyclerViewOnClickListener listener;
        CircleImageView circleImageView;
        CBAlignTextView cbAlignTextView;

        public RightHolder(View itemView,OnRecyclerViewOnClickListener listener) {
            super(itemView);
            circleImageView= (CircleImageView) itemView.findViewById(R.id.chat_img_right);
            cbAlignTextView= (CBAlignTextView) itemView.findViewById(R.id.chat_text_right);
            this.listener=listener;
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
