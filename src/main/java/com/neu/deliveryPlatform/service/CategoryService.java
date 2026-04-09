package com.neu.deliveryPlatform.service;


import com.neu.deliveryPlatform.dto.cmd.AddCategoryCmd;
import com.neu.deliveryPlatform.dto.cmd.UpdateCategoryCmd;
import com.neu.deliveryPlatform.util.Response;

import java.util.List;

/**
 * @author Asia
 * @description 针对表【category】的数据库操作Service
 * @createDate 2023-04-05 17:25:22
 */
public interface CategoryService {
    Response getById(Long id);

    Response getAll(int perPage, int nowPage);

    Response deleteById(Long id);

    Response deleteBatch(List<Long> ids);

    Response update(UpdateCategoryCmd updateCategoryCmd);

    Response add(AddCategoryCmd addCategoryCmd);
}
