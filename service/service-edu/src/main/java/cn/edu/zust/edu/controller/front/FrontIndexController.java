package cn.edu.zust.edu.controller.front;

import cn.edu.zust.common.result.ResultMode;
import cn.edu.zust.edu.entity.Course;
import cn.edu.zust.edu.entity.EduTeacher;
import cn.edu.zust.edu.service.CourseService;
import cn.edu.zust.edu.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


@Api(tags = "前台系统首页API")
@RestController
@RequestMapping("/eduservice/frontindex")
public class FrontIndexController {

    @Autowired
    private CourseService eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;

    //查询前8门热门课程以及查询前4名讲师（按照添加时间）
    @ApiOperation(value = "首页热门课程以及讲师展示")
    @GetMapping("index")
    public ResultMode index() {
        //查询前8门热门课程
        List<Course> courseList = eduCourseService.getCourses();

        //查询前4名讲师
        List<EduTeacher> teacherList = eduTeacherService.getTeachers();

        HashMap<String, Object> map = new HashMap<>();
        map.put("courseList", courseList);
        map.put("teacherList", teacherList);

        return ResultMode.ok(map);
    }

}
