package com.example.islam.bookz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.islam.bookz.APIHelper.ApiModule;
import com.example.islam.bookz.APIHelper.BookApiService;
import com.example.islam.bookz.Models.Book;
import com.example.islam.bookz.Models.GoodreadsResponse;
import com.squareup.picasso.Picasso;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A placeholder fragment containing a simple view.
 */
public class BookDetailActivityFragment extends Fragment {

    private FloatingActionButton favouriteBtn;
    private ImageView bookImage;
    private ImageView authorImage;
    private TextView bookTitle;
    private TextView bookDate;
    private TextView bookVoteAverage;
    private TextView bookDescription;
    private TextView bookAuthor;
    private TextView bookPublisher;
    private Book bookModel;
    private Button openLink;
    private View view;
    private boolean bookFav;
    private String bookName;
    private Book book;
    private ApiModule apiModule;
    private BookApiService bookApiService;

    public BookDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_book_detail, container, false);
        apiModule=new ApiModule();
        bookApiService=apiModule.provideApiService();
        initView();
        if(getActivity().getIntent().hasExtra("bookName")) {
            bookName = getActivity().getIntent().getStringExtra("bookName");
            book = new Book();
            getBook();
        }

        return view;
    }

    private void getBook() {
        rx.Observable<GoodreadsResponse> bookResponse= bookApiService.getBook(bookName);
        bookResponse.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    bindData(result.getBook());
                    Log.e("moviesccc",result.getBook().getImageUrl());
                },throwable -> {
                    Log.e("errorrrrr",throwable.toString());
                });
    }

    private void bindData(Book book) {
        this.book=book;
        Picasso.with(getActivity()).load(book.getImageUrl()).into(bookImage);
        bookImage.setVisibility(View.VISIBLE);
        Picasso.with(getActivity()).load(book.getSmallImageUrl()).into(authorImage);
        authorImage.setVisibility(View.VISIBLE);
        bookTitle.setText(book.getTitle());
        bookTitle.setVisibility(View.VISIBLE);
        bookDate.setText(book.getDate());
        bookDate.setVisibility(View.VISIBLE);
        bookAuthor.setText(book.getAuthors().get(0).getName());
        bookAuthor.setVisibility(View.VISIBLE);
        bookDescription.setText(book.getDescription());
        bookDescription.setVisibility(View.VISIBLE);
        bookVoteAverage.setText(book.getAverageRating());
        bookVoteAverage.setVisibility(View.VISIBLE);
        bookPublisher.setText(book.getPublisher());
        bookPublisher.setVisibility(View.VISIBLE);
        openLink.setVisibility(View.VISIBLE);
        openLink.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(book.getLink()));
            startActivity(i);
        });
    }

    private void initView() {
        favouriteBtn= (FloatingActionButton) view.findViewById(R.id.favorite_fab);
        favouriteBtn.setSelected(bookFav);
        bookImage= (ImageView) view.findViewById(R.id.book_cover);
        bookImage.setVisibility(View.INVISIBLE);
        authorImage = (ImageView) view.findViewById(R.id.book_image);
        authorImage.setVisibility(View.INVISIBLE);
        bookTitle= (TextView) view.findViewById(R.id.book_title);
        bookTitle.setVisibility(View.INVISIBLE);
        bookDate= (TextView) view.findViewById(R.id.book_date);
        bookDate.setVisibility(View.INVISIBLE);
        bookVoteAverage= (TextView) view.findViewById(R.id.book_vote_average);
        bookVoteAverage.setVisibility(View.INVISIBLE);
        bookDescription= (TextView) view.findViewById(R.id.book_description);
        bookDescription.setVisibility(View.INVISIBLE);
        openLink= (Button) view.findViewById(R.id.btnLink);
        openLink.setVisibility(View.INVISIBLE);
        bookAuthor=(TextView)view.findViewById(R.id.book_author);
        bookAuthor.setVisibility(View.INVISIBLE);
        bookPublisher=(TextView)view.findViewById(R.id.book_publisher);
        bookPublisher.setVisibility(View.INVISIBLE);
    }
}
