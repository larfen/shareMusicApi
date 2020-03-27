package com.example.sharemusicplayer.musicPlayer.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sharemusicplayer.R;
import com.example.sharemusicplayer.entity.Song;
import com.example.sharemusicplayer.httpService.DownloadImageTask;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongsViewHolder> {

    Song[] songList;    // 显示的歌曲列表
    SongClickListener listener;

    public static class SongsViewHolder extends RecyclerView.ViewHolder {
        TextView artist;
        TextView album;
        ImageView pic_url;
        View rootView;

        public SongsViewHolder(View view) {
            super(view);
            rootView = view;
            artist = view.findViewById(R.id.artist);
            album = view.findViewById(R.id.album);
            pic_url = view.findViewById(R.id.pic_url);
        }
    }

    public SongsAdapter(Song[] songs, SongClickListener listener) {
        this.songList = songs;
        this.listener = listener;
    }

    /**
     * 设置歌曲列表
     * @param songs
     */
    public void setSongs(Song[] songs) {
        this.songList = songs;
        notifyDataSetChanged();
    }


    @Override
    public SongsAdapter.SongsViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.songs_item, parent, false);
        SongsViewHolder vh = new SongsViewHolder(v);
        return vh;
    }

    /**
     * 根据歌曲信息更新ui
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(SongsAdapter.SongsViewHolder holder, final int position) {
        Song song = songList[position];
        holder.artist.setText(song.getName() + "--" + song.getArtist());
        holder.album.setText(song.getAlbum() + "   来源:" + song.getOrigin());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(songList[position], position);
            }
        });
        new DownloadImageTask(holder.pic_url)
                .execute(song.getPic_url());

    }

    @Override
    public int getItemCount() {
        return this.songList.length;
    }

    public interface SongClickListener {
        void onClick(Song song, int position);
    }

}
