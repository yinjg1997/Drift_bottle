package cn.edu.zust.sms.service;


public interface SmsService {

    /**
     * 发送手机短信验证码（阿里云短信服务，模板类型为验证码）
     * @param phoneNumber
     * 返回值为验证码 code
     */
    String sendSmsCode(String phoneNumber);

}
