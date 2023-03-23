package cn.edu.zust.edu.mapper;

import cn.edu.zust.edu.entity.EduTeacher;
import cn.edu.zust.edu.entity.query.TeacherQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 讲师 Mapper 接口
 * </p>
 *
 * @author Dragon Wen
 * @since 2020-02-27
 */
@Mapper
public interface EduTeacherMapper extends BaseMapper<EduTeacher> {

    @Select("SELECT * FROM edu_teacher WHERE is_deleted = 0")
    List<EduTeacher> findAll();

    @Update("UPDATE edu_teacher SET is_deleted = 1 WHERE id = #{id}")
    void removeById(String id);

    @Select("<script> " +
            "SELECT * FROM edu_teacher t WHERE is_deleted = 0 " +
            "<if test=\"item != null\"> " +
                "<if test=\"item.name != null and item.name != ''\"> " +
                "   AND (t.name LIKE \"%\"#{item.name}\"%\" ) " +
                "</if>" +
                "<if test=\"item.level != null\"> " +
                "   AND t.level = #{item.level} " +
                "</if>" +
                "<if test=\"item.begin != null\"> " +
                "   AND t.gmt_create &gt;= #{item.begin} " +
                "</if>" +
                "<if test=\"item.end != null\"> " +
                "   AND t.gmt_create &lt;= #{item.end} " +
                "</if>" +
            "</if>" +
            "ORDER BY t.level " +
            "</script>")
    List<EduTeacher> findByCondition(@Param("item") TeacherQuery teacherQuery);

    @Insert("INSERT INTO edu_teacher (id, name, intro, career, level, avatar, sort, is_deleted, gmt_create, gmt_modified) " +
            "VALUES (#{id}, #{name}, #{intro}, #{career}, #{level}, #{avatar}, #{sort}, #{isDeleted}, #{gmtCreate}, #{gmtModified})")
    void saveTeacher(EduTeacher teacher);

    @Select("SELECT * FROM edu_teacher WHERE is_deleted = 0 AND id = #{id}")
    EduTeacher getById(String id);

    // @Update("UPDATE edu_teacher SET name = #{name}, intro = #{intro}, career = #{career}, level = #{level}, " +
    //         "avatar = #{avatar}, sort = #{sort}, is_deleted = #{isDeleted}, gmt_create = #{gmtCreate}, " +
    //         "gmt_modified = #{gmtModified} " +
    //         "WHERE id = #{id}")
    // int updateById(EduTeacher teacher);
}
