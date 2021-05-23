package com.tx.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tx.edu.entity.Subject;
import com.tx.edu.entity.excel.SubjectData;
import com.tx.edu.entity.subject.OneSubject;
import com.tx.edu.entity.subject.TwoSubject;
import com.tx.edu.listener.SubjectExcelListener;
import com.tx.edu.mapper.SubjectMapper;
import com.tx.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

    // 获取所有课程列表(树形)
    @Override
    public List<OneSubject> getAllSubject() {
        // 3种方式调用封装好的查询方法
        // 1. 自己注入SubjectMapper
        // 2. this(SubjectService)
        // 3. baseMapper(当前父类中已自动注入)

        // 最后返回的结果集
        List<OneSubject> result = new ArrayList<>();

        // 获取所有一级分类(parent_id = 0)
        QueryWrapper<Subject> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("parent_id", "0");
        List<Subject> oneSubjectList = this.list(wrapper1);

        // 获取所有的二级分类(parent_id != 0)
        QueryWrapper<Subject> wrapper2 = new QueryWrapper<>();
        wrapper2.ne("parent_id", "0");
        List<Subject> twoSubjectList = this.list(wrapper2);

        // 封装所有的一级分类和二级分类
        for (Subject one : oneSubjectList) {
            // 创建一个一级分类
            OneSubject oneSubject = new OneSubject();
            // 把id，title赋值给oneSubject
            BeanUtils.copyProperties(one, oneSubject);
            // children属性
            List<TwoSubject> child = new ArrayList<>();
            for (Subject two : twoSubjectList) {
                if (two.getParentId().equals(oneSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(two, twoSubject);
                    // 给child赋值
                    child.add(twoSubject);
                }
            }
            oneSubject.setChildren(child);
            result.add(oneSubject);
        }
        return result;
    }
}
