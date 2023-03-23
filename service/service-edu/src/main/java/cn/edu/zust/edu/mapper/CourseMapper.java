package cn.edu.zust.edu.mapper;

import cn.edu.zust.common.vo.FrontCourseDetailVo;
import cn.edu.zust.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-06-28
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    @Select("<script> " +
            "select " +
            "        et.`name` as teacherName, " +
            "        ec.id, " +
            "        ec.cover, " +
            "        ec.lesson_num as lessonNum, " +
            "        ec.title, " +
            "        CONVERT(ec.price, DECIMAL(8,2)) as price , " +
            "        es1.title as subjectLevelOne, " +
            "        es2.title as subjectLevelTwo " +
            "        from edu_course ec " +
            "        LEFT JOIN edu_teacher et on ec.teacher_id = et.id " +
            "        LEFT JOIN edu_subject es1 on ec.subject_parent_id = es1.id " +
            "        LEFT JOIN edu_subject es2 on ec.subject_id = es2.id " +
            "        where ec.id = #{id}" +
            "</script>")
    Map<String, Object> getMapById(String id);

    @Select("<script> " +
            "SELECT " +
            "        c.id, " +
            "        c.title, " +
            "        c.cover, " +
            "        CONVERT(c.price, DECIMAL(8,2)) AS price, " +
            "        c.lesson_num AS lessonNum, " +
            "        c.cover, " +
            "        c.buy_count AS buyCount, " +
            "        c.view_count AS viewCount, " +
            "        cd.description, " +
            "        t.id AS teacherId, " +
            "        t.name AS teacherName, " +
            "        t.intro, " +
            "        t.avatar, " +
            "        s1.id AS subjectLevelOneId, " +
            "        s1.title AS subjectLevelOne, " +
            "        s2.id AS subjectLevelTwoId, " +
            "        s2.title AS subjectLevelTwo " +
            "      FROM " +
            "        edu_course c " +
            "        LEFT JOIN edu_course_description cd ON c.id = cd.id " +
            "        LEFT JOIN edu_teacher t ON c.teacher_id = t.id " +
            "        LEFT JOIN edu_subject s1 ON c.subject_parent_id = s1.id " +
            "        LEFT JOIN edu_subject s2 ON c.subject_id = s2.id " +
            "      WHERE " +
            "        c.id = #{id}" +
            "</script>")
    Map<String, Object> getMapByIdFront(String id);

    @Select("<script> " +
            "SELECT " +
            "        c.id, " +
            "        c.title, " +
            "        c.cover, " +
            "        CONVERT(c.price, DECIMAL(8,2)) AS price, " +
            "        c.lesson_num AS lessonNum, " +
            "        c.cover, " +
            "        c.buy_count AS buyCount, " +
            "        c.view_count AS viewCount, " +
            "        cd.description, " +
            "        t.id AS teacherId, " +
            "        t.name AS teacherName, " +
            "        t.intro, " +
            "        t.avatar, " +
            "        s1.id AS subjectLevelOneId, " +
            "        s1.title AS subjectLevelOne, " +
            "        s2.id AS subjectLevelTwoId, " +
            "        s2.title AS subjectLevelTwo " +
            "      FROM " +
            "        edu_course c " +
            "        LEFT JOIN edu_course_description cd ON c.id = cd.id " +
            "        LEFT JOIN edu_teacher t ON c.teacher_id = t.id " +
            "        LEFT JOIN edu_subject s1 ON c.subject_parent_id = s1.id " +
            "        LEFT JOIN edu_subject s2 ON c.subject_id = s2.id " +
            "      WHERE " +
            "        c.id = #{id}" +
            "</script>")
    FrontCourseDetailVo getBaseCourseInfo(String id);
}
