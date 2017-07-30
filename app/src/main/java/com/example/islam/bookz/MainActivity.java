package com.example.islam.bookz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.islam.bookz.APIHelper.ApiModule;
import com.example.islam.bookz.APIHelper.BookApiService;
import com.example.islam.bookz.APIHelper.Connector;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    private ApiModule apiModule;
    private BookApiService bookApiService;
    private Connector connector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connector=new Connector();
        connector.setListener(1,response -> {
            response.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(goodreadsResponse -> {
                Log.e("Async task",goodreadsResponse.getBook().getImageUrl());
            },throwable -> {
                        Log.e("errorrrrr",throwable.toString());
                    });
        });

        connector.execute("Hound of the baskervilles");

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
                        connector=new Connector();
                       connector.setListener(2,response -> {
                           response.subscribeOn(Schedulers.newThread())
                                   .observeOn(AndroidSchedulers.mainThread())
                                   .subscribe(result -> {
                                       String id=getID(result.getAuthor().getLink());
                                       Intent intent=new Intent(MainActivity.this,AuthorActivity.class);
                                       intent.putExtra("authorId",id);
                                       startActivity(intent);
                                       getAuthor(id);
                                   },throwable -> {
                                       Log.e("errorrrrr",throwable.toString());
                                   });
                       });
                        connector.execute(input.toString());

                    }
                }).show();
    }
    private void getAuthor(String id) {
        connector=new Connector();
        connector.setListener(3,response -> {
            response.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        Log.e("movies",result.getAuthor().getBooks().get(0).getTitle());
                    },throwable -> {
                        Log.e("errorrrrr",throwable.toString());
                    });
        });
       connector.execute(id);

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
                        Intent intent=new Intent(MainActivity.this,BookDetailActivity.class);
                        intent.putExtra("book",input.toString());
                        startActivity(intent);

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
        }else if(id==R.id.search){
            showAuthorInputDialog();
        }

        return super.onOptionsItemSelected(item);
    }
}
