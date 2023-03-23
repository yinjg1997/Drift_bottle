package cn.edu.zust.sms.controller;


import cn.edu.zust.common.result.ResponseCode;
import cn.edu.zust.common.result.ResultMode;
import cn.edu.zust.sms.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@Api(tags = "阿里云短信服务API")
@RestController
@RequestMapping("/smsservice/sms")
@CrossOrigin
public class SmsController {

    @Autowired
    private SmsService smsService;

    //发送手机短信验证码（阿里云短信服务，模板类型为验证码）
    @ApiOperation(value = "发送手机短信验证码")
    @GetMapping("sendSms/{phoneNumber}")
    public ResultMode sendSms(@ApiParam(name = "phoneNumber", value = "手机号码") @PathVariable("phoneNumber") String phoneNumber) {
        String smsCode = smsService.sendSmsCode(phoneNumber);
        return ResultMode.ok(ResponseCode.SUCCESS.getCode(), "验证码成功发送，请您注意及时查收(*￣︶￣)",
                "发送的验证码为" + smsCode);
    }

}
