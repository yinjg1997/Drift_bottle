package cn.edu.zust.sms.service.impl;

import cn.edu.zust.common.exception.BusinessException;
import cn.edu.zust.common.result.ResponseCode;
import cn.edu.zust.sms.service.SmsService;


import cn.edu.zust.sms.util.ConstantSmsUtils;
import cn.edu.zust.sms.util.RandomUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    /**
     * spring boot底层整合了redis，
     * 自动配置类中通过了一定的条件然后向spring ioc容器里面注册了redis模板组件
     */
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public String sendSmsCode(String phoneNumber) {
//        String code = redisTemplate.opsForValue().get(phoneNumber);
//        if(!StringUtils.isEmpty(code)) {
//            throw new GuliException(ResultCode.ERROR,"获取验证码太频繁了，请您稍后再试(⊙︿⊙)");
//        }

        /**
         * 应用内部自己生成的手机短信验证码, 之后调用阿里云短信发到用户手机上
         * （注意：根据项目具体的业务需求，有时候需要建立一张验证码表做一些的事情）
         */
        String code = RandomUtils.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        /**
         * key必须是code，因为阿里云openapi里面要求的请求报文的参数名相关内容必须是这个，详情参照阿里云openapi相关文档
         */
        param.put("code",code);
        //调用阿里云短信服务，实现发送手机短信验证码
        // 服务过期了
        // boolean isSend = sendSmsCode(param,phoneNumber);

        /**
         *   发送手机短信验证码成功后，将发送成功的验证码放到redis里面
         *   并设置redis键值对的有效时间
         */

        redisTemplate.opsForValue().set(phoneNumber,code,5, TimeUnit.MINUTES);//验证码5分钟内有效
        return code;
    }

    /**
     * 调用阿里云短信服务实现发送手机短信验证码
     * @param param
     * @param phoneNumber
     * @return 如果发送手机短信验证码成功返回true，否则返回false
     */
    private boolean sendSmsCode(Map<String, Object> param, String phoneNumber) {
        if (StringUtils.isEmpty(phoneNumber)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("cn-hangzhou", ConstantSmsUtils.ACCESS_KEY_ID, ConstantSmsUtils.ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        //设置相关固定的参数
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");//表示调用阿里云openapi中的哪一个接口，此处为SendSms
        request.putQueryParameter("RegionId", "cn-hangzhou");

        //设置发送手机短信验证码相关的参数
        request.putQueryParameter("PhoneNumbers", phoneNumber);//手机号
        request.putQueryParameter("SignName", "谷粒学院在线教育网站");//阿里云短信服务中申请的签名的签名名称
        request.putQueryParameter("TemplateCode", "SMS_189030146");//阿里云短信服务中申请的模板的模板CODE
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));//验证码数据，接口文档要求转换为json数据格式

        try {
            //最终发送手机短信验证码
            CommonResponse response = client.getCommonResponse(request);
            JSONObject jsonObject = JSON.parseObject(response.getData());
            if ("OK".equals(jsonObject.get("Code")))//表明发送手机短信验证码成功，代码为什么这么写，要去看阿里云openapi相关文档
                return true;
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
