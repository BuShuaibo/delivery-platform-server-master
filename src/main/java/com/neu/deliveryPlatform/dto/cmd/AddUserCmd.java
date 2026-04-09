package com.neu.deliveryPlatform.dto.cmd;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author asiawu
 * @Date 2023-04-17 18:51
 * @Description:
 */
@Data
public class AddUserCmd {
    /**
     * 小程序唯一标识
     */
    private String openid;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 电话
     */
    @NotBlank(message = "电话不能为空")
    private String phone;

    /**
     * 性别
     */
    @NotNull(message = "性别不能为空")
    private Integer gender;

    /**
     * 学号(骑手)
     */
    @NotNull(message = "学号不能为空")
    private Integer studentId;

    /**
     * 头像url
     */
    private String avatarUrl;
}
