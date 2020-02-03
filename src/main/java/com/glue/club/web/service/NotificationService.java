package com.glue.club.web.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.glue.club.web.dao.NotificationMapper;
import com.glue.club.web.model.Notification;
@Service
public class NotificationService{

    @Resource
    private NotificationMapper notificationMapper;

    
    public int deleteByPrimaryKey(Long id) {
        return notificationMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(Notification record) {
        return notificationMapper.insert(record);
    }

    
    public int insertSelective(Notification record) {
        return notificationMapper.insertSelective(record);
    }

    
    public Notification selectByPrimaryKey(Long id) {
        return notificationMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(Notification record) {
        return notificationMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Notification record) {
        return notificationMapper.updateByPrimaryKey(record);
    }

    public Long unreadCount(Long userId) {
        return null;
    }
}
