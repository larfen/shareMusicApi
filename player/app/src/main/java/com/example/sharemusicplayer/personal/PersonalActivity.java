package com.example.sharemusicplayer.personal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.sharemusicplayer.R;
import com.example.sharemusicplayer.entity.User;
import com.example.sharemusicplayer.httpService.UserService;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class PersonalActivity extends AppCompatActivity {

    UserService userService = UserService.getInstance();
    User user;
    TextView userName;
    TextView nickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        userName = findViewById(R.id.username_tex);
        nickName = findViewById(R.id.nick_name_tex);

        userService.currentUser.subscribe(new Observer<User>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull User user) {
                PersonalActivity.this.user = user;
                updateMessage(user);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void updateMessage(User user) {
        userName.setText(user.getUsername());
        nickName.setText(user.getNickName());
    }
}
