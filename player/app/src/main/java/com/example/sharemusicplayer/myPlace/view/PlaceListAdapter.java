package com.example.sharemusicplayer.myPlace.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sharemusicplayer.R;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.PlaceListViewHolder> {

    public static class PlaceListViewHolder extends RecyclerView.ViewHolder {

        public PlaceListViewHolder(final View view) {
            super(view);
        }
    }

    @Override
    public PlaceListAdapter.PlaceListViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.place_list_item, parent, false);

        PlaceListViewHolder vh = new PlaceListViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final PlaceListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

}
