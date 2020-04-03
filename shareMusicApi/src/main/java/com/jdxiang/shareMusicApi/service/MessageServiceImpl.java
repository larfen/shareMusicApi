package com.jdxiang.shareMusicApi.service;

import com.jdxiang.shareMusicApi.entity.Message;
import com.jdxiang.shareMusicApi.entity.Place;
import com.jdxiang.shareMusicApi.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Override
    public List<Message> getByPlace(Long placeId) {
        return messageRepository.findAllByPlaceId(placeId);
    }

    @Override
    public Message createMessage(Long placeId, Message message) {
        Place place = new Place();
        place.setId(placeId);
        message.setPlace(place);
        message.setCreateTime(new Date());
        return messageRepository.save(message);
    }
}
