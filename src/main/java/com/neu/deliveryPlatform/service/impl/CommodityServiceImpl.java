package com.neu.deliveryPlatform.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neu.deliveryPlatform.config.exception.BizException;
import com.neu.deliveryPlatform.dto.cmd.AddCommodityCmd;
import com.neu.deliveryPlatform.dto.cmd.UpdateCommodityCmd;
import com.neu.deliveryPlatform.dto.dto.QueryCommodityDto;
import com.neu.deliveryPlatform.entity.Commodity;
import com.neu.deliveryPlatform.entity.ShopkeeperCommodity;
import com.neu.deliveryPlatform.mapper.CommodityMapper;
import com.neu.deliveryPlatform.mapper.ShopkeeperCommodityMapper;
import com.neu.deliveryPlatform.properties.ErrorCode;
import com.neu.deliveryPlatform.properties.ExcelColumnProperties;
import com.neu.deliveryPlatform.service.CommodityService;
import com.neu.deliveryPlatform.util.ObjectUtils;
import com.neu.deliveryPlatform.util.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

/**
 * @author Asia
 * @description 针对表【commodity(商品表)】的数据库操作Service实现
 * @createDate 2023-04-05 17:25:22
 */
@Service
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private ShopkeeperCommodityMapper shopkeeperCommodityMapper;

    @Autowired
    ExcelColumnProperties excelColumnProperties;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Response addCommodity(AddCommodityCmd addCommodityCmd) {
        Commodity commodity = new Commodity();
        BeanUtils.copyProperties(addCommodityCmd, commodity);
        commodity.setUpdateTime(new Date());
        int cnt1 = commodityMapper.insert(commodity), cnt2 = 0;
        Long commodityId = commodity.getId();
        List<Long> shopkeeperIds = addCommodityCmd.getShopkeeperIds();
        for (Long shopkeeperId : shopkeeperIds) {
            ShopkeeperCommodity shopkeeperCommodity = new ShopkeeperCommodity();
            shopkeeperCommodity.setCommodityId(commodityId);
            shopkeeperCommodity.setShopkeeperId(shopkeeperId);
            cnt2 += shopkeeperCommodityMapper.insert(shopkeeperCommodity);
        }
        Map<String, Long> res = new HashMap<String, Long>() {{
            put("id", commodityId);
        }};
        return cnt1 + cnt2 >= 1 + shopkeeperIds.size() ? Response.of(res) : Response.error();
    }

    @Override
    public Response deleteById(Long id) {
        int cnt = commodityMapper.deleteById(id);
        return cnt > 0 ? Response.success() : Response.error();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Response updateById(UpdateCommodityCmd updateCommodityCmd) {
        //先保存原来的数据
        QueryCommodityDto commodity = (QueryCommodityDto) getById(updateCommodityCmd.getId()).getData();
        if (commodity == null) {
            throw new BizException(ErrorCode.ID_MISS);
        }
        //删除商品，连带删除商家-商品中间表的数据
        deleteById(updateCommodityCmd.getId());
        //再重新新增商品
        AddCommodityCmd addCommodityCmd = new AddCommodityCmd();
        try {
            ObjectUtils.copyNonNullProperties(commodity, addCommodityCmd);
            ObjectUtils.copyNonNullProperties(updateCommodityCmd, addCommodityCmd);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return addCommodity(addCommodityCmd);
    }

    @Override
    public Response getAllBrief() {
        List<Commodity> commodities = commodityMapper.getAllBrief();
        return Response.of(getQueryCommodityDtos(commodities));
    }

    @Override
    public Response getAllDetail(int currentPage, int pageSize) {
        Page<Commodity> page = new Page<>(currentPage, pageSize);
        commodityMapper.selectPage(page, null);
        List<Commodity> commodities = page.getRecords();
        List<QueryCommodityDto> commodityDtos = getQueryCommodityDtos(commodities);
        IPage<QueryCommodityDto> res = new Page<>();
        BeanUtils.copyProperties(page, res);
        res.setRecords(commodityDtos);
        return Response.of(res);
    }

    @Override
    public Response getById(Long id) {
        Commodity commodity = commodityMapper.selectById(id);
        return Response.of(getQueryCommodityDtos(Arrays.asList(commodity)).get(0));
    }

    @Override
    public Response getByCategory(Long categoryId,int perPage, int nowPage) {
        Page page = new Page<>(nowPage, perPage);
        QueryWrapper<Commodity> wrapper=new QueryWrapper<>();
        wrapper.eq("category_id",categoryId);
        commodityMapper.selectPage(page,wrapper);
        List<Commodity> commodityList = page.getRecords();
        if(null!=commodityList&&commodityList.size()>0){
            return Response.of(commodityList);
        }else{
            return Response.error(ErrorCode.NO_DATA);
        }
    }

    @Override
    public Response getCountByCategory(Long categoryId) {
        QueryWrapper<Commodity> wrapper=new QueryWrapper<>();
        wrapper.eq("category_id",categoryId);
        Long count = commodityMapper.selectCount(wrapper);
        return Response.of(count);
    }

    //传入商品列表，返回带所属商家id的商品列表
    @NotNull
    private List<QueryCommodityDto> getQueryCommodityDtos(List<Commodity> commodities) {
        List<QueryCommodityDto> commodityDtos = new ArrayList<>();
        commodities.forEach(commodity -> {
            Long commodityId = commodity.getId();
            LambdaQueryWrapper<ShopkeeperCommodity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ShopkeeperCommodity::getCommodityId, commodityId);
            List<ShopkeeperCommodity> shopkeeperCommodities = shopkeeperCommodityMapper.selectList(wrapper);
            List<Long> shopkeeperIds = new ArrayList<>();
            shopkeeperCommodities.forEach(shopkeeperCommodity -> shopkeeperIds.add(shopkeeperCommodity.getShopkeeperId()));
            QueryCommodityDto queryCommodityDto = new QueryCommodityDto();
            BeanUtils.copyProperties(commodity, queryCommodityDto);
            queryCommodityDto.setShopkeeperIds(shopkeeperIds);
            commodityDtos.add(queryCommodityDto);
        });
        return commodityDtos;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Response importFromExcel(MultipartFile file) {
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new BizException(ErrorCode.FILE_ANALYZE_ERROR);
        }
        ExcelReader reader = ExcelUtil.getReader(inputStream, 0);
        List<Map<String, Object>> lines = reader.readAll();
        lines.stream()
                .map(line -> ObjectUtils.transferExcelLineToCommodity(line, excelColumnProperties, commodityMapper))
                .forEach(commodity -> commodityMapper.InsertOrUpdate(commodity));
        return Response.success();
    }

    @Override
    public Response getByKeyword(String keyword) {
        LambdaQueryWrapper<Commodity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Commodity::getName, keyword);
        return Response.of(commodityMapper.selectList(wrapper));
    }

    @Override
    public List<Long> getByNameAndPrice(String keyword,Double price) {
        QueryWrapper<Commodity> wrapper = new QueryWrapper<>();
        wrapper.eq("name",keyword).eq("price",price);
        List<Long>ids=new ArrayList<>();
        for(Commodity x:commodityMapper.selectList(wrapper)){
            ids.add(x.getId());
        }
        return ids;
    }
}
