package com.tx.edu.controller;


import com.tx.edu.entity.Teacher;
import com.tx.edu.service.TeacherService;
import com.tx.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author lishilong
 * @since 2021-05-19
 */
@RestController
@RequestMapping("/edu/teacher")
@Api(description = "讲师管理")
public class TeacherController {

    // 注入TeacherService,这个类被Mybatis-Plus封装过，底层调用了Mapper层
    @Autowired
    private TeacherService teacherService;

    /**
     * 查询所有Teacher
     * @return CommonResult
     */
    @GetMapping("/findAll")
    @ApiOperation(value = "查询所有讲师")
    public CommonResult findAllTeacher() {
        List<Teacher> teacherList = teacherService.list(null);
        // 构建一个map数据集
//        Map<String, Object> data = new HashMap<>();
//        data.put("teacherList",teacherList);
        return CommonResult.ok().data("teacherList",teacherList);
    }

    /**
     * 根据id 逻辑 删除讲师
     * @param id id
     * @return CommonResult
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "逻辑删除讲师")
    public CommonResult deleteTeacherById(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable(value = "id") String id) {
        return teacherService.removeById(id) ? CommonResult.ok() : CommonResult.error();
    }
}

