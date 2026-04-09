package com.neu.deliveryPlatform.controller;

import com.neu.deliveryPlatform.dto.cmd.AddCommodityCmd;
import com.neu.deliveryPlatform.dto.cmd.UpdateCommodityCmd;
import com.neu.deliveryPlatform.service.CommodityService;
import com.neu.deliveryPlatform.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;


/**
 * @Author asiawu
 * @Date 2023-04-05 15:11
 * @Description:
 */

@Api(tags = "商品")
@RestController
@RequestMapping("/api/commodity")
public class CommodityController {
    @Autowired
    CommodityService commodityService;

    @ApiOperation(value = "增加商品", notes = "传的参数不仅有商品信息，还有商品所属的商家id数组：shopkeeperIds，返回该商品的id")
    @PostMapping("/add")
    public Response addCommodity(@Valid @RequestBody AddCommodityCmd addCommodityCmd) {
        return commodityService.addCommodity(addCommodityCmd);
    }

    @ApiOperation(value = "根据id删除商品", notes = "根据id删除商品")
    @PostMapping("/deleteById")
    public Response deleteById(@RequestParam Long id) {
        return commodityService.deleteById(id);
    }

    @ApiOperation(value = "根据id更新商品信息", notes = "传的参数不仅有商品信息，还有商品所属的商家id数组：shopkeeperIds")
    @PostMapping("/updateById")
    public Response updateById(@Valid @RequestBody UpdateCommodityCmd updateCommodityCmd) {
        return commodityService.updateById(updateCommodityCmd);
    }

    @ApiOperation(value = "简短查询所有商品", notes = "不带分页查询商品的简要信息，包括商品名、图片、价格、销售量和所属商家")
    @GetMapping("/getAllBrief")
    public Response getAllBrief() {
        return commodityService.getAllBrief();
    }

    @ApiOperation(value = "详细查询所有商品", notes = "带分页查询商品的详细信息，包括所属商家，不分页请传入-1、-1")
    @GetMapping("/getAllDetail")
    public Response getAllDetail(@RequestParam int currentPage, @RequestParam int pageSize) {
        return commodityService.getAllDetail(currentPage, pageSize);
    }

    @ApiOperation(value = "根据id查询商品", notes = "根据id查询商品，包括所属的商家id")
    @GetMapping("/getById")
    public Response getById(@RequestParam Long id) {
        return commodityService.getById(id);
    }


    @ApiOperation(value = "根据分类查询商品", notes = "带分页根据商品分类查询商品的详细信息，不分页请传入-1、-1")
    @GetMapping("/getByCategory")
    public Response getByCategory(@RequestParam Long categoryId,@RequestParam int currentPage, @RequestParam int pageSize){
        return commodityService.getByCategory(categoryId,pageSize,currentPage);
    }

    @ApiOperation(value="查询分类下商品数量",notes = "查询分类下商品数量")
    @GetMapping("/getCountByCategory")
    public Response getCountByCategory(@RequestParam Long categoryId) {
        return commodityService.getCountByCategory(categoryId);
    }

    @ApiOperation(value = "上传EXCEL更新商品数据", notes = "将微生活后台导出的excel上传，更新商品数据")
    @PostMapping("/importFromExcel")
    @ResponseBody
    public Response importFromExcel(@RequestParam("file") MultipartFile file) {
        return commodityService.importFromExcel(file);
    }

    @ApiOperation(value = "根据商品名关键字搜索商品列表", notes = "根据商品名关键字搜索商品列表")
    @GetMapping("/getByKeyword")
    public Response getByKeyword(@NotBlank(message = "关键字不能为空") @RequestParam String keyword) {
        return commodityService.getByKeyword(keyword);
    }

    @ApiOperation(value = "根据商品名关键字搜索商品", notes = "根据商品名关键字搜索商品，这个接口是具体搜索")
    @GetMapping("/getByNameAndPrice")
    public List<Long> getByNameAndPrice(@NotBlank(message = "关键字不能为空") @RequestParam String keyword,@NotBlank(message = "关键字不能为空") @RequestParam Double price) {
        return commodityService.getByNameAndPrice(keyword,price);
    }
}
