package com.neu.deliveryPlatform.dto.cmd;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author asiawu
 * @Date 2023-04-10 20:21
 * @Description: 新增商家时也要为其创建一个用户，传递的参数不仅包含商家信息，还包含部分用户信息
 */
@Data
public class AddShopkeeperCmd {
    /**
     * 店名
     */
    @NotBlank(message = "店名不能为空")
    private String name;

    /**
     * 描述
     */
    @NotBlank(message = "描述不能为空")
    private String description;

    /**
     * 电话
     */
    @NotBlank(message = "电话不能为空")
    private String phone;

    /**
     * 开始营业时间
     */
    @NotNull(message = "开始营业时间不能为空")
    private Date startTime;

    /**
     * 结束营业时间
     */
    @NotNull(message = "结束营业时间不能为空")
    private Date endTime;

    /**
     * 是否正常营业
     */
    @NotNull(message = "营业状态不能为空")
    private Integer isAvailable;

    /**
     * 位置id
     */
    @NotBlank(message = "位置不能为空")
    private String placeId;

    /**
     * 微信开放平台唯一id
     */
    private String openid;

    /**
     * 姓名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像url
     */
    private String avatarUrl;
}
