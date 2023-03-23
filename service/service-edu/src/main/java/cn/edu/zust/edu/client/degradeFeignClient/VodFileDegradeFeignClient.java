package cn.edu.zust.edu.client.degradeFeignClient;

import cn.edu.zust.common.result.ResponseCode;
import cn.edu.zust.common.result.ResultMode;
import cn.edu.zust.edu.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 当服务调用出错时,进行服务降级
 *
 * Degrade 降级
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public ResultMode deleteVideoById(String videoSourceId) {
        return ResultMode.error(ResponseCode.ERROR.getCode(), "云端删除视频出错了", null);
    }

    @Override
    public void removeVideoList(List<String> videoIdList) {
        // return ResultMode.error(ResponseCode.ERROR.getCode(), "删除多个视频出错了", null);
        return;
    }
}
