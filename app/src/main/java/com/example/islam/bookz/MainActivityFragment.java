package com.example.islam.bookz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.islam.bookz.APIHelper.ApiModule;
import com.example.islam.bookz.APIHelper.BookApiService;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {


    private TextView errorTextView;
    private ApiModule apiModule;
    private BookApiService bookApiService;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_main, container, false);
        apiModule = new ApiModule();
        bookApiService = apiModule.provideApiService();
        errorTextView=(TextView)view.findViewById(R.id.error_tv);
        errorTextView.setText(R.string.no_books);

        return view;
    }

}
