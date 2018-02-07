package com.example.dipansh.the_boring_blog_android;

/**
 * Created by DIPANSH KHANDELWAL on 09-05-2017.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.example.dipansh.the_boring_blog_android.MainActivity.LOG_TAG;

/**
 * Helper methods related to requesting and receiving posts data from blog.
 */
public final class QueryUtils {

    public static URL createUrl(String x){
        URL u;
        try{
            u = new URL(x);
        }catch (MalformedURLException exception){
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return u;
    }

    public static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        if(url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            int responseCode = urlConnection.getResponseCode();
            if(responseCode == 200){

                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            }else {
                Log.e(LOG_TAG, "Error response cose: " + responseCode);
            }

        } catch (IOException e) {
            // TODO: Handle the exception

            Log.e(LOG_TAG, "Problem retreiving the earthquake JSON results.",e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private QueryUtils() {
    }

    /**
     * Return a list of {@link Post} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Post> extractPosts(String s) {

        // Create an empty ArrayList that we can start adding posts to
        ArrayList<Post> postlist = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONArray root = new JSONArray(s);
            for(int i =0;i<root.length() ;i++) {
                JSONObject post = root.getJSONObject(i);

                int id = post.getInt("id");
                String title = post.getString("title");
                String slug = post.getString("slug");
                String content = post.getString("content");
                String seoTitle = post.getString("seo_title");
                String seoDescription = post.getString("seo_description");
                String publishedDateTime = post.getString("published");
                String createdDateTime = post.getString("created");
                String status = post.getString("status");
                String musicLink = post.getString("music_link");
                int category = post.getInt("category");
                int author = post.getInt("author");

                Post e = new Post(id, title, slug, content, seoTitle, seoDescription, publishedDateTime, createdDateTime, status, musicLink, category, author);
                postlist.add(e);

            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the posts JSON results", e);
        }
        // Return the list of posts
        return postlist;
    }

}
