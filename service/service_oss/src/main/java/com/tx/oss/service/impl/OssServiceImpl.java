package com.tx.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.tx.oss.service.OssService;
import com.tx.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    // 上传讲师头像，返回存储的url值
    @Override
    public String upLoadAvatar(MultipartFile file) {
        // 获取常量
        String endPoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        OSS ossClient = null;
        InputStream fileInputStream = null;
        try {
            // 创建一个Oss实例
            ossClient = new OSSClientBuilder().build(endPoint,accessKeyId,accessKeySecret);
            // 获取上传文件的文件输入流
            fileInputStream = file.getInputStream();
            // 获取文件名称
            String fileName = file.getOriginalFilename();
            // 获取一个UUID，
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            // 拼接文件名
            fileName = uuid + fileName;

            // 生成日期格式文件夹
            String datePath = new DateTime().toString("yyyy/MM/dd");

            // 拼接文件路径/文件名
            fileName = datePath + "/" + fileName;

            // 调用Oss方法实现上传
            // 三个参数，bucketName，文件路径+文件名称（可以拼接文件路径），文件输入流
            ossClient.putObject(bucketName,fileName,fileInputStream);
            ossClient.shutdown();
            // 获取上传之后的路径，返回，存数据库
            return "https://"+bucketName+"."+endPoint+"/"+fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
