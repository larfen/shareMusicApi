package com.example.sharemusicplayer.musicPlayer.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sharemusicplayer.R;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongsViewHolder> {
    int total;

    public static class SongsViewHolder extends RecyclerView.ViewHolder {
        public SongsViewHolder(View view) {
            super(view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SongsAdapter(int total) {
        this.total = total;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SongsAdapter.SongsViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.songs_item, parent, false);

        SongsViewHolder vh = new SongsViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SongsAdapter.SongsViewHolder holder, int position) {
//        holder.textView.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return total;
    }

}
