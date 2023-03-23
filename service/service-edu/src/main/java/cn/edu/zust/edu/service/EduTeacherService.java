package cn.edu.zust.edu.service;

import cn.edu.zust.edu.entity.EduTeacher;
import cn.edu.zust.edu.entity.query.TeacherQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface EduTeacherService extends IService<EduTeacher> {


    List<EduTeacher> findAll();

    void removeById(String id);

    PageInfo<EduTeacher> selectByPage(Integer pageNum, Integer pageSize);

    PageInfo<EduTeacher> selectTeacherByQuery(Integer pageNum, Integer pageSize, TeacherQuery teacherQuery);

    void saveTeacher(EduTeacher teacher);

    EduTeacher getById(String id);



    /**
     * 查询前4名讲师
     * @return
     */
    List<EduTeacher> getTeachers();

    /**
     * 分页查询讲师数据
     * @param current
     * @param size
     * @return
     */
    Map<String, Object> frontPageTeacher(Long current,Long size);
}
