package com.jdxiang.shareMusicApi.service;

import com.jdxiang.shareMusicApi.entity.Place;
import com.jdxiang.shareMusicApi.entity.User;

import java.util.List;

public interface PlaceService {

    /**
     * 判断用户是否创建了圈子
     *
     * @return
     */
    Boolean isCreatePlace(User user);

    /**
     * 创建圈子
     *
     * @param place
     * @return
     */
    Place createPlace(Place place);

    /**
     * 将用户添加到圈子
     * @param users
     */
    void addUsersToPlace(Long id, List<User> users);

}
