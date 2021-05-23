package com.tx.edu.entity.subject;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 二级分类课程
 */
@Data
@Accessors(chain = true)
public class TwoSubject {
    private String id;
    private String title;
}
