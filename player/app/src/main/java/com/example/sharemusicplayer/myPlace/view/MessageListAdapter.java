package com.example.sharemusicplayer.myPlace.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sharemusicplayer.R;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageListHolder> {
    public static class MessageListHolder extends RecyclerView.ViewHolder {

        public MessageListHolder(View view) {
            super(view);
        }
    }

    @Override
    public MessageListAdapter.MessageListHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        MessageListHolder vh = new MessageListHolder(v);
        return vh;
    }

    /**
     * 根据歌曲信息更新ui
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MessageListAdapter.MessageListHolder holder, final int position) {

    }

    //    @Override
    public int getItemCount() {
        return 30;
    }
}
