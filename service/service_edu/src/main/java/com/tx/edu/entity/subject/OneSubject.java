package com.tx.edu.entity.subject;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 一级分类课程
 * 包括id，title，多个二级分类课程
 * 封装成一个树形结构
 */
@Data
@Accessors(chain = true)
public class OneSubject {
    private String id;
    private String title;

    List<TwoSubject> children = new ArrayList<>();
}
