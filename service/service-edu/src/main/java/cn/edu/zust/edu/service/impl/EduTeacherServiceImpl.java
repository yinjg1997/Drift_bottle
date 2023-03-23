package cn.edu.zust.edu.service.impl;

import cn.edu.zust.edu.entity.EduTeacher;
import cn.edu.zust.edu.entity.query.TeacherQuery;
import cn.edu.zust.edu.mapper.EduTeacherMapper;
import cn.edu.zust.edu.service.EduTeacherService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Autowired
    private EduTeacherMapper teacherMapper;

    @Override
    public List<EduTeacher> findAll() {
        return teacherMapper.findAll();
    }

    @Override
    public void removeById(String id) {
        teacherMapper.removeById(id);
    }

    @Override
    public PageInfo<EduTeacher> selectByPage(Integer pageNum, Integer pageSize) {
        // 紧跟着的第一个select方法会被分页,
        PageHelper.startPage(pageNum, pageSize);
        List<EduTeacher> teacherList = teacherMapper.findAll();
        /*
           PageInfo 是怎么包含分页信息的呢?
         * 分页时，findAll()实际返回的 teacherList类型 是Page<E>，
         * 如果想取出分页信息有两种办法,
         * 1.Page<Teacher> pageInfo = (Page<Teacher>)teacherList;
         * 2.PageInfo<Teacher> PageInfo = new PageInfo<>(teacherList)
         * PageInfo 包含 Page 的信息
         */
        PageInfo<EduTeacher> pageInfo = new PageInfo<>(teacherList);
        return pageInfo;
    }

    @Override
    public PageInfo<EduTeacher> selectTeacherByQuery(Integer pageNum, Integer pageSize, TeacherQuery teacherQuery) {
        PageHelper.startPage(pageNum, pageSize);
        List<EduTeacher> teacherList = teacherMapper.findByCondition(teacherQuery);
        PageInfo<EduTeacher> pageInfo = new PageInfo<>(teacherList);
        return pageInfo;
    }

    @Override
    public void saveTeacher(EduTeacher teacher) {
        teacherMapper.saveTeacher(teacher);
    }

    @Override
    public EduTeacher getById(String id) {
       return teacherMapper.getById(id);
    }

    @Override
    @Cacheable(value = "teacher", key = "'getTeachers'")
    public List<EduTeacher> getTeachers() {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        wrapper.last("limit 4");
        List<EduTeacher> teachers = baseMapper.selectList(wrapper);
        return teachers;
    }


    @Override
    public Map<String, Object> frontPageTeacher(Long current,Long size) {
        Page<EduTeacher> pageTeacher = new Page<>(current,size);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(pageTeacher, wrapper);

        current = pageTeacher.getCurrent();
        size = pageTeacher.getSize();
        long pages = pageTeacher.getPages();
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        boolean hasNext = pageTeacher.hasNext();//当前页是否有下一页
        boolean hasPrevious = pageTeacher.hasPrevious();//当前页是否有上一页

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
}
