package cn.edu.zust.order.controller;


import cn.edu.zust.common.result.ResponseCode;
import cn.edu.zust.common.result.ResultMode;
import cn.edu.zust.order.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-07-05
 */
@RestController
@RequestMapping("/orderservice/payLog")
public class PayLogController {

    @Autowired
    private PayLogService payLogService;
    //生成微信支付二维码的接口
    //参数是订单号
    @GetMapping("createWXPayCode/{orderNo}")
    public ResultMode createWXPayCode(@PathVariable String orderNo){
        //返回信息，包含二维码地址，还有其他信息
        Map map = payLogService.createWXPayCode(orderNo);
        System.out.println("返回信息，包含二维码地址，还有其他信息"+map);
        return ResultMode.ok(map);
    }


    //查询订单支付状态
    //参数:订单号，根据订单号查询支付状态
    @GetMapping("/queryPayStatus/{orderNo}")
    public ResultMode queryPayStatus(@PathVariable String orderNo) {
        //查询支付状态
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        System.out.println("根据订单号查询支付状态"+map);
        if (map == null) {
            //出错
            return ResultMode.error(ResponseCode.ERROR.getCode(), "支付出错了", null);
        }
        if (map.get("trade_state").equals("SUCCESS")) {//如果成功
            //更改订单状态
            payLogService.updateOrderStatus(map);
            return ResultMode.ok(ResponseCode.SUCCESS.getCode(), "支付成功", null);
        }
        return ResultMode.ok(25000, "支付中", null);
    }

}

