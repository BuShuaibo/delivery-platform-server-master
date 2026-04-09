package com.neu.deliveryPlatform.service;

import com.neu.deliveryPlatform.dto.cmd.AddShopkeeperCmd;
import com.neu.deliveryPlatform.dto.cmd.UpdateShopkeeperCmd;
import com.neu.deliveryPlatform.dto.qry.GetShopkeeperCommodityCmd;
import com.neu.deliveryPlatform.util.Response;


/**
 * @author Asia
 * @description 针对表【shopkeeper(商家表)】的数据库操作Service
 * @createDate 2023-04-05 17:25:22
 */
public interface ShopkeeperService {
    Response getShopkeeperById(Long id);

    Response getShopkeepers(int currentPage, int pageSize);

    Response addShopkeeper(AddShopkeeperCmd addShopkeeperCmd);

    Response deleteShopkeeper(Long id);

    Response updateShopkeeper(UpdateShopkeeperCmd updateShopkeeperCmd);

    Response getShopkeeperCommodity(GetShopkeeperCommodityCmd getShopkeeperCommodityCmd);

    Response getCommodityCount(Long id);

    Response getUserIdByShopkeeperId(Long id);

}
