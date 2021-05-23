package com.tx.edu.controller;


import com.tx.edu.entity.vo.CourseInfoVo;
import com.tx.edu.service.CourseService;
import com.tx.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lishilong
 * @since 2021-05-23
 */
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
@Api(description = "课程管理")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 添加课程相关信息
     * @param courseInfoVo 封装的课程info
     * @return 课程id
     */
    @PostMapping("/addCourseInfo")
    @ApiOperation("添加课程相关信息")
    public CommonResult addCourseInfo(@ApiParam(name = "courseInfoVo", value = "课程相关信息", required = true)
                                          @RequestBody CourseInfoVo courseInfoVo) {
        String courseId = courseService.addCourseInfo(courseInfoVo);
        return CommonResult.ok().data("courseId",courseId);
    }

}

