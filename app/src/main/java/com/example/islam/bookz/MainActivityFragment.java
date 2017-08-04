package com.example.islam.bookz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.islam.bookz.APIHelper.ApiModule;
import com.example.islam.bookz.APIHelper.BookApiService;
import com.example.islam.bookz.Models.ViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {


    private TextView errorTextView;
    private ApiModule apiModule;
    private BookApiService bookApiService;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private BookViewAdapter bookViewAdapter;
    private List<ViewModel> viewModels;
    private String userId;

    public MainActivityFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        getBooks();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_main, container, false);
        apiModule = new ApiModule();
        bookApiService = apiModule.provideApiService();
        errorTextView=(TextView)view.findViewById(R.id.error_tv);
        errorTextView.setText(R.string.loading);
        userId=Authenticator.getInstance().getUserID();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        recyclerView=(RecyclerView)view.findViewById(R.id.book_view);
        recyclerView.setVisibility(View.INVISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        viewModels=new LinkedList<>();

        return view;
    }

    public void getBooks(){
        RxFireBaseDB.observeSingleValueEvent(databaseReference.child(getResources().getString(R.string.user_node)).child(userId).child(getResources().getString(R.string.book_node)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataSnapshot -> {
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        ViewModel model=dataSnapshot1.getValue(ViewModel.class);
                        viewModels.add(model);
                    }
                    if(viewModels.isEmpty()){
                        errorTextView.setText(getResources().getString(R.string.no_books));
                    }else {
                        errorTextView.setVisibility(View.INVISIBLE);
                        bookViewAdapter = new BookViewAdapter(getActivity(), viewModels);
                        bookViewAdapter.setListener(position -> {
                            Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                            intent.putExtra(getResources().getString(R.string.book_name), viewModels.get(position).getTitle());
                            startActivity(intent);
                        });
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(bookViewAdapter);
                    }

                },throwable -> {

                });

    }

}
