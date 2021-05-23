package com.tx.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.tx.edu.entity.Subject;
import com.tx.edu.entity.excel.SubjectData;
import com.tx.edu.listener.SubjectExcelListener;
import com.tx.edu.mapper.SubjectMapper;
import com.tx.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author lishilong
 * @since 2021-05-23
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    // 添加课程分类
    @Override
    public void saveSubject(MultipartFile file,SubjectService subjectService) {
        try {
            // 获取文件输入流
            InputStream inputStream = file.getInputStream();
            // 读excel文件
            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
