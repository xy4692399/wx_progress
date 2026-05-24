package org.goodbye.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.goodbye.entity.User;
import org.goodbye.mapper.UserMapper;
import org.goodbye.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByOpenid(String openid) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getOpenid, openid);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public User createOrUpdateUser(String openid, String nickname, String avatarUrl) {
        User existingUser = getUserByOpenid(openid);
        if (existingUser != null) {
            existingUser.setNickname(nickname);
            existingUser.setAvatarUrl(avatarUrl);
            existingUser.setUpdateTime(LocalDateTime.now());
            userMapper.updateById(existingUser);
            return existingUser;
        }
        User newUser = new User();
        newUser.setOpenid(openid);
        newUser.setNickname(nickname);
        newUser.setAvatarUrl(avatarUrl);
        newUser.setCreateTime(LocalDateTime.now());
        newUser.setUpdateTime(LocalDateTime.now());
        userMapper.insert(newUser);
        return newUser;
    }

    @Override
    public User getUserById(String id) {
        return userMapper.selectById(id);
    }
}