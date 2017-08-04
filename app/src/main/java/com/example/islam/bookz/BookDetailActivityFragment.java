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
import android.widget.Toast;

import com.example.islam.bookz.APIHelper.ApiModule;
import com.example.islam.bookz.APIHelper.BookApiService;
import com.example.islam.bookz.Models.Book;
import com.example.islam.bookz.Models.GoodreadsResponse;
import com.example.islam.bookz.Models.ViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private DatabaseReference databaseReference;
    private String userId;

    public BookDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_book_detail, container, false);
        apiModule=new ApiModule();
        bookApiService=apiModule.provideApiService();
        initView();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        userId=Authenticator.getInstance().getUserID();

        if(getActivity().getIntent().hasExtra(getResources().getString(R.string.book_name))) {
            bookName = getActivity().getIntent().getStringExtra(getResources().getString(R.string.book_name));
            book = new Book();
            getBook();
        }

        return view;
    }

    public boolean isBookFav(){
        boolean[] bookFav = {false};
        RxFireBaseDB.observeSingleValueEvent(databaseReference.child(getResources().getString(R.string.user_node)).child(userId).child(getResources().getString(R.string.book_node)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataSnapshot -> {
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        ViewModel model=dataSnapshot1.getValue(ViewModel.class);
                        if(model.getTitle().equals(bookName))
                            bookFav[0] =true;
                    }

                },throwable -> {
                    Log.e(getResources().getString(R.string.error),getResources().getString(R.string.error_retrieving_data));
                });
        return bookFav[0];
    }

    private void getBook() {
        rx.Observable<GoodreadsResponse> bookResponse= bookApiService.getBook(bookName);
        bookResponse.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    bindData(result.getBook());
                },throwable -> {
                    Log.e(getResources().getString(R.string.error),getResources().getString(R.string.error_retrieving_data));
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
        bookDate.setText(book.getPublicationYear());
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
        ViewModel model=new ViewModel(book.getImageUrl(),bookName);
        favouriteBtn.setVisibility(View.VISIBLE);
        if(isBookFav())
            favouriteBtn.setImageResource(R.drawable.ic_favorite_full);

        favouriteBtn.setOnClickListener(v -> {
            RxFireBaseDB.setValue(databaseReference.child(getResources().getString(R.string.user_node)).child(userId).child(getResources().getString(R.string.book_node)).child(bookName),model).
                    subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aVoid -> {
                        Toast.makeText(getActivity(),getResources().getString(R.string.book_added),Toast.LENGTH_SHORT).show();
                        favouriteBtn.setImageResource(R.drawable.ic_favorite_full);
                    },throwable -> {
                        Toast.makeText(getActivity(),getResources().getString(R.string.book_not_added),Toast.LENGTH_SHORT).show();
                    });
        });
    }

    private void initView() {
        favouriteBtn= (FloatingActionButton) view.findViewById(R.id.favorite_fab);
        favouriteBtn.setSelected(bookFav);
        favouriteBtn.setVisibility(View.INVISIBLE);
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
