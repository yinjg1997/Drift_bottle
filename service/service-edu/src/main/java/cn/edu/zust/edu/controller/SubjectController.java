package cn.edu.zust.edu.controller;


import cn.edu.zust.common.result.ResultMode;
import cn.edu.zust.edu.entity.Subject;
import cn.edu.zust.edu.entity.vo.OneSubject;
import cn.edu.zust.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-06-28
 */
@Api(tags = "课程科目管理")
@RestController
@Slf4j
@RequestMapping("/eduservice/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    /**
     * 导入课程分类
     * @param file
     * @return
     */
    @ApiOperation(value = "导入课程分类")
    @PostMapping("import")
    public ResultMode importSubject(MultipartFile file){
        //考虑到Excel模板中的数据不准确，所以返回多个错误信息，那么多个错误信息放在集合中
        List<String> mesList = subjectService.importExcel(file);

        if(mesList.size() ==0){
            return ResultMode.ok();
        } else {
            HashMap<String, List<String>> map = new HashMap<>();
            map.put("messageList", mesList);
            return ResultMode.error(map);
        }
    }

    /**
     * 获取课程分类的Tree
     * @return
     */
    @ApiOperation(value = "获取课程分类的Tree")
    @GetMapping("/tree")
    public ResultMode TreeSubject(){
        List<OneSubject> subjectList = subjectService.getTree();
        HashMap<String, List<OneSubject>> map = new HashMap<>();
        map.put("subjectList", subjectList);
        return ResultMode.ok(map);
    }

    /**
     * 根据ID删除课程分类
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID删除课程分类")
    @DeleteMapping("/{id}")
    public ResultMode deleteById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable  String id){
        boolean isDelete = subjectService.deleteById(id);
        if(isDelete){
            return ResultMode.ok();
        } else {
            return ResultMode.error();
        }
    }

    /**
     * 新增一级分类
     * @param subject
     * @return
     */
    @ApiOperation(value = "新增一级分类")
    @PostMapping("saveLevelOne")
    public ResultMode saveLevelOne(
            @ApiParam(name = "subject", value = "课程分类对象", required = true)
            @RequestBody Subject subject){

        boolean result = subjectService.saveLevelOne(subject);
        if(result){
            return ResultMode.ok();
        }else{
            return ResultMode.error(99999, "新增失败", null);
        }
    }

    /**
     * 新增二级分类
     * @param subject
     * @return
     */
    @ApiOperation(value = "新增二级分类")
    @PostMapping("saveLevelTwo")
    public ResultMode saveLevelTwo(
            @ApiParam(name = "subject", value = "课程分类对象", required = true)
            @RequestBody Subject subject){

        boolean result = subjectService.saveLevelTwo(subject);
        if(result){
            return ResultMode.ok();
        }else{
            return ResultMode.error(99999, "新增失败", null);
        }
    }
}

