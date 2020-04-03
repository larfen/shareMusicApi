package com.jdxiang.shareMusicApi.repository;

import com.jdxiang.shareMusicApi.entity.Place;
import org.springframework.data.repository.CrudRepository;

public interface PlaceRepository extends CrudRepository<Place, Long> {

    /**
     * 判断用户是否创建了圈子
     *
     * @param id
     * @return
     */
    boolean existsByBelongUserId(Long id);

    Place findByBelongUserId(Long id
    );

}
