package cn.edu.zust.edu.controller;


import cn.edu.zust.common.result.ResponseCode;
import cn.edu.zust.common.result.ResultMode;
import cn.edu.zust.edu.entity.Course;
import cn.edu.zust.edu.entity.query.CourseQuery;
import cn.edu.zust.edu.entity.vo.CourseVo;
import cn.edu.zust.edu.service.CourseDescriptionService;
import cn.edu.zust.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-06-28
 */
@Api(tags = "课程基本信息管理")
@RestController
@RequestMapping("/eduservice/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    /**
     * 保存基本信息
     */
    @ApiOperation(value = "保存基本信息")
    @PostMapping("addCourseInfo")
    public ResultMode save(@RequestBody CourseVo courseVo){
        System.out.println(courseVo);
        if (courseVo == null) {
            return ResultMode.error(ResponseCode.ERROR.getCode(), "课程基本信息不能为空", null);
        }
        String courseId = courseService.saveCourseInfo(courseVo);
        HashMap<String, String> map = new HashMap<>();
        map.put("courseId", courseId);
        return ResultMode.ok(map);
    }

    /**
     * 根据课程ID获取课程基本信息和描述
     */
    @ApiOperation(value = "根据课程ID获取课程基本信息和描述")
    @GetMapping("getCourseInfo/{id}")
    public ResultMode getCourseVoById(@PathVariable(name = "id") String id){
        CourseVo vo = courseService.getCourseVoById(id);
        HashMap<String, CourseVo> map = new HashMap<>();
        map.put("courseInfo", vo);
        return ResultMode.ok(map);
    }

    /**
     * 修改课程基本信息和描述
     */
    @ApiOperation(value = "修改课程基本信息和描述")
    @PostMapping("updateCourseInfo")
    public ResultMode updateCourseVo(@RequestBody CourseVo courseVo){
        boolean flag = courseService.updateCourseVo(courseVo);
        if(flag){
            return ResultMode.ok();
        } else {
            return ResultMode.error();
        }
    }

    /**
     * 根据条件分页查询课程列表

     * @param courseQuery
     * @return
     */
    @ApiOperation(value = "分页课程列表")
    @PostMapping("list/{pageNum}/{pageSize}")
    public ResultMode getPageList(
            @ApiParam(name = "pageNum", value = "当前页码", required = true)
            @PathVariable Integer pageNum,
            @ApiParam(name = "pageSize" ,value = "每页记录数", required = true)
            @PathVariable Integer pageSize,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody CourseQuery courseQuery){

        Page<Course> pageParam = new Page<>(pageNum, pageSize);
        courseService.getPageList(pageParam, courseQuery);
        List<Course> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", records);
        return ResultMode.ok(map);

    }

    /**
     *  根据课程ID删除课程信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID删除课程")
    @DeleteMapping("{id}")
    public ResultMode removeById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable(name = "id") String id){

        boolean result = courseService.deleteCourseById(id);
        if(result){
            return ResultMode.ok();
        }else{
            return ResultMode.error(ResponseCode.ERROR.getCode(), "删除失败",null);
        }
    }

    /**
     * 根据课程Id查询课程Map对象
     * 这个Map对象实际上是 CoursePublishVo 对象, 只不过这里没用
     * @param id
     * @return
     */
    @ApiOperation(value = "")
    @GetMapping("getPublishCourseInfo/{id}")
    public ResultMode getCoursePublishById(@PathVariable String id){
        Map<String, Object> map = courseService.getMapById(id);
        return ResultMode.ok(map);
    }

    /**
     * 根据课程Id修改课程状态, 发布视频
     * @param id
     * @return
     */
    @ApiOperation(value = "")
    @PostMapping("/publishCourse/{id}")
    public ResultMode updateByStatusById(@PathVariable String id){
        Boolean flag = courseService.updateStatusById(id);
        if(flag){
            return ResultMode.ok();
        } else{
            return ResultMode.error();
        }
    }


}

