package com.neu.deliveryPlatform.dto.cmd;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author asiawu
 * @Date 2023-04-17 18:47
 * @Description:
 */
@Data
public class UpdateUserCmd {
    /**
     * 用户id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 姓名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 电话
     */
    private String phone;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 头像url
     */
    private String avatarUrl;

    /**
     * 学号(骑手)
     */
    private Integer studentId;
}
