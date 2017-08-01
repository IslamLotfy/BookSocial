package com.example.islam.bookz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by islam on 31/07/17.
 */

public class BookViewHolder extends RecyclerView.ViewHolder {
    private View itemView;
    private Context context;
    public BookViewHolder(View itemView, Context context) {
        super(itemView);
        this.itemView=itemView;
        this.context=context;
    }
    public void bindData(int position, String url, String title, BookClickedListener listener){
        ImageView imageView=(ImageView)itemView.findViewById(R.id.item_image_view);
        TextView textView=(TextView)itemView.findViewById(R.id.item_view_title);
        Picasso.with(context).load(url).into(imageView);
        textView.setText(title);
        itemView.setOnClickListener(v -> {
            listener.OnBookClicked(position);
        });

    }
}
