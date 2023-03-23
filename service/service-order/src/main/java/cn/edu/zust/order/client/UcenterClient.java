package cn.edu.zust.order.client;


import cn.edu.zust.common.vo.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    /**
     * 根据token字符串获取用户信息
     * @param id
     * @return
     */
    @PostMapping("/ucenterservice/member/getMemberInfoById")
    UcenterMemberVo getUserInfoOrder(@RequestParam(name = "id") String id);
}
