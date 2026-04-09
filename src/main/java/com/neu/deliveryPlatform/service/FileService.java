package com.neu.deliveryPlatform.service;

import com.neu.deliveryPlatform.util.Response;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author asiawu
 * @date 2023/05/28 22:21
 * @description:
 */
public interface FileService {
    Response uploadImage(MultipartFile image) throws IOException;

    void getImage(HttpServletResponse response, String imageName) throws IOException;
}
