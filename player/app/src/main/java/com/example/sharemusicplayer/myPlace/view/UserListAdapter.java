package com.example.sharemusicplayer.myPlace.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sharemusicplayer.R;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListHolder> {


    public static class UserListHolder extends RecyclerView.ViewHolder {

        public UserListHolder(View view) {
            super(view);
        }
    }

    @Override
    public UserListAdapter.UserListHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item, parent, false);
        UserListHolder vh = new UserListHolder(v);
        return vh;
    }

    /**
     * 根据歌曲信息更新ui
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(UserListAdapter.UserListHolder holder, final int position) {

    }

    //    @Override
    public int getItemCount() {
        return 20;
    }
}
