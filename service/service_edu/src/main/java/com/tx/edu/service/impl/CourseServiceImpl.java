package com.tx.edu.service.impl;

import com.tx.base.exception.MyException;
import com.tx.edu.entity.Course;
import com.tx.edu.entity.CourseDescription;
import com.tx.edu.entity.vo.CourseInfoVo;
import com.tx.edu.mapper.CourseMapper;
import com.tx.edu.service.CourseDescriptionService;
import com.tx.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lishilong
 * @since 2021-05-23
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    // 注入课程描述service，需要存储课程描述信息
    @Autowired
    private CourseDescriptionService courseDescriptionService;

    // 添加课程相关信息(返回课程id)
    @Override
    @Transactional
    public String addCourseInfo(CourseInfoVo courseInfoVo) {
        // 获取course信息
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        // 存储course
        int row = baseMapper.insert(course);
        // 影响行数==0，存储不成功
        if (row == 0) {
            throw new MyException(20001,"存储课程信息失败");
        }
        // 获取course.id
        String id = course.getId();
        // 获取courseDescription信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(id).setDescription(courseInfoVo.getDescription());
        // 存courseDescription
        if (!courseDescriptionService.save(courseDescription)) {
            throw new MyException(20001,"存储课程描述信息失败");
        }
        // 返回课程id
        return id;
    }
}
