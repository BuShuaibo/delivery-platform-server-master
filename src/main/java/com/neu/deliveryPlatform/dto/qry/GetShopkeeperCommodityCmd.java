package com.neu.deliveryPlatform.dto.qry;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author Nirvana
 * @Date 2023/4/17 21:51
 * @Description:
 */
@Data
public class GetShopkeeperCommodityCmd {
    /**
     * 商家id
     */
    @NotNull(message = "商家id不能为空")
    private Long id;

    /**
     * 当前查询页
     */
    private int currentPage;

    /**
     * 每页查询数
     */
    private int pageSize;
}
