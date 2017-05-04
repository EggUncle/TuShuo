package com.egguncle.imagetohtml.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.egguncle.imagetohtml.MyApplication;
import com.egguncle.imagetohtml.R;
import com.egguncle.imagetohtml.model.HtmlImage;
import com.egguncle.imagetohtml.ui.activity.WebViewActivity;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.List;

/**
 * Created by egguncle on 17-4-30.
 */

public class HomeRcvAdapter extends RecyclerView.Adapter<HomeRcvAdapter.HomeVideHolder> {

    private final static String TAG = "HomeAdapter";

    private List<HtmlImage> listData;

    public HomeRcvAdapter(List<HtmlImage> data) {
        listData = data;
    }

    @Override
    public HomeVideHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HomeVideHolder holderView = new HomeVideHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false));
        return holderView;
    }

    @Override
    public void onBindViewHolder(final HomeVideHolder holder, final int position) {
        Glide.with(holder.itemView.getContext()).load(listData.get(position).getImgPath()).centerCrop().into(holder.imgHomeItem);

        final HtmlImage htmlImage = listData.get(position);
        final String htmlPath = htmlImage.getHtmlPath();
        final String title = htmlImage.getTitle();

        File file = new File(htmlPath);
        //当列表项生成的时候，对应的htmlpath里的图片可能还没有生成
        //所以先判断文件是否存在，若不存在，显示进度条，并不设值其点击事件
        if (!file.exists()) {
            holder.prgItem.setVisibility(View.VISIBLE);
            holder.itemView.setClickable(false);
        }else{
            holder.prgItem.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = holder.itemView.getContext();
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("url", htmlPath);
                    intent.putExtra("title", title);
                    context.startActivity(intent);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(holder.itemView.getContext())
                            .setTitle(holder.itemView.getContext().getResources().getString(R.string.delete))
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.i(TAG, "onClick: " + DataSupport.deleteAll(HtmlImage.class, "htmlPath = ?", htmlPath));

                                    removeItem(htmlImage);
                                }
                            })
                            .setNegativeButton(R.string.no, null)
                            .create()
                            .show();

                    return true;
                }
            });
        }
        file=null;

        holder.ivHomeItem.setText(title);

    }

    /**
     * 添加列表项
     *
     * @param htmlImage
     */
    public void insertItem(HtmlImage htmlImage) {
        listData.add(htmlImage);
        this.notifyItemChanged(listData.size());
    }

    /**
     * 当文件已经生成完毕的时候，刷新列表项
     */
    public void refreshLastItem(){
        this.notifyItemChanged(listData.size()-1);
    }

    /**
     * 删除列表项
     *
     * @param htmlImage
     */
    public void removeItem(HtmlImage htmlImage) {
        int postion = listData.indexOf(htmlImage);
        if (listData.remove(htmlImage)) {
            this.notifyItemRemoved(postion);
        }
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }

    public class HomeVideHolder extends RecyclerView.ViewHolder {
        private ImageView imgHomeItem;
        private TextView ivHomeItem;
        private ContentLoadingProgressBar prgItem;


        public HomeVideHolder(View itemView) {
            super(itemView);
            imgHomeItem = (ImageView) itemView.findViewById(R.id.img_home_item);
            ivHomeItem = (TextView) itemView.findViewById(R.id.iv_home_item);
            prgItem = (ContentLoadingProgressBar) itemView.findViewById(R.id.prg_item);

        }


    }

}
