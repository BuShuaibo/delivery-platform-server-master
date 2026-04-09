package com.neu.deliveryPlatform.dto.cmd;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author asiawu
 * @Date 2023-04-06 0:47
 * @Description:
 */
@Data
public class AddCommodityCmd {
    /**
     * 商品图片
     */
    @NotBlank(message = "商品图片不能为空")
    private String imgUrl;

    /**
     * 商品名
     */
    @NotBlank(message = "商品名不能为空")
    private String name;

    /**
     * 商品描述
     */
    @NotBlank(message = "商品描述不能为空")
    private String description;

    /**
     * 商品价格
     */
    @NotNull(message = "商品价格不能为空")
    private Double price;

    /**
     * 上架状态
     */
    @NotNull(message = "上架状态不能为空")
    private Integer isAvailable;

    /**
     * 重量
     */
    @NotNull(message = "重量不能为空")
    private Double weight;

    /**
     * 商品属于的类别的id
     */
    private Long categoryId;

    /**
     * 商品所属的商家的id们
     */
    private List<Long> shopkeeperIds;
}
