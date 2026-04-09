package com.neu.deliveryPlatform.controller;

import com.neu.deliveryPlatform.service.FileService;
import com.neu.deliveryPlatform.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author asiawu
 * @date 2023/05/28 22:18
 * @description:
 */
@RestController
@Api(tags = "文件")
@RequestMapping("/api/file")
public class FileController {
    @Autowired
    FileService fileService;

    @ApiOperation(value = "上传图片", notes = "上传图片，返回图片url")
    @PostMapping("/uploadImage")
    public Response uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        return fileService.uploadImage(image);
    }

    @ApiOperation(value = "下载图片", notes = "传入图片相对路径+文件名，下载文件")
    @GetMapping("/getImage/{imageName}")
    public void getImage(HttpServletResponse response, @PathVariable("imageName") String imageName) throws IOException {
        fileService.getImage(response, imageName);
    }
}
