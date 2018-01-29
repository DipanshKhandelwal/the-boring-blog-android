package com.example.dipansh.the_boring_blog_android;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dipansh on 19/1/18.
 */

public class PostAdapter extends ArrayAdapter<Post> {
    public PostAdapter(@NonNull Context context, int resource, List<Post> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.post_item, parent, false);
        }

        Post post = getItem(position);
        TextView title = convertView.findViewById(R.id.title);
        TextView content = convertView.findViewById(R.id.content);
        TextView date = convertView.findViewById(R.id.date);
        TextView category = convertView.findViewById(R.id.category);
        title.setText(post.getTitle());
        content.setText(post.getContent());
        date.setText(post.getPublishedDateTime());
        category.setText(String.valueOf(position));


        return convertView;
    }
}
