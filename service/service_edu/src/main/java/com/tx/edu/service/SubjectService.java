package com.tx.edu.service;

import com.tx.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

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
}
