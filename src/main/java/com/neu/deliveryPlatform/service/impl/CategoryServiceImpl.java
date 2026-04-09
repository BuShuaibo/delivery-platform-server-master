package com.neu.deliveryPlatform.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neu.deliveryPlatform.dto.cmd.AddCategoryCmd;
import com.neu.deliveryPlatform.dto.cmd.UpdateCategoryCmd;
import com.neu.deliveryPlatform.entity.Category;
import com.neu.deliveryPlatform.mapper.CategoryMapper;
import com.neu.deliveryPlatform.properties.ErrorCode;
import com.neu.deliveryPlatform.service.CategoryService;
import com.neu.deliveryPlatform.util.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Asia
 * @description 针对表【category】的数据库操作Service实现
 * @createDate 2023-04-05 17:25:22
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public Response getById(Long id) {
        Category category = categoryMapper.selectById(id);
        if (null != category) {
            return Response.of(category);
        } else {
            return Response.error(ErrorCode.ID_MISS);
        }
    }

    @Override
    public Response getAll(int perPage, int nowPage) {
        Page<Category> page = new Page<>(nowPage, perPage);
        categoryMapper.selectPage(page, null);
        return Response.of(page);
    }

    @Override
    public Response deleteById(Long id) {
        Category category = categoryMapper.selectById(id);
        if (category != null) {
            categoryMapper.deleteById(id);
            return Response.success();
        } else {
            return Response.error(ErrorCode.ID_MISS);
        }
    }

    @Override
    public Response deleteBatch(List<Long> ids) {
        try {
            categoryMapper.deleteBatchIds(ids);
            return Response.success();
        } catch (Exception e) {
            throw new RuntimeException("批量删除用户失败", e);
        }
    }

    @Override
    public Response update(UpdateCategoryCmd updateCategoryCmd) {
        Category category = new Category();
        BeanUtils.copyProperties(updateCategoryCmd, category);
        Long id = category.getId();
        Category exist = categoryMapper.selectById(id);
        if (exist != null) {
            categoryMapper.updateById(category);
            return Response.success();
        } else {
            return Response.error(ErrorCode.ID_MISS);
        }
    }

    @Override
    public Response add(AddCategoryCmd addCategoryCmd) {
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryCmd, category);
        int success = categoryMapper.insert(category);
        if (success == 1) {
            Map<String, Long> res = new HashMap<String, Long>() {{
                put("id", category.getId());
            }};
            return Response.of(res);
        } else {
            return Response.error();
        }
    }
}
