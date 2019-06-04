package com.funplay.shop.controller;

import entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import util.FastDFSClient;

/**
 * 文件上传controller
 */
@RestController
public class UploadController {
    @Value("${FILE_SERVER_URL}")
    private String file_server_url;

    @RequestMapping("upload")
    public Result upload(MultipartFile file){
        //1.获取文件扩展名
        String originalFilename = file.getOriginalFilename();
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        try {
            //2、创建一个 FastDFS 的客户端
            FastDFSClient client=new FastDFSClient("classpath:config/fdfs_client.conf");
            //3.执行上传处理
            String path = client.uploadFile(file.getBytes(), extName);
            //4.组合url
            String url=file_server_url+path;
            return  new Result(true,url);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"上传失败");
        }

    }
}
