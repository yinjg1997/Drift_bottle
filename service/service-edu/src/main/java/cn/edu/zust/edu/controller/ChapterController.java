package cn.edu.zust.edu.controller;


import cn.edu.zust.common.result.ResultMode;
import cn.edu.zust.edu.entity.Chapter;
import cn.edu.zust.edu.entity.vo.OneChapter;
import cn.edu.zust.edu.service.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-06-28
 */
@Api(tags = "章节管理")
@RestController
@RequestMapping("/eduservice/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    /**
     * 根据课程ID查询章节、小节的列表
     * 1、创建一个对象，作为章节Vo，里面包括二级Vo
     * 2、创建二级的Vo（Video）
     * 3、根据课程ID查询章节的列表，遍历这个列表，根据每一个章节的ID查询二级列表（Video集合）
     * @param courseId
     * @return
     */
    @ApiOperation(value = "根据课程ID查询章节、小节的列表")
    @GetMapping("getChapterVideo/{courseId}")
    public ResultMode getChapterAndVideoList(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId){
        List<OneChapter> list = chapterService.queryChapterAndVideoList(courseId);
        if(list.size()>=0){
            HashMap<String , List<OneChapter>> map = new HashMap<>();
            map.put("list", list);
            return ResultMode.ok(map);
        }
        return ResultMode.error();
    }

    /**
     * 课程章节添加
     * @param chapter
     * @return
     */
    @ApiOperation(value = "课程章节添加")
    @PostMapping("addChapter")
    public ResultMode save(
            @ApiParam(name = "chapter", value = "课程章节对象", required = true)
            @RequestBody Chapter chapter){

        boolean flag = chapterService.saveChapter(chapter);
        if(flag){
            return ResultMode.ok();
        }else {
            return ResultMode.error();
        }
    }

    /**
     * 根据章节ID获取章节信息
     * @param
     * @return
     */
    @ApiOperation(value = "根据章节ID获取章节信息")
    @GetMapping("getChapterInfo/{chapterId}")
    public ResultMode getChapterById(
            @ApiParam(name = "chapterId", value = "课程ID", required = true)
            @PathVariable String chapterId){

        Chapter chapter = chapterService.getById(chapterId);
        HashMap<String, Chapter> map = new HashMap<>();
        map.put("chapter", chapter);
        return ResultMode.ok(map);
    }

    /**
     * 根据章节的ID修改章节信息
     * @param chapter
     * @return
     */
    @ApiOperation(value = "根据章节的ID修改章节信息")
    @PostMapping("updateChapter")
    public ResultMode updateById(
            @ApiParam(name = "chapter", value = "课程章节对象", required = true)
            @RequestBody Chapter chapter){
        chapter.setGmtModified(LocalDateTime.now());
        boolean flag = chapterService.updateById(chapter);
        if(flag){
            return ResultMode.ok();
        } else {
            return ResultMode.error();
        }
    }

    /**
     * 根据章节的ID删除章节信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据章节的ID删除章节信息")
    @DeleteMapping("{id}")
    public ResultMode deleteChapterById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id){

        boolean flag = chapterService.deleteChapterById(id);
        if(flag){
            return ResultMode.ok();
        } else {
            return ResultMode.error();
        }
    }

}

