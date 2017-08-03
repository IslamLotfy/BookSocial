package com.example.islam.bookz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.islam.bookz.APIHelper.ApiModule;
import com.example.islam.bookz.APIHelper.BookApiService;
import com.example.islam.bookz.APIHelper.Connector;
import com.example.islam.bookz.Models.Author;
import com.example.islam.bookz.Models.Book;
import com.example.islam.bookz.Models.GoodreadsResponse;
import com.example.islam.bookz.Models.ViewModel;

import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A placeholder fragment containing a simple view.
 */
public class AuthorActivityFragment extends Fragment {

    private TextView authorName;
    private Author author;
    private String authorId;
    private Connector connector;
    private ApiModule apiModule;
    private BookApiService bookApiService;
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
        authorName.setVisibility(View.INVISIBLE);
        apiModule=new ApiModule();
        bookApiService=apiModule.provideApiService();

        openButton=(Button)view.findViewById(R.id.btnLink_author);
        openButton.setVisibility(View.INVISIBLE);
        recyclerView=(RecyclerView)view.findViewById(R.id.books_author);
        recyclerView.setVisibility(View.INVISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        authorId=getActivity().getIntent().getStringExtra("authorId");

        Observable<GoodreadsResponse> responseObservable=bookApiService.getAuthor(authorId);
        responseObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(goodreadsResponse -> {
                    bindData(goodreadsResponse.getAuthor());
                },throwable -> {
                    Log.e("error","error in retrieving data");
                });
//        connector=new Connector();
//        connector.setListener(3,response -> {
//            response.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(goodreadsResponse -> {
//                        bindData(goodreadsResponse.getAuthor());
//                    },throwable -> {
//
//                    });
//        });
//        connector.execute(authorId);


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
        authorName.setVisibility(View.VISIBLE);
        openButton.setVisibility(View.VISIBLE);
        bookViewAdapter=new BookViewAdapter(getActivity(),getViewModels());
        bookViewAdapter.setListener(position -> {
            Book book=this.author.getBooks().get(position);
            Intent intent=new Intent(getActivity(),BookDetailActivity.class);
            intent.putExtra("bookName", book.getTitle());
            startActivity(intent);
        });
        recyclerView.setAdapter(bookViewAdapter);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
