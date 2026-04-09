package com.neu.deliveryPlatform.dto.qry;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author asiawu
 * @Date 2023-04-14 19:10
 * @Description:
 */
@Data
public class AdminLoginQry {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
}
