package com.neu.deliveryPlatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neu.deliveryPlatform.dto.cmd.AddShopkeeperCmd;
import com.neu.deliveryPlatform.dto.cmd.UpdateShopkeeperCmd;
import com.neu.deliveryPlatform.dto.qry.GetShopkeeperCommodityCmd;
import com.neu.deliveryPlatform.entity.Commodity;
import com.neu.deliveryPlatform.entity.Shopkeeper;
import com.neu.deliveryPlatform.entity.ShopkeeperCommodity;
import com.neu.deliveryPlatform.entity.User;
import com.neu.deliveryPlatform.mapper.ShopkeeperCommodityMapper;
import com.neu.deliveryPlatform.mapper.ShopkeeperMapper;
import com.neu.deliveryPlatform.properties.ErrorCode;
import com.neu.deliveryPlatform.service.CommodityService;
import com.neu.deliveryPlatform.service.ShopkeeperService;
import com.neu.deliveryPlatform.service.UserService;
import com.neu.deliveryPlatform.util.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Asia
 * @description 针对表【shopkeeper(商家表)】的数据库操作Service实现
 * @createDate 2023-04-05 17:25:22
 */
@Service
public class ShopkeeperServiceImpl implements ShopkeeperService {

    @Resource
    ShopkeeperMapper shopkeeperMapper;

    @Resource
    ShopkeeperCommodityMapper shopkeeperCommodityMapper;

    @Autowired
    UserService userService;

    @Autowired
    CommodityService commodityService;

    @Override
    public Response getShopkeeperById(Long id) {
        Shopkeeper shopkeeper = shopkeeperMapper.selectById(id);
        return Response.of(shopkeeper);
    }

    @Override
    public Response getShopkeepers(int currentPage, int pageSize) {
        Page<Shopkeeper> page = new Page<>(currentPage, pageSize);
        shopkeeperMapper.selectPage(page, null);
        return Response.of(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Response addShopkeeper(AddShopkeeperCmd addShopkeeperCmd) {
        User user = new User();
        BeanUtils.copyProperties(addShopkeeperCmd, user);
        Shopkeeper shopkeeper = new Shopkeeper();
        BeanUtils.copyProperties(addShopkeeperCmd, shopkeeper);
        int cnt = shopkeeperMapper.insert(shopkeeper);
        Long shopkeeperId = shopkeeper.getId();
        boolean flag = userService.addUserForShopkeeper(user, shopkeeperId);
        Map<String, Long> res = new HashMap<String, Long>() {{
            put("id", shopkeeperId);
        }};
        return cnt > 0 && flag ? Response.of(res) : Response.error();
    }

    @Override
    public Response deleteShopkeeper(Long id) {
        shopkeeperMapper.deleteById(id);
        return Response.success();
    }

    @Override
    public Response updateShopkeeper(UpdateShopkeeperCmd updateShopkeeperCmd) {
        Shopkeeper shopkeeper = new Shopkeeper();
        BeanUtils.copyProperties(updateShopkeeperCmd, shopkeeper);
        shopkeeper.setUpdateTime(new Date());
        Long id = shopkeeper.getId();
        Shopkeeper temp = shopkeeperMapper.selectById(id);
        if (temp != null) {
            shopkeeperMapper.updateById(shopkeeper);
            return Response.success();
        } else {
            return Response.error(ErrorCode.ID_MISS);
        }
    }

    @Override
    public Response getShopkeeperCommodity(GetShopkeeperCommodityCmd getShopkeeperCommodityCmd) {
        List<Commodity> commodities = shopkeeperCommodityMapper.getShopkeeperCommodity(
                getShopkeeperCommodityCmd.getId(),
                (getShopkeeperCommodityCmd.getCurrentPage() - 1)*getShopkeeperCommodityCmd.getPageSize(),
                getShopkeeperCommodityCmd.getPageSize()
        );
        return Response.of(commodities);
    }

    @Override
    public Response getCommodityCount(Long id) {
        LambdaQueryWrapper<ShopkeeperCommodity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShopkeeperCommodity::getShopkeeperId,id);
        return Response.of(shopkeeperCommodityMapper.selectCount(wrapper));
    }

    @Override
    public Response getUserIdByShopkeeperId(Long id) {
        return  Response.of(shopkeeperMapper.getUserIdByShopkeeperId(id));
    }
}




