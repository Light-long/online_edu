package com.tx.edu.service;

import com.tx.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tx.edu.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lishilong
 * @since 2021-05-23
 */
public interface CourseService extends IService<Course> {

    // 添加课程相关信息(返回课程id)
    String addCourseInfo(CourseInfoVo courseInfoVo);
}
