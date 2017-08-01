package com.example.islam.bookz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.islam.bookz.Models.ViewModel;

import java.util.List;

/**
 * Created by islam on 31/07/17.
 */

public class BookViewAdapter extends RecyclerView.Adapter<BookViewHolder> {

    private Context context;
    private List<ViewModel> models;
    private BookClickedListener listener;

    public BookViewAdapter(Context context , List<ViewModel> models){
        this.context=context;
        this.models=models;
    }
    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item_view, parent, false);
        return new BookViewHolder(itemView,context);

    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position ) {
        holder.bindData(position,models.get(position).getUrl(),models.get(position).getTitle(),listener);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setListener(BookClickedListener listener) {
        this.listener = listener;
    }
}
