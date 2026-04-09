package com.neu.deliveryPlatform.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import com.neu.deliveryPlatform.config.exception.BizException;
import com.neu.deliveryPlatform.properties.ErrorCode;
import com.neu.deliveryPlatform.service.FileService;
import com.neu.deliveryPlatform.util.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @author asiawu
 * @date 2023/05/28 22:21
 * @description:
 */
@Service
public class FileServiceImpl implements FileService {
    @Value("${file.upload-path}")
    String uploadPath;
    @Value("${file.server-address}")
    String serverAddress;

    @Override
    public Response uploadImage(MultipartFile image) throws IOException {
        String originalFilename = image.getOriginalFilename();
        String extName = FileUtil.extName(originalFilename);
        if (!"jpeg".equals(extName) && !"png".equals(extName) && !"jpg".equals(extName)) {
            throw new BizException(ErrorCode.FILE_TYPE_ERROR);
        }
        String path = uploadPath;
        //判断文件夹是否存在，存在就不需要重新创建，不存在就创建
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        String finalFileName = format + "_" + UUID.fastUUID().toString(true) + '.' + extName;
        //转换成对应的文件存储，new File第一个参数是目录的路径，第二个参数是文件的完整名字
        image.transferTo(new File(file, finalFileName));

        //上传文件的全路径
        String url = serverAddress + "api/file/getImage/" + finalFileName;
        return Response.of(new HashMap<String, String>(1) {{
            put("url", url);
        }});
    }

    @Override
    public void getImage(HttpServletResponse response, String imageName) throws IOException {
        response.setContentType("image/jpeg;charset=utf-8");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(Files.readAllBytes(Paths.get(uploadPath).resolve(imageName)));
        outputStream.flush();
        outputStream.close();
    }
}
