package cn.edu.zust.edu.controller.front;


import cn.edu.zust.common.result.ResultMode;
import cn.edu.zust.edu.entity.Course;
import cn.edu.zust.edu.entity.EduTeacher;
import cn.edu.zust.edu.service.CourseService;
import cn.edu.zust.edu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月27日
 */
@Api(tags = "前台系统讲师API")
@RestController
@RequestMapping("/eduservice/frontteacher")
@CrossOrigin
public class FrontTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private CourseService courseService;

    //分页查询讲师数据
    @ApiOperation(value = "分页查询讲师列表")
    //current表示当前页；size表示每页记录数
    @GetMapping("frontPageTeacher/{current}/{size}")
    public ResultMode frontPageTeacher(@ApiParam(name = "current", value = "当前页") @PathVariable("current") Long current,
                                       @ApiParam(name = "size", value = "每页记录数") @PathVariable("size") Long size){
//        IPage<Map<String, Object>> result = eduTeacherService.pageTeacher(current,size);
        Map<String, Object> result = teacherService.frontPageTeacher(current, size);
        HashMap<Object, Map<String, Object>> map = new HashMap<>();
        map.put("result", result);
        return ResultMode.ok(map);
    }

    //根据讲师ID查询讲师
    @ApiOperation(value = "根据讲师ID查询讲师")
    @GetMapping("frontGetTeacherById/{teacherId}")
    public ResultMode frontGetTeacherById(@ApiParam(name = "teacherId", value = "讲师ID", required = true) @PathVariable("teacherId") String teacherId){
        //根据讲师ID查询讲师
        EduTeacher eduTeacher = teacherService.getById(teacherId);

        //根据讲师ID查询讲师所讲课程
        List<Course> courses = courseService.selectCourseByTeacherIdFront(teacherId);


        HashMap<Object, Object> map = new HashMap<>();
        map.put("teacher", eduTeacher);
        map.put("courseList", courses);

        return ResultMode.ok(map);
    }

}
