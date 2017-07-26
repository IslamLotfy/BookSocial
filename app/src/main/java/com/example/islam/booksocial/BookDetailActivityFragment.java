package com.example.islam.booksocial;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.islam.booksocial.APIHelper.BookApiService;
import com.example.islam.booksocial.Models.Book;

/**
 * A placeholder fragment containing a simple view.
 */
public class BookDetailActivityFragment extends Fragment {

    private FloatingActionButton favouriteBtn;
    private BookApiService bookApiService;
    private ImageView bookImage;
    private ImageView posterImage;
    private TextView bookTitle;
    private TextView bookDate;
    private TextView bookVoteAverage;
    private TextView bookDescription;
    private Book bookModel;
    private Button openLink;
    private View view;
    private boolean bookFav;

    public BookDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_book_detail, container, false);
        initView();
        return view;
    }

    private void initView() {
        favouriteBtn= (FloatingActionButton) view.findViewById(R.id.favorite_fab);
        favouriteBtn.setSelected(bookFav);
        bookImage= (ImageView) view.findViewById(R.id.book_cover);
        posterImage= (ImageView) view.findViewById(R.id.book_image);
        bookTitle= (TextView) view.findViewById(R.id.book_title);
        bookDate= (TextView) view.findViewById(R.id.book_date);
        bookVoteAverage= (TextView) view.findViewById(R.id.book_vote_average);
        bookDescription= (TextView) view.findViewById(R.id.book_description);
        openLink= (Button) view.findViewById(R.id.btnLink);
    }
}
