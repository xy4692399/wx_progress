package org.goodbye.service;

import org.goodbye.entity.User;

public interface UserService {
    User getUserByOpenid(String openid);
    User createOrUpdateUser(String openid, String nickname, String avatarUrl);
    User getUserById(String id);
}