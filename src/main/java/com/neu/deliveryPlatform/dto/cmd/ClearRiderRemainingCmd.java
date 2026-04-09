package com.neu.deliveryPlatform.dto.cmd;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author asiawu
 * @date 2023/06/12 15:09
 * @description:
 */
@Data
public class ClearRiderRemainingCmd {
    /**
     * 要清空的骑手的id
     */
    @NotNull(message = "用户id不能为空")
    private Long id;
    /**
     * 后台管理员用户名
     */
    @NotEmpty(message = "用户名不能为空")
    private String username;
    /**
     * 后台管理员密码
     */
    @NotEmpty(message = "密码不能为空")
    private String password;
    /**
     * 临时提现码
     */
    @NotEmpty(message = "临时提现码不能为空")
    private String temporaryWithdrawalCode;
}
