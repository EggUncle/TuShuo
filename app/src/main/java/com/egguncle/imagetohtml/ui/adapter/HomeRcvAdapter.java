package com.egguncle.imagetohtml.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.egguncle.imagetohtml.MyApplication;
import com.egguncle.imagetohtml.R;
import com.egguncle.imagetohtml.model.HtmlImage;
import com.egguncle.imagetohtml.ui.activity.WebViewActivity;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by egguncle on 17-4-30.
 */

public class HomeRcvAdapter extends RecyclerView.Adapter<HomeRcvAdapter.HomeVideHolder> {

    private final static String TAG="HomeAdapter";

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
        Glide.with(holder.itemView.getContext()).load(listData.get(position).getImgPath()).centerCrop().into(holder.imgHome);

        final HtmlImage htmlImage = listData.get(position);
        final String htmlPath = htmlImage.getHtmlPath();
        final String title = htmlImage.getTitle();
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
                                Log.i(TAG, "onClick: "+DataSupport.deleteAll(HtmlImage.class, "htmlPath = ?", htmlPath));

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
        private ImageView imgHome;


        public HomeVideHolder(View itemView) {
            super(itemView);
            imgHome = (ImageView) itemView.findViewById(R.id.img_home);
        }


    }

}
