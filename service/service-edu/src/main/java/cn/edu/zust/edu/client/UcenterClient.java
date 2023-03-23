package cn.edu.zust.edu.client;

import cn.edu.zust.common.result.ResultMode;
import cn.edu.zust.edu.client.degradeFeignClient.UcenterDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "service-ucenter", fallback = UcenterDegradeFeignClient.class)
@Component
public interface UcenterClient {

    //根据用户token获取用户信息
    @PostMapping("/ucenterservice/member/getMemberInfoById")
    ResultMode getMemberInfoForComment(@RequestParam(name = "id") String id);

}
