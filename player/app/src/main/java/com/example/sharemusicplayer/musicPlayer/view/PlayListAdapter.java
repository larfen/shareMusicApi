package com.example.sharemusicplayer.musicPlayer.view;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sharemusicplayer.R;
import com.example.sharemusicplayer.entity.PlayList;
import com.example.sharemusicplayer.httpService.DownloadImageTask;
import com.example.sharemusicplayer.musicPlayer.activities.SongsActivity;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder> {
    PlayList[] playLists;

    public static class PlayListViewHolder extends RecyclerView.ViewHolder {
        ImageView playListImage;
        TextView nameText;
        TextView desNameText;

        public PlayListViewHolder(final View view) {
            super(view);
            playListImage = view.findViewById(R.id.play_list_image);
            nameText = view.findViewById(R.id.play_list_name);
            desNameText = view.findViewById(R.id.play_list_des_name);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(view.getContext(), SongsActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    public PlayListAdapter(PlayList[] playLists) {
        this.playLists = playLists;
    }

    @Override
    public PlayListAdapter.PlayListViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.play_lists_item, parent, false);

        PlayListViewHolder vh = new PlayListViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PlayListViewHolder holder, int position) {
        PlayList playList = this.playLists[position];
        holder.nameText.setText(playList.getName());
        holder.desNameText.setText(playList.getDes_name());
        new DownloadImageTask(holder.playListImage)
                .execute(playList.getPic_url());
    }

    @Override
    public int getItemCount() {
      return this.playLists.length;
    }

    public void setPlayLists(PlayList[] playLists) {
        this.playLists = playLists;
        notifyDataSetChanged();
    }
}
