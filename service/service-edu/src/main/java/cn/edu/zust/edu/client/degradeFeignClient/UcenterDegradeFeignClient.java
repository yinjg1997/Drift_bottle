package cn.edu.zust.edu.client.degradeFeignClient;

import cn.edu.zust.common.result.ResponseCode;
import cn.edu.zust.common.result.ResultMode;
import cn.edu.zust.edu.client.UcenterClient;
import org.springframework.stereotype.Component;

@Component
public class UcenterDegradeFeignClient implements UcenterClient {
    @Override
    public ResultMode getMemberInfoForComment(String id) {
        return ResultMode.error(ResponseCode.ERROR.getCode(), "获取用户信息失败",null);
    }
}
