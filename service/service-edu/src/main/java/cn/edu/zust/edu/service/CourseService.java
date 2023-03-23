package cn.edu.zust.edu.service;

import cn.edu.zust.common.vo.FrontCourseDetailVo;
import cn.edu.zust.edu.entity.Course;
import cn.edu.zust.edu.entity.query.CourseQuery;
import cn.edu.zust.edu.entity.vo.CourseVo;
import cn.edu.zust.edu.entity.vo.FrontCourseVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-06-28
 */
public interface CourseService extends IService<Course> {

    /**
     * 保存课程基本信息
     * @param courseVo
     * @return
     */
    String saveCourseInfo(CourseVo courseVo);

    /**
     *根据课程ID查询课程基本信息
     * @param id
     * @return
     */
    CourseVo getCourseVoById(String id);

    /**
     * 修改课程基本信息和描述
     * @param courseVo
     * @return
     */
    boolean updateCourseVo(CourseVo courseVo);

    /**
     * 根据条件分页查询课程列表
     * @param pageParam
     * @param courseQuery
     */
    void getPageList(Page<Course> pageParam, CourseQuery courseQuery);

    /**
     * 据课程ID删除课程信息
     * @param id
     * @return
     */
    boolean deleteCourseById(String id);

    /**
     * 根据课程Id修改课程状态
     * @param id
     * @return
     */
    Boolean updateStatusById(String id);

    /**
     * 根据课程Id查询课程Map对象
     * @param id
     * @return
     */
    Map<String, Object> getMapById(String id);

    /**
     * 根据讲师id查询当前讲师的课程列表
     * @param id
     * @return
     */
    List<Course> selectCourseByTeacherIdFront(String id);

    /**
     * 前端获取分页课程列表
     * @param pageParam
     * @return
     */
    Map<String, Object> pageListFront(Page<Course> pageParam);

    /**
     * 前端根据课程Id查询课程Map对象
     * @param id
     * @return
     */
    Map<String, Object> getMapByIdFront(String id);

    /**
     * 更新课程浏览数
     * @param courseId
     */
    void updateCourseViewCount(String courseId);

    /**
     * 查询前8门热门课程
     * @return
     */
    List<Course> getCourses();

    /**
     * 分页查询课程数据
     * @param current
     * @param size
     * @param frontCourseVo
     * @return
     */
    Map<String, Object> frontPageCourse(Long current, Long size, FrontCourseVo frontCourseVo);

    /**
     * 根据课程ID查询出前端所需的课程详情
     * @param courseId
     * @return
     */
    FrontCourseDetailVo getBaseCourseInfo(String courseId);
}
