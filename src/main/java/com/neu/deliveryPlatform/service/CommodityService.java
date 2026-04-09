package com.neu.deliveryPlatform.service;

import com.neu.deliveryPlatform.dto.cmd.AddCommodityCmd;
import com.neu.deliveryPlatform.dto.cmd.UpdateCommodityCmd;
import com.neu.deliveryPlatform.util.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Asia
 * @description 针对表【commodity(商品表)】的数据库操作Service
 * @createDate 2023-04-05 17:25:22
 */
public interface CommodityService {

    Response addCommodity(AddCommodityCmd addCommodityCmd);

    Response deleteById(Long id);

    Response updateById(UpdateCommodityCmd updateCommodityCmd);

    Response getAllBrief();

    Response getAllDetail(int currentPage, int pageSize);

    Response getById(Long id);

    Response getByCategory(Long categoryId,int perPage, int nowPage);

    Response getCountByCategory(Long categoryId);

    Response importFromExcel(MultipartFile file);

    Response getByKeyword(String keyword);
    List<Long> getByNameAndPrice(String keyword,Double price);
}
