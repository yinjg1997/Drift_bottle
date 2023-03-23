package cn.edu.zust.edu.service;

import cn.edu.zust.edu.entity.Chapter;
import cn.edu.zust.edu.entity.vo.ChapterVo;
import cn.edu.zust.edu.entity.vo.OneChapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-06-28
 */
public interface ChapterService extends IService<Chapter> {

    /**
     * 课程章节添加
     * @param chapter
     * @return
     */
    boolean saveChapter(Chapter chapter);

    /**
     * 根据课程ID查询章节、小节的列表
     * @param id
     * @return
     */
    List<OneChapter> queryChapterAndVideoList(String id);


    /**
     * 根据章节的ID删除章节信息
     * @param id
     * @return
     */
    boolean deleteChapterById(String id);

    /**
     * 根据课程ID删除章节信息
     * @param courseId
     */
    void deleteChapterByCourseId(String courseId);

    /**
     * 根据课程ID来查询课程下面的课程章节信息和课程小节信息
     * @param courseId 课程ID
     * @return
     */
    List<ChapterVo> getChapterVideoByCourseId(String courseId);
}
