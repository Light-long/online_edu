package com.tx.edu.controller;


import com.tx.edu.service.SubjectService;
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

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author lishilong
 * @since 2021-05-23
 */
@RestController
@RequestMapping("/edu/subject")
@CrossOrigin
@Api(description = "课程分类管理")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    /**
     * 添加课程分类
     * @param file 获取上传过来的文件，把文件的内容读取出来
     * @return 添加是否成功
     */
    @PostMapping("/addSubject")
    @ApiOperation("添加课程分类")
    public CommonResult addSubject(@ApiParam(name = "file", value = "课程分类文件", required = true)
                                               MultipartFile file) {
        // 通过构造器传入这个subjectService
        subjectService.saveSubject(file,subjectService);
        return CommonResult.ok();
    }
}

