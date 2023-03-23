package cn.edu.zust.edu.service.impl;

import cn.edu.zust.common.exception.BusinessException;
import cn.edu.zust.common.result.ResponseCode;
import cn.edu.zust.common.vo.FrontCourseDetailVo;
import cn.edu.zust.edu.entity.Course;
import cn.edu.zust.edu.entity.CourseDescription;
import cn.edu.zust.edu.entity.query.CourseQuery;
import cn.edu.zust.edu.entity.vo.CourseVo;
import cn.edu.zust.edu.entity.vo.FrontCourseVo;
import cn.edu.zust.edu.enums.CourseStatusEnum;
import cn.edu.zust.edu.mapper.CourseMapper;
import cn.edu.zust.edu.service.ChapterService;
import cn.edu.zust.edu.service.CourseDescriptionService;
import cn.edu.zust.edu.service.CourseService;
import cn.edu.zust.edu.service.VideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-06-28
 */
@Service
@Transactional
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {


    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;
    @Override
    public String saveCourseInfo(CourseVo courseVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseVo, course);
        course.setGmtCreate(LocalDateTime.now());
        course.setGmtModified(LocalDateTime.now());
        //1、添加课程
        int i = baseMapper.insert(course);
        if (i <= 0)
            // 添加失败
            throw new BusinessException(99999, "课程添加失败,失败方法: saveCourseInfo()");

        /*
            2、获取课程的ID
            mybatis-plus 会将插入的uid反射到 course 中
         */
        String courseId = course.getId();
        //3、添加课程描述
        CourseDescription courseDesc = new CourseDescription();
        courseDesc.setDescription(courseVo.getDescription());
        courseDesc.setId(courseId);
        courseDesc.setGmtCreate(LocalDateTime.now());
        courseDesc.setGmtModified(LocalDateTime.now());
        courseDescriptionService.save(courseDesc);
        return courseId;
    }

    @Override
    public CourseVo getCourseVoById(String id) {
        CourseVo courseVo = new CourseVo();
        //获取课程
        Course eduCourse = baseMapper.selectById(id);
        if(eduCourse == null){
            throw new BusinessException(20001,"此课程不存在");
        }
        BeanUtils.copyProperties(eduCourse, courseVo);

        CourseDescription eduCourseDescription = courseDescriptionService.getById(id);
        if(eduCourseDescription != null){
            courseVo.setDescription(eduCourseDescription.getDescription());
        }
        return courseVo;
    }

    @Override
    public boolean updateCourseVo(CourseVo courseVo) {

        //判断ID是否存在、如果存在直接放回false
        if(StringUtils.isEmpty(courseVo.getId())){
            throw new BusinessException(20001,"没有课程编号，修改失败");
        }
        Course course = new Course();
        BeanUtils.copyProperties(courseVo, course);
        int i = baseMapper.updateById(course);
        if(i <= 0 ){
            throw new BusinessException(20001,"修改课程信息失败");
        }
        //获取course—ID
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseVo.getDescription());
        courseDescription.setId(course.getId());
        //设置课程描述的ID
        boolean b = courseDescriptionService.updateById(courseDescription);
        return b;
    }
    @Override
    public void getPageList(Page<Course> pageParam, CourseQuery courseQuery) {

        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        if(courseQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("subject_id", subjectId);
        }
        baseMapper.selectPage(pageParam, queryWrapper);

    }

    @Override
    public boolean deleteCourseById(String id) {
        // TODO: 根据id删除所有视频
        // videoService.deleteByCourseId(id);
        // 根据id删除所有章节
        chapterService.deleteChapterByCourseId(id);

        //删除课程描述
        boolean b = courseDescriptionService.removeById(id);
        if(!b){// 如果描述没有删除成功直接返回
            return false;
        }
        Integer result = baseMapper.deleteById(id);

        return result == 1 ;

    }

    @Override
    public Boolean updateStatusById(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus("Normal");
        int update = baseMapper.updateById(course);
        return update > 0;
    }

    @Override
    public Map<String, Object> getMapById(String id) {
        Map<String, Object> map = baseMapper.getMapById(id);
        return map;
    }

    @Override
    public List<Course> selectCourseByTeacherIdFront(String teacherId) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<Course>();

        queryWrapper.eq("teacher_id", teacherId);
        //按照最后更新时间倒序排列
        queryWrapper.orderByDesc("gmt_modified");

        List<Course> courses = baseMapper.selectList(queryWrapper);
        return courses;
    }

    @Override
    public Map<String, Object> pageListFront(Page<Course> pageParam) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_modified");

        baseMapper.selectPage(pageParam, queryWrapper);

        List<Course> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public Map<String, Object> getMapByIdFront(String id) {
        Map<String, Object> map = baseMapper.getMapByIdFront(id);
        return map;
    }

    @Override
    public void updateCourseViewCount(String courseId) {

        Course course = baseMapper.selectById(courseId);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);
    }

    @Override
    @Cacheable(value = "course", key = "'getCourses'")
    public List<Course> getCourses() {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        wrapper.eq("status", CourseStatusEnum.NORMAL.getStatus());//已经发布的课程才可以在前台系统查询的到
        wrapper.last("limit 8");
        List<Course> courses = baseMapper.selectList(wrapper);
        return courses;
    }

    @Override
    public Map<String, Object> frontPageCourse(Long current, Long size, FrontCourseVo frontCourseVo) {
        Page<Course> pageCourse = new Page<>(current,size);
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，不为空拼接
        if(!StringUtils.isEmpty(frontCourseVo.getSubjectParentId())) {//一级分类
            wrapper.eq("subject_parent_id",frontCourseVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(frontCourseVo.getSubjectId())) {//二级分类
            wrapper.eq("subject_id",frontCourseVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(frontCourseVo.getBuyCountSort())) {//关注度===销售数量
            wrapper.orderByDesc("buy_count");//业务上面使用销售数量来表示关注度，销售数量多的课程表示其关注度高
        }
        if (!StringUtils.isEmpty(frontCourseVo.getGmtCreateSort())) {//最新
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(frontCourseVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }
        wrapper.eq("status", CourseStatusEnum.NORMAL.getStatus());//已经发布的课程才可以在前台系统查询的到
        baseMapper.selectPage(pageCourse, wrapper);

        current = pageCourse.getCurrent();
        size = pageCourse.getSize();
        long pages = pageCourse.getPages();
        long total = pageCourse.getTotal();
        List<Course> records = pageCourse.getRecords();
        boolean hasNext = pageCourse.hasNext();//当前页是否有下一页
        boolean hasPrevious = pageCourse.hasPrevious();//当前页是否有上一页

        //将分页模型里面的属性全部取出来，放到map集合里面，用于返回给前端
        Map<String, Object> result = new HashMap<>();
        result.put("current", current);
        result.put("size", size);
        result.put("pages", pages);
        result.put("total", total);
        result.put("records", records);
        result.put("hasNext", hasNext);
        result.put("hasPrevious", hasPrevious);

        return result;
    }

    @Override
    public FrontCourseDetailVo getBaseCourseInfo(String courseId) {
        FrontCourseDetailVo frontCourseDetailVo = baseMapper.getBaseCourseInfo(courseId);
        return frontCourseDetailVo;
    }
}
