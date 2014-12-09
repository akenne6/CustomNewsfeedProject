package com.example.alex.customnewsfeedproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alex on 11/30/2014.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ListItemViewHolder>
{
    private LinkedList<Articles> articlesList;

    public RecyclerViewAdapter(LinkedList<Articles> list)
    {
        if (list == null)
        {
            throw new IllegalArgumentException("The list of articles cannot be null");
        }
        else
            articlesList = list;
    }
    public void addItems(LinkedList<Articles> newList)
    {
        for(Articles a: newList)
        {
            articlesList.add(a);
        }
        //Collections.sort(articlesList);
        notifyDataSetChanged();
    }
    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.newsfeed_list_item, viewGroup, false);
        return new ListItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder listItemViewHolder, int i) {
        Articles article = articlesList.get(i);
        listItemViewHolder.title.setText(article.getTitle());
        listItemViewHolder.description.setText(article.getDescription());
        listItemViewHolder.thumbnail.setImageResource(R.drawable.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public static final class ListItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        TextView description;
        ImageView thumbnail;
        public ListItemViewHolder(View v)
        {
            super(v);
            title = (TextView) v.findViewById(R.id.article_title);
            description = (TextView) v.findViewById(R.id.article_description);
            thumbnail = (ImageView) v.findViewById(R.id.article_thumbnail);
        }
    }
}
