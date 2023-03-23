package cn.edu.zust.edu.controller;


import cn.edu.zust.common.result.ResultMode;
import cn.edu.zust.edu.client.VodClient;
import cn.edu.zust.edu.entity.Video;
import cn.edu.zust.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-06-28
 */
@Api(tags = "课时管理")
@Slf4j
@RestController
@RequestMapping("/eduservice/video")
public class VideoController {

    @Autowired
    private VideoService videoService;



    /**
     * 保存课程章节小节
     * @param video
     * @return
     */
    @ApiOperation(value = "保存课程章节小节")
    @PostMapping("addVideo")
    public ResultMode save(
            @ApiParam(name = "video", value = "课程视频对象", required = true)
            @RequestBody Video video){
        System.out.println("保存的video信息: "+ video);
        video.setGmtCreate(LocalDateTime.now());
        video.setGmtModified(LocalDateTime.now());
        boolean save = videoService.save(video);
        if(save){
            return ResultMode.ok();
        }
        return ResultMode.error();
    }

    /**
     * 查询课程章节小节
     * @param id
     * @return
     */
    @ApiOperation(value = "查询课程章节小节")
    @GetMapping("{id}")
    public ResultMode getVideoById(
            @ApiParam(name = "id", value = "课程章节小节", required = true)
            @PathVariable String id){
        try {
            Video eduVideo = videoService.getById(id);
            HashMap<String, Video> map = new HashMap<>();
            map.put("video", eduVideo);
            return  ResultMode.ok(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMode.error();
        }
    }

    /**
     * 修改课程章节小节
     * @param video
     * @return
     */
    @ApiOperation(value = "修改课程章节小节")
    @PostMapping("updateVideo")
    public ResultMode updateVideo(
            @ApiParam(name = "video", value = "课程章节小节对象", required = true)
            @RequestBody Video video){
        boolean update = videoService.updateById(video);
        if(update){
            return ResultMode.ok();
        }
        return ResultMode.error();
    }

    /**
     * TODO
     * 删除课程章节小节
     * @param id
     * @return
     */
    @ApiOperation(value = "删除课程章节小节")
    @DeleteMapping("{id}")
    public ResultMode deleteVideo(
            @ApiParam(name = "id", value = "课程章节小节ID", required = true)
            @PathVariable String id){
        Boolean flag = videoService.deleteByVideoId(id);
        if(flag){
            return ResultMode.ok();
        }
        return ResultMode.error();
    }

}

