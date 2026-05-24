package org.goodbye.common.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String openid;
    private String nickname;
    private String avatarUrl;
    private String phone;
}