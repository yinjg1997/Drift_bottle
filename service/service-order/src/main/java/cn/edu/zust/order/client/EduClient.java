package cn.edu.zust.order.client;

import cn.edu.zust.common.vo.FrontCourseDetailVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Component
@FeignClient(name = "service-edu")
public interface EduClient {

    /**
     * 根据课程ID查询课程信息
     * @param courseId
     * @return
     */
    @PostMapping("/eduservice/frontcourse/getFrontCourseInfoForOrder/{courseId}")
    FrontCourseDetailVo frontGetCourseInfoForOder(@PathVariable(name = "courseId") String courseId);

}
