package com.auguigu.eduservice.controller;


import com.auguigu.commonutils.R;
import com.auguigu.eduservice.entity.EduTeacher;
import com.auguigu.eduservice.entity.vo.TeacherQuery;
import com.auguigu.eduservice.service.EduTeacherService;
import com.auguigu.eduservice.service.TeacherService;
import com.auguigu.servicebase.handler.GuliException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-10
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private TeacherService teacherService;

    /**
     * 查询所有
     *
     * @return
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items", list);
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@PathVariable String id) {
        return R.ok();
    }

    @ApiOperation(value = "分页查询讲师")
    @GetMapping("pageTeacher/{page}/{limit}")
    public R getPageList(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable long page, @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable long limit) {
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        eduTeacherService.page(teacherPage, null);

        try{
            int i=10/0;
        }catch (Exception e){
            throw new GuliException(20001,"执行了自定义异常");
        }

        List<EduTeacher> records = teacherPage.getRecords();
        long total = teacherPage.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "条件查询讲师")
    @PostMapping("pageTeacherCondition/{page}/{limit}")
    public R getPageListCondition(@ApiParam(name = "page", value = "当前页码", required = true)
                                  @PathVariable long page, @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {

        Page<EduTeacher> teacherPage = new Page<>(page, limit);

        teacherService.pageQuery(teacherPage, teacherQuery);
        List<EduTeacher> records = teacherPage.getRecords();
        long total = teacherPage.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping
    public R save(@ApiParam(name = "teacher", value = "讲师对象", required = true) @RequestBody EduTeacher teacher) {
        Boolean save = teacherService.save(teacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getTeacherById(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {
        return R.ok().data("items", eduTeacherService.getById(id));
    }

    @ApiOperation(value = "根据ID修改讲师")
    @PutMapping("updateTeacherById")
    public R updateById(@ApiParam(name = "teacher", value = "讲师对象", required = true) @RequestBody EduTeacher teacher) {
        Boolean update = teacherService.updateById(teacher);
        if(update){
            return R.ok();
        }else {
            return R.error();
        }

    }
}

