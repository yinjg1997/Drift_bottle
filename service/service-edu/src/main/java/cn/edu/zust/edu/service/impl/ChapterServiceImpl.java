package cn.edu.zust.edu.service.impl;

import cn.edu.zust.common.exception.BusinessException;
import cn.edu.zust.edu.entity.Chapter;
import cn.edu.zust.edu.entity.Video;
import cn.edu.zust.edu.entity.vo.ChapterVo;
import cn.edu.zust.edu.entity.vo.OneChapter;
import cn.edu.zust.edu.entity.vo.TwoVideo;
import cn.edu.zust.edu.entity.vo.VideoVo;
import cn.edu.zust.edu.mapper.ChapterMapper;
import cn.edu.zust.edu.service.ChapterService;
import cn.edu.zust.edu.service.VideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-06-28
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
    @Autowired
    private VideoService videoService;

    @Override
    public boolean saveChapter(Chapter chapter) {
        if(chapter == null){
            return false;
        }
        QueryWrapper<Chapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("title", chapter.getTitle());
        Integer count = baseMapper.selectCount(chapterWrapper);
        if(count > 0){
            return false;
        }
        chapter.setGmtCreate(LocalDateTime.now());
        chapter.setGmtModified(LocalDateTime.now());
        int insert = baseMapper.insert(chapter);
        return insert == 1;
    }

    @Override
    public List<OneChapter> queryChapterAndVideoList(String id) {
        //定义一个章节集合
        List<OneChapter> oneChapterList = new ArrayList<>();
        QueryWrapper<Chapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id",id);
        chapterWrapper.orderByAsc("sort", "id");
        //先查询章节列表集合
        List<Chapter> chapterList = baseMapper.selectList(chapterWrapper);
        //再遍历章节集合，获取每个章节ID
        for (Chapter eduChapter : chapterList) {
            OneChapter oneChapter = new OneChapter();
            BeanUtils.copyProperties(eduChapter,oneChapter);
            //再根据每个章节的ID查询节点的列表
            QueryWrapper<Video> videoWrapper = new QueryWrapper<>();
            videoWrapper.eq("chapter_id",oneChapter.getId());
            videoWrapper.orderByAsc("sort", "id");
            List<Video> eduVideoList = videoService.list(videoWrapper);
            //把小节的列表添加章节中
            for(Video eduVideo : eduVideoList){
                TwoVideo twoVideo = new TwoVideo();
                BeanUtils.copyProperties(eduVideo,twoVideo);
                oneChapter.getChildren().add(twoVideo);
            }
            oneChapterList.add(oneChapter);
        }
        return oneChapterList;
    }



    @Override
    public boolean deleteChapterById(String id) {
        //判断是否存在小节
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        List<Video> list = videoService.list(wrapper);
        if(list.size() != 0){
            throw new BusinessException(20001,"该分章节下存在视频课程，请先删除视频课程");
        }
        //删除章节
        int delete = baseMapper.deleteById(id);
        return delete > 0;

    }

    @Override
    public void deleteChapterByCourseId(String courseId) {
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        List<Chapter> chapterList = baseMapper.selectList(wrapper);

        // 定义一个集合存放章节ID
        List<String> chapterIds = new ArrayList<>();
        // 获取课程ID
        for (Chapter chapter : chapterList) {
            if(!StringUtils.isEmpty(chapter.getId())){
                chapterIds.add(chapter.getId());
            }
        }

        if(chapterIds.size() > 0){
            baseMapper.deleteBatchIds(chapterIds);
        }
    }

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<Chapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<Chapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        QueryWrapper<Video> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<Video> eduVideoList = videoService.list(wrapperVideo);

        //最终的数据模型，即最终需要给前端返回的信息
        List<ChapterVo> finalList = new ArrayList<>();

        for (int i = 0; i < eduChapterList.size(); i++) {
            Chapter eduChapter = eduChapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);

            finalList.add(chapterVo);

            List<VideoVo> videoList = new ArrayList<>();
            for (int m = 0; m < eduVideoList.size(); m++) {
                Video eduVideo = eduVideoList.get(m);
                if(eduChapter.getId().equals(eduVideo.getChapterId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoList);
        }
        return finalList;
    }
}
