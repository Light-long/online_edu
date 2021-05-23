package com.tx.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tx.base.exception.MyException;
import com.tx.edu.entity.Subject;
import com.tx.edu.entity.excel.SubjectData;
import com.tx.edu.service.SubjectService;
import lombok.Data;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    // 因为SubjectExcelListener不能被Spring管理，没办法自动注入SubjectService
    // 因而不能实现数据库操作
    // 我们需要通过构造器注入
    public SubjectService subjectService;
    public SubjectExcelListener() {}
    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    // 读取每行数据
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        // 判断excel是否为空
        if (subjectData == null) {
            throw new MyException(20001,"excel为空");
        }
        // 一行一行读取，第一个值为一级分类，第二个值为二级分类
        // 判断是否存在该一级分类
        Subject oneSubject = this.isExistOneSubject(subjectData.getOneSubjectName());
        // 如果不存在，则添加
        if (oneSubject == null) {
            oneSubject = new Subject();
            // 设置属性
            oneSubject.setTitle(subjectData.getOneSubjectName()).setParentId("0");
            // 保存
            subjectService.save(oneSubject);
        }

        // 获取该一级分类的id,是之后存的二级分类的parent_id
        String pid = oneSubject.getId();
        // 判断是否存在该二级分类
        Subject twoSubject = this.isExistTwoSubject(subjectData.getTwoSubjectName(), pid);
        // 不存在，添加
        if (twoSubject == null) {
            twoSubject = new Subject();
            // 设置二级分类的属性
            twoSubject.setTitle(subjectData.getTwoSubjectName()).setParentId(pid);
            subjectService.save(twoSubject);
        }
    }

    // 判断该一级分类是否存在,判断名称是否存在
    // 判断一级分类时，pid = 0
    public Subject isExistOneSubject(String name) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        return subjectService.getOne(wrapper);
    }

    // 判断二级分类是否存在，名称是否存在
    // 判断二级分类是，pid = 传过来的pid
    public Subject isExistTwoSubject(String name, String pid) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        return subjectService.getOne(wrapper);
    }

    // 读操作完成以后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
