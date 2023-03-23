package cn.edu.zust.order.service.impl;

import cn.edu.zust.common.vo.FrontCourseDetailVo;
import cn.edu.zust.common.vo.UcenterMemberVo;
import cn.edu.zust.order.client.EduClient;
import cn.edu.zust.order.client.UcenterClient;
import cn.edu.zust.order.entity.Order;
import cn.edu.zust.order.mapper.OrderMapper;
import cn.edu.zust.order.service.OrderService;
import cn.edu.zust.order.utils.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-07-05
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient ;

    @Autowired
    private UcenterClient ucenterClient;

    //1.生产订单
    @Override
    public String saveOrder(String courseId, String memberIdByJwtToken) {
        //通过远程调用根据用户id获取用户信息
        UcenterMemberVo ucenterMemberVo = ucenterClient.getUserInfoOrder(memberIdByJwtToken);
        log.info("courseId: {}", courseId);
        //通过远程调用根据课程id获取课信息
        FrontCourseDetailVo courseWebVoOrder = eduClient.frontGetCourseInfoForOder(courseId);
        //创建订单 设置数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseWebVoOrder.getTitle());
        order.setCourseCover(courseWebVoOrder.getCover());
        order.setTeacherName(courseWebVoOrder.getTeacherName());
        order.setTotalFee(courseWebVoOrder.getPrice());
        order.setMemberId(memberIdByJwtToken);
        order.setEmail(ucenterMemberVo.getMobile());
        order.setNickname(ucenterMemberVo.getNickname());
        order.setGmtCreate(LocalDateTime.now());
        order.setGmtModified(LocalDateTime.now());

        order.setStatus(0); //0 未支付 1 已支付
        order.setPayType(1); // 1 微信  2 支付宝
        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
