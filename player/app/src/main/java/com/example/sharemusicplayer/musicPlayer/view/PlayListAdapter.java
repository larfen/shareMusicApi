package com.example.sharemusicplayer.musicPlayer.view;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sharemusicplayer.R;
import com.example.sharemusicplayer.entity.OriginType;
import com.example.sharemusicplayer.entity.PlayList;
import com.example.sharemusicplayer.httpService.DownloadImageTask;
import com.example.sharemusicplayer.musicPlayer.activities.SongsActivity;

/**
 * 歌单适配器 用于显示歌单 carview
 */
public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder> {
    PlayList[] playLists;
    /**
     * 下列信息用于显示歌单的详情 主要说明点击了哪个歌单
     */
    public final static String PLAY_LIST_ID = "play_list_id";
    public final static String PLAY_LIST_IMAGE = "play_list_image";
    public final static String PLAY_LIST_NAME = "play_list_name";
    public final static String ORIGIN_TYPE = "origin_type";

    // 歌单来源类型 外部或本地
    OriginType originType;

    public static class PlayListViewHolder extends RecyclerView.ViewHolder {
        ImageView playListImage;    // 歌单封面
        TextView nameText;          // 歌单名
        TextView desNameText;       // 歌单描述名
        View view;

        public PlayListViewHolder(final View view) {
            super(view);
            playListImage = view.findViewById(R.id.play_list_image);
            nameText = view.findViewById(R.id.play_list_name);
            desNameText = view.findViewById(R.id.play_list_des_name);
            this.view = view;
        }
    }

    public PlayListAdapter(PlayList[] playLists, OriginType type) {
        this.playLists = playLists;
        this.originType = type;
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
    public void onBindViewHolder(final PlayListViewHolder holder, int position) {
        // 设置歌单的信息
        final PlayList playList = this.playLists[position];
        holder.nameText.setText(playList.getName());
        holder.desNameText.setText(playList.getDes_name());

        // 当点击歌单 进入个歌曲的展示
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.view.getContext(), SongsActivity.class);
                intent.putExtra(PLAY_LIST_ID, playList.getPlay_list_id());
                intent.putExtra(PLAY_LIST_NAME, playList.getName());
                intent.putExtra(PLAY_LIST_IMAGE, playList.getPic_url());
                intent.putExtra(ORIGIN_TYPE, originType.name());
                holder.view.getContext().startActivity(intent);
            }
        });

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
