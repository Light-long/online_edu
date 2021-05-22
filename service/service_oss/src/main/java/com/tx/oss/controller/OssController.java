package com.tx.oss.controller;

import com.tx.oss.service.OssService;
import com.tx.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/oss/file")
@CrossOrigin
@Api(description = "阿里云文件上传")
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("/upLoadAvatar")
    @ApiOperation("头像上传")
    public CommonResult upLoadAvatar(@ApiParam(name = "file", value = "上传文件", required = true)
                                                 MultipartFile file) {
        String url = ossService.upLoadAvatar(file);
        return url == null ? CommonResult.error() : CommonResult.ok().data("url",url);
    }

}
