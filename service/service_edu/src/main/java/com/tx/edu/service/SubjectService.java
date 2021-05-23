package com.tx.edu.service;

import com.tx.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tx.edu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author lishilong
 * @since 2021-05-23
 */
public interface SubjectService extends IService<Subject> {

    // 添加课程分类
    void saveSubject(MultipartFile file,SubjectService subjectService);

    // 获取所有课程列表(树形)
    List<OneSubject> getAllSubject();
}
