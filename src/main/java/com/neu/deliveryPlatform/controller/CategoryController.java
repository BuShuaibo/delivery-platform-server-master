package com.neu.deliveryPlatform.controller;

import com.neu.deliveryPlatform.dto.cmd.AddCategoryCmd;
import com.neu.deliveryPlatform.dto.cmd.UpdateCategoryCmd;
import com.neu.deliveryPlatform.service.CategoryService;
import com.neu.deliveryPlatform.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author asiawu
 * @Date 2023-04-05 15:16
 * @Description:
 */

@Api(tags = "商品分类")
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    @ApiOperation(value = "根据id获取商品分类", notes = "根据传入的商品分类id查询商品分类信息")
    @GetMapping("/getById")
    public Response getById(@RequestParam("id") Long id) {
        return categoryService.getById(id);
    }

    @ApiOperation(value = "获取所有商品分类",
            notes = "分页查询所有商品分类信息，传入当前页和每页展示条数，无需分页请传入-1、-1")
    @GetMapping("/getAll")
    public Response getAll(@RequestParam int currentPage, @RequestParam int pageSize) {
        return categoryService.getAll(pageSize, currentPage);
    }

    @ApiOperation(value = "根据id删除商品分类", notes = "根据id删除商品分类")
    @DeleteMapping("/deleteById")
    public Response deleteById(@RequestParam("id") Long id) {
        return categoryService.deleteById(id);
    }

    @ApiOperation(value = "删除多个商品分类", notes = "根据商品分类id数组删除多个商品分类")
    @DeleteMapping("/deleteBatch")
    public Response dedleteBatch(@RequestBody List<Long> ids){
        return categoryService.deleteBatch(ids);
    }

    @ApiOperation(value = "根据id更新商品分类", notes = "根据id修改商品分类数据")
    @PostMapping("/updateById")
    public Response update(@Valid @RequestBody UpdateCategoryCmd updateCategoryCmd) {
        return categoryService.update(updateCategoryCmd);
    }

    @ApiOperation(value = "新增商品分类", notes = "新增商品分类，传入商品分类名，返回该分类的id")
    @PostMapping("/add")
    public Response add(@Valid @RequestBody AddCategoryCmd addCategoryCmd) {
        return categoryService.add(addCategoryCmd);
    }
}
