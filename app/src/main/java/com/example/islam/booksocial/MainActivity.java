package com.example.islam.booksocial;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.islam.booksocial.APIHelper.ApiModule;
import com.example.islam.booksocial.APIHelper.BookApiService;
import com.example.islam.booksocial.Models.GoodreadsResponse;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ApiModule apiModule;
    private BookApiService bookApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiModule=new ApiModule();
        bookApiService=apiModule.provideApiService();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fabBook = (FloatingActionButton) findViewById(R.id.fab_book);
        fabBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBookInputDialog();
            }
        });
        FloatingActionButton fabAuthor = (FloatingActionButton) findViewById(R.id.fab_author);
        fabAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAuthorInputDialog();
            }
        });
    }

    private void showAuthorInputDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.add_author)
                .content(R.string.content_author_name)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL)
                .input(R.string.author_name_hint,R.string.input_prefill, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                        rx.Observable<GoodreadsResponse> author= bookApiService.getAuthorId(input.toString());
                        author.subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(result -> {
                                    String id=getID(result.getAuthor().getLink());
                                    Log.e("movies",id);
                                    getAuthor(id);
                                },throwable -> {
                                    Log.e("errorrrrr",throwable.toString());
                                });

                    }
                }).show();
    }
    private void getAuthor(String id) {
        rx.Observable<GoodreadsResponse> author= bookApiService.getAuthor(id);
        author.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    Log.e("movies",result.getAuthor().getBooks().get(0).getTitle());
                },throwable -> {
                    Log.e("errorrrrr",throwable.toString());
                });

    }

    public String getID(String link){
        String id="";
        int x=link.indexOf("show/");
        for (int i=x+5 ; i< link.length() ; i++){
            if(link.charAt(i)!='.'){
                id+=link.charAt(i);
            }else{
                break;
            }
        }
        return id;
    }
    private void showBookInputDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.add_book)
                .content(R.string.content_book_name)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL)
                .input(R.string.book_name_hint,R.string.input_prefill, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something

                        rx.Observable<GoodreadsResponse> book= bookApiService.getBook(input.toString());
                        book.subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(result -> {
                                    Intent intent=new Intent(MainActivity.this,BookDetailActivity.class);
                                    intent.putExtra("book", (Parcelable) result.getBook());
                                    startActivity(intent);
                                    Log.e("moviesccc",result.getBook().getImageUrl());
                                },throwable -> {
                                    Log.e("errorrrrr",throwable.toString());
                                });
                    }
                }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
