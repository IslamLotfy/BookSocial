package com.example.islam.bookz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.islam.bookz.APIHelper.Connector;
import com.example.islam.bookz.Models.Author;
import com.example.islam.bookz.Models.Book;
import com.example.islam.bookz.Models.ViewModel;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A placeholder fragment containing a simple view.
 */
public class AuthorActivityFragment extends Fragment {

    private TextView authorName;
    private TextView averageRating;
    private TextView ratingCount;
    private ImageView authorImage;
    private ImageView authorSmallImage;
    private Author author;
    private String authorId;
    private Connector connector;
    private Button openButton;
    private RecyclerView recyclerView;
    private BookViewAdapter bookViewAdapter;

    public AuthorActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_author, container, false);
        authorName=(TextView)view.findViewById(R.id.author_name);
        averageRating=(TextView)view.findViewById(R.id.author_rating);
        ratingCount=(TextView)view.findViewById(R.id.author_rating_count);
        authorImage=(ImageView)view.findViewById(R.id.author_image_large);
        authorSmallImage=(ImageView)view.findViewById(R.id.author_image_small);
        openButton=(Button)view.findViewById(R.id.btnLink_author);
        recyclerView=(RecyclerView)view.findViewById(R.id.books_author);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        authorId=getActivity().getIntent().getStringExtra("authorId");
        connector=new Connector();
        connector.setListener(3,response -> {
            response.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(goodreadsResponse -> {
                        bindData(goodreadsResponse.getAuthor());
                    },throwable -> {

                    });
        });
        connector.execute(authorId);


        openButton.setOnClickListener(v -> {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(author.getLink()));
                startActivity(i);
        });
        return view;
    }

    private List<ViewModel> getViewModels() {
        List<ViewModel> models=new LinkedList<>();
        for(Book book:author.getBooks()){
            ViewModel viewModel=new ViewModel(book.getImageUrl(),book.getTitle());
            models.add(viewModel);
        }
        return models;
    }

    private void bindData(Author author) {
        this.author=author;
        authorName.setText(author.getName());
        averageRating.setText(author.getAverageRating());
        ratingCount.setText(author.getRatingsCount());
        Picasso.with(getActivity()).load(author.getImageUrl()).into(authorImage);
        Picasso.with(getActivity()).load(author.getSmallImageUrl()).into(authorSmallImage);
        bookViewAdapter=new BookViewAdapter(getActivity(),getViewModels());
        recyclerView.setAdapter(bookViewAdapter);

    }
}
