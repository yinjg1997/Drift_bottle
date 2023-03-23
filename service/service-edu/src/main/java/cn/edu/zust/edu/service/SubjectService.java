package cn.edu.zust.edu.service;

import cn.edu.zust.edu.entity.Subject;
import cn.edu.zust.edu.entity.vo.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-06-28
 */
public interface SubjectService extends IService<Subject> {
    /**
     * 导入Excl表格
     * @param file
     * @return
     */
    List<String> importExcel(MultipartFile file);

    /**
     * 获取课程分类树状数据
     * @return
     */
    List<OneSubject> getTree();

    /**
     * 根据ID删除课程分类
     * @param id
     * @return
     */
    boolean deleteById(String id);

    /**
     * 保存课程一级分类
     * @param subject
     * @return
     */
    boolean saveLevelOne(Subject subject);

    /**
     * 保存课程二级分类
     * @param subject
     * @return
     */
    boolean saveLevelTwo(Subject subject);

}
