package com.jdxiang.shareMusicApi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.jdxiang.shareMusicApi.entity.Place;
import com.jdxiang.shareMusicApi.entity.User;
import com.jdxiang.shareMusicApi.service.PlaceService;
import com.jdxiang.shareMusicApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("place")
public class PlaceController {
    @Autowired
    UserService userService;
    @Autowired
    PlaceService placeService;

    /**
     * 判断当前登陆用户是否创建了圈子
     *
     * @param request
     * @return
     */
    @GetMapping("/isCreate")
    public Boolean isCreate(HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        return placeService.isCreatePlace(user);
    }

    /**
     * 创建圈子
     *
     * @param place
     * @return
     */
    @PostMapping
    @JsonView(BaseJsonView.class)
    public Place createPlace(@RequestBody Place place) {
        return placeService.createPlace(place);
    }

    /**
     * 将用户添加到圈子
     * @param id
     * @param users
     */
    @PutMapping("/addUsers/{id}")
    public void addUsersToPlace(@PathVariable Long id, @RequestBody List<User> users) {
        placeService.addUsersToPlace(id, users);
    }


    private interface BaseJsonView {
    }
}
