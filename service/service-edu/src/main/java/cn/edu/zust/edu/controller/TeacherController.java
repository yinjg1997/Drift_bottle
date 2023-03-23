package cn.edu.zust.edu.controller;

import cn.edu.zust.common.result.ResponseCode;
import cn.edu.zust.common.result.ResultMode;
import cn.edu.zust.edu.entity.EduTeacher;
import cn.edu.zust.edu.entity.query.TeacherQuery;
import cn.edu.zust.edu.service.EduTeacherService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Dragon Wen
 * @since 2020-02-27
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class TeacherController {

    @Autowired
    private EduTeacherService teacherService;

    /**
     * 获取所有讲师列表
     *
     * @return
     */
    @ApiOperation(value = "获取所有讲师列表")
    @GetMapping("list")
    public ResultMode list(){
        List<EduTeacher> list = teacherService.findAll();
        return ResultMode.ok(ResponseCode.SUCCESS, list);
    }


    /**
     * 讲师分页列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "讲师分页列表")
    @GetMapping("/{pageNum}/{pageSize}")
    public ResultMode selectTeacherByPage(
            @ApiParam(name = "pageNum", value = "当前页", required = true)
            @PathVariable(value = "pageNum") Integer  pageNum,
            @ApiParam(name = "pageSize", value = "每页显示记录数", required = true)
            @PathVariable(value = "pageSize") Integer  pageSize) {

        PageInfo<EduTeacher> teacherList = teacherService.selectByPage(pageNum, pageSize);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", teacherList.getTotal());
        map.put("rows", teacherList.getList());
        return ResultMode.ok(ResponseCode.SUCCESS, map);
    }

    /**
     * 根据ID删除讲师
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("/delete")
    public ResultMode deleteTeacherById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @RequestParam String id){
        teacherService.removeById(id);
        return ResultMode.ok(ResponseCode.SUCCESS, null);
    }


    /**
     * 根据讲师条件分页查询
     * @param pageNum
     * @param pageSize
     * @param teacherQuery
     * @return
     */
    @ApiOperation(value = "根据讲师条件分页查询")
    @PostMapping("/list/{pageNum}/{pageSize}")
    public ResultMode selectTeacherByQuery(
            @ApiParam(name = "pageNum", value = "当前页", required = true)
            @PathVariable(value = "pageNum") Integer  pageNum,
            @ApiParam(name = "pageSize", value = "每页显示记录数", required = true)
            @PathVariable(value = "pageSize") Integer  pageSize,
            @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
            @RequestBody TeacherQuery teacherQuery) {


        PageInfo<EduTeacher> teacherList = teacherService.selectTeacherByQuery(pageNum, pageSize, teacherQuery);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", teacherList.getTotal());
        map.put("list", teacherList.getList());
        return ResultMode.ok(ResponseCode.SUCCESS, map);
    }

    /**
     * 新增讲师
     * @param teacher
     * @return
     */
    @ApiOperation(value = "新增讲师")
    @PostMapping("save")
    public ResultMode saveTeacher(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher){
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        teacher.setId(id);
        teacher.setIsDeleted(false);
        teacherService.saveTeacher(teacher);
        return ResultMode.ok(ResponseCode.SUCCESS, null);
    }

    /**
     * 根据ID查询讲师
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("/info")
    public ResultMode selectTeacherById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @RequestParam String id){

        EduTeacher teacher = teacherService.getById(id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("teacher", teacher);
        return ResultMode.ok(ResponseCode.SUCCESS, map);
    }

    /**
     * 根据讲师ID修改讲师信息
     * @param teacher
     * @return
     */
    @ApiOperation(value = "根据讲师ID修改讲师信息")
    @PostMapping("update")
    public ResultMode updateTeacherById(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher){
        teacherService.updateById(teacher);
        return ResultMode.ok(ResponseCode.SUCCESS, null);
    }



}

