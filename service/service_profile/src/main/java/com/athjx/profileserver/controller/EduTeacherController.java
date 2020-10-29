package com.athjx.profileserver.controller;


import com.athjx.commonutils.entity.mpWrapper.BaseQueryWrapper;
import com.athjx.commonutils.R;
import com.athjx.profileserver.entity.EduTeacher;
import com.athjx.profileserver.service.EduTeacherService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-28
 */
@RestController
@RequestMapping("/profileserver/edu-teacher")
@Api(tags = "讲师列表")
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;
    /**
     * 添加
     * @param eduTeacher
     * @return R
     * @throws Exception
     */
    @ApiOperation(value = "|EduTeacher|新增")
    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
    //@RequiresPermissions("business:feedback:save")
    public R  create(@RequestBody @Validated EduTeacher eduTeacher) throws Exception {
        boolean save = eduTeacherService.save(eduTeacher);
        return R.ok();
    }
    /**
     * 分页查询
     * @param page
     * @return R
     * @throws Exception
     */
    @ApiOperation(value = "|EduTeacher|分页查询")
    @PostMapping(value = "/page", consumes = APPLICATION_JSON_UTF8_VALUE)
    public R page(@RequestBody BaseQueryWrapper<EduTeacher> page) throws Exception {
        Page<EduTeacher> objectPage = new Page<EduTeacher>(1,10);
        IPage<EduTeacher> eduTeacherIPage = eduTeacherService.page(objectPage, page.getQueryWrapper());
        int total = eduTeacherService.count(page.getQueryWrapper());
        return R.ok().data("records", eduTeacherIPage.getRecords()).data("total", total);
    }
    @ApiOperation(value = "根据id获取数据")
    @GetMapping(value = "getById/{id}")
    public R getById(@PathVariable String id){
        EduTeacher byId = eduTeacherService.getById(id);
        return R.ok().data("byId",byId);
    }
    @ApiOperation(value = "删除")
    @DeleteMapping("")
    public R delete (@RequestBody List<String> ids){
        boolean remove = eduTeacherService.removeByIds(ids);
        return R.ok().success(remove);
    }

    @ApiOperation(value = "修改")
    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
    public R update (@RequestBody EduTeacher eduTeacher){
        boolean update = eduTeacherService.updateById(eduTeacher);
        return R.ok().success(update);
    }
    @ApiOperation(value = "list查询")
    @PostMapping(value = "/list",consumes = APPLICATION_JSON_UTF8_VALUE)
    public R list (@RequestBody BaseQueryWrapper<EduTeacher> eduTeacher){
        List<EduTeacher> list = eduTeacherService.list(eduTeacher.getQueryWrapper());
        return R.ok().data("list",list);
    }




}

