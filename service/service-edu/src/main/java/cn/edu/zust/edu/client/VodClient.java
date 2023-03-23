package cn.edu.zust.edu.client;

import cn.edu.zust.common.result.ResultMode;
import cn.edu.zust.edu.client.degradeFeignClient.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *     在调用端创建 interface, 使用注解指定调用服务的名称,
 *     定义调用方法的映射路径
 *
 *     fallback 服务降级/容错时,调用本服务中的方法
 */
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
    /*
        @PathVariable 一定要指定名称,否则出错
     */
    @DeleteMapping("/vodservice/removeAlyVideo/{videoSourceId}") // 路径必须是完整的路径
    ResultMode deleteVideoById(@PathVariable(name = "videoSourceId") String videoSourceId);

    @DeleteMapping(value = "/vodservice/removeList")
    void removeVideoList(@RequestParam("videoIdList") List<String> videoIdList);
}
