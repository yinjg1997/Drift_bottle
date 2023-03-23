package cn.edu.zust.edu.service;

import cn.edu.zust.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-06-28
 */
public interface VideoService extends IService<Video> {

    /**
     * 删除小节
     * @param id
     * @return
     */
    Boolean deleteByVideoId(String id);

    /**
     *  根据课程ID删除小节
     * @param courseId
     * @return
     */
    Boolean deleteByCourseId(String courseId);

}
