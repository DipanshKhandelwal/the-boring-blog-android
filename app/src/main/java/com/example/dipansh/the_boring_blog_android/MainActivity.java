package com.example.dipansh.the_boring_blog_android;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getName();
    private static final String REQUEST_URL =
            "http://mukulkyadav.pythonanywhere.com/blog/api/";

    private ListView listView;
    private PostAdapter adapter;
    private ArrayList<Post> posts;
    private MediaPlayer media;
    private ProgressBar progessBar;
    private Handler handler;
    private Runnable position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        posts = new ArrayList<>();
        listView =  findViewById(R.id.listView);
        progessBar = findViewById(R.id.progressBar);

        GetPosts m = new GetPosts();
        m.execute(REQUEST_URL);

        handler = new Handler();
        position = new Runnable() {
            @Override
            public void run() {
                String showText ="";
                handler.postDelayed(this, 4000);

                int first = listView.getFirstVisiblePosition();
                int last = listView.getLastVisiblePosition();

                if(last - first == 1){
                    if(first == 0){
                        showText = "0";
                    }else if(last == listView.getCount()-1){
                        showText = String.valueOf(last);
                    }else{
                        showText = (String.valueOf((first + last)/2));
                    }
                }else{
                    showText = (String.valueOf((first + last)/2));
                }
                Toast.makeText(MainActivity.this, showText, Toast.LENGTH_SHORT).show();
            }
        };
        MainActivity.this.runOnUiThread(position);
    }

    public void update(ArrayList<Post> list){

        adapter = new PostAdapter(MainActivity.this ,R.layout.post_item,list);
        listView.setAdapter(adapter);
        progessBar.setVisibility(View.INVISIBLE);
    }

    private class GetPosts extends AsyncTask<String , Void , String> {

        @Override
        protected String doInBackground(String... params) {

            if (params.length < 1 || params[0] == null) {
                return null;
            }

            URL url = QueryUtils.createUrl(params[0]);
            String jsonResponse = "";
            try {
                jsonResponse = QueryUtils.makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
            }

            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s == null) {
                return;
            }

            posts = QueryUtils.extractPosts(s);
            update(posts);
        }
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

    @Override
    protected void onResume() {
        handler.postDelayed(position, 4000);
        super.onResume();
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(position);
        super.onPause();
    }

}
