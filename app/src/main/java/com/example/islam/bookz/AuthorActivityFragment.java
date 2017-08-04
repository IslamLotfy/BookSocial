package com.example.islam.bookz;

import android.app.ProgressDialog;
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
    private ApiModule apiModule;
    private BookApiService bookApiService;
    private Button openButton;
    private RecyclerView recyclerView;
    private BookViewAdapter bookViewAdapter;
    private ProgressDialog progressDialog;
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
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle(getResources().getString(R.string.loading));
        progressDialog.setMessage(getResources().getString(R.string.wait_message));
        progressDialog.show();
        openButton=(Button)view.findViewById(R.id.btnLink_author);
        openButton.setVisibility(View.INVISIBLE);
        recyclerView=(RecyclerView)view.findViewById(R.id.books_author);
        recyclerView.setVisibility(View.INVISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        authorId=getActivity().getIntent().getStringExtra(getResources().getString(R.string.author_id));

        Observable<GoodreadsResponse> responseObservable=bookApiService.getAuthor(authorId);
        responseObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(goodreadsResponse -> {
                    bindData(goodreadsResponse.getAuthor());
                },throwable -> {
                    Log.e(getResources().getString(R.string.error),getResources().getString(R.string.error_retrieving_data));
                });


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
        progressDialog.dismiss();
        this.author=author;
        authorName.setText(author.getName());
        authorName.setVisibility(View.VISIBLE);
        openButton.setVisibility(View.VISIBLE);
        bookViewAdapter=new BookViewAdapter(getActivity(),getViewModels());
        bookViewAdapter.setListener(position -> {
            Book book=this.author.getBooks().get(position);
            Intent intent=new Intent(getActivity(),BookDetailActivity.class);
            intent.putExtra(getResources().getString(R.string.book_name), book.getTitle());
            startActivity(intent);
        });
        recyclerView.setAdapter(bookViewAdapter);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
