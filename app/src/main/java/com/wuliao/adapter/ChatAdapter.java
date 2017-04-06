package com.wuliao.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuliao.R;
import com.wuliao.interfaze.OnRecyclerViewOnClickListener;
import com.wuliao.mvp.detail.url.WebActivity;
import com.wuliao.source.ChatBean;
import com.wuliao.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

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
        final ChatBean item=list.get(position);
        if (holder instanceof LeftHolder){
            if (item.getView() ==ChatBean.VIEW_TEXT){
                ((LeftHolder) holder).textView.setText(item.getText());
            }else if (item.getView()==ChatBean.VIEW_URL){
                ((LeftHolder) holder).textView.setText("[点击查看]");
                ((LeftHolder) holder).textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                ((LeftHolder) holder).textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (NetworkUtil.isInstallChrome(context)){
                            // 使用Chrome Custom Tab打开
                            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                            builder.setToolbarColor(context.getResources().getColor(R.color.colorPrimary));
                            CustomTabsIntent customTabsIntent = builder.build();
                            customTabsIntent.launchUrl(context, Uri.parse(item.getUrl()));
                            /*if (context instanceof Activity) {
                                customTabsIntent.launchUrl((Activity) context, Uri.parse(item.getUrl()));
                            } else {
                                Intent intent = customTabsIntent.intent;
                                intent.setData(Uri.parse(item.getUrl()));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    context.startActivity(intent, customTabsIntent.startAnimationBundle);
                                } else {
                                    context.startActivity(intent);
                                }
                            }*/
                            Log.i(TAG, "onClick: open in chrome");
//                            Toast.makeText(context,"open in chrome",Toast.LENGTH_SHORT).show();
                        }else {
                            //使用自定义的WebViewActivit打开
                            Intent intent=new Intent(context, WebActivity.class);
                            intent.putExtra("url",item.getUrl());
                            intent.putExtra("title",item.getText());
                            /**
                             * Content的startActivity方法，需要开启一个新的task。如果使用 Activity的startActivity方法，
                             * 不会有任何限制，因为Activity继承自Context，重载了startActivity方法。
                             */
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
//                            Toast.makeText(context,"open in webview",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }else if (item.getView()==ChatBean.VIEW_LIST){

            }
        }else if (holder instanceof RightHolder){
            ((RightHolder) holder).textView.setText(item.getText());
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
        Log.d(TAG,"list.size = " + list.size());
        notifyItemInserted(list.size());
    }

    class LeftHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView circleImageView;
        TextView textView;
        OnRecyclerViewOnClickListener listener;

        public LeftHolder(View itemView,OnRecyclerViewOnClickListener listener) {
            super(itemView);
            this.listener=listener;
            circleImageView= (CircleImageView) itemView.findViewById(R.id.chat_img_left);
            textView = (TextView) itemView.findViewById(R.id.chat_text_left);
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
        TextView textView;

        public RightHolder(View itemView,OnRecyclerViewOnClickListener listener) {
            super(itemView);
            circleImageView= (CircleImageView) itemView.findViewById(R.id.chat_img_right);
            textView = (TextView) itemView.findViewById(R.id.chat_text_right);
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
