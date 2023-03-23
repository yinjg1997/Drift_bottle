package cn.edu.zust.order.controller;


import cn.edu.zust.common.jwt.JwtUtils;
import cn.edu.zust.common.result.ResultMode;
import cn.edu.zust.order.entity.Order;
import cn.edu.zust.order.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-07-05
 */
@RestController
@RequestMapping("/orderservice/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    //1.根据课程id和用户id创建订单，返回订单id
    @PostMapping("createOrder/{courseId}")
    public ResultMode save(@PathVariable String courseId, HttpServletRequest request){
        //创建订单，返回订单号
        String orderNo =  orderService.saveOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        HashMap<String, String> map = new HashMap<>();
        map.put("orderNo", orderNo);

        return ResultMode.ok(map);
    }

    //2.根据订单ID获取订单信息
    @GetMapping("getOrderInfo/{orderNo}")
    public ResultMode getOrderInfo(@PathVariable String orderNo){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);
        HashMap<String, Order> map = new HashMap<>();
        map.put("order", order);
        return ResultMode.ok(map);
    }


    //根据用户id和课程id查询订单信息
    @GetMapping("isBuyCourse/{memberId}/{courseId}")
    public boolean isBuyCourse(@PathVariable String memberId,@PathVariable String courseId){
        //订单状态是1表示支付成功
        int count = orderService.count(new QueryWrapper<Order>()
                .eq("member_id", memberId)
                .eq("course_id", courseId)
                .eq("status", 1));
        if (count>0){
            return true;
        }else {
            return false;
        }

    }
}

