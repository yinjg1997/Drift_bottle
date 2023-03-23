package cn.edu.zust.edu.controller.front;


import cn.edu.zust.common.result.ResultMode;
import cn.edu.zust.common.vo.FrontCourseDetailVo;
import cn.edu.zust.edu.entity.vo.ChapterVo;
import cn.edu.zust.edu.entity.vo.FrontCourseVo;
import cn.edu.zust.edu.service.ChapterService;
import cn.edu.zust.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(tags = "前台系统课程API")
@RestController
@RequestMapping("/eduservice/frontcourse")
@CrossOrigin
public class FrontCourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    //分页查询课程数据
    @ApiOperation(value = "分页查询课程数据")
    //current表示当前页；size表示每页记录数
    @PostMapping("getFrontCourseList/{current}/{size}")
    public ResultMode frontPageCourse(@ApiParam(name = "current", value = "当前页") @PathVariable("current") Long current,
                                      @ApiParam(name = "size", value = "每页记录数") @PathVariable("size") Long size,
                                      @RequestBody(required = false) FrontCourseVo searchObj) {
        Map<String, Object> result = courseService.frontPageCourse(current, size, searchObj);


        return  ResultMode.ok(result);
    }

    //查询课程详情
    @ApiOperation(value = "查询课程详情信息")
    @GetMapping("getFrontCourseInfo/{courseId}")
    public ResultMode frontGetCourseInfo(@ApiParam(name = "courseId", value = "课程ID", required = true) @PathVariable("courseId") String courseId){
        //根据课程ID查询出前端所需的课程详情
        FrontCourseDetailVo frontCourseDetailVo = courseService.getBaseCourseInfo(courseId);

        //根据课程ID来查询课程下面的课程章节信息和课程小节信息
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        HashMap<Object, Object> res = new HashMap<>();
        res.put("frontCourseDetailVo", frontCourseDetailVo);
        res.put("chapterVideoList", chapterVideoList);
        return ResultMode.ok(res);
    }

    //查询课程详情
    @ApiOperation(value = "查询课程详情信息,用于订单信息")
    @PostMapping("getFrontCourseInfoForOrder/{courseId}")
    public FrontCourseDetailVo frontGetCourseInfoForOder(@ApiParam(name = "courseId", value = "课程ID", required = true)
                                                             @PathVariable("courseId") String courseId){
        //根据课程ID查询出前端所需的课程详情
        return courseService.getBaseCourseInfo(courseId);
    }

}
