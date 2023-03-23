package cn.edu.zust.vod.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface VodService {

    /**
     *
     * @param file
     * @return
     */
    String uploadVideo(MultipartFile file);

    /**
     * 根据视频ID删除视频
     * @param videoSourceId
     * @return
     */
    Boolean deleteVodById(String videoSourceId);

    /**
     * 批量删除云端视频
     * @param videoIdList
     * @return
     */
    Boolean removeVideoList(List videoIdList);

    /**
     * 获取播放凭证
     * @param videoId
     * @return
     */
    String getVideoPlayAuth(String videoId) throws ClientException;
}
