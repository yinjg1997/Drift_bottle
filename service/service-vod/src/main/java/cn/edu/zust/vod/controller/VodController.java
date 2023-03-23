package cn.edu.zust.vod.controller;

import cn.edu.zust.common.result.ResultMode;
import cn.edu.zust.vod.service.VodService;
import com.aliyuncs.exceptions.ClientException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;


@Api(tags = "阿里云视频点播微服务")
@RestController
@RequestMapping("vodservice/vod")
public class VodController {

    @Autowired
    private VodService vodService;

    @ApiOperation(value = "上传视频")
    @PostMapping("uploadAlyVideo")
    public ResultMode uploadVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) throws Exception {

        String videoSourceId = vodService.uploadVideo(file);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("videoSourceId", videoSourceId);
        return ResultMode.ok(map);
    }

    /**
     * 根据视频ID删除视频
     * @return
     */
    @ApiOperation(value = "删除视频")
    @DeleteMapping("removeAlyVideo/{videoSourceId}")
    public ResultMode deleteVideoById(@PathVariable("videoSourceId") String videoSourceId){
        Boolean flag = vodService.deleteVodById(videoSourceId);
        if(flag){
            return ResultMode.ok();
        }
        return ResultMode.error();
    }


    /**
     * 批量删除视频
     * @return
     */
    @ApiOperation(value = "批量删除视频")
    @DeleteMapping("removeList")
    public ResultMode removeVideoList(
            @ApiParam(name = "videoIdList", value = "云端视频id", required = true)
            @RequestParam("videoIdList") List videoIdList){
        Boolean flag = vodService.removeVideoList(videoIdList);
        if(flag){
            return ResultMode.ok();
        }
        return ResultMode.error();
    }

    @ApiOperation(value = "获取播放凭证")
    @GetMapping("getVideoPlayAuth/{videoSourceId}")
    public ResultMode getVideoPlayAuth(
            @ApiParam(name = "videoSourceId", value = "云端视频id", required = true)
            @PathVariable String videoSourceId) throws ClientException {
        String playAuth = vodService.getVideoPlayAuth(videoSourceId);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("videoPlayAuth", playAuth);
        return ResultMode.ok(map);
    }

}
