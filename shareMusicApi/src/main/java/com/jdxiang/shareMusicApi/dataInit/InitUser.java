package com.jdxiang.shareMusicApi.dataInit;

import com.jdxiang.shareMusicApi.entity.User;
import com.jdxiang.shareMusicApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class InitUser implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    UserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        if (!userRepository.findAll().iterator().hasNext()) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword("123456");
            user.setNickName("admin");
            userRepository.save(user);
        }
    }
}
