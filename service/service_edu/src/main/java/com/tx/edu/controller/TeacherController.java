package com.tx.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tx.edu.entity.Teacher;
import com.tx.edu.entity.vo.TeacherQuery;
import com.tx.edu.service.TeacherService;
import com.tx.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author lishilong
 * @since 2021-05-19
 */
@RestController
@RequestMapping("/edu/teacher")
@Api(description = "讲师管理")
@CrossOrigin
public class TeacherController {

    // 注入TeacherService,这个类被Mybatis-Plus封装过，底层调用了Mapper层
    @Autowired
    private TeacherService teacherService;

    /**
     * 查询所有Teacher
     * @return CommonResult
     */
    @GetMapping("/findAll")
    @ApiOperation(value = "查询所有讲师")
    public CommonResult findAllTeacher() {
        List<Teacher> teacherList = teacherService.list(null);
        // 构建一个map数据集
        // Map<String, Object> data = new HashMap<>();
        // data.put("teacherList",teacherList);

        // 测试全局异常
//        try {
//            int i = 10/0;
//        } catch (Exception e) {
//            throw new MyException().setCode(400).setMsg("自定义异常");
//        }

        return CommonResult.ok().data("teacherList",teacherList);
    }

    /**
     * 根据id 逻辑 删除讲师
     * @param id id
     * @return CommonResult
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "逻辑删除讲师")
    public CommonResult deleteTeacherById(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable(value = "id") String id) {
        return teacherService.removeById(id) ? CommonResult.ok() : CommonResult.error();
    }

    /**
     * 分页查询
     * @param current 当前页码
     * @param size 每页记录数
     * @return 当前页数据
     */
    @GetMapping("/pageTeacher/{current}/{size}")
    @ApiOperation(value = "分页查询讲师列表")
    public CommonResult pageTeacherList(@ApiParam(name = "current", value = "当前页码", required = true) @PathVariable(value = "current") Long current,
                                        @ApiParam(name = "size", value = "每页记录数", required = true) @PathVariable(value = "size") Long size) {
        // 创建Page对象,传递 当前页码和每页记录数
        Page<Teacher> page = new Page<>(current,size);
        // 调用分页查询
        teacherService.page(page, null);
        // 所有的数据都封装在了Page对象中
        long total = page.getTotal();
        List<Teacher> records = page.getRecords();
        // 返回结果,将total和records封装在一个map中
        Map<String, Object> data = new HashMap<>();
        data.put("total",total);
        data.put("records",records);
        return CommonResult.ok().data(data);
    }

    /**
     * 分页条件查询
     * @param current 当前页
     * @param size 每页记录数
     * @param teacherQuery 构建的条件查询对象
     * @return 满足条件的当前页的记录
     */
    @PostMapping("/pageTeacherCondition/{current}/{size}")
    @ApiOperation(value = "组合条件分页查询")
    public CommonResult pageTeacherCondition(@ApiParam(name = "current", value = "当前页码", required = true) @PathVariable(value = "current") Long current,
                                             @ApiParam(name = "size", value = "每页记录数", required = true) @PathVariable(value = "size") Long size,
                                             @ApiParam(name = "teacherQuery", value = "条件查询对象", required = false) @RequestBody(required = false)TeacherQuery teacherQuery) {
        // 创建page对象
        Page<Teacher> page = new Page<>(current,size);
        // 创建条件查询对象
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        // 构建条件
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create",end);
        }

        // 按照更新顺序降序排列
        wrapper.orderByDesc("gmt_modified");
        // 条件分页查询
        teacherService.page(page,wrapper);
        // 所有数据都封装在了page对象中
        long total = page.getTotal();
        List<Teacher> records = page.getRecords();
        return CommonResult.ok().data("total",total).data("records",records);
    }

    /**
     * 添加讲师，不用传 id(MP自动生成)，gmtCreate和gmtModified(自动填充)
     * @param teacher teacher对象的json字符串
     * @return 成功or失败
     */
    @PostMapping("/addTeacher")
    @ApiOperation(value = "添加讲师")
    public CommonResult addTeacher(@ApiParam(name = "teacher", value = "讲师对象", required = true)
                                   @RequestBody(required = true) Teacher teacher) {
        // 添加
        return teacherService.save(teacher) ? CommonResult.ok() : CommonResult.error();
    }

    /**
     * 根据id查询讲师
     * @param id 讲师id
     * @return 讲师对象
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询讲师")
    public CommonResult getTeacherById(@ApiParam(name = "id", value = "讲师id", required = true)
                                       @PathVariable(value = "id") String id) {
        Teacher teacher = teacherService.getById(id);
        return teacher==null ? CommonResult.error().message("id不存在") : CommonResult.ok().data("teacher",teacher);
    }

    /**
     * 更新讲师
     * @param id 需要更新的讲师的id
     * @param teacher 讲师的信息，id可以不传
     * @return 更新是否成功
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "更新讲师")
    public CommonResult updateTeacher(@ApiParam(name = "id", value = "讲师id", required = true)
                                          @PathVariable(value = "id") String id,
                                      @ApiParam(name = "teacher", value = "更新的讲师", required = true)
                                          @RequestBody(required = true) Teacher teacher) {
        teacher.setId(id);
        return teacherService.updateById(teacher) ? CommonResult.ok() : CommonResult.error();
    }
}

