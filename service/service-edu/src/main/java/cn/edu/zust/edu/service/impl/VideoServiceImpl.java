package cn.edu.zust.edu.service.impl;

import cn.edu.zust.common.exception.BusinessException;
import cn.edu.zust.common.result.ResultMode;
import cn.edu.zust.edu.client.VodClient;
import cn.edu.zust.edu.entity.Video;
import cn.edu.zust.edu.mapper.VideoMapper;
import cn.edu.zust.edu.service.VideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-06-28
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {


    @Autowired
    private VodClient vodClient;

    @Override
    public Boolean deleteByVideoId(String id) {
        //根据小节的ID查询视频的ID
        //查询云端视频id
        Video video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        System.out.println(videoSourceId);
        //删除视频资源
        if(!StringUtils.isEmpty(videoSourceId)){
            ResultMode resultMode = vodClient.deleteVideoById(videoSourceId);
            throw new BusinessException(resultMode.getCode(), resultMode.getMessage());
        }

        int delete = baseMapper.deleteById(id);

        return delete > 0;

    }

    @Override
    public Boolean deleteByCourseId(String courseId) {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.select("video_source_id");
        List<Video> videoList = baseMapper.selectList(wrapper);

        // 定义一个集合存放视频ID
        List<String> videoSourceIds = new ArrayList<>();

        // 可以获取视频ID
        for (Video video : videoList) {
            if(!StringUtils.isEmpty(video.getVideoSourceId())){
                videoSourceIds.add(video.getVideoSourceId());
            }
        }

        if(videoSourceIds.size() > 0){
            vodClient.removeVideoList(videoSourceIds);
        }

        // 删除
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        int delete = baseMapper.delete(queryWrapper);

        return delete > 0;
    }


}
