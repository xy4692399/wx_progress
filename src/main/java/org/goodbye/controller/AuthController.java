package org.goodbye.controller;

import org.goodbye.common.Result;
import org.goodbye.entity.User;
import org.goodbye.service.UserService;
import org.goodbye.service.WxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private WxService wxService;

    @PostMapping("/login")
    public Result<User> wxLogin(@RequestBody WxLoginRequest request) {
        String code = request.getCode();
        String nickname = request.getNickname();
        String avatarUrl = request.getAvatarUrl();

        String openid = wxService.getOpenid(code);
        if (openid == null) {
            return Result.error("Invalid code or unable to get openid");
        }

        User user = userService.createOrUpdateUser(openid, nickname, avatarUrl);
        return Result.success(user);
    }

    @GetMapping("/user/{id}")
    public Result<User> getUser(@PathVariable String id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return Result.error("User not found");
        }
        return Result.success(user);
    }

    public static class WxLoginRequest {
        private String code;
        private String nickname;
        private String avatarUrl;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }
    }
}